package de.conciso.codingdojo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    @ParameterizedTest
    @CsvSource({"-1,1",
            "1,-1",
            "0,1",
            "1,0",
            Board.MAX_DIMENSION + 1 + ",1",
            "1," + (Board.MAX_DIMENSION + 1)})
    void testInvalidDimensionsThrowsException(int width, int height) {
        assertThrows(IllegalArgumentException.class, () -> new Board(width, height));
    }

    @ParameterizedTest
    @CsvSource({"-1,0",
            "0,-1",
            "1,0",
            "0,1"
    })
    void testThatSetCellStateWithOutOfBoundsPositionsThrowException(int x, int y) {
        assertThrows(IllegalArgumentException.class, () -> new Board(1, 1).setCellState(new Position(x, y), Board.CellState.ALIVE));
    }

    @ParameterizedTest
    @CsvSource({"-1,0",
            "0,-1",
            "1,0",
            "0,1"
    })
    void testThatGetCellStateWithOutOfBoundsPositionsThrowException(int x, int y) {
        assertThrows(IllegalArgumentException.class, () -> new Board(1, 1).getCellState(new Position(x, y)));
    }

    @Test
    void testThatBoardIsInitializedAsDead() {
        int width = 5;
        int height = 5;
        Board board = new Board(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                assertThat(board.getCellState(new Position(i, j))).isEqualTo(Board.CellState.DEAD);
            }

        }
    }

}
