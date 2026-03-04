package spaceinvaders.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlienTest {

    @Test
    void constructor_basic() {
        Alien alien = new Alien(10, 20, AlienType.BASIC);
        assertEquals(10, alien.getX());
        assertEquals(20, alien.getY());
        assertEquals(30, alien.getWidth());
        assertEquals(24, alien.getHeight());
        assertEquals(AlienType.BASIC, alien.getType());
        assertEquals(1, alien.getHitPoints());
        assertEquals(10, alien.getPoints());
        assertTrue(alien.isAlive());
    }

    @Test
    void constructor_tank() {
        Alien alien = new Alien(0, 0, AlienType.TANK);
        assertEquals(2, alien.getHitPoints());
        assertEquals(30, alien.getPoints());
    }

    @Test
    void constructor_boss() {
        Alien alien = new Alien(0, 0, AlienType.BOSS);
        assertEquals(3, alien.getHitPoints());
        assertEquals(50, alien.getPoints());
    }

    @Test
    void constructor_fast() {
        Alien alien = new Alien(0, 0, AlienType.FAST);
        assertEquals(1, alien.getHitPoints());
        assertEquals(20, alien.getPoints());
    }

    @Test
    void hit_basic_destroysImmediately() {
        Alien alien = new Alien(0, 0, AlienType.BASIC);
        assertTrue(alien.hit());
        assertFalse(alien.isAlive());
    }

    @Test
    void hit_tank_requiresTwoHits() {
        Alien alien = new Alien(0, 0, AlienType.TANK);
        assertFalse(alien.hit());
        assertTrue(alien.isAlive());
        assertEquals(1, alien.getHitPoints());
        assertTrue(alien.hit());
        assertFalse(alien.isAlive());
    }

    @Test
    void hit_boss_requiresThreeHits() {
        Alien alien = new Alien(0, 0, AlienType.BOSS);
        assertFalse(alien.hit());
        assertTrue(alien.isAlive());
        assertFalse(alien.hit());
        assertTrue(alien.isAlive());
        assertTrue(alien.hit());
        assertFalse(alien.isAlive());
    }
}
