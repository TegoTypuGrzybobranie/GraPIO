package com.example.grapio;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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

    @FXML
    private Button btnThrow, btnEnd;

    private Board board;
    private final PauseTransition pause = new PauseTransition(Duration.seconds(1));

    @FXML
    private Label playerLabel, rankList;

    @FXML
    private ImageView playerImage, diceImage, rankImage;

    @FXML
    public void throwDice() {
        int roll = board.dice.giveRandom(1, 6);
        showDice(roll);

        board.tryMove(roll);
        if(!board.nextPlayer()) {
            endGame();
            return;
        }

        playerLabel.setText(board.getPlayers(board.getWhichPlayer()).getNickName());
        playerImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/" +((Integer)board.getWhichPlayer()).toString()+".png"))));
    }

    @FXML
    private void toMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu-view.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

        playerLabel.setText(board.getPlayers(0).getNickName());

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

    private void showDice(int num) {
        if(num < 0 || num > 6) {
            return;
        }

        diceImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/dice/dice" +((Integer)num).toString()+".png"))));
        pause.setOnFinished(e -> diceImage.setImage(null));
        pause.play();
    }

    private void endGame() {
        btnThrow.setDisable(true);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < board.getMaxPlayers(); i++) {
            res.append(i + 1).append(". ").append(board.getPlayers(board.getRank()[i]).getNickName()).append("\n");
        }

        rankImage.setVisible(true);
        rankList.setText(res.toString());
        btnEnd.setVisible(true);
    }
}
