package spaceinvaders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void gameHasShip() {
        Game game = new Game();

        assertThat(game.getShip()).isNotNull();
    }

    @Test
    void gameHasAlienSwarm() {
        Game game = new Game();

        assertThat(game.getAlienSwarm()).isNotNull();
    }

    @Test
    void shootAddsBullet() {
        Game game = new Game();

        game.shoot();

        assertThat(game.getBullets()).hasSize(1);
    }

    @Test
    void tickMovesBullets() {
        Game game = new Game();
        game.shoot();
        int startY = game.getBullets().getFirst().getY();

        game.tick();

        assertThat(game.getBullets().getFirst().getY()).isLessThan(startY);
    }

    @Test
    void tickKillsAlienWhenBulletHits() {
        Game game = new Game();
        Alien target = game.getAlienSwarm().getAliens().getFirst();

        // Move alien directly in front of ship (one tick away)
        int shipX = game.getShip().getX();
        int shipY = game.getShip().getY();
        target.moveBy(shipX - target.getX(), shipY - 1 - target.getY());

        game.shoot();
        game.tick();

        assertThat(target.isAlive()).isFalse();
    }

    @Test
    void bulletRemovedAfterHit() {
        Game game = new Game();
        Alien target = game.getAlienSwarm().getAliens().getFirst();
        int shipX = game.getShip().getX();
        int shipY = game.getShip().getY();
        target.moveBy(shipX - target.getX(), shipY - 1 - target.getY());

        game.shoot();
        game.tick();

        assertThat(game.getBullets()).isEmpty();
    }

    @Test
    void isWonWhenAllAliensDead() {
        Game game = new Game();

        game.getAlienSwarm().getAliens().forEach(Alien::kill);

        assertThat(game.isWon()).isTrue();
    }

    @Test
    void isNotOverInitially() {
        Game game = new Game();

        assertThat(game.isOver()).isFalse();
    }

    @Test
    void isOverWhenAliensReachBottom() {
        Game game = new Game();

        game.getAlienSwarm().getAliens().getFirst().moveBy(0, 600);

        assertThat(game.isOver()).isTrue();
    }
}
