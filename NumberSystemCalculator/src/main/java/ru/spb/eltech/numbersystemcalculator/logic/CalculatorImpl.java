package ru.spb.eltech.numbersystemcalculator.logic;

import java.math.BigInteger;

/**
 * Created by andrey on 29.03.16.
 */
public class CalculatorImpl implements Calculator {

    private final int FIRST_LITERAL_CODE = 'A';
    private final int LAST_LITERAL_CODE = 'Z';

    private final int NUMBER_SYSTEM;

    /**
     * Создание объекта калькулятора с выбранной системой счисления
     *
     * @param numberSystem выбранная система счисления
     */
    public CalculatorImpl(int numberSystem) {

        this.NUMBER_SYSTEM = numberSystem;
    }

    public String calculate(String firstArgument, String secondArgument, Operation operation) {

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

    protected BigInteger convertToValueInTwentyNumberSystem(String value) {
        return new BigInteger(value, NUMBER_SYSTEM);
    }

    protected String convertToValueInSpecefiedNumberSystem(BigInteger value) {
        return value.toString(NUMBER_SYSTEM).toUpperCase();
    }
}
