package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("An uninitialized Hangman Game")
class HangmanTestUnitialized {

    Hangman cut;

    @BeforeEach
    public void setUp() {
        cut = new HangmanImpl();
    }

    @Nested
    @DisplayName("when initialized")
    class WhenInitalized {

        @Test
        @DisplayName("with an empty start phrase throws IllegalArgumentException")
        public void thatSetNewPhraseWithEmptyStringThrowsIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> {
                cut.setNewPhrase("");
            });
        }

        @Test
        @DisplayName("without start phrase throws NullPointerException")
        public void thatSetNewPhraseWithNullThrowsNullPointerException() {
            assertThrows(NullPointerException.class, () -> {
                cut.setNewPhrase(null);
            });
        }

        @Test
        @DisplayName("with valid start phrase is running")
        public void thatIsGameRunningIsTrueWithInitializedStartPhrase() {
            cut.setNewPhrase("Dies ist ein Test");

            assertTrue(cut.isGameRunning());
        }

        @Test
        @DisplayName("with invalid start phrase is not running")
        public void thatIsGameRunningIsFalseWithoutInitializedStartPhrase() {
            try {
                cut.setNewPhrase(null);
            } catch (Exception e) {
                // ignore
            }
            assertFalse(cut.isGameRunning());
        }
    }

    @Nested
    @DisplayName("when guessed")
    class WhenGuessed {

        @Test
        @DisplayName("Without character then GameNotStartedException is thrown")
        public void thatGuessCharacterWithNullThrowsException()
        {
            assertThrows(GameNotStartedException.class, () -> cut.guessCharacter(null));
        }

        @Test
        @DisplayName("With one character then GameNotStartedException is thrown")
        public void thatGuessCharacterWithCharacterThrowsException()
        {
            assertThrows(GameNotStartedException.class, () -> cut.guessCharacter("T"));
        }

        @Test
        @DisplayName("With multiple characters then GameNotStartedException is thrown")
        public void thatGuessCharacterWithMultipleCharactersThrowsException()
        {
            assertThrows(GameNotStartedException.class, () -> cut.guessCharacter("Test"));
        }
    }

    @Test
    @DisplayName("when mask phrase is requested then GameNotStartedException is thrown")
    public void thatGetMaskedPhraseThrowsException() {
        assertThrows(GameNotStartedException.class, () -> cut.getMaskedPhrase());
    }

    @Test
    @DisplayName("when number of tries are requested then zero is returned")
    public void thatGetNumberOfTriesReturnsZero() {
        assertEquals(0, cut.getNumberOfTries());
    }

    @Test
    @DisplayName("when number of unsuccessful tries are requested then zero is returned")
    public void thatGetNumberOfUnsuccessfulTriesReturnsZero() {
        assertEquals(0, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    @DisplayName("is not running")
    public void thatIsGameRunningIsFalseWithoutInitialization() {
        assertFalse(cut.isGameRunning());
    }
}