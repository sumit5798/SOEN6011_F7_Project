package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerCalculatorTest {

    @Test
    void testPositiveIntegerExponent() {
        assertEquals(8.0, PowerCalculator.power(2.0, 3.0), 0.0001);
        assertEquals(1.0, PowerCalculator.power(10.0, 0.0), 0.0001);
        assertEquals(16.0, PowerCalculator.power(2.0, 4.0), 0.0001);
    }

    @Test
    void testNegativeIntegerExponent() {
        assertEquals(0.125, PowerCalculator.power(2.0, -3.0), 0.0001);
        assertEquals(1.0 / 16.0, PowerCalculator.power(2.0, -4.0), 0.0001);
    }

    @Test
    void testFractionalExponent() {
        assertEquals(2.0, PowerCalculator.power(4.0, 0.5), 0.0001);
        assertEquals(3.0, PowerCalculator.power(9.0, 0.5), 0.0001);
        assertEquals(2.2360679775, PowerCalculator.power(5.0, 0.5), 0.0001);
    }

    @Test
    void testZeroBase() {
        assertEquals(0.0, PowerCalculator.power(0.0, 5.0), 0.0001);
        assertEquals(1.0, PowerCalculator.power(0.0, 0.0), 0.0001);
        assertThrows(IllegalArgumentException.class, () -> PowerCalculator.power(0.0, -2.0));
    }

    @Test
    void testOneBase() {
        assertEquals(1.0, PowerCalculator.power(1.0, 100.0), 0.0001);
        assertEquals(1.0, PowerCalculator.power(1.0, -50.0), 0.0001);
        assertEquals(1.0, PowerCalculator.power(1.0, 0.5), 0.0001);
    }

    @Test
    void testNegativeBaseIntegerExponent() {
        assertEquals(-8.0, PowerCalculator.power(-2.0, 3.0), 0.0001);
        assertEquals(16.0, PowerCalculator.power(-2.0, 4.0), 0.0001);
        assertEquals(-0.125, PowerCalculator.power(-2.0, -3.0), 0.0001);
    }

    @Test
    void testNegativeBaseNonIntegerExponent() {
        assertThrows(IllegalArgumentException.class, () -> PowerCalculator.power(-4.0, 0.5));
        assertThrows(IllegalArgumentException.class, () -> PowerCalculator.power(-2.0, 1.5));
    }

    @Test
    void testLargeNumbers() {
        // Test with large positive exponent
        assertEquals(1.0e30, PowerCalculator.power(10.0, 30.0), 1.0e25);
        // Test with large negative exponent
        assertEquals(1.0e-30, PowerCalculator.power(10.0, -30.0), 1.0e-35);
    }

    @Test
    void testSmallNumbers() {
        assertEquals(0.000000000000001, PowerCalculator.power(0.1, 15.0), 1.0e-18);
    }

    @Test
    void testEdgeCases() {
        assertEquals(1.0, PowerCalculator.power(Double.MAX_VALUE, 0.0), 0.0001);
        assertEquals(0.0, PowerCalculator.power(0.0, Double.MIN_VALUE), 0.0001);
    }

    @Test
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> PowerCalculator.power(0.0, -1.0));
        assertThrows(IllegalArgumentException.class, () -> PowerCalculator.power(-1.0, 0.5));
        assertThrows(IllegalArgumentException.class, () -> PowerCalculator.power(-2.0, 0.5));
    }
}


