package spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienFormation {
    private final List<Alien> aliens;
    private double direction;
    private double baseSpeed;
    private double dropDistance;
    private double shootInterval;
    private double shootTimer;
    private final Random random;

    public AlienFormation(double baseSpeed, double shootInterval) {
        this.aliens = new ArrayList<>();
        this.direction = 1;
        this.baseSpeed = baseSpeed;
        this.dropDistance = 20;
        this.shootInterval = shootInterval;
        this.shootTimer = shootInterval;
        this.random = new Random();
    }

    public AlienFormation(double baseSpeed, double shootInterval, long seed) {
        this(baseSpeed, shootInterval);
        this.random.setSeed(seed);
    }

    public void createFormation(int rows, int cols, AlienType[] rowTypes) {
        aliens.clear();
        double startX = 60;
        double startY = 60;
        double spacingX = 50;
        double spacingY = 40;

        for (int row = 0; row < rows; row++) {
            AlienType type = rowTypes[row % rowTypes.length];
            for (int col = 0; col < cols; col++) {
                aliens.add(new Alien(startX + col * spacingX, startY + row * spacingY, type));
            }
        }
    }

    public void update(double deltaTime, int screenWidth) {
        if (aliens.stream().noneMatch(Alien::isAlive)) return;

        double speed = calculateSpeed();
        double dx = direction * speed * deltaTime;

        boolean needsDrop = false;
        for (Alien alien : aliens) {
            if (!alien.isAlive()) continue;
            double newX = alien.getX() + dx;
            if (newX <= 0 || newX + alien.getWidth() >= screenWidth) {
                needsDrop = true;
                break;
            }
        }

        if (needsDrop) {
            direction *= -1;
            for (Alien alien : aliens) {
                if (alien.isAlive()) {
                    alien.setY(alien.getY() + dropDistance);
                }
            }
        } else {
            for (Alien alien : aliens) {
                if (alien.isAlive()) {
                    alien.setX(alien.getX() + dx);
                }
            }
        }

        shootTimer -= deltaTime;
    }

    private double calculateSpeed() {
        long aliveCount = aliens.stream().filter(Alien::isAlive).count();
        long totalCount = aliens.size();
        if (totalCount == 0) return baseSpeed;
        double ratio = 1.0 + (1.0 - (double) aliveCount / totalCount) * 2.0;
        return baseSpeed * ratio;
    }

    public Bullet tryShoot() {
        if (shootTimer > 0) return null;
        shootTimer = shootInterval;

        List<Alien> aliveAliens = aliens.stream().filter(Alien::isAlive).toList();
        if (aliveAliens.isEmpty()) return null;

        Alien shooter = aliveAliens.get(random.nextInt(aliveAliens.size()));
        return new Bullet(
                shooter.getX() + shooter.getWidth() / 2.0 - 2,
                shooter.getY() + shooter.getHeight(),
                false, 0
        );
    }

    public boolean hasReachedBottom(int screenHeight) {
        return aliens.stream()
                .filter(Alien::isAlive)
                .anyMatch(a -> a.getY() + a.getHeight() >= screenHeight - 60);
    }

    public boolean allDestroyed() {
        return aliens.stream().noneMatch(Alien::isAlive);
    }

    public List<Alien> getAliens() { return aliens; }
    public double getDirection() { return direction; }
    public void setDirection(double direction) { this.direction = direction; }
    public double getBaseSpeed() { return baseSpeed; }
    public void setBaseSpeed(double baseSpeed) { this.baseSpeed = baseSpeed; }
    public double getShootInterval() { return shootInterval; }
    public void setShootInterval(double shootInterval) { this.shootInterval = shootInterval; }
    public double getShootTimer() { return shootTimer; }
    public void setShootTimer(double shootTimer) { this.shootTimer = shootTimer; }
}
