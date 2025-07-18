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
        ImageIcon fondo = new ImageIcon("src/main/Imagenes/cuadro_azul_ucv.png"); // Ajusta la ruta si es necesario
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        // Botón Consultar Saldo
        JButton btnConsultarSaldo = new JButton("Consultar Saldo");
        btnConsultarSaldo.setBounds(200, 300, 300, 80);
        btnConsultarSaldo.setFont(new Font("Arial", Font.BOLD, 24));
        btnConsultarSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame_menu_principal, "Funcionalidad de Consultar Saldo");
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
                JOptionPane.showMessageDialog(frame_menu_principal, "Funcionalidad de Consultar Menú");
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