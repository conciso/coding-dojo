package de.conciso.codingdojo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

      assertThat(cut.getResolution()).isEqualTo(testValue);
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
          () -> cut.startNewGame("1234567890".repeat(5) + "1"));
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

      assertThat(cut.getNumberOfTries()).isEqualTo(0);
    }

    @Test
    public void withValidPhraseThenNumberOfUnsuccessfulTriesIsSetToZero() {
      startAndWrongGuess();
      startGame();

      assertThat(cut.getNumberOfUnsuccessfulTries()).isEqualTo(0);
    }

    @Test
    public void withValidPhraseThenMaskedPhraseIsSet() {
      startGame();

      assertThat(cut.getMaskedPhrase().length()).isEqualTo(TEST_PHRASE.length());
    }

    @Test
    public void withValidPhraseThenGameIsStarted() {
      startGame();

      assertThat(cut.isGameRunning()).isTrue();
    }
  }

  @Nested
  class WhenRunningTheGameAndTryingGameRestartWithInvalidPhrase {

    @Test
    public void thenNumberOfTriesIsKept() {
      startAndWrongGuess();
      restartWithInvalidPhrase();

      assertThat(cut.getNumberOfTries()).isEqualTo(1);
    }


    @Test
    public void thenNumberOfUnsuccessfulTriesIsKept() {
      startAndWrongGuess();
      restartWithInvalidPhrase();

      assertThat(cut.getNumberOfUnsuccessfulTries()).isEqualTo(1);
    }

    @Test
    public void thenMaskedPhraseIsKept() {
      startGame();
      restartWithInvalidPhrase();

      assertThat(cut.getMaskedPhrase().length()).isEqualTo(TEST_PHRASE.length());
    }

    @Test
    public void thenRunningGameIsKept() {
      startGame();
      restartWithInvalidPhrase();

      assertThat(cut.isGameRunning()).isTrue();
    }

    @Test
    public void andEndedGameThenEndedGameIsKept() {
      startGame();
      resolveGame();
      restartWithInvalidPhrase();

      assertThat(cut.isGameRunning()).isFalse();
    }
  }

  @Nested
  class WhenStartingTheGameAndRequestingTheMaskPhrase {
    @Test
    public void thenMaskedPhraseHasInitialPhraseLength() {
      startGame();
      assertThat(cut.getMaskedPhrase().length()).isEqualTo(TEST_PHRASE.length());
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_PHRASE, "Hallo, hallo", "Die ist daß Häuschen vom Nikoläusschen.",
        ". . , -, fäÄrtig üÜst daß MöÖndgesüÜcht."})
    public void thenMaskedPhraseMasksAlphabeticCharactersAndNoOthers(String testPhrase) {
      cut.startNewGame(testPhrase);

      assertThat(cut.getMaskedPhrase()).containsPattern("^[^a-zA-Z0-9äÄöÖüÜß]+$");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789",
        "ÄäÖöÜü"})
    public void thenMaskedPhraseMasksAlphabeticCharactersWithUnderscore(String testPhrase) {
      cut.startNewGame(testPhrase);

      assertThat(cut.getMaskedPhrase()).containsPattern("^[_]+$");
      assertThat(cut.getMaskedPhrase().length()).isEqualTo(testPhrase.length());
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

      assertThat(cut.getMaskedPhrase()).isEqualTo("_e__");
    }

    @Test
    public void withValidMultiCharacterGuessedAllAreReplacedInMaskedPhraseIndependentOfCase() {
      startGame();

      cut.guessCharacter("t");

      assertThat(cut.getMaskedPhrase()).isEqualTo("T__t");
    }

    @Test
    public void withWrongGuessNumberOfUnsuccessfulTriesIsIncreased() {
      startGame();
      int initialNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();
      cut.guessCharacter("q");

      assertThat(cut.getNumberOfUnsuccessfulTries()).isEqualTo(initialNumberOfUnsuccessfulTries + 1);
    }

    @Test
    public void withRightGuessNumberOfUnsuccessfulTriesStaysTheSame() {
      startGame();
      int initialNumberOfUnsuccessfulTries = cut.getNumberOfUnsuccessfulTries();
      cut.guessCharacter("t");

      assertThat(cut.getNumberOfUnsuccessfulTries()).isEqualTo(initialNumberOfUnsuccessfulTries);
    }

    @Test
    public void withWrongGuessNumberOfTriesIsIncreased() {
      startGame();
      int initialNumberOfTries = cut.getNumberOfTries();
      cut.guessCharacter("q");

      assertThat(cut.getNumberOfTries()).isEqualTo(initialNumberOfTries + 1);
    }

    @Test
    public void withRightGuessNumberOfTriesIsIncreased() {
      startGame();
      int initialNumberOfTries = cut.getNumberOfTries();
      cut.guessCharacter("t");

      assertThat(cut.getNumberOfTries()).isEqualTo(initialNumberOfTries + 1);
    }
  }
  @Nested
  class WhenRunningTheGameAndResolutionIsRequested {

    @Test
    public void thenGameIsEnded() {
      startGame();
      cut.getResolution();

      assertThat(cut.isGameRunning()).isFalse();
    }
    @Test
    public void AndGuessingAgainthenExceptionIsThrown() {
      startGame();
      cut.getResolution();

      assertThrows(IllegalStateException.class, () -> cut.guessCharacter("a"));
    }

  }
}