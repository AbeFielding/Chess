package chess;

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

        // Set up black pieces (TeamColor.BLACK)
        setUpMajorPieces(ChessGame.TeamColor.BLACK, 1);
        setUpPawns(ChessGame.TeamColor.BLACK, 2);

        // Set up white pieces (TeamColor.WHITE)
        setUpMajorPieces(ChessGame.TeamColor.WHITE, 8);
        setUpPawns(ChessGame.TeamColor.WHITE, 7);
    }

    /**
     * Places all major pieces (Rook, Knight, Bishop, Queen, King) for a team
     * @param color The team color (WHITE or BLACK)
     * @param row The row in which to place the pieces (1 or 8)
     */
    private void setUpMajorPieces(ChessGame.TeamColor color, int row) {
        board[row - 1][0] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
        board[row - 1][1] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        board[row - 1][2] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        board[row - 1][3] = new ChessPiece(color, ChessPiece.PieceType.QUEEN);
        board[row - 1][4] = new ChessPiece(color, ChessPiece.PieceType.KING);
        board[row - 1][5] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        board[row - 1][6] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        board[row - 1][7] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
    }

    /**
     * Places pawns for a team
     * @param color The team color (WHITE or BLACK)
     * @param row The row in which to place the pawns (2 or 7)
     */
    private void setUpPawns(ChessGame.TeamColor color, int row) {
        for (int col = 0; col < 8; col++) {
            board[row - 1][col] = new ChessPiece(color, ChessPiece.PieceType.PAWN);
        }
    }

    /**
     * Gets the piece located at the given position, or null if the position is empty.
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
     * @param position The position to place the piece at.
     * @param piece The piece to place.
     */
    public void placePiece(ChessPosition position, ChessPiece piece) {
        if (isValidPosition(position)) {
            board[position.getRow() - 1][position.getColumn() - 1] = piece;
        }
    }

    /**
     * Adds a piece at the given position on the board. If a piece already exists at the position,
     * it is replaced by the new piece.
     * @param position The position to add the piece.
     * @param piece The piece to add.
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (isValidPosition(position)) {
            board[position.getRow() - 1][position.getColumn() - 1] = piece;
        }
    }

    /**
     * Clears the piece at the given position.
     * @param position The position to clear.
     */
    public void clearPiece(ChessPosition position) {
        if (isValidPosition(position)) {
            board[position.getRow() - 1][position.getColumn() - 1] = null;
        }
    }

    /**
     * Helper method to check if a position is valid on the board.
     * @param position The position to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() <= 8 &&
                position.getColumn() >= 1 && position.getColumn() <= 8;
    }

    /**
     * Resets the board to a custom setup for tests or special scenarios.
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
}
