package de.conciso.codingdojo;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("An initialized Hangman Game")
class HangmanTestInitialized {

    public static final String TEST_PHRASE = "Dies ist ein Test";
    private static final String NOT_CONTAINED_CHARACTER = "k";
    Hangman cut;

    @BeforeEach
    public void setUp() {
        cut = new HangmanImpl();
        cut.setNewPhrase(TEST_PHRASE);
    }

    @Test
    @DisplayName("is running")
    public void thatIsRunningReturnsTrue() {
        assertTrue(cut.isGameRunning());
    }

    @Test
    @DisplayName("when setting new phrase then number of tries is reset")
    public void thatSetNewPhraseResetsNumberOfTries() {
        cut.guessCharacter(TEST_PHRASE.substring(0, 1));
        int previousNumberOfTries = cut.getNumberOfTries();

        cut.setNewPhrase(TEST_PHRASE);
        assertNotEquals(previousNumberOfTries, cut.getNumberOfTries());
        assertEquals(0, cut.getNumberOfTries());
    }

    @Test
    @DisplayName("when setting new phrase then number of unsuccessful tries is reset")
    public void thatSetNewPhraseResetsNumberOfUnsuccessfulTries() {
        cut.guessCharacter(NOT_CONTAINED_CHARACTER);
        int previousNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();

        cut.setNewPhrase(TEST_PHRASE);
        assertNotEquals(previousNumberOfUnsuccessfulTries, cut.getNumberOfUnsuccessfulTries());
        assertEquals(0, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    @DisplayName("when setting new phrase then mask has same length")
    public void thatMaskedPhraseHasLengthOfPhrase()
    {
        String maskedPhrase = cut.getMaskedPhrase();

        assertEquals(TEST_PHRASE.length(), maskedPhrase.length());
    }

    @Test
    @DisplayName("when setting new phrase then mask does show spaces")
    public void thatMaskedPhraseShowsSpaces()
    {
        String maskedPhrase = cut.getMaskedPhrase();

        assertEquals(maskedPhrase.chars().filter(c -> c == ' ').count(),
            TEST_PHRASE.chars().filter(c -> c == ' ').count()) ;
    }

    @Test
    @DisplayName("when setting new phrase then mask replaces non space characters")
    public void thatMaskedPhraseMasksNonSpaceCharacters()
    {
        String maskedPhrase = cut.getMaskedPhrase();

        assertEquals(maskedPhrase.chars().filter(c -> c == ' ').count(),
            TEST_PHRASE.chars().filter(c -> c == ' ').count()) ;
    }

    @Nested
    @DisplayName("when guessed incorrectly")
    class WhenGuessedIncorrectly {

        @Test
        @DisplayName("Without character then NullPointerException is thrown")
        public void thatGuessCharacterWithNullThrowsException()
        {
            assertThrows(NullPointerException.class, () -> cut.guessCharacter(null));
        }

        @Test
        @DisplayName("With multiple characters then IllegalArgumentException is thrown")
        public void thatGuessCharacterWithMultipleCharactersThrowsException()
        {
            assertThrows(IllegalArgumentException.class, () -> cut.guessCharacter("Test"));
        }

        @Test
        @DisplayName("With one incorrect character then false is returned")
        public void thatGuessCharacterWithIncorrectCharacterReturnsFalse()
        {
            assertFalse(cut.guessCharacter("2"));
        }

        @Test
        @DisplayName("With one incorrect character then number of unsuccessful tries is incremented")
        public void thatGuessCharacterWithIncorrectCharacterIncrementsUnsuccessfulTries()
        {
            int currentNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();

            cut.guessCharacter("2");

            assertEquals(currentNumberOfUnsuccessfulTries + 1, cut.getNumberOfUnsuccessfulTries());
        }

        @Test
        @DisplayName("With one incorrect character then number of tries is incremented")
        public void thatGuessCharacterWithIncorrectCharacterIncrementsTries()
        {
            int currentNumberOfTries = cut.getNumberOfTries();

            cut.guessCharacter("2");

            assertEquals(currentNumberOfTries + 1, cut.getNumberOfTries());
        }
    }

    @Nested
    @DisplayName("when guessed correctly")
    class WhenGuessedCorrectly {

        @Test
        @DisplayName("With one correct character then true is returned")
        public void thatGuessCharacterWithCorrectCharacterReturnsTrue()
        {
            assertTrue(cut.guessCharacter(TEST_PHRASE.substring(0, 1)));
        }

        @Test
        @DisplayName("With one correct character then number of unsuccessful tries stays the same")
        public void thatGuessCharacterWithCorrectCharacterUnsuccessfulTriesStaysTheSame()
        {
            int currentNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();

            cut.guessCharacter("T");

            assertEquals(currentNumberOfUnsuccessfulTries, cut.getNumberOfUnsuccessfulTries());
        }

        @Test
        @DisplayName("With one correct character then number of tries is incremented")
        public void thatGuessCharacterWithCorrectCharacterIncrementsTries()
        {
            int currentNumberOfTries = cut.getNumberOfTries();

            cut.guessCharacter(TEST_PHRASE.substring(1, 1));

            assertEquals(currentNumberOfTries + 1, cut.getNumberOfTries());
        }

        @Test
        @DisplayName("")
        public void thatOnFirstSuccessfulGuessOnlyGuessedCharacterIsShown() {
            fail("Not implemented yet");

        }
    }
}