package de.conciso.codingdojo;

public class TicTacToeImpl implements TicTacToe {

  private String g = "";
  private String p1 = "player1";
  private String p2 = "player1";
  private int p1m;
  private int p2m;
  private String p1t = "x";
  private String p2t = "o";
  private int c;

  @Override
  public void startGame(int r, int c) {
    int i = 0;
    do {
      g += "-" + (++i % c == 0 ? "\n" : "");
    } while (i < r * c);
    this.c = c;
  }

  @Override
  public String getPlayer1Name() {
    return this.p1;
  }

  @Override
  public void setPlayer1Name(String n) {
    this.p1 = n;
  }

  @Override
  public String getPlayer2Name() {
    return this.p2;
  }

  @Override
  public void setPlayer2Name(String n) {
    this.p2 = n;
  }

  @Override
  public String getGrid() {
    return g;
  }

  @Override
  public void setMovePlayer1(int r, int c) {
    p1m = p1m + 1;
    g = g.substring(0, r*(this.c + 1) + c) + p1t + g.substring(r*(this.c + 1) + c + 1);
  }

  @Override
  public void setMovePlayer2(int r, int c) {
    p2m = p2m + 1;
    g = g.substring(0, r*(this.c + 1) + c) + p2t + g.substring(r*(this.c + 1) + c + 1);
  }

  @Override
  public boolean isGameRunning() {
    return !isWinnerPlayer1() && !isWinnerPlayer2() ;
  }

  @Override
  public boolean isWinnerPlayer1() {
    return g.indexOf(p1t + p1t + p1t) > -1;
  }

  @Override
  public boolean isWinnerPlayer2() {
    return g.indexOf(p2t + p2t + p2t) > -1;
  }

  @Override
  public int numberOfMovesPlayer1() {
    return p1m;
  }

  @Override
  public int numberOfMovesPlayer2() {
    return p2m;
  }

  @Override
  public String getGameTilePlayer1() {
    return p1t;
  }

  @Override
  public void setGameTilePlayer1(String t) {
    this.p1t = t;
  }

  @Override
  public String getGameTilePlayer2() {
    return p2t;
  }

  @Override
  public void setGameTilePlayer2(String t) {
    p2t = t;
  }
}
