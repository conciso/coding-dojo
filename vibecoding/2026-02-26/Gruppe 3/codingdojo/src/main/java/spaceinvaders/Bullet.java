package spaceinvaders;

class Bullet {

    private int x;
    private int y;

    Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void move() {
        y--;
    }
}
