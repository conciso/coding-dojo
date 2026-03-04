package spaceinvaders.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticleTest {

    @Test
    void constructor_setsFields() {
        Particle p = new Particle(10, 20, 30, 40, 1.0, 255, 128, 0);
        assertEquals(10, p.getX());
        assertEquals(20, p.getY());
        assertEquals(30, p.getVx());
        assertEquals(40, p.getVy());
        assertEquals(1.0, p.getLife());
        assertEquals(1.0, p.getMaxLife());
        assertEquals(255, p.getR());
        assertEquals(128, p.getG());
        assertEquals(0, p.getB());
    }

    @Test
    void update_movesParticle() {
        Particle p = new Particle(0, 0, 100, 200, 1.0, 255, 255, 255);
        p.update(0.1);
        assertEquals(10, p.getX(), 0.01);
        assertEquals(20, p.getY(), 0.01);
    }

    @Test
    void update_decreasesLife() {
        Particle p = new Particle(0, 0, 0, 0, 1.0, 0, 0, 0);
        p.update(0.3);
        assertEquals(0.7, p.getLife(), 0.01);
    }

    @Test
    void isDead_false() {
        Particle p = new Particle(0, 0, 0, 0, 1.0, 0, 0, 0);
        assertFalse(p.isDead());
    }

    @Test
    void isDead_true() {
        Particle p = new Particle(0, 0, 0, 0, 0.5, 0, 0, 0);
        p.update(0.6);
        assertTrue(p.isDead());
    }

    @Test
    void getAlpha_fullLife() {
        Particle p = new Particle(0, 0, 0, 0, 1.0, 0, 0, 0);
        assertEquals(1.0f, p.getAlpha(), 0.01f);
    }

    @Test
    void getAlpha_halfLife() {
        Particle p = new Particle(0, 0, 0, 0, 1.0, 0, 0, 0);
        p.update(0.5);
        assertEquals(0.5f, p.getAlpha(), 0.01f);
    }

    @Test
    void getAlpha_dead_returnsZero() {
        Particle p = new Particle(0, 0, 0, 0, 0.1, 0, 0, 0);
        p.update(1.0);
        assertEquals(0.0f, p.getAlpha(), 0.01f);
    }
}
