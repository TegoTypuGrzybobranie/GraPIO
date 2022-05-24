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

    public void nMove(PlayerClass player, int n){
        player.movePlayer(n);
    }

    public void diceMove(PlayerClass player, Board board, GameController controller){
        int roll = board.dice.giveRandom(1, 6);
        controller.diceLabel.setText(((Integer)roll).toString());
        board.tryMove(roll);
    }

    public void skipTurn(PlayerClass player, short n){
        player.setBlocked(n);
    }

    public void moveSpecific(PlayerClass player, Board board, int pos){
        board.tryMove(pos-player.getPosition());
    }
}
