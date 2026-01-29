package game.cards;
import game.Board;
import game.pieces.*;
import java.util.Scanner;

public class TeleportKing extends Card {
    public TeleportKing() {
        super("Teleport King", "Teleport your king anywhere on the board. If you have this card during checkmate teleport your King instead.", 6);
    }

    @Override
    public void playCard(Board board) {
        // Prompt the player to select a piece to teleport
        System.out.println("Select the coordinates of your King to teleport (e.g., '3 4' for row 3, column 4):");
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
        if (selectedPiece == null || !selectedPiece.getColor().equals(board.currentPlayer) || !selectedPiece.getType().equals("King")) {
            System.out.println("No valid King at the selected coordinates. Please try again.");
            return;
        }
        else {
            // Teleport the selected King to new coordinates
            System.out.println("Enter the new coordinates to teleport your King (e.g., '3 4' for row 3, column 4):");
            String newMove = scanner.nextLine();
            int toY = (int) newMove.charAt(0) - 'a';
            int toX = Character.getNumericValue(newMove.charAt(1)) - 1;

            // Validate the new coordinates
            if (toX < 0 || toX >= 8 || toY < 0 || toY >= 8) {
                System.out.println("Invalid coordinates. Please try again.");
                return;
            }

            // Move the King to the new position
            board.placePiece(toX, toY, selectedPiece);
            board.removePiece(fromX, fromY);

            while(!board.isKingInCheck(board.currentPlayer.getColor())) {
                System.out.println("The King must be teleported to a position that is in check. Please try again.");
                System.out.println("Enter the new coordinates to teleport your King (e.g., '3 4' for row 3, column 4):");
                newMove = scanner.nextLine();
                toY = (int) newMove.charAt(0) - 'a';
                toX = Character.getNumericValue(newMove.charAt(1)) - 1;

                // Validate the new coordinates
                if (toX < 0 || toX >= 8 || toY < 0 || toY >= 8) {
                    System.out.println("Invalid coordinates. Please try again.");
                    return;
                }

                // Move the King to the new position
                board.placePiece(toX, toY, selectedPiece);
            }

            System.out.println("King teleported to (" + toX + ", " + toY + ").");
        }
    }
}