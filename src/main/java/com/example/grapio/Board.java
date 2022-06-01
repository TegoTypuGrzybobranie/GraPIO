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
            fieldsImg.get(position + FIELD_START_INDEX + whichPlayer).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" + ((Integer) whichPlayer).toString() + ".png"))));
        } else {
            //STANDARD IMAGES
            fieldsImg.get(position + FIELD_START_INDEX).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" + ((Integer) whichPlayer).toString() + ".png"))));
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
        int[] indexes = {6, 13, 19, 21, 26, 30, 33, 37, 44, 49, 51, 54,};
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++) {

            specialFields[i] = new SpecialField();
            if (i % 2 == 0) {
                specialFields[i].setEffect(Effect.BLOCKS);
                specialFields[i].setValue(random.nextInt(2) + 1);
            } else {
                specialFields[i].setEffect(Effect.MOVES);
                specialFields[i].setValue(random.nextInt(12) - 6);
            }
            specialFields[i].setIndex(indexes[i]);
        }
    }

    public static boolean isSpecial(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return true;

        return false;
    }

}
