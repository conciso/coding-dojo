package de.conciso.codingdojo;

/**
 * The game should work with a simple GUI interface. This should have following behaviour:
 *
 * 1. Ask for the names of the players
 * 2. Ask for the size of the game grid
 * 3. Show the grid and the players with their game tile and their number of moves.
 * 4. Ask in changing order for the game move of the actual player and then repeat with step 3.
 * 5. If a player has won the game show the name of the winner together with the needed moves.
 * 6. Ask if the game should be played again and restart at step 1 if needed.
 */
public interface TicTacToeGui {

  void start();

  // TODO
}
