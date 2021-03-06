package com.example.grapio;


import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
import java.util.Random;

enum Effect {
    BLOCKS, MOVES, ADDIT_THROW
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
    public static final int SPECIAL_FIELDS_NUMBER = 9;

    public Board(List<ImageView> fieldsImg, int maxPlayers, List<PlayerClass> players, Label blockLabel) {
        setSpecialFields();
        this.fieldsImg = fieldsImg;
        this.maxPlayers = maxPlayers;
        this.players = players;
        this.blockLabel = blockLabel;

        rank = new int[maxPlayers];
    }

    public Dice dice = new Dice();
    private final List<ImageView> fieldsImg;
    private final List<PlayerClass> players;
    private SpecialField[] specialFields;
    private final int maxPlayers;

    @FXML
    private Label blockLabel;
    private final PauseTransition pause = new PauseTransition(Duration.seconds(2));


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
                if (players.get(whichPlayer).getBlocked() > 0) {
                    players.get(whichPlayer).decreaseBlocked();
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    public void revertPlayer() {
        whichPlayer = whichPlayer > 0 ? whichPlayer - 1 : maxPlayers - 1;
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
        boolean blocked = false;
        boolean additionalThrow = false;
        int additionalMove = 0;

        if (isSpecial(positionToMove)) {
            if (getEffect(positionToMove) == Effect.BLOCKS) {
                blocked = true;
            } else if (getEffect(positionToMove) == Effect.MOVES) {
                if (getValue(positionToMove) == 1 || getValue(positionToMove) == -1)
                    blockLabel.setText("Przesuwasz si?? o " + getValue(positionToMove) + " pole");
                else if (getValue(positionToMove) == 2 || getValue(positionToMove) == 3 || getValue(positionToMove) == 4 || getValue(positionToMove) == -2 || getValue(positionToMove) == -3 || getValue(positionToMove) == -4)
                    blockLabel.setText("Przesuwasz si?? o " + getValue(positionToMove) + " pola");
                else blockLabel.setText("Przesuwasz si?? o " + getValue(positionToMove) + " p??l");
                blockLabel.setVisible(true);
                pause.setOnFinished(e -> blockLabel.setVisible(false));
                pause.play();
                additionalMove = getValue(positionToMove);
            }
            else if(getEffect(positionToMove) == Effect.ADDIT_THROW) {
                additionalThrow = true;
            }
        }

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

        if (blocked) {
            players.get(whichPlayer).setBlocked((short) getValue(positionToMove));
            if (players.get(whichPlayer).getBlocked() == 1)
                blockLabel.setText("Czekasz: 1 tur??");
            else blockLabel.setText("Czekasz: 2 tury");
            blockLabel.setVisible(true);
            pause.setOnFinished(e -> blockLabel.setVisible(false));
            pause.play();
        }

        if (additionalMove != 0) {
            tryMove(additionalMove);
            return;
        }

        if (additionalThrow){
            blockLabel.setText("Dodatkowy rzut!");
            blockLabel.setVisible(true);
            pause.setOnFinished(e -> blockLabel.setVisible(false));
            pause.play();
            revertPlayer();
        }
    }

    private Effect getEffect(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++)
            if (specialFields[i].getIndex() == index)
                return specialFields[i].getEffect();
        return null;
    }

    private int getValue(int index) {
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
        int[] indexes = {6, 9, 13, 18, 20, 27, 31, 36, 50};
        int[] movesFor = {-4, -3, -2, -1, 1, 2, 3, 4, 5, 6};
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++) {

            specialFields[i] = new SpecialField();
            if(indexes[i] == 9 || indexes[i] == 18 || indexes[i] == 31) {
                specialFields[i].setEffect(Effect.ADDIT_THROW);
                specialFields[i].setValue(0);
            } else if (indexes[i] % 2 != 0) {
                specialFields[i].setEffect(Effect.BLOCKS);
                specialFields[i].setValue(random.nextInt(2) + 1);
            } else {
                specialFields[i].setEffect(Effect.MOVES);
                specialFields[i].setValue(movesFor[random.nextInt(10)]);
            }
            specialFields[i].setIndex(indexes[i]);
        }
    }

    private boolean isSpecial(int index) {
        for (int i = 0; i < SPECIAL_FIELDS_NUMBER; i++) {
            if (specialFields[i].getIndex() == index) {
                return true;
            }
        }

        return false;
    }

}
