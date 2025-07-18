import javax.swing.*;
import java.awt.*;
public class saldo {
    
    private JFrame frame_saldo;
    
    public saldo() {
        initialize();
    }
    
    private void initialize() {
        frame_saldo = new JFrame();
        frame_saldo.setTitle("Inicio de sesión");
        frame_saldo.setSize(700, 866);
    frame_saldo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_saldo.setLocationRelativeTo(null);
        frame_saldo.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        frame_saldo.add(pantalla);
        
        ImageIcon saldo=new ImageIcon("src\\main\\Imagenes\\cuadro_azul_ucv.png");
    


        
    Image cuadrar_imagen=saldo.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));

cuadro_imagen.setBounds(0,0,700,866);
pantalla.add(cuadro_imagen);


JButton boton_cancelar=new JButton();



boton_cancelar.setBounds(580,5,93,34);
boton_cancelar.setOpaque(false);
boton_cancelar.setContentAreaFilled(false);
boton_cancelar.setFocusPainted(false);


ImageIcon imagen_boton_cancelar=new ImageIcon("src\\main\\Imagenes\\\\cancelar.png");

Image cuadrar_imagen_boton_cancelar=imagen_boton_cancelar.getImage().getScaledInstance(93, 34,Image.SCALE_SMOOTH);

JLabel label_boton_cancelar=new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));

      JLabel monto_cuenta = new JLabel("Saldo 4$");
        monto_cuenta.setFont(new Font("Arial",Font.BOLD,24));
        monto_cuenta.setForeground(Color.WHITE);
        monto_cuenta.setBounds(280, 250, 100, 20); 
        


        
        pantalla.setFocusable(true);

JPanel ultimas_transacciones=new JPanel();
ultimas_transacciones.setBackground(new Color(255,255,255,255));
ultimas_transacciones.setBounds(90,380,550,100);


JPanel ultimas_transacciones2=new JPanel();
ultimas_transacciones2.setBackground(new Color(255,255,255,255));
ultimas_transacciones2.setBounds(90,470,550,100);

    


   pantalla.revalidate();
        pantalla.repaint();

label_boton_cancelar.setBounds(580,5,93,34);

cuadro_imagen.add(monto_cuenta);
cuadro_imagen.add(boton_cancelar);
cuadro_imagen.add(label_boton_cancelar);

cuadro_imagen.add(ultimas_transacciones);

JLabel pago_transacciones=new JLabel("Ultimas Transacciones");
pago_transacciones.setFont(new Font("Arial ",Font.BOLD,24));
pago_transacciones.setForeground(Color.WHITE);
pago_transacciones.setHorizontalAlignment(JLabel.CENTER);
pago_transacciones.setBounds(190,350,300,30);


cuadro_imagen.add(pago_transacciones);

cuadro_imagen.add(ultimas_transacciones);
cuadro_imagen.add(ultimas_transacciones2);
 Font headerFont = new Font("Monospaced", Font.BOLD, 12);
int titulo_ancho = 300;
        int titulo_alto = 25;
        int titulo_x = (ultimas_transacciones.getWidth() - titulo_ancho) / 2;
        int titulo_y = 5;

        Color headerColor = Color.BLACK;
        int header_y = titulo_y + titulo_alto + 10; 

        JLabel headerFecha = new JLabel("Fecha de la Transaccion");
        headerFecha.setFont(headerFont);
        headerFecha.setForeground(headerColor);
        headerFecha.setBounds(10, header_y, 80, 20);
        ultimas_transacciones.add(headerFecha);

        JLabel headerTipo = new JLabel("Tipo de Transferencia");
        headerTipo.setFont(headerFont);
        headerTipo.setForeground(headerColor);
        headerTipo.setBounds(90, header_y, 100, 20); 
        ultimas_transacciones.add(headerTipo);

        JLabel headerDescripcion = new JLabel("Descripción");
        headerDescripcion.setFont(headerFont);
        headerDescripcion.setForeground(headerColor);
        headerDescripcion.setBounds(190, header_y, 120, 20); 
        ultimas_transacciones.add(headerDescripcion);

        JLabel headerMonto = new JLabel("Monto");
        headerMonto.setFont(headerFont);
        headerMonto.setForeground(headerColor);
        headerMonto.setBounds(330, header_y, 60, 20); 
        ultimas_transacciones.add(headerMonto);

        JLabel headerSaldo = new JLabel("Saldo Actual");
        headerSaldo.setFont(headerFont);
        headerSaldo.setForeground(headerColor);
        headerSaldo.setBounds(400, header_y, 90, 20); 
        ultimas_transacciones.add(headerSaldo);

       
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(5, header_y + 20, ultimas_transacciones.getWidth() - 10, 5);
        ultimas_transacciones.add(separator);


    
        Font dataFont = new Font("Monospaced", Font.PLAIN, 14); 
        int data_y_start = header_y + 25; 

       
        
        addTransactionRow(ultimas_transacciones, "11/07/2025", "Pago Movil", "Pago", "-15.00$", "0$", dataFont, data_y_start, 0);
        addTransactionRow(ultimas_transacciones2, "12/07/2025", "Pago Movil", "Recarga", "+4.00$", "4.00$", dataFont, data_y_start, 0);

    }

    private void addTransactionRow(JPanel panel, String fecha, String tipo, String descripcion, String monto, String saldoActual, Font font, int start_y, int rowIndex) {
        int rowHeight = 50;
        int current_y = start_y + (rowIndex * rowHeight);

        JLabel labelFecha = new JLabel(fecha);
        labelFecha.setFont(font);
        labelFecha.setBounds(10, current_y, 80, rowHeight);
        panel.add(labelFecha);

        JLabel labelTipo = new JLabel(tipo);
        labelTipo.setFont(font);
        labelTipo.setBounds(90, current_y, 100, rowHeight);
        panel.add(labelTipo);

        JLabel labelDescripcion = new JLabel(descripcion);
        labelDescripcion.setFont(font);
        labelDescripcion.setBounds(190, current_y, 120, rowHeight);
        panel.add(labelDescripcion);

        JLabel labelMonto = new JLabel(monto);
        labelMonto.setFont(font);
        labelMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        labelMonto.setBounds(330, current_y, 60, rowHeight);
    
        if (monto.startsWith("-")) {
            labelMonto.setForeground(Color.RED);
        } else if (monto.startsWith("+")) {
            labelMonto.setForeground(new Color(0, 100, 0)); 
        } else {
            labelMonto.setForeground(Color.BLACK);
        }
        panel.add(labelMonto);

        JLabel labelSaldo = new JLabel(saldoActual);
        labelSaldo.setFont(font);
        labelSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
        labelSaldo.setBounds(400, current_y, 90, rowHeight);
        panel.add(labelSaldo);


    }


    public void mostrar() {
         
        frame_saldo.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new saldo().mostrar());
            }
        }
