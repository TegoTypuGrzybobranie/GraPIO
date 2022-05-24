package com.example.grapio;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;

enum Effect {
    BLOCKS, MOVES
}

class SpecialField {
    Effect effect;
    // Number of rounds it's blocked or number of fields it moves with possibility of being negative
    private int value;
    private int index;

    SpecialField() {}

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return this.index;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getValue() {
        return value;
    }
}

public class Board {
    private static final int FIELD_START_INDEX = 3;
    public static final int FIELD_META_INDEX = 57;
    public static final int SPECIAL_FIELDS_NUMBER = 40;

    private static SpecialField[] specialFields;

    public static Effect getEffect(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return specialFields[i].getEffect();

        return null;
    }
    public static int getValue(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return specialFields[i].getValue();
        System.out.println("Dupa");
        return 0;
    }

    private void setSpecialFields() {
        Random random = new Random();
        specialFields = new SpecialField[SPECIAL_FIELDS_NUMBER];
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++) {

            int x = random.nextInt(2);
            specialFields[i] = new SpecialField();
            if (x == 0) {
                specialFields[i].setEffect(Effect.BLOCKS);
                specialFields[i].setValue(random.nextInt(2) + 1);
            }
            else {
                specialFields[i].setEffect(Effect.MOVES);
                specialFields[i].setValue(random.nextInt(12) - 6);
            }
            specialFields[i].setIndex(random.nextInt(FIELD_START_INDEX + 1, FIELD_META_INDEX - 1));
        }
        System.out.println();
    }

    public static boolean isSpecial(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return true;

        return false;
    }

     public Dice dice = new Dice();



    private final List<ImageView> fieldsImg;
    private final List<PlayerClass> players;

    private final int maxPlayers;
    private int whichPlayer = 0;

    public Board(List<ImageView> fieldsImg, int maxPlayers, List<PlayerClass> players) {
        setSpecialFields();
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
        if (position == FIELD_META_INDEX) {
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
        if (positionToMove == 0)
            return;

        for (int i = 0; i < maxPlayers; i++) {
            if(i == whichPlayer)
                continue;

            if (positionToMove == players.get(i).getPosition() && positionToMove != FIELD_META_INDEX) {
                tryMove(move-1);
                return;
            }
        }

        moveCurrentPlayer(move);
    }
}
