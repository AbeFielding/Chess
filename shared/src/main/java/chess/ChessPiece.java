package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
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
     * Calculates all the positions a chess piece can move to.
     * Does not take into account moves that are illegal due to leaving the king in danger.
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        switch (this.type) {
            case BISHOP -> addBishopMoves(validMoves, board, myPosition);
            case ROOK -> addRookMoves(validMoves, board, myPosition);
            case QUEEN -> addQueenMoves(validMoves, board, myPosition);
            case KNIGHT -> addKnightMoves(validMoves, board, myPosition);
            // Add other cases for different pieces here
        }
        return validMoves;
    }

    private void addBishopMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {
                {1, 1}, {-1, -1}, {1, -1}, {-1, 1} // Diagonal movements
        };

        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            addMovesInDirection(validMoves, board, myPosition, direction, row, col);
        }
    }

    private void addRookMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1} // Vertical and horizontal
        };

        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            addMovesInDirection(validMoves, board, myPosition, direction, row, col);
        }
    }

    private void addQueenMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Vertical and horizontal
                {1, 1}, {-1, -1}, {1, -1}, {-1, 1} // Diagonal movements
        };

        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            addMovesInDirection(validMoves, board, myPosition, direction, row, col);
        }
    }

    private void addKnightMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int[][] knightMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : knightMoves) {
            int newRow = myPosition.getRow() + move[0];
            int newCol = myPosition.getColumn() + move[1];
            ChessPosition newPos = new ChessPosition(newRow, newCol);
            if (board.isValidPosition(newPos)) {
                ChessPiece pieceAtNewPos = board.getPiece(newPos);
                if (pieceAtNewPos == null || pieceAtNewPos.getTeamColor() != this.pieceColor) {
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
    }

    private void addMovesInDirection(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, int[] direction, int row, int col) {
        while (true) {
            row += direction[0];
            col += direction[1];

            ChessPosition newPos = new ChessPosition(row, col);
            if (board.isValidPosition(newPos)) {
                ChessPiece pieceAtNewPos = board.getPiece(newPos);
                if (pieceAtNewPos == null) {
                    // No piece blocking, add move
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                } else {
                    if (pieceAtNewPos.getTeamColor() != this.pieceColor) {
                        // Enemy piece, add move and stop further movement
                        validMoves.add(new ChessMove(myPosition, newPos, null));
                    }
                    break;
                }
            } else {
                // Out of bounds
                break;
            }
        }
    }
}
