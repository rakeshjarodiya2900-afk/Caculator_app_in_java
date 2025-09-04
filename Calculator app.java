import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class CalculatorApp extends JFrame implements ActionListener {
    private JTextField textField;
    private JTextArea historyTextArea;
    private String currentInput = "";
    private ArrayList<String> history = new ArrayList<>();

    public CalculatorApp() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());


// Set red background color
        Color bgColor = new Color(57, 122, 230);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 100));
        textField.setBackground(bgColor);
        textField.setForeground(Color.black);
        textField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        add(textField, BorderLayout.NORTH);

        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        historyTextArea.setBackground(bgColor);
        historyTextArea.setForeground(Color.black);
        historyTextArea.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font and size
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        add(scrollPane, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "Back", "Delete", "History"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            button.setBackground(bgColor);
            button.setForeground(Color.black);
            button.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font and size
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        getContentPane().setBackground(bgColor);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            currentInput += command;
            textField.setText(currentInput);
        } else if ("+-*/".contains(command)) {
            currentInput += " " + command + " ";
            textField.setText(currentInput);
        } else if ("=".equals(command)) {
            try {
                String result = evaluateExpression(currentInput);
                history.add(currentInput + " = " + result);
                currentInput = result;
                textField.setText(currentInput);
            } catch (Exception ex) {
                textField.setText("Error");
            }
        } else if ("C".equals(command)) {
            currentInput = "";
            textField.setText("");
        } else if ("Back".equals(command)) {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                textField.setText(currentInput);
            }
        } else if ("Delete".equals(command)) {
            currentInput = "";
            textField.setText(currentInput);
        } else if ("History".equals(command)) {
            showHistory();
        }
    }

    private String evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    result /= operand;
                    break;
            }
        }

        return Double.toString(result);
    }

    private void showHistory() {
        StringBuilder historyText = new StringBuilder();
        for (String item : history) {
            historyText.append(item).append("\n");
        }
        historyTextArea.setText(historyText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorApp());
    }
}