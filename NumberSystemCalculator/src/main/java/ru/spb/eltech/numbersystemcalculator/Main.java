package ru.spb.eltech.numbersystemcalculator;

import ru.spb.eltech.numbersystemcalculator.exception.ExceedResultLenghtLimitException;
import ru.spb.eltech.numbersystemcalculator.logic.Calculator;
import ru.spb.eltech.numbersystemcalculator.logic.CalculatorImpl;
import ru.spb.eltech.numbersystemcalculator.logic.Operation;
import ru.spb.eltech.numbersystemcalculator.settings.Color;
import ru.spb.eltech.numbersystemcalculator.settings.Settings;
import ru.spb.eltech.numbersystemcalculator.settings.SettingsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by andrey on 29.02.16.
 */
public class Main
{
    private static final int NUMBER_SYSTEM = 30;
    private static final int MAX_DIGITS_COUNT = 32;
    private static final int MAX_RESULT_COUNT_OF_DIGITS = 40;

    private static final ResourceBundle locale = ResourceBundle.getBundle("output", new Locale("ru"));

    private static BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));

    private static Settings settings = SettingsImpl.getInstance();

    private static Calculator calculator =
            new CalculatorImpl(NUMBER_SYSTEM, MAX_DIGITS_COUNT, MAX_RESULT_COUNT_OF_DIGITS,
                    Arrays.asList(Operation.MULTIPLICATION));

    public static void main(String[] args) throws IOException {
        settings.setFontColor(Color.White, System.out);

        welcome();

        while(true)
        {
            System.out.println("\n" + locale.getString("input1"));
            String firstNumber = getNumberFromScanner().toUpperCase();

            System.out.println("\n" + locale.getString("input2"));
            String secondNumber = getNumberFromScanner().toUpperCase();

            String result = null;
            try {
                result = calculator.calculate(firstNumber, secondNumber, Operation.MULTIPLICATION);
            } catch (ExceedResultLenghtLimitException e) {
                result = locale.getString("long_result");
            }

            System.out.println("\n" + locale.getString("result"));
            System.out.println(firstNumber + " x " + secondNumber + " = " + result);

            System.out.println("\n" + locale.getString("repeat"));
            inReader.readLine();

            System.out.println(locale.getString("separator"));
        }
    }

    private static void welcome() {
        System.out.println(locale.getString("separator"));
        System.out.println(locale.getString("welcome"));
        System.out.println(locale.getString("separator"));
    }

    public static String getNumberFromScanner() throws IOException {
        String errorMessage;
        String number = inReader.readLine();

        while ((errorMessage = calculator.getValidationErrorMessage(number)) != null) {
            System.out.println(locale.getString("error") + ": " + errorMessage
                    + locale.getString("repeat_or_exit"));
            System.out.print("\n" + locale.getString("repeat_input") + ": ");
            number = inReader.readLine();
        }

        return number;
    }

    public static void setInReader(BufferedReader inReader) {
        Main.inReader = inReader;
    }

    public static void setSettings(Settings settings) {
        Main.settings = settings;
    }

    public static void setCalculator(Calculator calculator) {
        Main.calculator = calculator;
    }
}
