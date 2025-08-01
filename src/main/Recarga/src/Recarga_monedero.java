import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Recarga_monedero {
    
    private JFrame frame;
    private JComboBox<String> metodoPagoCombo;
    private JPanel panelPagoMovil;
    private JTextField referenciaField, bancoField, fechaField;
    private final Calendar maxDate = Calendar.getInstance();
    
    public Recarga_monedero() {
        maxDate.set(2025, Calendar.AUGUST, 31);
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Recarga de Monedero");
        frame.setSize(700, 866);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        JPanel pantalla = new JPanel(new BorderLayout());
        frame.add(pantalla);
        
        ImageIcon fondoIcon = new ImageIcon("src\\main\\Imagenes\\cuadro_ucv.png");
        Image imagenFondo = fondoIcon.getImage().getScaledInstance(700, 866, Image.SCALE_SMOOTH);
        JLabel cuadro_imagen = new JLabel(new ImageIcon(imagenFondo));
        cuadro_imagen.setBounds(0, 0, 700, 866);
        pantalla.add(cuadro_imagen);
        
        String[] metodosPago = {"Seleccione el tipo de recarga", "Pago Móvil"};
        metodoPagoCombo = new JComboBox<>(metodosPago);
        metodoPagoCombo.setBounds(150, 100, 400, 50);
        metodoPagoCombo.setFont(new Font("Arial", Font.BOLD, 20));
        metodoPagoCombo.setBackground(Color.WHITE);
        
        panelPagoMovil = new JPanel();
        panelPagoMovil.setBounds(100, 180, 500, 350);
        panelPagoMovil.setLayout(new GridLayout(7, 1, 10, 10));
        panelPagoMovil.setOpaque(false);
        panelPagoMovil.setVisible(false);
        
        JLabel lblDatos = new JLabel("Datos del Beneficiario:", SwingConstants.CENTER);
        lblDatos.setFont(new Font("Arial", Font.BOLD, 20));
        lblDatos.setForeground(Color.WHITE);
        
        JLabel lblCedula = new JLabel("Cédula: 17.888.999", SwingConstants.CENTER);
        lblCedula.setFont(new Font("Arial", Font.PLAIN, 18));
        lblCedula.setForeground(Color.WHITE);
        
        JLabel lblNumero = new JLabel("Teléfono: 0412-556-6789", SwingConstants.CENTER);
        lblNumero.setFont(new Font("Arial", Font.PLAIN, 18));
        lblNumero.setForeground(Color.WHITE);
        
        JLabel lblBanco = new JLabel("Banco: Venezuela", SwingConstants.CENTER);
        lblBanco.setFont(new Font("Arial", Font.PLAIN, 18));
        lblBanco.setForeground(Color.WHITE);
        
        JLabel lblValidacion = new JLabel("Validar Pago:", SwingConstants.CENTER);
        lblValidacion.setFont(new Font("Arial", Font.BOLD, 20));
        lblValidacion.setForeground(Color.WHITE);
        
        referenciaField = new JTextField("Número de referencia");
        estiloTextField(referenciaField, true);
        
        bancoField = new JTextField("Banco");
        estiloTextField(bancoField, false);
        
        JPanel fechaPanel = new JPanel(new BorderLayout());
        fechaPanel.setOpaque(false);
        
        fechaField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        estiloTextField(fechaField, false);
        fechaField.setEditable(false);
        
        JButton btnCalendario = new JButton("▼");
        btnCalendario.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalendario.setPreferredSize(new Dimension(50, 30));
        btnCalendario.setContentAreaFilled(true);
        btnCalendario.setBackground(new Color(70, 130, 180));
        btnCalendario.setForeground(Color.WHITE);
        btnCalendario.setBorder(BorderFactory.createRaisedBevelBorder());
        btnCalendario.setFocusPainted(false);
        btnCalendario.addActionListener(e -> mostrarCalendario());
        
        fechaPanel.add(fechaField, BorderLayout.CENTER);
        fechaPanel.add(btnCalendario, BorderLayout.EAST);
        
        panelPagoMovil.add(lblDatos);
        panelPagoMovil.add(lblCedula);
        panelPagoMovil.add(lblNumero);
        panelPagoMovil.add(lblBanco);
        panelPagoMovil.add(lblValidacion);
        panelPagoMovil.add(referenciaField);
        panelPagoMovil.add(bancoField);
        panelPagoMovil.add(fechaPanel);
        
        metodoPagoCombo.addActionListener(e -> {
            if(metodoPagoCombo.getSelectedItem().equals("Pago Móvil")) {
                panelPagoMovil.setVisible(true);
            } else {
                panelPagoMovil.setVisible(false);
            }
        });
        
        JButton btnRecargar = new JButton();
        btnRecargar.setBounds(200, 400, 262, 76);
        btnRecargar.setOpaque(false);
        btnRecargar.setContentAreaFilled(false);
        btnRecargar.setBorderPainted(false);
        
        JButton btnCancelar = new JButton();
        btnCancelar.setBounds(580, 5, 93, 34);
        btnCancelar.setOpaque(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setBorderPainted(false);
        
        ImageIcon iconRecargar = new ImageIcon("src\\main\\Imagenes\\recarga_completa.png");
        ImageIcon iconCancelar = new ImageIcon("src\\main\\Imagenes\\cancelar.png");
        
        Image imgRecargar = iconRecargar.getImage().getScaledInstance(262, 76, Image.SCALE_SMOOTH);
        Image imgCancelar = iconCancelar.getImage().getScaledInstance(93, 34, Image.SCALE_SMOOTH);
        
        JLabel lblRecargar = new JLabel(new ImageIcon(imgRecargar));
        lblRecargar.setBounds(200, 400, 262, 76);
        
        JLabel lblCancelar = new JLabel(new ImageIcon(imgCancelar));
        lblCancelar.setBounds(580, 5, 93, 34);
        
        btnRecargar.addActionListener(e -> validarPago());
        btnCancelar.addActionListener(e -> frame.dispose());
        
        btnRecargar.addMouseListener(crearHoverEffect(lblRecargar, iconRecargar, 262, 76));
        btnCancelar.addMouseListener(crearHoverEffect(lblCancelar, iconCancelar, 93, 34));
        
        cuadro_imagen.add(metodoPagoCombo);
        cuadro_imagen.add(panelPagoMovil);
        cuadro_imagen.add(btnRecargar);
        cuadro_imagen.add(btnCancelar);
        cuadro_imagen.add(lblRecargar);
        cuadro_imagen.add(lblCancelar);
        
        pantalla.revalidate();
        pantalla.repaint();
    }
    
    private void mostrarCalendario() {
        JDialog dialog = new JDialog(frame, "Seleccionar Fecha (Hasta agosto 2025)", true);
        dialog.setSize(350, 330);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(Color.WHITE);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel monthPanel = new JPanel(new BorderLayout());
        monthPanel.setBackground(new Color(70, 130, 180));
        monthPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton prevMonth = new JButton("◄");
        prevMonth.setFont(new Font("Arial", Font.BOLD, 16));
        prevMonth.setForeground(Color.WHITE);
        prevMonth.setBackground(new Color(50, 100, 150));
        prevMonth.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        prevMonth.setFocusPainted(false);

        JButton nextMonth = new JButton("►");
        nextMonth.setFont(new Font("Arial", Font.BOLD, 16));
        nextMonth.setForeground(Color.WHITE);
        nextMonth.setBackground(new Color(50, 100, 150));
        nextMonth.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        nextMonth.setFocusPainted(false);

        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        monthLabel.setForeground(Color.WHITE);

        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setOpaque(false);
        navPanel.add(prevMonth, BorderLayout.WEST);
        navPanel.add(monthLabel, BorderLayout.CENTER);
        navPanel.add(nextMonth, BorderLayout.EAST);

        monthPanel.add(navPanel, BorderLayout.CENTER);

        prevMonth.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                prevMonth.setBackground(new Color(30, 80, 130));
            }
            public void mouseExited(MouseEvent evt) {
                prevMonth.setBackground(new Color(50, 100, 150));
            }
        });

        nextMonth.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                nextMonth.setBackground(new Color(30, 80, 130));
            }
            public void mouseExited(MouseEvent evt) {
                nextMonth.setBackground(new Color(50, 100, 150));
            }
        });

        JPanel daysPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        daysPanel.setBackground(Color.WHITE);
        
        Calendar cal = Calendar.getInstance();
        updateCalendar(cal, monthLabel, daysPanel, prevMonth, nextMonth);
        
        prevMonth.addActionListener(e -> {
            cal.add(Calendar.MONTH, -1);
            updateCalendar(cal, monthLabel, daysPanel, prevMonth, nextMonth);
        });
        
        nextMonth.addActionListener(e -> {
            cal.add(Calendar.MONTH, 1);
            updateCalendar(cal, monthLabel, daysPanel, prevMonth, nextMonth);
        });
        
        panel.add(monthPanel, BorderLayout.NORTH);
        panel.add(daysPanel, BorderLayout.CENTER);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void updateCalendar(Calendar cal, JLabel monthLabel, JPanel daysPanel, JButton prevMonth, JButton nextMonth) {
        daysPanel.removeAll();
        
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthFormat.format(cal.getTime()));
        
        Calendar nextMonthCal = (Calendar) cal.clone();
        nextMonthCal.add(Calendar.MONTH, 1);
        nextMonthCal.set(Calendar.DAY_OF_MONTH, 1);

        prevMonth.setEnabled(!(cal.get(Calendar.YEAR) == 2020 && cal.get(Calendar.MONTH) == Calendar.JANUARY));
        nextMonth.setEnabled(nextMonthCal.get(Calendar.YEAR) < maxDate.get(Calendar.YEAR) || 
                           (nextMonthCal.get(Calendar.YEAR) == maxDate.get(Calendar.YEAR) && 
                            nextMonthCal.get(Calendar.MONTH) <= maxDate.get(Calendar.MONTH)));
        
        Calendar tempCal = (Calendar) cal.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        String[] weekDays = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (String day : weekDays) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(new Color(70, 130, 180));
            daysPanel.add(label);
        }
        
        for (int i = 1; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }
        
        Calendar today = Calendar.getInstance();
        Calendar selectedDate = Calendar.getInstance();
        try {
            selectedDate.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(fechaField.getText()));
        } catch (Exception e) {
            selectedDate = today;
        }
        
        for (int i = 1; i <= daysInMonth; i++) {
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.setFont(new Font("Arial", Font.PLAIN, 14));
            dayButton.setBackground(Color.WHITE);
            dayButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) && 
                cal.get(Calendar.MONTH) == today.get(Calendar.MONTH) && 
                i == today.get(Calendar.DAY_OF_MONTH)) {
                dayButton.setBackground(new Color(220, 240, 255));
            }
            
            if (cal.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) && 
                cal.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) && 
                i == selectedDate.get(Calendar.DAY_OF_MONTH)) {
                dayButton.setBackground(new Color(180, 220, 255));
                dayButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
            }
            
            final int day = i;
            dayButton.addActionListener(e -> {
                tempCal.set(Calendar.DAY_OF_MONTH, day);
                fechaField.setText(new SimpleDateFormat("dd/MM/yyyy").format(tempCal.getTime()));
                ((Window) SwingUtilities.getRoot(dayButton)).dispose();
            });
            daysPanel.add(dayButton);
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        daysPanel.revalidate();
        daysPanel.repaint();
    }
    
    private void estiloTextField(JTextField field, boolean esReferencia) {
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(new Font("Arial", Font.BOLD, 18));
        field.setBackground(new Color(255, 255, 255, 200));
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        if(esReferencia) {
            field.setText("Número de referencia");
            field.setForeground(Color.GRAY);
            
            // Filtro para solo números
            ((PlainDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                    if (string.matches("\\d*")) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                    if (text.matches("\\d*")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
            
            field.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (field.getText().equals("Número de referencia")) {
                        field.setText("");
                        field.setForeground(Color.BLACK);
                    }
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    if (field.getText().isEmpty()) {
                        field.setText("Número de referencia");
                        field.setForeground(Color.GRAY);
                    }
                }
            });
        } else if (field != fechaField) {
            field.setText("Banco");
            field.setForeground(Color.GRAY);
            
            // Filtro para solo letras (incluyendo espacios y acentos)
            ((PlainDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                    if (string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                    if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
            
            field.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (field.getText().equals("Banco")) {
                        field.setText("");
                        field.setForeground(Color.BLACK);
                    }
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    if (field.getText().isEmpty()) {
                        field.setText("Banco");
                        field.setForeground(Color.GRAY);
                    }
                }
            });
        }
    }
    
    private MouseAdapter crearHoverEffect(JLabel label, ImageIcon icon, int ancho, int alto) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(ancho + 10, alto + 5, Image.SCALE_SMOOTH)));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));
            }
        };
    }
    
    private void validarPago() {
        if(!panelPagoMovil.isVisible()) {
            JOptionPane.showMessageDialog(frame, "Por favor seleccione el tipo de recarga", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (referenciaField.getText().isEmpty() || referenciaField.getText().equals("Número de referencia") ||
            bancoField.getText().isEmpty() || bancoField.getText().equals("Banco")) {
            JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Pago validado exitosamente!\nReferencia: " + referenciaField.getText() + 
                "\nBanco: " + bancoField.getText() + "\nFecha: " + fechaField.getText(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void mostrar() {
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Recarga_monedero().mostrar());
    }
}