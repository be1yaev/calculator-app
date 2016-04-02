package ru.spb.eltech.numbersystemcalculator.logic;

/**
 * Created by andrey on 29.02.16.
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

    /**
     * ��������� ���������� ������������� �����
     *
     * @param numberValue ��������� ������������� �����
     * @return true, ���� ��������� ������ �������, false, ���� �������� ������
     */
    boolean validateNumberString(String numberValue);

    /**
     * ���������� �������� ������ ��� ���������, ���� ��� ����
     *
     * @param numberValue ��������� ������������� �����
     * @return �������� ��������� ������ ��� ��������� ������ � ������, ���� null, ���� ������ ���
     */
    String getValidationErrorMessage(String numberValue);
}
