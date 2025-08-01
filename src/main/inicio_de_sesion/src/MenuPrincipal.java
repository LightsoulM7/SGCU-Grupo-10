import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {

    private JFrame frame_menu_principal;
    private String cedulaUsuario;

    public MenuPrincipal(String cedula) {
        this.cedulaUsuario = cedula;
        initialize();
    }

    private void initialize() {
        frame_menu_principal = new JFrame();
        frame_menu_principal.setTitle("Menú Principal");
        frame_menu_principal.setSize(700, 866);
        frame_menu_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_menu_principal.setLocationRelativeTo(null);
        frame_menu_principal.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_menu_principal.add(panel);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(50, 50, 150, 40);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 16));
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose();
                inicio_de_sesion loginScreen = new inicio_de_sesion();
                loginScreen.mostrar();
            }
        });
        fondo_label.add(btnCerrarSesion);

        JButton btnConsultarSaldo = new JButton("Consultar Saldo");
        btnConsultarSaldo.setBounds(200, 250, 300, 80);
        btnConsultarSaldo.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose();
                saldo saldoScreen = new saldo(cedulaUsuario);
                saldoScreen.mostrar();
            }
        });
        fondo_label.add(btnConsultarSaldo);

        JButton btnConsultarMenu = new JButton("Consultar Menú");
        btnConsultarMenu.setBounds(200, 375, 300, 80);
        btnConsultarMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose();
                MenuSelectionScreen menuSelectionScreen = new MenuSelectionScreen(cedulaUsuario);
                menuSelectionScreen.mostrar();
            }
        });
        fondo_label.add(btnConsultarMenu);

        JButton btnRecargarMonedero = new JButton("Recargar Monedero");
        btnRecargarMonedero.setBounds(200, 500, 300, 80);
        btnRecargarMonedero.setFont(new Font("Arial", Font.BOLD, 24));
        btnRecargarMonedero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_menu_principal.dispose();
                Recarga_monedero recarga = new Recarga_monedero(cedulaUsuario);
                recarga.mostrar();
            }
        });
        fondo_label.add(btnRecargarMonedero);
    }

    public void mostrar() {
        frame_menu_principal.setVisible(true);
    }

    public void ocultar() {
        frame_menu_principal.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal("12345678").mostrar());
    }
}