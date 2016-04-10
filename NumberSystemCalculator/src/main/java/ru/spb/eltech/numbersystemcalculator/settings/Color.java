package ru.spb.eltech.numbersystemcalculator.settings;

/**
 * Created by andrey on 10.04.2016.
 */
public enum Color
{
    White(null),
    Black(null),
    Reset(null);

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
