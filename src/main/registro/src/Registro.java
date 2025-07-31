import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
public class Registro {
    
    private JFrame frame_inicio_de_sesion;
    
    public Registro() {
        initialize();
    }
    
    private void initialize() {
        frame_inicio_de_sesion = new JFrame();
        frame_inicio_de_sesion.setTitle("Registro");
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
JButton boton_cancelar=new JButton();
JButton boton_registrarse=new JButton();
boton_inicio_sesion.setBounds(400,520,163,76);
boton_inicio_sesion.setOpaque(false);
boton_inicio_sesion.setContentAreaFilled(false);
boton_inicio_sesion.setFocusPainted(false);



boton_cancelar.setBounds(580,5,93,34);
boton_cancelar.setOpaque(false);
boton_cancelar.setContentAreaFilled(false);
boton_cancelar.setFocusPainted(false);


boton_registrarse.setBounds(110,520,163,76);
boton_registrarse.setOpaque(false);
boton_registrarse.setContentAreaFilled(false);
boton_registrarse.setFocusPainted(false);

ImageIcon imagen_boton_inicio_sesion=new ImageIcon("src\\main\\Imagenes\\iniciar_sesion.png");
ImageIcon imagen_boton_registrarse=new ImageIcon("src\\main\\Imagenes\\Registrarse.png");
ImageIcon imagen_boton_cancelar=new ImageIcon("src\\main\\Imagenes\\\\cancelar.png");
Image cuadrar_imagen_boton_inicio_sesion=imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);
Image cuadrar_imagen_boton_registrarse=imagen_boton_registrarse.getImage().getScaledInstance(163, 76,Image.SCALE_SMOOTH);

Image cuadrar_imagen_boton_cancelar=imagen_boton_cancelar.getImage().getScaledInstance(93, 34,Image.SCALE_SMOOTH);
JLabel label_boton_inicio=new JLabel(new ImageIcon(cuadrar_imagen_boton_inicio_sesion));
JLabel label_boton_cancelar=new JLabel(new ImageIcon(cuadrar_imagen_boton_cancelar));
JLabel label_boton_registrarse=new JLabel(new ImageIcon(cuadrar_imagen_boton_registrarse));
JTextField ingresar_cedula=new JTextField("INGRESE SU CEDULA");
ingresar_cedula.setBounds(110,220,480,66);
ingresar_cedula.setHorizontalAlignment(JTextField.CENTER);
ingresar_cedula.setFont(new Font("Inter",Font.BOLD,24));
          ingresar_cedula.setForeground(Color.BLACK);
JTextField ingresar_correo=new JTextField("INGRESE SU CORREO ELECTRONICO");
ingresar_correo.setBounds(110,290,480,66);
ingresar_correo.setHorizontalAlignment(JTextField.CENTER);
ingresar_correo.setFont(new Font("Inter",Font.BOLD,24));
         ingresar_correo.setForeground(Color.BLACK);
JTextField ingresar_contrasena=new JTextField("INGRESE SU CONTRASEÑA");
ingresar_contrasena.setBounds(110,360,480,66);
ingresar_contrasena.setHorizontalAlignment(JTextField.CENTER);
ingresar_contrasena.setFont(new Font("Inter",Font.BOLD,24));
         ingresar_contrasena.setForeground(Color.BLACK);

JTextField confirmar_contrasena=new JTextField("CONFIRMAR CONTRASEÑA");
confirmar_contrasena.setBounds(110,430,480,66);
confirmar_contrasena.setHorizontalAlignment(JTextField.CENTER);
confirmar_contrasena.setFont(new Font("Inter",Font.BOLD,24));
         confirmar_contrasena.setForeground(Color.BLACK);


ingresar_cedula.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        if (ingresar_cedula.getText().equals("INGRESE SU CEDULA")) {
            ingresar_cedula.setText("");
            ingresar_cedula.setForeground(Color.BLACK);
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (ingresar_cedula.getText().isEmpty()) {
            ingresar_cedula.setText("INGRESE SU CEDULA");
            ingresar_cedula.setFont(new Font("Inter", Font.BOLD, 24));
        }
    }
});


ingresar_correo.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        if (ingresar_correo.getText().equals("INGRESE SU CORREO ELECTRONICO")) {
            ingresar_correo.setText("");
            ingresar_correo.setForeground(Color.BLACK);
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (ingresar_correo.getText().isEmpty()) {
            ingresar_correo.setText("INGRESE SU CORREO ELECTRONICO");
            ingresar_correo.setFont(new Font("Inter", Font.BOLD, 24));
        }
    }
});

ingresar_contrasena.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        if (ingresar_contrasena.getText().equals("INGRESE SU CONTRASEÑA")) {
            ingresar_contrasena.setText("");
            ingresar_contrasena.setForeground(Color.BLACK);
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (ingresar_contrasena.getText().isEmpty()) {
            ingresar_contrasena.setText("INGRESE SU CONTRASEÑA");
            ingresar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        }
    }
});

confirmar_contrasena.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        if (confirmar_contrasena.getText().equals("CONFIRMAR CONTRASEÑA")) {
            confirmar_contrasena.setText("");
            confirmar_contrasena.setForeground(Color.BLACK);
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (confirmar_contrasena.getText().isEmpty()) {
            confirmar_contrasena.setText("CONFIRMAR CONTRASEÑA");
            confirmar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        }
    }
});



        pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getSource() == pantalla && ingresar_cedula.isFocusOwner()||e.getSource()==pantalla&&ingresar_contrasena.isFocusOwner()||e.getSource()==pantalla&&ingresar_correo.isFocusOwner()||e.getSource()==pantalla&&confirmar_contrasena.isFocusOwner()) {
                


                    pantalla.requestFocusInWindow(); 
                                    
                }
            }
        });

        
        pantalla.setFocusable(true);


        cuadro_imagen.add(ingresar_cedula);
       
cuadro_imagen.add(ingresar_contrasena);
    cuadro_imagen.add(ingresar_correo);  
    cuadro_imagen.add(confirmar_contrasena);


   pantalla.revalidate();
        pantalla.repaint();



ingresar_cedula.setPreferredSize(new Dimension(480,66));
ingresar_cedula.setBorder(BorderFactory.createLineBorder(Color.WHITE));

label_boton_inicio.setBounds(400,520,163,76);
label_boton_registrarse.setBounds(110,520,163,76);
label_boton_cancelar.setBounds(580,5,93,34);


cuadro_imagen.add(boton_inicio_sesion);
cuadro_imagen.add(boton_registrarse);
cuadro_imagen.add(label_boton_registrarse);
      cuadro_imagen.add(label_boton_inicio);

cuadro_imagen.add(boton_cancelar);
cuadro_imagen.add(label_boton_cancelar);
cuadro_imagen.add(ingresar_cedula);


boton_registrarse.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Registro Exitoso");
        });

    
boton_inicio_sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton_inicio.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(168, 81, Image.SCALE_SMOOTH)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_boton_inicio.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
            }
        });




    }


    



    public void mostrar() {
         
        frame_inicio_de_sesion.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new Registro().mostrar());
            }
        }

