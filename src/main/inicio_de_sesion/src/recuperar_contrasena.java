import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class recuperar_contrasena {

    private JFrame frame_recuperar_contrasena;
    private JTextField campoCorreo;
    private Set<String> registeredEmails = new HashSet<>();

    public recuperar_contrasena() {
        loadRegisteredEmails();
        initialize();
    }

    private void loadRegisteredEmails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("../../db/usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) { // Assuming format: cedula,email,password_hash
                    registeredEmails.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar los correos registrados: " + e.getMessage());
            // No es crítico si el archivo no existe al inicio
        }
    }

    private void initialize() {
        frame_recuperar_contrasena = new JFrame();
        frame_recuperar_contrasena.setTitle("Recuperar Contraseña");
        frame_recuperar_contrasena.setSize(700, 866);
        frame_recuperar_contrasena.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_recuperar_contrasena.setLocationRelativeTo(null);
        frame_recuperar_contrasena.setResizable(false);

        JPanel pantalla = new JPanel(new BorderLayout());
        frame_recuperar_contrasena.add(pantalla);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_ucv.png");
        Image cuadrar_imagen = fondo.getImage().getScaledInstance(700, 866, Image.SCALE_SMOOTH);
        JLabel cuadro_imagen = new JLabel(new ImageIcon(cuadrar_imagen));
        cuadro_imagen.setBounds(0, 0, 700, 866);
        pantalla.add(cuadro_imagen);

        JLabel labelRecuperacion = new JLabel("Recuperar Contraseña", SwingConstants.CENTER);
        labelRecuperacion.setFont(new Font("Inter", Font.BOLD, 28));
        labelRecuperacion.setBounds(150, 100, 400, 50);
        labelRecuperacion.setForeground(Color.WHITE);
        cuadro_imagen.add(labelRecuperacion);

        campoCorreo = new JTextField("INGRESE SU CORREO ELECTRÓNICO");
        campoCorreo.setBounds(100, 200, 500, 66);
        campoCorreo.setHorizontalAlignment(JTextField.CENTER);
        campoCorreo.setFont(new Font("Inter", Font.BOLD, 24));
        campoCorreo.setForeground(Color.GRAY);
        cuadro_imagen.add(campoCorreo);

        JButton botonEnviar = new JButton();
        botonEnviar.setBounds(250, 300, 163, 76);
        botonEnviar.setOpaque(false);
        botonEnviar.setContentAreaFilled(false);
        botonEnviar.setFocusPainted(false);

        cuadro_imagen.add(botonEnviar);

        ImageIcon imagen_boton_enviar = new ImageIcon("../../Imagenes/enviar.png");
        Image cuadrar_imagen_enviar = imagen_boton_enviar.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH);
        JLabel label_boton_enviar = new JLabel(new ImageIcon(cuadrar_imagen_enviar));

        label_boton_enviar.setBounds(250, 300, 163, 76);
        cuadro_imagen.add(label_boton_enviar);

        campoCorreo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoCorreo.getText().equals("INGRESE SU CORREO ELECTRÓNICO")) {
                    campoCorreo.setText("");
                    campoCorreo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campoCorreo.getText().isEmpty()) {
                    campoCorreo.setText("INGRESE SU CORREO ELECTRÓNICO");
                    campoCorreo.setForeground(Color.GRAY);
                }
            }
        });

        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == pantalla && campoCorreo.isFocusOwner()) {
                    campoCorreo.transferFocusBackward();
                    pantalla.requestFocusInWindow();
                }
            }
        });

        // Botón Atrás
        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(580, 5, 93, 34);
        btnBack.addActionListener(e -> {
            frame_recuperar_contrasena.dispose();
            inicio_de_sesion loginScreen = new inicio_de_sesion();
            loginScreen.mostrar();
        });
        cuadro_imagen.add(btnBack);

        botonEnviar.addActionListener(e -> {
            String email = campoCorreo.getText().trim();

            if (email.equals("INGRESE SU CORREO ELECTRÓNICO") || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese su correo electrónico.", "Error de Correo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un correo electrónico válido (debe contener '@' y '.').", "Error de Correo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!registeredEmails.contains(email)) {
                JOptionPane.showMessageDialog(null, "Este correo electrónico no está registrado.", "Error de Correo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Se ha enviado un código de recuperación a su correo electrónico.");
            // Aquí iría la lógica para enviar el código de recuperación
        });
    }

    public void mostrar() {
        frame_recuperar_contrasena.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new recuperar_contrasena().mostrar());
    }
}