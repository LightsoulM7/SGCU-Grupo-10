import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class gastos_fijos {

    private JFrame frame_gastos_fijos;
    private JComboBox<String> tipo_gasto_combo;
    private JComboBox<String> servicios_combo;
    private JComboBox<String> empleados_combo;
    private JTextField monto_periodo_field;
    private JTextField sueldo_mensual_field;
    private JTextField id_empleado_field;

    public gastos_fijos() {
        initialize();
    }

    private void initialize() {
        frame_gastos_fijos = new JFrame();
        frame_gastos_fijos.setTitle("Gastos Fijos");
        frame_gastos_fijos.setSize(700, 866);
        frame_gastos_fijos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_gastos_fijos.setLocationRelativeTo(null);
        frame_gastos_fijos.setResizable(false);

        JPanel pantalla = new JPanel(null);
        frame_gastos_fijos.add(pantalla);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel cuadro_imagen = new JLabel(fondo);
        cuadro_imagen.setBounds(0, 0, 700, 866);

        // --- COMBOBOX PRINCIPAL ---
        String tipo_gasto[] = {"Seleccione tipo de gasto", "Servicio", "Sueldo de Empleado"};
        tipo_gasto_combo = new JComboBox<>(tipo_gasto);
        tipo_gasto_combo.setBounds(128, 220, 450, 50);
        tipo_gasto_combo.setFont(new Font("Arial", Font.BOLD, 20));

        // --- COMPONENTES PARA SERVICIOS ---
        String tipo_servicio[] = {"Tipo de Servicio", "Agua", "Gas", "Luz"};
        servicios_combo = new JComboBox<>(tipo_servicio);
        servicios_combo.setBounds(128, 280, 450, 50);
        servicios_combo.setFont(new Font("Arial", Font.BOLD, 20));

        monto_periodo_field = createPlaceholderTextField("Monto del periodo");
        monto_periodo_field.setBounds(128, 340, 450, 50);

        // --- COMPONENTES PARA EMPLEADOS ---
        String tipo_de_empleado[] = {"Tipo de Empleado", "Personal de Cocina", "Administrador", "Personal de Limpieza"};
        empleados_combo = new JComboBox<>(tipo_de_empleado);
        empleados_combo.setBounds(128, 280, 450, 50);
        empleados_combo.setFont(new Font("Arial", Font.BOLD, 20));

        sueldo_mensual_field = createPlaceholderTextField("Sueldo Mensual");
        sueldo_mensual_field.setBounds(128, 340, 450, 50);

        id_empleado_field = createPlaceholderTextField("ID Empleado");
        id_empleado_field.setBounds(128, 400, 450, 50);

        // --- BOTÓN GUARDAR ---
        JButton guardar_gastos = new JButton();
        guardar_gastos.setBounds(250, 500, 184, 76);
        guardar_gastos.setOpaque(false);
        guardar_gastos.setContentAreaFilled(false);
        guardar_gastos.setFocusPainted(false);
        guardar_gastos.setBorderPainted(false);

        ImageIcon imagen_boton_guardar = new ImageIcon("../../Imagenes/Guardar_gastos.png");
        JLabel label_boton_guardar = new JLabel(imagen_boton_guardar);
        label_boton_guardar.setBounds(0, 0, 184, 76);
        guardar_gastos.add(label_boton_guardar);

        // --- LÓGICA DE VISIBILIDAD ---
        updateFieldsVisibility(false, false); // Ocultar todo al inicio

        tipo_gasto_combo.addActionListener(e -> {
            int selection = tipo_gasto_combo.getSelectedIndex();
            if (selection == 1) { // Servicio
                updateFieldsVisibility(true, false);
            } else if (selection == 2) { // Empleado
                updateFieldsVisibility(false, true);
            } else {
                updateFieldsVisibility(false, false);
            }
        });

        // --- ACCIÓN DE GUARDAR ---
        guardar_gastos.addActionListener(e -> {
            int selection = tipo_gasto_combo.getSelectedIndex();
            if (selection == 1) { // Guardar Servicio
                if (servicios_combo.getSelectedIndex() == 0 || monto_periodo_field.getText().equals("Monto del periodo")) {
                    JOptionPane.showMessageDialog(frame_gastos_fijos, "Por favor, complete todos los campos de servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String mensaje = String.format("Gasto de Servicio Registrado:\nTipo: %s\nMonto: %s", servicios_combo.getSelectedItem(), monto_periodo_field.getText());
                    JOptionPane.showMessageDialog(frame_gastos_fijos, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    resetForm();
                }
            } else if (selection == 2) { // Guardar Empleado
                if (empleados_combo.getSelectedIndex() == 0 || sueldo_mensual_field.getText().equals("Sueldo Mensual") || id_empleado_field.getText().equals("ID Empleado")) {
                    JOptionPane.showMessageDialog(frame_gastos_fijos, "Por favor, complete todos los campos de empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String mensaje = String.format("Gasto de Empleado Registrado:\nTipo: %s\nSueldo: %s\nID: %s", empleados_combo.getSelectedItem(), sueldo_mensual_field.getText(), id_empleado_field.getText());
                    JOptionPane.showMessageDialog(frame_gastos_fijos, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    resetForm();
                }
            }
            else {
                JOptionPane.showMessageDialog(frame_gastos_fijos, "Por favor, seleccione un tipo de gasto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- AGREGAR COMPONENTES AL PANEL ---
        pantalla.add(tipo_gasto_combo);
        pantalla.add(servicios_combo);
        pantalla.add(monto_periodo_field);
        pantalla.add(empleados_combo);
        pantalla.add(sueldo_mensual_field);
        pantalla.add(id_empleado_field);
        pantalla.add(guardar_gastos);
        pantalla.add(cuadro_imagen);

        frame_gastos_fijos.setVisible(true);
    }

    private void updateFieldsVisibility(boolean servicioVisible, boolean empleadoVisible) {
        servicios_combo.setVisible(servicioVisible);
        monto_periodo_field.setVisible(servicioVisible);
        empleados_combo.setVisible(empleadoVisible);
        sueldo_mensual_field.setVisible(empleadoVisible);
        id_empleado_field.setVisible(empleadoVisible);
    }

    private void resetForm() {
        tipo_gasto_combo.setSelectedIndex(0);
        servicios_combo.setSelectedIndex(0);
        empleados_combo.setSelectedIndex(0);
        resetPlaceholderTextField(monto_periodo_field, "Monto del periodo");
        resetPlaceholderTextField(sueldo_mensual_field, "Sueldo Mensual");
        resetPlaceholderTextField(id_empleado_field, "ID Empleado");
        updateFieldsVisibility(false, false);
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

    public void mostrar() {
        frame_gastos_fijos.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gastos_fijos().mostrar());
    }
}
