package spaceinvaders.model;

public class Alien extends Entity {
    private final AlienType type;
    private int hitPoints;

    public Alien(double x, double y, AlienType type) {
        super(x, y, 30, 24);
        this.type = type;
        this.hitPoints = type.getHitPoints();
    }

    public boolean hit() {
        hitPoints--;
        if (hitPoints <= 0) {
            alive = false;
            return true;
        }
        return false;
    }

    public AlienType getType() { return type; }
    public int getHitPoints() { return hitPoints; }
    public int getPoints() { return type.getPoints(); }
}
