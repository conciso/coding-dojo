package spaceinvaders.logic;

import spaceinvaders.model.*;

import java.util.List;

public class CollisionDetector {

    public static class CollisionResult {
        public int scoreGained;
        public boolean playerHit;
        public boolean player2Hit;
        public Alien destroyedAlien;

        public CollisionResult() {
            this.scoreGained = 0;
            this.playerHit = false;
            this.player2Hit = false;
            this.destroyedAlien = null;
        }
    }

    public CollisionResult checkAll(
            List<Bullet> bullets,
            AlienFormation formation,
            Player player,
            Player player2,
            List<Shield> shields,
            List<PowerUp> powerUps
    ) {
        CollisionResult result = new CollisionResult();

        checkPlayerBulletsVsAliens(bullets, formation, result);
        checkAlienBulletsVsPlayers(bullets, player, player2, result);
        checkBulletsVsShields(bullets, shields);
        checkPowerUpsVsPlayers(powerUps, player, player2);

        return result;
    }

    void checkPlayerBulletsVsAliens(List<Bullet> bullets, AlienFormation formation, CollisionResult result) {
        for (Bullet bullet : bullets) {
            if (!bullet.isPlayerBullet() || !bullet.isAlive()) continue;
            for (Alien alien : formation.getAliens()) {
                if (!alien.isAlive()) continue;
                if (bullet.intersects(alien)) {
                    bullet.setAlive(false);
                    if (alien.hit()) {
                        result.scoreGained += alien.getPoints();
                        result.destroyedAlien = alien;
                    }
                    break;
                }
            }
        }
    }

    void checkAlienBulletsVsPlayers(List<Bullet> bullets, Player player, Player player2, CollisionResult result) {
        for (Bullet bullet : bullets) {
            if (bullet.isPlayerBullet() || !bullet.isAlive()) continue;
            if (player != null && player.isAlive() && bullet.intersects(player)) {
                bullet.setAlive(false);
                player.hit();
                result.playerHit = true;
            }
            if (player2 != null && player2.isAlive() && bullet.intersects(player2)) {
                bullet.setAlive(false);
                player2.hit();
                result.player2Hit = true;
            }
        }
    }

    void checkBulletsVsShields(List<Bullet> bullets, List<Shield> shields) {
        for (Bullet bullet : bullets) {
            if (!bullet.isAlive()) continue;
            for (Shield shield : shields) {
                if (!shield.isAlive()) continue;
                if (bullet.intersects(shield)) {
                    bullet.setAlive(false);
                    shield.hit();
                    break;
                }
            }
        }
    }

    void checkPowerUpsVsPlayers(List<PowerUp> powerUps, Player player, Player player2) {
        for (PowerUp powerUp : powerUps) {
            if (!powerUp.isAlive()) continue;
            if (player != null && player.isAlive() && powerUp.intersects(player)) {
                powerUp.setAlive(false);
                player.applyPowerUp(powerUp.getType());
            }
            if (player2 != null && player2.isAlive() && powerUp.intersects(player2)) {
                powerUp.setAlive(false);
                player2.applyPowerUp(powerUp.getType());
            }
        }
    }
}
