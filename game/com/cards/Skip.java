package com.cards;
import com.Board;

public class Skip extends Card {
    public Skip() {
        super("Skip", "Skip your opponent's next turn.", 3);
    }

    @Override
    public void playCard(Board board) {
        // Set a flag on the board to skip the next turn
        board.setSkipNextTurn(true);
        System.out.println("Opponent's next turn will be skipped!");
    }
}