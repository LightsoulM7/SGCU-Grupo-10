import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
public class inicio_de_sesion {
    
    private JFrame frame_inicio_de_sesion;
    
    public inicio_de_sesion() {
        initialize();
    }
    
    private void initialize() {
        frame_inicio_de_sesion = new JFrame();
        frame_inicio_de_sesion.setTitle("Inicio de sesión");
        frame_inicio_de_sesion.setSize(700, 866);
    frame_inicio_de_sesion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_inicio_de_sesion.setLocationRelativeTo(null);
        frame_inicio_de_sesion.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        frame_inicio_de_sesion.add(pantalla);
        
        ImageIcon inicio_sesion=new ImageIcon("src\\main\\Imagenes\\cuadro_ucv.png");
    


        
    Image cuadrar_imagen=inicio_sesion.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));

cuadro_imagen.setBounds(0,0,700,866);
pantalla.add(cuadro_imagen);

JButton boton_inicio_sesion=new JButton();
JButton boton_olvido_clave=new JButton();
JButton boton_cancelar=new JButton();

boton_inicio_sesion.setBounds(100,450,163,76);
boton_inicio_sesion.setOpaque(false);
boton_inicio_sesion.setContentAreaFilled(false);
boton_inicio_sesion.setFocusPainted(false);

boton_olvido_clave.setBounds(430,450,163,76);
boton_olvido_clave.setOpaque(false);
boton_olvido_clave.setContentAreaFilled(false);
boton_olvido_clave.setFocusPainted(false);

boton_cancelar.setBounds(580,5,93,34);
boton_cancelar.setOpaque(false);
boton_cancelar.setContentAreaFilled(false);
boton_cancelar.setFocusPainted(false);

ImageIcon imagen_boton_inicio_sesion=new ImageIcon("src\\main\\Imagenes\\iniciar_sesion.png");
ImageIcon imagen_boton_olvido_clave=new ImageIcon("src\\main\\Imagenes\\\\olvido_clave.png");
ImageIcon imagen_boton_cancelar=new ImageIcon("src\\main\\Imagenes\\\\cancelar.png");
Image cuadrar_imagen_boton_inicio_sesion=imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);
Image cuadrar_imagen_boton_olvido_clave=imagen_boton_olvido_clave.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);
Image cuadrar_imagen_boton_cancelar=imagen_boton_cancelar.getImage().getScaledInstance(93, 34,Image.SCALE_SMOOTH);
JLabel label_boton=new JLabel(new ImageIcon(cuadrar_imagen_boton_inicio_sesion));
JLabel label_boton_olvido_clave=new JLabel(new ImageIcon(cuadrar_imagen_boton_olvido_clave));
JLabel label_boton_cancelar=new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));

JTextField ingresar_cedula=new JTextField("INGRESE SU CEDULA");
ingresar_cedula.setBounds(110,300,480,66);
ingresar_cedula.setHorizontalAlignment(JTextField.CENTER);
ingresar_cedula.setFont(new Font("Inter",Font.BOLD,24));
JTextField ingresar_contraseña=new JTextField("INGRESE SU CONTRASEÑA");
ingresar_contraseña.setBounds(110,370,480,66);
ingresar_contraseña.setHorizontalAlignment(JTextField.CENTER);
ingresar_contraseña.setFont(new Font("Inter",Font.BOLD,24));
ingresar_cedula.addFocusListener(new FocusAdapter() {
     private boolean isPlaceholderActive = true;
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholderActive||ingresar_cedula.getText().equals("INGRESE SU CEDULA")) {
            ingresar_cedula.setText("");
            ingresar_cedula.setForeground(Color.BLACK);
           
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (isPlaceholderActive||ingresar_cedula.getText().isEmpty()) {
            ingresar_cedula.setText("INGRESE SU CEDULA");
            ingresar_cedula.setFont(new Font("Inter",Font.BOLD,24));
             isPlaceholderActive = true;
        }else{
      ingresar_cedula.setFont(new Font("Inter",Font.BOLD,24));
      isPlaceholderActive=false;
        }
    }
});

ingresar_contraseña.addFocusListener(new FocusAdapter() {
     private boolean isPlaceholderActive = true;
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholderActive||ingresar_cedula.getText().equals("INGRESE SU CONTRASEÑA")) {
            ingresar_contraseña.setText("");
            ingresar_contraseña.setForeground(Color.BLACK);
           
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (isPlaceholderActive||ingresar_cedula.getText().isEmpty()) {
            ingresar_contraseña.setText("INGRESE SU CONTRASEÑA");
            ingresar_contraseña.setFont(new Font("Inter",Font.BOLD,24));
             isPlaceholderActive = true;
        }else{
      ingresar_cedula.setFont(new Font("Inter",Font.BOLD,24));
      isPlaceholderActive=false;
        }
    }
}); 



        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getSource() == pantalla && ingresar_cedula.isFocusOwner()||e.getSource()==pantalla&&ingresar_contraseña.isFocusOwner()) {
                    ingresar_cedula.transferFocusBackward(); 
                            ingresar_contraseña.transferFocusBackward();                           
        
                    pantalla.requestFocusInWindow(); 
                                    
                }
            }
        });

        
        pantalla.setFocusable(true);


        cuadro_imagen.add(ingresar_cedula);
       
cuadro_imagen.add(ingresar_contraseña);
      
    


   pantalla.revalidate();
        pantalla.repaint();



ingresar_cedula.setPreferredSize(new Dimension(480,66));
ingresar_cedula.setBorder(BorderFactory.createLineBorder(Color.WHITE));

label_boton.setBounds(100,450,163,76);
label_boton_olvido_clave.setBounds(430,450,163,76);
label_boton_cancelar.setBounds(580,5,93,34);


cuadro_imagen.add(boton_inicio_sesion);
      cuadro_imagen.add(label_boton);
      cuadro_imagen.add(boton_olvido_clave);
cuadro_imagen.add(label_boton_olvido_clave);
cuadro_imagen.add(boton_cancelar);
cuadro_imagen.add(label_boton_cancelar);
cuadro_imagen.add(ingresar_cedula);


    
boton_inicio_sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(168, 81, Image.SCALE_SMOOTH)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
            }
        });




    }


    



    public void mostrar() {
         
        frame_inicio_de_sesion.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new inicio_de_sesion().mostrar());
            }
        }

