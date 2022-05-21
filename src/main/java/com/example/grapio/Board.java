package com.example.grapio;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Objects;


public class Board {
    private static final int FIELD_START_INDEX = 3;
    public static final int FIELD_META_INDEX = 57;

    public Dice dice = new Dice();

    private final List<ImageView> fieldsImg;
    private final List<PlayerClass> players;

    private final int maxPlayers;
    private int whichPlayer = 0;

    public Board(List<ImageView> fieldsImg, int maxPlayers, List<PlayerClass> players) {
        this.fieldsImg = fieldsImg;
        this.maxPlayers = maxPlayers;
        this.players = players;
    }

    public void nextPlayer() {
        whichPlayer = whichPlayer + 1 < maxPlayers ? whichPlayer + 1 : 0;
    }

    private void updateLastFieldImage() {
        int position = players.get(whichPlayer).getPosition();
        if(position == 0) {
            //START IMAGES
            fieldsImg.get(position + whichPlayer).setImage(null);
        }
        else {
            //STANDARD IMAGES
            fieldsImg.get(position + FIELD_START_INDEX).setImage(null);
        }
    }

    private void updateCurrentFieldImage() {
        int position = players.get(whichPlayer).getPosition();
        if(position == FIELD_META_INDEX) {
            //META IMAGES
            fieldsImg.get(position + FIELD_START_INDEX + whichPlayer).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" +((Integer)whichPlayer).toString()+".png"))));
        }
        else {
            //STANDARD IMAGES
            fieldsImg.get(position + FIELD_START_INDEX).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" +((Integer)whichPlayer).toString()+".png"))));
        }
    }

    private void moveCurrentPlayer(int move) {
        updateLastFieldImage();
        players.get(whichPlayer).movePlayer(move);
        updateCurrentFieldImage();
    }

    public void tryMove(int move) {
        int positionToMove = players.get(whichPlayer).getPosition() + move;
        if(positionToMove == 0)
            return;

        for(int i = 0; i < maxPlayers; i++) {
            if(i == whichPlayer)
                continue;

            if(positionToMove == players.get(i).getPosition() && positionToMove != FIELD_META_INDEX) {
                tryMove(move-1);
                return;
            }
        }

        moveCurrentPlayer(move);
    }
}
