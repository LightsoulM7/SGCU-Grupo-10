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

        // Fondo de la pantalla
        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png"); // Ruta relativa
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        // --- TITULO ---
        JLabel lblTitulo = new JLabel("Menú de Administrador");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(150, 100, 400, 50);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        fondo_label.add(lblTitulo);

        // Botón Gestión de Costos Variables
        JButton btnCostosVariables = new JButton("Gestión de Costos Variables");
        btnCostosVariables.setBounds(150, 180, 400, 80);
        btnCostosVariables.setFont(new Font("Arial", Font.BOLD, 24));
        btnCostosVariables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir gestion_insumos
                gestion_insumos gestionInsumos = new gestion_insumos();
                gestionInsumos.mostrar();
                frame_admin_menu.dispose(); // Cierra la ventana actual
            }
        });
        fondo_label.add(btnCostosVariables);

        // Botón Gestión de Costos Fijos
        JButton btnCostosFijos = new JButton("Gestión de Costos Fijos");
        btnCostosFijos.setBounds(150, 290, 400, 80);
        btnCostosFijos.setFont(new Font("Arial", Font.BOLD, 24));
        btnCostosFijos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir gastos_fijos
                gastos_fijos gastosFijos = new gastos_fijos();
                gastosFijos.mostrar();
                frame_admin_menu.dispose(); // Cierra la ventana actual
            }
        });
        fondo_label.add(btnCostosFijos);

        // Botón Consultar Menú
        JButton btnConsultarMenu = new JButton("Consultar Menú");
        btnConsultarMenu.setBounds(150, 400, 400, 80);
        btnConsultarMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_admin_menu.dispose();
                MenuSelectionScreen2 menuSelectionScreen = new MenuSelectionScreen2();
                menuSelectionScreen.mostrar();
            }
        });
        fondo_label.add(btnConsultarMenu);

        // Botón Datos de Consumo
        JButton btnDatosConsumo = new JButton("Datos de Consumo");
        btnDatosConsumo.setBounds(150, 510, 400, 80);
        btnDatosConsumo.setFont(new Font("Arial", Font.BOLD, 24));
        btnDatosConsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_admin_menu.dispose();
                DatosConsumo datosConsumo = new DatosConsumo();
                datosConsumo.mostrar();
            }
        });
        fondo_label.add(btnDatosConsumo);

        // Botón Reconocimiento
        JButton btnReconocimiento = new JButton("Reconocimiento");
        btnReconocimiento.setBounds(150, 620, 400, 80);
        btnReconocimiento.setFont(new Font("Arial", Font.BOLD, 24));
        btnReconocimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_admin_menu.dispose();
                ReconocimientoFacial reconocimiento = new ReconocimientoFacial();
                reconocimiento.mostrar();
            }
        });
        fondo_label.add(btnReconocimiento);

        // Botón Gestion de menu
        JButton btnGestionMenu = new JButton("Gestion de menu");
        btnGestionMenu.setBounds(150, 730, 400, 80);
        btnGestionMenu.setFont(new Font("Arial", Font.BOLD, 24));
        btnGestionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_admin_menu.dispose();
                EditarMenu editarMenu = new EditarMenu();
                editarMenu.mostrar();
            }
        });
        fondo_label.add(btnGestionMenu);

        // Botón de retroceso (opcional, pero útil)
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