import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu {

    private JFrame frame_admin_menu;

    public AdminMenu() {
        initialize();
    }

    private void initialize() {
        frame_admin_menu = new JFrame();
        frame_admin_menu.setTitle("Menú de Administrador");
        frame_admin_menu.setSize(700, 1000); // Increased height
        frame_admin_menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
        frame_admin_menu.setLocationRelativeTo(null);
        frame_admin_menu.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_admin_menu.add(panel);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 1000); // Adjusted height
        panel.add(fondo_label);

        JLabel lblTitulo = new JLabel("Menú de Administrador");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(150, 50, 400, 50); // Moved up
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        fondo_label.add(lblTitulo);

        JButton btnGestionInsumos = new JButton("Gestión de Insumos");
        btnGestionInsumos.setBounds(200, 150, 300, 80);
        btnGestionInsumos.setFont(new Font("Arial", Font.BOLD, 24));
        btnGestionInsumos.setBackground(new Color(70, 130, 180));
        btnGestionInsumos.setForeground(Color.WHITE);
        btnGestionInsumos.addActionListener(e -> {
            frame_admin_menu.dispose();
            new gestion_insumos().mostrar();
        });
        fondo_label.add(btnGestionInsumos);

        JButton btnGestionGastosFijos = new JButton("Gestión de Gastos Fijos");
        btnGestionGastosFijos.setBounds(200, 240, 300, 80);
        btnGestionGastosFijos.setFont(new Font("Arial", Font.BOLD, 24));
        btnGestionGastosFijos.setBackground(new Color(70, 130, 180));
        btnGestionGastosFijos.setForeground(Color.WHITE);
        btnGestionGastosFijos.addActionListener(e -> {
            frame_admin_menu.dispose();
            new gastos_fijos().mostrar();
        });
        fondo_label.add(btnGestionGastosFijos);

        JButton btnEditarMenu = new JButton("Editar Menú Semanal");
        btnEditarMenu.setBounds(200, 330, 300, 80);
        btnEditarMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnEditarMenu.setBackground(new Color(70, 130, 180));
        btnEditarMenu.setForeground(Color.WHITE);
        btnEditarMenu.addActionListener(e -> {
            frame_admin_menu.dispose();
            new EditarMenu().mostrar();
        });
        fondo_label.add(btnEditarMenu);

        JButton btnReconocimientoFacial = new JButton("Reconocimiento Facial");
        btnReconocimientoFacial.setBounds(200, 420, 300, 80);
        btnReconocimientoFacial.setFont(new Font("Arial", Font.BOLD, 24));
        btnReconocimientoFacial.setBackground(new Color(70, 130, 180));
        btnReconocimientoFacial.setForeground(Color.WHITE);
        btnReconocimientoFacial.addActionListener(e -> {
            frame_admin_menu.dispose();
            new ReconocimientoFacial().mostrar();
        });
        fondo_label.add(btnReconocimientoFacial);

        JButton btnConsultarMenuAdmin = new JButton("Consultar Menú (Admin)");
        btnConsultarMenuAdmin.setBounds(200, 510, 300, 80);
        btnConsultarMenuAdmin.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarMenuAdmin.setBackground(new Color(70, 130, 180));
        btnConsultarMenuAdmin.setForeground(Color.WHITE);
        btnConsultarMenuAdmin.addActionListener(e -> {
            frame_admin_menu.dispose();
            new MenuSelectionScreen2(inicio_de_sesion.cedulaUsuarioLogueado).mostrar();
        });
        fondo_label.add(btnConsultarMenuAdmin);

        JButton btnRecargarMonedero = new JButton("Recargar Monedero");
        btnRecargarMonedero.setBounds(200, 600, 300, 80);
        btnRecargarMonedero.setFont(new Font("Arial", Font.BOLD, 24));
        btnRecargarMonedero.setBackground(new Color(70, 130, 180));
        btnRecargarMonedero.setForeground(Color.WHITE);
        btnRecargarMonedero.addActionListener(e -> {
            frame_admin_menu.dispose();
            new Recarga_monedero(inicio_de_sesion.cedulaUsuarioLogueado).mostrar();
        });
        fondo_label.add(btnRecargarMonedero);

        JButton btnConsultarSaldo = new JButton("Consultar Saldo");
        btnConsultarSaldo.setBounds(200, 690, 300, 80);
        btnConsultarSaldo.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarSaldo.setBackground(new Color(70, 130, 180));
        btnConsultarSaldo.setForeground(Color.WHITE);
        btnConsultarSaldo.addActionListener(e -> {
            frame_admin_menu.dispose();
            new saldo(inicio_de_sesion.cedulaUsuarioLogueado).mostrar();
        });
        fondo_label.add(btnConsultarSaldo);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 780, 300, 80);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 24));
        btnSalir.setBackground(new Color(200, 50, 50));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.addActionListener(e -> {
            frame_admin_menu.dispose();
            new inicio_de_sesion().mostrar();
        });
        fondo_label.add(btnSalir);

        JButton btnBack = new JButton("Cerrar Sesión");
        btnBack.setBounds(50, 50, 150, 40);
        btnBack.addActionListener(e -> {
            frame_admin_menu.dispose();
            inicio_de_sesion loginScreen = new inicio_de_sesion();
            loginScreen.mostrar();
        });
        fondo_label.add(btnBack);
    }

    public void mostrar() {
        frame_admin_menu.setVisible(true);
    }

    public void ocultar() {
        frame_admin_menu.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMenu().mostrar());
    }
}