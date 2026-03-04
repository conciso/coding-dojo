package spaceinvaders.model;

public class Particle {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double life;
    private double maxLife;
    private int r, g, b;

    public Particle(double x, double y, double vx, double vy, double life, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.life = life;
        this.maxLife = life;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        life -= deltaTime;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public float getAlpha() {
        return (float) Math.max(0, Math.min(1, life / maxLife));
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getLife() { return life; }
    public double getMaxLife() { return maxLife; }
    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }
}
