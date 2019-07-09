package de.conciso.codingdojo;

import java.util.LinkedList;
import java.util.Objects;

public class HangmanImpl implements Hangman {

    private boolean gameRunning = false;
    private String phrase;
    private int numberOfUnsuccessfulTries = 0;
    private int numberOfTries = 0;

    @Override
    public void setNewPhrase(String phrase) {
        Objects.requireNonNull(phrase, "Phrase must not be null");
        requireNotEmpty(phrase);

        this.phrase = phrase;
        resetGameState();

    }

    private void resetGameState() {
        numberOfTries = 0;
        numberOfUnsuccessfulTries = 0;
        gameRunning = true;
    }

    private void requireNotEmpty(String phrase) {
        if (phrase.isEmpty()) {
            throw new IllegalArgumentException("Phrase must not be empty");
        }
    }

    @Override
    public boolean guessCharacter(String guess) {
        requireGameIsRunning();
        Objects.requireNonNull(guess, "Guess must not be null");
        requireLengthOne(guess);

        numberOfTries++;

        boolean phraseContainsGuess = phrase.contains(guess);
        if (!phraseContainsGuess) {
            numberOfUnsuccessfulTries++;
        }

        return phraseContainsGuess;
    }

    private void requireGameIsRunning() {
        if (!isGameRunning()) {
            throw new GameNotStartedException("Game is not running");
        }
    }

    private void requireLengthOne(String guessed) {
        if(guessed.length() > 1) {
            throw new IllegalArgumentException("Guess must only be one character");
        }
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
        if (isGameRunning()) {
            return phrase;
        }
        throw new GameNotStartedException("Game is not started");
    }

    @Override
    public boolean isGameRunning() {
        return gameRunning;
    }
}
