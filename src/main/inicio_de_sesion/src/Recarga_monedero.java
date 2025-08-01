import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class Recarga_monedero {

    private JFrame frame;
    private JComboBox<String> metodoPagoCombo;
    private JPanel panelPagoMovil;
    private JTextField referenciaField, bancoField, fechaField, montoRecargaField;
    private final Calendar maxDate = Calendar.getInstance();
    private final Calendar minDate = Calendar.getInstance();
    private String cedulaUsuario;
    private String userType;
    private final String usuarios_file_path = "../../db/usuarios.txt";

    public Recarga_monedero(String cedula, String userType) {
        this.cedulaUsuario = cedula;
        this.userType = userType;
        maxDate.set(2030, Calendar.AUGUST, 31);
        minDate.set(2000, Calendar.JANUARY, 1);
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

        ImageIcon fondoIcon = new ImageIcon("../../Imagenes/cuadro_ucv.png");
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
        panelPagoMovil.setBounds(100, 180, 500, 400); // Aumentar altura para el nuevo campo
        panelPagoMovil.setLayout(new GridLayout(9, 1, 10, 10)); // Aumentar filas
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

        montoRecargaField = new JTextField("Monto de Recarga");
        estiloTextField(montoRecargaField, false); // No es referencia, pero necesita filtro de números
        ((PlainDocument) montoRecargaField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (string.matches("\\d*\\.?\\d*")) { // Permite números y un punto decimal
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                if (text.matches("\\d*\\.?\\d*")) { // Permite números y un punto decimal
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        montoRecargaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (montoRecargaField.getText().equals("Monto de Recarga")) {
                    montoRecargaField.setText("");
                    montoRecargaField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (montoRecargaField.getText().isEmpty()) {
                    montoRecargaField.setText("Monto de Recarga");
                    montoRecargaField.setForeground(Color.GRAY);
                }
            }
        });

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
        panelPagoMovil.add(montoRecargaField); // Nuevo campo
        panelPagoMovil.add(referenciaField);
        panelPagoMovil.add(bancoField);
        panelPagoMovil.add(fechaPanel);

        metodoPagoCombo.addActionListener(e -> {
            if (metodoPagoCombo.getSelectedItem().equals("Pago Móvil")) {
                panelPagoMovil.setVisible(true);
                if (montoRecargaField.getText().isEmpty()) {
                    montoRecargaField.setText("Monto de Recarga");
                    montoRecargaField.setForeground(Color.GRAY);
                }
                if (referenciaField.getText().isEmpty()) {
                    referenciaField.setText("Número de referencia");
                    referenciaField.setForeground(Color.GRAY);
                    referenciaField.setFont(new Font("Arial", Font.BOLD, 18));
                }
                if (bancoField.getText().isEmpty()) {
                    bancoField.setText("Banco");
                    bancoField.setForeground(Color.GRAY);
                }
            } else {
                panelPagoMovil.setVisible(false);
            }
        });

        JButton btnRecargar = new JButton();
        btnRecargar.setBounds(200, 600, 262, 76);
        btnRecargar.setOpaque(false);
        btnRecargar.setContentAreaFilled(false);
        btnRecargar.setBorderPainted(false);

        
        

        ImageIcon iconRecargar = new ImageIcon("../../Imagenes/recarga_completa.png");
        

        Image imgRecargar = iconRecargar.getImage().getScaledInstance(262, 76, Image.SCALE_SMOOTH);
        

        JLabel lblRecargar = new JLabel(new ImageIcon(imgRecargar));
        lblRecargar.setBounds(200, 600, 262, 76);

        
        

        btnRecargar.addActionListener(e -> validarPagoYRecargar());
        

        btnRecargar.addMouseListener(crearHoverEffect(lblRecargar, iconRecargar, 262, 76));
        

        JButton btnAtras = new JButton("Atrás");
        btnAtras.setBounds(50, 50, 100, 40);
        btnAtras.addActionListener(e -> {
            frame.dispose();
            if ("admin".equals(userType)) {
                new AdminMenu().mostrar();
            } else {
                new MenuPrincipal(cedulaUsuario).mostrar();
            }
        });
        cuadro_imagen.add(btnAtras);
        cuadro_imagen.add(metodoPagoCombo);
        cuadro_imagen.add(panelPagoMovil);
        cuadro_imagen.add(btnRecargar);
        
        cuadro_imagen.add(lblRecargar);
        

        pantalla.revalidate();
        pantalla.repaint();
    }

    private void mostrarCalendario() {
        JDialog dialog = new JDialog(frame, "Seleccionar Fecha", true);
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

        final Calendar mutableCal = Calendar.getInstance();
        try {
            Date currentFieldDate = new SimpleDateFormat("dd/MM/yyyy").parse(fechaField.getText());
            mutableCal.setTime(currentFieldDate);
        } catch (Exception e) {
            mutableCal.setTime(Calendar.getInstance().getTime());
        }

        updateCalendar(mutableCal, monthLabel, daysPanel, prevMonth, nextMonth, dialog);

        prevMonth.addActionListener(e -> {
            mutableCal.add(Calendar.MONTH, -1);
            updateCalendar(mutableCal, monthLabel, daysPanel, prevMonth, nextMonth, dialog);
        });

        nextMonth.addActionListener(e -> {
            mutableCal.add(Calendar.MONTH, 1);
            updateCalendar(mutableCal, monthLabel, daysPanel, prevMonth, nextMonth, dialog);
        });

        panel.add(monthPanel, BorderLayout.NORTH);
        panel.add(daysPanel, BorderLayout.CENTER);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void updateCalendar(Calendar cal, JLabel monthLabel, JPanel daysPanel, JButton prevMonth, JButton nextMonth, JDialog dialog) {
        daysPanel.removeAll();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        monthLabel.setText(monthFormat.format(cal.getTime()));

        Calendar tempCalForPrev = (Calendar) cal.clone();
        tempCalForPrev.set(Calendar.DAY_OF_MONTH, 1);
        prevMonth.setEnabled(tempCalForPrev.after(minDate));

        Calendar tempCalForNext = (Calendar) cal.clone();
        tempCalForNext.set(Calendar.DAY_OF_MONTH, tempCalForNext.getActualMaximum(Calendar.DAY_OF_MONTH));
        nextMonth.setEnabled(tempCalForNext.before(maxDate));

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

            Calendar currentDayInLoop = (Calendar) cal.clone();
            currentDayInLoop.set(Calendar.DAY_OF_MONTH, i);
            currentDayInLoop.set(Calendar.HOUR_OF_DAY, 0);
            currentDayInLoop.set(Calendar.MINUTE, 0);
            currentDayInLoop.set(Calendar.SECOND, 0);
            currentDayInLoop.set(Calendar.MILLISECOND, 0);

            if (currentDayInLoop.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                currentDayInLoop.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                currentDayInLoop.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                dayButton.setBackground(new Color(220, 240, 255));
            }

            if (currentDayInLoop.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) &&
                currentDayInLoop.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
                currentDayInLoop.get(Calendar.DAY_OF_MONTH) == selectedDate.get(Calendar.DAY_OF_MONTH)) {
                dayButton.setBackground(new Color(180, 220, 255));
                dayButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
            }
            
            if (currentDayInLoop.after(maxDate) || currentDayInLoop.before(minDate)) {
                 dayButton.setEnabled(false);
                 dayButton.setForeground(Color.LIGHT_GRAY);
            }

            final int day = i;
            dayButton.addActionListener(e -> {
                Calendar selectedCal = (Calendar) cal.clone();
                selectedCal.set(Calendar.DAY_OF_MONTH, day);
                fechaField.setText(new SimpleDateFormat("dd/MM/yyyy").format(selectedCal.getTime()));
                dialog.dispose();
            });
            daysPanel.add(dayButton);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void estiloTextField(JTextField field, boolean esReferencia) {
        field.setHorizontalAlignment(JTextField.CENTER);
        if (esReferencia) {
            field.setFont(new Font("Arial", Font.BOLD, 18));
        } else {
            field.setFont(new Font("Arial", Font.PLAIN, 18));
        }
        field.setBackground(new Color(255, 255, 255, 200));
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        if (esReferencia) {
            field.setText("Número de referencia");
            field.setForeground(Color.GRAY);

            ((PlainDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                        throws BadLocationException {
                    if (string == null) return;
                    if (string.matches("\\d*")) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                        throws BadLocationException {
                    if (text == null) {
                        super.replace(fb, offset, length, text, attrs);
                        return;
                    }
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
        } else if (field != fechaField && field != montoRecargaField) { // Excluir montoRecargaField también
            field.setText("Banco");
            field.setForeground(Color.GRAY);

            ((PlainDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                        throws BadLocationException {
                    if (string == null) return;
                    if (string.matches("[a-zA-ZáéíóúÉÓÚñÑ\\s]*")) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                        throws BadLocationException {
                    if (text == null) {
                        super.replace(fb, offset, length, text, attrs);
                        return;
                    }
                    if (text.matches("[a-zA-ZáéíóúÉÓÚñÑ\\s]*")) {
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

    private void validarPagoYRecargar() {
        if (!panelPagoMovil.isVisible()) {
            JOptionPane.showMessageDialog(frame, "Por favor seleccione el tipo de recarga", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String montoStr = montoRecargaField.getText().trim();
        if (montoStr.isEmpty() || montoStr.equals("Monto de Recarga")) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese el monto de recarga.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double montoRecarga;
        try {
            montoRecarga = Double.parseDouble(montoStr);
            if (montoRecarga <= 0) {
                JOptionPane.showMessageDialog(frame, "El monto de recarga debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Monto de recarga inválido. Por favor ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (referenciaField.getText().isEmpty() || referenciaField.getText().equals("Número de referencia") ||
            bancoField.getText().isEmpty() || bancoField.getText().equals("Banco")) {
            JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos de validación de pago.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lógica para actualizar el saldo
        try {
            List<String> fileContent = new ArrayList<>();
            boolean userFound = false;
            double newSaldo = 0.0; // Declare newSaldo here
            try (BufferedReader reader = new BufferedReader(new FileReader(usuarios_file_path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4 && parts[0].trim().equals(cedulaUsuario)) {
                        double currentSaldo = Double.parseDouble(parts[3].trim());
                        newSaldo = currentSaldo + montoRecarga;
                        fileContent.add(parts[0] + "," + parts[1] + "," + parts[2] + "," + String.format("%.2f", newSaldo));
                        userFound = true;
                    } else {
                        fileContent.add(line);
                    }
                }
            }

            if (userFound) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(usuarios_file_path))) {
                    for (String line : fileContent) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
                JOptionPane.showMessageDialog(frame, "Recarga exitosa! Su nuevo saldo es: " + String.format("%.2f", newSaldo) + " Bs.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error al procesar la recarga: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private double getSaldoActual(String cedula) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usuarios_file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].trim().equals(cedula)) {
                    return Double.parseDouble(parts[3].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Recarga_monedero("12345678", "admin").mostrar());
    }
}