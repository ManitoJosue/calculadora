import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraGUI extends JFrame implements ActionListener {
    private JTextField display;

    public CalculadoraGUI() {
        super("Calculadora");
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 45));
        setUndecorated(true);

        // Panel redondeado para contener la interfaz gráfica
        JPanel roundedPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int radius = 10;  // Radio de esquinas redondeadas
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setColor(getBackground());
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.fillRoundRect(0, 0, width - 1, height - 1, radius, radius);
            }
        };
        roundedPanel.setBackground(new Color(35, 35, 35));
        roundedPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(roundedPanel, BorderLayout.CENTER);

        // Configuración del campo de texto (display)
        display = new JTextField("");
        setupDisplay();
        roundedPanel.add(display, BorderLayout.NORTH);

        // Configuración de los botones en un panel con GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        setupButtons(buttonPanel);
        roundedPanel.add(buttonPanel, BorderLayout.CENTER);

        // Configuración del JFrame principal
        setSize(300, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setupDisplay() {
        display.setEditable(false);
        display.setBackground(new Color(35, 35, 35));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45), 3));
        display.setFont(new Font("Arial", Font.PLAIN, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(display.getPreferredSize().width, 100));
    }

    private void setupButtons(JPanel panel) {
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", ".", ".", "."
        };
        for (String buttonText : buttons) {
            JButton button = createButton(buttonText);
            panel.add(button);
        }
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(65, 105, 225, 180)); // Azul opaco
        button.setForeground(Color.WHITE);
        button.setFocusPainted(true);

        // Borde redondeado con línea adicional
        Border roundedBorder = new RoundedBorder(8, new Color(45, 45, 45));
        Border lineBorder = new LineBorder(new Color(45, 45, 45), 1);
        button.setBorder(new CompoundBorder(roundedBorder, lineBorder));

        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.addActionListener(this);

        // Cambio de color al pasar el ratón
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225, 180));
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("=")) {
            String expression = display.getText();
            String result = evaluateExpression(expression);
            display.setText(result);
        } else if (command.equals("C")) {
            String currentText = display.getText();
            if (!currentText.isEmpty()) {
                String newText = currentText.substring(0, currentText.length() - 1);
                display.setText(newText);
            }
        } else {
            display.setText(display.getText() + command);
        }
    }

    private String evaluateExpression(String expression) {
        try {
            // Implementa la lógica para evaluar la expresión matemática aquí
            // (Por ahora, simplemente devuelve la expresión sin cambios)
            return expression;
        } catch (ArithmeticException e) {
            return "Error";
        }
    }

    public static void main(String[] args) {
        new CalculadoraGUI();
    }
}

class RoundedBorder implements Border {
    private int radius;
    private Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(this.color);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

class LineBorder implements Border {
    private Color color;
    private int thickness;

    public LineBorder(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        for (int i = 0; i < thickness; i++) {
            g.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
        }
    }
}