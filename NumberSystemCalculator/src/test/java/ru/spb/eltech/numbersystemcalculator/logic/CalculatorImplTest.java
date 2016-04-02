package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 01.03.2016.
 */
public class CalculatorImplTest {

    private final String[] regularStrings = new String[] {
            "0",
            "1",
            "9",
            "A",
            "F",
            "10",
            "-1",
            "FFFF",
            "123456789ABCDEF",
            "FEDCBA987654321",
            " 1",
            "1 ",
            " 1 ",
    };

    private final String[] incorrectStrings = new String[] {
            null,
            "",
            "1 1",
            "1.0",
            "_LOL",
            "123ÀÁÂÃÄ",
            "123#/.';,.;",
    };

    @Test
    public void testCalculate() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(16, 16);

        assertEquals("0", calculator.calculate("0", "0", Operation.MULTIPLICATION));
        assertEquals("0", calculator.calculate("1", "0", Operation.MULTIPLICATION));
        assertEquals("0", calculator.calculate("0", "1", Operation.MULTIPLICATION));
        assertEquals("1", calculator.calculate("1", "1", Operation.MULTIPLICATION));
        assertEquals("4", calculator.calculate("2", "2", Operation.MULTIPLICATION));

        assertEquals("19", calculator.calculate("5", "5", Operation.MULTIPLICATION));
        assertEquals("14", calculator.calculate("A", "2", Operation.MULTIPLICATION));
        assertEquals("186", calculator.calculate("27", "A", Operation.MULTIPLICATION));
        assertEquals("8653", calculator.calculate("89", "FB", Operation.MULTIPLICATION));

        assertEquals("-1", calculator.calculate("1", "-1", Operation.MULTIPLICATION));
        assertEquals("1", calculator.calculate("-1", "-1", Operation.MULTIPLICATION));
        assertEquals("-1A1E5E", calculator.calculate("-FFF", "1A2", Operation.MULTIPLICATION));
    }

    @Test
    public void testCalculateFor30NumberSystem() throws Exception {
        CalculatorImpl calculator30 = new CalculatorImpl(30, 30);

        assertEquals("0", calculator30.calculate("0", "0", Operation.MULTIPLICATION));
        assertEquals("0", calculator30.calculate("1", "0", Operation.MULTIPLICATION));
        assertEquals("0", calculator30.calculate("0", "1", Operation.MULTIPLICATION));
        assertEquals("4", calculator30.calculate("2", "2", Operation.MULTIPLICATION));

        assertEquals("O9EO6", calculator30.calculate("TR", "OBS", Operation.MULTIPLICATION));
        assertEquals("-4H350", calculator30.calculate("-M7", "650", Operation.MULTIPLICATION));
    }

    @Test
    public void testGetValidationErrorMessage() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(34, 34);

        for (String s : regularStrings) {
            Assert.assertNull(calculator.getValidationErrorMessage(s));
        }

        for (String s : incorrectStrings) {
            Assert.assertNotNull(calculator.getValidationErrorMessage(s));
        }
    }

    @Test
    public void testGetValidationErrorMessageForLongStrings() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(10, 2);
        assertNull(calculator.getValidationErrorMessage("12"));
        assertNotNull(calculator.getValidationErrorMessage("123"));
    }

    @Test
    public void testGetValidationErrorMessageForIncorrectNumberSystem() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(2, 10);
        assertNull(calculator.getValidationErrorMessage("11"));
        assertNotNull(calculator.getValidationErrorMessage("12"));
    }

    @Test
    public void testIntValueToString() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(16, 10);

        assertEquals("1", calculator.intValueToString(1));
        assertEquals("F", calculator.intValueToString(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntValueToStringIncorrectNumberSystem() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(16, 10);

        assertEquals("E", calculator.intValueToString(16));
    }

    @Test
    public void testCharValueToInt() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(16, 10);

        assertEquals(0, calculator.charValueToInt('0'));
        assertEquals(1, calculator.charValueToInt('1'));
        assertEquals(9, calculator.charValueToInt('9'));
        assertEquals(10, calculator.charValueToInt('A'));
        assertEquals(35, calculator.charValueToInt('Z'));
    }

    @Test
    public void testConvertToValueInTwentyNumberSystem() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(16, 10);

        assertEquals(new BigInteger("1"), calculator.convertToValueInTwentyNumberSystem("1"));
        assertEquals(new BigInteger("10"), calculator.convertToValueInTwentyNumberSystem("A"));
        assertEquals(new BigInteger("16"), calculator.convertToValueInTwentyNumberSystem("10"));
        assertEquals(new BigInteger("3567"), calculator.convertToValueInTwentyNumberSystem("DEF"));

        assertEquals(new BigInteger("0"), calculator.convertToValueInTwentyNumberSystem("0"));

        assertEquals(new BigInteger("-171"), calculator.convertToValueInTwentyNumberSystem("-AB"));
        assertEquals(new BigInteger("-1"), calculator.convertToValueInTwentyNumberSystem("-1"));
    }

    @Test
    public void testConvertToValueInSpecefiedNumberSystem() throws Exception {
        CalculatorImpl calculator = new CalculatorImpl(16, 10);

        assertEquals("1", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("1")));
        assertEquals("A", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("10")));
        assertEquals("10", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("16")));
        assertEquals("DEF", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("3567")));

        assertEquals("0", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("0")));

        assertEquals("-AB", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("-171")));
        assertEquals("-1", calculator.convertToValueInSpecefiedNumberSystem(new BigInteger("-1")));
    }
}