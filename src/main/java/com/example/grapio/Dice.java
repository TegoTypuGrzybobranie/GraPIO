package com.example.grapio;
import java.util.Random;

public class Dice {
    Random random;

    public Dice() {
        this.random = new Random();
    }

    int giveRandom(int from, int to) {
        return random.nextInt(from, to + 1);
    }
}
