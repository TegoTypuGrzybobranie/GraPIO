package com.example.grapio;

public class PlayerClass {
    // blocked for $blocked turns
    private short blocked = 0;
    private int position = 0;
    private final String nickName;
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
        if (position > Board.FIELD_META_INDEX)
            position = Board.FIELD_META_INDEX;

        int rem = position;
        if (Board.isSpecial(position)) {
            if (Board.getEffect(position) == Effect.BLOCKS) {
                this.blocked += Board.getValue(position);
                System.out.println("Blocked for " + Board.getValue(position));
            }
            else {
                System.out.println("Moves for " + Board.getValue(position));
                for (int i = 0; i < 100000000; i++)
                    for (int j = 0; j < 25; j++);

                position += Board.getValue(position);

                System.out.println("Ok");
            }
        }

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
