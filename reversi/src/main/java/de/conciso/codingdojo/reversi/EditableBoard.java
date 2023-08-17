package de.conciso.codingdojo.reversi;

public interface EditableBoard<C, T extends Disc<C>> extends Board<C, T> {

  /**
   * Set the disc at the given position
   *
   * @param position position of the stone
   * @param disc disc of the player to set
   */
  void setPosition(Position position, T disc);
}
