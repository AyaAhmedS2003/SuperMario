package models;


public class CurGame {

    public boolean paused;
    public long Timer;
    public String firstPlayerName;
    public String secondPlayerName;
    public boolean isMultipalyer;
    private int currentPlayer;
    private int firstPlayerLives;
    private int secondPlayerLives;
    private int secondPlayerScore;
    private int firstPlayerScore;
    public int marioIdx = 1;// mario idx is between {1, 2, 3, 4}
    public int xBullet;
    public boolean fired = false;
    public int xMario;

    public String getCurrentPlayerName() {
        if (currentPlayer == 1) {
            return firstPlayerName;
        }
        return secondPlayerName;
    }

    public int getCurrentPlayerScore() {
        if (currentPlayer == 1) {
            return firstPlayerScore;
        }
        return secondPlayerScore;
    }

    public void setCurrentPlayerScore(int score) {
        if (currentPlayer == 1) {
            firstPlayerScore = score;
        } else {
            secondPlayerScore = score;
        }
    }

    public int getCurrentPlayerLives() {
        if (currentPlayer == 1) {
            return firstPlayerLives;
        }
        return secondPlayerLives;
    }

    public void setCurrentPlayerLives(int score) {
        if (currentPlayer == 1) {
            firstPlayerLives = score;
        } else {
            secondPlayerLives = score;
        }
    }

    public void newGame() {
        paused = false;
        isMultipalyer = false;
        Timer = 0;
        firstPlayerScore = 0;
        secondPlayerScore = 0;
        firstPlayerLives = 3;
        secondPlayerLives = 3;
        xMario = 10;
    }
}
