package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Test;
import ru.spb.eltech.numbersystemcalculator.Main;
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
    private final Settings settings = SettingsImpl.getInstance();

    @Test
    public void testChangingColor() throws IOException
    {
        // Создаем мок на поток вывода на консоль
        PrintStream psOutMock = mock(PrintStream.class);
        System.setOut(psOutMock);

        // Проверяем, что белый цвет устанавливается
        settings.setFontColor(Color.White, psOutMock);
        verify(psOutMock, times(1)).print(Color.White.getColorCode());

        // Проверяем, что цвет меняется на черный
        settings.setFontColor(Color.Black, psOutMock);
        verify(psOutMock, times(1)).print(Color.Black.getColorCode());

        // Проверяем, что цвет сброшен
        settings.resetFontColor(psOutMock);
        verify(psOutMock, times(1)).print(Color.Reset.getColorCode());
    }

    @Test
    public void testWhiteColorInMainApp() throws IOException
    {
        // Создаем мок на поток вывода на консоль
        PrintStream psOutMock = mock(PrintStream.class);
        System.setOut(psOutMock);

        // Тестируем, что внутри Main'a вызывалась смена цвета на белый
        Main main = new Main();

        // Эмулируем ввод числа
        BufferedReader readerMock = mock(BufferedReader.class);
        when(readerMock.readLine()).thenReturn("1");

        Main.setInReader(readerMock);

        // Прогоняем main
        Main.main(null);

        // Проверяем, что был вызван метод установки цвета, а затем его сброс
        verify(settings, times(1)).setFontColor(Color.White, psOutMock);
        verify(settings, times(1)).resetFontColor(psOutMock);
    }
}
