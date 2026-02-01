package com.cards;
import com.Board;
import com.pieces.Piece;
import java.util.Scanner;

public class Kamikaze extends Card {
    public Kamikaze() {
        super("Kamikaze", "Pick one of your pieces to sacrifice. All pieces around it will be destroyed as well.", 5);
    }

    @Override
    public void playCard(Board board) {
        // Prompt the player to select a piece to sacrifice
        System.out.println("Select the coordinates of the piece to sacrifice (e.g., '3 4' for row 3, column 4):");
        Scanner scanner = new Scanner(System.in);
        String userMove = scanner.nextLine();
        int fromY = (int) userMove.charAt(0) - 'a';
        int fromX = Character.getNumericValue(userMove.charAt(1)) - 1;

        // Validate the selected coordinates
        if (fromX < 0 || fromX >= 8 || fromY < 0 || fromY >= 8) {
            System.out.println("Invalid coordinates. Please try again.");
            return;
        }

        Piece selectedPiece = board.getPiece(fromX, fromY);
        if (selectedPiece == null || !selectedPiece.getColor().equals(board.currentPlayer)) {
            System.out.println("No valid piece at the selected coordinates. Please try again.");
            return;
        }
        else {
            // Sacrifice the selected piece and destroy surrounding pieces
            System.out.println("Sacrificed piece at (" + fromX + ", " + fromY + ").");

            for(int i = fromX - 1; i < fromX + 1; i++) {
                for(int j = fromY - 1; j < fromY + 1; j++) {
                    if(i >= 0 && i < 8 && j >= 0 && j < 8) {
                        if(board.getPiece(i, j) != null) {
                            board.removePiece(i, j);
                            System.out.println("Destroyed piece at (" + i + ", " + j + ").");
                        }
                    }
                }
            }
        }
    }
}