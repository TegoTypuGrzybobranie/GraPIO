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

    public void movePlayer(int move) {
        if (move == 0 || finished) {
            return;
        }

        if (blocked > 0) {
            blocked--;
            return;
        }

        diceRollCount++;
        position += move;
        if (position >= Board.FIELD_META_INDEX) {
            position = Board.FIELD_META_INDEX;
            finished = true;
        }

        if (Board.isSpecial(position)) {
            if (Board.getEffect(position) == Effect.BLOCKS) {
                this.blocked += Board.getValue(position);
                System.out.println("Blocked for " + Board.getValue(position));
            }
            else {
                System.out.println("Moves for " + Board.getValue(position));
                position += Board.getValue(position);
                System.out.println("Ok");
            }
        }

    }

    public boolean isFinished() {
        return finished;
    }

    public String getNickName() {
        return nickName;
    }

    public int getPosition() {
        return position;
    }

}
