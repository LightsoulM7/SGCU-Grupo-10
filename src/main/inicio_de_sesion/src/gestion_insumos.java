import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class gestion_insumos {

    private JFrame frame_gestion_insumos;
    private JComboBox<String> insumo_nutrientes;
    private JTextField cantidad;
    private JTextField nombre;
    private JTextField precio;
    private int lastInsumoIndex = 0;
    private static final String COSTOS_VARIABLES_FILE = "../../db/CostosVariables.txt";

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

        nombre = createPlaceholderTextField("Nombre");
        nombre.setBounds(110, 370, 480, 66);

        ((AbstractDocument) nombre.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (string.matches("[a-zA-Z\sáéíóúÉÓÚñÑ]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                if (text.matches("[a-zA-Z\sáéíóúÉÓÚñÑ]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        precio = createPlaceholderTextField("Precio");
        precio.setBounds(110, 440, 480, 66);

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

        JButton btnAtras = new JButton("Atrás");
        btnAtras.setBounds(50, 50, 100, 40);
        btnAtras.addActionListener(e -> {
            frame_gestion_insumos.dispose();
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.mostrar();
        });
        cuadro_imagen.add(btnAtras);

        registrar_insumo.addActionListener(e -> {
            String selectedInsumo = (String) insumo_nutrientes.getSelectedItem();
            String cantidadInsumo = cantidad.getText();
            String nombreInsumo = nombre.getText();
            String precioInsumoStr = precio.getText();

            if (insumo_nutrientes.getSelectedIndex() == 0 ||                cantidadInsumo.equals("Cantidad") || cantidadInsumo.isEmpty() ||                nombreInsumo.equals("Nombre") || nombreInsumo.isEmpty() ||                precioInsumoStr.equals("Precio") || precioInsumoStr.isEmpty()) {                JOptionPane.showMessageDialog(frame_gestion_insumos, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);            } else if (!cantidadInsumo.matches("^\\d*\\.?\\d*$") || !precioInsumoStr.matches("^\\d*\\.?\\d*$")) {                JOptionPane.showMessageDialog(frame_gestion_insumos, "Cantidad y Precio deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);            } else {                try {                    double precioInsumo = Double.parseDouble(precioInsumoStr);                    saveInsumoToCostosVariables(selectedInsumo, nombreInsumo, cantidadInsumo, precioInsumo);                    String mensaje = String.format("Insumo Registrado:\nTipo: %s\nNombre: %s\nCantidad: %s\nPrecio: %.2f", selectedInsumo, nombreInsumo, cantidadInsumo, precioInsumo);                    JOptionPane.showMessageDialog(frame_gestion_insumos, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);                    resetForm();                    frame_gestion_insumos.revalidate();                    frame_gestion_insumos.repaint();                } catch (NumberFormatException ex) {                    JOptionPane.showMessageDialog(frame_gestion_insumos, "Error al convertir precio o cantidad a número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);                } catch (IOException ex) {                    JOptionPane.showMessageDialog(frame_gestion_insumos, "Error al guardar el insumo: " + ex.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);                }            }
        });
        
pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cantidad.isFocusOwner() || nombre.isFocusOwner() || precio.isFocusOwner()) {
                    pantalla.requestFocusInWindow();
                }
            }
        });
        pantalla.setFocusable(true);

        pantalla.add(insumo_nutrientes);
        pantalla.add(cantidad);
        pantalla.add(nombre);
        pantalla.add(precio);
        pantalla.add(registrar_insumo);
        pantalla.add(cuadro_imagen);

        frame_gestion_insumos.setVisible(true);
    }

    private void saveInsumoToCostosVariables(String tipo, String nombre, String cantidad, double precio) throws IOException {
        File file = new File(COSTOS_VARIABLES_FILE);
        double currentTotal = 0.0;
        StringBuilder fileContent = new StringBuilder();

        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String firstLine = reader.readLine();
                if (firstLine != null && !firstLine.trim().isEmpty()) {
                    try {
                        currentTotal = Double.parseDouble(firstLine.trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: La primera línea de CostosVariables.txt no es un número válido. Se reiniciará el total.");
                        currentTotal = 0.0;
                    }
                }
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }
        }

        currentTotal += precio;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.format("%.2f", currentTotal));
            writer.newLine();
            writer.write(fileContent.toString());
            writer.write(String.format("Tipo: %s, Nombre: %s, Cantidad: %s, Precio: %.2f", tipo, nombre, cantidad, precio));
            writer.newLine();
        }
    }

    private void resetForm() {
        insumo_nutrientes.setSelectedIndex(0);
        lastInsumoIndex = 0;
        insumo_nutrientes.setForeground(Color.GRAY);
        
        resetPlaceholderTextFieldVisual(cantidad, "Cantidad");
        resetPlaceholderTextFieldVisual(nombre, "Nombre");
        resetPlaceholderTextFieldVisual(precio, "Precio");
    }

    private void resetPlaceholderTextFieldVisual(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
    }

    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Inter", Font.BOLD, 24));
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder) && textField.getForeground().equals(Color.GRAY)) {
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

        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updatePlaceholder(textField, placeholder);
            }
            public void removeUpdate(DocumentEvent e) {
                updatePlaceholder(textField, placeholder);
            }
            public void changedUpdate(DocumentEvent e) {
                updatePlaceholder(textField, placeholder);
            }

            private void updatePlaceholder(JTextField tf, String ph) {
                if (tf.getText().isEmpty()) {
                    tf.setForeground(Color.GRAY);
                    // Only set text if it's not already the placeholder to avoid infinite loop
                    if (!tf.getText().equals(ph)) {
                        tf.setText(ph);
                    }
                } else if (tf.getForeground().equals(Color.GRAY) && !tf.getText().equals(ph)) {
                    tf.setForeground(Color.BLACK);
                }
            }
        });

        return textField;
    }

    public void mostrar() {
        frame_gestion_insumos.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gestion_insumos().mostrar());
    }
}