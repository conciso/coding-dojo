package spaceinvaders.model;

public class Bullet extends Entity {
    public static final double PLAYER_BULLET_SPEED = -400.0;
    public static final double ALIEN_BULLET_SPEED = 200.0;

    private final double speedY;
    private final boolean playerBullet;
    private final int ownerPlayerNumber;

    public Bullet(double x, double y, boolean playerBullet, int ownerPlayerNumber) {
        super(x, y, 4, 10);
        this.playerBullet = playerBullet;
        this.ownerPlayerNumber = ownerPlayerNumber;
        this.speedY = playerBullet ? PLAYER_BULLET_SPEED : ALIEN_BULLET_SPEED;
    }

    public void update(double deltaTime) {
        y += speedY * deltaTime;
    }

    public boolean isOffScreen(int screenHeight) {
        return y < -height || y > screenHeight;
    }

    public boolean isPlayerBullet() { return playerBullet; }
    public int getOwnerPlayerNumber() { return ownerPlayerNumber; }
    public double getSpeedY() { return speedY; }
}
