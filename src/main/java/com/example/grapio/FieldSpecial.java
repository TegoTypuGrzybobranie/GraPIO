package com.example.grapio;

public class FieldSpecial extends Field{
    @Override
    public void setSpecial(boolean special) {
        special = true;
    }

    @Override
    public PlayerClass getPlayerOn() {
        return playerOn;
    }

    @Override
    public void setPlayerOn(PlayerClass playerOn) {

    }
}
