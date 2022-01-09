package de.conciso.codingdojo.solution;

import java.util.Objects;

import de.conciso.codingdojo.Hangman;

public class HangmanImpl implements Hangman {

  public static final int MAX_PHRASE_LENGTH = 50;
  public static final int MAX_GUESS_LENGTH = 1;
  public static final String PATTERN_VALID_CHARACTERS = "[a-zA-ZäöüÖÄÜß0-9]";
  public static final String PATTERN_VALID_WORDS = ".*" + PATTERN_VALID_CHARACTERS + ".*";
  private String phrase;
  private int numberOfTries;
  private int numberOfUnsuccessfulTries;
  private boolean gameRunning;
  private String maskedPhrase;

  @Override
  public void startNewGame(String phrase) {
    Objects.requireNonNull(phrase);
    requireNotEmptyAndMaxLength(phrase, MAX_PHRASE_LENGTH,
        "Game phrase is empty or greater than 30 characters");
    requirePatternMatch(phrase, PATTERN_VALID_WORDS,
        "Game phrase must contain at least one alphabetical character");

    resetGameState();
    this.phrase = phrase;
    this.maskedPhrase = createMaskedPhrase(phrase);
  }

  private String createMaskedPhrase(String phrase) {
    return phrase.replaceAll(PATTERN_VALID_CHARACTERS, "_");
  }

  private void resetGameState() {
    gameRunning = true;
    numberOfTries = 0;
    numberOfUnsuccessfulTries = 0;
  }

  @Override
  public boolean guessCharacter(String guess) {
    reguireRunningGame();
    Objects.requireNonNull(guess);
    requireNotEmptyAndMaxLength(guess, MAX_GUESS_LENGTH,
        "Game guess is empty or greater than one character");
    requirePatternMatch(guess, PATTERN_VALID_CHARACTERS,
        "Game guess must be alphabetical character");

    if (!replaceGuessedCharacter(guess)) {
      increaseNumberOfUnsuccessfulTries();
    }

    increaseNumberOfTries();
    return false;
  }

  private void reguireRunningGame() {
    if (!gameRunning) {
      throw new IllegalStateException("Game is not running");
    }
  }

  private boolean replaceGuessedCharacter(String guess) {
    String upperCaseGuess = guess.toUpperCase();
    String uppercasePhrase = phrase.toUpperCase();

    int foundCharacterIndex = uppercasePhrase.indexOf(upperCaseGuess);
    if (foundCharacterIndex > -1) {
      int startIndex = 0;

      do {
        StringBuilder newMaskedPhrase = new StringBuilder();
        newMaskedPhrase.append(maskedPhrase.substring(0, foundCharacterIndex));

        if (upperCaseGuess.equals(phrase.substring(foundCharacterIndex, foundCharacterIndex + 1))) {
          newMaskedPhrase.append(upperCaseGuess);
        } else {
          newMaskedPhrase.append(guess.toLowerCase());
        }

        newMaskedPhrase.append(maskedPhrase.substring(foundCharacterIndex + 1));

        maskedPhrase = newMaskedPhrase.toString();
        startIndex = foundCharacterIndex + 1;
        foundCharacterIndex = uppercasePhrase.indexOf(upperCaseGuess, startIndex);
      } while (foundCharacterIndex > -1);

      return true;
    }

    return false;
  }

  private void requirePatternMatch(String value, String pattern, String exceptionMessage) {
    if (!value.matches(pattern)) {
      throw new IllegalArgumentException(exceptionMessage);
    }
  }

  private void requireNotEmptyAndMaxLength(String value, int maxLength, String exceptionMessage) {
    if (value.isEmpty() || value.length() > maxLength) {
      throw new IllegalArgumentException(exceptionMessage);
    }
  }

  private void increaseNumberOfUnsuccessfulTries() {
    numberOfUnsuccessfulTries++;
  }

  private void increaseNumberOfTries() {
    numberOfTries++;
  }

  @Override
  public int getNumberOfTries() {
    return numberOfTries;
  }

  @Override
  public int getNumberOfUnsuccessfulTries() {
    return numberOfUnsuccessfulTries;
  }

  @Override
  public String getMaskedPhrase() {
    return maskedPhrase;
  }

  @Override
  public String getResolution() {
    gameRunning = false;
    return phrase;
  }

  @Override
  public boolean isGameRunning() {
    return gameRunning;
  }
}
