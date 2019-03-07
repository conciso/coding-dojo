package de.conciso.codingdojo;

public class TennisGame1 implements TennisGame {

    private int firstPlayerScoreValue = 0;
    private int secondPlayerScoreValue = 0;
    private String firstPlayerName;
    private String secondPlayerName;


    public TennisGame1(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
    }

    public void wonPoint(String playerName) {
        if (this.firstPlayerName.equals(playerName)) {
            firstPlayerScoreValue++;
        } else if (this.secondPlayerName.equals(playerName)) {
            secondPlayerScoreValue++;
        } else {
            throw new IllegalArgumentException("Unknown player");
        }
    }


    public String getScore() {
        if (isTie()) {
            return getScoreForTie();
        } else if (isAdvantage()) {
            return getScoreForAdvantage();
        } else if (isWin()) {
            return getScoreForWin();
        } else {
            return getScoreForOther();
        }
    }

    private String getScoreForWin() {
        return "Win for " + getLeadingPlayer();
    }

    private String getScoreForAdvantage() {
        return "Advantage " + getLeadingPlayer();
    }

    private String getScoreForOther() {
        return getScoreForScoreValue(firstPlayerScoreValue) + "-" + getScoreForScoreValue(
            secondPlayerScoreValue);
    }

    private String getLeadingPlayer() {
        return firstPlayerScoreValue > secondPlayerScoreValue ? firstPlayerName : secondPlayerName;
    }

    private boolean isAdvantage() {
        return isEndGame()
            && getAbsoluteScoreDifference() == 1;
    }

    private int getAbsoluteScoreDifference() {
        return Math.abs(firstPlayerScoreValue - secondPlayerScoreValue);
    }

    private boolean isEndGame() {
        return firstPlayerScoreValue >= 4 || secondPlayerScoreValue >= 4;
    }

    private boolean isWin() {
        return (isEndGame())
            && getAbsoluteScoreDifference() >= 2;
    }

    private boolean isTie() {
        return firstPlayerScoreValue == secondPlayerScoreValue;
    }

    private String getScoreForTie() {
        switch (firstPlayerScoreValue) {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            default:
                return "Deuce";
        }
    }

    private String getScoreForScoreValue(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return "";
        }
    }
}