package spaceinvaders.model;

public class PowerUp extends Entity {
    private static final double FALL_SPEED = 100.0;
    private final PowerUpType type;

    public PowerUp(double x, double y, PowerUpType type) {
        super(x, y, 20, 20);
        this.type = type;
    }

    public void update(double deltaTime) {
        y += FALL_SPEED * deltaTime;
    }

    public boolean isOffScreen(int screenHeight) {
        return y > screenHeight;
    }

    public PowerUpType getType() { return type; }
    public double getFallSpeed() { return FALL_SPEED; }
}
