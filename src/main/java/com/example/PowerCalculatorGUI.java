package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * GUI for the x^y Power Calculator
 * Provides a user-friendly interface for calculating powers from scratch
 */
public class PowerCalculatorGUI extends JFrame {

    private JTextField baseField;
    private JTextField exponentField;
    private JLabel resultLabel;
    private JLabel errorLabel;
    private JButton calculateButton;
    private JButton clearButton;
    private Border defaultTextFieldBorder;

    public PowerCalculatorGUI() {
        initializeGUI();
        setupEventHandlers();
    }

    /**
     * Initialize the GUI components and layout
     */
    private void initializeGUI() {
        setTitle("x^y Power Calculator - From Scratch Implementation");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(true);

        // Set up the main panel with a more sophisticated layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Power Calculator (x^y)");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 15));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Values"));

        // Base input
        JLabel baseLabel = new JLabel("Base (x):");
        baseLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        baseField = new JTextField(15);
        baseField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        baseField.setToolTipText("Enter the base value (any real number)");
        baseLabel.setLabelFor(baseField);
        baseLabel.setDisplayedMnemonic(KeyEvent.VK_B);
        baseField.getAccessibleContext().setAccessibleName("Base input field");
        baseField.getAccessibleContext().setAccessibleDescription(
                "Enter the base value x for the power calculation");

        // Exponent input
        JLabel exponentLabel = new JLabel("Exponent (y):");
        exponentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        exponentField = new JTextField(15);
        exponentField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        exponentField.setToolTipText("Enter the exponent value (any real number)");
        exponentLabel.setLabelFor(exponentField);
        exponentLabel.setDisplayedMnemonic(KeyEvent.VK_E);
        exponentField.getAccessibleContext().setAccessibleName("Exponent input field");
        exponentField.getAccessibleContext().setAccessibleDescription(
                "Enter the exponent value y for the power calculation");

        inputPanel.add(baseLabel);
        inputPanel.add(baseField);
        inputPanel.add(exponentLabel);
        inputPanel.add(exponentField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        calculateButton = new JButton("Calculate x^y");
        calculateButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        calculateButton.setPreferredSize(new Dimension(150, 35));
        calculateButton.setBackground(new Color(70, 130, 180));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setOpaque(true);
        calculateButton.setContentAreaFilled(true);
        calculateButton.setBorderPainted(false);
        calculateButton.setMnemonic(KeyEvent.VK_C);
        calculateButton.getAccessibleContext().setAccessibleName("Calculate button");
        calculateButton.getAccessibleContext().setAccessibleDescription(
                "Compute x raised to the power y using the entered values");

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        clearButton.setPreferredSize(new Dimension(100, 35));
        clearButton.setMnemonic(KeyEvent.VK_L);
        clearButton.getAccessibleContext().setAccessibleName("Clear inputs button");
        clearButton.getAccessibleContext().setAccessibleDescription(
                "Clear all inputs and results and focus the base field");

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        // Result panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));

        resultLabel = new JLabel("Result: (Enter values and click Calculate)");
        resultLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultLabel.getAccessibleContext().setAccessibleName("Result label");
        resultLabel.getAccessibleContext().setAccessibleDescription(
                "Displays the result of the power calculation or a status message");

        // Error message display
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        errorLabel.getAccessibleContext().setAccessibleName("Error message label");
        errorLabel.getAccessibleContext().setAccessibleDescription(
                "Displays input validation errors and helpful guidance");

        resultPanel.add(resultLabel, BorderLayout.CENTER);
        resultPanel.add(errorLabel, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Create a combined panel for input and result
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(resultPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Set default button for Enter key and bind ESC to clear
        getRootPane().setDefaultButton(calculateButton);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearFields");
        getRootPane().getActionMap().put("clearFields", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Keep default border to restore during validation
        defaultTextFieldBorder = baseField.getBorder();
    }

    /**
     * Setup event handlers for buttons and keyboard input
     */
    private void setupEventHandlers() {
        // Calculate button action
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePower();
            }
        });

        // Clear button action
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Enter key support for both text fields
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculatePower();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Not used
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used
            }
        };

        baseField.addKeyListener(enterKeyListener);
        exponentField.addKeyListener(enterKeyListener);

