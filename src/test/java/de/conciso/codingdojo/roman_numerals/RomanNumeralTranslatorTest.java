package de.conciso.codingdojo.roman_numerals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralTranslatorTest {

    RomanNumeralTranslator cut_;

    @BeforeEach
    void setUp() {
        cut_ = new RomanNumeralTranslatorImpl();
    }

    @Test
    public void testEmptyStringReturnsZero() {
        assertEquals(0, cut_.translateRomanNumeral(""));
    }

    @Test
    void testThatNullThrowsException() {
        assertThrows(NullPointerException.class, () -> cut_.translateRomanNumeral(null));
    }

    @ParameterizedTest
    @ValueSource( strings = {"a", "A", "b", "B", "c", "K", "€", "@", ":-)" })
    void testThatIllegalCharacterThrowsIllegalArgumentException( final String inputCharacter ) {
        assertThrows( IllegalArgumentException.class, () -> cut_.translateRomanNumeral( inputCharacter ) );
    }

    @ParameterizedTest
    @CsvSource({"I, 1", "V, 5", "X, 10", "L, 50", "C, 100", "D, 500", "M, 1000"})
    void testThatOneCharacterReturnsCorrectNumber( final String inputCharacter, final int expectedResult ) {
        assertEquals( expectedResult, cut_.translateRomanNumeral( inputCharacter ));
    }

    @ParameterizedTest
    @CsvSource({"II, 2", "XXX, 30", "CC, 200", "MMM, 3000"})
    void testThatEqualRomanCharactersReturnsExpectedValue( final String inputCharacter, final int expectedResult ) {
        assertEquals( expectedResult, cut_.translateRomanNumeral( inputCharacter ));
    }

}