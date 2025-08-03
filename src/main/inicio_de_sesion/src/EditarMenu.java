import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class EditarMenu {

    private JFrame frame_editar_menu;
    private JComboBox<String> day_selector;
    private JComboBox<String> menu_type_selector;
    private JTextField food_name_field;
    private JTextField food_description_field;
    private JList<String> menu_display_list;
    private DefaultListModel<String> list_model;

    private final String menu_file_path = "../../db/menu_semanal.txt";
    // Usamos un TreeMap para mantener los datos ordenados por clave (Día;Tipo)
    private Map<String, String> weekly_menu_data;

    public EditarMenu() {
        weekly_menu_data = new TreeMap<>();
        initialize();
        createMenuFileIfNotExists();
        loadWeeklyMenuFromFile();
    }

    private void createMenuFileIfNotExists() {
        try {
            File file = new File(menu_file_path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_editar_menu, "No se pudo crear el archivo de menú.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initialize() {
        frame_editar_menu = new JFrame();
        frame_editar_menu.setTitle("Gestión de Menú Semanal");
        frame_editar_menu.setSize(700, 866);
        frame_editar_menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame_editar_menu.setLocationRelativeTo(null);
        frame_editar_menu.setResizable(false);

        JPanel pantalla = new JPanel(null);
        frame_editar_menu.add(pantalla);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel cuadro_imagen = new JLabel(fondo);
        cuadro_imagen.setBounds(0, 0, 700, 866);

        JLabel lblTitulo = new JLabel("Gestión de Menú Semanal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(100, 20, 500, 50);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        pantalla.add(lblTitulo);

        // --- LISTA DE VISUALIZACIÓN DE MENÚS ---
        JLabel lblMenuList = new JLabel("Menús Planificados:");
        lblMenuList.setBounds(100, 80, 500, 25);
        lblMenuList.setForeground(Color.WHITE);
        lblMenuList.setFont(new Font("Arial", Font.BOLD, 16));
        pantalla.add(lblMenuList);

        list_model = new DefaultListModel<>();
        menu_display_list = new JList<>(list_model);
        JScrollPane scrollPane = new JScrollPane(menu_display_list);
        scrollPane.setBounds(100, 110, 500, 200);
        pantalla.add(scrollPane);

        // --- CAMPOS DE EDICIÓN ---
        JLabel lblDay = new JLabel("Día:");
        lblDay.setBounds(100, 330, 100, 25);
        lblDay.setForeground(Color.WHITE);
        pantalla.add(lblDay);
        String[] days = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        day_selector = new JComboBox<>(days);
        day_selector.setBounds(100, 355, 240, 30);
        pantalla.add(day_selector);

        JLabel lblMenuType = new JLabel("Tipo de Menú:");
        lblMenuType.setBounds(360, 330, 150, 25);
        lblMenuType.setForeground(Color.WHITE);
        pantalla.add(lblMenuType);
        String[] menu_types = {"Desayuno/Cena", "Almuerzo"};
        menu_type_selector = new JComboBox<>(menu_types);
        menu_type_selector.setBounds(360, 355, 240, 30);
        pantalla.add(menu_type_selector);

        JLabel lblFoodName = new JLabel("Nombre de la Comida:");
        lblFoodName.setBounds(100, 400, 200, 25);
        lblFoodName.setForeground(Color.WHITE);
        pantalla.add(lblFoodName);
        food_name_field = new JTextField();
        food_name_field.setBounds(100, 425, 500, 35);
        pantalla.add(food_name_field);

        JLabel lblFoodDescription = new JLabel("Descripción:");
        lblFoodDescription.setBounds(100, 470, 200, 25);
        lblFoodDescription.setForeground(Color.WHITE);
        pantalla.add(lblFoodDescription);
        food_description_field = new JTextField();
        food_description_field.setBounds(100, 495, 500, 60);
        pantalla.add(food_description_field);

        // --- BOTONES DE ACCIÓN ---
        JButton btnSaveChanges = new JButton("Guardar Cambios");
        btnSaveChanges.setBounds(100, 580, 240, 50);
        pantalla.add(btnSaveChanges);

        JButton btnDeleteMenu = new JButton("Eliminar Seleccionado");
        btnDeleteMenu.setBounds(360, 580, 240, 50);
        pantalla.add(btnDeleteMenu);

        JButton btnClear = new JButton("Limpiar Campos / Nuevo");
        btnClear.setBounds(100, 640, 500, 50);
        pantalla.add(btnClear);

        JButton btnAtras = new JButton("Atrás");
        btnAtras.setBounds(20, 20, 100, 40);
        pantalla.add(btnAtras);

        // --- ACCIONES ---
        menu_display_list.addListSelectionListener(this::onListSelection);
        btnSaveChanges.addActionListener(e -> saveOrUpdateMenu());
        btnDeleteMenu.addActionListener(e -> deleteSelectedMenu());
        btnClear.addActionListener(e -> clearFields());
        btnAtras.addActionListener(e -> {
            frame_editar_menu.dispose();
            new AdminMenu().mostrar();
        });

        pantalla.add(cuadro_imagen);
        frame_editar_menu.setVisible(true);
    }

    private void loadWeeklyMenuFromFile() {
        weekly_menu_data.clear();
        list_model.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(menu_file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 4);
                if (parts.length == 4) {
                    String key = parts[0] + ";" + parts[1];
                    String value = parts[2] + ";" + parts[3];
                    weekly_menu_data.put(key, value);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Error al cargar el menú semanal.", "Error de Lectura", JOptionPane.ERROR_MESSAGE);
        }
        // Poblar la lista visual
        for (Map.Entry<String, String> entry : weekly_menu_data.entrySet()) {
            String[] keyParts = entry.getKey().split(";");
            String[] valueParts = entry.getValue().split(";");
            String displayText = String.format("%s - %s: %s (%s)", keyParts[0], keyParts[1], valueParts[0], valueParts[1]);
            list_model.addElement(displayText);
        }
    }

    private void onListSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && menu_display_list.getSelectedValue() != null) {
            String selectedValue = menu_display_list.getSelectedValue();
            // Parsear el texto: "Día - Tipo: Nombre (Descripción)"
            String[] mainParts = selectedValue.split(": ", 2);
            String[] keyParts = mainParts[0].split(" - ");
            String valuePart = mainParts[1];
            String name = valuePart.substring(0, valuePart.lastIndexOf(" ("));
            String description = valuePart.substring(valuePart.lastIndexOf(" (") + 2, valuePart.length() - 1);

            day_selector.setSelectedItem(keyParts[0]);
            menu_type_selector.setSelectedItem(keyParts[1]);
            food_name_field.setText(name);
            food_description_field.setText(description);
        }
    }

    private void saveOrUpdateMenu() {
        String day = (String) day_selector.getSelectedItem();
        String menuType = (String) menu_type_selector.getSelectedItem();
        String foodName = food_name_field.getText().trim();
        String foodDescription = food_description_field.getText().trim();

        if (foodName.isEmpty() || foodDescription.isEmpty()) {
            JOptionPane.showMessageDialog(frame_editar_menu, "El nombre y la descripción no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String key = day + ";" + menuType;
        String value = foodName + ";" + foodDescription;
        weekly_menu_data.put(key, value);

        writeMapToFile();
        loadWeeklyMenuFromFile(); // Recargar todo para reflejar el cambio
        JOptionPane.showMessageDialog(frame_editar_menu, "Menú guardado con éxito.");
    }

    private void deleteSelectedMenu() {
        if (menu_display_list.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Por favor, seleccione un menú de la lista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String day = (String) day_selector.getSelectedItem();
        String menuType = (String) menu_type_selector.getSelectedItem();
        String key = day + ";" + menuType;

        if (weekly_menu_data.containsKey(key)) {
            int confirm = JOptionPane.showConfirmDialog(frame_editar_menu, "¿Está seguro de que desea eliminar este menú?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                weekly_menu_data.remove(key);
                writeMapToFile();
                loadWeeklyMenuFromFile(); // Recargar
                clearFields();
            }
        } else {
             JOptionPane.showMessageDialog(frame_editar_menu, "El menú seleccionado no parece existir en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        menu_display_list.clearSelection();
        day_selector.setSelectedIndex(0);
        menu_type_selector.setSelectedIndex(0);
        food_name_field.setText("");
        food_description_field.setText("");
    }

    private void writeMapToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(menu_file_path, false))) { // false para sobrescribir
            for (Map.Entry<String, String> entry : weekly_menu_data.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Error al guardar el menú en el archivo.", "Error de Escritura", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        frame_editar_menu.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditarMenu::new);
    }
}
