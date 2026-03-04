package spaceinvaders.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlienFormationTest {
    private AlienFormation formation;

    @BeforeEach
    void setUp() {
        formation = new AlienFormation(50, 1.0, 42L);
        formation.createFormation(3, 5, new AlienType[]{AlienType.BASIC, AlienType.FAST, AlienType.TANK});
    }

    @Test
    void createFormation_createsCorrectNumberOfAliens() {
        assertEquals(15, formation.getAliens().size());
    }

    @Test
    void createFormation_allAlive() {
        assertTrue(formation.getAliens().stream().allMatch(Alien::isAlive));
    }

    @Test
    void createFormation_correctTypes() {
        // Row 0: BASIC (indices 0-4), Row 1: FAST (5-9), Row 2: TANK (10-14)
        assertEquals(AlienType.BASIC, formation.getAliens().get(0).getType());
        assertEquals(AlienType.FAST, formation.getAliens().get(5).getType());
        assertEquals(AlienType.TANK, formation.getAliens().get(10).getType());
    }

    @Test
    void update_movesAliens() {
        double initialX = formation.getAliens().get(0).getX();
        formation.update(0.1, 800);
        double newX = formation.getAliens().get(0).getX();
        assertNotEquals(initialX, newX);
    }

    @Test
    void update_reverseDirectionAtEdge() {
        // Move aliens towards edge
        for (Alien alien : formation.getAliens()) {
            alien.setX(770);
        }
        double dirBefore = formation.getDirection();
        formation.update(0.1, 800);
        assertEquals(-dirBefore, formation.getDirection());
    }

    @Test
    void update_dropsOnReverse() {
        for (Alien alien : formation.getAliens()) {
            alien.setX(770);
        }
        double yBefore = formation.getAliens().get(0).getY();
        formation.update(0.1, 800);
        double yAfter = formation.getAliens().get(0).getY();
        assertTrue(yAfter > yBefore);
    }

    @Test
    void update_noUpdateWhenAllDead() {
        formation.getAliens().forEach(a -> a.setAlive(false));
        double x = formation.getAliens().get(0).getX();
        formation.update(0.1, 800);
        assertEquals(x, formation.getAliens().get(0).getX());
    }

    @Test
    void tryShoot_returnsNullWhenTimerPositive() {
        formation.setShootTimer(1.0);
        assertNull(formation.tryShoot());
    }

    @Test
    void tryShoot_returnsBulletWhenReady() {
        formation.setShootTimer(0);
        Bullet b = formation.tryShoot();
        assertNotNull(b);
        assertFalse(b.isPlayerBullet());
    }

    @Test
    void tryShoot_resetsTimer() {
        formation.setShootTimer(0);
        formation.tryShoot();
        assertTrue(formation.getShootTimer() > 0);
    }

    @Test
    void tryShoot_returnsNullWhenAllDead() {
        formation.getAliens().forEach(a -> a.setAlive(false));
        formation.setShootTimer(0);
        assertNull(formation.tryShoot());
    }

    @Test
    void allDestroyed_falseWithAlive() {
        assertFalse(formation.allDestroyed());
    }

    @Test
    void allDestroyed_trueWhenAllDead() {
        formation.getAliens().forEach(a -> a.setAlive(false));
        assertTrue(formation.allDestroyed());
    }

    @Test
    void hasReachedBottom_false() {
        assertFalse(formation.hasReachedBottom(600));
    }

    @Test
    void hasReachedBottom_trueWhenLow() {
        formation.getAliens().get(0).setY(550);
        assertTrue(formation.hasReachedBottom(600));
    }

    @Test
    void speedIncreasesAsAliensDefeated() {
        // Kill some aliens and verify formation moves faster
        double xBefore = formation.getAliens().get(0).getX();
        formation.update(0.1, 800);
        double moveWithAll = formation.getAliens().get(0).getX() - xBefore;

        // Reset position
        formation.getAliens().get(0).setX(xBefore);
        // Kill half the aliens
        for (int i = 0; i < 7; i++) {
            formation.getAliens().get(i + 1).setAlive(false);
        }
        formation.update(0.1, 800);
        double moveWithFewer = formation.getAliens().get(0).getX() - xBefore;

        assertTrue(Math.abs(moveWithFewer) > Math.abs(moveWithAll));
    }

    @Test
    void setBaseSpeed_works() {
        formation.setBaseSpeed(100);
        assertEquals(100, formation.getBaseSpeed());
    }

    @Test
    void setShootInterval_works() {
        formation.setShootInterval(2.0);
        assertEquals(2.0, formation.getShootInterval());
    }

    @Test
    void setDirection_works() {
        formation.setDirection(-1);
        assertEquals(-1, formation.getDirection());
    }

    @Test
    void update_reversesAtLeftEdge() {
        // Move aliens to left edge
        for (Alien alien : formation.getAliens()) {
            if (alien.isAlive()) {
                alien.setX(-5);
            }
        }
        formation.setDirection(-1);
        formation.update(0.1, 800);
        assertEquals(1, formation.getDirection());
    }

    @Test
    void emptyFormation_calculateSpeed() {
        AlienFormation empty = new AlienFormation(50, 1.0);
        // No aliens created → totalCount == 0
        empty.update(0.1, 800);
        assertTrue(empty.allDestroyed());
    }

    @Test
    void update_deadAlienNotMoved() {
        Alien first = formation.getAliens().get(0);
        first.setAlive(false);
        double xBefore = first.getX();
        formation.update(0.1, 800);
        assertEquals(xBefore, first.getX());
    }

    @Test
    void update_deadAlienNotDropped() {
        // Move alive aliens to edge to trigger drop
        for (Alien alien : formation.getAliens()) {
            alien.setX(770);
        }
        Alien dead = formation.getAliens().get(0);
        dead.setAlive(false);
        double yBefore = dead.getY();
        formation.update(0.1, 800);
        assertEquals(yBefore, dead.getY());
    }
}
