package ru.spb.eltech.numbersystemcalculator.logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by andrey on 10.04.2016.
 */
public class LocalizationTest
{
    private final String VALID_BUNDLE_NAME = "output";
    private final String INVALID_BUNDLE_NAME = "invalid_name";

    private final String INPUT_FIRST_NUMBER_RU_MESSAGE = "Введите первое число:";
    private final String INPUT_FIRST_NUMBER_EN_MESSAGE = "Input first number:";

    private final String INPUT_FIRST_NUMBER_RES_NAME = "input1";

    private final String INVALID_RESOURCE_NAME = "invalid_res_name";

    private final String RU = "ru";
    private final String EN = "en";

    private final String FR = "fr";
    private final String TR = "tr";

    @Test
    public void testGetResourceBundle()
    {
        // Вытаскиваем настоящий бандл для текущей локали / дефолтный
        Assert.assertNotNull(ResourceBundle.getBundle(VALID_BUNDLE_NAME));

        // Вытаскиваем бандлы для валидных локалей
        ResourceBundle ru = ResourceBundle.getBundle(VALID_BUNDLE_NAME, new Locale(RU));
        ResourceBundle en = ResourceBundle.getBundle(VALID_BUNDLE_NAME, new Locale(EN));

        Assert.assertNotNull(ru);
        Assert.assertNotNull(en);

        // Проверяем, что они разные
        Assert.assertNotEquals(ru, en);

        // Вытаскиваем бандлы для несуществующих локалей
        ResourceBundle fr = ResourceBundle.getBundle(VALID_BUNDLE_NAME, new Locale(FR));
        ResourceBundle tr = ResourceBundle.getBundle(VALID_BUNDLE_NAME, new Locale(TR));

        // Проверяем, что они есть (дефолтная локаль)
        Assert.assertNotNull(fr);
        Assert.assertNotNull(tr);

        // Проверяем, что они одинаковые
        Assert.assertEquals(fr, tr);

        try
        {
            // Вытаскиваем несуществующий бандл
            ResourceBundle.getBundle(INVALID_BUNDLE_NAME);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof MissingResourceException);
        }
    }

    @Test
    public void testGetLocalizedMessage()
    {
        // Вытаскиваем бандлы для валидных локалей
        ResourceBundle ru = ResourceBundle.getBundle(VALID_BUNDLE_NAME, new Locale(RU));
        ResourceBundle en = ResourceBundle.getBundle(VALID_BUNDLE_NAME, new Locale(EN));

        Assert.assertEquals(INPUT_FIRST_NUMBER_RU_MESSAGE, ru.getString(INPUT_FIRST_NUMBER_RES_NAME));
        Assert.assertEquals(INPUT_FIRST_NUMBER_EN_MESSAGE, en.getString(INPUT_FIRST_NUMBER_RES_NAME));

        try
        {
            // Вытаскиваем несуществующий ресурс
            Assert.assertNull(en.getString(INVALID_RESOURCE_NAME));
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof MissingResourceException);
        }
    }
}
