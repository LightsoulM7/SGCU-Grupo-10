import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class gestion_insumos {

    private JFrame frame_gestion_insumos;
    private JComboBox<String> insumo_nutrientes;
    private JTextField cantidad;
    private JTextField nombre;
    private JTextField precio;
    private JTextField campo_busqueda;
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

        // --- BARRA DE BÚSQUEDA ---
        campo_busqueda = createPlaceholderTextField("Buscar insumo por nombre");
        campo_busqueda.setBounds(50, 150, 450, 50);
        pantalla.add(campo_busqueda);

        JButton boton_buscar = new JButton("Buscar");
        boton_buscar.setBounds(510, 150, 120, 50);
        boton_buscar.setFont(new Font("Arial", Font.BOLD, 18));
        pantalla.add(boton_buscar);

        // --- COMBOBOX DE INSUMOS ---
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
            if (insumo_nutrientes.getSelectedIndex() != 0) {
                lastInsumoIndex = insumo_nutrientes.getSelectedIndex();
            }
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
        JButton registrar_insumo_btn = new JButton();
        registrar_insumo_btn.setBounds(210, 550, 264, 76);
        registrar_insumo_btn.setOpaque(false);
        registrar_insumo_btn.setContentAreaFilled(false);
        registrar_insumo_btn.setFocusPainted(false);
        registrar_insumo_btn.setBorderPainted(false);

        ImageIcon imagen_boton_registrar = new ImageIcon("../../Imagenes/Registrar_insumo.png");
        JLabel label_boton_registrar = new JLabel(imagen_boton_registrar);
        label_boton_registrar.setBounds(0, 0, 264, 76);
        registrar_insumo_btn.add(label_boton_registrar);

        JButton boton_cancelar = createCancelButton();

        // --- ACCIÓN DE REGISTRAR ---
        registrar_insumo_btn.addActionListener(e -> {
            String selectedInsumo = (String) insumo_nutrientes.getSelectedItem();
            String cantidadInsumo = cantidad.getText();
            String nombreInsumo = nombre.getText();
            String precioInsumo = precio.getText();

            if (insumo_nutrientes.getSelectedIndex() == 0 || cantidadInsumo.equals("Cantidad") || nombreInsumo.equals("Nombre") || precioInsumo.equals("Precio")) {
                JOptionPane.showMessageDialog(frame_gestion_insumos, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String nuevoInsumo = String.format("%s,%s,%s,%s", selectedInsumo, nombreInsumo, cantidadInsumo, precioInsumo);
                guardarInsumo(nuevoInsumo);
                JOptionPane.showMessageDialog(frame_gestion_insumos, "Insumo Registrado Exitosamente", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
        });

        // --- ACCIÓN DE BÚSQUEDA ---
        boton_buscar.addActionListener(e -> {
            String nombreBusqueda = campo_busqueda.getText();
            if (nombreBusqueda.isEmpty() || nombreBusqueda.equals("Buscar insumo por nombre")) {
                JOptionPane.showMessageDialog(frame_gestion_insumos, "Por favor, ingrese un nombre para buscar.", "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
                return;
            }
            buscarInsumo(nombreBusqueda);
        });

        // --- AGREGAR COMPONENTES AL PANEL ---
        pantalla.add(insumo_nutrientes);
        pantalla.add(cantidad);
        pantalla.add(nombre);
        pantalla.add(precio);
        pantalla.add(registrar_insumo_btn);
        pantalla.add(boton_cancelar);
        pantalla.add(cuadro_imagen);

        frame_gestion_insumos.setVisible(true);
    }

    private void guardarInsumo(String insumoData) {
        String rutaArchivo = "../../../../src/main/db/insumos.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            bw.write(insumoData);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame_gestion_insumos, "Error al guardar el insumo.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarInsumo(String nombreInsumo) {
        String rutaArchivo = "../../../../src/main/db/insumos.txt";
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4 && datos[1].trim().equalsIgnoreCase(nombreInsumo.trim())) {
                    insumo_nutrientes.setSelectedItem(datos[0].trim());
                    nombre.setText(datos[1].trim());
                    cantidad.setText(datos[2].trim());
                    precio.setText(datos[3].trim());
                    
                    nombre.setForeground(Color.BLACK);
                    cantidad.setForeground(Color.BLACK);
                    precio.setForeground(Color.BLACK);

                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(frame_gestion_insumos, "Insumo no encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame_gestion_insumos, "Error al leer el archivo de insumos.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        insumo_nutrientes.setSelectedIndex(0);
        lastInsumoIndex = 0;
        insumo_nutrientes.setForeground(Color.GRAY);
        resetPlaceholderTextField(cantidad, "Cantidad");
        resetPlaceholderTextField(nombre, "Nombre");
        resetPlaceholderTextField(precio, "Precio");
        resetPlaceholderTextField(campo_busqueda, "Buscar insumo por nombre");
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
