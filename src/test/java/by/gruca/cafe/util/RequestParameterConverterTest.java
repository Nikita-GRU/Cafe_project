package by.gruca.cafe.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParameterConverterTest {
    private static final RequestParameterConverter converter = RequestParameterConverter.INSTANCE;

    @Test
    public void valueOfIntegerTest() throws UtilException {
        int expected = 6;
        int actual = converter.valueOfInteger("6");
        assertEquals(expected, actual);
    }
    @Test(expected =UtilException.class)
    public void valueOfIntegerWrongParameterTest() throws UtilException {
        int expected = 6;
        int actual = converter.valueOfInteger("6d");
    }

}