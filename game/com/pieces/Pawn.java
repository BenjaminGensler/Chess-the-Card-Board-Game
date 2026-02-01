package com.pieces;
// import Board;

public class Pawn extends Piece {
    public Pawn(String color) {
        super(color, "Pawn", 1);
    }

    // Pawns can move forward one square, or two squares from their starting position
    // public boolean isLegalMove(int fromX, int fromY, int toX, int toY, Board board) {
    public boolean isLegalMove(int fromX, int fromY, int toX, int toY) {
        
        System.out.println("isLegalMove() 0");
        System.out.println("fromX: " + fromX + " fromY: " + fromY + " toX: " + toX + " toY: " + toY);
        // System.out.println("Piece Color: " + board.getPiece(fromX, fromY).getColor());
        // System.out.println("Target Piece: " + (board.getPiece(toX, toY) != null ? board.getPiece(toX, toY).getColor() : "none"));

        // //White debugging checks
        // System.out.println("White debugging checks:");
        // System.out.println(board.getPiece(fromX, fromY).getColor().equals("white"));
        // System.out.println((toX == fromX + 1));
        // System.out.println((toX == fromX - 1));
        // System.out.println((toY == fromY));
        // System.out.println(board.getPiece(toX, toY) == null);

        
        // //Black debugging checks
        // System.out.println("Black debugging checks:");
        // System.out.println(board.getPiece(fromX, fromY).getColor().equals("black"));
        // System.out.println((toX == fromX - 1));
        // System.out.println((toY == fromY));
        // System.out.println(board.getPiece(toX, toY) == null);
        
        // Check if 1 space forward is empty
        if(((toX == fromX + 1) && (toY == fromY)) ||
           ((toX == fromX - 1) && (toY == fromY))) {
            System.out.println("Pawn isLegalMove() 1");
            return true;
        }

        // Check if 2 spaces forward is empty (space in between must be empty)
        else if((fromX == 1 && toX == fromX + 2 && toY == fromY) ||
                (fromX == 6 && toX == fromX - 2 && toY == fromY)) {
            System.out.println("Pawn isLegalMove() 2");
            return true;
        }


        // White pawn captures diagonally forward
        // if (board.getPiece(fromX, fromY).getColor().equals("white") &&
        //     toX == fromX + 1 && Math.abs(toY - fromY) == 1 &&
        //     board.getPiece(toX, toY) != null &&
        //     !board.getPiece(toX, toY).getColor().equals("white")) {
        //     System.out.println("Pawn isLegalMove() 3 white");
        //     return true;
        // }
        else if(toX == fromX + 1 && Math.abs(toY - fromY) == 1 ) {
            System.out.println("Pawn isLegalMove() 3 white");
            return true;
        }

        // Black pawn captures diagonally forward
        else if (toX == fromX - 1 && Math.abs(toY - fromY) == 1 ) {
            System.out.println("Pawn isLegalMove() 3 black");
            return true;
        }

        System.out.println("Pawn isLegalMove() 4 flase");
        return false;
    }
    
}