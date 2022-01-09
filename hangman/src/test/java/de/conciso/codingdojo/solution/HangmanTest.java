package de.conciso.codingdojo.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.conciso.codingdojo.Hangman;

class HangmanTest {

  public static final String TEST_PHRASE = "Test";
  private Hangman cut;

  @BeforeEach
  public void setUp() {
    cut = new HangmanImpl();
  }

  private void resolveGame() {
    cut.getResolution();
  }

  private void startGameAndRightGuess() {
    startGame();
    cut.guessCharacter(TEST_PHRASE.substring(0, 1));
  }

  private void startAndWrongGuess() {
    startGame();
    cut.guessCharacter("Q");
  }

  private void startGame() {
    cut.startNewGame(TEST_PHRASE);
  }

  private void restartWithInvalidPhrase() {
    try {
      cut.startNewGame("");
    } catch (Throwable ex) {
      // is intentionally ignored here
    }
  }

  @Nested
  class WhenStartingTheGameAndSettingANewPhrase {

    @ParameterizedTest
    @ValueSource(strings = {TEST_PHRASE, "Long Test", "Very long but valid Test", "äöü", "test, test, (test9,)[9}"})
    public void withValidPhraseThenPhraseIsUsedForGame(String testValue) {
      cut.startNewGame(testValue);

      assertEquals(testValue, cut.getResolution());
    }

    @Test
    public void withNullPhraseThenExceptionIsThrown() {
      assertThrows(NullPointerException.class, () -> cut.startNewGame(null));
    }

