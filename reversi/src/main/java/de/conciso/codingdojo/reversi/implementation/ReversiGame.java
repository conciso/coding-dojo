package de.conciso.codingdojo.reversi.implementation;

import de.conciso.codingdojo.reversi.AlreadyOccupiedException;
import de.conciso.codingdojo.reversi.Board;
import de.conciso.codingdojo.reversi.Disc;
import de.conciso.codingdojo.reversi.Player;
import de.conciso.codingdojo.reversi.Position;
import de.conciso.codingdojo.reversi.Reversi;
import de.conciso.codingdojo.reversi.implementation.CharacterDisc;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class ReversiGame implements Reversi<Character, CharacterDisc> {

  private final CharacterBoard board;
  private final Player<Character> player1;
  private final Player<Character> player2;

  private Player<Character> currentPlayer;

  public ReversiGame(Player<Character> player1, Player<Character> player2) {
    Objects.requireNonNull(player1);
    Objects.requireNonNull(player2);
    this.player1 = player1;
    this.player2 = player2;
    this.currentPlayer = player1;
    this.board = new CharacterBoard();
    this.board.setPosition(new Position(3, 3), player1.disc());
    this.board.setPosition(new Position(3, 4), player2.disc());
    this.board.setPosition(new Position(4, 3), player2.disc());
    this.board.setPosition(new Position(4, 4), player1.disc());

  }

  @Override
  public Board<Character, CharacterDisc> getBoard() {
    return this.board;
  }

  @Override
  public Player<?> getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public List<Position> calculateMoves(Player<?> player) {
    Disc<Character> disk = player.disc();
    Set<Position> result = new
    for (int i = 0; i < board.getMaximumPosition().x(); i++) {
      for (int j = 0; j < board.getMaximumPosition().y(); j++) {
        Position position = new Position(i, j);
        if (board.getPosition(position).isPresent() && board.getPosition(position).get().equals(disk)) {
          for (int n = 0; n < 9; n++) {
            var x = n % 3 - 1;
            var y = n / 3 - 1;
            var pos = new Position(x + i, y + j);
            if (board.getPosition(pos).isPresent() && !board.getPosition(pos).get().equals(disk)) {
              var step = 1;
              while (board.getPosition(new Position(x * step + i, y * step + j)).isPresent() && !board.getPosition(
                  new Position(x * step + i, y * step + j)).get().equals(disk)){
                step++;

              }
              if (board.getPosition(new Position(x * step + i, y * step + j)).isEmpty() ){

              }
            }
          }
          if (board.getPosition())
        }
      }
    }
    return null;
  }

  @Override
  public void doMove(Position position) {
    if (board.getPosition(position).isPresent()) {
      throw new AlreadyOccupiedException();
    }
    this.board.setPosition(position, currentPlayer.disc());
    switchPlayer();
  }

  private void switchPlayer() {
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }
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
