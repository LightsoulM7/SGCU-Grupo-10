import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class inicio_de_sesion {
    
    private JFrame frame_inicio_de_sesion;
    
    public inicio_de_sesion() {
        initialize();
    }
    
    private void initialize() {
        frame_inicio_de_sesion = new JFrame();
        frame_inicio_de_sesion.setTitle("Inicio de sesión");
        frame_inicio_de_sesion.setSize(700, 866);
        frame_inicio_de_sesion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_inicio_de_sesion.setLocationRelativeTo(null);
        frame_inicio_de_sesion.setResizable(false);
        
        JPanel pantalla = new JPanel(null);
        frame_inicio_de_sesion.add(pantalla);
        
        ImageIcon inicio_sesion=new ImageIcon("../../Imagenes/cuadro_ucv.png");
        
        Image cuadrar_imagen=inicio_sesion.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
        JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));
        cuadro_imagen.setBounds(0,0,700,866);
        pantalla.add(cuadro_imagen);
        
        JButton boton_inicio_sesion=new JButton();
        JButton boton_olvido_clave=new JButton();
        
        boton_inicio_sesion.setBounds(100,500,163,76);
        boton_inicio_sesion.setOpaque(false);
        boton_inicio_sesion.setContentAreaFilled(false);
        boton_inicio_sesion.setFocusPainted(false);
        boton_inicio_sesion.setBorderPainted(false);
        
        boton_olvido_clave.setBounds(430,500,163,76);
        boton_olvido_clave.setOpaque(false);
        boton_olvido_clave.setContentAreaFilled(false);
        boton_olvido_clave.setFocusPainted(false);
        boton_olvido_clave.setBorderPainted(false);
        
        ImageIcon imagen_boton_inicio_sesion=new ImageIcon("../../Imagenes/iniciar_sesion.png");
        ImageIcon imagen_boton_olvido_clave=new ImageIcon("../../Imagenes/olvido_clave.png");
        Image cuadrar_imagen_boton_inicio_sesion=imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);
        Image cuadrar_imagen_boton_olvido_clave=imagen_boton_olvido_clave.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);
        JLabel label_boton=new JLabel(new ImageIcon(cuadrar_imagen_boton_inicio_sesion));
        JLabel label_boton_olvido_clave=new JLabel(new ImageIcon(cuadrar_imagen_boton_olvido_clave));
        label_boton.setBounds(100,500,163,76);
        label_boton_olvido_clave.setBounds(430,500,163,76);
        
        JTextField ingresar_cedula=new JTextField("INGRESE SU CEDULA");
        ingresar_cedula.setBounds(110,300,480,66);
        ingresar_cedula.setHorizontalAlignment(JTextField.CENTER);
        ingresar_cedula.setFont(new Font("Inter",Font.BOLD,24));
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

        JPasswordField ingresar_contrasena=new JPasswordField();
        final String placeholder_contrasena = "INGRESE SU CONTRASEÑA";
        ingresar_contrasena.setBounds(110,370,480,66);
        ingresar_contrasena.setHorizontalAlignment(JTextField.CENTER);
        ingresar_contrasena.setFont(new Font("Inter",Font.BOLD,24));
        ingresar_contrasena.setText(placeholder_contrasena);
        ingresar_contrasena.setEchoChar((char)0);
        ingresar_contrasena.setForeground(Color.GRAY);
        
        JCheckBox mostrar_contrasena = new JCheckBox("Mostrar Contraseña");
        mostrar_contrasena.setBounds(110, 440, 200, 30);
        mostrar_contrasena.setFont(new Font("Inter", Font.BOLD, 14));
        mostrar_contrasena.setForeground(Color.WHITE);
        mostrar_contrasena.setOpaque(false);
        cuadro_imagen.add(mostrar_contrasena);
        
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
                    ingresar_cedula.setForeground(Color.GRAY);
                }
            }
        });
        
        ingresar_contrasena.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(ingresar_contrasena.getPassword()).equals(placeholder_contrasena)) {
                    ingresar_contrasena.setText("");
                    if (!mostrar_contrasena.isSelected()) {
                        ingresar_contrasena.setEchoChar('*');
                    }
                    ingresar_contrasena.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(ingresar_contrasena.getPassword()).isEmpty()) {
                    ingresar_contrasena.setText(placeholder_contrasena);
                    ingresar_contrasena.setEchoChar((char)0);
                    ingresar_contrasena.setForeground(Color.GRAY);
                }
            }
        }); 
        
        mostrar_contrasena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!String.valueOf(ingresar_contrasena.getPassword()).equals(placeholder_contrasena)) {
                    if (mostrar_contrasena.isSelected()) {
                        ingresar_contrasena.setEchoChar((char) 0);
                    } else {
                        ingresar_contrasena.setEchoChar('*');
                    }
                }
            }
        });
        
        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == pantalla && (ingresar_cedula.isFocusOwner() || ingresar_contrasena.isFocusOwner())) {
                    pantalla.requestFocusInWindow(); 
                }
                checkAndRestorePlaceholder(ingresar_cedula, "INGRESE SU CEDULA");
                checkAndRestorePlaceholderPassword(ingresar_contrasena, placeholder_contrasena, mostrar_contrasena.isSelected());
            }
        });
        
        pantalla.setFocusable(true);
        
        cuadro_imagen.add(ingresar_cedula);
        cuadro_imagen.add(ingresar_contrasena);
        cuadro_imagen.add(mostrar_contrasena);
        cuadro_imagen.add(boton_inicio_sesion);
        cuadro_imagen.add(label_boton);
        cuadro_imagen.add(boton_olvido_clave);
        cuadro_imagen.add(label_boton_olvido_clave);
        
        boton_inicio_sesion.addActionListener(e -> {
            String cedula = ingresar_cedula.getText();
            String contrasena = new String(ingresar_contrasena.getPassword());
            
            if (cedula.equals("INGRESE SU CEDULA") || cedula.isEmpty()) {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Por favor, ingrese su cédula.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (contrasena.equals(placeholder_contrasena) || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Por favor, ingrese su contraseña.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (cedula.equals("Admin") || cedula.equals("0000")) {
                frame_inicio_de_sesion.dispose();
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.mostrar();
            } else {
                frame_inicio_de_sesion.dispose();
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.mostrar();
            }
        });
        
        boton_olvido_clave.addActionListener(e -> {
            frame_inicio_de_sesion.dispose();
            recuperar_contrasena recuperarContrasena = new recuperar_contrasena();
            recuperarContrasena.mostrar();
        });
        
        boton_inicio_sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(168, 81, Image.SCALE_SMOOTH)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
            }
        });
        
        frame_inicio_de_sesion.setVisible(true);
    }
    
    private void checkAndRestorePlaceholder(JTextField textField, String placeholderText) {
        if (textField.getText().isEmpty() && !textField.isFocusOwner()) {
            textField.setText(placeholderText);
            textField.setForeground(Color.GRAY);
        }
    }

    private void checkAndRestorePlaceholderPassword(JPasswordField passwordField, String placeholderText, boolean showPassword) {
        if (String.valueOf(passwordField.getPassword()).isEmpty() && !passwordField.isFocusOwner()) {
            passwordField.setText(placeholderText);
            passwordField.setEchoChar((char)0);
            passwordField.setForeground(Color.GRAY);
        }
    }
    
    public void mostrar() {
        frame_inicio_de_sesion.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new inicio_de_sesion().mostrar());
    }
}