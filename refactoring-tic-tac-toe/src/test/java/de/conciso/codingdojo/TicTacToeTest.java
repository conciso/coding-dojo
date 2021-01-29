package de.conciso.codingdojo;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TicTacToeTest {

  public static final String NAME_PLAYER_1 = "Player1";
  public static final String NAME_PLAYER_2 = "Player2";
  private TicTacToe cut = new TicTacToeImpl();

  private static Stream<int[]> sizeProvider() {
    return Stream.of(
        new int[]{3, 3},
        new int[]{3, 5},
        new int[]{5, 3},
        new int[]{10, 11},
        new int[]{1050, 1000}
    );
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
    public void withoutGameStartGameIsNotRunning() {
      assertThat(cut.isGameRunning()).isFalse();
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
  class WhenPlayer1Moves {

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

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer1(row, column));
    }

    @Test
    public void withTooLowRowMoveThrowsException() {
      final int row = 0, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer1(row, column));
    }

    @Test
    public void withTooHighColumnMoveThrowsException() {
      final int row = 3, column = 5, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer1(row, column));
    }

    @Test
    public void withTooLowColumnMoveThrowsException() {
      final int row = 3, column = -1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer1(row, column));
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

  }

  @Nested
  class WhenPlayer2Moves {

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

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer2(row, column));
    }

    @Test
    public void withTooLowRowMoveThrowsException() {
      final int row = 0, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer2(row, column));
    }

    @Test
    public void withTooHighColumnMoveThrowsException() {
      final int row = 3, column = 5, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer2(row, column));
    }

    @Test
    public void withTooLowColumnMoveThrowsException() {
      final int row = 3, column = -1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);

      assertThrows(IllegalArgumentException.class, () ->cut.setMovePlayer2(row, column));
    }

    @Test
    public void withSettingTileToOccupiedPositionThrowsException() {
      final int row = 3, column = 1, rowAndColumnSize = 3;
      cut.startGame(rowAndColumnSize, rowAndColumnSize);
      cut.setMovePlayer2(row, column);

      assertThrows(IllegalArgumentException.class, () -> cut.setMovePlayer2(row, column));
    }

  }

}