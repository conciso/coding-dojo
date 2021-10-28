package de.conciso.codingdojo;

import java.util.Set;

public interface Game {
    /**
     * Creates the card deck with the appropriate number of cards. The cards will be build from the given values and
     * suits.
     * @param numberOfCards number of cards to be created
     * @return Set with cards
     */
    Set<Card> createCardDeck(int numberOfCards);

    /**
     * Shuffle the cards
     * @param cardsToShuffle cards to be mixed
     * @return given cards in new order
     */
    Set<Card> shuffleCards(Set<Card> cardsToShuffle);

    /**
     * Do one move of the game.
     * @param one player one
     * @param two player two
     * @return <code>true</code> if the move could be done, <code>false</code> otherwise
     */
    boolean doMove(Player one, Player two);

    /**
     * Calculate the winner from the current situation.
     * @param one player one
     * @param two player two
     * @return winning player
     */
    Player getWinner(Player one, Player two);
}
