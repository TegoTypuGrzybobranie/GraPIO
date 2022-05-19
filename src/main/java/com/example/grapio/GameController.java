package com.example.grapio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameController {

    @FXML
    Label diceLabel;
    Dice dice = new Dice();

    @FXML
    public void throwDice() {
        diceLabel.setText(((Integer)dice.giveRandom(1, 6)).toString());
    }


}