import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class gestion_insumos {

    private JFrame frame_gestion_insumos;
    private JComboBox<String> insumo_nutrientes;
    private JTextField cantidad;
    private JTextField nombre;
    private JTextField precio;
    private int lastInsumoIndex = 0;

    public gestion_insumos() {
        initialize();
    }

    private void initialize() {
        frame_gestion_insumos = new JFrame();
        frame_gestion_insumos.setTitle("Gestion de Insumos");
        frame_gestion_insumos.setSize(700, 866);
        frame_gestion_insumos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_gestion_insumos.setLocationRelativeTo(null);
        frame_gestion_insumos.setResizable(false);

        JPanel pantalla = new JPanel(null);
        frame_gestion_insumos.add(pantalla);

        ImageIcon fondo_insumos = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel cuadro_imagen = new JLabel(fondo_insumos);
        cuadro_imagen.setBounds(0, 0, 700, 866);

        String insumos[] = {"Seleccione un insumo", "Proteínas", "Lípidos", "Carbohidratos", "Productos de limpieza"};
        insumo_nutrientes = new JComboBox<>(insumos);
        insumo_nutrientes.setBounds(128, 220, 450, 70);
        insumo_nutrientes.setFont(new Font("Arial", Font.BOLD, 22));
        insumo_nutrientes.setForeground(Color.GRAY);
        insumo_nutrientes.setBackground(Color.WHITE);

        insumo_nutrientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == -1) {
                    if (insumo_nutrientes.getSelectedIndex() == 0) {
                        setForeground(Color.GRAY);
                    } else {
                        setForeground(Color.BLACK);
                    }
                } else {
                    if (index == 0) {
                        setForeground(Color.GRAY);
                    } else {
                        setForeground(Color.BLACK);
                    }
                }
                return this;
            }
        });

        insumo_nutrientes.addActionListener(e -> {
            if (insumo_nutrientes.getSelectedIndex() == 0) {
                insumo_nutrientes.setSelectedIndex(lastInsumoIndex);
            } else {
                lastInsumoIndex = insumo_nutrientes.getSelectedIndex();
            }
            insumo_nutrientes.repaint();
        });

        cantidad = createPlaceholderTextField("Cantidad");
        cantidad.setBounds(110, 300, 480, 66);

        // RESTAURADO: DocumentFilter de cantidad para números decimales
        ((AbstractDocument) cantidad.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + string + currentText.substring(offset);

                if (isValidDecimalNumber(newText)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                if (isValidDecimalNumber(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidDecimalNumber(String text) {
                if (text.isEmpty()) {
                    return true;
                }
                return text.matches("^\\d*\\.?\\d*$") && text.chars().filter(ch -> ch == '.').count() <= 1;
            }
        });

        nombre = createPlaceholderTextField("Nombre");
        nombre.setBounds(110, 370, 480, 66);

        // DocumentFilter de nombre (ya estaba bien)
        ((AbstractDocument) nombre.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (string.matches("[a-zA-Z\\sáéíóúÁÉÍÓÚñÑ]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                if (text.matches("[a-zA-Z\\sáéíóúÁÉÍÓÚñÑ]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        precio = createPlaceholderTextField("Precio");
        precio.setBounds(110, 440, 480, 66);

        // RESTAURADO: DocumentFilter de precio para números decimales
        ((AbstractDocument) precio.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + string + currentText.substring(offset);

                if (isValidDecimalNumber(newText)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                if (isValidDecimalNumber(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidDecimalNumber(String text) {
                if (text.isEmpty()) {
                    return true;
                }
                return text.matches("^\\d*\\.?\\d*$") && text.chars().filter(ch -> ch == '.').count() <= 1;
            }
        });

        JButton registrar_insumo = new JButton();
        int registrarWidth = 170;
        int registrarHeight = 38;
        registrar_insumo.setBounds(250, 530, registrarWidth, registrarHeight);
        registrar_insumo.setOpaque(false);
        registrar_insumo.setContentAreaFilled(false);
        registrar_insumo.setFocusPainted(false);
        registrar_insumo.setBorderPainted(false);
        ImageIcon originalImagenRegistrar = new ImageIcon("../../Imagenes/Registrar_insumo.png");
        Image scaledImagenRegistrar = originalImagenRegistrar.getImage().getScaledInstance(registrarWidth, registrarHeight, Image.SCALE_SMOOTH);
        registrar_insumo.setIcon(new ImageIcon(scaledImagenRegistrar));

        JButton boton_cancelar = createCancelButton();

        registrar_insumo.addActionListener(e -> {
            String selectedInsumo = (String) insumo_nutrientes.getSelectedItem();
            String cantidadInsumo = cantidad.getText();
            String nombreInsumo = nombre.getText();
            String precioInsumo = precio.getText();

            // Validación: Si el campo contiene el placeholder O está vacío
            if (insumo_nutrientes.getSelectedIndex() == 0 ||
                cantidadInsumo.equals("Cantidad") || cantidadInsumo.isEmpty() ||
                nombreInsumo.equals("Nombre") || nombreInsumo.isEmpty() ||
                precioInsumo.equals("Precio") || precioInsumo.isEmpty()) {
                JOptionPane.showMessageDialog(frame_gestion_insumos, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String mensaje = String.format("Insumo Registrado:\nTipo: %s\nNombre: %s\nCantidad: %s\nPrecio: %s", selectedInsumo, nombreInsumo, cantidadInsumo, precioInsumo);
                JOptionPane.showMessageDialog(frame_gestion_insumos, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
        });
        
        // MouseListener en el JPanel principal para quitar el foco
        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cantidad.isFocusOwner() || nombre.isFocusOwner() || precio.isFocusOwner()) {
                    pantalla.requestFocusInWindow(); // Quita el foco de los JTextFields
                }
            }
        });
        pantalla.setFocusable(true); // Necesario para que el JPanel pueda recibir foco


        pantalla.add(insumo_nutrientes);
        pantalla.add(cantidad);
        pantalla.add(nombre);
        pantalla.add(precio);
        pantalla.add(registrar_insumo);
        pantalla.add(boton_cancelar);
        pantalla.add(cuadro_imagen); // cuadro_imagen debe añadirse después de otros componentes si los contiene

        frame_gestion_insumos.setVisible(true);
    }

    private void resetForm() {
        insumo_nutrientes.setSelectedIndex(0);
        lastInsumoIndex = 0;
        insumo_nutrientes.setForeground(Color.GRAY);
        
        // Restablecer los text fields a su estado de placeholder inicial
        resetPlaceholderTextFieldVisual(cantidad, "Cantidad");
        resetPlaceholderTextFieldVisual(nombre, "Nombre");
        resetPlaceholderTextFieldVisual(precio, "Precio");
    }

    // Método auxiliar para restablecer visualmente el placeholder
    private void resetPlaceholderTextFieldVisual(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
    }

    // Este método contiene la lógica del FocusListener para el placeholder
    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Inter", Font.BOLD, 24));
        textField.setForeground(Color.GRAY); // Color inicial del placeholder

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Si el texto actual es el placeholder Y el color es gris, bórralo y cambia a negro
                if (textField.getText().equals(placeholder) && textField.getForeground().equals(Color.GRAY)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Si el campo está vacío al perder el foco, restaura el placeholder y el color gris
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }

    private JButton createCancelButton() {
        JButton boton_cancelar = new JButton();
        int cancelWidth = 93;
        int cancelHeight = 34;

        boton_cancelar.setBounds(580, 5, cancelWidth, cancelHeight);
        boton_cancelar.setOpaque(false);
        boton_cancelar.setContentAreaFilled(false);
        boton_cancelar.setFocusPainted(false);
        boton_cancelar.setBorderPainted(false);

        ImageIcon originalImagenCancelar = new ImageIcon("../../Imagenes/cancelar.png");
        Image scaledImagenCancelar = originalImagenCancelar.getImage().getScaledInstance(cancelWidth, cancelHeight, Image.SCALE_SMOOTH);
        boton_cancelar.setIcon(new ImageIcon(scaledImagenCancelar));

        boton_cancelar.addActionListener(e -> frame_gestion_insumos.dispose());
        return boton_cancelar;
    }

    public void mostrar() {
        frame_gestion_insumos.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gestion_insumos().mostrar());
    }
}