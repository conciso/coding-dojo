package de.conciso.codingdojo.reversi;

import java.util.List;

public interface Reversi<C, T extends Disc<C>> {

  /**
   * Get the actual board of the game
   *
   * @return actual board
   */
  Board<C, T> getBoard();

  /**
   * Get the current player for the next move
   * @return actual player on the move
   */
  Player<C> getCurrentPlayer();

  /**
   * Calculate the possible moves for the given player
   *
   * @param player player to check
   * @return list with the possible positions for the given player
   */
  List<Position> calculateMoves(Player<C> player);

  /**
   * Do a move for the current player
   * @param position position, where the disc is set
   */
  void doMove(Position position);

  /**
   * Checks if the game is still running and there are possible positions for the actual player
   *
   * @return <code>true</code> if the game is still running, <code>false</code> otherwise
   */
  boolean isGameRunning();

  /**
   * Get the winning player in the actual situation
   *
   * @return winning player of the game
   */
  Player<C> getWinner();
}
