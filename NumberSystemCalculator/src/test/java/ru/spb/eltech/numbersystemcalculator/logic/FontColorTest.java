package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.spb.eltech.numbersystemcalculator.Main;
import ru.spb.eltech.numbersystemcalculator.exception.ExceedResultLenghtLimitException;
import ru.spb.eltech.numbersystemcalculator.settings.Color;
import ru.spb.eltech.numbersystemcalculator.settings.Settings;
import ru.spb.eltech.numbersystemcalculator.settings.SettingsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

/**
 * Created by andrey on 10.04.2016.
 */
public class FontColorTest
{
    static private final String EXCEPTION_MESSAGE = "AVOID_INFINITE_LOOP";

    static private final Settings SETTINGS = SettingsImpl.getInstance();

    static private final String NUMBER_FOR_RESULT_COLOR_TEST = "1";

    static private boolean isResultColorTestAlreadyExecuted = false;

    @Test
    public void testChangingColor() throws IOException
    {
        // Создаем мок на поток вывода на консоль
        PrintStream psOutMock = mock(PrintStream.class);
        System.setOut(psOutMock);

        // Проверяем, что белый цвет устанавливается
        SETTINGS.setFontColor(Color.White, psOutMock);
        verify(psOutMock, times(1)).print(Color.White.getColorCode());

        // Проверяем, что цвет меняется на черный
        SETTINGS.setFontColor(Color.Black, psOutMock);
        verify(psOutMock, times(1)).print(Color.Black.getColorCode());

        // Проверяем, что цвет сброшен
        SETTINGS.resetFontColor(psOutMock);
        verify(psOutMock, times(1)).print(Color.Reset.getColorCode());
    }

    @Test
    public void testWhiteColorInMainApp() throws IOException {
        // Создаем мок на поток вывода на консоль
        PrintStream psOutMock = mock(PrintStream.class);
        System.setOut(psOutMock);

        Main main = new Main();

        // Создаем мок настроек
        Settings settingsMock = mock(Settings.class);
        Main.setSettings(settingsMock);

        // Эмулируем эксепшн вместо ввода числа, чтобы избежать бескончный цикл
        BufferedReader readerMock = mock(BufferedReader.class);
        when(readerMock.readLine()).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        Main.setInReader(readerMock);

        // Прогоняем main
        try
        {
            Main.main(null);
        }
        catch (Exception e)
        {
            Assert.assertEquals(EXCEPTION_MESSAGE, e.getMessage());
        }

        // Проверяем, что был вызван метод установки белого цвета
        verify(settingsMock, times(1)).setFontColor(Color.White, psOutMock);
    }

    @Test
    public void testRedColorResult() throws IOException, ExceedResultLenghtLimitException {
        // Создаем мок на поток вывода на консоль
        PrintStream psOutMock = mock(PrintStream.class);
        System.setOut(psOutMock);

        Main main = new Main();

        // Создаем мок настроек
        final Settings settingsMock = mock(Settings.class);
        Main.setSettings(settingsMock);

        // Эмулируем ввод числа с клавиатуры
        BufferedReader readerMock = mock(BufferedReader.class);
        when(readerMock.readLine()).thenReturn(NUMBER_FOR_RESULT_COLOR_TEST);

        Main.setInReader(readerMock);

        // Мокаем метод калькулятора, который позволит прогнать нам один цикл ввода
        Calculator calculatorMock = mock(Calculator.class);
        when(calculatorMock.validateNumberString(anyString())).thenReturn(true);
        when(calculatorMock.calculate(anyString(), anyString(), any(Operation.class))).thenAnswer(
                new Answer<Object>() {
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        if (!isResultColorTestAlreadyExecuted) {
                            // Сбрасываем мок, чтобы непосредственно видеть то, что произшло после "подсчета" результата
                            reset(settingsMock);
                            isResultColorTestAlreadyExecuted = true;
                            return NUMBER_FOR_RESULT_COLOR_TEST;
                        } else {
                            throw new Exception(EXCEPTION_MESSAGE);
                        }
                    }
                });

        Main.setCalculator(calculatorMock);

        // Прогоняем main
        try
        {
            Main.main(null);
        }
        catch (Exception e)
        {
            Assert.assertEquals(EXCEPTION_MESSAGE, e.getMessage());
        }

        // Проверяем, что был вызван метод установки красного цвета
        verify(settingsMock, times(1)).setFontColor(Color.Red, psOutMock);

        // Проверяем, что был вызван метод установки белого цвета
        verify(settingsMock, times(1)).setFontColor(Color.White, psOutMock);
    }
}
