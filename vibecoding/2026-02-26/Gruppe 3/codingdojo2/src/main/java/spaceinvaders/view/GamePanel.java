package spaceinvaders.view;

import spaceinvaders.logic.GameEngine;
import spaceinvaders.logic.GameState;
import spaceinvaders.logic.InputState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = GameEngine.SCREEN_WIDTH;
    public static final int HEIGHT = GameEngine.SCREEN_HEIGHT;
    private static final int TARGET_FPS = 60;

    private final GameEngine engine;
    private final MenuRenderer menuRenderer;
    private final GameRenderer gameRenderer;
    private Thread gameThread;
    private volatile boolean running;

    public GamePanel(GameEngine engine) {
        this.engine = engine;
        this.menuRenderer = new MenuRenderer();
        this.gameRenderer = new GameRenderer();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new InputHandler());
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerFrame = 1_000_000_000.0 / TARGET_FPS;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerFrame;
            lastTime = now;

            if (delta >= 1) {
                engine.update(1.0 / TARGET_FPS);
                repaint();
                delta--;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GameState state = engine.getState();
        if (state == GameState.MENU) {
            menuRenderer.render(g2d, engine, WIDTH, HEIGHT);
        } else {
            gameRenderer.render(g2d, engine, WIDTH, HEIGHT);
        }
    }

    private class InputHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            InputState input = engine.getInputState();
            switch (e.getKeyCode()) {
                // Player 1 - Arrow keys
                case KeyEvent.VK_LEFT -> input.setLeft(true);
                case KeyEvent.VK_RIGHT -> input.setRight(true);
                case KeyEvent.VK_SPACE -> input.setShoot(true);
                // Player 2 - WASD
                case KeyEvent.VK_A -> input.setLeft2(true);
                case KeyEvent.VK_D -> input.setRight2(true);
                case KeyEvent.VK_W -> input.setShoot2(true);
                // Menu/Pause
                case KeyEvent.VK_ESCAPE -> input.setPause(true);
                case KeyEvent.VK_ENTER -> input.setEnter(true);
                case KeyEvent.VK_UP -> input.setUp(true);
                case KeyEvent.VK_DOWN -> input.setDown(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            InputState input = engine.getInputState();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> input.setLeft(false);
                case KeyEvent.VK_RIGHT -> input.setRight(false);
                case KeyEvent.VK_SPACE -> input.setShoot(false);
                case KeyEvent.VK_A -> input.setLeft2(false);
                case KeyEvent.VK_D -> input.setRight2(false);
                case KeyEvent.VK_W -> input.setShoot2(false);
            }
        }
    }
}
