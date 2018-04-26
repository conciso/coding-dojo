package de.conciso.codingdojo.roman_numerals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanNumeralTranslatorImplTest {


    private RomanNumeralTranslator romanNumeralTranslator;

    @BeforeEach
    public void instantiateTranslator(){
         romanNumeralTranslator = new RomanNumeralTranslatorImpl();
    }

    @ParameterizedTest
    @MethodSource({"invalidAdditiveRomanNumeralProvider"})
    void checkThatInvalidNumeralThrowsException(String romanNumeral) {
        assertThrows(IllegalArgumentException.class, () -> romanNumeralTranslator.translateRomanNumeral(romanNumeral));
    }

    @Test
    void checkThatNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> romanNumeralTranslator.translateRomanNumeral(null));
    }

    @ParameterizedTest
    @MethodSource({"expectedResultAndSimpleRomanNumeralProvider", "expectedResultAdditiveRomanNumeralProvider"})
    void checkValidIntegerResults( int expectedResult, String romanNumeral) {
        assertEquals(expectedResult, romanNumeralTranslator.translateRomanNumeral(romanNumeral));
    }

    static Stream<Arguments> expectedResultAndSimpleRomanNumeralProvider() {
        return Stream.of(
                Arguments.of(1, "I"),
                Arguments.of(5, "V"),
                Arguments.of(10, "X"),
                Arguments.of(50, "L"),
                Arguments.of(100, "C"),
                Arguments.of(500, "D"),
                Arguments.of(1000, "M")
        );
    }

    static Stream<Arguments> expectedResultAdditiveRomanNumeralProvider() {
        return Stream.of(
                Arguments.of(2, "II"),
                Arguments.of(6, "VI"),
                Arguments.of(20, "XX"),
                Arguments.of(23, "XXIII"),
                Arguments.of(32, "XXXII"),
                Arguments.of(36, "XXXVI"),
                Arguments.of(53, "LIII"),
                Arguments.of(200, "CC"),
                Arguments.of(600, "DC"),
                Arguments.of(601, "DCI"),
                Arguments.of(2000, "MM")
        );
    }

    static Stream<Arguments> invalidAdditiveRomanNumeralProvider() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("IIII"),
                Arguments.of("XXXX"),
                Arguments.of("CCCC"),
                Arguments.of("MMMM"),
                Arguments.of("VV"),
                Arguments.of("LL"),
                Arguments.of("DD")
        );
    }

}
