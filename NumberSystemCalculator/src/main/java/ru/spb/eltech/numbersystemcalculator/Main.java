package ru.spb.eltech.numbersystemcalculator;

import ru.spb.eltech.numbersystemcalculator.logic.Calculator;
import ru.spb.eltech.numbersystemcalculator.logic.CalculatorImpl;
import ru.spb.eltech.numbersystemcalculator.logic.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by andrey on 29.02.16.
 */
public class Main
{
    private static final int NUMBER_SYSTEM = 30;
    private static final int MAX_DIGITS_COUNT = 32;

    private static final Calculator calculator =
            new CalculatorImpl(NUMBER_SYSTEM, MAX_DIGITS_COUNT, Arrays.asList(Operation.MULTIPLICATION));

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        welcome();

        System.out.print("\nВведите первое число: ");
        String firstNumber = getNumberFromScanner(in).toUpperCase();

        System.out.print("\nВведите второе число: ");
        String secondNumber = getNumberFromScanner(in).toUpperCase();

        String result = calculator.calculate(firstNumber, secondNumber, Operation.MULTIPLICATION);

        System.out.println("\nПроизведение двух введенных чисел равно: ");
        System.out.println(firstNumber + " x " + secondNumber + " = " + result);
    }

    private static void welcome() {
        System.out.println("#####################################################################");
        System.out.println("                       Лабораторная работа №3");
        System.out.println("       Вычисление произведения чисел в 30-ой системе счисления");
        System.out.println("                 Выполнили студенты группы 1374:");
        System.out.println("                            Беляев А.А.");
        System.out.println("                            Зварич В.Н.");
        System.out.println("                          Кирьякова Е.А.");
        System.out.println("\n      (В любой момент для выхода из программы нажмите Ctrl+C)");
        System.out.println("#####################################################################");
    }

    public static String getNumberFromScanner(BufferedReader in) throws IOException {
        String errorMessage;
        String number = in.readLine();

        while ((errorMessage = calculator.getValidationErrorMessage(number)) != null) {
            System.out.println("Ошибка: " + errorMessage + ". Повторите ввод или нажмите Ctrl+C для выхода.");
            System.out.print("\nВведите число еще раз: ");
            number = in.readLine();
        }

        return number;
    }
}
