import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSelectionScreen {

    private JFrame frame_menu_selection;

    public MenuSelectionScreen() {
        initialize();
    }

    private void initialize() {
        frame_menu_selection = new JFrame();
        frame_menu_selection.setTitle("Selección de Menú");
        frame_menu_selection.setSize(700, 866);
        frame_menu_selection.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        frame_menu_selection.setLocationRelativeTo(null);
        frame_menu_selection.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_menu_selection.add(panel);

        // Fondo de la pantalla
        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png"); // Ruta relativa
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        // Botón Desayuno/Cena
        JButton btnDesayunoCena = new JButton("Desayuno");
        btnDesayunoCena.setBounds(200, 300, 300, 80);
        btnDesayunoCena.setFont(new Font("Arial", Font.BOLD, 24));
        btnDesayunoCena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir la pantalla de Desayuno/Cena
                menu_desayuno menuDesayunoCena = new menu_desayuno();
                menuDesayunoCena.mostrar();
                frame_menu_selection.dispose(); // Cierra la ventana actual
            }
        });
        fondo_label.add(btnDesayunoCena);

        // Botón Almuerzo
        JButton btnAlmuerzo = new JButton("Almuerzo");
        btnAlmuerzo.setBounds(200, 450, 300, 80);
        btnAlmuerzo.setFont(new Font("Arial", Font.BOLD, 24));
        btnAlmuerzo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir la pantalla de Almuerzo
                menu_almuerzo menuAlmuerzo = new menu_almuerzo();
                menuAlmuerzo.mostrar();
                frame_menu_selection.dispose(); // Cierra la ventana actual
            }
        });
        fondo_label.add(btnAlmuerzo);

        // Botón de retroceso (opcional, pero útil)
        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(50, 50, 100, 40);
        btnBack.addActionListener(e -> {
            frame_menu_selection.dispose();
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            menuPrincipal.mostrar();
        });
        fondo_label.add(btnBack);
    }

    public void mostrar() {
        frame_menu_selection.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuSelectionScreen().mostrar());
    }
}