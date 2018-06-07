package de.conciso.codingdojo.roman_numerals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralTranslatorTest {

    RomanNumeralTranslator cut;

    @BeforeEach
    void setup() {
        cut = new RomanNumeralTranslatorImpl();
    }

    @Nested
    class GivenSingleLetter {

        @ParameterizedTest
        @CsvSource({"I, 1", "V, 5", "X, 10", "L, 50", "C, 100", "D, 500", "M, 1000"})
        void whenInputIsCorrect_thenReturnCorrectNumber(String literal, int expected) {

            int actual = cut.translateRomanNumeral(literal);

            assertEquals(expected, actual);

        }

        @ParameterizedTest
        @ValueSource(strings = {"i", "v", "x", "l", "c", "d", "m"})
        void whenLowerCaseNumerals_thenThrowIllegalArgumentException(String literal) {

            assertThrows(IllegalArgumentException.class,() -> cut.translateRomanNumeral(literal));

        }

        @ParameterizedTest
        @ValueSource(strings ={" ", "$", "a", "K", "\t", "1"})
        void whenNoNumeral_thenThrowIllegalArgumentException(String literal) {

            assertThrows(IllegalArgumentException.class,() -> cut.translateRomanNumeral(literal));

        }
    }

    @Nested
    class GivenMultipleLetters {

        @Nested
        class WhenAdditiveLetters {
            @ParameterizedTest
            @CsvSource({"II,2","III,3", "XX, 20", "XXX,30", "CC, 200", "CCC, 300", "MM, 2000", "MMM,3000" })
            void whenValidSameNumerals_thenReturnCorrectNumber(String numeral, int expected) {
                int actual = cut.translateRomanNumeral(numeral);

                assertEquals(expected, actual);
            }

            @ParameterizedTest
            @ValueSource(strings ={"IIII", "VV", "XXXX", "LL", "CCCC", "DD", "MMMM"})
            void whenIncorrectNumberOfCorrectNumerals_thenThrowIllegalArgumentException(String numeral){
                assertThrows(IllegalArgumentException.class,() -> cut.translateRomanNumeral(numeral));

            }

        }

    }

    @Test
    void givenNull_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() -> cut.translateRomanNumeral(null));
    }
}