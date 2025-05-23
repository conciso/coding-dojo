package de.conciso.codingdojo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeImplTest {

    @Test
    void testInvalidDimensionsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new GameOfLifeImpl().initialize(new GameConfig(0, 1, new Position(0, 0))));
    }

    @Test
    void testValidDimensionsDoesNotThrowException() {
        assertDoesNotThrow(() -> new GameOfLifeImpl().initialize(new GameConfig(1, 1, new Position(0, 0))));
    }

    @Test
    void testLessThanOneLiveCellThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new GameOfLifeImpl().initialize(new GameConfig(1, 1)));
    }

    @Test
    void testPositionsCreateAliveCells() {
        var gameOfLife = new GameOfLifeImpl();
        var position = new Position(0, 0);
        gameOfLife.initialize(new GameConfig(1, 1, position));
        assertThat(gameOfLife.getBoard().getCellState(position)).isEqualTo(Board.CellState.ALIVE);
    }

}