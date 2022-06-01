package com.example.grapio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MenuController {
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        gameController.initializeBoard(maxPlayers, playerName);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toOptions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("options-view.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Parent root = loader.load();

        MenuController menuController = loader.getController();
        menuController.updateOptionsSettings((int) playerSlider.getValue(), new String[]{p1Name.getText(), p2Name.getText(), p3Name.getText(), p4Name.getText()});


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void exit() {
        System.exit(0);
    }

    @FXML
    private Slider playerSlider;
    private int maxPlayers = 4;
    private String[] playerName = {
            "Player 1", "Player 2", "Player 3", "Player 4"
    };


    @FXML
    private ImageView p3Image, p4Image;
    @FXML
    private TextField p1Name, p2Name, p3Name, p4Name;

    @FXML
    private void hideOptionsLabels() {
        switch ((int) playerSlider.getValue()) {
            case 2 -> {
                p3Image.setVisible(false);
                p3Name.setVisible(false);
                p4Image.setVisible(false);
                p4Name.setVisible(false);
            }
            case 3 -> {
                p3Image.setVisible(true);
                p3Name.setVisible(true);
                p4Image.setVisible(false);
                p4Name.setVisible(false);
            }
            case 4 -> {
                p3Image.setVisible(true);
                p3Name.setVisible(true);
                p4Image.setVisible(true);
                p4Name.setVisible(true);
            }
        }
    }

    private void updateOptionsSettings(int maxPlayers, String[] playerName) {
        this.maxPlayers = maxPlayers;
        this.playerName = playerName;
    }
}