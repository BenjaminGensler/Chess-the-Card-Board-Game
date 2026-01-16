package game.cards;
import pieces.*;
import board.*;
import game.Board;

import java.util.Scanner;

public class PawnUpgrade extends Card {
    public PawnUpgrade() {
        super("Pawn Upgrade", "Promote a pawn to a rook, bishop, or knight immediately.", 5);
    }

    public void playCard(Board board) {
        
        System.out.println("Pawn Upgrade card played! You can promote a pawn.");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the desired pawn position (e.g., 'a2'): ");
        String userMove = scanner.nextLine();

        int pieceX = Character.getNumericValue(userMove.charAt(1)) - 1;
        int pieceY = (int) userMove.charAt(0) - 'a';

        // logic of pawn upgrade
        Piece pawn = board.getPiece(pieceX, pieceY);

        if (pawn == null) {
            System.out.println("No piece found at that position.");
            return;
        }

        if (board.getPiece(pieceX, pieceY).getType() == "Pawn" && pawn.getColor().equals(board.currentPlayer)) {
            System.out.println("Promote your pawn to:");
            System.out.println("1. Rook");
            System.out.println("2. Bishop");
            System.out.println("3. Knight");
            
            int choice = scanner.nextInt();
            Piece upgradedPiece = null;
            switch (choice) {
                case 1:
                    upgradedPiece = new Rook(pawn.getColor());
                    break;
                case 2:
                    upgradedPiece = new Bishop(pawn.getColor());
                    break;
                case 3:
                    upgradedPiece = new Knight(pawn.getColor());
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            board.placePiece(pieceX, pieceY, upgradedPiece);
            System.out.println("Pawn promoted!");
        } else {
            System.out.println("Invalid pawn.");
        }
    }
}