import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReconocimientoFacial {

    private JFrame frame_reconocimiento;
    private JLabel uploaded_image_label;
    private BufferedImage uploaded_image;
    private JComboBox<String> menu_selection_combo;
    private final String ESTADO_COMEDOR_FILE = "../../db/estado_comedor.txt";
    private final String USUARIOS_FILE = "../../db/usuarios.txt";
    private final String CEDULAS_COMUNIDAD_FILE = "../../db/cedulasComunidad.txt";
    private final String MENU_FILE = "../../db/menu_semanal.txt";
    private final String COSTOS_VARIABLES_FILE = "../../db/CostosVariables.txt";
    private final String GASTOS_FIJOS_FILE = "../../db/gastos_fijos.txt";

    public ReconocimientoFacial() {
        initialize();
    }

    private void initialize() {
        frame_reconocimiento = new JFrame();
        frame_reconocimiento.setTitle("Reconocimiento Facial");
        frame_reconocimiento.setSize(700, 866);
        frame_reconocimiento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame_reconocimiento.setLocationRelativeTo(null);
        frame_reconocimiento.setResizable(false);

        JPanel pantalla = new JPanel(null);
        frame_reconocimiento.add(pantalla);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel cuadro_imagen = new JLabel(fondo);
        cuadro_imagen.setBounds(0, 0, 700, 866);

        // --- TITULO ---
        JLabel lblTitulo = new JLabel("Reconocimiento Facial");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(150, 100, 400, 50);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        pantalla.add(lblTitulo);

        // --- SELECCION DE MENU ---
        String[] menuOptions = {"Desayuno", "Almuerzo"};
        menu_selection_combo = new JComboBox<>(menuOptions);
        menu_selection_combo.setBounds(250, 160, 200, 30);
        pantalla.add(menu_selection_combo);

        // --- BOTONES ---
        JButton btnSubirImagen = new JButton("Subir Imagen");
        btnSubirImagen.setBounds(250, 210, 200, 50);
        pantalla.add(btnSubirImagen);

        JButton btnComparar = new JButton("Comparar");
        btnComparar.setBounds(250, 600, 200, 50);
        pantalla.add(btnComparar);

        // --- VISUALIZADOR DE IMAGEN ---
        uploaded_image_label = new JLabel();
        uploaded_image_label.setBounds(200, 300, 300, 300);
        uploaded_image_label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        pantalla.add(uploaded_image_label);
        
        // --- BOTON ATRAS ---
        JButton btnAtras = new JButton("Atrás");
        btnAtras.setBounds(50, 50, 100, 40);
        pantalla.add(btnAtras);

        // --- ACCIONES DE LOS BOTONES ---
        btnSubirImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame_reconocimiento);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    uploaded_image = ImageIO.read(selectedFile);
                    Image scaledImage = uploaded_image.getScaledInstance(uploaded_image_label.getWidth(), uploaded_image_label.getHeight(), Image.SCALE_SMOOTH);
                    uploaded_image_label.setIcon(new ImageIcon(scaledImage));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnComparar.addActionListener(e -> {
            if (uploaded_image == null) {
                JOptionPane.showMessageDialog(frame_reconocimiento, "Por favor, suba una imagen primero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File reconocimientoDir = new File("../../db/reconocimiento");
            if (!reconocimientoDir.exists() || !reconocimientoDir.isDirectory()) {
                JOptionPane.showMessageDialog(frame_reconocimiento, "El directorio 'reconocimiento' no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean matchFound = false;
            String cedulaReconocida = null;

            for (File imageFile : reconocimientoDir.listFiles()) {
                try {
                    BufferedImage dbImage = ImageIO.read(imageFile);
                    if (compareImages(uploaded_image, dbImage)) {
                        matchFound = true;
                        // Extraer la cédula del nombre del archivo (asumiendo formato cedula.png)
                        cedulaReconocida = imageFile.getName().replace(".png", "");
                        break;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (matchFound && cedulaReconocida != null) {
                Map<String, String> estadoComedor = readEstadoComedor();
                String userRole = getUserRole(cedulaReconocida);
                String selectedMenuType = (String) menu_selection_combo.getSelectedItem();
                String estadoKey = cedulaReconocida + ":" + selectedMenuType;

                if (estadoComedor.containsKey(estadoKey) && estadoComedor.get(estadoKey).equals("entered")) {
                    JOptionPane.showMessageDialog(frame_reconocimiento, "La persona con cédula " + cedulaReconocida + " ya ha entrado al comedor para " + selectedMenuType.toLowerCase() + ".", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
                } else {
                    String[] menuDetails = getMenuDetails(selectedMenuType);
                    if (menuDetails != null) {
                        String ingredients = menuDetails[1];
                        double merma = Double.parseDouble(menuDetails[2]);
                        int estimacionConsumo = Integer.parseInt(menuDetails[3]);

                        double mealCost = calculateCCB(ingredients, merma, estimacionConsumo, userRole);
                        double currentBalance = getSaldoActual(cedulaReconocida);

                        if (currentBalance >= mealCost) {
                            updateSaldo(cedulaReconocida, currentBalance - mealCost);
                            estadoComedor.put(estadoKey, "entered");
                            writeEstadoComedor(estadoComedor);
                            JOptionPane.showMessageDialog(frame_reconocimiento, "Acceso concedido para la cédula: " + cedulaReconocida + ". Costo de la comida: " + String.format("%.2f", mealCost) + " Bs. Saldo restante: " + String.format("%.2f", getSaldoActual(cedulaReconocida)) + " Bs.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame_reconocimiento, "Saldo insuficiente para la cédula: " + cedulaReconocida + ". Costo de la comida: " + String.format("%.2f", mealCost) + " Bs. Saldo actual: " + String.format("%.2f", currentBalance) + " Bs.", "Saldo Insuficiente", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame_reconocimiento, "Menú de " + selectedMenuType.toLowerCase() + " no disponible para calcular el costo.", "Error de Menú", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame_reconocimiento, "Acceso denegado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnAtras.addActionListener(e -> {
            frame_reconocimiento.dispose();
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.mostrar();
        });

        pantalla.add(cuadro_imagen);
        frame_reconocimiento.setVisible(true);
    }

    private boolean compareImages(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }

        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void mostrar() {
        frame_reconocimiento.setVisible(true);
    }

    private Map<String, String> readEstadoComedor() {
        Map<String, String> estado = new HashMap<>();
        File file = new File(ESTADO_COMEDOR_FILE);
        if (!file.exists()) {
            return estado;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) { // Ahora esperamos cedula:tipo_comida:estado
                    estado.put(parts[0] + ":" + parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de estado del comedor: " + e.getMessage());
        }
        return estado;
    }

    private void writeEstadoComedor(Map<String, String> estado) {
        try (FileWriter fw = new FileWriter(ESTADO_COMEDOR_FILE)) {
            for (Map.Entry<String, String> entry : estado.entrySet()) {
                fw.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de estado del comedor: " + e.getMessage());
        }
    }

    private String getUserRole(String cedula) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CEDULAS_COMUNIDAD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equals(cedula)) {
                    return parts[1].trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de roles de la comunidad: " + e.getMessage());
        }
        return null;
    }

    private double getSaldoActual(String cedula) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].trim().equals(cedula)) {
                    return Double.parseDouble(parts[3].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el saldo del usuario: " + e.getMessage());
        }
        return 0.0;
    }

    private void updateSaldo(String cedula, double newSaldo) {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].trim().equals(cedula)) {
   fileContent.add(parts[0] + "," + parts[1] + "," + parts[2] + "," + String.format(Locale.US, "%.2f", newSaldo));
                } else {
                    fileContent.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios para actualizar el saldo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE))) {
            for (String line : fileContent) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de usuarios para actualizar el saldo: " + e.getMessage());
        }
    }

    private String[] getMenuDetails(String menuType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equalsIgnoreCase(menuType)) {
                    return new String[]{parts[1], parts[2], parts[3], parts[4], parts[5]};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double calculateCCB(String ingredients, double merma, double estimacionConsumo, String userType) {
        double sumatoriaCostosFijos = getSumatoriaCostosFijos();
        double sumatoriaIngredientes = getSumatoriaIngredientes(ingredients);

        double ccb = (sumatoriaCostosFijos / 20 + sumatoriaIngredientes) / estimacionConsumo * (1 + merma);

        if ("usuario".equals(userType)) {
            return ccb * 0.20;
        } else if ("admin".equals(userType)) {
            return ccb;
        } else {
            return ccb; // Default para otros roles o si no se especifica
        }
    }

    private double getSumatoriaCostosFijos() {
        double sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(GASTOS_FIJOS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    sum += Double.parseDouble(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return sum;
    }

    private double getSumatoriaIngredientes(String ingredients) {
        Map<String, Double> ingredientPrices = getIngredientPrices();
        String[] ingredientList = ingredients.split(",");
        double totalPrice = 0;

        for (String ingredient : ingredientList) {
            totalPrice += ingredientPrices.getOrDefault(ingredient.trim(), 0.0);
        }

        return totalPrice;
    }

    private Map<String, Double> getIngredientPrices() {
        Map<String, Double> prices = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COSTOS_VARIABLES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = "";
                double price = 0;
                for (String part : parts) {
                    if (part.trim().startsWith("Nombre:")) {
                        name = part.replace("Nombre:", "").trim();
                    }
                    if (part.trim().startsWith("Precio:")) {
                        price = Double.parseDouble(part.replace("Precio:", "").trim());
                    }
                }
                if (!name.isEmpty()) {
                    prices.put(name, price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return prices;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReconocimientoFacial().mostrar());
    }
}
