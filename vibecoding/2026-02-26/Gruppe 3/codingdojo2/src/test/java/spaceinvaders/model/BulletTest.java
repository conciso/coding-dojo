package spaceinvaders.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    @Test
    void constructor_playerBullet() {
        Bullet b = new Bullet(100, 200, true, 1);
        assertEquals(100, b.getX());
        assertEquals(200, b.getY());
        assertEquals(4, b.getWidth());
        assertEquals(10, b.getHeight());
        assertTrue(b.isPlayerBullet());
        assertEquals(1, b.getOwnerPlayerNumber());
        assertTrue(b.getSpeedY() < 0); // Moves up
    }

    @Test
    void constructor_alienBullet() {
        Bullet b = new Bullet(100, 200, false, 0);
        assertFalse(b.isPlayerBullet());
        assertTrue(b.getSpeedY() > 0); // Moves down
    }

    @Test
    void update_playerBullet_movesUp() {
        Bullet b = new Bullet(100, 200, true, 1);
        b.update(0.1);
        assertTrue(b.getY() < 200);
    }

    @Test
    void update_alienBullet_movesDown() {
        Bullet b = new Bullet(100, 200, false, 0);
        b.update(0.1);
        assertTrue(b.getY() > 200);
    }

    @Test
    void isOffScreen_aboveScreen() {
        Bullet b = new Bullet(100, -20, true, 1);
        assertTrue(b.isOffScreen(600));
    }

    @Test
    void isOffScreen_belowScreen() {
        Bullet b = new Bullet(100, 610, false, 0);
        assertTrue(b.isOffScreen(600));
    }

    @Test
    void isOffScreen_onScreen() {
        Bullet b = new Bullet(100, 300, true, 1);
        assertFalse(b.isOffScreen(600));
    }

    @Test
    void speedValues() {
        assertEquals(-400.0, Bullet.PLAYER_BULLET_SPEED);
        assertEquals(200.0, Bullet.ALIEN_BULLET_SPEED);
    }
}
