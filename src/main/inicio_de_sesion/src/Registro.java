import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Registro {

    private JFrame frame_inicio_de_sesion;
    private Set<String> cedulasComunidad = new HashSet<>();
    private Set<String> cedulasRegistradas = new HashSet<>();

    public Registro() {
        cargarCedulas();
        initialize();
    }

    private void cargarCedulas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("../../db/cedulasComunidad.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    cedulasComunidad.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar las cédulas de la comunidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("../../db/usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    cedulasRegistradas.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            // El archivo puede no existir al principio, no es un error crítico.
        }
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
        ingresar_cedula.setForeground(Color.GRAY);

        ((AbstractDocument) ingresar_cedula.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        JTextField ingresar_correo = new JTextField("INGRESE SU CORREO ELECTRONICO");
        ingresar_correo.setBounds(110, 290, 480, 66);
        ingresar_correo.setHorizontalAlignment(JTextField.CENTER);
        ingresar_correo.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_correo.setForeground(Color.GRAY);

        JPasswordField ingresar_contrasena = new JPasswordField("INGRESE SU CONTRASEÑA");
        ingresar_contrasena.setBounds(110, 360, 480, 66);
        ingresar_contrasena.setHorizontalAlignment(JTextField.CENTER);
        ingresar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_contrasena.setForeground(Color.GRAY);
        ingresar_contrasena.setEchoChar((char) 0);


        JPasswordField confirmar_contrasena = new JPasswordField("CONFIRMAR CONTRASEÑA");
        confirmar_contrasena.setBounds(110, 430, 480, 66);
        confirmar_contrasena.setHorizontalAlignment(JTextField.CENTER);
        confirmar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        confirmar_contrasena.setForeground(Color.GRAY);
        confirmar_contrasena.setEchoChar((char) 0);

        addPlaceholder(ingresar_cedula, "INGRESE SU CEDULA");
        addPlaceholder(ingresar_correo, "INGRESE SU CORREO ELECTRONICO");
        addPasswordPlaceholder(ingresar_contrasena, "INGRESE SU CONTRASEÑA");
        addPasswordPlaceholder(confirmar_contrasena, "CONFIRMAR CONTRASEÑA");

        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == pantalla) {
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

        boton_registrarse.addActionListener(e -> {
            String cedula = ingresar_cedula.getText();
            String correo = ingresar_correo.getText();
            String contrasena = new String(ingresar_contrasena.getPassword());
            String confirmarContrasena = new String(confirmar_contrasena.getPassword());

            if (validarCampos(cedula, correo, contrasena, confirmarContrasena)) {
                if (registrarUsuario(cedula, correo, contrasena)) {
                    JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Registro Exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    frame_inicio_de_sesion.dispose();
                    inicio_de_sesion login = new inicio_de_sesion();
                    login.mostrar();
                }
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

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void addPasswordPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private boolean validarCampos(String cedula, String correo, String contrasena, String confirmarContrasena) {
        if (cedula.equals("INGRESE SU CEDULA") || correo.equals("INGRESE SU CORREO ELECTRONICO") ||
            contrasena.equals("INGRESE SU CONTRASEÑA") || confirmarContrasena.equals("CONFIRMAR CONTRASEÑA")) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Por favor, complete todos los campos.", "Error de Registro", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!cedulasComunidad.contains(cedula)) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "La cédula no está autorizada para registrarse.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (cedulasRegistradas.contains(cedula)) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "La cédula ya se encuentra registrada.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!validarCorreo(correo)) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "El formato del correo electrónico no es válido.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!validarContrasena(contrasena)) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "La contraseña no es segura. Debe tener al menos 8 caracteres, un número y un carácter especial.", "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Las contraseñas no coinciden.", "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    private boolean validarContrasena(String contrasena) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contrasena);
        return matcher.matches();
    }

    private boolean registrarUsuario(String cedula, String correo, String contrasena) {
        try {
            String contrasenaHasheada = hashContrasena(contrasena);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("../../db/usuarios.txt", true))) {
                writer.write(cedula + "," + correo + "," + contrasenaHasheada);
                writer.newLine();
                cedulasRegistradas.add(cedula);
                return true;
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Error al guardar el usuario.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private String hashContrasena(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void mostrar() {
        frame_inicio_de_sesion.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Registro().mostrar());
    }
}
