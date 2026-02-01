package com.pieces;
// import Board;

public class King extends Piece {
    public King(String color) {
        super(color, "King", 0);
    }

    // public boolean isLegalMove(int fromX, int fromY, int toX, int toY, Board board) {
    public boolean isLegalMove(int fromX, int fromY, int toX, int toY) {
        // Kings can move one square in any direction
        if(Math.abs(toX - fromX) <= 1 && Math.abs(toY - fromY) <= 1) {
            // // Check for capturing same color piece
            // if(board.getPiece(toX, toY) != null && board.getPiece(toX, toY).getColor().equals(board.getPiece(fromX, fromY).getColor())) {
            //     return false;
            // }
            return true;
        }
        return false;
    }
}