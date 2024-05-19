package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean up, down, left, right, space;

    public KeyHandler() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W,
                 KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_A,
                 KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_S,
                 KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_D,
                 KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_SPACE:
                space = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W
            , KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN,
                 KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_A,
                 KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_D,
                 KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_SPACE:
                space = false;
                break;
        }
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
