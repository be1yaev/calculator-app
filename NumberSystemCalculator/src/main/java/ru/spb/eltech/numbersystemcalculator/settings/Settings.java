package ru.spb.eltech.numbersystemcalculator.settings;

import java.io.PrintStream;

/**
 * Created by andrey on 10.04.2016.
 */
public interface Settings
{
    /**
     * Установить цвет шрифта для потока вывода
     *
     * @param c цвет шрифта
     * @param ps поток вывода
     */
    void setFontColor(Color c, PrintStream ps);

    /**
     * Сбросить текущий цвет шрифта для определенного потока
     *
     * @param ps поток вывода для сброса цвета
     */
    void resetFontColor(PrintStream ps);
}