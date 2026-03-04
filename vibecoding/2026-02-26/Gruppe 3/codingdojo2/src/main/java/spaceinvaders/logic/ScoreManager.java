package spaceinvaders.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {
    private static final int MAX_HIGHSCORES = 10;
    private final String filePath;
    private int score;
    private List<HighScoreEntry> highScores;

    public static class HighScoreEntry {
        public String name;
        public int score;
        public int level;

        public HighScoreEntry() {}

        public HighScoreEntry(String name, int score, int level) {
            this.name = name;
            this.score = score;
            this.level = level;
        }
    }

    public ScoreManager(String filePath) {
        this.filePath = filePath;
        this.score = 0;
        this.highScores = new ArrayList<>();
        loadHighScores();
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() { return score; }
    public void resetScore() { score = 0; }

    public boolean isHighScore() {
        return highScores.size() < MAX_HIGHSCORES ||
               score > highScores.get(highScores.size() - 1).score;
    }

    public void addHighScore(String name, int level) {
        highScores.add(new HighScoreEntry(name, score, level));
        highScores.sort(Comparator.comparingInt((HighScoreEntry e) -> e.score).reversed());
        if (highScores.size() > MAX_HIGHSCORES) {
            highScores = new ArrayList<>(highScores.subList(0, MAX_HIGHSCORES));
        }
        saveHighScores();
    }

    public List<HighScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }

    void loadHighScores() {
        File file = new File(filePath);
        if (!file.exists()) return;
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<HighScoreEntry>>() {}.getType();
            List<HighScoreEntry> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                highScores = loaded;
            }
        } catch (IOException | com.google.gson.JsonSyntaxException e) {
            // Ignore, start with empty list
        }
    }

    void saveHighScores() {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(highScores, writer);
        } catch (IOException e) {
            // Ignore save errors
        }
    }
}
