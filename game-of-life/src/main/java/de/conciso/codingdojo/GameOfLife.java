package de.conciso.codingdojo;

public interface GameOfLife {
    void initialize(GameConfig config);
    void step();
    Board getBoard();
    boolean stillAlive();
}
