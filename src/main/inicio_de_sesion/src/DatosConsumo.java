import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DatosConsumo {

    private JFrame frame_datos_consumo;
    private JTextField merma_field;
    private JTextField consumo_estimado_field;

    public DatosConsumo() {
        initialize();
    }

    private void initialize() {
        frame_datos_consumo = new JFrame();
        frame_datos_consumo.setTitle("Datos de Consumo");
        frame_datos_consumo.setSize(700, 866);
        frame_datos_consumo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame_datos_consumo.setLocationRelativeTo(null);
        frame_datos_consumo.setResizable(false);

        JPanel pantalla = new JPanel(null);
        frame_datos_consumo.add(pantalla);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel cuadro_imagen = new JLabel(fondo);
        cuadro_imagen.setBounds(0, 0, 700, 866);

        // --- TITULO ---
        JLabel lblTitulo = new JLabel("Datos de Consumo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(150, 150, 400, 50);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        pantalla.add(lblTitulo);

        // --- CAMPOS DE TEXTO ---
        JLabel lblMerma = new JLabel("Merma");
        lblMerma.setFont(new Font("Arial", Font.BOLD, 20));
        lblMerma.setForeground(Color.WHITE);
        lblMerma.setBounds(128, 250, 450, 20);
        pantalla.add(lblMerma);

        merma_field = createPlaceholderTextField("Cantidad de Merma por periodo");
        merma_field.setBounds(128, 280, 450, 50);

        JLabel lblConsumo = new JLabel("Consumo");
        lblConsumo.setFont(new Font("Arial", Font.BOLD, 20));
        lblConsumo.setForeground(Color.WHITE);
        lblConsumo.setBounds(128, 330, 450, 20);
        pantalla.add(lblConsumo);

        consumo_estimado_field = createPlaceholderTextField("Cantidad de Consumo Estimado");
        consumo_estimado_field.setBounds(128, 360, 450, 50);

        // --- BOTÓN GUARDAR ---
        JButton guardar_btn = new JButton("Guardar");
        guardar_btn.setBounds(250, 480, 200, 60);
        guardar_btn.setFont(new Font("Arial", Font.BOLD, 24));

        // --- BOTON ATRAS ---
        JButton btnAtras = new JButton("Atrás");
        btnAtras.setBounds(50, 50, 100, 40);

        // --- ACCIONES DE LOS BOTONES ---
        guardar_btn.addActionListener(e -> {
            if (merma_field.getText().equals("Cantidad de Merma por periodo") || consumo_estimado_field.getText().equals("Cantidad de Consumo Estimado")) {
                JOptionPane.showMessageDialog(frame_datos_consumo, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String mensaje = String.format("Datos Guardados:\nMerma: %s\nConsumo Estimado: %s", merma_field.getText(), consumo_estimado_field.getText());
                JOptionPane.showMessageDialog(frame_datos_consumo, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                // Aquí se podría agregar la lógica para guardar los datos en un archivo o base de datos
            }
        });

        btnAtras.addActionListener(e -> {
            frame_datos_consumo.dispose();
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.mostrar();
        });

        // --- AGREGAR COMPONENTES AL PANEL ---
        pantalla.add(merma_field);
        pantalla.add(consumo_estimado_field);
        pantalla.add(guardar_btn);
        pantalla.add(btnAtras);
        pantalla.add(cuadro_imagen);

        frame_datos_consumo.setVisible(true);
    }

    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Inter", Font.BOLD, 20));
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
        frame_datos_consumo.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DatosConsumo().mostrar());
    }
}