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
import java.util.HashMap;
import java.util.Map;

public class ReconocimientoFacial {

    private JFrame frame_reconocimiento;
    private JLabel uploaded_image_label;
    private BufferedImage uploaded_image;
    private final String ESTADO_COMEDOR_FILE = "../../db/estado_comedor.txt";

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

        // --- BOTONES ---
        JButton btnSubirImagen = new JButton("Subir Imagen");
        btnSubirImagen.setBounds(250, 200, 200, 50);
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
                if (estadoComedor.containsKey(cedulaReconocida) && estadoComedor.get(cedulaReconocida).equals("entered")) {
                    JOptionPane.showMessageDialog(frame_reconocimiento, "La persona con cédula " + cedulaReconocida + " ya ha entrado al comedor.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
                } else {
                    estadoComedor.put(cedulaReconocida, "entered");
                    writeEstadoComedor(estadoComedor);
                    JOptionPane.showMessageDialog(frame_reconocimiento, "Acceso concedido para la cédula: " + cedulaReconocida, "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
                if (parts.length == 2) {
                    estado.put(parts[0], parts[1]);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReconocimientoFacial().mostrar());
    }
}
