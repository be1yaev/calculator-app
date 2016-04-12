package ru.spb.eltech.numbersystemcalculator.logic;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Andrew on 01.03.2016.
 */
public class CalculatorImpl implements Calculator {

    private final int FIRST_LITERAL_CODE = 'A';
    private final int LAST_LITERAL_CODE = 'Z';
    private final int MAX_DIGIT_VALUE = 10 + LAST_LITERAL_CODE - FIRST_LITERAL_CODE;

    private final int NUMBER_SYSTEM;
    private final int MAX_COUNT_OF_DIGITS;
    private final Collection<Operation> POSSIBLE_OPERATIONS;

    private final ResourceBundle locale = ResourceBundle.getBundle("output", new Locale("ru"));

    /**
     * Создание объекта калькулятора с выбранной системой счисления и максимальным числом разрядов
     *
     * @param numberSystem выбранная система счисления
     * @param maxCountOfDigits максимальное количество разрядов
     */
    public CalculatorImpl(int numberSystem, int maxCountOfDigits)
    {
        this(numberSystem, maxCountOfDigits, Arrays.asList((Operation.values())));
    }

    /**
     * Создание объекта калькулятора с выбранной системой счисления, максимальным числом разрядов и возможностью
     * только определенных операций
     *
     * @param numberSystem выбранная система счисления
     * @param maxCountOfDigits максимальное количество разрядов
     * @param possibleOperations возможные операции между числами
     */
    public CalculatorImpl(int numberSystem, int maxCountOfDigits, Collection<Operation> possibleOperations) {

        if (numberSystem < 2 || numberSystem >= MAX_DIGIT_VALUE) {
            throw new IllegalArgumentException(locale.getString("not_supported_number_system") + (MAX_DIGIT_VALUE - 1));
        }

        this.NUMBER_SYSTEM = numberSystem;
        this.POSSIBLE_OPERATIONS = possibleOperations;
        this.MAX_COUNT_OF_DIGITS = maxCountOfDigits;
    }

    public String calculate(String firstArgument, String secondArgument, Operation operation) {

        if (!POSSIBLE_OPERATIONS.contains(operation)) {
            throw new IllegalStateException(locale.getString("not_possible_operation"));
        }

        BigInteger firstNumber = convertToValueInTwentyNumberSystem(firstArgument);
        BigInteger secondNumber = convertToValueInTwentyNumberSystem(secondArgument);

        BigInteger  result;

        switch (operation) {
            case MULTIPLICATION:
                result = firstNumber.multiply(secondNumber);
                break;
            default:
                throw new IllegalStateException(locale.getString("not_implemented_operation"));
        }

        return convertToValueInSpecefiedNumberSystem(result);
    }

    public boolean validateNumberString(String numberValue) {
        return getValidationErrorMessage(numberValue) == null ? true : false;
    }

    public String getValidationErrorMessage(String numberValue) {
        if (numberValue == null || numberValue.trim().isEmpty()) {
            return locale.getString("empty_string");
        }

        String formattedValue = numberValue.trim().toUpperCase();
        char[] charArray = formattedValue.toCharArray();

        if (charArray.length > MAX_COUNT_OF_DIGITS) {
            return locale.getString("very_long_number") + ": " + MAX_COUNT_OF_DIGITS;
        }

        if (charArray[0] == '-') {
            charArray = Arrays.copyOfRange(charArray, 1, charArray.length);
        }


        for (char c : charArray) {
            if ((c < '0' || c > '9') && (c < FIRST_LITERAL_CODE || c > LAST_LITERAL_CODE) && c != '-') {
               return locale.getString("incorect_symbol") + ": '" + c
                       + ((c == '.' || c == ',') ? "' " + locale.getString("need_whole_number") : "'");
            }

            try {
                intValueToString(charValueToInt(c));
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }
        }

        return null;
    }

    protected String intValueToString(int value) {
        if (value < 0 || value > NUMBER_SYSTEM - 1) {
            throw new IllegalArgumentException(locale.getString("incorrect_number_system") + " (" + NUMBER_SYSTEM + ")"
            );
        }

        if (value > 9) {
            char charValue = (char) (FIRST_LITERAL_CODE + (value - 10));
            return Character.toString(charValue);
        }

        return Integer.toString(value);
    }

    protected int charValueToInt(char value) {
        if (value >= '0' && value <= '9') {
            return value - '0';
        } else if (value >= FIRST_LITERAL_CODE && value <= LAST_LITERAL_CODE) {
            return value - FIRST_LITERAL_CODE + 10;
        } else {
            throw new IllegalArgumentException(locale.getString("incorect_symbol") + ": '" + value + "'");
        }
    }

    protected BigInteger convertToValueInTwentyNumberSystem(String value) {
        return new BigInteger(value, NUMBER_SYSTEM);
    }

    protected String convertToValueInSpecefiedNumberSystem(BigInteger value) {
        return value.toString(NUMBER_SYSTEM).toUpperCase();
    }
}
