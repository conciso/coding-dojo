package de.conciso.codingdojo.roman_numerals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralTranslatorImplTest {
    private RomanNumeralTranslator cut;

    @BeforeEach
    public void setup() {
        cut = new RomanNumeralTranslatorImpl();
    }

    @ParameterizedTest
    @MethodSource({"romanAtoms", "romanMolecules"})
    public void testTranslateCorrectly(String romanAtom, int arabicValue){
        checkTranslationResult(romanAtom,arabicValue);
    }

    private void checkTranslationResult(String roman, int expectedArabic) {

        int result = cut.translateRomanNumeral(roman);

        assertEquals(expectedArabic,result);
    }

    static Stream<Arguments> romanAtoms() {
        return Stream.of(
                Arguments.of("I", 1),
                Arguments.of("V", 5),
                Arguments.of("X", 10),
                Arguments.of("L", 50),
                Arguments.of("C", 100),
                Arguments.of("D", 500),
                Arguments.of("M", 1000)
        );
    }

    static Stream<Arguments> romanMolecules() {
        return Stream.of(
                Arguments.of("II", 2),
                Arguments.of("XX", 20),
                Arguments.of("XII", 12),
                Arguments.of("III", 3)
        );
    }

    @Test
    public void testNullThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, ()->cut.translateRomanNumeral(null));
    }


    @Test
    public void testKThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, ()->cut.translateRomanNumeral("K"));
    }
}