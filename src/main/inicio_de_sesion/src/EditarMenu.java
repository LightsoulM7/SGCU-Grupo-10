import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class EditarMenu {

    private JFrame frame_editar_menu;
    private JComboBox<String> menu_type_selector;
    private JTextField food_name_field;
    private JTextField food_ingredients_field;
    private JTextField merma_porcentaje_field;
    private JTextField estimacion_consumo_field;
    private JTextField food_description_field;
    private JList<String> menu_display_list;
    private DefaultListModel<String> list_model;

    private final String menu_file_path = "../../db/menu_semanal.txt";
    private final String costos_variables_file_path = "../../db/CostosVariables.txt";
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
        JLabel lblMenuType = new JLabel("Tipo de Menú:");
        lblMenuType.setBounds(100, 330, 150, 25);
        lblMenuType.setForeground(Color.WHITE);
        pantalla.add(lblMenuType);
        String[] menu_types = {"Desayuno", "Almuerzo"};
        menu_type_selector = new JComboBox<>(menu_types);
        menu_type_selector.setBounds(100, 355, 240, 30);
        pantalla.add(menu_type_selector);

        JLabel lblFoodName = new JLabel("Nombre de la Comida:");
        lblFoodName.setBounds(100, 380, 200, 25);
        lblFoodName.setForeground(Color.WHITE);
        pantalla.add(lblFoodName);
        food_name_field = new JTextField();
        food_name_field.setBounds(100, 405, 500, 35);
        pantalla.add(food_name_field);

        JLabel lblFoodIngredients = new JLabel("Ingredientes (separados por coma):");
        lblFoodIngredients.setBounds(100, 445, 300, 25);
        lblFoodIngredients.setForeground(Color.WHITE);
        pantalla.add(lblFoodIngredients);
        food_ingredients_field = new JTextField();
        food_ingredients_field.setBounds(100, 470, 500, 35);
        pantalla.add(food_ingredients_field);

        JLabel lblMermaPorcentaje = new JLabel("% de Merma:");
        lblMermaPorcentaje.setBounds(100, 515, 200, 25);
        lblMermaPorcentaje.setForeground(Color.WHITE);
        pantalla.add(lblMermaPorcentaje);
        merma_porcentaje_field = new JTextField();
        merma_porcentaje_field.setBounds(100, 540, 240, 35);
        pantalla.add(merma_porcentaje_field);
        ((AbstractDocument) merma_porcentaje_field.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        JLabel lblEstimacionConsumo = new JLabel("Estimación de Consumo:");
        lblEstimacionConsumo.setBounds(360, 515, 200, 25);
        lblEstimacionConsumo.setForeground(Color.WHITE);
        pantalla.add(lblEstimacionConsumo);
        estimacion_consumo_field = new JTextField();
        estimacion_consumo_field.setBounds(360, 540, 240, 35);
        pantalla.add(estimacion_consumo_field);
        ((AbstractDocument) estimacion_consumo_field.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        JLabel lblFoodDescription = new JLabel("Descripción:");
        lblFoodDescription.setBounds(100, 585, 200, 25);
        lblFoodDescription.setForeground(Color.WHITE);
        pantalla.add(lblFoodDescription);
        food_description_field = new JTextField();
        food_description_field.setBounds(100, 610, 500, 60);
        pantalla.add(food_description_field);

        // --- BOTONES DE ACCIÓN ---
        JButton btnSaveChanges = new JButton("Guardar Cambios");
        btnSaveChanges.setBounds(100, 690, 240, 50);
        pantalla.add(btnSaveChanges);

        JButton btnDeleteMenu = new JButton("Eliminar Seleccionado");
        btnDeleteMenu.setBounds(360, 620, 240, 50);
        pantalla.add(btnDeleteMenu);

        JButton btnClear = new JButton("Limpiar Campos / Nuevo");
        btnClear.setBounds(100, 680, 500, 50);
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
                String[] parts = line.split(";", 6); // Ahora esperamos 6 partes: Tipo;Nombre;Ingredientes;Merma;Consumo;Descripcion
                if (parts.length == 6) {
                    String key = parts[0]; // La clave es solo el tipo de menú
                    String value = parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + parts[4] + ";" + parts[5]; // Nombre;Ingredientes;Merma;Consumo;Descripcion
                    weekly_menu_data.put(key, value);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Error al cargar el menú semanal.", "Error de Lectura", JOptionPane.ERROR_MESSAGE);
        }
        // Poblar la lista visual
        for (Map.Entry<String, String> entry : weekly_menu_data.entrySet()) {
            String key = entry.getKey();
            String[] valueParts = entry.getValue().split(";"); // Nombre;Ingredientes;Merma;Consumo;Descripcion
            String displayText = String.format("%s: %s (Ingredientes: %s, Merma: %s%%, Consumo: %s, Descripción: %s)", key, valueParts[0], valueParts[1], valueParts[2], valueParts[3], valueParts[4]);
            list_model.addElement(displayText);
        }
    }

    private void onListSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && menu_display_list.getSelectedValue() != null) {
            String selectedValue = menu_display_list.getSelectedValue();
            // Parsear el texto: "Tipo: Nombre (Ingredientes: Ing1,Ing2, Merma: X%, Consumo: Y, Descripción: Desc)"
            String[] mainParts = selectedValue.split(": ", 2);
            String menuType = mainParts[0];
            String dataPart = mainParts[1];

            // Extraer Nombre, Ingredientes, Merma, Consumo y Descripción
            String[] dataFields = dataPart.split(" \\(Ingredientes: ", 2);
            String name = dataFields[0];

            String[] ingredientsAndOthers = dataFields[1].split(", Merma: ", 2);
            String ingredients = ingredientsAndOthers[0];

            String[] mermaAndOthers = ingredientsAndOthers[1].split("%, Consumo: ", 2);
            String merma = mermaAndOthers[0];

            String[] consumoAndDescription = mermaAndOthers[1].split(", Descripción: ", 2);
            String consumo = consumoAndDescription[0];
            String description = consumoAndDescription[1].substring(0, consumoAndDescription[1].length() - 1);

            menu_type_selector.setSelectedItem(menuType);
            food_name_field.setText(name);
            food_ingredients_field.setText(ingredients);
            merma_porcentaje_field.setText(merma);
            estimacion_consumo_field.setText(consumo);
            food_description_field.setText(description);
        }
    }

    private void saveOrUpdateMenu() {
        String menuType = (String) menu_type_selector.getSelectedItem();
        String foodName = food_name_field.getText().trim();
        String foodIngredients = food_ingredients_field.getText().trim();
        String mermaPorcentajeStr = merma_porcentaje_field.getText().trim();
        String estimacionConsumoStr = estimacion_consumo_field.getText().trim();
        String foodDescription = food_description_field.getText().trim();

        if (foodName.isEmpty() || foodIngredients.isEmpty() || mermaPorcentajeStr.isEmpty() || estimacionConsumoStr.isEmpty() || foodDescription.isEmpty()) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Todos los campos (Nombre, Ingredientes, % de Merma, Estimación de Consumo, Descripción) deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double mermaPorcentaje = Double.parseDouble(mermaPorcentajeStr);
            double estimacionConsumo = Double.parseDouble(estimacionConsumoStr);

            if (mermaPorcentaje < 0 || estimacionConsumo < 0) {
                JOptionPane.showMessageDialog(frame_editar_menu, "El % de Merma y la Estimación de Consumo no pueden ser negativos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame_editar_menu, "El % de Merma y la Estimación de Consumo deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validateIngredients(foodIngredients)) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Uno o más ingredientes no se encuentran en la base de datos de costos variables.", "Error de Ingredientes", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String key = menuType;
        String value = foodName + ";" + foodIngredients + ";" + mermaPorcentajeStr + ";" + estimacionConsumoStr + ";" + foodDescription;
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

        String selectedValue = menu_display_list.getSelectedValue();
        String menuType = selectedValue.split(":")[0].trim();
        String key = menuType;

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
        menu_type_selector.setSelectedIndex(0);
        food_name_field.setText("");
        food_ingredients_field.setText("");
        merma_porcentaje_field.setText("");
        estimacion_consumo_field.setText("");
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

    private boolean validateIngredients(String ingredients) {
        String[] individualIngredients = ingredients.split(",");
        try (BufferedReader reader = new BufferedReader(new FileReader(costos_variables_file_path))) {
            String line;
            StringBuilder costosVariablesNames = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // Buscar "Nombre: " y extraer el nombre
                int nameIndex = line.indexOf("Nombre: ");
                if (nameIndex != -1) {
                    String namePart = line.substring(nameIndex + "Nombre: ".length());
                    int endIndex = namePart.indexOf(",");
                    String ingredientName;
                    if (endIndex != -1) {
                        ingredientName = namePart.substring(0, endIndex).trim();
                    } else {
                        ingredientName = namePart.trim();
                    }
                    costosVariablesNames.append(ingredientName).append("\n");
                }
            }

            for (String ingredient : individualIngredients) {
                String trimmedIngredient = ingredient.trim();
                if (!trimmedIngredient.isEmpty() && !costosVariablesNames.toString().contains(trimmedIngredient)) {
                    return false; // Ingrediente no encontrado en los nombres
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_editar_menu, "Error al leer el archivo de costos variables: " + e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            return false; // Error al leer el archivo
        }
        return true; // Todos los ingredientes son válidos
    }

    public void mostrar() {
        frame_editar_menu.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditarMenu::new);
    }
}