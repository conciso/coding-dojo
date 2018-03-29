package de.conciso.codingdojo.roman_numerals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralTranslatorImplTest {

    private RomanNumeralTranslatorImpl cut;

    @BeforeEach
    public void setUp() {
        cut = new RomanNumeralTranslatorImpl();
    }

    @ParameterizedTest
    @CsvSource({"I, 1","V, 5","X, 10","L, 50","C, 100","D, 500","M, 1000"})
    public void testReturnsValueForSingleNumerals(String numeral, int expected)
    {
        assertEquals(expected, cut.translateRomanNumeral(numeral));
    }

    @ParameterizedTest
    @CsvSource({"VI, 6","MMMDCCCLXXXVIII, 3888", "LXVI, 66"})
    public void testReturnsValueForAdditionNumerals(String numeral, int expected)
    {
        assertEquals(expected, cut.translateRomanNumeral(numeral));
    }

    @ParameterizedTest
    @CsvSource({"IX, 9","XLIX, 49", "MMCMXCIX, 2999"})
    public void testReturnsValueForSubtractionNumerals(String numeral, int expected)
    {
        assertEquals(expected, cut.translateRomanNumeral(numeral));
    }

    @ParameterizedTest
    @ValueSource(strings = {"IIX", "IIII", "foo", "IC", "VV", "MCCM", "Mfoo"})
    public void testShouldThrowIllegalArgumentExceptionForIllegalParameters(String numeral)
    {
        assertThrows(IllegalArgumentException.class, () -> cut.translateRomanNumeral(numeral));
    }


}