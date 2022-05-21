package com.example.grapio;

import javafx.scene.image.ImageView;
import java.util.List;

public class Board {
    public static final int MAX_FILED_INDEX = 57;
    public Dice dice = new Dice();

    //xd
    public final List<ImageView> fieldsImg;
    private final List<PlayerClass> players;

    private int maxPlayers;
    private int whichPlayer = 0;

    public Board(List<ImageView> fieldsImg, int maxPlayers, List<PlayerClass> players) {
        this.fieldsImg = fieldsImg;
        this.maxPlayers = maxPlayers;
        this.players = players;

        System.out.println(maxPlayers);
        for(int i = 0; i < maxPlayers; i++) {
            System.out.println(players.get(i).getNickName());
        }

    }
}
