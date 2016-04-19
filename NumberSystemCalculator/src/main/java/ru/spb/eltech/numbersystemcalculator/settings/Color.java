package ru.spb.eltech.numbersystemcalculator.settings;

/**
 * Created by andrey on 10.04.2016.
 */
public enum Color
{
    White("\u001B[97m"),
    Black("\u001B[38m"),
    Red("\u001B[31m"),
    BlackBackground("\u001B[40m"),
    Reset("\u001B[0m");

    Color(final String s)
    {
        COLOR_CODE = s;
    }

    private final String COLOR_CODE;

    public String getColorCode()
    {
        return COLOR_CODE;
    }
}
