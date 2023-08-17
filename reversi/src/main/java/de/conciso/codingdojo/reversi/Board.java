package de.conciso.codingdojo.reversi;

import de.conciso.codingdojo.reversi.implementation.CharacterDisc;
import java.util.Optional;

public interface Board<C, T extends Disc<C>> {

  /**
   * Get the board as printable string
   *
   * @return board as string
   */
  String toString();

  /**
   * Get the stone at the given position
   *
   * @param position
   * @return disc at the position or empty string if no disc is present
   */
  Optional<CharacterDisc> getPosition(Position position);

  /**
   * Get the maximum position for the square board
   * @return maximum position
   */
  Position getMaximumPosition();

  /**
   * Get the minimum position for the square board
   * @return minimum position
   */
  Position getMinimumPosition();
}
