package de.conciso.codingdojo;

/**
 * Interface for a simple tic tac toe game. This is a two player game where each of them have a
 * defined game tile which they have to set on a rectangular game board. If one player has three of
 * his tiles in a row in a column or as diagonal line then he has won the game. The other player has
 * to prevent this and has to try to put his tiles on the board in the same manner to win the game.
 */
public interface TicTacToe {

  /**
   * Start game with a given grid. The grid must have minimum of 9 fields to play the game
   * regularly.
   *
   * @param numberOfRows    number of rows of the game grid
   * @param numberOfColumns number of columns of the game grid
   */
  public void startGame(int numberOfRows, int numberOfColumns);

  /**
   * Get the name of player 1.
   *
   * @return name of player 1
   */
  String getPlayer1Name();

  /**
   * Set the name of player 1.
   *
   * @param name name of player 1
   */
  void setPlayer1Name(String name);

  /**
   * Get the name of player 2.
   *
   * @return name of player 2
   */
  String getPlayer2Name();

  /**
   * Set the name of player 2
   *
   * @param name name of player 2
   */
  void setPlayer2Name(String name);

  /**
   * Get the actual game grid.
   *
   * @return game grid as string
   */
  String getGrid();

  /**
   * Set a move for player 1.
   *
   * @param row    row of the move
   * @param column column of the move
   */
  public void setMovePlayer1(int row, int column);

  /**
   * Set a move for player 2.
   *
   * @param row    row of the move
   * @param column column of the move
   */
  public void setMovePlayer2(int row, int column);

  /**
   * Check, if the game is still running.
   *
   * @return <code>true</code> if the game is running, <code>false</code> otherwise
   */
  public boolean isGameRunning();

  /**
   * Check, if player 1 is winner of the game.
   *
   * @return <code>true</code> if player 1 has won, <code>false</code> otherwise
   */
  public boolean isWinnerPlayer1();

  /**
   * Check, if player 2 is winner of the game.
   *
   * @return <code>true</code> if player 2 has won, <code>false</code> otherwise
   */
  public boolean isWinnerPlayer2();

  /**
   * Get the number of the moves, which player 1 has done.
   *
   * @return number of moves
   */
  public int numberOfMovesPlayer1();

  /**
   * Get the number of the moves, which player 2 has done.
   *
   * @return number of moves
   */
  public int numberOfMovesPlayer2();

  /**
   * Get the tile which player 1 uses in the game.
   *
   * @return tile tile of player 1
   */
  public String getGameTilePlayer1();

  /**
   * Set the tile which player 1 uses in the game.
   *
   * @param tile tile of player 1
   */
  public void setGameTilePlayer1(String tile);

  /**
   * Get the tile which player 2 uses in the game.
   *
   * @return tile tile of player 2
   */
  public String getGameTilePlayer2();

  /**
   * Set the tile which player 2 uses in the game.
   *
   * @param tile tile of player 2
   */
  public void setGameTilePlayer2(String tile);
}
