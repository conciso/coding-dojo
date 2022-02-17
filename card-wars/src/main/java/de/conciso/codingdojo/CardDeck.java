package de.conciso.codingdojo;

import java.util.List;

public interface CardDeck {
    /**
     * Get the cards of the card deck
     * @return list with cards
     */
    List<Card> getCards();

    /**
     * Shuffles the cards of the card deck
     */
    void shuffleCards();
}