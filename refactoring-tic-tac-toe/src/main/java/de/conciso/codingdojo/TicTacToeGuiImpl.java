package de.conciso.codingdojo;

import java.util.Scanner;

public class TicTacToeGuiImpl implements TicTacToeGui {

  private TicTacToe ticTacToe;

  public TicTacToeGuiImpl(TicTacToe ticTacToe) {
    this.ticTacToe= ticTacToe;
  }

  @Override
  public void start() {
    System.out.print("Player1 name: ");
    Scanner scanner = new Scanner(System.in);
    String name = scanner.nextLine();

    ticTacToe.setPlayer1Name(name);
    System.out.print("Player2 name: ");
  }
}
