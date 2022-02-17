package de.conciso.codingdojo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Player {
    /**
     * Initialize the card deck of the player
     */
    void initCards(List<Card> cardDeck);

    /**
     * Get the top card of the players card deck
     * @return card or empty if player's card deck is gone
     */
    Optional<Card> getTopOneCard();

    /**
     * Get the top three cards of the players card deck.
     * @return list with three cards or empty list if player's card deck has not enough cards
     */
    List<Card> getTopThreeCards();

    /**
     * Hand over won cards to player
     * @param wonCards won cards
     */
    void won(List<Card> wonCards);

    /**
     * Get the current card deck of the player
     * @return list with all cards or empty list if card deck is gone
     */
    List<Card> getCardDeck();
}
