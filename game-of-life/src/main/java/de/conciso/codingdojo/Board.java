package de.conciso.codingdojo;

import java.util.Arrays;

public class Board {

    public enum CellState {
        ALIVE, DEAD
    }
    private CellState[][] cells;

    public static final int MAX_DIMENSION = 80;

    public Board(int width, int height) {
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
        if(width > MAX_DIMENSION || height > MAX_DIMENSION) {
            throw new IllegalArgumentException("Width and height must be less than " + MAX_DIMENSION);
        }
        cells = new CellState[width][height];
        Arrays.stream(cells).forEach(row -> Arrays.fill(row, CellState.DEAD));
    }

    public void setCellState(Position position, CellState state) {
        if(position.x() < 0 || position.y() < 0 || position.x() >= cells.length || position.y() >= cells[0].length) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        cells[position.x()][position.y()] = state;
    }

    public CellState getCellState(Position position) {
        if(position.x() < 0 || position.y() < 0 || position.x() >= cells.length || position.y() >= cells[0].length) {
            throw new IllegalArgumentException("Position out of bounds");
        }

        return cells[position.x()][position.y()];
    }
}
