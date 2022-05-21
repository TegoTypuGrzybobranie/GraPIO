package com.example.grapio;

public class PlayerClass {
    // blocked for $blocked turns
    private short blocked;
    private String nickName;
    private int position;
    // How many rolls were made
    private int diceRollCount;

    PlayerClass(String nickName) {
        this.nickName = nickName;
    }

    public int rollTheDice() {
        if (blocked > 0) {
            blocked--;
            return 0;
        }
        else {
            // ToDo
            int ret = 0;
            return ret;
        }
    }

    public boolean movePlayer() {
        int move = this.rollTheDice();
        if (move == 0)
            return false;

        position += move;
        if(position > Board.MAX_FILED_INDEX)
            position = Board.MAX_FILED_INDEX;

        return true;
    }


    public short getBlocked() {
        return blocked;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDiceRollCount() {
        return diceRollCount;
    }

}
