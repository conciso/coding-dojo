package de.conciso.codingdojo.reversi.implementation;

import de.conciso.codingdojo.reversi.Board;
import de.conciso.codingdojo.reversi.Player;
import de.conciso.codingdojo.reversi.Position;
import de.conciso.codingdojo.reversi.Reversi;
import de.conciso.codingdojo.reversi.implementation.CharacterDisc;
import java.util.List;
import java.util.Objects;

public class ReversiGame implements Reversi<Character, CharacterDisc> {

  private CharacterBoard board;
  public ReversiGame(Player<Character> player1, Player<Character> player2) {
    Objects.requireNonNull(player1);
    Objects.requireNonNull(player2);
    this.board = new CharacterBoard();
    this.board.setPosition(new Position(3,3), player1.disc());
    this.board.setPosition(new Position(3,4), player2.disc());
    this.board.setPosition(new Position(4,3), player2.disc());
    this.board.setPosition(new Position(4,4), player1.disc());

  }
  @Override
  public Board<Character, CharacterDisc> getBoard() {
    return this.board;
  }

  @Override
  public Player<?> getActualPlayer() {

    return null;
  }

  @Override
  public List<Position> calculateMoves(Player<?> player) {
    return null;
  }

  @Override
  public void doMove(Position position, Player<?> player) {

  }

  @Override
  public boolean isGameRunning() {
    return false;
  }

  @Override
  public Player getWinner() {
    return null;
  }
}
