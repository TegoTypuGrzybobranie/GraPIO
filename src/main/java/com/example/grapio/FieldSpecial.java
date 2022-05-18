package com.example.grapio;

public class FieldSpecial extends Field{
    enum Effects {
        N_MOVE, DICE_MOVE, SKIP_TURN, MOVE_SPECIFIC;
    }

    private final Effects[] effs = Effects.values();
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

    public Effects getEffect(int effect_num){
        return effs[effect_num];
    }
}
