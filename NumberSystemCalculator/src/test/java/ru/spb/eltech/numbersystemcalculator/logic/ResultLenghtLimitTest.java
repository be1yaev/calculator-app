package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Assert;
import org.junit.Test;
import ru.spb.eltech.numbersystemcalculator.exception.ExceedResultLenghtLimitException;

/**
 * Created by andry on 19.04.2016.
 */
public class ResultLenghtLimitTest {

    private final int RESULT_MAX_LENGTH = 2;

    private final Calculator calculator = new CalculatorImpl(10, 10, RESULT_MAX_LENGTH);

    private final String[][] validPairNumbers = new String[][] {
            {"0", "1"},
            {"0", "1000"},
            {"1", "1"},
            {"1", "10"},
            {"1", "99"},
            {"2", "2"},
            {"9", "11"},
    };

    private final String[][] notValidPairNumbers = new String[][] {
            {"1", "100"},
            {"1", "1000"},
            {"10", "10"},
            {"50", "50"},
            {"9999", "9999"},
    };

    @Test
    public void testValidResult() throws ExceedResultLenghtLimitException {
        for (String[] pair : validPairNumbers)
        {
            String result = calculator.calculate(pair[0], pair[1], Operation.MULTIPLICATION);
            Assert.assertNotNull(result);
            Assert.assertTrue(result.length() <= RESULT_MAX_LENGTH);
        }
    }

    @Test
    public void testNotValidResult()
    {
        for (String[] pair : notValidPairNumbers)
        {
            try
            {
                calculator.calculate(pair[0], pair[1], Operation.MULTIPLICATION);
            }
            catch (Exception e)
            {
                Assert.assertTrue(e instanceof ExceedResultLenghtLimitException);
            }
        }
    }
}
