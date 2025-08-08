package com.example;

import java.util.Scanner;

public final class PowerCalculator {

    private PowerCalculator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Calculates x raised to the power of y from scratch.
     * Handles special cases and throws IllegalArgumentException for invalid inputs.
     * 
     * @param x The base value.
     * @param y The exponent value.
     * @return x raised to the power of y.
     * @throws IllegalArgumentException if inputs are invalid (e.g., 0^0,
     *                                  0^negative,
     *                                  negative base with non-integer exponent).
     */
    public static double power(double x, double y) throws IllegalArgumentException {
        // FR-XY-004: Handle special cases
        if (x == 0.0) {
            if (y == 0.0) {
                // 0^0 is typically defined as 1 in many contexts, but undefined in strict
                // mathematical
                // sense. For calculator context, often treated as 1.
                return 1.0;
            } else if (y < 0.0) {
                // FR-XY-003: Display error for 0^negative
                throw new IllegalArgumentException("0 raised to a negative power is undefined.");
            } else {
                // 0^positive is 0
                return 0.0;
            }
        }

        if (x == 1.0) {
            // 1^y is 1 for any y
            return 1.0;
        }

        if (y == 0.0) {
            // Any number (except 0) raised to power 0 is 1
            return 1.0;
        }

        if (y == 1.0) {
            // Any number raised to power 1 is itself
            return x;
        }

        // Handle negative base with non-integer exponent
        if (x < 0 && !isInteger(y)) {
            // FR-XY-003: Display error for negative base with non-integer exponent
            throw new IllegalArgumentException(
                    "Negative base with a non-integer exponent results in a complex number,"
                            + " which is not supported.");
        }

        // Handle negative exponent
        if (y < 0) {
            return 1.0 / power(x, -y);
        }

        // Handle integer exponents
        if (isInteger(y)) {
            return powerInteger(x, (long) y);
        }

        // Handle fractional exponents using exponential and logarithm
        if (x <= 0) {
            throw new IllegalArgumentException(
                    "Cannot compute fractional power of non-positive number.");
        }

        // For x^y where y is fractional, use x^y = e^(y * ln(x))
        double lnX = naturalLog(x);
        double yLnX = y * lnX;
        return exponential(yLnX);
    }

    /**
     * Check if a double value is an integer
     */
    private static boolean isInteger(double value) {
        return value == Math.floor(value) && !Double.isInfinite(value);
    }

    /**
     * Calculate x^n where n is an integer using exponentiation by squaring
     */
    private static double powerInteger(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        if (n == 1) {
            return x;
        }
        if (n < 0) {
            return 1.0 / powerInteger(x, -n);
        }

        double result = 1.0;
        double base = x;

        while (n > 0) {
            if (n % 2 == 1) {
                result *= base;
            }
            base *= base;
            n /= 2;
        }

        return result;
    }

    /**
     * Calculate natural logarithm using Taylor series
     * ln(x) = 2 * sum((1/(2k+1)) * ((x-1)/(x+1))^(2k+1)) for k=0 to infinity
     */
    private static double naturalLog(double x) throws IllegalArgumentException {
        if (x <= 0) {
            throw new IllegalArgumentException(
                    "Natural logarithm is undefined for non-positive numbers.");
        }

        if (x == 1.0) {
            return 0.0;
        }

        // For better convergence, use ln(x) = ln(2^k * m) = k*ln(2) + ln(m)
        // where m is in [1, 2)
        int k = 0;
        double m = x;

        // Normalize x to range [1, 2)
        while (m >= 2.0) {
            m /= 2.0;
            k++;
        }
        while (m < 1.0) {
            m *= 2.0;
            k--;
        }

        // Calculate ln(m) using Taylor series around 1
        // ln(1+u) = u - u^2/2 + u^3/3 - u^4/4 + ...
        double u = m - 1.0;
        double result = 0.0;
        double term = u;

        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 1) {
                result += term / i;
            } else {
                result -= term / i;
            }
            term *= u;

            // Check for convergence
            if (Math.abs(term / i) < 1e-15) {
                break;
            }
        }

        // ln(2) ≈ 0.6931471805599453
        double ln2 = 0.6931471805599453;
        return k * ln2 + result;
    }

    /**
     * Calculate e^x using Taylor series
     * e^x = 1 + x + x^2/2! + x^3/3! + ...
     */
    private static double exponential(double x) {
        if (x == 0.0) {
            return 1.0;
        }

        // For large |x|, use e^x = e^(n + f) = e^n * e^f
        // where n is integer and |f| < 1
        int n = (int) Math.floor(x);
        double f = x - n;

        // Calculate e^f using Taylor series
        double result = 1.0;
        double term = 1.0;

        for (int i = 1; i <= 100; i++) {
            term *= f / i;
            result += term;

            // Check for convergence
            if (Math.abs(term) < 1e-15) {
                break;
            }
        }

        // Calculate e^n = (e^1)^n
        // e ≈ 2.718281828459045
        double e = 2.718281828459045;
        double eToN = powerInteger(e, n);

        return eToN * result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueCalculation = true;

            System.out.println("Welcome to the x^y Power Calculator!");
            System.out.println("Enter \'exit\' at any prompt to quit.");

            while (continueCalculation) {
                double x = 0;
                double y = 0;
                boolean validInput = false;

                // FR-XY-002: Accept input for base (x)
                while (!validInput) {
                    System.out.print("Enter the base (x): ");
                    if (scanner.hasNextDouble()) {
                        x = scanner.nextDouble();
                        validInput = true;
                    } else {
                        String input = scanner.next();
                        if ("exit".equalsIgnoreCase(input)) {
                            continueCalculation = false;
                            break;
                        }
                        // FR-XY-003: Display error for invalid input
                        System.out.println("Invalid input. Please enter a numeric value for x.");
                    }
                }
                if (!continueCalculation) {
                    break;
                }

                validInput = false;
                // FR-XY-002: Accept input for exponent (y)
                while (!validInput) {
                    System.out.print("Enter the exponent (y): ");
                    if (scanner.hasNextDouble()) {
                        y = scanner.nextDouble();
                        validInput = true;
                    } else {
                        String input = scanner.next();
                        if ("exit".equalsIgnoreCase(input)) {
                            continueCalculation = false;
                            break;
                        }
                        // FR-XY-003: Display error for invalid input
                        System.out.println("Invalid input. Please enter a numeric value for y.");
                    }
                }
                if (!continueCalculation) {
                    break;
                }

                try {
                    double result = power(x, y);
                    // FR-XY-005: Provide result with precision
                    System.out.printf("Result: %.10f%n", result);
                } catch (IllegalArgumentException e) {
                    // FR-XY-003: Display error messages for invalid inputs
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
                System.out.println(); // New line for readability
            }

            System.out.println("Exiting Power Calculator. Goodbye!");
            scanner.close();
        }
    }
}
