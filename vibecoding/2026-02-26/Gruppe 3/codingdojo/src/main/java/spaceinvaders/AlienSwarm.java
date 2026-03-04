package spaceinvaders;

import java.util.ArrayList;
import java.util.List;

class AlienSwarm {

    private final List<Alien> aliens = new ArrayList<>();
    private int direction = 1;
    private int descendCounter;

    AlienSwarm(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                aliens.add(new Alien(100 + col * 60, 60 + row * 40));
            }
        }
    }

    List<Alien> getAliens() {
        return aliens;
    }

    void move() {
        aliens.stream().filter(Alien::isAlive).forEach(alien -> alien.moveBy(direction, 0));
        boolean hitEdge = aliens.stream().filter(Alien::isAlive)
                .anyMatch(a -> a.getX() <= 10 || a.getX() >= 790);
        if (hitEdge) {
            direction = -direction;
        }
        descendCounter++;
        if (descendCounter >= 5) {
            aliens.stream().filter(Alien::isAlive).forEach(alien -> alien.moveBy(0, 2));
            descendCounter = 0;
        }
    }

    boolean allDead() {
        return aliens.stream().noneMatch(Alien::isAlive);
    }

    private static final int BOTTOM_BORDER = 580;

    boolean hasReachedBottom() {
        return aliens.stream().anyMatch(alien -> alien.getY() >= BOTTOM_BORDER);
    }
}
