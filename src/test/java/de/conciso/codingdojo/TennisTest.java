package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class TennisTest {


    public static Stream<Arguments>  getAllScores() {
        return Stream.of(
            arguments(0, 0, "Love-All"),
            arguments(1, 1, "Fifteen-All"),
            arguments(2, 2, "Thirty-All"),
            arguments(3, 3, "Deuce"),
            arguments(4, 4, "Deuce"),

            arguments(1, 0, "Fifteen-Love"),
            arguments(0, 1, "Love-Fifteen"),
            arguments(2, 0, "Thirty-Love"),
            arguments(0, 2, "Love-Thirty"),
            arguments(3, 0, "Forty-Love"),
            arguments(0, 3, "Love-Forty"),
            arguments(4, 0, "Win for player1"),
            arguments(0, 4, "Win for player2"),

            arguments(2, 1, "Thirty-Fifteen"),
            arguments(1, 2, "Fifteen-Thirty"),
            arguments(3, 1, "Forty-Fifteen"),
            arguments(1, 3, "Fifteen-Forty"),
            arguments(4, 1, "Win for player1"),
            arguments(1, 4, "Win for player2"),

            arguments(3, 2, "Forty-Thirty"),
            arguments(2, 3, "Thirty-Forty"),
            arguments(4, 2, "Win for player1"),
            arguments(2, 4, "Win for player2"),

            arguments(4, 3, "Advantage player1"),
            arguments(3, 4, "Advantage player2"),
            arguments(5, 4, "Advantage player1"),
            arguments(4, 5, "Advantage player2"),
            arguments(15, 14, "Advantage player1"),
            arguments(14, 15, "Advantage player2"),

            arguments(6, 4, "Win for player1"),
            arguments(4, 6, "Win for player2"),
            arguments(16, 14, "Win for player1"),
            arguments(14, 16, "Win for player2")
        );
    }

    public void checkAllScores(TennisGame game, int player1Score, int player2Score,
        String expectedScore) {
        int highestScore = Math.max(player1Score, player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < player1Score) {
                game.wonPoint("player1");
            }
            if (i < player2Score) {
                game.wonPoint("player2");
            }
        }
        assertEquals(expectedScore, game.getScore());
    }

    @ParameterizedTest
    @MethodSource("getAllScores")
    public void checkAllScoresTennisGame1(int player1Score, int player2Score,
        String expectedScore) {
        TennisGame1 game = new TennisGame1("player1", "player2");
        checkAllScores(game, player1Score, player2Score, expectedScore);
    }

    @Test
    public void checkInvalidPlayer() {
        TennisGame1 game = new TennisGame1("player1", "player2");
        assertThrows(IllegalArgumentException.class, ()->game.wonPoint("player3"));
    }

    @Test
    public void checkNullPlayer() {
        TennisGame1 game = new TennisGame1("player1", "player2");
        assertThrows(IllegalArgumentException.class, ()->game.wonPoint(null));
    }
}