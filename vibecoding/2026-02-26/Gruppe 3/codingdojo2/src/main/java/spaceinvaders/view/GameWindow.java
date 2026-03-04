package spaceinvaders.view;

import spaceinvaders.logic.GameEngine;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final GamePanel gamePanel;

    public GameWindow(GameEngine engine) {
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel(engine);
        add(gamePanel);
        pack();

        setLocationRelativeTo(null);
    }

    public GamePanel getGamePanel() { return gamePanel; }
}
