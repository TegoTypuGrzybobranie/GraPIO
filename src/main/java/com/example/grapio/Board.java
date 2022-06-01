package com.example.grapio;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    public static final int SPECIAL_FIELDS_NUMBER = 5;

    public static final int MAX_BLOCKED = 2;
    public static final int MAX_MOVE_DISTANCE = 6;
    // How many effects do we implement
    public static final int EFFECT_NUMBER = 2;

    public Board(List<ImageView> fieldsImg, int maxPlayers, List<PlayerClass> players) {
        setSpecialFields();
        this.fieldsImg = fieldsImg;
        this.maxPlayers = maxPlayers;
        this.players = players;

        rank = new int[maxPlayers];
    }

    public Dice dice = new Dice();
    private final List<ImageView> fieldsImg;
    private final List<PlayerClass> players;
    private static SpecialField[] specialFields;
    private final int maxPlayers;

    private final int[] rank;
    private int placeIndex = 0;

    private int whichPlayer = 0;

    public int getWhichPlayer() {
        return whichPlayer;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int[] getRank() {
        return rank;
    }

    public PlayerClass getPlayers(int id) {
        if (id < 0 && id >= maxPlayers) return null;
        return players.get(id);
    }

    public boolean nextPlayer() {
        for (int i = 0; i < maxPlayers; i++) {
            whichPlayer = whichPlayer + 1 < maxPlayers ? whichPlayer + 1 : 0;
            if (!players.get(whichPlayer).isFinished()) {
                return true;
            }
        }
        return false;
    }

    private void updateLastFieldImage() {
        int position = players.get(whichPlayer).getPosition();
        if (position == 0) {
            //START IMAGES
            fieldsImg.get(position + whichPlayer).setImage(null);
        } else {
            //STANDARD IMAGES
            fieldsImg.get(position + FIELD_START_INDEX).setImage(null);
        }
    }

    private void updateCurrentFieldImage() {
        int position = players.get(whichPlayer).getPosition();
        if (position == FIELD_META_INDEX) {
            //META IMAGES
            fieldsImg.get(position + FIELD_START_INDEX + whichPlayer).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" + whichPlayer + ".png"))));
        } else {
            //STANDARD IMAGES
            fieldsImg.get(position + FIELD_START_INDEX).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" + whichPlayer + ".png"))));
        }
    }

    private void moveCurrentPlayer(int move) {
        updateLastFieldImage();

        players.get(whichPlayer).movePlayer(move);
        if (players.get(whichPlayer).isFinished()) {
            rank[placeIndex++] = whichPlayer;
        }

        updateCurrentFieldImage();
    }

    public void tryMove(int move) {
        int positionToMove = players.get(whichPlayer).getPosition() + move;
        if (positionToMove == 0)
            return;

        for (int i = 0; i < maxPlayers; i++) {
            if (i == whichPlayer)
                continue;

            if (positionToMove == players.get(i).getPosition() && positionToMove != FIELD_META_INDEX) {
                tryMove(move - 1);
                return;
            }
        }

        moveCurrentPlayer(move);
    }

    public static Effect getEffect(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return specialFields[i].getEffect();
        return null;
    }

    public static int getValue(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++) {
            if (specialFields[i].getIndex() == index) {
                return specialFields[i].getValue();
            }
        }
        return 0;
    }

    private void setSpecialFields() {
        Random random = new Random();
        specialFields = new SpecialField[SPECIAL_FIELDS_NUMBER];

        specialFields[0].setIndex(5);
        specialFields[0].setValue(2);
        specialFields[0].setEffect(Effect.BLOCKS);

        specialFields[1].setIndex(11);
        specialFields[1].setValue(-6);
        specialFields[1].setEffect(Effect.MOVES);

        specialFields[2].setIndex(14);
        specialFields[2].setValue(5);
        specialFields[2].setEffect(Effect.MOVES);

        specialFields[3].setIndex(19);
        specialFields[3].setValue(2);
        specialFields[3].setEffect(Effect.BLOCKS);

        specialFields[4].setIndex(25);
        specialFields[4].setValue(-6);
        specialFields[4].setEffect(Effect.MOVES);

        specialFields[5].setIndex(29);
        specialFields[5].setValue(1);
        specialFields[5].setEffect(Effect.BLOCKS);

        specialFields[6].setIndex(31);
        specialFields[6].setValue(1);
        specialFields[6].setEffect(Effect.BLOCKS);

        specialFields[7].setIndex(34);
        specialFields[7].setValue(-4);
        specialFields[7].setEffect(Effect.MOVES);

        specialFields[8].setIndex(39);
        specialFields[8].setValue(1);
        specialFields[8].setEffect(Effect.BLOCKS);

        specialFields[9].setIndex(45);
        specialFields[9].setValue(1);
        specialFields[9].setEffect(Effect.MOVES);

        specialFields[10].setIndex(47);
        specialFields[10].setValue(3);
        specialFields[10].setEffect(Effect.MOVES);

        specialFields[11].setIndex(50);
        specialFields[11].setValue(2);
        specialFields[11].setEffect(Effect.BLOCKS);

        specialFields[12].setIndex(58);
        specialFields[12].setValue(-5);
        specialFields[12].setEffect(Effect.MOVES);

    }

    public static boolean isSpecial(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return true;

        return false;
    }

}
