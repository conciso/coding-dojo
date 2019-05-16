package de.conciso.codingdojo;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class HangmanImpl implements Hangman {

    String phrase = "";
    int tries = 0;
    int unsuccessfulTries = 0;

    @Override
    public void setNewPhrase(String phrase) {
        String testedPhrase = Optional.of(phrase)
            .filter(e ->!phrase.isEmpty())
            .orElseThrow(IllegalArgumentException::new);

        this.phrase = testedPhrase;
    }

    @Override
    public boolean guessCharacter(String guessed) {
        Stream.of(phrase)
            .filter(String::isEmpty)
            .forEach(x -> {throw new RuntimeException();});

        tries++;

        boolean result = phrase.contains(guessed);
        unsuccessfulTries += result ? 0 : 1;

        return result;
    }

    @Override
    public int getNumberOfTries() {
        return tries;
    }

    @Override
    public int getNumberOfUnsuccessfulTries() {
        return unsuccessfulTries;
    }

    @Override
    public String getMaskedPhrase() {
        return "";
    }

    @Override
    public boolean isGameRunning() {
        return !phrase.isEmpty();
    }
}
