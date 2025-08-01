import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSelectionScreen2 {

    private JFrame frame_menu_selection;

    public MenuSelectionScreen2() {
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
        JButton btnDesayunoCena = new JButton();
        btnDesayunoCena.setBounds(250, 300, 209, 72);
        btnDesayunoCena.setOpaque(false);
        btnDesayunoCena.setContentAreaFilled(false);
        btnDesayunoCena.setFocusPainted(false);
ImageIcon imagen_boton_desayuno=new ImageIcon("../../Imagenes/Desayuno.png");
Image desayuno=imagen_boton_desayuno.getImage().getScaledInstance(209, 72,Image.SCALE_SMOOTH);
JLabel label_boton_desayuno=new JLabel(new ImageIcon(desayuno));

label_boton_desayuno.setBounds(250,300,209,72);
        btnDesayunoCena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir la pantalla de Desayuno/Cena
                menu_desayuno2 menuDesayunoCena = new menu_desayuno2();
                menuDesayunoCena.mostrar();
                frame_menu_selection.dispose(); // Cierra la ventana actual
            }
        });
        fondo_label.add(btnDesayunoCena);
        fondo_label.add(label_boton_desayuno);

        // Botón Almuerzo
        JButton btnAlmuerzo = new JButton();
        btnAlmuerzo.setBounds(250, 450, 209, 72);
         btnAlmuerzo.setOpaque(false);
        btnAlmuerzo.setContentAreaFilled(false);
        btnAlmuerzo.setFocusPainted(false);
ImageIcon imagen_boton_almuerzo=new ImageIcon("../../Imagenes/Almuerzo.png");
Image almuerzo=imagen_boton_almuerzo.getImage().getScaledInstance(209, 72,Image.SCALE_SMOOTH);

JLabel label_boton_almuerzo=new JLabel(new ImageIcon(almuerzo));
label_boton_almuerzo.setBounds(250,450,209,72);
        btnAlmuerzo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir la pantalla de Almuerzo
                menu_almuerzo2 menuAlmuerzo2 = new menu_almuerzo2();
                menuAlmuerzo2.mostrar();
                frame_menu_selection.dispose(); // Cierra la ventana actual
            }
        });
        fondo_label.add(btnAlmuerzo);
        fondo_label.add(label_boton_almuerzo);

        // Botón de retroceso (opcional, pero útil)
        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(50, 50, 100, 40);
        btnBack.addActionListener(e -> {
            frame_menu_selection.dispose();
            AdminMenu menuPrincipal = new AdminMenu();
            menuPrincipal.mostrar();
        });
        fondo_label.add(btnBack);
    }

    public void mostrar() {
        frame_menu_selection.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuSelectionScreen2().mostrar());
    }
}