package com.example.grapio;

public class PlayerClass {
    // blocked for $blocked turns
    private short blocked = 0;
    private int position = 0;
    private String nickName;
    // How many rolls were made
    private int diceRollCount = 0;

    PlayerClass(String nickName) {
        this.nickName = nickName;
    }


    public boolean movePlayer(int move) {
        if (move == 0)
            return false;

        if (blocked > 0) {
            blocked--;
            return false;
        }

        diceRollCount++;
        position += move;
        if(position > Board.FIELD_META_INDEX)
            position = Board.FIELD_META_INDEX;

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

    public int getDiceRollCount() {
        return diceRollCount;
    }

}
