package spaceinvaders.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticleSystemTest {
    private ParticleSystem system;

    @BeforeEach
    void setUp() {
        system = new ParticleSystem(42L);
    }

    @Test
    void initiallyEmpty() {
        assertTrue(system.getParticles().isEmpty());
    }

    @Test
    void spawnExplosion_createsParticles() {
        system.spawnExplosion(100, 100, 10, 255, 200, 50);
        assertEquals(10, system.getParticles().size());
    }

    @Test
    void spawnExplosion_setsColors() {
        system.spawnExplosion(100, 100, 5, 255, 128, 64);
        Particle p = system.getParticles().get(0);
        assertEquals(255, p.getR());
        assertEquals(128, p.getG());
        assertEquals(64, p.getB());
    }

    @Test
    void update_removesDeadParticles() {
        system.spawnExplosion(100, 100, 10, 255, 200, 50);
        // Advance time well past max life
        system.update(5.0);
        assertTrue(system.getParticles().isEmpty());
    }

    @Test
    void update_keepsAliveParticles() {
        system.spawnExplosion(100, 100, 10, 255, 200, 50);
        system.update(0.01);
        assertFalse(system.getParticles().isEmpty());
    }

    @Test
    void clear_removesAllParticles() {
        system.spawnExplosion(100, 100, 20, 255, 200, 50);
        system.clear();
        assertTrue(system.getParticles().isEmpty());
    }

    @Test
    void multipleExplosions_accumulate() {
        system.spawnExplosion(100, 100, 5, 255, 0, 0);
        system.spawnExplosion(200, 200, 5, 0, 255, 0);
        assertEquals(10, system.getParticles().size());
    }
}
