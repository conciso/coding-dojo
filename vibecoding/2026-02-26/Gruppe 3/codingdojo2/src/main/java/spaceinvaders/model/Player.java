package spaceinvaders.model;

public class Player extends Entity {
    public static final int DEFAULT_LIVES = 3;
    public static final double DEFAULT_SPEED = 300.0;
    public static final double SHOOT_COOLDOWN = 0.4;
    public static final double RAPID_FIRE_COOLDOWN = 0.15;

    private int lives;
    private double speed;
    private double shootCooldown;
    private double shieldTimer;
    private double rapidFireTimer;
    private double multiShotTimer;
    private int playerNumber;

    public Player(double x, double y, int playerNumber) {
        super(x, y, 40, 30);
        this.lives = DEFAULT_LIVES;
        this.speed = DEFAULT_SPEED;
        this.shootCooldown = 0;
        this.shieldTimer = 0;
        this.rapidFireTimer = 0;
        this.multiShotTimer = 0;
        this.playerNumber = playerNumber;
    }

    public void update(double deltaTime) {
        if (shootCooldown > 0) shootCooldown -= deltaTime;
        if (shieldTimer > 0) shieldTimer -= deltaTime;
        if (rapidFireTimer > 0) rapidFireTimer -= deltaTime;
        if (multiShotTimer > 0) multiShotTimer -= deltaTime;
    }

    public boolean canShoot() {
        return shootCooldown <= 0;
    }

    public void resetShootCooldown() {
        shootCooldown = hasRapidFire() ? RAPID_FIRE_COOLDOWN : SHOOT_COOLDOWN;
    }

    public void applyPowerUp(PowerUpType type) {
        switch (type) {
            case SHIELD -> shieldTimer = type.getDuration();
            case RAPID_FIRE -> rapidFireTimer = type.getDuration();
            case MULTI_SHOT -> multiShotTimer = type.getDuration();
            case EXTRA_LIFE -> lives++;
        }
    }

    public void hit() {
        if (hasShield()) {
            shieldTimer = 0;
            return;
        }
        lives--;
        if (lives <= 0) {
            alive = false;
        }
    }

    public boolean hasShield() { return shieldTimer > 0; }
    public boolean hasRapidFire() { return rapidFireTimer > 0; }
    public boolean hasMultiShot() { return multiShotTimer > 0; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
    public double getSpeed() { return speed; }
    public int getPlayerNumber() { return playerNumber; }
    public double getShieldTimer() { return shieldTimer; }
    public double getRapidFireTimer() { return rapidFireTimer; }
    public double getMultiShotTimer() { return multiShotTimer; }
    public double getShootCooldown() { return shootCooldown; }
}
