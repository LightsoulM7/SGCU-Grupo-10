import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream; // Importación para cargar recursos desde el JAR
import java.io.InputStreamReader; // Importación para leer streams
import java.net.URL; // Importación para obtener URLs de recursos

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
        frame_inicio_de_sesion.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame_inicio_de_sesion.setResizable(false); // Evita que la ventana sea redimensionable

        // Usamos JLayeredPane para superponer componentes.
        // Esto es crucial porque permite que los campos de texto, botones, etc.,
        // se muestren *encima* de la imagen de fondo.
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(700, 866));
        frame_inicio_de_sesion.add(layeredPane, BorderLayout.CENTER);

        // --- Carga de la imagen de fondo ---
        // Se recomienda usar `getClass().getResource()` para cargar imágenes y otros recursos.
        // Esto asegura que la aplicación pueda encontrar los archivos incluso cuando están
        // empaquetados dentro de un archivo JAR.
        // La ruta debe ser relativa a la raíz del classpath (ej., si 'Imagenes' está en 'src/main/resources').
        ImageIcon inicio_sesion = loadImageIcon("/Imagenes/cuadro_ucv.png");
        JLabel cuadro_imagen;

        if (inicio_sesion == null) {
            System.err.println("Error: No se pudo cargar la imagen de fondo: /Imagenes/cuadro_ucv.png");
            // Si la imagen no se encuentra, usamos una etiqueta de respaldo.
            JLabel fallbackLabel = new JLabel("Imagen de Fondo No Encontrada");
            fallbackLabel.setBounds(0, 0, 700, 866);
            fallbackLabel.setOpaque(true);
            fallbackLabel.setBackground(Color.LIGHT_GRAY);
            fallbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cuadro_imagen = fallbackLabel;
        } else {
            Image cuadrar_imagen = inicio_sesion.getImage().getScaledInstance(700, 866, Image.SCALE_SMOOTH);
            cuadro_imagen = new JLabel(new ImageIcon(cuadrar_imagen));
        }
        cuadro_imagen.setBounds(0, 0, 700, 866);
        // Añade la imagen al layeredPane en la capa por defecto (fondo)
        layeredPane.add(cuadro_imagen, JLayeredPane.DEFAULT_LAYER);

        // --- Campos de texto y contraseña ---
        JTextField ingresar_cedula = new JTextField("INGRESE SU CEDULA");
        ingresar_cedula.setBounds(110, 300, 480, 66);
        ingresar_cedula.setHorizontalAlignment(JTextField.CENTER);
        ingresar_cedula.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_cedula.setForeground(Color.GRAY);
        ingresar_cedula.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        // Añade el campo de texto a una capa superior para que sea visible
        layeredPane.add(ingresar_cedula, JLayeredPane.PALETTE_LAYER);

        JPasswordField ingresar_contrasena = new JPasswordField();
        final String placeholder_contrasena = "INGRESE SU CONTRASEÑA";
        ingresar_contrasena.setBounds(110, 370, 480, 66);
        ingresar_contrasena.setHorizontalAlignment(JTextField.CENTER);
        ingresar_contrasena.setFont(new Font("Inter", Font.BOLD, 24));
        ingresar_contrasena.setText(placeholder_contrasena);
        ingresar_contrasena.setEchoChar((char) 0); // Muestra el texto del placeholder
        ingresar_contrasena.setForeground(Color.GRAY);
        ingresar_contrasena.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        // Añade el campo de contraseña a una capa superior
        layeredPane.add(ingresar_contrasena, JLayeredPane.PALETTE_LAYER);

        // --- Checkbox para mostrar/ocultar contraseña ---
        JCheckBox mostrar_contrasena = new JCheckBox("Mostrar Contraseña");
        mostrar_contrasena.setBounds(110, 440, 200, 30);
        mostrar_contrasena.setFont(new Font("Inter", Font.BOLD, 14));
        mostrar_contrasena.setForeground(Color.WHITE);
        mostrar_contrasena.setOpaque(false); // Hace el fondo del checkbox transparente
        layeredPane.add(mostrar_contrasena, JLayeredPane.PALETTE_LAYER);

        // --- Botones invisibles para la lógica de clic ---
        // Estos botones son funcionales pero no visibles; las etiquetas con imágenes los cubren.
        JButton boton_inicio_sesion = new JButton();
        boton_inicio_sesion.setBounds(100, 500, 163, 76);
        boton_inicio_sesion.setOpaque(false);
        boton_inicio_sesion.setContentAreaFilled(false);
        boton_inicio_sesion.setFocusPainted(false);
        layeredPane.add(boton_inicio_sesion, JLayeredPane.PALETTE_LAYER);

        JButton boton_olvido_clave = new JButton();
        boton_olvido_clave.setBounds(430, 500, 163, 76);
        boton_olvido_clave.setOpaque(false);
        boton_olvido_clave.setContentAreaFilled(false);
        boton_olvido_clave.setFocusPainted(false);
        layeredPane.add(boton_olvido_clave, JLayeredPane.PALETTE_LAYER);

        // --- Etiquetas con imágenes de los botones (visibles) ---
        ImageIcon imagen_boton_inicio_sesion = loadImageIcon("/Imagenes/iniciar_sesion.png");
        ImageIcon imagen_boton_olvido_clave = loadImageIcon("/Imagenes/olvido_clave.png");

        JLabel label_boton = new JLabel();
        if (imagen_boton_inicio_sesion != null) {
            label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
        } else {
            System.err.println("Error: No se pudo cargar la imagen del botón de inicio de sesión.");
            label_boton.setText("Iniciar Sesión"); // Texto de respaldo
            label_boton.setForeground(Color.WHITE);
        }
        label_boton.setBounds(100, 500, 163, 76);
        layeredPane.add(label_boton, JLayeredPane.PALETTE_LAYER);

        JLabel label_boton_olvido_clave = new JLabel();
        if (imagen_boton_olvido_clave != null) {
            label_boton_olvido_clave.setIcon(new ImageIcon(imagen_boton_olvido_clave.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
        } else {
            System.err.println("Error: No se pudo cargar la imagen del botón de olvido de clave.");
            label_boton_olvido_clave.setText("Olvidó Clave"); // Texto de respaldo
            label_boton_olvido_clave.setForeground(Color.WHITE);
        }
        label_boton_olvido_clave.setBounds(430, 500, 163, 76);
        layeredPane.add(label_boton_olvido_clave, JLayeredPane.PALETTE_LAYER);


        // --- Listeners de Foco para los campos de texto ---
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
                    ingresar_cedula.setForeground(Color.GRAY);
                }
            }
        });

        ingresar_contrasena.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(ingresar_contrasena.getPassword()).equals(placeholder_contrasena)) {
                    ingresar_contrasena.setText("");
                    // Si no está marcada la opción de "Mostrar Contraseña", activamos el eco de caracteres
                    if (!mostrar_contrasena.isSelected()) {
                        ingresar_contrasena.setEchoChar('*');
                    }
                    ingresar_contrasena.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(ingresar_contrasena.getPassword()).isEmpty()) {
                    ingresar_contrasena.setText(placeholder_contrasena);
                    ingresar_contrasena.setEchoChar((char) 0); // Vuelve a mostrar el texto del placeholder
                    ingresar_contrasena.setForeground(Color.GRAY);
                }
            }
        });

        // Listener para el checkbox de mostrar contraseña
        mostrar_contrasena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Solo cambia el echoChar si el campo no contiene el placeholder
                if (!String.valueOf(ingresar_contrasena.getPassword()).equals(placeholder_contrasena)) {
                    if (mostrar_contrasena.isSelected()) {
                        ingresar_contrasena.setEchoChar((char) 0); // Muestra los caracteres
                    } else {
                        ingresar_contrasena.setEchoChar('*'); // Oculta los caracteres
                    }
                }
            }
        });

        // --- Listener de Ratón para deseleccionar campos al hacer clic fuera ---
        layeredPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic en cualquier parte del layeredPane, quitamos el foco de los campos
                layeredPane.requestFocusInWindow();
            }
        });
        layeredPane.setFocusable(true); // Hace que el panel sea enfocable

        // --- Action Listeners para los botones ---
        boton_inicio_sesion.addActionListener(e -> {
            String cedula = ingresar_cedula.getText();
            String contrasena = new String(ingresar_contrasena.getPassword());

            // Validar que la cédula no sea el placeholder
            if (cedula.equals("INGRESE SU CEDULA")) {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Por favor ingrese su cédula.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                return; // Sale del método si la cédula es el placeholder
            }

            try {
                Integer.parseInt(cedula); // Confirma si la cédula es un número
                String role = validarCredenciales(cedula, contrasena);

                if (role != null) {
                    if (role.equals("admin")) {
                        JOptionPane.showMessageDialog(frame_inicio_de_sesion, "¡Inicio de sesión de Administrador exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                        frame_inicio_de_sesion.dispose(); // Cierra la ventana de inicio de sesión
                        // Aquí deberías crear e iniciar tu menú de administrador
                        // AdminMenu adminMenu = new AdminMenu();
                        // adminMenu.mostrar();
                    } else {
                        JOptionPane.showMessageDialog(frame_inicio_de_sesion, "¡Inicio de sesión exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                        frame_inicio_de_sesion.dispose(); // Cierra la ventana de inicio de sesión
                        // Aquí deberías crear e iniciar tu menú principal de usuario
                        // MenuPrincipal menuPrincipal = new MenuPrincipal();
                        // menuPrincipal.mostrar();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Cédula o contraseña incorrecta.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame_inicio_de_sesion, "La cédula debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        boton_olvido_clave.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame_inicio_de_sesion, "Funcionalidad de recuperación de contraseña no implementada.", "Recuperar Contraseña", JOptionPane.INFORMATION_MESSAGE);
            frame_inicio_de_sesion.dispose(); // Cierra la ventana actual
            // Aquí deberías crear e iniciar tu ventana de recuperación de contraseña
            // recuperar_contrasena recuperarContrasena = new recuperar_contrasena();
            // recuperarContrasena.mostrar();
        });

        // --- Listener de Ratón para el efecto hover del botón de inicio de sesión ---
        if (imagen_boton_inicio_sesion != null) { // Solo si la imagen se cargó correctamente
            boton_inicio_sesion.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(168, 81, Image.SCALE_SMOOTH)));
                }

                public void mouseExited(MouseEvent evt) {
                    label_boton.setIcon(new ImageIcon(imagen_boton_inicio_sesion.getImage().getScaledInstance(163, 76, Image.SCALE_SMOOTH)));
                }
            });
        }
        // Puedes añadir un listener similar para boton_olvido_clave si tienes una imagen de hover para él.
    }

    /**
     * Método auxiliar para cargar ImageIcons desde el classpath.
     * Esta es la forma recomendada para que las imágenes se carguen correctamente
     * cuando la aplicación está empaquetada en un archivo JAR.
     * @param path La ruta al recurso, debe comenzar con '/' si está en la raíz del classpath.
     * @return ImageIcon si se encuentra, o null si no se puede cargar.
     */
    private ImageIcon loadImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se pudo encontrar el archivo: " + path);
            return null;
        }
    }

    private String validarCredenciales(String cedula, String contrasena) {
        // La ruta del archivo ahora debe ser relativa al classpath.
        // Si 'usuarios.txt' está dentro de una carpeta 'db' en tus recursos,
        // la ruta sería "/db/usuarios.txt".
        String rutaArchivo = "/db/usuarios.txt";
        // Usamos try-with-resources para asegurar que el BufferedReader se cierre automáticamente
        try (InputStream is = getClass().getResourceAsStream(rutaArchivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) { // Si el InputStream es null, el archivo no se encontró en el classpath
                System.err.println("Error: No se pudo encontrar el archivo de usuarios en el classpath: " + rutaArchivo);
                return null;
            }

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Asegúrate de que la línea tiene el formato esperado (cédula,contraseña,rol)
                if (datos.length == 3) {
                    String cedulaArchivo = datos[0].trim();
                    String contrasenaArchivo = datos[1].trim();
                    String rolArchivo = datos[2].trim();

                    if (cedulaArchivo.equals(cedula) && contrasenaArchivo.equals(contrasena)) {
                        return rolArchivo;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo de usuarios: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            // Esto puede ocurrir si is es null y no se maneja antes del BufferedReader
            System.err.println("Error: El archivo de usuarios es nulo, posiblemente no se encontró: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void mostrar() {
        frame_inicio_de_sesion.setVisible(true);
    }

    public static void main(String[] args) {
        // Asegura que la interfaz de usuario se cree y actualice en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new inicio_de_sesion().mostrar());
    }
}