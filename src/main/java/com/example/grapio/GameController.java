package com.example.grapio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {

    @FXML
    private ImageView f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
            f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
            f21, f22, f23, f24, f25, f26, f27, f28, f29, f30,
            f31, f32, f33, f34, f35, f36, f37, f38, f39, f40,
            f41, f42, f43, f44, f45, f46, f47, f48, f49, f50,
            f51, f52, f53, f54, f55, f56;

    private final List<ImageView> fields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collections.addAll(fields, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
                f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
                f21, f22, f23, f24, f25, f26, f27, f28, f29, f30,
                f31, f32, f33, f34, f35, f36, f37, f38, f39, f40,
                f41, f42, f43, f44, f45, f46, f47, f48, f49, f50,
                f51, f52, f53, f54, f55, f56);

    }

    @FXML
    Label diceLabel;
    Dice dice = new Dice();

    @FXML
    public void throwDice() {
        int roll = dice.giveRandom(1, 6);
        diceLabel.setText(((Integer)roll).toString());
        fields.get(roll).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/players/3.png"))));
    }


}