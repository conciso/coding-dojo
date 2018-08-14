package de.conciso.codingdojo.fizzbuzz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzTest {

    private FizzBuzzImpl cut_;

    @BeforeEach
    void setUp() {
        cut_ = new FizzBuzzImpl();
    }

    @ParameterizedTest
    @DisplayName("Test for correct array size computation")
    @MethodSource("arrayLengthParameters")
    void testForCorrectyArrayLength_generic(int firstParam, int lastParam, int expectedVal) {
        assertEquals(expectedVal, cut_.createFizzBuzz(firstParam, lastParam).length);
    }

    private static Stream<Arguments> arrayLengthParameters() {
        return Stream.of(
                Arguments.of(5, 3, 0),
                Arguments.of(3, 5, 3),
                Arguments.of(42, 42, 1)
        );
    }
    @ParameterizedTest
    @DisplayName("Test for correct array content")
    @MethodSource("arrayContentParameters")
    void testForCorrectyArrayContent_generic(int firstParam, int lastParam, String[] expectedContent) {
        assertArrayEquals( expectedContent, cut_.createFizzBuzz(firstParam, lastParam));
    }

    private static Stream<Arguments> arrayContentParameters() {
        return Stream.of(
                Arguments.of(1, 1, new String[]{"1"}),
                Arguments.of(3, 3, new String[]{"Fizz"}),
                Arguments.of(5, 5, new String[]{"Buzz"}),
                Arguments.of(15, 15, new String[]{"FizzBuzz"})

        );
    }
}