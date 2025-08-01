import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class saldo2 {

    private JFrame frame_saldo;

    public saldo2() {
        initialize();
    }

    private void initialize() {
        frame_saldo = new JFrame();
        frame_saldo.setTitle("Consultar Saldo");
        frame_saldo.setSize(700, 866);
        frame_saldo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        frame_saldo.setLocationRelativeTo(null);
        frame_saldo.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_saldo.add(panel);

        // Fondo de la pantalla
        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png"); // Ruta relativa
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        // Etiqueta de ejemplo para mostrar el saldo
        JLabel lblSaldo = new JLabel("Su saldo actual es: $150.00");
        lblSaldo.setBounds(100, 200, 500, 50);
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 30));
        lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaldo.setForeground(Color.WHITE);
        fondo_label.add(lblSaldo);

        // Botón de retroceso
        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(50, 50, 100, 40);
        btnBack.addActionListener(e -> {
            frame_saldo.dispose();
            AdminMenu adminmenu = new AdminMenu();
            adminmenu.mostrar();
        });
        fondo_label.add(btnBack);
    }

    public void mostrar() {
        frame_saldo.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new saldo("00000000").mostrar());
    }
}