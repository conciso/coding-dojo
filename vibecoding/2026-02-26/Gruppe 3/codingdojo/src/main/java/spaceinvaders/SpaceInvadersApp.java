package spaceinvaders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpaceInvadersApp extends JPanel {

    private final Game game = new Game();
    private boolean leftPressed, rightPressed, spaceDown;
    private int shootCooldown;
    private int alienMoveCounter;

    public SpaceInvadersApp() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> leftPressed = true;
                    case KeyEvent.VK_RIGHT -> rightPressed = true;
                    case KeyEvent.VK_SPACE -> spaceDown = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> leftPressed = false;
                    case KeyEvent.VK_RIGHT -> rightPressed = false;
                    case KeyEvent.VK_SPACE -> spaceDown = false;
                }
            }
        });

        new Timer(16, _ -> {
            update();
            repaint();
        }).start();
    }

    private void update() {
        if (game.isOver() || game.isWon()) return;

        if (leftPressed) {
            for (int i = 0; i < 5; i++) game.getShip().moveLeft();
        }
        if (rightPressed) {
            for (int i = 0; i < 5; i++) game.getShip().moveRight();
        }

        if (shootCooldown > 0) shootCooldown--;
        if (spaceDown && shootCooldown == 0) {
            game.shoot();
            shootCooldown = 15;
        }

        for (int i = 0; i < 3; i++) {
            game.tick();
        }

        game.getAlienSwarm().move();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Ship
        Ship ship = game.getShip();
        g2.setColor(Color.GREEN);
        g2.fillRect(ship.getX() - 20, ship.getY() - 5, 40, 10);
        g2.fillRect(ship.getX() - 5, ship.getY() - 15, 10, 10);

        // Aliens
        for (Alien alien : game.getAlienSwarm().getAliens()) {
            if (alien.isAlive()) {
                g2.setColor(Color.WHITE);
                g2.fillRect(alien.getX() - 15, alien.getY() - 10, 30, 20);
                g2.setColor(Color.RED);
                g2.fillRect(alien.getX() - 8, alien.getY() - 5, 5, 5);
                g2.fillRect(alien.getX() + 3, alien.getY() - 5, 5, 5);
            }
        }

        // Bullets
        g2.setColor(Color.YELLOW);
        for (Bullet bullet : game.getBullets()) {
            g2.fillRect(bullet.getX() - 2, bullet.getY() - 5, 4, 10);
        }

        // Game state overlay
        g2.setFont(new Font("Monospaced", Font.BOLD, 48));
        if (game.isWon()) {
            g2.setColor(Color.GREEN);
            drawCentered(g2, "YOU WIN!", 300);
        } else if (game.isOver()) {
            g2.setColor(Color.RED);
            drawCentered(g2, "GAME OVER", 300);
        }
    }

    private void drawCentered(Graphics2D g2, String text, int y) {
        int x = (getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        g2.drawString(text, x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var frame = new JFrame("Space Invaders");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SpaceInvadersApp());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
