package com.example.grapio;

public class PlayerClass {
    // blocked for $blocked turns
    private short blocked = 0;
    private int position = 0;
    private boolean finished = false;
    private final String nickName;
    // How many rolls were made
    private int diceRollCount = 0;

    PlayerClass(String nickName) {
        this.nickName = nickName;
    }

    public boolean movePlayer(int move) {
        if (move == 0 || finished) {
            return false;
        }

        if (blocked > 0) {
            blocked--;
            return false;
        }

        diceRollCount++;
        position += move;
        if (position >= Board.FIELD_META_INDEX) {
            position = Board.FIELD_META_INDEX;
            finished = true;
        }

        return true;
    }

    public boolean isFinished() {
        return finished;
    }

    public short getBlocked() {
        return blocked;
    }

    public void decreaseBlocked() {
        blocked--;
        if(blocked < 0) {
            blocked = 0;
        }
    }

    public void setBlocked(short blocked) {
        this.blocked = blocked;
    }

    public String getNickName() {
        return nickName;
    }

    public int getPosition() {
        return position;
    }

    public int getDiceRollCount() {
        return diceRollCount;
    }

}
