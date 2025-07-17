import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class gestion_insumos {
    
    private JFrame frame_gestion_insumos;
    
    public gestion_insumos() {
        initialize();
    }
    
    private void initialize() {
        frame_gestion_insumos = new JFrame();
        frame_gestion_insumos.setTitle("Gestion de Insumos");
        frame_gestion_insumos.setSize(700, 866);
    frame_gestion_insumos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_gestion_insumos.setLocationRelativeTo(null);
        frame_gestion_insumos.setResizable(false);
        
       
JPanel pantalla = new JPanel(new BorderLayout());


        frame_gestion_insumos.add(pantalla);
        
        ImageIcon gestion_insumos=new ImageIcon("src\\main\\Imagenes\\cuadro_azul_ucv.png");
    


        
    Image cuadrar_imagen=gestion_insumos.getImage().getScaledInstance(700, 866,Image.SCALE_SMOOTH);
JLabel cuadro_imagen=new JLabel(new ImageIcon(cuadrar_imagen));

cuadro_imagen.setBounds(0,0,700,866);
pantalla.add(cuadro_imagen);

JButton registrar_insumo=new JButton();


registrar_insumo.setBounds(210,550,264,76);

registrar_insumo.setOpaque(false);
registrar_insumo.setContentAreaFilled(false);
registrar_insumo.setFocusPainted(false);
JButton seleccionar_insumo=new JButton();
seleccionar_insumo.setBounds(200,220,311,76);
seleccionar_insumo.setContentAreaFilled(false);
seleccionar_insumo.setFocusPainted(false);

ImageIcon imagen_boton_registrar_insumo=new ImageIcon("src\\main\\Imagenes\\\\Registrar_insumo.png");
Image cuadrar_imagen_boton_registrar_insumo=imagen_boton_registrar_insumo.getImage().getScaledInstance(264, 76,Image.SCALE_SMOOTH);
JLabel label_boton_registrar_insumo=new JLabel(new ImageIcon(cuadrar_imagen_boton_registrar_insumo));

label_boton_registrar_insumo.setBounds(210,550,264,76);

ImageIcon imagen_seleccionar_insumo=new ImageIcon("src\\main\\Imagenes\\barra_despegable_insumos.png");
Image cuadrar_imagen_seleccionar_insumo=imagen_seleccionar_insumo.getImage().getScaledInstance(311, 76,Image.SCALE_SMOOTH);
JLabel label_seleccionar_insumo=new JLabel(new ImageIcon(cuadrar_imagen_seleccionar_insumo));
label_seleccionar_insumo.setBounds(200,220,311,76);

cuadro_imagen.add(registrar_insumo);
cuadro_imagen.add(label_boton_registrar_insumo);

String insumos[]={"Seleccione un insumo","Proteínas","Lipidos","Carbohidratos","Productos de limpieza"};
JComboBox<String>insumo_nutrientes=new JComboBox<>(insumos);
insumo_nutrientes.setEditable(false);
insumo_nutrientes.setBounds(128,220,450,70);

Font letras_nutrientes=new Font("Arial",Font.BOLD,22);
insumo_nutrientes.setForeground(Color.BLACK);
insumo_nutrientes.setFont(letras_nutrientes);
insumo_nutrientes.setBackground(Color.WHITE);

JTextField cantidad=new JTextField("Cantidad");
cantidad.setBounds(110,300,480,66);
cantidad.setHorizontalAlignment(JTextField.CENTER);
cantidad.setFont(new Font("Inter",Font.BOLD,24));
JTextField nombre=new JTextField("Nombre");
nombre.setBounds(110,370,480,66);
nombre.setHorizontalAlignment(JTextField.CENTER);
nombre.setFont(new Font("Inter",Font.BOLD,24));
JTextField precio=new JTextField("Precio");
precio.setBounds(110,440,480,66);
precio.setHorizontalAlignment(JTextField.CENTER);
precio.setFont(new Font("Inter",Font.BOLD,24));

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

    
cantidad.addFocusListener(new FocusAdapter() {
     private boolean isPlaceholderActive = true;
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholderActive||cantidad.getText().equals("Cantidad")) {
            cantidad.setText("");
            cantidad.setForeground(Color.BLACK);
           
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (isPlaceholderActive||cantidad.getText().isEmpty()) {
            cantidad.setText("Cantidad");
            cantidad.setFont(new Font("Inter",Font.BOLD,24));
             isPlaceholderActive = true;
        }else{
    cantidad.setFont(new Font("Inter",Font.BOLD,24));
      isPlaceholderActive=false;
        }
    }
});

nombre.addFocusListener(new FocusAdapter() {
     private boolean isPlaceholderActive = true;
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholderActive||nombre.getText().equals("Nombre")) {
            nombre.setText("");
            nombre.setForeground(Color.BLACK);
           
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (isPlaceholderActive||nombre.getText().isEmpty()) {
            nombre.setText("Nombre");
            nombre.setFont(new Font("Inter",Font.BOLD,24));
             isPlaceholderActive = true;
        }else{
      nombre.setFont(new Font("Inter",Font.BOLD,24));
      isPlaceholderActive=false;
        }
    }
}); 

precio.addFocusListener(new FocusAdapter() {
     private boolean isPlaceholderActive = true;
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholderActive||precio.getText().equals("Precio")) {
            precio.setText("");
            precio.setForeground(Color.BLACK);
           
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        if (isPlaceholderActive||precio.getText().isEmpty()) {
            precio.setText("Precio");
    precio.setFont(new Font("Inter",Font.BOLD,24));
             isPlaceholderActive = true;
        }else{
      precio.setFont(new Font("Inter",Font.BOLD,24));
      isPlaceholderActive=false;
        }
    }
}); 
 pantalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getSource() == pantalla && cantidad.isFocusOwner()||e.getSource()==pantalla&&nombre.isFocusOwner()||e.getSource()==pantalla&&precio.isFocusOwner()) {
                    cantidad.transferFocusBackward(); 
                            nombre.transferFocusBackward();                           
        precio.transferFocus();
                    pantalla.requestFocusInWindow(); 
                                    
                }
            }
        });

    
    registrar_insumo.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Insumo Registrado");
        });
   
        pantalla.setFocusable(true);


  
    
    

   pantalla.revalidate();
        pantalla.repaint();

    
registrar_insumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_boton_registrar_insumo.setIcon(new ImageIcon(imagen_boton_registrar_insumo.getImage().getScaledInstance(264, 76, Image.SCALE_SMOOTH)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            label_boton_registrar_insumo.setIcon(new ImageIcon(imagen_boton_registrar_insumo.getImage().getScaledInstance(264, 76, Image.SCALE_SMOOTH)));
            }
        });
      
cuadro_imagen.add(insumo_nutrientes);
cuadro_imagen.add(cantidad);
cuadro_imagen.add(nombre);
cuadro_imagen.add(precio);


    }





    public void mostrar() {
         
        frame_gestion_insumos.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new gestion_insumos().mostrar());
            }
        }

