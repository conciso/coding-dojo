package spaceinvaders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputStateTest {
    private InputState input;

    @BeforeEach
    void setUp() {
        input = new InputState();
    }

    @Test
    void initialState_allFalse() {
        assertFalse(input.isLeft());
        assertFalse(input.isRight());
        assertFalse(input.isShoot());
        assertFalse(input.isLeft2());
        assertFalse(input.isRight2());
        assertFalse(input.isShoot2());
        assertFalse(input.isPause());
        assertFalse(input.isEnter());
        assertFalse(input.isUp());
        assertFalse(input.isDown());
    }

    @Test
    void setters_player1() {
        input.setLeft(true);
        input.setRight(true);
        input.setShoot(true);
        assertTrue(input.isLeft());
        assertTrue(input.isRight());
        assertTrue(input.isShoot());
    }

    @Test
    void setters_player2() {
        input.setLeft2(true);
        input.setRight2(true);
        input.setShoot2(true);
        assertTrue(input.isLeft2());
        assertTrue(input.isRight2());
        assertTrue(input.isShoot2());
    }

    @Test
    void setters_menu() {
        input.setPause(true);
        input.setEnter(true);
        input.setUp(true);
        input.setDown(true);
        assertTrue(input.isPause());
        assertTrue(input.isEnter());
        assertTrue(input.isUp());
        assertTrue(input.isDown());
    }

    @Test
    void reset_clearsAll() {
        input.setLeft(true);
        input.setRight(true);
        input.setShoot(true);
        input.setLeft2(true);
        input.setRight2(true);
        input.setShoot2(true);
        input.setPause(true);
        input.setEnter(true);
        input.setUp(true);
        input.setDown(true);

        input.reset();

        assertFalse(input.isLeft());
        assertFalse(input.isRight());
        assertFalse(input.isShoot());
        assertFalse(input.isLeft2());
        assertFalse(input.isRight2());
        assertFalse(input.isShoot2());
        assertFalse(input.isPause());
        assertFalse(input.isEnter());
        assertFalse(input.isUp());
        assertFalse(input.isDown());
    }
}
