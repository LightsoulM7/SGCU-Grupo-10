import javax.swing.*;



import java.awt.*;
public class menu_almuerzo{
    
    private JFrame menu;
    public menu_almuerzo() {
        initialize();
    }
    
    private void initialize() {
        menu = new JFrame();
        menu.setTitle(" Menu Almuerzo");
        menu.setSize(700, 866);
    menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);
    menu.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        menu.add(pantalla);
        


        ImageIcon fondo=new ImageIcon("../../Imagenes/cuadro_azul_ucv.png");


        
    Image cuadrar_imagen=fondo.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));
        ImageIcon desayuno_cena=new ImageIcon("../../Imagenes/menu_almuerzo.png");
        Image cuadrar_imagen_desayuno= desayuno_cena.getImage().getScaledInstance(465, 517,Image.SCALE_SMOOTH);
JLabel cuadro_desayuno= new JLabel(new ImageIcon(cuadrar_imagen_desayuno));
cuadro_imagen.setBounds(0,0,700,866);
cuadro_desayuno.setBounds(130,150,465,517);
pantalla.add(cuadro_imagen);

cuadro_imagen.add(cuadro_desayuno);

JButton btnAtras = new JButton("Atrás");
btnAtras.setBounds(50, 50, 100, 40);
cuadro_imagen.add(btnAtras);

btnAtras.addActionListener(e -> {
    menu.dispose();
    MenuSelectionScreen menuSelectionScreen = new MenuSelectionScreen();
    menuSelectionScreen.mostrar();
});



        pantalla.setFocusable(true);


    
      
    


   pantalla.revalidate();
        pantalla.repaint();



    }


    



    public void mostrar() {
         
        menu.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new menu_almuerzo().mostrar());
            }
        }

