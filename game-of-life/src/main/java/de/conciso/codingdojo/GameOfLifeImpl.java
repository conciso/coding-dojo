package de.conciso.codingdojo;

import java.util.Arrays;

public class GameOfLifeImpl implements GameOfLife{

    private Board board;

    @Override
    public void initialize(GameConfig config) {
        this.board = new Board(config.width(), config.height());

        if (config.livingPositions().length == 0) {
            throw new IllegalArgumentException("At least one living cell must be specified");
        }
        Arrays.stream(config.livingPositions()).forEach(position -> board.setCellState(position, Board.CellState.ALIVE));
    }

    @Override
    public void step() {

    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean stillAlive() {
        return false;
    }
}
