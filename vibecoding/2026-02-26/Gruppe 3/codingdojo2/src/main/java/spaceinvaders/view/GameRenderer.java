package spaceinvaders.view;

import spaceinvaders.logic.GameEngine;
import spaceinvaders.logic.GameState;
import spaceinvaders.model.*;

import java.awt.*;

public class GameRenderer {
    private static final Color[] ALIEN_COLORS = {
            Color.GREEN, Color.CYAN, Color.ORANGE, Color.RED
    };

    public void render(Graphics2D g, GameEngine engine, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        renderShields(g, engine);
        renderAliens(g, engine);
        renderBullets(g, engine);
        renderPowerUps(g, engine);
        renderPlayer(g, engine.getPlayer(), Color.GREEN);
        if (engine.getPlayer2() != null && engine.getPlayer2().isAlive()) {
            renderPlayer(g, engine.getPlayer2(), Color.BLUE);
        }
        renderParticles(g, engine);
        renderHUD(g, engine, width);

        if (engine.getState() == GameState.PAUSED) {
            renderPaused(g, width, height);
        }
        if (engine.getState() == GameState.GAME_OVER) {
            renderGameOver(g, engine, width, height);
        }
    }

    private void renderPlayer(Graphics2D g, Player player, Color color) {
        if (player == null || !player.isAlive()) return;
        g.setColor(player.hasShield() ? Color.CYAN : color);
        int px = (int) player.getX();
        int py = (int) player.getY();
        // Draw ship shape
        int[] xPoints = {px + player.getWidth() / 2, px, px + player.getWidth()};
        int[] yPoints = {py, py + player.getHeight(), py + player.getHeight()};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    private void renderAliens(Graphics2D g, GameEngine engine) {
        for (Alien alien : engine.getFormation().getAliens()) {
            if (!alien.isAlive()) continue;
            Color color = ALIEN_COLORS[alien.getType().ordinal()];
            g.setColor(color);
            g.fillRect((int) alien.getX(), (int) alien.getY(), alien.getWidth(), alien.getHeight());
            // Eyes
            g.setColor(Color.BLACK);
            g.fillRect((int) alien.getX() + 6, (int) alien.getY() + 6, 4, 4);
            g.fillRect((int) alien.getX() + 20, (int) alien.getY() + 6, 4, 4);
        }
    }

    private void renderBullets(Graphics2D g, GameEngine engine) {
        for (Bullet bullet : engine.getBullets()) {
            if (!bullet.isAlive()) continue;
            g.setColor(bullet.isPlayerBullet() ? Color.YELLOW : Color.RED);
            g.fillRect((int) bullet.getX(), (int) bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
    }

    private void renderShields(Graphics2D g, GameEngine engine) {
        for (Shield shield : engine.getShields()) {
            if (!shield.isAlive()) continue;
            int alpha = (int) (shield.getHealthPercent() * 255);
            g.setColor(new Color(0, 255, 0, alpha));
            g.fillRect((int) shield.getX(), (int) shield.getY(), shield.getWidth(), shield.getHeight());
        }
    }

    private void renderPowerUps(Graphics2D g, GameEngine engine) {
        for (PowerUp powerUp : engine.getPowerUps()) {
            if (!powerUp.isAlive()) continue;
            switch (powerUp.getType()) {
                case SHIELD -> g.setColor(Color.CYAN);
                case RAPID_FIRE -> g.setColor(Color.YELLOW);
                case MULTI_SHOT -> g.setColor(Color.MAGENTA);
                case EXTRA_LIFE -> g.setColor(Color.RED);
            }
            g.fillOval((int) powerUp.getX(), (int) powerUp.getY(), powerUp.getWidth(), powerUp.getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 10));
            String label = powerUp.getType().name().substring(0, 1);
            g.drawString(label, (int) powerUp.getX() + 6, (int) powerUp.getY() + 15);
        }
    }

    private void renderParticles(Graphics2D g, GameEngine engine) {
        for (Particle p : engine.getParticleSystem().getParticles()) {
            float alpha = p.getAlpha();
            g.setColor(new Color(p.getR() / 255f, p.getG() / 255f, p.getB() / 255f, alpha));
            g.fillRect((int) p.getX(), (int) p.getY(), 3, 3);
        }
    }

    private void renderHUD(Graphics2D g, GameEngine engine, int width) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Score: " + engine.getScoreManager().getScore(), 10, 25);
        g.drawString("Level: " + engine.getLevelManager().getCurrentLevel(), width / 2 - 30, 25);

        String lives = "Lives: " + engine.getPlayer().getLives();
        if (engine.getPlayer2() != null) {
            lives += "  P2: " + engine.getPlayer2().getLives();
        }
        g.drawString(lives, width - 200, 25);
    }

    private void renderPaused(Graphics2D g, int width, int height) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        String text = "PAUSED";
        g.drawString(text, (width - fm.stringWidth(text)) / 2, height / 2);
    }

    private void renderGameOver(Graphics2D g, GameEngine engine, int width, int height) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String text = "GAME OVER";
        g.drawString(text, (width - fm.stringWidth(text)) / 2, height / 2 - 40);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 24));
        fm = g.getFontMetrics();
        String score = "Score: " + engine.getScoreManager().getScore();
        g.drawString(score, (width - fm.stringWidth(score)) / 2, height / 2 + 20);
        String prompt = "Press ENTER to continue";
        g.drawString(prompt, (width - fm.stringWidth(prompt)) / 2, height / 2 + 60);
    }
}
