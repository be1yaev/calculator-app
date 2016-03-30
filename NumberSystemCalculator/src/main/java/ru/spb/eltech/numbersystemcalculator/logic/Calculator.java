package ru.spb.eltech.numbersystemcalculator.logic;

/**
 * Created by andrey on 29.03.16.
 */
public interface Calculator
{
    /**
     * ���������� ���������� �������� ����� ����� �����������
     *
     * @param firstArgument ��������� ������������� ������� �����
     * @param secondArgument ��������� ������������� ������� �����
     * @param operation ��������� �������� ����� �������
     * @return ��������� ���������� �������� � ���� �����
     */
    String calculate(String firstArgument, String secondArgument, Operation operation);
}
