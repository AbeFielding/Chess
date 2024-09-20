package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board;

    public ChessBoard() {
        // Initialize the board as an 8x8 array
        board = new ChessPiece[8][8];
    }

    /**
     * Resets the board to the default starting position.
     * Pawns are placed on the 2nd and 7th rows, and the main pieces are placed on the
     * 1st and 8th rows for both teams.
     */
    public void resetBoard() {
        // Clear the board first
        board = new ChessPiece[8][8];

        // Place black pieces
        board[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        board[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        board[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

        // Place white pieces
        board[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        board[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        board[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }
    }


    /**
     * Gets the piece located at the given position, or null if the position is empty.
     *
     * @param position The position to check.
     * @return The piece at that position, or null if the position is empty.
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (!isValidPosition(position)) {
            return null;
        }
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Places a piece at the given position on the board.
     *
     * @param position The position to place the piece at.
     * @param piece    The piece to place.
     */
    public void placePiece(ChessPosition position, ChessPiece piece) {
        if (isValidPosition(position)) {
            board[position.getRow() - 1][position.getColumn() - 1] = piece;
        }
    }

    /**
     * Adds a piece at the given position on the board. If a piece already exists at the position,
     * it is replaced by the new piece.
     *
     * @param position The position to add the piece.
     * @param piece    The piece to add.
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (isValidPosition(position)) {
            board[position.getRow() - 1][position.getColumn() - 1] = piece;
        }
    }

    /**
     * Clears the piece at the given position.
     *
     * @param position The position to clear.
     */
    public void clearPiece(ChessPosition position) {
        if (isValidPosition(position)) {
            board[position.getRow() - 1][position.getColumn() - 1] = null;
        }
    }

    /**
     * Helper method to check if a position is valid on the board.
     *
     * @param position The position to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() <= 8 &&
                position.getColumn() >= 1 && position.getColumn() <= 8;
    }

    /**
     * Resets the board to a custom setup for tests or special scenarios.
     *
     * @param customBoard A 2D array of strings representing the custom board.
     */
    public void setupCustomBoard(String[][] customBoard) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String pieceCode = customBoard[row][col];
                if (!pieceCode.equals(" ")) {
                    ChessPiece.PieceType type = getTypeFromCode(pieceCode.charAt(1));
                    ChessGame.TeamColor color = (pieceCode.charAt(0) == 'w') ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
                    placePiece(new ChessPosition(row + 1, col + 1), new ChessPiece(color, type));
                } else {
                    clearPiece(new ChessPosition(row + 1, col + 1));
                }
            }
        }
    }

    /**
     * Helper method to get the PieceType from a character code.
     *
     * @param code The character representing the piece type.
     * @return The corresponding PieceType.
     */
    private ChessPiece.PieceType getTypeFromCode(char code) {
        return switch (code) {
            case 'P' -> ChessPiece.PieceType.PAWN;
            case 'R' -> ChessPiece.PieceType.ROOK;
            case 'N' -> ChessPiece.PieceType.KNIGHT;
            case 'B' -> ChessPiece.PieceType.BISHOP;
            case 'Q' -> ChessPiece.PieceType.QUEEN;
            case 'K' -> ChessPiece.PieceType.KING;
            default -> throw new IllegalArgumentException("Invalid piece type code: " + code);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                sb.append(piece != null ? piece.toString() : ' '); // Use a space for empty squares
                sb.append('|'); // Separator for visualization
            }
            sb.append('\n'); // New line for the next row
        }
        return sb.toString();
    }
}
