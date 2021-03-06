package ru.spb.eltech.numbersystemcalculator.settings;

import java.io.PrintStream;

/**
 * Created by andrey on 10.04.2016.
 */
public class SettingsImpl implements Settings {

    static private SettingsImpl instance;

    private SettingsImpl()
    {

    };

    synchronized static public SettingsImpl getInstance()
    {
        if (instance == null)
        {
            return instance = new SettingsImpl();
        }

        return instance;
    }

    public void setFontColor(Color c, PrintStream ps) {
        ps.print(c.getColorCode());
    }

    public void resetFontColor(PrintStream ps) {
        ps.print(Color.Reset.getColorCode());
    }
}
