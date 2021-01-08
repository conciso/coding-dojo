package de.conciso.codingdojo;

import java.util.Objects;

public class HangmanImpl implements Hangman {

  private String phrase;

  @Override
  public void setNewPhrase(String phrase) {
    Objects.requireNonNull(phrase);

    this.phrase = phrase;
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
    return null;
  }

  @Override
  public boolean isGameRunning() {
    return false;
  }

  public String getPhrase() {
    return phrase;
  }
}
