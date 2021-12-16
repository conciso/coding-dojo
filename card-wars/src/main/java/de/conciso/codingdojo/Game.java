package de.conciso.codingdojo;

import java.util.List;

public interface Game {
    /**
     * Deal the cards for the players
     * @param one player one
     * @param two player two
     */
    void dealCards(Player one, Player two);

    /**
     * Do one move of the game.
     *
     * @param one player one
     * @param two player two
     * @return <code>true</code> if the move could be done, <code>false</code> otherwise
     */
    boolean doMove(Player one, Player two);

    /**
     * Calculate the winner from the current situation.
     *
     * @param one player one
     * @param two player two
     * @return winning player
     */
    Player getWinner(Player one, Player two);
}
