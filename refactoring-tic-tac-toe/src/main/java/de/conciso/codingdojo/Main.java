package de.conciso.codingdojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public void run() throws IOException {
    String input = "";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    TicTacToe game = new TicTacToeImpl();
    do {
      if (!game.isGameRunning() && input.equals("n")) {
        System.out.println("Name of Player 1: ");
        String n = br.readLine();
        game.setPlayer1Name(n);
        System.out.println("Name of Player 2: ");
        n = br.readLine();
        game.setPlayer2Name(n);
        game.startGame(3, 3);
      }
      if (game.isGameRunning() && input.equals("x")) {
        System.out.println("Row:");
        String r = br.readLine();
        String c = br.readLine();
        game.setMovePlayer1(Integer.parseInt(r), Integer.parseInt(c));
      }
      if (game.isGameRunning() && input.equals("o")) {
        System.out.println("Row:");
        String r = br.readLine();
        String c = br.readLine();
        game.setMovePlayer1(Integer.parseInt(r), Integer.parseInt(c));
      }
      if (game.isGameRunning()) {
        System.out.println(game.getGameTilePlayer1() + " " + game.getPlayer1Name() + ": " + game
            .numberOfMovesPlayer1());
        System.out.println(game.getGameTilePlayer2() + " " + game.getPlayer2Name() + ": " + game
            .numberOfMovesPlayer2());
        System.out.println(game.getGrid());
      }
      if (!game.isGameRunning() && game.isWinnerPlayer1()) {
        System.out.println(game.getPlayer1Name() + " has won");
      }
      if (!game.isGameRunning() && game.isWinnerPlayer2()) {
        System.out.println(game.getPlayer2Name() + " has won");
      }
      input = br.readLine();
    } while (!"q".equals(input));
  }

  public static void main(String[] args) throws IOException {
    new Main().run();
  }
}
