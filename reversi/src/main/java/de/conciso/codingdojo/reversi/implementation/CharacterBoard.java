package de.conciso.codingdojo.reversi.implementation;

import static java.util.stream.IntStream.range;

import de.conciso.codingdojo.reversi.EditableBoard;
import de.conciso.codingdojo.reversi.Position;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class CharacterBoard implements EditableBoard<Character, CharacterDisc> {

  public static final int DEFAULT_MAXIMUM_HORIZONTAL = 8;
  public static final int DEFAULT_MAXIMUM_VERTICAL = 8;

  private final int maxX;
  private final int maxY;

  private final Optional<CharacterDisc>[][] board;

  public CharacterBoard() {
    this(DEFAULT_MAXIMUM_HORIZONTAL, DEFAULT_MAXIMUM_VERTICAL);
  }

  @SuppressWarnings("unchecked")
  public CharacterBoard(int maxX, int maxY) {
    this.maxX = maxX;
    this.maxY = maxY;

    board = range(0, maxY)
        .mapToObj(i -> range(0, maxX)
            .mapToObj(j -> Optional.empty())
            .toArray(Optional[]::new))
            .toArray(Optional[][]::new);
  }

  @Override
  public Optional<CharacterDisc> getPosition(Position position) {
    checkPosition(position);
    return board[position.y()][position.x()];
  }

  private void checkPosition(Position position) {
    if ((position.x() < 0) || (position.x() >= maxX)) {
      throw new IllegalArgumentException("position exceeds horizontal range");
    }
    if ((position.y() < 0) || (position.y() >= maxY)) {
      throw new IllegalArgumentException("position exceeds vertical range");
    }
  }

  @Override
  public void setPosition(Position position, CharacterDisc disc) {
    checkPosition(position);
    board[position.y()][position.x()] = Optional.of(disc);
  }

  @Override
  public Position getMaximumPosition() {
    return new Position(maxX - 1, maxY - 1);
  }

  @Override
  public Position getMinimumPosition() {
    return new Position(0, 0);
  }

  @Override
  public String toString() {
    return Arrays.stream(board)
        .map((array) ->
            Arrays.stream(array)
                .map((disc) -> disc.map(CharacterDisc::getSymbol).orElse('.').toString())
                .collect(Collectors.joining()))
        .collect(Collectors.joining("\n"));
  }
}
