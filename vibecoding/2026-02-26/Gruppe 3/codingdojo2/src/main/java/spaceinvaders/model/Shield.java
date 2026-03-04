package spaceinvaders.model;

public class Shield extends Entity {
    public static final int MAX_HEALTH = 5;
    private int health;

    public Shield(double x, double y) {
        super(x, y, 60, 40);
        this.health = MAX_HEALTH;
    }

    public void hit() {
        health--;
        if (health <= 0) {
            alive = false;
        }
    }

    public int getHealth() { return health; }

    public double getHealthPercent() {
        return (double) health / MAX_HEALTH;
    }
}
