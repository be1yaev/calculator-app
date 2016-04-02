package ru.spb.eltech.numbersystemcalculator.logic;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

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
            throw new IllegalArgumentException("Некорректная система счисления."
                    + " Значение должно быть в пределах от 2 до " + (MAX_DIGIT_VALUE - 1));
        }

        this.NUMBER_SYSTEM = numberSystem;
        this.POSSIBLE_OPERATIONS = possibleOperations;
        this.MAX_COUNT_OF_DIGITS = maxCountOfDigits;
    }

    public String calculate(String firstArgument, String secondArgument, Operation operation) {

        if (!POSSIBLE_OPERATIONS.contains(operation)) {
            throw new IllegalStateException("Выбранная операция не поддерживается");
        }

        BigInteger firstNumber = convertToValueInTwentyNumberSystem(firstArgument);
        BigInteger secondNumber = convertToValueInTwentyNumberSystem(secondArgument);

        BigInteger  result;

        switch (operation) {
            case MULTIPLICATION:
                result = firstNumber.multiply(secondNumber);
                break;
            default:
                throw new IllegalStateException("Операция не реализована");
        }

        return convertToValueInSpecefiedNumberSystem(result);
    }

    public boolean validateNumberString(String numberValue) {
        return getValidationErrorMessage(numberValue) == null ? true : false;
    }

    public String getValidationErrorMessage(String numberValue) {
        if (numberValue == null || numberValue.trim().isEmpty()) {
            return "Пустая строка вместо числа";
        }

        String formattedValue = numberValue.trim().toUpperCase();
        char[] charArray = formattedValue.toCharArray();

        if (charArray.length > MAX_COUNT_OF_DIGITS) {
            return "Слишком длинное число. Максимально возможная длина: " + MAX_COUNT_OF_DIGITS;
        }

        if (charArray[0] == '-') {
            charArray = Arrays.copyOfRange(charArray, 1, charArray.length);
        }


        for (char c : charArray) {
            if ((c < '0' || c > '9') && (c < FIRST_LITERAL_CODE || c > LAST_LITERAL_CODE) && c != '-') {
               return "Обработан некорректный символ: '" + c
                       + ((c == '.' || c == ',') ? "' (Число должно быть целым)" : "'");
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
            throw new IllegalArgumentException(
                    "Введенное число не соответствует выбранной системе счисления (" + NUMBER_SYSTEM + ")"
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
            throw new IllegalArgumentException("Обработан некорректный символ: '" + value + "'");
        }
    }

    protected BigInteger convertToValueInTwentyNumberSystem(String value) {
        return new BigInteger(value, NUMBER_SYSTEM);
    }

    protected String convertToValueInSpecefiedNumberSystem(BigInteger value) {
        return value.toString(NUMBER_SYSTEM).toUpperCase();
    }
}
