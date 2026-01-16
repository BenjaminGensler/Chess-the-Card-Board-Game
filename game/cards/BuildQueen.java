package game.cards;
import pieces.*;
import board.*;
import game.Board;

import java.util.Scanner;

public class BuildQueen extends Card {
    public BuildQueen() {
        super("Build Queen", "Sacrifice and Rook and Bishop to make a Queen.", 5);
    }

    public void playCard(Board board) {

        System.out.println("Build Queen card played! You can sacrifice a Rook and Bishop to create a Queen.");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the desired Rook position (e.g., 'a2'): ");
        String rookPosition = scanner.nextLine();
        int rookX = Character.getNumericValue(rookPosition.charAt(1)) - 1;
        int rookY = (int) rookPosition.charAt(0) - 'a';

        System.out.print("Please enter the desired Bishop position (e.g., 'b3'): ");
        String bishopPosition = scanner.nextLine();
        int bishopX = Character.getNumericValue(bishopPosition.charAt(1)) - 1;
        int bishopY = (int) bishopPosition.charAt(0) - 'a';

        Piece rook = board.getPiece(rookX, rookY);
        Piece bishop = board.getPiece(bishopX, bishopY);

        if (rook == null || bishop == null) {
            System.out.println("Invalid Rook or Bishop position.");
            return;
        }

        if (rook.getColor().equals(board.currentPlayer) && bishop.getColor().equals(board.currentPlayer)) {
            // Sacrifice the Rook and Bishop
            board.removePiece(rookX, rookY);
            board.removePiece(bishopX, bishopY);
            System.out.println("Sacrificed Rook at (" + rookX + ", " + rookY + ") and Bishop at (" + bishopX + ", " + bishopY + ").");

            System.out.println("Should the Queen be placed at the Rook's position or the Bishop's position?");
            System.out.println("1 - Rook's position");
            System.out.println("2 - Bishop's position");
            String choice = scanner.nextLine().toUpperCase();
            while(true) {
                // Validate input
                if (choice.equals("1")) {
                // Create a new Queen at the Rook's position
                board.placePiece(rookX, rookY, new Queen(board.currentPlayer));
                System.out.println("Created a new Queen at (" + rookX + ", " + rookY + ").");
                return;
                } else if (choice.equals("2")) {
                    // Create a new Queen at the Bishop's position
                    board.placePiece(bishopX, bishopY, new Queen(board.currentPlayer));
                    System.out.println("Created a new Queen at (" + bishopX + ", " + bishopY + ").");
                    return;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            }
        } else {
            System.out.println("You can only sacrifice your own pieces.");
        }
    }
}