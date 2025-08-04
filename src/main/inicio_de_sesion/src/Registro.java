import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.AbstractDocument; // Importación necesaria
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter; // Importación necesaria

public class Registro {

    private JFrame frame_inicio_de_sesion;

    public Registro() {
        initialize();
    }

    private void initialize() {
        frame_inicio_de_sesion = new JFrame();
        frame_inicio_de_sesion.setTitle("Registro");
        frame_inicio_de_sesion.setSize(700, 866);
        frame_inicio_de_sesion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_inicio_de_sesion.setLocationRelativeTo(null);
        frame_inicio_de_sesion.setResizable(false);

        JPanel pantalla = new JPanel(new BorderLayout());
        frame_inicio_de_sesion.add(pantalla);

        ImageIcon inicio_sesion = new ImageIcon("../../Imagenes/cuadro_ucv.png");
        Image cuadrar_imagen = inicio_sesion.getImage().getScaledInstance(700, 866, Image.SCALE_SMOOTH);
        JLabel cuadro_imagen = new JLabel(new ImageIcon(cuadrar_imagen));

        cuadro_imagen.setBounds(0, 0, 700, 866);
        pantalla.add(cuadro_imagen);

        JButton btnAtras = new JButton("Atrás");
        btnAtras.setBounds(50, 50, 100, 40);
        cuadro_imagen.add(btnAtras);

        btnAtras.addActionListener(e -> {
            frame_inicio_de_sesion.dispose();
            inicio_de_sesion login = new inicio_de_sesion();
            login.mostrar();
        });

        JButton boton_registrarse = new JButton();
        boton_registrarse.setBounds(250, 520, 163, 76);
        boton_registrarse.setOpaque(false);
        boton_registrarse.setContentAreaFilled(false);
        boton_registrarse.setFocusPainted(false);

        ImageIcon imagen_boton_registrarse = new ImageIcon("../../Imagenes/Registrarse.png");
        Image cuadrar_imagen_boton_registrarse = imagen_boton_registrarse.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH);
        JLabel label_boton_registrarse = new JLabel(new ImageIcon(cuadrar_imagen_boton_registrarse));

        JTextField ingresar_cedula = new JTextField("INGRESE SU CEDULA");
        ingresar_cedula.setBounds(110, 220, 480, 66);
        ingresar_cedula.setHorizontalAlignment(JTextField.CENTER);
        ingresar_cedula.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_cedula.setForeground(Color.GRAY); // Cambiado a GRAY para el placeholder

