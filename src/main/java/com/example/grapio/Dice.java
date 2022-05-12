package com.example.grapio;
import java.util.Random;

public class Dice {
    private int from, to;
    Random random;

    public Dice() {
        this.random = new Random();
    }

    int giveRandom() {
        return random.nextInt(from, to + 1);
    }
}
