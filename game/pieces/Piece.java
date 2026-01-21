package pieces;
import board;
import game.Board;

public class Piece {
    private String color;
    private String type;
    private int points;

    public Piece(String color, String type, int points) {
        this.color = color;
        this.type = type;
        this.points = points;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    public char getLetter() {
        switch (type.toLowerCase()) {
            case "pawn":
                return color.equalsIgnoreCase("white") ? 'P' : 'p';
            case "rook":
                return color.equalsIgnoreCase("white") ? 'R' : 'r';
            case "knight":
                return color.equalsIgnoreCase("white") ? 'N' : 'n';
            case "bishop":
                return color.equalsIgnoreCase("white") ? 'B' : 'b';
            case "queen":
                return color.equalsIgnoreCase("white") ? 'Q' : 'q';
            case "king":
                return color.equalsIgnoreCase("white") ? 'K' : 'k';
            default:
                return '?';
        }
    }

    public boolean isLegalMove(int fromX, int fromY, int toX, int toY, Board board) {
        return false;
    }
}