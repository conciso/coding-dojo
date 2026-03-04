package spaceinvaders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spaceinvaders.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {
    private CollisionDetector detector;
    private AlienFormation formation;
    private Player player;
    private List<Bullet> bullets;
    private List<Shield> shields;
    private List<PowerUp> powerUps;

    @BeforeEach
    void setUp() {
        detector = new CollisionDetector();
        formation = new AlienFormation(50, 2.0);
        formation.createFormation(1, 3, new AlienType[]{AlienType.BASIC});
        player = new Player(400, 500, 1);
        bullets = new ArrayList<>();
        shields = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    @Test
    void playerBulletHitsAlien() {
        Alien target = formation.getAliens().get(0);
        Bullet b = new Bullet(target.getX() + 5, target.getY() + 5, true, 1);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);

        assertEquals(10, result.scoreGained);
        assertFalse(b.isAlive());
        assertFalse(target.isAlive());
        assertNotNull(result.destroyedAlien);
    }

    @Test
    void playerBulletHitsTank_firstHit() {
        AlienFormation tankFormation = new AlienFormation(50, 2.0);
        tankFormation.createFormation(1, 1, new AlienType[]{AlienType.TANK});
        Alien tank = tankFormation.getAliens().get(0);
        Bullet b = new Bullet(tank.getX() + 5, tank.getY() + 5, true, 1);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, tankFormation, player, null, shields, powerUps);

        assertEquals(0, result.scoreGained);
        assertFalse(b.isAlive());
        assertTrue(tank.isAlive());
    }

    @Test
    void alienBulletHitsPlayer() {
        Bullet b = new Bullet(player.getX() + 5, player.getY() + 5, false, 0);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);

        assertTrue(result.playerHit);
        assertFalse(b.isAlive());
        assertEquals(Player.DEFAULT_LIVES - 1, player.getLives());
    }

    @Test
    void alienBulletHitsPlayer2() {
        Player player2 = new Player(200, 500, 2);
        Bullet b = new Bullet(player2.getX() + 5, player2.getY() + 5, false, 0);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, player2, shields, powerUps);

        assertTrue(result.player2Hit);
        assertEquals(Player.DEFAULT_LIVES - 1, player2.getLives());
    }

    @Test
    void bulletHitsShield() {
        Shield shield = new Shield(300, 400);
        shields.add(shield);
        Bullet b = new Bullet(310, 410, false, 0);
        bullets.add(b);

        detector.checkAll(bullets, formation, player, null, shields, powerUps);

        assertFalse(b.isAlive());
        assertEquals(Shield.MAX_HEALTH - 1, shield.getHealth());
    }

    @Test
    void playerBulletHitsShield() {
        Shield shield = new Shield(300, 400);
        shields.add(shield);
        Bullet b = new Bullet(310, 410, true, 1);
        bullets.add(b);

        detector.checkAll(bullets, formation, player, null, shields, powerUps);

        assertFalse(b.isAlive());
        assertEquals(Shield.MAX_HEALTH - 1, shield.getHealth());
    }

    @Test
    void playerCollectsPowerUp() {
        PowerUp pu = new PowerUp(player.getX() + 5, player.getY() + 5, PowerUpType.EXTRA_LIFE);
        powerUps.add(pu);

        detector.checkAll(bullets, formation, player, null, shields, powerUps);

        assertFalse(pu.isAlive());
        assertEquals(Player.DEFAULT_LIVES + 1, player.getLives());
    }

    @Test
    void player2CollectsPowerUp() {
        Player player2 = new Player(200, 500, 2);
        PowerUp pu = new PowerUp(player2.getX() + 5, player2.getY() + 5, PowerUpType.SHIELD);
        powerUps.add(pu);

        detector.checkAll(bullets, formation, player, player2, shields, powerUps);

        assertFalse(pu.isAlive());
        assertTrue(player2.hasShield());
    }

    @Test
    void deadBullet_ignored() {
        Alien target = formation.getAliens().get(0);
        Bullet b = new Bullet(target.getX(), target.getY(), true, 1);
        b.setAlive(false);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertEquals(0, result.scoreGained);
        assertTrue(target.isAlive());
    }

    @Test
    void deadAlien_ignored() {
        Alien target = formation.getAliens().get(0);
        target.setAlive(false);
        Bullet b = new Bullet(target.getX(), target.getY(), true, 1);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertEquals(0, result.scoreGained);
        assertTrue(b.isAlive());
    }

    @Test
    void nullPlayer2_noException() {
        Bullet b = new Bullet(200, 500, false, 0);
        bullets.add(b);

        assertDoesNotThrow(() ->
            detector.checkAll(bullets, formation, player, null, shields, powerUps)
        );
    }

    @Test
    void deadShield_ignored() {
        Shield shield = new Shield(300, 400);
        shield.setAlive(false);
        shields.add(shield);
        Bullet b = new Bullet(310, 410, false, 0);
        bullets.add(b);

        detector.checkAll(bullets, formation, player, null, shields, powerUps);
        assertTrue(b.isAlive());
    }

    @Test
    void deadPowerUp_ignored() {
        PowerUp pu = new PowerUp(player.getX() + 5, player.getY() + 5, PowerUpType.EXTRA_LIFE);
        pu.setAlive(false);
        powerUps.add(pu);

        int livesBefore = player.getLives();
        detector.checkAll(bullets, formation, player, null, shields, powerUps);
        assertEquals(livesBefore, player.getLives());
    }

    @Test
    void alienBullet_skippedInPlayerBulletsVsAliens() {
        // Alien bullet should be skipped in checkPlayerBulletsVsAliens
        Alien target = formation.getAliens().get(0);
        Bullet alienBullet = new Bullet(target.getX() + 5, target.getY() + 5, false, 0);
        bullets.add(alienBullet);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertTrue(target.isAlive()); // alien bullet doesn't destroy aliens
    }

    @Test
    void playerBullet_missesAllAliens() {
        // Player bullet that doesn't intersect any alien
        Bullet b = new Bullet(700, 700, true, 1);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertEquals(0, result.scoreGained);
        assertTrue(b.isAlive());
    }

    @Test
    void alienBullet_missesPlayer() {
        // Alien bullet far from player
        Bullet b = new Bullet(10, 10, false, 0);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertFalse(result.playerHit);
        assertTrue(b.isAlive());
    }

    @Test
    void alienBullet_playerDead_ignored() {
        player.setLives(0);
        player.setAlive(false);
        Bullet b = new Bullet(player.getX() + 5, player.getY() + 5, false, 0);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertFalse(result.playerHit);
    }

    @Test
    void alienBullet_missesPlayer2() {
        Player player2 = new Player(200, 500, 2);
        Bullet b = new Bullet(10, 10, false, 0);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, player2, shields, powerUps);
        assertFalse(result.player2Hit);
    }

    @Test
    void alienBullet_player2Dead_ignored() {
        Player player2 = new Player(200, 500, 2);
        player2.setLives(0);
        player2.setAlive(false);
        Bullet b = new Bullet(player2.getX() + 5, player2.getY() + 5, false, 0);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, player2, shields, powerUps);
        assertFalse(result.player2Hit);
    }

    @Test
    void aliveBullet_missesShield() {
        Shield shield = new Shield(300, 400);
        shields.add(shield);
        Bullet b = new Bullet(10, 10, true, 1);
        bullets.add(b);

        detector.checkAll(bullets, formation, player, null, shields, powerUps);
        assertTrue(b.isAlive());
        assertEquals(Shield.MAX_HEALTH, shield.getHealth());
    }

    @Test
    void powerUp_missesPlayer() {
        PowerUp pu = new PowerUp(10, 10, PowerUpType.EXTRA_LIFE);
        powerUps.add(pu);

        int livesBefore = player.getLives();
        detector.checkAll(bullets, formation, player, null, shields, powerUps);
        assertTrue(pu.isAlive());
        assertEquals(livesBefore, player.getLives());
    }

    @Test
    void powerUp_playerDead_ignored() {
        player.setLives(0);
        player.setAlive(false);
        PowerUp pu = new PowerUp(player.getX() + 5, player.getY() + 5, PowerUpType.EXTRA_LIFE);
        powerUps.add(pu);

        detector.checkAll(bullets, formation, player, null, shields, powerUps);
        assertTrue(pu.isAlive());
    }

    @Test
    void powerUp_missesPlayer2() {
        Player player2 = new Player(200, 500, 2);
        PowerUp pu = new PowerUp(10, 10, PowerUpType.SHIELD);
        powerUps.add(pu);

        detector.checkAll(bullets, formation, player, player2, shields, powerUps);
        assertTrue(pu.isAlive());
        assertFalse(player2.hasShield());
    }

    @Test
    void powerUp_player2Dead_ignored() {
        Player player2 = new Player(200, 500, 2);
        player2.setLives(0);
        player2.setAlive(false);
        PowerUp pu = new PowerUp(player2.getX() + 5, player2.getY() + 5, PowerUpType.SHIELD);
        powerUps.add(pu);

        detector.checkAll(bullets, formation, player, player2, shields, powerUps);
        assertTrue(pu.isAlive());
    }

    @Test
    void playerBullet_skippedInAlienBulletsVsPlayers() {
        // Player bullet at player position should NOT damage player
        Bullet b = new Bullet(player.getX() + 5, player.getY() + 5, true, 1);
        bullets.add(b);

        CollisionDetector.CollisionResult result = detector.checkAll(
                bullets, formation, player, null, shields, powerUps);
        assertFalse(result.playerHit);
    }
}
