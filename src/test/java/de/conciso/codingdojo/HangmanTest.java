package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("A Hangman game")
class HangmanTest {

    private Hangman cut;

    @BeforeEach
    public void setUp() {
        cut = new HangmanImpl();
    }

    @Test
    @DisplayName("throws an exception when initialized with an empty string")
    public void initializeWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> cut.setNewPhrase(""));
    }

    @Test
    @DisplayName("throws an exception when initialized with null")
    public void initializeWithNull() {
        assertThrows(NullPointerException.class, () -> cut.setNewPhrase(null));
    }

}