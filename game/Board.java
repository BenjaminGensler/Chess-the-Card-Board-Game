// package game;


import java.util.Scanner;
import pieces.*;

public class Board {
    // Create a Scanner object
    Scanner scanner = new Scanner(System.in);

    private Piece[][] board;

    public String currentPlayer = "white";

    private boolean skipNextTurn = false;

    // Coordinates for white and black kings (Default to -1, -1 if not placed)
    private int whiteKingX = -1, whiteKingY = -1;
    private int blackKingX = -1, blackKingY = -1;

    // Board constructor
    public Board() {
        board = new Piece[8][8];
    }

    public void switchPlayer() {
        if (skipNextTurn) {
            System.out.println(currentPlayer + "'s turn is skipped!");
            skipNextTurn = false; // Reset the flag
        } else {
            currentPlayer = currentPlayer.equals("white") ? "black" : "white";
        }
    }

    // Places object on board to x y position
    public void placePiece(int x, int y, Piece piece) {
        board[x][y] = piece;

        // Update king coordinates if a king is placed
        if (piece != null && piece.getType().equals("King")) {
            if (piece.getColor().equals("white")) {
                whiteKingX = x;
                whiteKingY = y;
            } else if (piece.getColor().equals("black")) {
                blackKingX = x;
                blackKingY = y;
            }
        }
    }

    public void removePiece(int x, int y) {
        // Piece piece = getPiece(x, y);
        board[x][y] = null;
    }

    // moves object on board to x y position
    public void movePiece(int fromX, int fromY, int toX, int toY) {
        if (this.getPiece(fromX, fromY) != null) {
            // check if the pieces move is legal
            if(this.getPiece(fromX, fromY).isLegalMove(fromX, fromY, toX, toY, this) == false) {
                System.out.println("Illegal move");
                return;
            }
            else {
                this.placePiece(toX, toY, this.getPiece(fromX, fromY));
                this.placePiece(fromX, fromY, null);

                if(this.getPiece(toX, toY).getType().equals("Pawn")) {
                    // Pawn promotion
                    if((this.getPiece(toX, toY).getColor().equals("white") && toX == 7) ||
                       (this.getPiece(toX, toY).getColor().equals("black") && toX == 0)) {
                        System.out.println("Pawn promoted! Please select a piece to promote to (Rook, Knight, Bishop, Queen): ");
                        Scanner scanner = new Scanner(System.in);
                        String promotionChoice = scanner.nextLine();

                        switch (promotionChoice.toLowerCase()) {
                            case "rook":
                                this.placePiece(toX, toY, new Rook(this.getPiece(toX, toY).getColor()));
                                break;
                            case "knight":
                                this.placePiece(toX, toY, new Knight(this.getPiece(toX, toY).getColor()));
                                break;
                            case "bishop":
                                this.placePiece(toX, toY, new Bishop(this.getPiece(toX, toY).getColor()));
                                break;
                            case "queen":
                                this.placePiece(toX, toY, new Queen(this.getPiece(toX, toY).getColor()));
                                break;
                            default:
                                System.out.println("Invalid choice. Promoting to Queen by default.");
                                this.placePiece(toX, toY, new Queen(this.getPiece(toX, toY).getColor()));
                                break;
                        }
                    }
                }
            }
        }
    }

    // Silent move for simulation (does not print or undo)
    public void movePieceSilent(int fromX, int fromY, int toX, int toY) {
        if (this.getPiece(fromX, fromY) != null) {
            if (this.getPiece(fromX, fromY).isLegalMove(fromX, fromY, toX, toY, this)) {
                this.placePiece(toX, toY, this.getPiece(fromX, fromY));
                this.placePiece(fromX, fromY, null);
            }
        }
    }

    // Retrieves the object at the x y position
    public Piece getPiece(int x, int y) {
        // Out of bounds check
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            return null;
        }

