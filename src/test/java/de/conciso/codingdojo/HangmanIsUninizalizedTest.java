package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("A Hangman game is uninitalized")
class HangmanIsUninizalizedTest {

    private Hangman cut;

    @BeforeEach
    public void setUp() {
        cut = new HangmanImpl();
    }

    @Test
    @DisplayName("then the game is not running")
    void gameNotRunning() {
        Assertions.assertFalse(cut.isGameRunning());
    }

    @Test
    @DisplayName("then there are no tries")
    void noTries() {
        Assertions.assertEquals(0, cut.getNumberOfTries());
    }

    @Test
    @DisplayName("then there are no unsuccessful tries")
    void noUnsuccessfulTries() {
        Assertions.assertEquals(0, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    @DisplayName("the masked string is empty")
    void maskedStringEmpty () {
        Assertions.assertEquals("", cut.getMaskedPhrase());
    }

    @Test
    @DisplayName("then a guess throws an exception")
    void aGuessThrowsException() {
        assertThrows(Exception.class, () -> {
            cut.guessCharacter("");});
    }

}
