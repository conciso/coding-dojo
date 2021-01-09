package de.conciso.codingdojo;

public interface Hangman {

    /**
     * Start of game. It resets all states and starts a new game.
     * @param phrase word or phrase which should be guessed
     */

    void startNewGame(String phrase);

    /**
     * Takes one character as guess. If the game is not initialized or is already ended an exception
     * is thrown
     * @param guessed character to be guessed
     * @return <code>true</code> if the character is contained in the phrase, <code>false</code>
     * otherwise.
     */
    boolean guessCharacter(String guessed);

    /**
     * Get the number of tries of the running game.
     * @return number of tries
     */
    int getNumberOfTries();

    /**
     * Get the number of unsuccessful tries.
     * @return number of unsuccessful tries
     */
    int getNumberOfUnsuccessfulTries();

    /**
     * Get the masked phrase where the characters which are not
     *  already guessed are replaced with "_". If the phrase contains whitespaces or punctuation
     * characters then these are not masked.
     * @return masked phrase
     */
    String getMaskedPhrase();

    /**
     * Get the solution phrase used for the initialisation of the game. When using this method the
     * game is stopped.
     * @return solution phrase of the game.
     */
    String getResolution();

    /**
     * Get the state of the game
     * @return <code>true</code> if the game is still running, otherwise <code>false</code>
     */
    boolean isGameRunning();
}
