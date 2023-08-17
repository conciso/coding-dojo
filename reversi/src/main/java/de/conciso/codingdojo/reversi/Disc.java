package de.conciso.codingdojo.reversi;

public interface Disc<T> {

  /**
   * Get the symbol for a player's disc
   * @return symbol of the disc
   */
  T getSymbol();
}
