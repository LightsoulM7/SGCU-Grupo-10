import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class saldo {

    private JFrame frame_saldo;
    private String cedulaUsuario;

    public saldo(String cedula) {
        this.cedulaUsuario = cedula;
        initialize();
    }

    private void initialize() {
        frame_saldo = new JFrame();
        frame_saldo.setTitle("Consultar Saldo");
        frame_saldo.setSize(700, 866);
        frame_saldo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame_saldo.setLocationRelativeTo(null);
        frame_saldo.setResizable(false);

        JPanel panel = new JPanel(null);
        frame_saldo.add(panel);

        ImageIcon fondo = new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
        JLabel fondo_label = new JLabel(fondo);
        fondo_label.setBounds(0, 0, 700, 866);
        panel.add(fondo_label);

        String saldo = getSaldo(cedulaUsuario);
        JLabel lblSaldo = new JLabel("Su saldo actual es: " + saldo);
        lblSaldo.setBounds(100, 200, 500, 50);
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 30));
        lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaldo.setForeground(Color.WHITE);
        fondo_label.add(lblSaldo);

        JButton btnBack = new JButton("Atrás");
        btnBack.setBounds(50, 50, 100, 40);
        btnBack.addActionListener(e -> {
            frame_saldo.dispose();
            new MenuPrincipal(cedulaUsuario).mostrar();
        });
        fondo_label.add(btnBack);
    }

    private String getSaldo(String cedula) {
        try (BufferedReader reader = new BufferedReader(new FileReader("../../db/usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].trim().equals(cedula)) {
                    return parts[3].trim() + " Bs.";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No disponible";
    }

    public void mostrar() {
        frame_saldo.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new saldo("12345678").mostrar()); // Cédula de ejemplo para pruebas
    }
}