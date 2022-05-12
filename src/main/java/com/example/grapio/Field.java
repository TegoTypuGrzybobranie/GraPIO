package com.example.grapio;

import javafx.scene.paint.Color;

public abstract class Field {
    // True -> special
    private boolean isSpecial;
    private PlayerClass playerOn;

    public boolean isSpecial() {
        return isSpecial;
    }

    public abstract void setSpecial(boolean special);

    public abstract PlayerClass getPlayerOn();

    public abstract void setPlayerOn(PlayerClass playerOn);
}
