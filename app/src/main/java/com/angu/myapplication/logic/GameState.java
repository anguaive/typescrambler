package com.angu.myapplication.logic;

public class GameState {

    public int level;
    public double timeLimit;
    public String word;
    private WordList wordList;

    public GameState(WordList wordList) {
        this.wordList = wordList;
    }

    private static double calculateTimeLimit(int difficulty) {
        double limit = 8000 - Math.sqrt(360000 * difficulty);
        System.out.println(limit);
        return limit;
    }

    public void increaseLevel() {
        level++;
        timeLimit = calculateTimeLimit(level);
        word = wordList.getRandomWord(level);
    }

}
