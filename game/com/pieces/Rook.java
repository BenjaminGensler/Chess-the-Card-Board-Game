package com.pieces;
// import Board;

// import game.Board;

public class Rook extends Piece {
    public Rook(String color) {
        super(color, "Rook", 3);
    }

    // Rooks can move any number of square horizontally or vertically
    // public boolean isLegalMove(int fromX, int fromY, int toX, int toY, Board board) {
    public boolean isLegalMove(int fromX, int fromY, int toX, int toY) {
        // Rook moves must be strictly horizontal or vertical
        if (fromX != toX && fromY != toY) {
            return false;
        }
        // Move is legal for a rook (ignoring board state)
        return true;
        
        // // Confirm move is strictly horizontal or vertical and not capturing same color piece
        // if(fromX != toX && fromY != toY) {
        //     return false;
        // }

        // if(board.getPiece(toX, toY) != null && board.getPiece(toX, toY).getColor().equals(board.getPiece(fromX, fromY).getColor())) {
        //     return false;
        // }

        // // Check for other pieces in path
        // if(fromX == toX) { // Vertical move
        //     int step = (toY > fromY) ? 1 : -1;
        //     for(int y = fromY + step; y != toY; y += step) {
        //         if(board.getPiece(fromX, y) != null) {
        //             return false; // Path is blocked
        //         }
        //     }
        // } else { // Horizontal move
        //     int step = (toX > fromX) ? 1 : -1;
        //     for(int x = fromX + step; x != toX; x += step) {
        //         if(board.getPiece(x, fromY) != null) {
        //             return false; // Path is blocked
        //         }
        //     }
        // }
        // return true;
    }
}