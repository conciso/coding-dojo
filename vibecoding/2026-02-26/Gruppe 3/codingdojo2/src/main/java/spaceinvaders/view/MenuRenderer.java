package spaceinvaders.view;

import spaceinvaders.logic.GameEngine;
import spaceinvaders.logic.ScoreManager;

import java.awt.*;
import java.util.List;

public class MenuRenderer {
    private static final String[] MENU_ITEMS = {"Start Game", "Multiplayer", "Highscores", "Quit"};

    public void render(Graphics2D g, GameEngine engine, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        String title = "SPACE INVADERS";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, (width - fm.stringWidth(title)) / 2, 120);

        g.setFont(new Font("Monospaced", Font.PLAIN, 24));
        fm = g.getFontMetrics();
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            if (i == engine.getMenuSelection()) {
                g.setColor(Color.YELLOW);
                String item = "> " + MENU_ITEMS[i] + " <";
                g.drawString(item, (width - fm.stringWidth(item)) / 2, 250 + i * 50);
            } else {
                g.setColor(Color.WHITE);
                g.drawString(MENU_ITEMS[i], (width - fm.stringWidth(MENU_ITEMS[i])) / 2, 250 + i * 50);
            }
        }

        // Highscores
        List<ScoreManager.HighScoreEntry> scores = engine.getScoreManager().getHighScores();
        if (!scores.isEmpty()) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("Monospaced", Font.BOLD, 18));
            g.drawString("HIGH SCORES", width / 2 - 70, 470);
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            for (int i = 0; i < Math.min(5, scores.size()); i++) {
                ScoreManager.HighScoreEntry entry = scores.get(i);
                String line = String.format("%d. %s - %d (Lvl %d)", i + 1, entry.name, entry.score, entry.level);
                g.drawString(line, width / 2 - 100, 500 + i * 20);
            }
        }
    }
}
