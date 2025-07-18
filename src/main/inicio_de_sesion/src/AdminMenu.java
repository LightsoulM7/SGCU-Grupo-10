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
        frame_admin_menu.setSize(700, 866);
        frame_admin_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_admin_menu.setLocationRelativeTo(null);
        frame_admin_menu.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_admin_menu.add(panel);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        int buttonWidth = 400;
        int buttonHeight = 70;
        int startY = 200;
        int gap = 90;

        JButton btnConsultarMenu = new JButton("Consultar Menú");
        btnConsultarMenu.setBounds(150, startY, buttonWidth, buttonHeight);
        btnConsultarMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarMenu.addActionListener(e -> {
            MenuSelectionScreen menuSelection = new MenuSelectionScreen();
            menuSelection.mostrar();
            frame_admin_menu.dispose();
        });
        fondo_label.add(btnConsultarMenu);

        JButton btnConsultarSaldo = new JButton("Consultar Saldo");
        btnConsultarSaldo.setBounds(150, startY + gap, buttonWidth, buttonHeight);
        btnConsultarSaldo.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarSaldo.addActionListener(e -> {
            saldo2 saldoView = new saldo2();
            saldoView.mostrar();
            frame_admin_menu.dispose();
        });
        fondo_label.add(btnConsultarSaldo);

        JButton btnCostosVariables = new JButton("Gestión de Insumos");
        btnCostosVariables.setBounds(150, startY + 2 * gap, buttonWidth, buttonHeight);
        btnCostosVariables.setFont(new Font("Arial", Font.BOLD, 24));
        btnCostosVariables.addActionListener(e -> {
            gestion_insumos gestionInsumos = new gestion_insumos();
            gestionInsumos.mostrar();
            frame_admin_menu.dispose();
        });
        fondo_label.add(btnCostosVariables);

        JButton btnCostosFijos = new JButton("Gestión de Gastos Fijos");
        btnCostosFijos.setBounds(150, startY + 3 * gap, buttonWidth, buttonHeight);
        btnCostosFijos.setFont(new Font("Arial", Font.BOLD, 24));
        btnCostosFijos.addActionListener(e -> {
            gastos_fijos gastosFijos = new gastos_fijos();
            gastosFijos.mostrar();
            frame_admin_menu.dispose();
        });
        fondo_label.add(btnCostosFijos);

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