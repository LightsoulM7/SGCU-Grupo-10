import javax.swing.*;
import java.awt.*;


public class recuperar_contrasena {

    private JFrame frame_recuperar_contrasena;

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

        ImageIcon fondo = new ImageIcon("C:\\Users\\Usuario\\OneDrive\\Escritorio\\Proyecto ingenieria\\IS-PROJECT\\Imagenes\\cuadro_ucv.png");
        Image cuadrar_imagen = fondo.getImage().getScaledInstance(700, 866, Image.SCALE_SMOOTH);
        JLabel cuadro_imagen = new JLabel(new ImageIcon(cuadrar_imagen));
        cuadro_imagen.setBounds(0, 0, 700, 866);
        pantalla.add(cuadro_imagen);

        JButton boton_cancelar = new JButton();
        boton_cancelar.setBounds(580, 5, 93, 34);
        boton_cancelar.setOpaque(false);
        boton_cancelar.setContentAreaFilled(false);
        boton_cancelar.setFocusPainted(false);

        ImageIcon imagen_boton_cancelar = new ImageIcon("Imagenes\\recuperar_contrasena\\cancelar.png");
        Image cuadrar_imagen_boton_cancelar = imagen_boton_cancelar.getImage().getScaledInstance(93, 34, Image.SCALE_SMOOTH);
        JLabel label_boton_cancelar = new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));

        JLabel labelRecuperacion = new JLabel("Recuperar Contraseña", SwingConstants.CENTER);
        labelRecuperacion.setFont(new Font("Inter", Font.BOLD, 28));
        labelRecuperacion.setBounds(150, 100, 400, 50);
        cuadro_imagen.add(labelRecuperacion);

        JTextField campoCorreo = new JTextField("INGRESE SU CORREO ELECTRÓNICO");
        campoCorreo.setBounds(100, 200, 500, 66);
        campoCorreo.setHorizontalAlignment(JTextField.CENTER);
        campoCorreo.setFont(new Font("Inter", Font.BOLD, 24));
        cuadro_imagen.add(campoCorreo);

        JButton botonEnviar = new JButton("ENVIAR");
        botonEnviar.setBounds(300, 300, 100, 50);
        botonEnviar.setFont(new Font("Inter", Font.BOLD, 20));
        cuadro_imagen.add(botonEnviar);

        label_boton_cancelar.setBounds(580, 5, 93, 34);
        cuadro_imagen.add(boton_cancelar);
        cuadro_imagen.add(label_boton_cancelar);

        botonEnviar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Se ha enviado un correo de recuperación a " + campoCorreo.getText());
        });

        boton_cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton_cancelar.setIcon(new ImageIcon(imagen_boton_cancelar.getImage().getScaledInstance(98, 39, Image.SCALE_SMOOTH)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_boton_cancelar.setIcon(new ImageIcon(imagen_boton_cancelar.getImage().getScaledInstance(93, 34, Image.SCALE_SMOOTH)));
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