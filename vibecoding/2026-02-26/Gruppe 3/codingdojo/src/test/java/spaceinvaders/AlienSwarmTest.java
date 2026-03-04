package spaceinvaders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlienSwarmTest {

    @Test
    void swarmCreatesAliens() {
        AlienSwarm swarm = new AlienSwarm(2, 3);

        assertThat(swarm.getAliens()).hasSize(6);
    }

    @Test
    void notAllDeadWhenAliensAlive() {
        AlienSwarm swarm = new AlienSwarm(1, 1);

        assertThat(swarm.allDead()).isFalse();
    }

    @Test
    void allDeadWhenAllAliensKilled() {
        AlienSwarm swarm = new AlienSwarm(1, 2);

        swarm.getAliens().forEach(Alien::kill);

        assertThat(swarm.allDead()).isTrue();
    }

    @Test
    void moveMovesAliensHorizontally() {
        AlienSwarm swarm = new AlienSwarm(1, 1);
        int startX = swarm.getAliens().getFirst().getX();

        swarm.move();

        assertThat(swarm.getAliens().getFirst().getX()).isNotEqualTo(startX);
    }

    @Test
    void hasNotReachedBottomInitially() {
        AlienSwarm swarm = new AlienSwarm(1, 1);

        assertThat(swarm.hasReachedBottom()).isFalse();
    }

    @Test
    void hasReachedBottomWhenAlienAtBottomBorder() {
        AlienSwarm swarm = new AlienSwarm(1, 1);

        swarm.getAliens().getFirst().moveBy(0, 600);

        assertThat(swarm.hasReachedBottom()).isTrue();
    }
}
