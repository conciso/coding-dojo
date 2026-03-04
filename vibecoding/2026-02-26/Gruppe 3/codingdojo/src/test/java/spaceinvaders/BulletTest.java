package spaceinvaders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BulletTest {

    @Test
    void bulletHasInitialPosition() {
        Bullet bullet = new Bullet(100, 500);

        assertThat(bullet.getX()).isEqualTo(100);
        assertThat(bullet.getY()).isEqualTo(500);
    }

    @Test
    void moveMovesUpward() {
        Bullet bullet = new Bullet(100, 500);

        bullet.move();

        assertThat(bullet.getY()).isEqualTo(499);
    }
}
