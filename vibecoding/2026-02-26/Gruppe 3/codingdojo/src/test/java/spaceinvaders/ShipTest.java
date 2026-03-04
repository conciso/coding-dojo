package spaceinvaders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShipTest {

    @Test
    void newShipIsAtCenterPosition() {
        Ship ship = new Ship();

        assertThat(ship.getX()).isEqualTo(400);
    }

    @Test
    void moveRightIncreasesX() {
        Ship ship = new Ship();

        ship.moveRight();

        assertThat(ship.getX()).isEqualTo(401);
    }

    @Test
    void moveLeftDecreasesX() {
        Ship ship = new Ship();

        ship.moveLeft();

        assertThat(ship.getX()).isEqualTo(399);
    }

    @Test
    void shootCreatesBulletAtShipPosition() {
        Ship ship = new Ship();

        Bullet bullet = ship.shoot();

        assertThat(bullet).isNotNull();
        assertThat(bullet.getX()).isEqualTo(400);
    }
}
