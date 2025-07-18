import javax.swing.*;
import java.awt.*;

public class menu_almuerzo {
    
    private JFrame menu;
    private JRadioButton pabellon;
    private JRadioButton pasta_carbonara;
    private JRadioButton hamburguesa;
     
    public menu_almuerzo() {
        initialize();
    }
    
    private void initialize() {
        menu = new JFrame();
        menu.setTitle("Inicio de sesión");
        menu.setSize(700, 866);
    menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);
    menu.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        menu.add(pantalla);
        
ButtonGroup botones=new ButtonGroup();
pabellon=new JRadioButton("");
pabellon.setOpaque(false);
pabellon.setForeground(Color.BLACK);
pasta_carbonara=new JRadioButton("");
pasta_carbonara.setOpaque(false);
pasta_carbonara.setForeground(Color.BLACK);
hamburguesa=new JRadioButton("");
hamburguesa.setOpaque(false);
hamburguesa.setForeground(Color.BLACK);


        ImageIcon inicio_sesion=new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");
    botones.add(pabellon);
    botones.add(pasta_carbonara);
    botones.add(pasta_carbonara);
    pabellon.setBounds(100,150,180,30);
 
pasta_carbonara.setBounds(100,300,180,30);
hamburguesa.setBounds(100,450,180,30);
        
    Image cuadrar_imagen=inicio_sesion.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));
        ImageIcon desayuno_cena=new ImageIcon("../../Imagenes/menu_almuerzo.png");
        Image cuadrar_imagen_desayuno_cena= desayuno_cena.getImage().getScaledInstance(465, 500,Image.SCALE_SMOOTH);
JLabel cuadro_desayuno_cena= new JLabel(new ImageIcon(cuadrar_imagen_desayuno_cena));
cuadro_imagen.setBounds(0,0,700,866);
cuadro_desayuno_cena.setBounds(130,70,465,517);
pantalla.add(cuadro_imagen);

cuadro_imagen.add(cuadro_desayuno_cena);

   cuadro_imagen.add(pabellon);
   cuadro_imagen.add(pasta_carbonara);
   cuadro_imagen.add(hamburguesa);
JButton boton_cancelar=new JButton();


boton_cancelar.setBounds(580,5,93,34);
boton_cancelar.setOpaque(false);
boton_cancelar.setContentAreaFilled(false);
boton_cancelar.setFocusPainted(false);






ImageIcon imagen_boton_cancelar=new ImageIcon("../../Imagenes/cancelar.png");

Image cuadrar_imagen_boton_cancelar=imagen_boton_cancelar.getImage().getScaledInstance(93, 34,Image.SCALE_SMOOTH);

JLabel label_boton_cancelar=new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));





JButton confirmar=new JButton();


confirmar.setBounds(260,600,209,72);
confirmar.setOpaque(false);
confirmar.setContentAreaFilled(false);
confirmar.setFocusPainted(false);

ImageIcon imagen_boton_confirmar=new ImageIcon("../../Imagenes/Confirmar.png");

Image cuadrar_imagen_boton_confirmar=imagen_boton_confirmar.getImage().getScaledInstance(209, 72,Image.SCALE_SMOOTH);

JLabel label_boton_confirmar=new JLabel(new ImageIcon(cuadrar_imagen_boton_confirmar));





        pantalla.setFocusable(true);


    
      
    


   pantalla.revalidate();
        pantalla.repaint();

confirmar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Menu Confirmado");
        });
   



label_boton_cancelar.setBounds(580,5,93,34);


label_boton_confirmar.setBounds(260,600,209,72);
cuadro_imagen.add(boton_cancelar);
cuadro_imagen.add(label_boton_cancelar);

cuadro_imagen.add(confirmar);
cuadro_imagen.add(label_boton_confirmar);






    }


    



    public void mostrar() {
         
        menu.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new menu_almuerzo().mostrar());
            }
        }