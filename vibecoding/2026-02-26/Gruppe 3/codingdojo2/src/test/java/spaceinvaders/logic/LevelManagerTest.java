package spaceinvaders.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spaceinvaders.model.AlienType;

import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {
    private LevelManager manager;

    @BeforeEach
    void setUp() {
        manager = new LevelManager();
    }

    @Test
    void initialLevel_isOne() {
        assertEquals(1, manager.getCurrentLevel());
    }

    @Test
    void nextLevel_incrementsLevel() {
        manager.nextLevel();
        assertEquals(2, manager.getCurrentLevel());
    }

    @Test
    void reset_goesBackToOne() {
        manager.nextLevel();
        manager.nextLevel();
        manager.reset();
        assertEquals(1, manager.getCurrentLevel());
    }

    @Test
    void setCurrentLevel_works() {
        manager.setCurrentLevel(5);
        assertEquals(5, manager.getCurrentLevel());
    }

    @Test
    void getRows_increasesWithLevel() {
        int rowsL1 = manager.getRows();
        manager.setCurrentLevel(5);
        int rowsL5 = manager.getRows();
        assertTrue(rowsL5 >= rowsL1);
    }

    @Test
    void getRows_maxIsSeven() {
        manager.setCurrentLevel(100);
        assertTrue(manager.getRows() <= 7);
    }

    @Test
    void getCols_increasesWithLevel() {
        int colsL1 = manager.getCols();
        manager.setCurrentLevel(10);
        int colsL10 = manager.getCols();
        assertTrue(colsL10 >= colsL1);
    }

    @Test
    void getCols_maxIsTwelve() {
        manager.setCurrentLevel(100);
        assertTrue(manager.getCols() <= 12);
    }

    @Test
    void getAlienSpeed_increasesWithLevel() {
        double speedL1 = manager.getAlienSpeed();
        manager.setCurrentLevel(5);
        double speedL5 = manager.getAlienSpeed();
        assertTrue(speedL5 > speedL1);
    }

    @Test
    void getShootInterval_decreasesWithLevel() {
        double intervalL1 = manager.getShootInterval();
        manager.setCurrentLevel(5);
        double intervalL5 = manager.getShootInterval();
        assertTrue(intervalL5 < intervalL1);
    }

    @Test
    void getShootInterval_hasMinimum() {
        manager.setCurrentLevel(100);
        assertTrue(manager.getShootInterval() >= 0.5);
    }

    @Test
    void getRowTypes_earlyLevels() {
        AlienType[] types = manager.getRowTypes();
        assertEquals(AlienType.BASIC, types[0]);
    }

    @Test
    void getRowTypes_midLevels() {
        manager.setCurrentLevel(3);
        AlienType[] types = manager.getRowTypes();
        assertEquals(AlienType.BASIC, types[0]);
        assertEquals(AlienType.FAST, types[1]);
        assertEquals(AlienType.TANK, types[2]);
    }

    @Test
    void getRowTypes_lateLevels() {
        manager.setCurrentLevel(5);
        AlienType[] types = manager.getRowTypes();
        assertEquals(AlienType.FAST, types[0]);
        assertEquals(AlienType.TANK, types[1]);
        assertEquals(AlienType.BOSS, types[2]);
    }

    @Test
    void getShieldCount_increasesWithLevel() {
        int shieldsL1 = manager.getShieldCount();
        manager.setCurrentLevel(10);
        int shieldsL10 = manager.getShieldCount();
        assertTrue(shieldsL10 >= shieldsL1);
    }

    @Test
    void getShieldCount_maxIsFour() {
        manager.setCurrentLevel(100);
        assertTrue(manager.getShieldCount() <= 4);
    }
}
