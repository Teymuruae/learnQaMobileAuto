package hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    void shouldReturnCorrectNumber(){
        final int expected = 14;
        Assertions.assertEquals(expected, mainClass.getLocalNumber(), "number is wrong. " +
                "Expected: %d, Actual: %d,".formatted(expected, mainClass.getLocalNumber()));
    }

    @Test
    void testGetClassNumber(){
         final int expected = 45;
         Assertions.assertEquals(expected, MainClass.getClassNumber(), "number is wrong. " +
                 "Expected: %d, Actual: %d,".formatted(expected, MainClass.getClassNumber()));
    }

    @Test
    void testGetClassString(){
        final String expected = "hello";
        Assertions.assertTrue(MainClass.getClassString().toLowerCase().contains(expected),
                "%s is not present in text: %s".formatted(expected, MainClass.getClassString()));
    }
}
