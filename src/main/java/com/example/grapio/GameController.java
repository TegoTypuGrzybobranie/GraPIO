package com.example.grapio;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class GameController {
    @FXML
    private ImageView f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
            f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
            f21, f22, f23, f24, f25, f26, f27, f28, f29, f30,
            f31, f32, f33, f34, f35, f36, f37, f38, f39, f40,
            f41, f42, f43, f44, f45, f46, f47, f48, f49, f50,
            f51, f52, f53, f54, f55, f56, f57;

    private Board board;

    @FXML
    Label diceLabel;

    @FXML
    public void throwDice() {
        int roll = board.dice.giveRandom(1, 6);
        diceLabel.setText(((Integer)roll).toString());
        board.fieldsImg.get(roll).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/1.png"))));
    }

    public void initializeBoard(int maxPlayers, String[] playerName) {
        List<ImageView> fieldsImg = new ArrayList<>();
        Collections.addAll(fieldsImg, f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
                f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
                f21, f22, f23, f24, f25, f26, f27, f28, f29, f30,
                f31, f32, f33, f34, f35, f36, f37, f38, f39, f40,
                f41, f42, f43, f44, f45, f46, f47, f48, f49, f50,
                f51, f52, f53, f54, f55, f56, f57);

        List<PlayerClass> players = new ArrayList<>();
        for(int i = 0; i < maxPlayers; i++)
            players.add(new PlayerClass(playerName[i]));

        board = new Board(fieldsImg, maxPlayers, players);

        if(maxPlayers == 2) board.fieldsImg.get(0).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/12.png"))));
        else if(maxPlayers == 3) board.fieldsImg.get(0).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/123.png"))));
        else board.fieldsImg.get(0).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/1234.png"))));
    }
}