package com.angu.myapplication.logic;

import java.util.Random;

public class GameState {

    public int difficulty;
    public double timeLimit;
    public String word;
    private static String[] strings = new String[]{"arc", "concrete", "self", "armory", "lamb", "restore"};

    public GameState() {
        difficulty = 0;
    }

    public static double calculateTimeLimit(int difficulty) {
        // TODO: calculate time limit from difficulty
        double limit = 8000 - Math.sqrt(360000 * difficulty);
        System.out.println(limit);
        return limit;
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
