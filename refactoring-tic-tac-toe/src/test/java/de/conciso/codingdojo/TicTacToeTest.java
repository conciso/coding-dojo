package de.conciso.codingdojo;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TicTacToeTest {

  public static final String NAME_PLAYER_1 = "Player1";
  public static final String NAME_PLAYER_2 = "Player2";
  private TicTacToe cut;

  private static Stream<int[]> sizeProvider() {
    return Stream.of(
        new int[]{3, 3},
        new int[]{3, 5},
        new int[]{5, 3},
        new int[]{10, 11},
        new int[]{1050, 1000}
    );
  }

  @BeforeEach
  public void setUp() {
    cut = new TicTacToeImpl();
  }

  @Nested
  class WhenGameIsStarted {

    @Test
    public void withInvalidRowNumberThrowsException() {
      assertThrows(IllegalArgumentException.class,
          () -> cut.startGame(-1, 5));
    }

    @Test
    public void withInvalidColumnNumberThrowsException() {
      assertThrows(IllegalArgumentException.class,
          () -> cut.startGame(5, -1));
    }

    @ParameterizedTest
    @MethodSource("de.conciso.codingdojo.TicTacToeTest#sizeProvider")
    public void withValidSizeCreatesValidSizedGrid(int[] size) {
      int rowNumber = size[0], columnNumber = size[1];

      String grid = IntStream
          .range(0, rowNumber)
          .mapToObj(i ->
              IntStream
                  .range(0, columnNumber)
                  .mapToObj(j -> "-")
                  .reduce((row1, row2) -> row1 + row2)
                  .orElse("") + "\n")
          .reduce((row1, row2) -> row1 + row2)
          .orElse("");
      cut.startGame(rowNumber, columnNumber);
      assertThat(cut.getGrid()).isEqualTo(grid);
    }

    @Test
    public void withValidGameStartGameIsRunning() {
      cut.startGame(5, 5);

      assertThat(cut.isGameRunning()).isTrue();
    }

    @Test
    public void numberOfMovesOfPlayer1IsReset() {
      cut.startGame(5, 5);
      cut.setMovePlayer1(1, 1);

      cut.startGame(4, 4);

      assertThat(cut.numberOfMovesPlayer1()).isEqualTo(0);
    }

    @Test
    public void numberOfMovesOfPlayer2IsReset() {
      cut.startGame(5, 5);
      cut.setMovePlayer2(1, 1);

      cut.startGame(4, 4);

      assertThat(cut.numberOfMovesPlayer2()).isEqualTo(0);
    }

    @Test
    public void withEqualGameTilesThrowsException() {
      cut.setGameTilePlayer1(cut.getGameTilePlayer2());

      assertThrows(IllegalStateException.class, () -> cut.startGame(5, 5));
    }

    @Test
    public void withEqualPlayerNamesThrowsException() {
      cut.setPlayer1Name(cut.getPlayer2Name());

      assertThrows(IllegalStateException.class, () -> cut.startGame(5, 5));
    }

    @Test
    public void changingPlayer1NameThrowsException() {
      cut.startGame(5, 5);

      assertThrows(IllegalStateException.class, () -> cut.setPlayer1Name("asdfasdf"));
    }

    @Test
    public void changingPlayer2NameThrowsException() {
      cut.startGame(5, 5);

      assertThrows(IllegalStateException.class, () -> cut.setPlayer2Name("asdfasdf"));
    }

    @Test
    public void changingPlayer1TileThrowsException() {
      cut.startGame(5, 5);

      assertThrows(IllegalStateException.class, () -> cut.setGameTilePlayer1("a"));
    }

    @Test
    public void changingPlayer2TileThrowsException() {
      cut.startGame(5, 5);

      assertThrows(IllegalStateException.class, () -> cut.setGameTilePlayer2("a"));
    }

  }

  @Nested
  class WhenGameIsNotStarted {

    @Test
    public void gameIsNotRunning() {
      assertThat(cut.isGameRunning()).isFalse();
    }

    @Test
    public void numberOfMovesOfPlayer1IsZero() {
      assertThat(cut.numberOfMovesPlayer1()).isEqualTo(0);
    }

    @Test
    public void numberOfMovesOfPlayer2IsZero() {
      assertThat(cut.numberOfMovesPlayer2()).isEqualTo(0);
    }

    @Test
    public void player1HasNotWon() {
      assertThat(cut.isWinnerPlayer1()).isFalse();
    }

    @Test
    public void player2HasNotWon() {
      assertThat(cut.isWinnerPlayer2()).isFalse();
    }

    @Test
    public void gameTilesAreDifferent() {
      assertThat(cut.getGameTilePlayer1()).isNotEqualTo(cut.getGameTilePlayer2());
    }

    @Test
    public void playerNamesAreDifferent() {
      assertThat(cut.getGameTilePlayer1()).isNotEqualTo(cut.getGameTilePlayer2());
    }
  }

  @Nested
  class WhenPlayerNamesAreSet {

    @Test
    public void withNullPlayer1NameThrowsException() {
      assertThrows(NullPointerException.class, () -> cut.setPlayer1Name(null));
    }

    @Test
    public void withEmptyPlayer1NameThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> cut.setPlayer1Name(""));
    }

    @Test
    public void withValidPlayer1NameTheNameIsSet() {

      cut.setPlayer1Name(NAME_PLAYER_1);
      assertThat(cut.getPlayer1Name()).isEqualTo(NAME_PLAYER_1);
    }

    @Test
    public void withNullPlayer2NameThrowsException() {
      assertThrows(NullPointerException.class, () -> cut.setPlayer2Name(null));
    }

    @Test
    public void withEmptyPlayer2NameThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> cut.setPlayer2Name(""));
    }

    @Test
    public void withValidPlayer2NameTheNameIsSet() {

      cut.setPlayer1Name(NAME_PLAYER_2);
      assertThat(cut.getPlayer1Name()).isEqualTo(NAME_PLAYER_2);
    }
  }

  @Nested
  class WhenPlayer1MakesAMove {

    @Test
    public void withValidMoveTileIsSet() {
      final int row = 3, column = 1, rowAndColumnSize = 5;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      cut.setMovePlayer1(row, column);

      assertThat(cut.getGrid()).isEqualTo("-----\n-----\nx----\n-----\n-----\n");
    }

    @Test
    public void withTooHighRowMoveThrowsException() {
      final int row = 4, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer1(row, column));
    }

    @Test
    public void withTooLowRowMoveThrowsException() {
      final int row = 0, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer1(row, column));
    }

    @Test
    public void withTooHighColumnMoveThrowsException() {
      final int row = 3, column = 5, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer1(row, column));
    }

    @Test
    public void withTooLowColumnMoveThrowsException() {
      final int row = 3, column = -1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer1(row, column));
    }

    @Test
    public void withSettingTileToOccupiedPositionThrowsException() {
      final int row = 3, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);
      cut.setMovePlayer2(row, column);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

    @Test
    public void withoutWinningMovePlayerIsNotWinning() {
      cut.startGame(3, 3);

      assertThat(cut.isWinnerPlayer1()).isFalse();
    }

    @Test
    public void withHorizontalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer1(2, 1);
      cut.setMovePlayer1(2, 2);

      cut.setMovePlayer1(2, 3);
      assertThat(cut.isWinnerPlayer1()).isTrue();
    }

    @Test
    public void withVerticalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer1(1, 1);
      cut.setMovePlayer1(2, 1);

      cut.setMovePlayer1(3, 1);
      assertThat(cut.isWinnerPlayer1()).isTrue();
    }

    @Test
    public void withLeftToRightDiagonalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer1(1, 1);
      cut.setMovePlayer1(2, 2);

      cut.setMovePlayer1(3, 3);
      assertThat(cut.isWinnerPlayer1()).isTrue();
    }

    @Test
    public void withRightToLeftDiagonalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer1(1, 3);
      cut.setMovePlayer1(2, 2);

      cut.setMovePlayer1(3, 1);
      assertThat(cut.isWinnerPlayer1()).isTrue();
    }

    @Test
    public void withValidMoveIncreasesNumberOfMoves() {
      cut.startGame(3, 3);
      int numberOfMoves = cut.numberOfMovesPlayer1();
      cut.setMovePlayer1(1, 1);

      assertThat(cut.numberOfMovesPlayer1()).isEqualTo(numberOfMoves + 1);
    }

  }

  @Nested
  class WhenPlayer2MakesAMove {

    @Test
    public void withValidMoveTileIsSet() {
      final int row = 3, column = 1, rowAndColumnSize = 5;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      cut.setMovePlayer2(row, column);

      assertThat(cut.getGrid()).isEqualTo("-----\n-----\no----\n-----\n-----\n");
    }

    @Test
    public void withTooHighRowMoveThrowsException() {
      final int row = 4, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

    @Test
    public void withTooLowRowMoveThrowsException() {
      final int row = 0, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

    @Test
    public void withTooHighColumnMoveThrowsException() {
      final int row = 3, column = 5, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

    @Test
    public void withTooLowColumnMoveThrowsException() {
      final int row = 3, column = -1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

    @Test
    public void withSettingTileToOccupiedPositionThrowsException() {
      final int row = 3, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);
      cut.setMovePlayer2(row, column);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

    @Test
    public void withoutWinningMovePlayerIsNotWinning() {
      cut.startGame(3, 3);

      assertThat(cut.isWinnerPlayer2()).isFalse();
    }

    @Test
    public void withHorizontalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer2(2, 1);
      cut.setMovePlayer2(2, 2);

      cut.setMovePlayer2(2, 3);
      assertThat(cut.isWinnerPlayer2()).isTrue();
    }

    @Test
    public void withVerticalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer2(1, 1);
      cut.setMovePlayer2(2, 1);

      cut.setMovePlayer2(3, 1);
      assertThat(cut.isWinnerPlayer2()).isTrue();
    }

    @Test
    public void withLeftToRightDiagonalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer2(1, 1);
      cut.setMovePlayer2(2, 2);

      cut.setMovePlayer2(3, 3);
      assertThat(cut.isWinnerPlayer2()).isTrue();
    }

    @Test
    public void withRightToLeftDiagonalWinningMovePlayerWins() {
      cut.startGame(3, 3);
      cut.setMovePlayer2(1, 3);
      cut.setMovePlayer2(2, 2);

      cut.setMovePlayer2(3, 1);
      assertThat(cut.isWinnerPlayer2()).isTrue();
    }
  }

  @Nested
  class WhenGameTilesAreSet {
    @Test
    public void withNullValuePlayer1TileThrowsException() {
      assertThrows(NullPointerException.class, () -> cut.setGameTilePlayer1(null));
    }

    @Test
    public void withNullValuePlayer2TileThrowsException() {
      assertThrows(NullPointerException.class, () -> cut.setGameTilePlayer2(null));
    }

    @Test
    public void withEmptyValuePlayer1TileThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> cut.setGameTilePlayer1(""));
    }

    @Test
    public void withEmptyValuePlayer2TileThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> cut.setGameTilePlayer2(""));
    }

    @Test
    public void withMultiCharacterValuePlayer1TileThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> cut.setGameTilePlayer1("as"));
    }

    @Test
    public void withMultiCharacterValuePlayer2TileThrowsException() {
      assertThrows(IllegalArgumentException.class, () -> cut.setGameTilePlayer2("er"));
    }

  }
}