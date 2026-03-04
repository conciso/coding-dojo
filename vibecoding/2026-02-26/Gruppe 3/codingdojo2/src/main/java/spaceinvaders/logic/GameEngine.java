package spaceinvaders.logic;

import spaceinvaders.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameEngine {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    private GameState state;
    private Player player;
    private Player player2;
    private AlienFormation formation;
    private final List<Bullet> bullets;
    private final List<Shield> shields;
    private final List<PowerUp> powerUps;
    private final ParticleSystem particleSystem;
    private final CollisionDetector collisionDetector;
    private final LevelManager levelManager;
    private final ScoreManager scoreManager;
    private final InputState inputState;
    private final Random random;
    private int menuSelection;
    private boolean multiplayer;

    public GameEngine(ScoreManager scoreManager) {
        this.state = GameState.MENU;
        this.bullets = new ArrayList<>();
        this.shields = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.particleSystem = new ParticleSystem();
        this.collisionDetector = new CollisionDetector();
        this.levelManager = new LevelManager();
        this.scoreManager = scoreManager;
        this.inputState = new InputState();
        this.random = new Random();
        this.menuSelection = 0;
        this.multiplayer = false;
    }

    public GameEngine(ScoreManager scoreManager, long seed) {
        this(scoreManager);
        this.random.setSeed(seed);
    }

    public void startGame(boolean multiplayer) {
        this.multiplayer = multiplayer;
        levelManager.reset();
        scoreManager.resetScore();
        bullets.clear();
        shields.clear();
        powerUps.clear();
        particleSystem.clear();

        player = new Player(SCREEN_WIDTH / 2.0 - 20, SCREEN_HEIGHT - 60, 1);
        if (multiplayer) {
            player2 = new Player(SCREEN_WIDTH / 2.0 + 40, SCREEN_HEIGHT - 60, 2);
            state = GameState.MULTIPLAYER;
        } else {
            player2 = null;
            state = GameState.PLAYING;
        }
        setupLevel();
    }

    void setupLevel() {
        formation = new AlienFormation(levelManager.getAlienSpeed(), levelManager.getShootInterval());
        formation.createFormation(levelManager.getRows(), levelManager.getCols(), levelManager.getRowTypes());
        setupShields();
    }

    void setupShields() {
        shields.clear();
        int count = levelManager.getShieldCount();
        double spacing = SCREEN_WIDTH / (count + 1.0);
        for (int i = 0; i < count; i++) {
            shields.add(new Shield(spacing * (i + 1) - 30, SCREEN_HEIGHT - 140));
        }
    }

    public void update(double deltaTime) {
        switch (state) {
            case MENU -> updateMenu();
            case PLAYING, MULTIPLAYER -> updatePlaying(deltaTime);
            case PAUSED -> updatePaused();
            case GAME_OVER -> updateGameOver();
        }
    }

    void updateMenu() {
        if (inputState.isUp()) {
            menuSelection = (menuSelection - 1 + 4) % 4;
            inputState.setUp(false);
        }
        if (inputState.isDown()) {
            menuSelection = (menuSelection + 1) % 4;
            inputState.setDown(false);
        }
        if (inputState.isEnter()) {
            inputState.setEnter(false);
            switch (menuSelection) {
                case 0 -> startGame(false);
                case 1 -> startGame(true);
                case 2 -> {} // Show highscores (handled in view)
                case 3 -> System.exit(0);
            }
        }
    }

    void updatePlaying(double deltaTime) {
        if (inputState.isPause()) {
            inputState.setPause(false);
            state = GameState.PAUSED;
            return;
        }

        updatePlayer(player, inputState.isLeft(), inputState.isRight(), inputState.isShoot(), deltaTime);
        if (player2 != null && player2.isAlive()) {
            updatePlayer(player2, inputState.isLeft2(), inputState.isRight2(), inputState.isShoot2(), deltaTime);
        }

        formation.update(deltaTime, SCREEN_WIDTH);
        Bullet alienBullet = formation.tryShoot();
        if (alienBullet != null) {
            bullets.add(alienBullet);
        }

        updateBullets(deltaTime);
        updatePowerUps(deltaTime);
        particleSystem.update(deltaTime);

        CollisionDetector.CollisionResult result = collisionDetector.checkAll(
                bullets, formation, player, player2, shields, powerUps);

        if (result.scoreGained > 0) {
            scoreManager.addScore(result.scoreGained);
            if (result.destroyedAlien != null) {
                spawnExplosion(result.destroyedAlien);
                maybeSpawnPowerUp(result.destroyedAlien);
            }
        }

        checkGameOver();
        checkLevelComplete();
    }

    void updatePlayer(Player p, boolean left, boolean right, boolean shoot, double deltaTime) {
        p.update(deltaTime);
        if (left) {
            p.setX(Math.max(0, p.getX() - p.getSpeed() * deltaTime));
        }
        if (right) {
            p.setX(Math.min(SCREEN_WIDTH - p.getWidth(), p.getX() + p.getSpeed() * deltaTime));
        }
        if (shoot && p.canShoot()) {
            firePlayerBullet(p);
        }
    }

    void firePlayerBullet(Player p) {
        p.resetShootCooldown();
        if (p.hasMultiShot()) {
            bullets.add(new Bullet(p.getX() + p.getWidth() / 2.0 - 2, p.getY(), true, p.getPlayerNumber()));
            bullets.add(new Bullet(p.getX() + 2, p.getY(), true, p.getPlayerNumber()));
            bullets.add(new Bullet(p.getX() + p.getWidth() - 6, p.getY(), true, p.getPlayerNumber()));
        } else {
            bullets.add(new Bullet(p.getX() + p.getWidth() / 2.0 - 2, p.getY(), true, p.getPlayerNumber()));
        }
    }

    void updateBullets(double deltaTime) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update(deltaTime);
            if (!b.isAlive() || b.isOffScreen(SCREEN_HEIGHT)) {
                it.remove();
            }
        }
    }

    void updatePowerUps(double deltaTime) {
        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            p.update(deltaTime);
            if (!p.isAlive() || p.isOffScreen(SCREEN_HEIGHT)) {
                it.remove();
            }
        }
    }

    void spawnExplosion(Alien alien) {
        particleSystem.spawnExplosion(
                alien.getX() + alien.getWidth() / 2.0,
                alien.getY() + alien.getHeight() / 2.0,
                15, 255, 200, 50
        );
    }

    void maybeSpawnPowerUp(Alien alien) {
        if (random.nextDouble() < 0.15) {
            PowerUpType[] types = PowerUpType.values();
            PowerUpType type = types[random.nextInt(types.length)];
            powerUps.add(new PowerUp(alien.getX(), alien.getY(), type));
        }
    }

    void checkGameOver() {
        boolean allPlayersDead = !player.isAlive() && (player2 == null || !player2.isAlive());
        boolean aliensReachedBottom = formation.hasReachedBottom(SCREEN_HEIGHT);

        if (allPlayersDead || aliensReachedBottom) {
            state = GameState.GAME_OVER;
        }
    }

    void checkLevelComplete() {
        if (formation.allDestroyed()) {
            levelManager.nextLevel();
            bullets.clear();
            powerUps.clear();
            setupLevel();
        }
    }

    void updatePaused() {
        if (inputState.isPause()) {
            inputState.setPause(false);
            state = multiplayer ? GameState.MULTIPLAYER : GameState.PLAYING;
        }
    }

    void updateGameOver() {
        if (inputState.isEnter()) {
            inputState.setEnter(false);
            if (scoreManager.isHighScore()) {
                scoreManager.addHighScore("Player", levelManager.getCurrentLevel());
            }
            state = GameState.MENU;
        }
    }

    // Getters
    public GameState getState() { return state; }
    public void setState(GameState state) { this.state = state; }
    public Player getPlayer() { return player; }
    public Player getPlayer2() { return player2; }
    public AlienFormation getFormation() { return formation; }
    public List<Bullet> getBullets() { return bullets; }
    public List<Shield> getShields() { return shields; }
    public List<PowerUp> getPowerUps() { return powerUps; }
    public ParticleSystem getParticleSystem() { return particleSystem; }
    public LevelManager getLevelManager() { return levelManager; }
    public ScoreManager getScoreManager() { return scoreManager; }
    public InputState getInputState() { return inputState; }
    public int getMenuSelection() { return menuSelection; }
    public boolean isMultiplayer() { return multiplayer; }
}
