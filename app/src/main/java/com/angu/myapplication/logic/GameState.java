package com.angu.myapplication.logic;

import java.util.Random;

public class GameState {

    public int difficulty;
    public int timeLimit;
    public String word;
    private static String[] strings = new String[]{"arc", "concrete", "self", "armory", "lamb", "restore"};

    public GameState() {
        difficulty = 0;
    }

    public static int calculateTimeLimit(int difficulty) {
        // TODO: calculate time limit from difficulty
        // for now, set time limit to 5s
        return 5000;
    }

    public static String fetchWord(int difficulty) {
        // TODO: get word based on difficulty
        // for now, select a random word from the list
        return strings[new Random().nextInt(strings.length)];
    }

    public void increaseLevel() {
        difficulty++;
        timeLimit = calculateTimeLimit(difficulty);
        word = fetchWord(difficulty);
    }

}
