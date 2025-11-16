import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class CalculatorGUI extends JFrame {
    private ScientificCalculator calculator;
    private LogManager logManager;
    private JTextField display;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewInput = true;
    private DecimalFormat df = new DecimalFormat("#.####");
    private String logFile = "sci_calc_logs.ser";

    public CalculatorGUI() {
        calculator = new ScientificCalculator("SciCalc");
        logManager = new LogManager();
        logManager.loadLogs(logFile);

        setupUI();
        setTitle("Scientific Calculator");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));

        // Display panel
        JPanel displayPanel = new JPanel(new BorderLayout(5, 5));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.setBackground(new Color(45, 45, 45));

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(60, 60, 60));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(display, BorderLayout.CENTER);

        add(displayPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        // Button definitions
        String[][] buttons = {
            {"sin", "cos", "tan", "H"},
            {"ln", "log", "√", "^"},
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"C", "0", "=", "+"}
        };

        for (String[] row : buttons) {
            for (String btnText : row) {
                JButton btn = createButton(btnText);
                buttonPanel.add(btn);
            }
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Add window listener to save logs on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logManager.saveLogs(logFile);
            }
        });
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setFocusPainted(false);

        // Color scheme
        if (text.equals("=")) {
            btn.setBackground(new Color(76, 175, 80));
            btn.setForeground(Color.WHITE);
        } else if (text.equals("C") || text.equals("H")) {
            btn.setBackground(new Color(244, 67, 54));
            btn.setForeground(Color.WHITE);
        } else if (text.matches("[0-9]")) {
            btn.setBackground(new Color(97, 97, 97));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(255, 152, 0));
            btn.setForeground(Color.WHITE);
        }

        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.addActionListener(new ButtonClickListener());
        return btn;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();

            if (command.matches("[0-9]")) {
                handleNumberInput(command);
            } else if (command.equals("C")) {
                handleClear();
            } else if (command.equals("=")) {
                handleEquals();
            } else if (command.equals("H")) {
                showHistory();
            } else if (command.equals("√")) {
                handleUnaryOperation("sqrt");
            } else if (command.equals("ln")) {
                handleUnaryOperation("ln");
            } else if (command.equals("log")) {
                handleUnaryOperation("log10");
            } else if (command.equals("sin")) {
                handleUnaryOperation("sin");
            } else if (command.equals("cos")) {
                handleUnaryOperation("cos");
            } else if (command.equals("tan")) {
                handleUnaryOperation("tan");
            } else {
                handleOperator(command);
            }
        }
    }

    private void handleNumberInput(String num) {
        if (isNewInput) {
            currentInput = num;
            isNewInput = false;
        } else {
            currentInput += num;
        }
        display.setText(currentInput);
    }

    private void handleClear() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        isNewInput = true;
        display.setText("0");
    }

    private void handleOperator(String op) {
        if (!currentInput.isEmpty()) {
            if (!operator.isEmpty()) {
                handleEquals();
            } else {
                firstOperand = Double.parseDouble(currentInput);
            }
            operator = op;
            isNewInput = true;
        }
    }

    private void handleEquals() {
        if (!operator.isEmpty() && !currentInput.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;
            String operation = "";
            boolean hasError = false;

            try {
                switch (operator) {
                    case "+":
                        result = calculator.add(firstOperand, secondOperand);
                        operation = String.format("%.4f + %.4f = %.4f", firstOperand, secondOperand, result);
                        break;
                    case "-":
                        result = calculator.subtract(firstOperand, secondOperand);
                        operation = String.format("%.4f - %.4f = %.4f", firstOperand, secondOperand, result);
                        break;
                    case "*":
                        result = calculator.multiply(firstOperand, secondOperand);
                        operation = String.format("%.4f * %.4f = %.4f", firstOperand, secondOperand, result);
                        break;
                    case "/":
                        if (secondOperand == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        result = calculator.divide(firstOperand, secondOperand);
                        operation = String.format("%.4f / %.4f = %.4f", firstOperand, secondOperand, result);
                        break;
                    case "^":
                        result = calculator.power(firstOperand, secondOperand);
                        operation = String.format("%.4f ^ %.4f = %.4f", firstOperand, secondOperand, result);
                        break;
                }

                if (!hasError) {
                    currentInput = df.format(result);
                    display.setText(currentInput);
                    logManager.addLog(operation);
                    operator = "";
                    isNewInput = true;
                }
            } catch (Exception ex) {
                display.setText("Error");
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Calculation Error", JOptionPane.ERROR_MESSAGE);
                handleClear();
            }
        }
    }

    private void handleUnaryOperation(String op) {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            double result = 0;
            String operation = "";
            boolean hasError = false;

            try {
                switch (op) {
                    case "sqrt":
                        if (value < 0) {
                            throw new ArithmeticException("Cannot calculate square root of negative number");
                        }
                        result = calculator.sqrt(value);
                        operation = String.format("sqrt(%.4f) = %.4f", value, result);
                        break;
                    case "ln":
                        if (value <= 0) {
                            throw new ArithmeticException("Logarithm input must be greater than 0");
                        }
                        result = calculator.log(value);
                        operation = String.format("ln(%.4f) = %.4f", value, result);
                        break;
                    case "log10":
                        if (value <= 0) {
                            throw new ArithmeticException("Logarithm input must be greater than 0");
                        }
                        result = calculator.log10(value);
                        operation = String.format("log10(%.4f) = %.4f", value, result);
                        break;
                    case "sin":
                        result = calculator.sin(value);
                        operation = String.format("sin(%.4f°) = %.4f", value, result);
                        break;
                    case "cos":
                        result = calculator.cos(value);
                        operation = String.format("cos(%.4f°) = %.4f", value, result);
                        break;
                    case "tan":
                        result = calculator.tan(value);
                        operation = String.format("tan(%.4f°) = %.4f", value, result);
                        break;
                }

                if (!hasError) {
                    currentInput = df.format(result);
                    display.setText(currentInput);
                    logManager.addLog(operation);
                    isNewInput = true;
                }
            } catch (Exception ex) {
                display.setText("Error");
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Calculation Error", JOptionPane.ERROR_MESSAGE);
                handleClear();
            }
        }
    }

    private void showHistory() {
        logManager.saveLogs(logFile);

        JDialog historyDialog = new JDialog(this, "Calculation History", true);
        historyDialog.setSize(500, 400);
        historyDialog.setLocationRelativeTo(this);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyArea.setBackground(new Color(245, 245, 245));
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);

        // Create scrollable text area
        JScrollPane scrollPane = new JScrollPane(historyArea);

        // Get logs from LogManager
        java.util.List<String> logs = logManager.getLogs();
        if (logs == null || logs.isEmpty()) {
            historyArea.setText("No calculation history found.");
        } else {
            StringBuilder historyText = new StringBuilder();
            historyText.append("=== CALCULATION HISTORY ===\n\n");
            for (int i = 0; i < logs.size(); i++) {
                historyText.append((i + 1)).append(". ").append(logs.get(i)).append("\n");
            }
            historyArea.setText(historyText.toString());
        }

        historyDialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> historyDialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        historyDialog.add(btnPanel, BorderLayout.SOUTH);

        historyDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calc = new CalculatorGUI();
            calc.setVisible(true);
        });
    }
}