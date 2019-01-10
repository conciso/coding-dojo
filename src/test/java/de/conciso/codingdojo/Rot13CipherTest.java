package de.conciso.codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Rot13CipherTest {

    private RotCipher cut;

    @BeforeEach
    void setUp() {
        cut = new RotCipherImpl();
    }

    @ParameterizedTest
    @CsvSource({"a, n", "b, o", "z, 0", "ä, a", "1, d", "9, l", "0, m"})
    void encryptSingleLetters(String input, String expectedValue) {
        String actual = cut.encrypt(input);

        assertThat(actual, is(expectedValue));
    }

    @ParameterizedTest
    @ValueSource(strings = {"!", " ", ",", ".", "\n", "\t"})
    void lettersNotInAlphabetAreNotEncrypted(String input) {
        String actual = cut.encrypt(input);

        assertThat(actual, is(input));
    }

    @Test
    void throwIllegalArgumentExceptionIfInputIsNull(){
        assertThrows(IllegalArgumentException.class, () -> cut.encrypt(null));
    }

    @Test
    void emptyStringEncryptsToEmptyString() {
        String actual = cut.encrypt("");

        assertThat(actual, is(""));
    }

    @Test
    void encryptText() {
        String actual = cut.encrypt("abcdefghijklmnopqrstuvwxyzäöü1234567890");

        assertThat(actual, is("nopqrstuvwxyzäöü1234567890abcdefghijklm"));
    }

    @Test
    void encryptHelloWorld() {
        String actual = cut.encrypt("Hello, World!");

        assertThat(actual, is("uryyö, 7ö2yq!"));
    }

    @ParameterizedTest
    @CsvSource({"A, n", "B, o", "Z, 0", "Ä, a"})
    void encryptSingleCapitalLetters(String input, String expectedValue) {
        String actual = cut.encrypt(input);

        assertThat(actual, is(expectedValue));
    }
}