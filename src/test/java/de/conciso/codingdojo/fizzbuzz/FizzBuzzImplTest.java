package de.conciso.codingdojo.fizzbuzz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzImplTest {
    private FizzBuzzImpl fizz = new FizzBuzzImpl();
    private final String f = "Fizz";
    private final String b = "Buzz";
    private final String fb = "FizzBuzz";

    @Test
    void ifInitNotNull() {
        assertNotNull(fizz);
    }

    @Test
    void createFizzBuzzReturnsNotNull() {
        assertNotNull(fizz.createFizzBuzz(1, 100));
    }

    @Test
    void createFizzBuzzReturnsFizzForInputThree() {
        assertThat(fizz.createFizzBuzz(3, 3)).contains(f);
    }

    @Test
    void createFizzBuzzReturnsBuzzForInputFive() {
        assertThat(fizz.createFizzBuzz(5, 5)).contains(b);
    }

    @Test
    void createFizzBuzzReturnsFizzBuzzForInputFifteen() {
        assertThat(fizz.createFizzBuzz(15, 15)).contains(fb);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 8, 11})
    void createFizzBuzzReturnsMultipleInput(int argument) {
        assertThat(fizz.createFizzBuzz(argument, argument)).contains("" + argument);

    }

    @ParameterizedTest
    @ValueSource(ints = {7, 8, 11})
    void createFizzBuzzReturnsMultipleInputDoesNotContainsBorForFB(int argument) {
        assertThat(fizz.createFizzBuzz(argument, argument)).doesNotContain(b, f, fb);

    }

    @Test
    void createFizzBuzzReturnsMultipleMixedInput() {
        assertThat(fizz.createFizzBuzz(2, 20)).contains(b, f, fb, "11");

    }



}