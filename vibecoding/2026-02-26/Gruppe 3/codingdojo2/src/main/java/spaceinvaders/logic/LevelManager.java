package spaceinvaders.logic;

import spaceinvaders.model.AlienType;

public class LevelManager {
    private int currentLevel;

    public LevelManager() {
        this.currentLevel = 1;
    }

    public void nextLevel() {
        currentLevel++;
    }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int level) { this.currentLevel = level; }

    public int getRows() {
        return Math.min(3 + currentLevel / 2, 7);
    }

    public int getCols() {
        return Math.min(8 + currentLevel / 3, 12);
    }

    public double getAlienSpeed() {
        return 30 + currentLevel * 10;
    }

    public double getShootInterval() {
        return Math.max(0.5, 2.5 - currentLevel * 0.2);
    }

    public AlienType[] getRowTypes() {
        if (currentLevel < 3) {
            return new AlienType[]{AlienType.BASIC, AlienType.BASIC, AlienType.FAST};
        } else if (currentLevel < 5) {
            return new AlienType[]{AlienType.BASIC, AlienType.FAST, AlienType.TANK};
        } else {
            return new AlienType[]{AlienType.FAST, AlienType.TANK, AlienType.BOSS};
        }
    }

    public int getShieldCount() {
        return Math.min(4, 2 + currentLevel / 3);
    }

    public void reset() {
        currentLevel = 1;
    }
}
