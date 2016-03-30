package ru.spb.eltech.numbersystemcalculator;

import ru.spb.eltech.numbersystemcalculator.logic.Calculator;
import ru.spb.eltech.numbersystemcalculator.logic.CalculatorImpl;
import ru.spb.eltech.numbersystemcalculator.logic.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by andrey on 29.03.16.
 */
public class Main
{
    private static final int NUMBER_SYSTEM = 30;

    private static final Calculator calculator = new CalculatorImpl(NUMBER_SYSTEM);

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nВведите первое число: ");
        String firstNumber = getNumberFromScanner(in).toUpperCase();

        System.out.print("\nВведите второе число: ");
        String secondNumber = getNumberFromScanner(in).toUpperCase();

        String result = calculator.calculate(firstNumber, secondNumber, Operation.MULTIPLICATION);

        System.out.println("\nПроизведение двух введенных чисел равно: ");
        System.out.println(firstNumber + " x " + secondNumber + " = " + result);
    }

    public static String getNumberFromScanner(BufferedReader in) throws IOException {
        String number = in.readLine();

        return number;
    }
}
