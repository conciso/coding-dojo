package spaceinvaders.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {
    @TempDir
    Path tempDir;
    private String filePath;
    private ScoreManager manager;

    @BeforeEach
    void setUp() {
        filePath = tempDir.resolve("test_highscores.json").toString();
        manager = new ScoreManager(filePath);
    }

    @Test
    void initialScore_isZero() {
        assertEquals(0, manager.getScore());
    }

    @Test
    void addScore_accumulatesPoints() {
        manager.addScore(100);
        manager.addScore(50);
        assertEquals(150, manager.getScore());
    }

    @Test
    void resetScore_clearsScore() {
        manager.addScore(100);
        manager.resetScore();
        assertEquals(0, manager.getScore());
    }

    @Test
    void isHighScore_trueWhenEmpty() {
        manager.addScore(100);
        assertTrue(manager.isHighScore());
    }

    @Test
    void addHighScore_savesToFile() {
        manager.addScore(500);
        manager.addHighScore("TestPlayer", 3);

        File file = new File(filePath);
        assertTrue(file.exists());
    }

    @Test
    void getHighScores_returnsList() {
        manager.addScore(500);
        manager.addHighScore("Player1", 3);
        assertFalse(manager.getHighScores().isEmpty());
    }

    @Test
    void highScores_sortedByScore() {
        manager.addScore(100);
        manager.addHighScore("Low", 1);
        manager.resetScore();
        manager.addScore(500);
        manager.addHighScore("High", 2);

        var scores = manager.getHighScores();
        assertEquals(500, scores.get(0).score);
        assertEquals(100, scores.get(1).score);
    }

    @Test
    void highScores_maxTen() {
        for (int i = 0; i < 15; i++) {
            manager.resetScore();
            manager.addScore(i * 10);
            manager.addHighScore("P" + i, 1);
        }
        assertTrue(manager.getHighScores().size() <= 10);
    }

    @Test
    void isHighScore_falseWhenListFullAndScoreLow() {
        for (int i = 0; i < 10; i++) {
            manager.resetScore();
            manager.addScore(1000 + i * 100);
            manager.addHighScore("P" + i, 1);
        }
        manager.resetScore();
        manager.addScore(5);
        assertFalse(manager.isHighScore());
    }

    @Test
    void persistence_loadsFromFile() {
        manager.addScore(777);
        manager.addHighScore("SaveTest", 5);

        ScoreManager loaded = new ScoreManager(filePath);
        var scores = loaded.getHighScores();
        assertFalse(scores.isEmpty());
        assertEquals(777, scores.get(0).score);
        assertEquals("SaveTest", scores.get(0).name);
        assertEquals(5, scores.get(0).level);
    }

    @Test
    void loadHighScores_handlesCorruptFile() throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("not valid json {{{");
        }
        assertDoesNotThrow(() -> new ScoreManager(filePath));
    }

    @Test
    void loadHighScores_handlesNonExistentFile() {
        ScoreManager m = new ScoreManager(tempDir.resolve("nonexistent.json").toString());
        assertTrue(m.getHighScores().isEmpty());
    }

    @Test
    void highScoreEntry_defaultConstructor() {
        ScoreManager.HighScoreEntry entry = new ScoreManager.HighScoreEntry();
        assertNull(entry.name);
        assertEquals(0, entry.score);
    }

    @Test
    void highScoreEntry_parameterizedConstructor() {
        ScoreManager.HighScoreEntry entry = new ScoreManager.HighScoreEntry("Test", 100, 3);
        assertEquals("Test", entry.name);
        assertEquals(100, entry.score);
        assertEquals(3, entry.level);
    }

    @Test
    void loadHighScores_handlesNullJson() throws IOException {
        // "null" is valid JSON that parses to null
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("null");
        }
        ScoreManager m = new ScoreManager(filePath);
        assertTrue(m.getHighScores().isEmpty());
    }

    @Test
    void addHighScore_doesNotTrimWhenUnderMax() {
        manager.addScore(100);
        manager.addHighScore("P1", 1);
        assertEquals(1, manager.getHighScores().size());
    }
}
