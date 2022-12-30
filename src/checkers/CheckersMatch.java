package checkers;

import boardgame.Board;
import checkers.enums.Color;
import checkers.pieces.Stone;

public class CheckersMatch {

    private Board board;

    public CheckersMatch() {
        this.board = new Board(8, 8);
        initialSetup();
    }

    public CheckersPiece[][] getPieces() {
        CheckersPiece[][] mat = new CheckersPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (CheckersPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private void placeNewPiece(char column, int row, CheckersPiece piece) {
        board.placePiece(piece, new CheckersPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('a', 2, new Stone(board, Color.BLUE));
        placeNewPiece('b', 2, new Stone(board, Color.BLUE));
        placeNewPiece('c', 2, new Stone(board, Color.BLUE));
    }
}
