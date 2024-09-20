package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // Define movement directions for a bishop (diagonal)
        int[][] directions = {
                {1, 1},   // down-right
                {1, -1},  // down-left
                {-1, 1},  // up-right
                {-1, -1}  // up-left
        };

        for (int[] direction : directions) {
            int dRow = direction[0];
            int dCol = direction[1];

            int newRow = row;
            int newCol = col;

            // Move in the current direction until blocked or out of bounds
            while (true) {
                newRow += dRow;
                newCol += dCol;

                // Check if the new position is out of bounds
                if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece targetPiece = board.getPiece(newPosition);

                // If the target position is empty, add the move
                if (targetPiece == null) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    // If there's a piece of the opposite color, add the capture move
                    if (targetPiece.getTeamColor() != this.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    // Stop if the bishop encounters a piece (can’t move further)
                    break;
                }
            }
        }

        return moves;
    }
}
