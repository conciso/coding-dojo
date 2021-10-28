package de.conciso.codingdojo;

import java.util.List;
import java.util.Optional;

public interface Player {
    /**
     * Get the top card of the players card deck
     * @return card or empty if player's card deck is gone
     */
    public Optional<Card> getTopOneCard();

    /**
     * Get the top three cards of the players card deck.
     * @return list with three cards or empty list if player's card deck has not enough cards
     */
    public List<Card> getTopThreeCards();

    /**
     * Hand over won cards to player
     * @param wonCards won cards
     */
    public void won(List<Card> wonCards);

    /**
     * Get the current card deck of the player
     * @return list with all cards or empty list if card deck is gone
     */
    public List<Card> getCardDeck();
}
