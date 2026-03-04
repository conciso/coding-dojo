package spaceinvaders.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(100, 500, 1);
    }

    @Test
    void constructor_setsDefaults() {
        assertEquals(100, player.getX());
        assertEquals(500, player.getY());
        assertEquals(40, player.getWidth());
        assertEquals(30, player.getHeight());
        assertEquals(Player.DEFAULT_LIVES, player.getLives());
        assertEquals(Player.DEFAULT_SPEED, player.getSpeed());
        assertEquals(1, player.getPlayerNumber());
        assertTrue(player.isAlive());
    }

    @Test
    void canShoot_initiallyTrue() {
        assertTrue(player.canShoot());
    }

    @Test
    void resetShootCooldown_normalCooldown() {
        player.resetShootCooldown();
        assertFalse(player.canShoot());
    }

    @Test
    void resetShootCooldown_rapidFireCooldown() {
        player.applyPowerUp(PowerUpType.RAPID_FIRE);
        player.resetShootCooldown();
        assertTrue(player.getShootCooldown() <= Player.RAPID_FIRE_COOLDOWN);
    }

    @Test
    void update_decreasesCooldowns() {
        player.resetShootCooldown();
        player.update(0.5);
        assertTrue(player.canShoot());
    }

    @Test
    void update_decreasesTimers() {
        player.applyPowerUp(PowerUpType.SHIELD);
        player.applyPowerUp(PowerUpType.RAPID_FIRE);
        player.applyPowerUp(PowerUpType.MULTI_SHOT);
        assertTrue(player.hasShield());
        assertTrue(player.hasRapidFire());
        assertTrue(player.hasMultiShot());
        player.update(10);
        assertFalse(player.hasShield());
        assertFalse(player.hasRapidFire());
        assertFalse(player.hasMultiShot());
    }

    @Test
    void applyPowerUp_shield() {
        player.applyPowerUp(PowerUpType.SHIELD);
        assertTrue(player.hasShield());
        assertTrue(player.getShieldTimer() > 0);
    }

    @Test
    void applyPowerUp_rapidFire() {
        player.applyPowerUp(PowerUpType.RAPID_FIRE);
        assertTrue(player.hasRapidFire());
        assertTrue(player.getRapidFireTimer() > 0);
    }

    @Test
    void applyPowerUp_multiShot() {
        player.applyPowerUp(PowerUpType.MULTI_SHOT);
        assertTrue(player.hasMultiShot());
        assertTrue(player.getMultiShotTimer() > 0);
    }

    @Test
    void applyPowerUp_extraLife() {
        int before = player.getLives();
        player.applyPowerUp(PowerUpType.EXTRA_LIFE);
        assertEquals(before + 1, player.getLives());
    }

    @Test
    void hit_withShield_removesShield() {
        player.applyPowerUp(PowerUpType.SHIELD);
        int livesBefore = player.getLives();
        player.hit();
        assertEquals(livesBefore, player.getLives());
        assertFalse(player.hasShield());
    }

    @Test
    void hit_withoutShield_removesLife() {
        int livesBefore = player.getLives();
        player.hit();
        assertEquals(livesBefore - 1, player.getLives());
        assertTrue(player.isAlive());
    }

    @Test
    void hit_lastLife_dies() {
        player.setLives(1);
        player.hit();
        assertEquals(0, player.getLives());
        assertFalse(player.isAlive());
    }

    @Test
    void setLives_works() {
        player.setLives(5);
        assertEquals(5, player.getLives());
    }

    @Test
    void hit_multipleHits_staysAliveAboveZero() {
        player.setLives(3);
        player.hit();
        player.hit();
        assertEquals(1, player.getLives());
        assertTrue(player.isAlive());
    }

    @Test
    void update_timersDoNotGoBelowZero() {
        // All timers start at 0, should stay non-negative after update
        player.update(1.0);
        assertTrue(player.getShootCooldown() <= 0);
        assertTrue(player.getShieldTimer() <= 0);
        assertTrue(player.getRapidFireTimer() <= 0);
        assertTrue(player.getMultiShotTimer() <= 0);
    }

    @Test
    void canShoot_falseWhileCooldown() {
        player.resetShootCooldown();
        player.update(0.1); // Still within cooldown
        assertFalse(player.canShoot());
    }
}
