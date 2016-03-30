package ru.spb.eltech.numbersystemcalculator.logic;

/**
 * Created by andrey on 29.03.16.
 */
public interface Calculator
{
    /**
     * Вычисления результата операции между двумя аргументами
     *
     * @param firstArgument строковое представление первого числа
     * @param secondArgument строковое представление второго числа
     * @param operation требуемая операция между числами
     * @return результат выполнения операции в виде числа
     */
    String calculate(String firstArgument, String secondArgument, Operation operation);
}
