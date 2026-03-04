package spaceinvaders.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShieldTest {

    @Test
    void constructor_setsDefaults() {
        Shield s = new Shield(100, 400);
        assertEquals(100, s.getX());
        assertEquals(400, s.getY());
        assertEquals(60, s.getWidth());
        assertEquals(40, s.getHeight());
        assertEquals(Shield.MAX_HEALTH, s.getHealth());
        assertTrue(s.isAlive());
    }

    @Test
    void hit_reducesHealth() {
        Shield s = new Shield(100, 400);
        s.hit();
        assertEquals(Shield.MAX_HEALTH - 1, s.getHealth());
        assertTrue(s.isAlive());
    }

    @Test
    void hit_destroysAtZero() {
        Shield s = new Shield(100, 400);
        for (int i = 0; i < Shield.MAX_HEALTH; i++) {
            s.hit();
        }
        assertEquals(0, s.getHealth());
        assertFalse(s.isAlive());
    }

    @Test
    void getHealthPercent_full() {
        Shield s = new Shield(100, 400);
        assertEquals(1.0, s.getHealthPercent(), 0.01);
    }

    @Test
    void getHealthPercent_half() {
        Shield s = new Shield(100, 400);
        s.hit();
        s.hit();
        double expected = (Shield.MAX_HEALTH - 2.0) / Shield.MAX_HEALTH;
        assertEquals(expected, s.getHealthPercent(), 0.01);
    }

    @Test
    void getHealthPercent_empty() {
        Shield s = new Shield(100, 400);
        for (int i = 0; i < Shield.MAX_HEALTH; i++) s.hit();
        assertEquals(0.0, s.getHealthPercent(), 0.01);
    }

    @Test
    void maxHealth_isFive() {
        assertEquals(5, Shield.MAX_HEALTH);
    }
}
