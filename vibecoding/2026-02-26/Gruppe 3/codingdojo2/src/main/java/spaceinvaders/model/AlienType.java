package spaceinvaders.model;

public enum AlienType {
    BASIC(10, 1, 1.0),
    FAST(20, 1, 1.8),
    TANK(30, 2, 0.8),
    BOSS(50, 3, 0.6);

    private final int points;
    private final int hitPoints;
    private final double speedMultiplier;

    AlienType(int points, int hitPoints, double speedMultiplier) {
        this.points = points;
        this.hitPoints = hitPoints;
        this.speedMultiplier = speedMultiplier;
    }

    public int getPoints() { return points; }
    public int getHitPoints() { return hitPoints; }
    public double getSpeedMultiplier() { return speedMultiplier; }
}
