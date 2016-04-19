package ru.spb.eltech.numbersystemcalculator.logic;

import ru.spb.eltech.numbersystemcalculator.exception.ExceedResultLenghtLimitException;

/**
 * Created by andrey on 29.02.16.
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
    String calculate(String firstArgument, String secondArgument, Operation operation) throws ExceedResultLenghtLimitException;

    /**
     * Валидация строкового представления числа
     *
     * @param numberValue строковое представление числа
     * @return true, если валидация прошла успешно, false, если возникли ошибки
     */
    boolean validateNumberString(String numberValue);

    /**
     * Возвращает описание ошибки при валидации, если она есть
     *
     * @param numberValue строковое представление числа
     * @return описание возникшей ошибки при обработке строки с числом, либо null, если ошибок нет
     */
    String getValidationErrorMessage(String numberValue);
}