        // Real-time validation to prevent errors and provide feedback
        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateValidationState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateValidationState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateValidationState();
            }
        };

        baseField.getDocument().addDocumentListener(validationListener);
        exponentField.getDocument().addDocumentListener(validationListener);

        // Initial validation state
        updateValidationState();
    }

    /**
     * Calculate the power using the from-scratch implementation
     */
    private void calculatePower() {
        // Clear previous results
        resultLabel.setText("Calculating...");
        errorLabel.setText("");

        try {
            // Validate and parse input
            String baseText = baseField.getText().trim();
            String exponentText = exponentField.getText().trim();

            if (baseText.isEmpty()) {
                throw new IllegalArgumentException("Please enter a base value (x).");
            }

            if (exponentText.isEmpty()) {
                throw new IllegalArgumentException("Please enter an exponent value (y).");
            }

            double x;
            double y;

            try {
                x = Double.parseDouble(baseText);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(
                        "Base value must be a valid number. Please check your input for \'"
                                + baseText + "\'.",
                        ex);
            }

            try {
                y = Double.parseDouble(exponentText);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(
                        "Exponent value must be a valid number. Please check your input for '"
                                + exponentText + "'.",
                        ex);
            }

            // Check for special input values
            if (Double.isNaN(x) || Double.isInfinite(x)) {
                throw new IllegalArgumentException("Base value cannot be NaN or Infinity.");
            }

            if (Double.isNaN(y) || Double.isInfinite(y)) {
                throw new IllegalArgumentException("Exponent value cannot be NaN or Infinity.");
            }

            // Calculate the result using our from-scratch implementation
            double result = PowerCalculator.power(x, y);

            // Check result validity
            if (Double.isNaN(result)) {
                resultLabel.setText("Result: NaN (Not a Number)");
                errorLabel.setText("The calculation resulted in an undefined value.");
            } else if (Double.isInfinite(result)) {
                if (result > 0) {
                    resultLabel.setText("Result: +∞ (Positive Infinity)");
                } else {
                    resultLabel.setText("Result: -∞ (Negative Infinity)");
                }
                errorLabel.setText("The result is too large to represent as a finite number.");
            } else {
                // Format the result appropriately
                if (Math.abs(result) < 1e-10 && result != 0) {
                    resultLabel.setText(String.format("Result: %.2e", result));
                } else if (Math.abs(result) > 1e10) {
                    resultLabel.setText(String.format("Result: %.2e", result));
                } else {
                    resultLabel.setText(String.format("Result: %.10f", result));
                }
                errorLabel.setText(""); // Clear any previous error
            }

        } catch (IllegalArgumentException ex) {
            resultLabel.setText("Result: Error");
            errorLabel.setText("Error: " + ex.getMessage());
        } catch (Exception ex) {
            resultLabel.setText("Result: Unexpected Error");
            errorLabel.setText("An unexpected error occurred. Please try again or contact support.");
            // Log the actual exception for debugging (in a real application)
            System.err.println("Unexpected error in PowerCalculatorGUI: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Clear all input fields and results
     */
    private void clearFields() {
        baseField.setText("");
        exponentField.setText("");
        resultLabel.setText("Result: (Enter values and click Calculate)");
        errorLabel.setText("");
        baseField.requestFocus(); // Set focus back to base field
        // Restore borders
        baseField.setBorder(defaultTextFieldBorder);
        exponentField.setBorder(defaultTextFieldBorder);
        calculateButton.setEnabled(false);
    }

    /**
     * Validate inputs in real time to prevent common errors and reduce memory load.
     * Enables/disables calculate button and sets helpful error messages.
     */
    private void updateValidationState() {
        String baseText = baseField.getText().trim();
        String exponentText = exponentField.getText().trim();

        errorLabel.setText("");
        baseField.setBorder(defaultTextFieldBorder);
        exponentField.setBorder(defaultTextFieldBorder);

        if (baseText.isEmpty() || exponentText.isEmpty()) {
            calculateButton.setEnabled(false);
            return;
        }

        Double x = tryParseDouble(baseText);
        Double y = tryParseDouble(exponentText);

        if (x == null || y == null) {
            errorLabel.setText("Please enter valid numeric values for x and y.");
            if (x == null) {
                baseField.setBorder(new LineBorder(Color.RED));
            }
            if (y == null) {
                exponentField.setBorder(new LineBorder(Color.RED));
            }
            calculateButton.setEnabled(false);
            return;
        }

        // Prevent known invalid cases before calculation for better UX
        if (x == 0.0 && y < 0.0) {
            errorLabel.setText("0 raised to a negative power is undefined.");
            exponentField.setBorder(new LineBorder(Color.RED));
            calculateButton.setEnabled(false);
            return;
        }

        if (x < 0.0 && !isInteger(y)) {
            errorLabel.setText("Negative base requires an integer exponent.");
            exponentField.setBorder(new LineBorder(Color.RED));
            calculateButton.setEnabled(false);
            return;
        }

        calculateButton.setEnabled(true);
    }

    private static Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static boolean isInteger(double value) {
        return value == Math.floor(value) && !Double.isInfinite(value);
    }

    /**
     * Main method to launch the GUI application
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If setting look and feel fails, continue with default
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PowerCalculatorGUI calculator = new PowerCalculatorGUI();
                    calculator.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error launching PowerCalculatorGUI: " + e.getMessage());
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error launching the calculator application. "
                                    + "Please check the console for details.",
                            "Application Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}