package spaceinvaders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlienTest {

    @Test
    void alienHasPosition() {
        Alien alien = new Alien(50, 30);

        assertThat(alien.getX()).isEqualTo(50);
        assertThat(alien.getY()).isEqualTo(30);
    }

    @Test
    void alienStartsAlive() {
        Alien alien = new Alien(0, 0);

        assertThat(alien.isAlive()).isTrue();
    }

    @Test
    void killMakesAlienDead() {
        Alien alien = new Alien(0, 0);

        alien.kill();

        assertThat(alien.isAlive()).isFalse();
    }
}
