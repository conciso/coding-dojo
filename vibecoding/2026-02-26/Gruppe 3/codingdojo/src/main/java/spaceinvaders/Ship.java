package spaceinvaders;

class Ship {

    private int x = 400;
    private int y = 550;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void moveRight() {
        x++;
    }

    void moveLeft() {
        x--;
    }

    Bullet shoot() {
        return new Bullet(x, y);
    }
}
