package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
            case KING -> addKingMoves(validMoves, board, myPosition);
            case PAWN -> addPawnMoves(validMoves, board, myPosition);
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

    private void addKingMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int[][] kingMoves = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Vertical and horizontal
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal movements
        };

        for (int[] move : kingMoves) {
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

    private void addPawnMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int direction = pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1; // White moves up, black down
        int startRow = pieceColor == ChessGame.TeamColor.WHITE ? 2 : 7; // Starting row for pawns

        // Move forward
        ChessPosition forwardPosition = new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn());
        if (board.isValidPosition(forwardPosition) && board.getPiece(forwardPosition) == null) {
            if (myPosition.getRow() == (pieceColor == ChessGame.TeamColor.WHITE ? 7 : 2)) {
                // Pawn promotion move: add all promotion types
                validMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.QUEEN)); // Promote to Queen
                validMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.ROOK)); // Promote to Rook
                validMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.BISHOP)); // Promote to Bishop
                validMoves.add(new ChessMove(myPosition, forwardPosition, PieceType.KNIGHT)); // Promote to Knight
            } else {
                validMoves.add(new ChessMove(myPosition, forwardPosition, null));
            }

            // Double move from starting position
            if (myPosition.getRow() == startRow) {
                ChessPosition doubleForwardPosition = new ChessPosition(myPosition.getRow() + 2 * direction, myPosition.getColumn());
                if (board.isValidPosition(doubleForwardPosition) && board.getPiece(doubleForwardPosition) == null) {
                    validMoves.add(new ChessMove(myPosition, doubleForwardPosition, null));
                }
            }
        }

        // Captures
        for (int offset : new int[]{-1, 1}) {
            ChessPosition capturePosition = new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn() + offset);
            if (board.isValidPosition(capturePosition)) {
                ChessPiece pieceAtCapturePosition = board.getPiece(capturePosition);
                if (pieceAtCapturePosition != null && pieceAtCapturePosition.getTeamColor() != this.pieceColor) {
                    if (myPosition.getRow() == (pieceColor == ChessGame.TeamColor.WHITE ? 7 : 2)) {
                        // Capture promotion: add all promotion types
                        validMoves.add(new ChessMove(myPosition, capturePosition, PieceType.QUEEN)); // Promote to Queen
                        validMoves.add(new ChessMove(myPosition, capturePosition, PieceType.ROOK)); // Promote to Rook
                        validMoves.add(new ChessMove(myPosition, capturePosition, PieceType.BISHOP)); // Promote to Bishop
                        validMoves.add(new ChessMove(myPosition, capturePosition, PieceType.KNIGHT)); // Promote to Knight
                    } else {
                        validMoves.add(new ChessMove(myPosition, capturePosition, null));
                    }
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
