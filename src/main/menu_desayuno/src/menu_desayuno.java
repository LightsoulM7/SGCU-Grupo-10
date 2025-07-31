import javax.swing.*;



import java.awt.*;
public class menu_desayuno{
    
    private JFrame menu;
    public menu_desayuno() {
        initialize();
    }
    
    private void initialize() {
        menu = new JFrame();
        menu.setTitle(" Menu Desayuno");
        menu.setSize(700, 866);
    menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);
    menu.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        menu.add(pantalla);
        


        ImageIcon fondo=new ImageIcon("src\\main\\Imagenes\\cuadro_azul_ucv.png");


        
    Image cuadrar_imagen=fondo.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));
        ImageIcon desayuno_cena=new ImageIcon("src\\main\\Imagenes\\menu_desayuno.png");
        Image cuadrar_imagen_desayuno= desayuno_cena.getImage().getScaledInstance(465, 517,Image.SCALE_SMOOTH);
JLabel cuadro_desayuno= new JLabel(new ImageIcon(cuadrar_imagen_desayuno));
cuadro_imagen.setBounds(0,0,700,866);
cuadro_desayuno.setBounds(130,150,465,517);
pantalla.add(cuadro_imagen);

cuadro_imagen.add(cuadro_desayuno);
JButton boton_cancelar=new JButton();


boton_cancelar.setBounds(580,5,93,34);
boton_cancelar.setOpaque(false);
boton_cancelar.setContentAreaFilled(false);
boton_cancelar.setFocusPainted(false);






ImageIcon imagen_boton_cancelar=new ImageIcon("src\\main\\Imagenes\\cancelar.png");

Image cuadrar_imagen_boton_cancelar=imagen_boton_cancelar.getImage().getScaledInstance(93, 34,Image.SCALE_SMOOTH);

JLabel label_boton_cancelar=new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));



        pantalla.setFocusable(true);


    
      
    


   pantalla.revalidate();
        pantalla.repaint();





label_boton_cancelar.setBounds(580,5,93,34);

cuadro_imagen.add(boton_cancelar);
cuadro_imagen.add(label_boton_cancelar);



    }


    



    public void mostrar() {
         
        menu.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new menu_desayuno().mostrar());
            }
        }