    @Test
    public void withEmptyStringThenExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class, () -> cut.startNewGame(""));
    }

    @Test
    public void withStringIsLongerThan50CharactersThenExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class,
          () -> cut.startNewGame("12345678901234567890123456789012345678901234567890" + "1"));
    }

    @Test
    public void withWhitespaceStringThenExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class,
          () -> cut.startNewGame(" \t\r\n"));
    }

    @Test
    public void withPunctuationStringThenExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class,
          () -> cut.startNewGame(".,;:"));
    }

    @Test
    public void withValidPhraseThenNumberOfTriesIsSetToZero() {
      startGameAndRightGuess();
      startGame();

      assertEquals(0, cut.getNumberOfTries());
    }

    @Test
    public void withValidPhraseThenNumberOfUnsuccessfulTriesIsSetToZero() {
      startAndWrongGuess();
      startGame();

      assertEquals(0, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    public void withValidPhraseThenMaskedPhraseIsSet() {
      startGame();

      assertEquals(TEST_PHRASE.length(), cut.getMaskedPhrase().length());
    }

    @Test
    public void withValidPhraseThenGameIsStarted() {
      startGame();

      assertTrue(cut.isGameRunning());
    }
  }

  @Nested
  class WhenRunningTheGameAndTryingGameRestartWithInvalidPhrase {

    @Test
    public void thenNumberOfTriesIsKept() {
      startAndWrongGuess();
      restartWithInvalidPhrase();

      assertEquals(1,cut.getNumberOfTries());
    }


    @Test
    public void thenNumberOfUnsuccessfulTriesIsKept() {
      startAndWrongGuess();
      restartWithInvalidPhrase();

      assertEquals(1, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    public void thenMaskedPhraseIsKept() {
      startGame();
      restartWithInvalidPhrase();

      assertEquals(TEST_PHRASE.length(), cut.getMaskedPhrase().length());
    }

    @Test
    public void thenRunningGameIsKept() {
      startGame();
      restartWithInvalidPhrase();

      assertTrue(cut.isGameRunning());
    }

    @Test
    public void andEndedGameThenEndedGameIsKept() {
      startGame();
      resolveGame();
      restartWithInvalidPhrase();

      assertFalse(cut.isGameRunning());
    }
  }

  @Nested
  class WhenStartingTheGameAndRequestingTheMaskPhrase {
    @Test
    public void thenMaskedPhraseHasInitialPhraseLength() {
      startGame();
      assertEquals(TEST_PHRASE.length(), cut.getMaskedPhrase().length());
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_PHRASE, "Hallo, hallo", "Die ist daß Häuschen vom Nikoläusschen.",
        ". . , -, fäÄrtig üÜst daß MöÖndgesüÜcht."})
    public void thenMaskedPhraseMasksAlphabeticCharactersAndNoOthers(String testPhrase) {
      cut.startNewGame(testPhrase);

      assertLinesMatch(Arrays.asList("^[^a-zA-Z0-9äÄöÖüÜß]+$"), Arrays.asList(cut.getMaskedPhrase()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789",
        "ÄäÖöÜü"})
    public void thenMaskedPhraseMasksAlphabeticCharactersWithUnderscore(String testPhrase) {
      cut.startNewGame(testPhrase);

      assertLinesMatch(Arrays.asList("^[_]+$"), Arrays.asList(cut.getMaskedPhrase()));
      assertEquals(testPhrase.length(), cut.getMaskedPhrase().length());
    }
  }

  @Nested
  class WhenRunningTheGameAndGuessing {

    @BeforeEach
    public void setUp() {
      startGame();
    }

    @Test
    public void withNullGuessThenExceptionIsThrown() {
      assertThrows(NullPointerException.class, () -> cut.guessCharacter(null));
    }

    @Test
    public void withEmptyGuessThenExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class, () -> cut.guessCharacter(""));
    }

    @Test
    public void withGuessLengthGreaterOneThenExceptionIsThrown() {
      assertThrows(IllegalArgumentException.class, () -> cut.guessCharacter("AB"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"%", ",", "\t" })
    public void withGuessNotBeingAlphabeticalOneThenExceptionIsThrown(String value) {
      assertThrows(IllegalArgumentException.class, () -> cut.guessCharacter(value));
    }

    @Test
    public void withValidSingleCharacterGuessedOneIsReplacedInMaskedPhraseIndependentOfCase() {
      startGame();

      cut.guessCharacter("e");

      assertEquals("_e__", cut.getMaskedPhrase());
    }

    @Test
    public void withValidMultiCharacterGuessedAllAreReplacedInMaskedPhraseIndependentOfCase() {
      startGame();

      cut.guessCharacter("t");

      assertEquals("T__t", cut.getMaskedPhrase());
    }

    @Test
    public void withWrongGuessNumberOfUnsuccessfulTriesIsIncreased() {
      startGame();
      int initialNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();
      cut.guessCharacter("q");

      assertEquals(initialNumberOfUnsuccessfulTries + 1, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    public void withRightGuessNumberOfUnsuccessfulTriesStaysTheSame() {
      startGame();
      int initialNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();
      cut.guessCharacter("t");

      assertEquals(initialNumberOfUnsuccessfulTries, cut.getNumberOfUnsuccessfulTries());
    }

    @Test
    public void withWrongGuessNumberOfTriesIsIncreased() {
      startGame();
      int initialNumberOfTries = cut.getNumberOfTries();
      cut.guessCharacter("q");

      assertEquals(initialNumberOfTries + 1, cut.getNumberOfTries());
    }

    @Test
    public void withRightGuessNumberOfTriesIsIncreased() {
      startGame();
      int initialNumberOfTries = cut.getNumberOfTries();
      cut.guessCharacter("t");

      assertEquals(initialNumberOfTries + 1, cut.getNumberOfTries());
    }
  }
  @Nested
  class WhenRunningTheGameAndResolutionIsRequested {

    @Test
    public void thenGameIsEnded() {
      startGame();
      cut.getResolution();

      assertFalse(cut.isGameRunning());
    }
    @Test
    public void AndGuessingAgainthenExceptionIsThrown() {
      startGame();
      cut.getResolution();

      assertThrows(IllegalStateException.class, () -> cut.guessCharacter("a"));
    }

  }
}