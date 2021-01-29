package de.conciso.codingdojo;

import java.util.Objects;
import java.util.stream.IntStream;

public class TicTacToeImpl implements TicTacToe {

  public static final int MIN_ROW_COUNT = 2;
  public static final int MIN_COLUMN_COUNT = 2;
  public static final String EMPTY_FIELD = "-";
  private String grid = "";
  private String Player1Name = "player1";
  private String Player2Name = "player2";
  private int player1NumberOfMoves;
  private int player2NumberOfMoves;
  private String player1Tile = "x";
  private String player2Tile = "o";
  private int numberOfColumns;
  private int numberOfRows;

  @Override
  public void startGame(int numberOfRows, int numberOfColumns) {
    requireMinGridSizeParameter(numberOfRows, MIN_ROW_COUNT,
        "Number of rows should be greater ");
    requireMinGridSizeParameter(numberOfColumns, MIN_COLUMN_COUNT,
        "Number of columns should be greater ");
    requireDifferentTiles();
    requireDifferentPlayerNames();

    this.player1NumberOfMoves = 0;
    this.player2NumberOfMoves = 0;

    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;

    final String row = IntStream
        .range(0, numberOfColumns)
        .mapToObj(i -> EMPTY_FIELD)
        .reduce((row1, row2) -> row1 + row2)
        .orElse("") + "\n";

    this.grid = IntStream
        .range(0, numberOfRows)
        .mapToObj(i -> row)
        .reduce((row1, row2) -> row1 + row2)
        .orElse("");

  }

  private void requireDifferentPlayerNames() {
    if (getPlayer1Name().equals(getPlayer2Name())) {
      throw new IllegalStateException("Player names have to be different");
    }
  }

  private void requireDifferentTiles() {
    if (getGameTilePlayer1().equals(getGameTilePlayer2())) {
      throw new IllegalStateException("Game tiles have to be different");
    }
  }

  private void requireMinGridSizeParameter(int size, int minRowNumber, String s) {
    if (size < minRowNumber) {
      throw new IllegalArgumentException(s + minRowNumber);
    }
  }

  @Override
  public String getPlayer1Name() {
    return this.Player1Name;
  }

  @Override
  public void setPlayer1Name(String name) {
    requireGameNotRunning();
    requireValidPlayerName(1, name);
    this.Player1Name = name;
  }

