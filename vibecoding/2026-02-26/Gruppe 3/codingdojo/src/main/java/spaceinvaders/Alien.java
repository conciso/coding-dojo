package spaceinvaders;

class Alien {

    private int x;
    private int y;

    Alien(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    private boolean alive = true;

    boolean isAlive() {
        return alive;
    }

    void kill() {
        alive = false;
    }

    void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
