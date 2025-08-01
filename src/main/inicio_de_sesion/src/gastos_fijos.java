import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class gastos_fijos {

    private JFrame frame_gastos_fijos;
    private JComboBox<String> tipo_gasto_combo;
    private JComboBox<String> servicios_combo;
    private JComboBox<String> empleados_combo;
    private JTextField monto_periodo_field;
    private JTextField sueldo_mensual_field;
    private JTextField id_empleado_field;
    private JButton guardar_gastos;
    private JLabel total_gastos_label;
    private final String GASTOS_FILE = "../../db/gastos_fijos.txt";

    public gastos_fijos() {
        initialize();
        calculateTotalGastos(); // Calculate total on startup
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

        total_gastos_label = new JLabel("Total Gastos: 0.00");
        total_gastos_label.setBounds(128, 180, 450, 30);
        total_gastos_label.setFont(new Font("Arial", Font.BOLD, 20));
        total_gastos_label.setForeground(Color.BLACK);
        pantalla.add(total_gastos_label);

        String tipo_gasto[] = {"Seleccione tipo de gasto", "Servicio", "Sueldo de Empleado"};
        tipo_gasto_combo = new JComboBox<>(tipo_gasto);
        tipo_gasto_combo.setBounds(128, 220, 450, 50);
        tipo_gasto_combo.setFont(new Font("Arial", Font.BOLD, 20));

        String tipo_servicio[] = {"Tipo de Servicio", "Agua", "Gas", "Luz"};
        servicios_combo = new JComboBox<>(tipo_servicio);
        servicios_combo.setBounds(128, 280, 450, 50);
        servicios_combo.setFont(new Font("Arial", Font.BOLD, 20));

        monto_periodo_field = createPlaceholderTextField("Monto del periodo");
        monto_periodo_field.setBounds(128, 340, 450, 50);

        ((AbstractDocument) monto_periodo_field.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        String tipo_de_empleado[] = {"Tipo de Empleado", "Personal de Cocina", "Administrador", "Personal de Limpieza"};
        empleados_combo = new JComboBox<>(tipo_de_empleado);
        empleados_combo.setBounds(128, 280, 450, 50);
        empleados_combo.setFont(new Font("Arial", Font.BOLD, 20));

        sueldo_mensual_field = createPlaceholderTextField("Sueldo Mensual");
        sueldo_mensual_field.setBounds(128, 340, 450, 50);
        ((AbstractDocument) sueldo_mensual_field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + string + currentText.substring(offset);

                if (newText.matches("\\d*\\.?\\d*")) {
                    if (string.equals(".") && currentText.contains(".")) {
                        return;
                    }
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

                if (newText.matches("\\d*\\.?\\d*")) {
                    if (text.equals(".") && currentText.contains(".") && length == 0) {
                        return;
                    }
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        id_empleado_field = createPlaceholderTextField("ID Empleado");
        id_empleado_field.setBounds(128, 400, 450, 50);
        ((AbstractDocument) id_empleado_field.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        guardar_gastos = new JButton("Guardar Gastos");
        guardar_gastos.setBounds(250, 500, 200, 50);
        guardar_gastos.setFont(new Font("Arial", Font.BOLD, 20));
        guardar_gastos.setBackground(new Color(70, 130, 180));
        guardar_gastos.setForeground(Color.WHITE);
        guardar_gastos.setFocusPainted(false);
        guardar_gastos.setVisible(false);

        updateFieldsVisibility(false, false);

        tipo_gasto_combo.addActionListener(e -> {
            int selection = tipo_gasto_combo.getSelectedIndex();
            if (selection == 1) { // Servicio
                updateFieldsVisibility(true, false);
                guardar_gastos.setBounds(250, 400, 200, 50);
            } else if (selection == 2) { // Empleado
                updateFieldsVisibility(false, true);
                guardar_gastos.setBounds(250, 500, 200, 50);
            } else {
                updateFieldsVisibility(false, false);
            }
        });

        guardar_gastos.addActionListener(e -> {
            int selection = tipo_gasto_combo.getSelectedIndex();
            if (selection == 1) { // Guardar Servicio
                if (servicios_combo.getSelectedIndex() == 0 || monto_periodo_field.getText().equals("Monto del periodo") || monto_periodo_field.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame_gastos_fijos, "Por favor, complete todos los campos de servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double monto = Double.parseDouble(monto_periodo_field.getText());
                        String tipo = (String) servicios_combo.getSelectedItem();
                        saveGasto(tipo, monto);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame_gastos_fijos, "Monto del periodo debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (selection == 2) { // Guardar Empleado
                if (empleados_combo.getSelectedIndex() == 0 || sueldo_mensual_field.getText().equals("Sueldo Mensual") || sueldo_mensual_field.getText().isEmpty() || id_empleado_field.getText().equals("ID Empleado") || id_empleado_field.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame_gastos_fijos, "Por favor, complete todos los campos de empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double sueldo = Double.parseDouble(sueldo_mensual_field.getText());
                        int idEmpleado = Integer.parseInt(id_empleado_field.getText());
                        String tipo = (String) empleados_combo.getSelectedItem() + " (ID: " + idEmpleado + ")";
                        saveGasto(tipo, sueldo);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame_gastos_fijos, "Sueldo Mensual y/o ID Empleado deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(frame_gastos_fijos, "Por favor, seleccione un tipo de gasto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pantalla.add(tipo_gasto_combo);
        pantalla.add(servicios_combo);
        pantalla.add(monto_periodo_field);
        pantalla.add(empleados_combo);
        pantalla.add(sueldo_mensual_field);
        pantalla.add(id_empleado_field);
        pantalla.add(guardar_gastos);
        pantalla.add(cuadro_imagen);

        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(10, 10, 93, 34);
        btnBack.addActionListener(e -> {
            frame_gastos_fijos.dispose();
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.mostrar();
        });
        cuadro_imagen.add(btnBack);

        frame_gastos_fijos.setVisible(true);
    }

    private void updateFieldsVisibility(boolean servicioVisible, boolean empleadoVisible) {
        servicios_combo.setVisible(servicioVisible);
        monto_periodo_field.setVisible(servicioVisible);
        empleados_combo.setVisible(empleadoVisible);
        sueldo_mensual_field.setVisible(empleadoVisible);
        id_empleado_field.setVisible(empleadoVisible);
        guardar_gastos.setVisible(servicioVisible || empleadoVisible);
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
        textField.setFont(new Font("Inter", Font.BOLD, 24)); // Asegura que el placeholder esté en la fuente original
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
                    textField.setFont(textField.getFont().deriveFont(Font.BOLD)); // Texto ingresado en negrita
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                    textField.setFont(new Font("Inter", Font.BOLD, 24)); // Vuelve a la fuente original para el placeholder
                }
            }
        });
        return textField;
    }

    public void mostrar() {
        frame_gastos_fijos.setVisible(true);
    }

    private void saveGasto(String tipo, double monto) {
        String gastoEntry = String.format("%s:%.2f", tipo, monto);
        if (isGastoDuplicado(tipo)) {
            JOptionPane.showMessageDialog(frame_gastos_fijos, "Este gasto ya ha sido registrado.", "Gasto Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Guardando gasto: " + gastoEntry);
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(GASTOS_FILE, true))) { // true para añadir al final
            fw.write(gastoEntry);
            fw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_gastos_fijos, "Error al guardar el gasto: " + e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame_gastos_fijos, "Gasto Registrado Exitosamente.\n" + gastoEntry, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        resetForm();
        calculateTotalGastos(); // Recalculate and rewrite total
    }

    private boolean isGastoDuplicado(String tipo) {
        try (BufferedReader br = new BufferedReader(new FileReader(GASTOS_FILE))) {
            br.readLine(); // Skip the first line (total)
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(tipo + ":")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de gastos para verificar duplicados: " + e.getMessage());
        }
        return false;
    }

    private void calculateTotalGastos() {
        double total = 0.0;
        StringBuilder expensesContent = new StringBuilder();
        boolean firstLineSkipped = false; // Flag to skip the first line

        try (BufferedReader br = new BufferedReader(new FileReader(GASTOS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true; // Skip the first line (previous total)
                    continue;
                }
                System.out.println("Línea de gasto leída: " + line);
                try {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        double montoGasto = Double.parseDouble(parts[1]);
                        total += montoGasto;
                        System.out.println("Gasto procesado: " + parts[0] + ", Monto: " + montoGasto + ", Total acumulado: " + total);
                    }
                    expensesContent.append(line).append("\n");
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato en la línea de gasto: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de gastos para calcular el total: " + e.getMessage());
        }

        System.out.println("Total final antes de reescribir: " + total);
        // Ahora, reescribir el archivo con el nuevo total en la primera línea
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(GASTOS_FILE, false))) { // false para sobrescribir
            fw.write(String.format("%.2f\n", total));
            fw.write(expensesContent.toString());
        } catch (IOException e) {
            System.err.println("Error al escribir el total en el archivo de gastos: " + e.getMessage());
        }

        total_gastos_label.setText(String.format("Total Gastos: %.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gastos_fijos().mostrar());
    }
}