import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {

    private JFrame frame_menu_principal;

    public MenuPrincipal() {
        initialize();
    }

    private void initialize() {
        frame_menu_principal = new JFrame();
        frame_menu_principal.setTitle("Menú Principal");
        frame_menu_principal.setSize(700, 866);
        frame_menu_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_menu_principal.setLocationRelativeTo(null);
        frame_menu_principal.setResizable(false);

        JPanel panel = new JPanel(null); // Usamos null layout para control absoluto
        frame_menu_principal.add(panel);

        // Fondo de la pantalla
        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png"); // Ruta relativa ajustada
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        // Botón Cerrar Sesión (arriba a la izquierda)
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(50, 50, 150, 40); // Posición y tamaño ajustados
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 16));
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose(); // Cierra la ventana actual
                inicio_de_sesion loginScreen = new inicio_de_sesion();
                loginScreen.mostrar(); // Muestra la pantalla de inicio de sesión
            }
        });
        fondo_label.add(btnCerrarSesion);

        // Botón Consultar Saldo
        JButton btnConsultarSaldo = new JButton("Consultar Saldo");
        btnConsultarSaldo.setBounds(200, 300, 300, 80);
        btnConsultarSaldo.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose(); // Cierra la ventana actual
                saldo saldoScreen = new saldo();
                saldoScreen.mostrar(); // Muestra la pantalla de saldo
            }
        });
        fondo_label.add(btnConsultarSaldo);

        // Botón Consultar Menú
        JButton btnConsultarMenu = new JButton("Consultar Menú");
        btnConsultarMenu.setBounds(200, 450, 300, 80);
        btnConsultarMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose(); // Cierra la ventana actual
                MenuSelectionScreen menuSelectionScreen = new MenuSelectionScreen();
                menuSelectionScreen.mostrar(); // Muestra la nueva ventana de selección de menú
            }
        });
        fondo_label.add(btnConsultarMenu);
    }

    public void mostrar() {
        frame_menu_principal.setVisible(true);
    }

    public void ocultar() {
        frame_menu_principal.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().mostrar());
    }
}