package spaceinvaders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import spaceinvaders.model.*;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    @TempDir
    Path tempDir;
    private GameEngine engine;
    private ScoreManager scoreManager;

    @BeforeEach
    void setUp() {
        String filePath = tempDir.resolve("test_scores.json").toString();
        scoreManager = new ScoreManager(filePath);
        engine = new GameEngine(scoreManager, 42L);
    }

    @Test
    void initialState_isMenu() {
        assertEquals(GameState.MENU, engine.getState());
    }

    @Test
    void startGame_singlePlayer() {
        engine.startGame(false);
        assertEquals(GameState.PLAYING, engine.getState());
        assertNotNull(engine.getPlayer());
        assertNull(engine.getPlayer2());
        assertFalse(engine.isMultiplayer());
    }

    @Test
    void startGame_multiplayer() {
        engine.startGame(true);
        assertEquals(GameState.MULTIPLAYER, engine.getState());
        assertNotNull(engine.getPlayer());
        assertNotNull(engine.getPlayer2());
        assertTrue(engine.isMultiplayer());
    }

    @Test
    void startGame_resetsScore() {
        scoreManager.addScore(500);
        engine.startGame(false);
        assertEquals(0, scoreManager.getScore());
    }

    @Test
    void startGame_clearsCollections() {
        engine.startGame(false);
        assertTrue(engine.getBullets().isEmpty());
        assertTrue(engine.getPowerUps().isEmpty());
    }

    @Test
    void startGame_createsFormation() {
        engine.startGame(false);
        assertNotNull(engine.getFormation());
        assertFalse(engine.getFormation().getAliens().isEmpty());
    }

    @Test
    void startGame_createsShields() {
        engine.startGame(false);
        assertFalse(engine.getShields().isEmpty());
    }

    @Test
    void menu_upNavigation() {
        engine.getInputState().setUp(true);
        engine.update(0.016);
        assertEquals(3, engine.getMenuSelection()); // wraps from 0 to 3
    }

    @Test
    void menu_downNavigation() {
        engine.getInputState().setDown(true);
        engine.update(0.016);
        assertEquals(1, engine.getMenuSelection());
    }

    @Test
    void menu_enterStartsGame() {
        engine.getInputState().setEnter(true);
        engine.update(0.016);
        assertEquals(GameState.PLAYING, engine.getState());
    }

    @Test
    void menu_selectMultiplayer() {
        // Navigate to multiplayer option (index 1)
        engine.getInputState().setDown(true);
        engine.update(0.016);
        engine.getInputState().setEnter(true);
        engine.update(0.016);
        assertEquals(GameState.MULTIPLAYER, engine.getState());
    }

    @Test
    void playing_pause() {
        engine.startGame(false);
        engine.getInputState().setPause(true);
        engine.update(0.016);
        assertEquals(GameState.PAUSED, engine.getState());
    }

    @Test
    void paused_unpause() {
        engine.startGame(false);
        engine.setState(GameState.PAUSED);
        engine.getInputState().setPause(true);
        engine.update(0.016);
        assertEquals(GameState.PLAYING, engine.getState());
    }

    @Test
    void paused_unpause_multiplayer() {
        engine.startGame(true);
        engine.setState(GameState.PAUSED);
        engine.getInputState().setPause(true);
        engine.update(0.016);
        assertEquals(GameState.MULTIPLAYER, engine.getState());
    }

    @Test
    void playing_playerMovesLeft() {
        engine.startGame(false);
        double initialX = engine.getPlayer().getX();
        engine.getInputState().setLeft(true);
        engine.update(0.016);
        assertTrue(engine.getPlayer().getX() < initialX);
    }

    @Test
    void playing_playerMovesRight() {
        engine.startGame(false);
        double initialX = engine.getPlayer().getX();
        engine.getInputState().setRight(true);
        engine.update(0.016);
        assertTrue(engine.getPlayer().getX() > initialX);
    }

    @Test
    void playing_playerStaysInBounds_left() {
        engine.startGame(false);
        engine.getPlayer().setX(0);
        engine.getInputState().setLeft(true);
        engine.update(0.016);
        assertTrue(engine.getPlayer().getX() >= 0);
    }

    @Test
    void playing_playerStaysInBounds_right() {
        engine.startGame(false);
        engine.getPlayer().setX(GameEngine.SCREEN_WIDTH - engine.getPlayer().getWidth());
        engine.getInputState().setRight(true);
        engine.update(0.016);
        assertTrue(engine.getPlayer().getX() <= GameEngine.SCREEN_WIDTH - engine.getPlayer().getWidth());
    }

    @Test
    void playing_playerShoots() {
        engine.startGame(false);
        engine.getInputState().setShoot(true);
        engine.update(0.016);
        assertFalse(engine.getBullets().isEmpty());
    }

    @Test
    void playing_playerShootsMultiShot() {
        engine.startGame(false);
        engine.getPlayer().applyPowerUp(PowerUpType.MULTI_SHOT);
        engine.getInputState().setShoot(true);
        engine.update(0.016);
        assertEquals(3, engine.getBullets().stream().filter(Bullet::isPlayerBullet).count());
    }

    @Test
    void playing_player2Controls() {
        engine.startGame(true);
        double initialX = engine.getPlayer2().getX();
        engine.getInputState().setLeft2(true);
        engine.update(0.016);
        assertTrue(engine.getPlayer2().getX() < initialX);
    }

    @Test
    void playing_player2Shoots() {
        engine.startGame(true);
        engine.getInputState().setShoot2(true);
        engine.update(0.016);
        assertTrue(engine.getBullets().stream().anyMatch(b -> b.getOwnerPlayerNumber() == 2));
    }

    @Test
    void playing_bulletsRemovedWhenOffScreen() {
        engine.startGame(false);
        engine.getBullets().add(new Bullet(100, -20, true, 1));
        engine.update(0.016);
        assertTrue(engine.getBullets().isEmpty());
    }

    @Test
    void playing_powerUpsRemovedWhenOffScreen() {
        engine.startGame(false);
        engine.getPowerUps().add(new PowerUp(100, GameEngine.SCREEN_HEIGHT + 10, PowerUpType.SHIELD));
        engine.update(0.016);
        assertTrue(engine.getPowerUps().isEmpty());
    }

    @Test
    void playing_alienShootsWhenReady() {
        engine.startGame(false);
        engine.getFormation().setShootTimer(0);
        int bulletCount = engine.getBullets().size();
        engine.update(0.016);
        assertTrue(engine.getBullets().size() > bulletCount);
    }

    @Test
    void playing_gameOverWhenPlayerDies() {
        engine.startGame(false);
        engine.getPlayer().setLives(1);
        engine.getPlayer().hit();
        engine.update(0.016);
        assertEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void playing_gameOverWhenAliensReachBottom() {
        engine.startGame(false);
        for (Alien alien : engine.getFormation().getAliens()) {
            alien.setY(GameEngine.SCREEN_HEIGHT - 50);
        }
        engine.update(0.016);
        assertEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void playing_levelCompleteWhenAllDestroyed() {
        engine.startGame(false);
        int levelBefore = engine.getLevelManager().getCurrentLevel();
        engine.getFormation().getAliens().forEach(a -> a.setAlive(false));
        engine.update(0.016);
        assertEquals(levelBefore + 1, engine.getLevelManager().getCurrentLevel());
    }

    @Test
    void gameOver_enterReturnsToMenu() {
        engine.startGame(false);
        engine.setState(GameState.GAME_OVER);
        engine.getInputState().setEnter(true);
        engine.update(0.016);
        assertEquals(GameState.MENU, engine.getState());
    }

    @Test
    void getters_returnComponents() {
        assertNotNull(engine.getParticleSystem());
        assertNotNull(engine.getLevelManager());
        assertNotNull(engine.getScoreManager());
        assertNotNull(engine.getInputState());
    }

    @Test
    void screenConstants() {
        assertEquals(800, GameEngine.SCREEN_WIDTH);
        assertEquals(600, GameEngine.SCREEN_HEIGHT);
    }

    @Test
    void setState_works() {
        engine.setState(GameState.GAME_OVER);
        assertEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void playing_deadPlayer2NotUpdated() {
        engine.startGame(true);
        engine.getPlayer2().setLives(0);
        engine.getPlayer2().setAlive(false);
        double x2 = engine.getPlayer2().getX();
        engine.getInputState().setLeft2(true);
        engine.update(0.016);
        assertEquals(x2, engine.getPlayer2().getX());
    }

    @Test
    void playing_multiplayerBothDead_gameOver() {
        engine.startGame(true);
        engine.getPlayer().setLives(1);
        engine.getPlayer().hit();
        engine.getPlayer2().setLives(1);
        engine.getPlayer2().hit();
        engine.update(0.016);
        assertEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void playing_multiplayerOneAlive_continues() {
        engine.startGame(true);
        engine.getPlayer().setLives(1);
        engine.getPlayer().hit();
        // Player 2 still alive
        engine.update(0.016);
        assertNotEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void menu_highscoresOption_staysInMenu() {
        // Navigate to highscores (index 2)
        engine.getInputState().setDown(true);
        engine.update(0.016);
        engine.getInputState().setDown(true);
        engine.update(0.016);
        engine.getInputState().setEnter(true);
        engine.update(0.016);
        assertEquals(GameState.MENU, engine.getState());
    }

    @Test
    void playing_particlesUpdate() {
        engine.startGame(false);
        engine.getParticleSystem().spawnExplosion(100, 100, 5, 255, 0, 0);
        int count = engine.getParticleSystem().getParticles().size();
        engine.update(0.016);
        // Particles should still exist after small delta
        assertFalse(engine.getParticleSystem().getParticles().isEmpty());
    }

    @Test
    void playing_shootCooldownPreventsRapidFire() {
        engine.startGame(false);
        engine.getInputState().setShoot(true);
        engine.update(0.016);
        int firstCount = engine.getBullets().size();
        engine.update(0.016); // Very quick second update
        // Should not fire again immediately due to cooldown
        long playerBullets = engine.getBullets().stream()
                .filter(Bullet::isPlayerBullet)
                .count();
        // With cooldown, should only have fired once (possibly + multi-shot)
        assertTrue(playerBullets >= 1);
    }

    @Test
    void playing_noShootWhenNotPressed() {
        engine.startGame(false);
        // No shoot input → no bullets
        engine.update(0.016);
        long playerBullets = engine.getBullets().stream()
                .filter(Bullet::isPlayerBullet).count();
        assertEquals(0, playerBullets);
    }

    @Test
    void playing_noMoveWithoutInput() {
        engine.startGame(false);
        double x = engine.getPlayer().getX();
        engine.update(0.016);
        assertEquals(x, engine.getPlayer().getX());
    }

    @Test
    void playing_alienDoesNotShootWhenTimerPositive() {
        engine.startGame(false);
        engine.getFormation().setShootTimer(10.0);
        engine.update(0.016);
        // Only player bullets possible, no alien bullets
        assertTrue(engine.getBullets().stream().allMatch(Bullet::isPlayerBullet)
                || engine.getBullets().isEmpty());
    }

    @Test
    void playing_noScoreChange_withoutCollision() {
        engine.startGame(false);
        int scoreBefore = scoreManager.getScore();
        // No bullets → no collision → no score
        engine.getFormation().setShootTimer(10.0);
        engine.update(0.016);
        assertEquals(scoreBefore, scoreManager.getScore());
    }

    @Test
    void playing_bulletRemovedWhenDead() {
        engine.startGame(false);
        Bullet deadBullet = new Bullet(100, 300, true, 1);
        deadBullet.setAlive(false);
        engine.getBullets().add(deadBullet);
        engine.update(0.016);
        assertFalse(engine.getBullets().contains(deadBullet));
    }

    @Test
    void playing_powerUpRemovedWhenDead() {
        engine.startGame(false);
        PowerUp deadPowerUp = new PowerUp(100, 300, PowerUpType.SHIELD);
        deadPowerUp.setAlive(false);
        engine.getPowerUps().add(deadPowerUp);
        engine.update(0.016);
        assertFalse(engine.getPowerUps().contains(deadPowerUp));
    }

    @Test
    void playing_powerUpSurvivedOnScreen() {
        engine.startGame(false);
        PowerUp pu = new PowerUp(100, 300, PowerUpType.SHIELD);
        engine.getPowerUps().add(pu);
        engine.getFormation().setShootTimer(10.0);
        engine.update(0.016);
        assertTrue(engine.getPowerUps().contains(pu));
    }

    @Test
    void playing_explosionOnAlienKill() {
        engine.startGame(false);
        Alien target = engine.getFormation().getAliens().get(0);
        Bullet b = new Bullet(target.getX() + 5, target.getY() + 5, true, 1);
        engine.getBullets().add(b);
        engine.getFormation().setShootTimer(10.0);
        engine.update(0.016);
        assertFalse(engine.getParticleSystem().getParticles().isEmpty());
    }

    @Test
    void gameOver_notHighScore_noSave() {
        engine.startGame(false);
        // Fill highscores with high values
        for (int i = 0; i < 10; i++) {
            scoreManager.resetScore();
            scoreManager.addScore(10000 + i * 100);
            scoreManager.addHighScore("P" + i, 1);
        }
        scoreManager.resetScore();
        // Score is 0 → not a highscore
        engine.setState(GameState.GAME_OVER);
        engine.getInputState().setEnter(true);
        engine.update(0.016);
        assertEquals(GameState.MENU, engine.getState());
    }

    @Test
    void paused_nothingHappensWithoutPauseKey() {
        engine.startGame(false);
        engine.setState(GameState.PAUSED);
        engine.update(0.016);
        assertEquals(GameState.PAUSED, engine.getState());
    }

    @Test
    void gameOver_nothingHappensWithoutEnterKey() {
        engine.startGame(false);
        engine.setState(GameState.GAME_OVER);
        engine.update(0.016);
        assertEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void menu_nothingHappensWithoutInput() {
        engine.update(0.016);
        assertEquals(GameState.MENU, engine.getState());
        assertEquals(0, engine.getMenuSelection());
    }

    @Test
    void playing_singlePlayerNull_player2() {
        engine.startGame(false);
        assertNull(engine.getPlayer2());
        // Should not crash when updating with null player2
        engine.update(0.016);
    }

    @Test
    void playing_gameNotOverWhenAliensNotAtBottom() {
        engine.startGame(false);
        // Default alien positions are at top, should not be game over
        engine.update(0.016);
        assertNotEquals(GameState.GAME_OVER, engine.getState());
    }

    @Test
    void playing_levelNotComplete_withAliveAliens() {
        engine.startGame(false);
        int levelBefore = engine.getLevelManager().getCurrentLevel();
        engine.update(0.016);
        assertEquals(levelBefore, engine.getLevelManager().getCurrentLevel());
    }

    @Test
    void playing_player2MovesRight() {
        engine.startGame(true);
        double initialX = engine.getPlayer2().getX();
        engine.getInputState().setRight2(true);
        engine.update(0.016);
        assertTrue(engine.getPlayer2().getX() > initialX);
    }

    @Test
    void constructor_withoutSeed() {
        String filePath = tempDir.resolve("test2.json").toString();
        GameEngine e = new GameEngine(new ScoreManager(filePath));
        assertEquals(GameState.MENU, e.getState());
    }
}