        // Aplicar DocumentFilter para permitir solo números en ingresar_cedula
        ((AbstractDocument) ingresar_cedula.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (string.matches("\\d*")) { // Solo permite dígitos
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                if (text.matches("\\d*")) { // Solo permite dígitos
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });


        JTextField ingresar_correo = new JTextField("INGRESE SU CORREO ELECTRONICO");
        ingresar_correo.setBounds(110, 290, 480, 66);
        ingresar_correo.setHorizontalAlignment(JTextField.CENTER);
        ingresar_correo.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_correo.setForeground(Color.GRAY); // Cambiado a GRAY para el placeholder

        JTextField ingresar_contrasena = new JTextField("INGRESE SU CONTRASEÑA");
        ingresar_contrasena.setBounds(110, 360, 480, 66);
        ingresar_contrasena.setHorizontalAlignment(JTextField.CENTER);
        ingresar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_contrasena.setForeground(Color.GRAY); // Cambiado a GRAY para el placeholder

        JTextField confirmar_contrasena = new JTextField("CONFIRMAR CONTRASEÑA");
        confirmar_contrasena.setBounds(110, 430, 480, 66);
        confirmar_contrasena.setHorizontalAlignment(JTextField.CENTER);
        confirmar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        confirmar_contrasena.setForeground(Color.GRAY); // Cambiado a GRAY para el placeholder


        ingresar_cedula.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ingresar_cedula.getText().equals("INGRESE SU CEDULA")) {
                    ingresar_cedula.setText("");
                    ingresar_cedula.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingresar_cedula.getText().isEmpty()) {
                    ingresar_cedula.setText("INGRESE SU CEDULA");
                    ingresar_cedula.setForeground(Color.GRAY); // Vuelve al color gris para el placeholder
                    ingresar_cedula.setFont(new Font("Inter", Font.BOLD, 24));
                }
            }
        });


        ingresar_correo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ingresar_correo.getText().equals("INGRESE SU CORREO ELECTRONICO")) {
                    ingresar_correo.setText("");
                    ingresar_correo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingresar_correo.getText().isEmpty()) {
                    ingresar_correo.setText("INGRESE SU CORREO ELECTRONICO");
                    ingresar_correo.setForeground(Color.GRAY); // Vuelve al color gris para el placeholder
                    ingresar_correo.setFont(new Font("Inter", Font.BOLD, 24));
                }
            }
        });

        ingresar_contrasena.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ingresar_contrasena.getText().equals("INGRESE SU CONTRASEÑA")) {
                    ingresar_contrasena.setText("");
                    ingresar_contrasena.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingresar_contrasena.getText().isEmpty()) {
                    ingresar_contrasena.setText("INGRESE SU CONTRASEÑA");
                    ingresar_contrasena.setForeground(Color.GRAY); // Vuelve al color gris para el placeholder
                    ingresar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
                }
            }
        });

        confirmar_contrasena.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confirmar_contrasena.getText().equals("CONFIRMAR CONTRASEÑA")) {
                    confirmar_contrasena.setText("");
                    confirmar_contrasena.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (confirmar_contrasena.getText().isEmpty()) {
                    confirmar_contrasena.setText("CONFIRMAR CONTRASEÑA");
                    confirmar_contrasena.setForeground(Color.GRAY); // Vuelve al color gris para el placeholder
                    confirmar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
                }
            }
        });


        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Si el clic no fue en un campo de texto, quita el foco de los campos
                if (e.getSource() == pantalla) {
                    if (ingresar_cedula.isFocusOwner()) {
                        ingresar_cedula.transferFocusBackward();
                    }
                    if (ingresar_correo.isFocusOwner()) {
                        ingresar_correo.transferFocusBackward();
                    }
                    if (ingresar_contrasena.isFocusOwner()) {
                        ingresar_contrasena.transferFocusBackward();
                    }
                    if (confirmar_contrasena.isFocusOwner()) {
                        confirmar_contrasena.transferFocusBackward();
                    }
                    pantalla.requestFocusInWindow();
                }
            }
        });


        pantalla.setFocusable(true);


        cuadro_imagen.add(ingresar_cedula);
        cuadro_imagen.add(ingresar_contrasena);
        cuadro_imagen.add(ingresar_correo);
        cuadro_imagen.add(confirmar_contrasena);


        pantalla.revalidate();
        pantalla.repaint();


        ingresar_cedula.setPreferredSize(new Dimension(480, 66));
        ingresar_cedula.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        label_boton_registrarse.setBounds(250, 520, 163, 76);


        cuadro_imagen.add(boton_registrarse);
        cuadro_imagen.add(label_boton_registrarse);
        // cuadro_imagen.add(ingresar_cedula); // Esto podría ser redundante si ya se añadió arriba

        boton_registrarse.addActionListener(e -> {
            // Validaciones antes de mostrar el mensaje de registro
            String cedula = ingresar_cedula.getText();
            String correo = ingresar_correo.getText();
            String contrasena = ingresar_contrasena.getText();
            String confirmarContrasena = confirmar_contrasena.getText();

            if (cedula.equals("INGRESE SU CEDULA") || cedula.isEmpty() ||
                correo.equals("INGRESE SU CORREO ELECTRONICO") || correo.isEmpty() ||
                contrasena.equals("INGRESE SU CONTRASEÑA") || contrasena.isEmpty() ||
                confirmarContrasena.equals("CONFIRMAR CONTRASEÑA") || confirmarContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Por favor, complete todos los campos para registrarse.", "Error de Registro", JOptionPane.WARNING_MESSAGE);
            } else if (!contrasena.equals(confirmarContrasena)) {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Las contraseñas no coinciden. Por favor, inténtelo de nuevo.", "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Registro Exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Aquí podrías agregar lógica para guardar los datos en una base de datos
                // O volver a la pantalla de inicio de sesión
                frame_inicio_de_sesion.dispose();
                inicio_de_sesion login = new inicio_de_sesion();
                login.mostrar();
            }
        });

        boton_registrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton_registrarse.setIcon(new ImageIcon(imagen_boton_registrarse.getImage().getScaledInstance(168, 81, Image.SCALE_SMOOTH)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_boton_registrarse.setIcon(new ImageIcon(imagen_boton_registrarse.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
            }
        });
    }

    public void mostrar() {
        frame_inicio_de_sesion.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Registro().mostrar());
    }
}