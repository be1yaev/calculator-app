package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by andry on 18.04.2016.
 */
public class PositiveNumbersTest {

    private final String NEGATIVE_NUMBER_ERROR_MESSAGE_RESOURCE = "negative_number";

    private final ResourceBundle locale = ResourceBundle.getBundle("output");

    private final Calculator calculator = new CalculatorImpl(16, 16);

    private final String[] positiveNumbers = new String[] {
            "0",
            "1",
            "9",
            "A",
            "F",
            "10",
            "FFFF",
    };

    private final String[] negativeNumbers = new String[] {
            "-0",
            "-1",
            "-9",
            "-A",
            "-F",
            "-10",
            "-FFFF",
    };

    @Test
    public void testPositiveNumbersValidation()
    {
        // Проверяем, что все положительные числа проходят валидацию
        for (String numberString : positiveNumbers)
        {
            boolean isValid = calculator.validateNumberString(numberString);
            Assert.assertTrue(isValid);
        }
    }

    @Test
    public void testNegativeNumbersValidation()
    {
        final String realErrorMessage = locale.getString(NEGATIVE_NUMBER_ERROR_MESSAGE_RESOURCE);
        Assert.assertNotNull(realErrorMessage);

        // Проверяем, что все отрицательные числа не проходят валидацию
        for (String numberString : negativeNumbers)
        {
            boolean isValid = calculator.validateNumberString(numberString);
            Assert.assertFalse(isValid);

            // Проверяем, что используется корректное сообщение валидации
            String errorMessage = calculator.getValidationErrorMessage(numberString);
            Assert.assertEquals(realErrorMessage, errorMessage);
        }
    }
}
