package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.spb.eltech.numbersystemcalculator.Main;
import ru.spb.eltech.numbersystemcalculator.exception.ExceedResultLenghtLimitException;
import ru.spb.eltech.numbersystemcalculator.settings.Color;
import ru.spb.eltech.numbersystemcalculator.settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by andrey on 10.04.2016.
 */
public class InputRepeatTest
{
    static private final int COUNT_OF_ITERATIONS = 5;
    static private int i = COUNT_OF_ITERATIONS;

    static private final String EXCEPTION_MESSAGE = "END_OF_ITERATIONS";

    private Answer<String> answerFromCalculator = new Answer<String>() {
        public String answer(InvocationOnMock invocationOnMock) throws Throwable {
            if (--InputRepeatTest.i > 0)
            {
                return "1";
            }
            else
            {
                throw new Exception(EXCEPTION_MESSAGE);
            }
        }
    };

    @Test
    public void testRepeatInput() throws IOException, ExceedResultLenghtLimitException {

        // Эмулируем ввод числа
        BufferedReader readerMock = mock(BufferedReader.class);
        when(readerMock.readLine()).thenReturn("1");

        Main.setInReader(readerMock);

        // Эмулируем ответ от калькулятора
        Calculator calculatorMock = mock(Calculator.class);
        when(calculatorMock.calculate(anyString(), anyString(), any(Operation.class))).
                thenAnswer(answerFromCalculator);

        Main.setCalculator(calculatorMock);

        try
        {
            // Прогоняем main
            Main.main(null);
        }
        catch (Exception e)
        {
            // Должно было быть выброшено исключение на последней итерации
            Assert.assertEquals(EXCEPTION_MESSAGE, e.getMessage());
        }


        // Проверяем, что был вызван метод ввода калькулятора ровно столько раз, сколько мы и хотели
        verify(calculatorMock, times(COUNT_OF_ITERATIONS)).
                calculate(anyString(), anyString(), any(Operation.class));
    }
}
