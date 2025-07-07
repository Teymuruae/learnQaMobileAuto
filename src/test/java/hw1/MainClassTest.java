package hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    void shouldReturnCorrectNumber(){
        final int expected = 14;
        Assertions.assertEquals(expected, mainClass.getLocalNumber(), ("number is wrong. " +
                "Expected: %d, Actual: %d,").formatted(expected, mainClass.getLocalNumber()));
    }
}
