package spaceinvaders;

import spaceinvaders.logic.GameEngine;
import spaceinvaders.logic.ScoreManager;
import spaceinvaders.view.GameWindow;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class SpaceInvaders {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            ScoreManager scoreManager = new ScoreManager("highscores.json");
            GameEngine engine = new GameEngine(scoreManager);
            GameWindow window = new GameWindow(engine);
            window.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    window.getGamePanel().stop();
                    latch.countDown();
                }
            });
            window.setVisible(true);
            window.getGamePanel().start();
        });
        latch.await();
    }
}
