package spaceinvaders;

import java.util.ArrayList;
import java.util.List;

class Game {

    private final Ship ship = new Ship();
    private final AlienSwarm alienSwarm = new AlienSwarm(3, 8);
    private final List<Bullet> bullets = new ArrayList<>();

    Ship getShip() {
        return ship;
    }

    AlienSwarm getAlienSwarm() {
        return alienSwarm;
    }

    List<Bullet> getBullets() {
        return bullets;
    }

    void shoot() {
        bullets.add(ship.shoot());
    }

    void tick() {
        bullets.forEach(Bullet::move);
        checkCollisions();
        bullets.removeIf(b -> b.getY() < 0);
    }

    private void checkCollisions() {
        var bulletsToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            for (Alien alien : alienSwarm.getAliens()) {
                if (alien.isAlive()
                        && Math.abs(bullet.getX() - alien.getX()) < 20
                        && Math.abs(bullet.getY() - alien.getY()) < 20) {
                    alien.kill();
                    bulletsToRemove.add(bullet);
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
    }

    boolean isOver() {
        return alienSwarm.hasReachedBottom();
    }

    boolean isWon() {
        return alienSwarm.allDead();
    }
}
