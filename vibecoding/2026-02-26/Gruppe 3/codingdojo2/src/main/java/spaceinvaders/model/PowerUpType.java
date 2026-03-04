package spaceinvaders.model;

public enum PowerUpType {
    SHIELD(5.0),
    RAPID_FIRE(8.0),
    MULTI_SHOT(7.0),
    EXTRA_LIFE(0.0);

    private final double duration;

    PowerUpType(double duration) {
        this.duration = duration;
    }

    public double getDuration() { return duration; }
}
