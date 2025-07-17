import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
public class gastos_fijos{
    
    private JFrame frame_gastos_fijos;
    
    public gastos_fijos() {
        initialize();
    }
    
    private void initialize() {
        frame_gastos_fijos= new JFrame();
        frame_gastos_fijos.setTitle("Gastos Fijos");
        frame_gastos_fijos.setSize(700, 866);
    frame_gastos_fijos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_gastos_fijos.setLocationRelativeTo(null);
        frame_gastos_fijos.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        frame_gastos_fijos.add(pantalla);
        
        ImageIcon gestion_insumos=new ImageIcon("src\\main\\Imagenes\\cuadro_azul_ucv.png");
    


        
    Image cuadrar_imagen=gestion_insumos.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));

cuadro_imagen.setBounds(0,0,700,866);
pantalla.add(cuadro_imagen);

JButton guardar_gastos=new JButton();


guardar_gastos.setBounds(250,460,184,76);

guardar_gastos.setOpaque(false);
guardar_gastos.setContentAreaFilled(false);
guardar_gastos.setFocusPainted(false);


ImageIcon imagen_boton_registrar_insumo=new ImageIcon("src\\main\\Imagenes\\Guardar_gastos.png");
Image cuadrar_imagen_boton_registrar_insumo=imagen_boton_registrar_insumo.getImage().getScaledInstance(184, 76,Image.SCALE_SMOOTH);
JLabel label_boton_guardar_gastos=new JLabel(new ImageIcon(cuadrar_imagen_boton_registrar_insumo));

label_boton_guardar_gastos.setBounds(250,460,184,76);



cuadro_imagen.add(guardar_gastos);
cuadro_imagen.add(label_boton_guardar_gastos);
JButton boton_cancelar=new JButton();
boton_cancelar.setBounds(580,5,93,34);
boton_cancelar.setOpaque(false);
boton_cancelar.setContentAreaFilled(false);
boton_cancelar.setFocusPainted(false);
ImageIcon imagen_boton_cancelar=new ImageIcon("src\\main\\Imagenes\\\\cancelar.png");
Image cuadrar_imagen_boton_cancelar=imagen_boton_cancelar.getImage().getScaledInstance(93, 34,Image.SCALE_SMOOTH);
JLabel label_boton_cancelar=new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));
label_boton_cancelar.setBounds(580,5,93,34);
cuadro_imagen.add(boton_cancelar);
cuadro_imagen.add(label_boton_cancelar);


String tipo_servicio[]={"Tipo de Servicio","Agua","Gas","Luz"};
JComboBox<String>servicios=new JComboBox<>(tipo_servicio);
servicios.setEditable(false);
servicios.setBounds(128,220,450,70);

Font letras_servicios=new Font("Arial",Font.BOLD,22);
servicios.setForeground(Color.BLACK);
servicios.setFont(letras_servicios);
servicios.setBackground(Color.WHITE);

   
String tipo_de_empleado[]={"Tipo de Empleado","Personal de Cocina","Administrador","Personal de Limpieza"};
JComboBox<String>empleados=new JComboBox<>(tipo_de_empleado);
empleados.setEditable(false);
empleados.setBounds(128,300,450,70);

Font letras_empleados=new Font("Arial",Font.BOLD,22);
empleados.setForeground(Color.BLACK);
empleados.setFont(letras_empleados);
empleados.setBackground(Color.WHITE);

        pantalla.setFocusable(true);

JTextField id_empleado=new JTextField("ID Empleado");
id_empleado.setBounds(128,380,450,70);
id_empleado.setHorizontalAlignment(JTextField.CENTER);
id_empleado.setFont(new Font("Inter",Font.BOLD,24));
   id_empleado.setForeground(Color.BLACK);
    
    

   pantalla.revalidate();
        pantalla.repaint();



id_empleado.addFocusListener(new FocusAdapter() {
     private boolean isPlaceholderActive = true;
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholderActive&&id_empleado.getText().equals("ID Empleado")) {
            id_empleado.setText("");
            id_empleado.setForeground(Color.BLACK);
           
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (id_empleado.getText().trim().isEmpty()) {
            id_empleado.setText("ID Empleado");
            id_empleado.setFont(new Font("Inter",Font.BOLD,24));
             isPlaceholderActive = true;
        }else{
    id_empleado.setFont(new Font("Inter",Font.BOLD,24));
      isPlaceholderActive=false;
        }
    }
});


pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getSource() == pantalla && id_empleado.isFocusOwner()) {
                    id_empleado.transferFocusBackward();
                    pantalla.requestFocusInWindow(); 
                                    
                }
            }
        });


    
guardar_gastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton_guardar_gastos.setIcon(new ImageIcon(imagen_boton_registrar_insumo.getImage().getScaledInstance(184, 76, Image.SCALE_SMOOTH)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            label_boton_guardar_gastos.setIcon(new ImageIcon(imagen_boton_registrar_insumo.getImage().getScaledInstance(184, 76, Image.SCALE_SMOOTH)));
            }


        });
      
cuadro_imagen.add(servicios);
cuadro_imagen.add(empleados);
cuadro_imagen.add(id_empleado);

guardar_gastos.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Gasto Registrado");
        });
   


    }





    public void mostrar() {
         
        frame_gastos_fijos.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new gastos_fijos().mostrar());
            }
        }