        return board[x][y];
    }

    // Check if king is in check
    public boolean isKingInCheck(String color){
        int kingX, kingY;
        if (color.equals("white")) {
            kingX = whiteKingX;
            kingY = whiteKingY;
        } else {
            kingX = blackKingX;
            kingY = blackKingY;
        }

        // Check if Pawns can attack the King
        if (color.equals("white")) {
            if ((this.getPiece(kingX - 1, kingY + 1) != null && this.getPiece(kingX - 1, kingY + 1).getType().equals("Pawn") && this.getPiece(kingX - 1, kingY + 1).getColor().equals("black")) ||
                (this.getPiece(kingX + 1, kingY + 1) != null && this.getPiece(kingX + 1, kingY + 1).getType().equals("Pawn") && this.getPiece(kingX + 1, kingY + 1).getColor().equals("black"))) {
                return true;
            }
        } else {
            if ((this.getPiece(kingX - 1, kingY - 1) != null && this.getPiece(kingX - 1, kingY - 1).getType().equals("Pawn") && this.getPiece(kingX - 1, kingY - 1).getColor().equals("white")) ||
                (this.getPiece(kingX + 1, kingY - 1) != null && this.getPiece(kingX + 1, kingY - 1).getType().equals("Pawn") && this.getPiece(kingX + 1, kingY - 1).getColor().equals("white"))) {
                return true;
            }
        }

        // Check if Knights can attack the king
        if ((this.getPiece(kingX - 1, kingY - 2) != null && this.getPiece(kingX - 1, kingY - 2).getType().equals("Knight")) && !this.getPiece(kingX - 1, kingY - 2).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX - 1, kingY + 2) != null && this.getPiece(kingX - 1, kingY + 2).getType().equals("Knight")) && !this.getPiece(kingX - 1, kingY + 2).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX + 1, kingY - 2) != null && this.getPiece(kingX + 1, kingY - 2).getType().equals("Knight")) && !this.getPiece(kingX + 1, kingY - 2).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX + 1, kingY + 2) != null && this.getPiece(kingX + 1, kingY + 2).getType().equals("Knight")) && !this.getPiece(kingX + 1, kingY + 2).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX - 2, kingY - 1) != null && this.getPiece(kingX - 2, kingY - 1).getType().equals("Knight")) && !this.getPiece(kingX - 2, kingY - 1).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX - 2, kingY + 1) != null && this.getPiece(kingX - 2, kingY + 1).getType().equals("Knight")) && !this.getPiece(kingX - 2, kingY + 1).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX + 2, kingY - 1) != null && this.getPiece(kingX + 2, kingY - 1).getType().equals("Knight")) && !this.getPiece(kingX + 2, kingY - 1).getColor().equals(this.getPiece(kingX, kingY).getColor()) ||
            (this.getPiece(kingX + 2, kingY + 1) != null && this.getPiece(kingX + 2, kingY + 1).getType().equals("Knight")) && !this.getPiece(kingX + 2, kingY + 1).getColor().equals(this.getPiece(kingX, kingY).getColor())) {
            return true;
        }

        // Check if Rooks or Queens can attack the king (horizontal and vertical)
        int directions[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int x = kingX + dir[0];
            int y = kingY + dir[1];
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (getPiece(x, y) != null) {
                    if ((getPiece(x, y).getType().equals("Rook") || getPiece(x, y).getType().equals("Queen")) &&
                        !getPiece(x, y).getColor().equals(this.getPiece(kingX, kingY).getColor())) {
                        return true;
                    } else {
                        break; // Blocked by another piece
                    }
                }
                x += dir[0];
                y += dir[1];
            }
        }

        // Check if Bishop or Queens can attack the king (diagonal)
        int diagonalDirections[][] = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : diagonalDirections) {
            int x = kingX + dir[0];
            int y = kingY + dir[1];
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (getPiece(x, y) != null) {
                    if ((getPiece(x, y).getType().equals("Bishop") || getPiece(x, y).getType().equals("Queen")) &&
                        !getPiece(x, y).getColor().equals(this.getPiece(kingX, kingY).getColor())) {
                        return true;
                    } else {
                        break; // Blocked by another piece
                    }
                }
                x += dir[0];
                y += dir[1];
            }
        }

        return false; // King is not in check
    }

    public boolean isCheckmate(String color) {
        if (!isKingInCheck(color)) {
            return false;
        }

        // Try every piece of the given color
        for (int fromX = 0; fromX < 8; fromX++) {
            for (int fromY = 0; fromY < 8; fromY++) {
                Piece piece = getPiece(fromX, fromY);
                if (piece != null && piece.getColor().equalsIgnoreCase(color)) {
                    // Try every possible destination
                    for (int toX = 0; toX < 8; toX++) {
                        for (int toY = 0; toY < 8; toY++) {
                            if (piece.isLegalMove(fromX, fromY, toX, toY, this)) {
                                // Simulate the move
                                Board copy = this.copyBoard();
                                copy.movePieceSilent(fromX, fromY, toX, toY);
                                if (!copy.isKingInCheck(color)) {
                                    return false; // Found a move that gets out of check
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; // No moves get out of check, so it's checkmate
    }

    //Display the board
    public void printBoard() {
        // System.out.println("printBoard() 1");
        // Top border
        System.out.print("   ");
        for (int i = 0; i < 8; i++) {
            System.out.print("---");
        }
        System.out.println();
        // System.out.println("printBoard() 2");

        // Board rows with row numbers (reverse order)
        for (int row = board.length - 1; row >= 0; row--) {
            System.out.print((row + 1) + " |");
            for (int col = 0; col < board.length; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    System.out.print(" " + piece.getLetter() + " ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println(" |");
        }

        // System.out.println("printBoard() 3");

        // Bottom border
        System.out.print("   ");
        for (int i = 0; i < 8; i++) {
            System.out.print("---");
        }
        System.out.println();
        
        // System.out.println("printBoard() 4");

        // Column letters
        System.out.print("   ");
        for (char c = 'a'; c <= 'h'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();

        // System.out.println("printBoard() 5");
    }

    // Copy the board (for simulating moves)
    public Board copyBoard() {
        Board newBoard = new Board();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = this.getPiece(x, y);
                if (piece != null) {
                    // Create a new instance of the same piece type and color
                    switch (piece.getType().toLowerCase()) {
                        case "pawn":
                            newBoard.placePiece(x, y, new Pawn(piece.getColor()));
                            break;
                        case "rook":
                            newBoard.placePiece(x, y, new Rook(piece.getColor()));
                            break;
                        case "knight":
                            newBoard.placePiece(x, y, new Knight(piece.getColor()));
                            break;
                        case "bishop":
                            newBoard.placePiece(x, y, new Bishop(piece.getColor()));
                            break;
                        case "queen":
                            newBoard.placePiece(x, y, new Queen(piece.getColor()));
                            break;
                        case "king":
                            newBoard.placePiece(x, y, new King(piece.getColor()));
                            break;
                    }
                }
            }
        }
        return newBoard;
    }

    public boolean isSkipNextTurn() {
        return skipNextTurn;
    }

    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }
}