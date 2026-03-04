package spaceinvaders.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerUpTest {

    @Test
    void constructor_setsCorrectly() {
        PowerUp p = new PowerUp(50, 100, PowerUpType.SHIELD);
        assertEquals(50, p.getX());
        assertEquals(100, p.getY());
        assertEquals(20, p.getWidth());
        assertEquals(20, p.getHeight());
        assertEquals(PowerUpType.SHIELD, p.getType());
        assertTrue(p.isAlive());
    }

    @Test
    void update_movesDown() {
        PowerUp p = new PowerUp(50, 100, PowerUpType.RAPID_FIRE);
        p.update(0.1);
        assertTrue(p.getY() > 100);
    }

    @Test
    void isOffScreen_true() {
        PowerUp p = new PowerUp(50, 610, PowerUpType.MULTI_SHOT);
        assertTrue(p.isOffScreen(600));
    }

    @Test
    void isOffScreen_false() {
        PowerUp p = new PowerUp(50, 300, PowerUpType.EXTRA_LIFE);
        assertFalse(p.isOffScreen(600));
    }

    @Test
    void getFallSpeed() {
        PowerUp p = new PowerUp(0, 0, PowerUpType.SHIELD);
        assertEquals(100.0, p.getFallSpeed());
    }

    @Test
    void allTypes_haveCorrectDurations() {
        assertTrue(PowerUpType.SHIELD.getDuration() > 0);
        assertTrue(PowerUpType.RAPID_FIRE.getDuration() > 0);
        assertTrue(PowerUpType.MULTI_SHOT.getDuration() > 0);
        assertEquals(0, PowerUpType.EXTRA_LIFE.getDuration());
    }
}
