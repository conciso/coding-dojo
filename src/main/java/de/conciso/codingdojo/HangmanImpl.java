package de.conciso.codingdojo;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HangmanImpl implements Hangman {

  public static final String REPLACEMENT_REGEX = "[a-zA-ZöäüÖÄÜß]";
  private String phrase;
  private boolean gameRunning;

  @Override
  public void setNewPhrase(String phrase) {
    if (!Pattern.compile(REPLACEMENT_REGEX + "+").matcher(phrase).matches()) {
      throw new IllegalArgumentException("Phrase does not contain letters");
    }
    this.phrase = phrase;
    gameRunning = true;
  }

  @Override
  public boolean guessCharacter(String guessed) {
    return false;
  }

  @Override
  public int getNumberOfTries() {
    return 0;
  }

  @Override
  public int getNumberOfUnsuccessfulTries() {
    return 0;
  }

  @Override
  public String getMaskedPhrase() {
    if (phrase == null) {
      throw new IllegalArgumentException("Phrase not set");
    }
    return phrase.replaceAll(REPLACEMENT_REGEX, "_");
  }

  @Override
  public boolean isGameRunning() {
    return gameRunning;
  }
}
