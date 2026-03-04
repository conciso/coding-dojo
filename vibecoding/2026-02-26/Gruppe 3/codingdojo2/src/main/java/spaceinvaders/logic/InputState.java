package spaceinvaders.logic;

public class InputState {
    private boolean left;
    private boolean right;
    private boolean shoot;
    private boolean left2;
    private boolean right2;
    private boolean shoot2;
    private boolean pause;
    private boolean enter;
    private boolean up;
    private boolean down;

    public boolean isLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }
    public boolean isRight() { return right; }
    public void setRight(boolean right) { this.right = right; }
    public boolean isShoot() { return shoot; }
    public void setShoot(boolean shoot) { this.shoot = shoot; }

    public boolean isLeft2() { return left2; }
    public void setLeft2(boolean left2) { this.left2 = left2; }
    public boolean isRight2() { return right2; }
    public void setRight2(boolean right2) { this.right2 = right2; }
    public boolean isShoot2() { return shoot2; }
    public void setShoot2(boolean shoot2) { this.shoot2 = shoot2; }

    public boolean isPause() { return pause; }
    public void setPause(boolean pause) { this.pause = pause; }
    public boolean isEnter() { return enter; }
    public void setEnter(boolean enter) { this.enter = enter; }
    public boolean isUp() { return up; }
    public void setUp(boolean up) { this.up = up; }
    public boolean isDown() { return down; }
    public void setDown(boolean down) { this.down = down; }

    public void reset() {
        left = right = shoot = false;
        left2 = right2 = shoot2 = false;
        pause = enter = up = down = false;
    }
}
