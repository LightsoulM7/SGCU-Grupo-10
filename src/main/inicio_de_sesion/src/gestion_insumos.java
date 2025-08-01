import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class gestion_insumos {

    private JFrame frame_gestion_insumos;
    private JComboBox<String> insumo_nutrientes;
    private JTextField cantidad;
    private JTextField nombre;
    private JTextField precio;
    private int lastInsumoIndex = 0; // Para evitar que se seleccione el placeholder

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

        // --- COMBOBOX DE INSUMOS ---
        String insumos[] = {"Seleccione un insumo", "Proteínas", "Lípidos", "Carbohidratos", "Productos de limpieza"};
        insumo_nutrientes = new JComboBox<>(insumos);
        insumo_nutrientes.setBounds(128, 220, 450, 70);
        insumo_nutrientes.setFont(new Font("Arial", Font.BOLD, 22));
        insumo_nutrientes.setForeground(Color.GRAY); // Color inicial para el placeholder
        insumo_nutrientes.setBackground(Color.WHITE);

        // Renderer personalizado para mostrar el placeholder en gris
        insumo_nutrientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == -1) { // Cuando es el item seleccionado
                    if (insumo_nutrientes.getSelectedIndex() == 0) {
                        setForeground(Color.GRAY);
                    } else {
                        setForeground(Color.BLACK);
                    }
                } else { // Para la lista desplegable
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
            // Forzar repintado para actualizar el color del item seleccionado
            insumo_nutrientes.repaint();
        });

        // --- CAMPOS DE TEXTO ---
        cantidad = createPlaceholderTextField("Cantidad");
        cantidad.setBounds(110, 300, 480, 66);

        nombre = createPlaceholderTextField("Nombre");
        nombre.setBounds(110, 370, 480, 66);

        precio = createPlaceholderTextField("Precio");
        precio.setBounds(110, 440, 480, 66);

        // --- BOTONES ---
        JButton registrar_insumo=new JButton();


registrar_insumo.setBounds(50,550,264,76);

registrar_insumo.setOpaque(false);
registrar_insumo.setContentAreaFilled(false);
registrar_insumo.setFocusPainted(false);
        ImageIcon imagen_boton_registrar = new ImageIcon("../../Imagenes/Registrar_insumo.png");
        JLabel label_boton_registrar = new JLabel(imagen_boton_registrar);
        label_boton_registrar.setBounds(50, 550, 264, 76);

        JButton boton_cancelar = createCancelButton();

        // --- ACCIÓN DE REGISTRAR ---
        registrar_insumo.addActionListener(e -> {
            String selectedInsumo = (String) insumo_nutrientes.getSelectedItem();
            String cantidadInsumo = cantidad.getText();
            String nombreInsumo = nombre.getText();
            String precioInsumo = precio.getText();

            if (insumo_nutrientes.getSelectedIndex() == 0 || cantidadInsumo.equals("Cantidad") || nombreInsumo.equals("Nombre") || precioInsumo.equals("Precio")) {
                JOptionPane.showMessageDialog(frame_gestion_insumos, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String mensaje = String.format("Insumo Registrado:\nTipo: %s\nNombre: %s\nCantidad: %s\nPrecio: %s", selectedInsumo, nombreInsumo, cantidadInsumo, precioInsumo);
                JOptionPane.showMessageDialog(frame_gestion_insumos, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
        });

        // --- AGREGAR COMPONENTES AL PANEL ---
        pantalla.add(insumo_nutrientes);
        pantalla.add(cantidad);
        pantalla.add(nombre);
        pantalla.add(precio);
        pantalla.add(registrar_insumo);
        pantalla.add(label_boton_registrar);
        pantalla.add(boton_cancelar);
        pantalla.add(cuadro_imagen); // El fondo siempre al final

        frame_gestion_insumos.setVisible(true);
    }

    private void resetForm() {
        insumo_nutrientes.setSelectedIndex(0);
        lastInsumoIndex = 0;
        insumo_nutrientes.setForeground(Color.GRAY);
        resetPlaceholderTextField(cantidad, "Cantidad");
        resetPlaceholderTextField(nombre, "Nombre");
        resetPlaceholderTextField(precio, "Precio");
    }

    private void resetPlaceholderTextField(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
    }

    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Inter", Font.BOLD, 24));
        textField.setForeground(Color.GRAY);

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
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
        return textField;
    }

    private JButton createCancelButton() {
        JButton boton_cancelar = new JButton();
        boton_cancelar.setBounds(580, 5, 93, 34);
        boton_cancelar.setOpaque(false);
        boton_cancelar.setContentAreaFilled(false);
        boton_cancelar.setFocusPainted(false);
        boton_cancelar.setBorderPainted(false);

        ImageIcon imagen_cancelar = new ImageIcon("../../Imagenes/cancelar.png");
        JLabel label_cancelar = new JLabel(imagen_cancelar);
        label_cancelar.setBounds(0, 0, 93, 34);
        boton_cancelar.add(label_cancelar);

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
