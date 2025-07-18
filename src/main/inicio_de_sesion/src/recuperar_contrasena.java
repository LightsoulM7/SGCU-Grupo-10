import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class recuperar_contrasena {

    private JFrame frame_recuperar_contrasena;
    private JTextField campoCorreo;

    public recuperar_contrasena() {
        initialize();
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

        ImageIcon imagen_boton_enviar=new ImageIcon("../../Imagenes/enviar.png");
        Image cuadrar_imagen_enviar=imagen_boton_enviar.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);
        JLabel label_boton_enviar=new JLabel(new ImageIcon(cuadrar_imagen_enviar));

        label_boton_enviar.setBounds(250,300,163,76);
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
            String email = campoCorreo.getText();
            if (email.contains("@") && !email.equals("INGRESE SU CORREO ELECTRÓNICO")) {
                JOptionPane.showMessageDialog(null, "Se ha enviado un código de recuperación a su correo electrónico ");
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un correo electrónico válido (debe contener '@').", "Error de Correo", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void mostrar() {
        frame_recuperar_contrasena.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new recuperar_contrasena().mostrar());
    }
}