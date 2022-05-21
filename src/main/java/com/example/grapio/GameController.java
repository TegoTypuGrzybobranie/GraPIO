package com.example.grapio;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class GameController {
    @FXML
    private ImageView f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
            f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
            f21, f22, f23, f24, f25, f26, f27, f28, f29, f30,
            f31, f32, f33, f34, f35, f36, f37, f38, f39, f40,
            f41, f42, f43, f44, f45, f46, f47, f48, f49, f50,
            f51, f52, f53, f54, f55, f56;

    @FXML
    private ImageView f0p0, f0p1, f0p2, f0p3, //start
                f57p0, f57p1, f57p2, f57p3; //meta


    private Board board;

    @FXML
    Label diceLabel;

    @FXML
    public void throwDice() {
        int roll = board.dice.giveRandom(1, 6);
        diceLabel.setText(((Integer)roll).toString());

        board.tryMove(roll);
        board.nextPlayer();
    }

    public void initializeBoard(int maxPlayers, String[] playerName) {
        List<ImageView> fieldsImg = new ArrayList<>();
        Collections.addAll(fieldsImg, f0p0, f0p1, f0p2, f0p3, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
                f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
                f21, f22, f23, f24, f25, f26, f27, f28, f29, f30,
                f31, f32, f33, f34, f35, f36, f37, f38, f39, f40,
                f41, f42, f43, f44, f45, f46, f47, f48, f49, f50,
                f51, f52, f53, f54, f55, f56, f57p0, f57p1, f57p2, f57p3);

        List<PlayerClass> players = new ArrayList<>();
        for(int i = 0; i < maxPlayers; i++)
            players.add(new PlayerClass(playerName[i]));

        board = new Board(fieldsImg, maxPlayers, players);

        switch (maxPlayers) {
            //Sets star field images
            case 4:
                f0p3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/3.png"))));
            case 3:
                f0p2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/2.png"))));
            case 2:
                f0p0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/0.png"))));
                f0p1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/1.png"))));
        }
    }
}