import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuSelectionScreen2 {

    private JFrame frame_menu_selection;
    private final String menu_file_path = "../../db/menu_semanal.txt";
    private String cedulaUsuario;

    public MenuSelectionScreen2(String cedula) {
        this.cedulaUsuario = cedula;
        initialize();
    }

    private void initialize() {
        frame_menu_selection = new JFrame();
        frame_menu_selection.setTitle("Selección de Menú");
        frame_menu_selection.setSize(700, 866);
        frame_menu_selection.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame_menu_selection.setLocationRelativeTo(null);
        frame_menu_selection.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_menu_selection.add(panel);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        JButton btnDesayunoCena = new JButton();
        btnDesayunoCena.setBounds(250, 300, 209, 72);
        btnDesayunoCena.setOpaque(false);
        btnDesayunoCena.setContentAreaFilled(false);
        btnDesayunoCena.setFocusPainted(false);
        ImageIcon imagen_boton_desayuno = new ImageIcon("../../Imagenes/Desayuno.png");
        Image desayuno = imagen_boton_desayuno.getImage().getScaledInstance(209, 72, Image.SCALE_SMOOTH);
        JLabel label_boton_desayuno = new JLabel(new ImageIcon(desayuno));
        label_boton_desayuno.setBounds(250, 300, 209, 72);
        btnDesayunoCena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMenuAvailable("Desayuno")) {
                    new MenuDisplayScreen("Desayuno", "admin", cedulaUsuario).mostrar();
                    frame_menu_selection.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame_menu_selection, "No se encuentra disponible el menú de hoy.", "Menú no Disponible", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        fondo_label.add(btnDesayunoCena);
        fondo_label.add(label_boton_desayuno);

        JButton btnAlmuerzo = new JButton();
        btnAlmuerzo.setBounds(250, 450, 209, 72);
        btnAlmuerzo.setOpaque(false);
        btnAlmuerzo.setContentAreaFilled(false);
        btnAlmuerzo.setFocusPainted(false);
        ImageIcon imagen_boton_almuerzo = new ImageIcon("../../Imagenes/Almuerzo.png");
        Image almuerzo = imagen_boton_almuerzo.getImage().getScaledInstance(209, 72, Image.SCALE_SMOOTH);
        JLabel label_boton_almuerzo = new JLabel(new ImageIcon(almuerzo));
        label_boton_almuerzo.setBounds(250, 450, 209, 72);
        btnAlmuerzo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMenuAvailable("Almuerzo")) {
                    new MenuDisplayScreen("Almuerzo", "admin", cedulaUsuario).mostrar();
                    frame_menu_selection.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame_menu_selection, "No se encuentra disponible el menú de hoy.", "Menú no Disponible", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        fondo_label.add(btnAlmuerzo);
        fondo_label.add(label_boton_almuerzo);

        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(50, 50, 100, 40);
        btnBack.addActionListener(e -> {
            frame_menu_selection.dispose();
            new AdminMenu().mostrar(); // No se pasa la cédula aquí, ya que AdminMenu no la necesita
        });
        fondo_label.add(btnBack);
    }

    private boolean isMenuAvailable(String menuType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(menu_file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(menuType + ";")) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame_menu_selection, "Error al leer el archivo de menú.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void mostrar() {
        frame_menu_selection.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuSelectionScreen2("12345678").mostrar());
    }
}