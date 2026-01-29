package pieces;
// import Board;

public class Bishop extends Piece {
    public Bishop(String color) {
        super(color, "Bishop", 3);
    }

    // Bishops can move any number of squares diagonally
    public boolean isLegalMove(int fromX, int fromY, int toX, int toY, Board board) {
        // Confirm move is strictly diagonal and not capturing same color piece
        if(Math.abs(toX - fromX) != Math.abs(toY - fromY)) {
            return false;
        }

        // Check if capturing same color piece
        if(board.getPiece(toX, toY) != null && board.getPiece(toX, toY).getColor().equals(board.getPiece(fromX, fromY).getColor())) {
            return false;
        }

        // Check for other pieces in path
        int stepX = (toX > fromX) ? 1 : -1;
        int stepY = (toY > fromY) ? 1 : -1;
        for(int x = fromX + stepX, y = fromY + stepY; x != toX && y != toY; x += stepX, y += stepY) {
            if(board.getPiece(x, y) != null) {
                return false; // Path is blocked
            }
        }
        return true;
    }
}