  private void requireValidPlayerName(int playerNumber, String name) {
    Objects.requireNonNull(name, "Name of player " + playerNumber + " must not be null");
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name of player " + playerNumber + " must not be empty");
    }
  }

  @Override
  public String getPlayer2Name() {
    return this.Player2Name;
  }

  @Override
  public void setPlayer2Name(String name) {
    requireGameNotRunning();
    requireValidPlayerName(2, name);
    this.Player2Name = name;
  }

  @Override
  public String getGrid() {
    return grid;
  }

  @Override
  public void setMovePlayer1(int row, int column) {
    setField(player1Tile, row, column);
    player1NumberOfMoves++;
  }

  private void setField(String tile, int row, int column) {
    requireValidField(row, this.numberOfRows, "Number of columns is outside of valid bounds");
    requireValidField(column, this.numberOfColumns, "Number of rows is outside of valid bounds");
    requireFieldIsNotOccupied(row, column);

    grid = grid.substring(0, (row - 1) * (this.numberOfColumns + 1) + column - 1)
        + tile
        + grid.substring((row - 1) * (this.numberOfColumns + 1) + column);
  }

  private String getField(int row, int column) {
    return grid.substring((row - 1) * (this.numberOfColumns + 1) + column - 1,
        (row - 1) * (this.numberOfColumns + 1) + column);
  }

  private void requireFieldIsNotOccupied(int row, int column) {
    requireValidField(row, this.numberOfRows, "Column is outside of valid bounds");
    requireValidField(column, this.numberOfColumns, "Rows is outside of valid bounds");

    if (!getField(row, column).equals(EMPTY_FIELD)) {
      throw new IllegalArgumentException("Field must be empty");
    }
  }

  private void requireValidField(int move, int maximumSize, String message) {
    if ((move <= 0) || (move > maximumSize)) {
      throw new IllegalArgumentException(message);
    }
  }

  @Override
  public void setMovePlayer2(int row, int column) {
    setField(player2Tile, row, column);
    player2NumberOfMoves++;
  }

  @Override
  public boolean isGameRunning() {
    return !isWinnerPlayer1() && !isWinnerPlayer2()
        && (player1NumberOfMoves + player2NumberOfMoves) < (numberOfRows * numberOfColumns);
  }

  @Override
  public boolean isWinnerPlayer1() {
    return horizontalCheck(player1Tile)
        || verticalCheck(player1Tile)
        || leftToRightDiagonalCheck(player1Tile)
        || RightToLeftDiagonalCheck(player1Tile);

  }

  private boolean RightToLeftDiagonalCheck(String player1Tile) {
    for (int i = 3; i <= numberOfRows; i++) {
      for (int j = 1; j <= numberOfColumns - 2; j++) {
        if (getField(i, j).equals(player1Tile)
            && getField(i - 1, j + 1).equals(player1Tile)
            && getField(i - 2, j + 2).equals(player1Tile)) {
          return true;
        }
      }
    }
    return false;

  }

  private boolean leftToRightDiagonalCheck(String player1Tile) {
    for (int i = 3; i <= numberOfRows; i++) {
      for (int j = 3; j <= numberOfColumns; j++) {
        if (getField(i, j).equals(player1Tile)
            && getField(i - 1, j - 1).equals(player1Tile)
            && getField(i - 2, j - 2).equals(player1Tile)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean verticalCheck(String player1Tile) {
    for (int i = 3; i <= numberOfRows; i++) {
      for (int j = 1; j <= numberOfColumns; j++) {
        if (getField(i, j).equals(player1Tile)
            && getField(i - 1, j).equals(player1Tile)
            && getField(i - 2, j).equals(player1Tile)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean horizontalCheck(String player1Tile) {
    for (int i = 1; i <= numberOfRows; i++) {
      for (int j = 3; j <= numberOfColumns; j++) {
        if (getField(i, j).equals(player1Tile)
            && getField(i, j - 1).equals(player1Tile)
            && getField(i, j - 2).equals(player1Tile)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isWinnerPlayer2() {
    return horizontalCheck(player2Tile)
        || verticalCheck(player2Tile)
        || leftToRightDiagonalCheck(player2Tile)
        || RightToLeftDiagonalCheck(player2Tile);

  }

  @Override
  public int numberOfMovesPlayer1() {
    return player1NumberOfMoves;
  }

  @Override
  public int numberOfMovesPlayer2() {
    return player2NumberOfMoves;
  }

  @Override
  public String getGameTilePlayer1() {
    return player1Tile;
  }

  @Override
  public void setGameTilePlayer1(String tile) {
    requireGameNotRunning();
    requireValidTileName(1, tile);
    this.player1Tile = tile;
  }

  @Override
  public String getGameTilePlayer2() {
    return player2Tile;
  }

  @Override
  public void setGameTilePlayer2(String tile) {
    requireGameNotRunning();
    requireValidTileName(2, tile);
    player2Tile = tile;
  }

  private void requireGameNotRunning() {
    if (isGameRunning()) {
      throw new IllegalStateException("Settings must not be made if game is running");
    }
  }

  private void requireValidTileName(int playerNumber, String tile) {
    Objects.requireNonNull(tile, "Tile of player " + playerNumber + " must not be null");
    if (tile.isEmpty()) {
      throw new IllegalArgumentException("Tile of player " + playerNumber + " must not be empty");
    }
    if (tile.length() > 1) {
      throw new IllegalArgumentException(
          "Tile of player " + playerNumber + " must not be longer than 1 character");
    }
  }

}
