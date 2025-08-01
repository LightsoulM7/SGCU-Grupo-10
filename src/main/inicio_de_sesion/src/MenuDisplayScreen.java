import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuDisplayScreen {

    private JFrame frame_menu_display;
    private final String menu_file_path = "../../db/menu_semanal.txt";
    private final String costos_variables_file_path = "../../db/CostosVariables.txt";
    private final String gastos_fijos_file_path = "../../db/gastos_fijos.txt";
    private String cedulaUsuario;

    public MenuDisplayScreen(String menuType, String userType, String cedula) {
        this.cedulaUsuario = cedula;
        initialize(menuType, userType);
    }

    private void initialize(String menuType, String userType) {
        frame_menu_display = new JFrame("Menú del Día");
        frame_menu_display.setSize(700, 866);
        frame_menu_display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame_menu_display.setLocationRelativeTo(null);
        frame_menu_display.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_menu_display.add(panel);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);

        String[] menuDetails = getMenuDetails(menuType);
        if (menuDetails != null) {
            String foodName = menuDetails[0];
            String ingredients = menuDetails[1];
            double merma = Double.parseDouble(menuDetails[2]);
            int estimacionConsumo = Integer.parseInt(menuDetails[3]);
            String description = menuDetails[4];

            double price = calculateCCB(ingredients, merma, estimacionConsumo, userType);

            JTextArea menuArea = new JTextArea();
            menuArea.setFont(new Font("Arial", Font.PLAIN, 18));
            menuArea.setText(String.format("Nombre: %s\n\nDescripción: %s\n\nPrecio: %.2f Bs.", foodName, description, price));
            menuArea.setWrapStyleWord(true);
            menuArea.setLineWrap(true);
            menuArea.setOpaque(false);
            menuArea.setEditable(false);
            menuArea.setForeground(Color.WHITE);
            menuArea.setBounds(100, 200, 500, 200);
            fondo_label.add(menuArea);

        } else {
            JLabel noMenuLabel = new JLabel("No se encuentra disponible el menú de hoy.", SwingConstants.CENTER);
            noMenuLabel.setFont(new Font("Arial", Font.BOLD, 24));
            noMenuLabel.setForeground(Color.WHITE);
            noMenuLabel.setBounds(100, 300, 500, 100);
            fondo_label.add(noMenuLabel);
        }

        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(50, 50, 80, 30);
        btnBack.addActionListener(e -> {
            frame_menu_display.dispose();
            if ("user".equals(userType)) {
                new MenuSelectionScreen(cedulaUsuario).mostrar();
            } else {
                new MenuSelectionScreen2(cedulaUsuario).mostrar();
            }
        });
        fondo_label.add(btnBack);

        panel.add(fondo_label);
    }

    private String[] getMenuDetails(String menuType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(menu_file_path))) {
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

    private double calculateCCB(String ingredients, double merma, int estimacionConsumo, String userType) {
        double sumatoriaCostosFijos = getSumatoriaCostosFijos();
        double sumatoriaIngredientes = getSumatoriaIngredientes(ingredients);

        double ccb = (sumatoriaCostosFijos / 20 + sumatoriaIngredientes) / estimacionConsumo * (1 + merma);

        if ("user".equals(userType)) {
            return ccb * 0.20;
        } else {
            return ccb;
        }
    }

    private double getSumatoriaCostosFijos() {
        double sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(gastos_fijos_file_path))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader(costos_variables_file_path))) {
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

    public void mostrar() {
        frame_menu_display.setVisible(true);
    }
}