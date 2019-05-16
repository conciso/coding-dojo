package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("A Hangman game is initialized with TEST")
class HangmanIsInitializedTest {

    private Hangman cut;

    @BeforeEach
    public void setUp() {
        cut = new HangmanImpl();
        cut.setNewPhrase("TEST");
    }

    @Test
    @DisplayName("then the game is running")
    void gameNotRunning() {
        Assertions.assertTrue(cut.isGameRunning());
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
    @DisplayName("then a successful guess returns true")
    void successfulGuess() {
        Assertions.assertTrue(cut.guessCharacter("T"));
    }

    @Test
    @DisplayName("then an unsuccessful guess returns false")
    void unsuccessfulGuess() {
        Assertions.assertFalse(cut.guessCharacter("Y"));
    }

    @Nested
    @DisplayName("with a successful guess")
    class WithSuccessfulGuess {
        @BeforeEach
        void setUp() {
            cut.guessCharacter("T");
        }

        @Test
        @DisplayName("there is one try")
        void noTries() {
            Assertions.assertEquals(1, cut.getNumberOfTries());
        }

        @Test
        @DisplayName("there are no unsuccessful tries")
        void noUnsuccessfulTries() {
            Assertions.assertEquals(0, cut.getNumberOfUnsuccessfulTries());
        }
    }

    @Nested
    @DisplayName("with an unsuccessful guess")
    class WithUnsuccessfulGuess {
        @BeforeEach
        void setUp() {
            cut.guessCharacter("X");
        }

        @Test
        @DisplayName("there is one try")
        void noTries() {
            Assertions.assertEquals(1, cut.getNumberOfTries());
        }

        @Test
        @DisplayName("there is one unsuccessful try")
        void noUnsuccessfulTries() {
            Assertions.assertEquals(1, cut.getNumberOfUnsuccessfulTries());
        }
    }
//
//        @Test
//        @DisplayName("the masked string is empty")
//        void maskedStringEmpty () {
//            assertEquals("", cut.getMaskedPhrase());
//        }
//
//        @Test
//        @DisplayName("then a guess throws an exception")
//        void aGuessThrowsException() {
//            assertThrows(Exception.class, () -> {cut.guessCharacter("");});
//        }
}
