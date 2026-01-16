package pieces;
import board.*;
import game.Board;

public class Queen extends Piece {
    public Queen(String color) {
        super(color, "Queen", 5);
    }

    // Queens can move any number of squares diagonally or straight
    public boolean isLegalMove(int fromX, int fromY, int toX, int toY, Board board) {
        // Debugging checks
        System.out.println("isLegalMove() 0");
        System.out.println("fromX: " + fromX + " fromY: " + fromY + " toX: " + toX + " toY: " + toY);
        System.out.println("Piece Color: " + board.getPiece(fromX, fromY).getColor());
        System.out.println("Target Piece: " + (board.getPiece(toX, toY) != null ? board.getPiece(toX, toY).getColor() : "none"));

        
        // Confirm move is strictly diagonal or straight
        if(Math.abs(toX - fromX) != Math.abs(toY - fromY) && fromX != toX && fromY != toY) {
            System.out.println("Queen isLegalMove() 1");
            return false;
        }

        // Check if capturing same color piece
        if(board.getPiece(toX, toY) != null && board.getPiece(toX, toY).getColor().equals(board.getPiece(fromX, fromY).getColor())) {
            System.out.println("Queen isLegalMove() 2");
            return false;
        }

        // Check for straight move (Rook style)
        if(fromX == toX || fromY == toY) {
            System.out.println("Queen isLegalMove() 3");
            // Check for other pieces in path
            if(fromX == toX) { // Horizontal move
                System.out.println("Queen isLegalMove() Horizontal");
                int step = (toY > fromY) ? 1 : -1;
                for(int y = fromY + step; y != toY; y += step) {
                    // Debugging output
                    System.out.println(board.getPiece(fromX, y));
                    if(board.getPiece(fromX, y) != null) {
                        System.out.println("Queen isLegalMove() Horizontal Blocked");
                        return false; // Path is blocked
                    }
                }
            } else { // Vertical move
                System.out.println("Queen isLegalMove() Vertical");
                int step = (toX > fromX) ? 1 : -1;
                for(int x = fromX + step; x != toX; x += step) {
                    if(board.getPiece(x, fromY) != null) {
                        System.out.println("Queen isLegalMove() Vertical Blocked");
                        return false; // Path is blocked
                    }
                }
            }
        } else {
            System.out.println("Queen isLegalMove() 4");
            int stepX = (toX > fromX) ? 1 : -1;
            int stepY = (toY > fromY) ? 1 : -1;
            for(int x = fromX + stepX, y = fromY + stepY; x != toX && y != toY; x += stepX, y += stepY) {
                if(board.getPiece(x, y) != null) {
                    return false; // Path is blocked
                }
            }
            return true;
        }
        return true;
    }
}