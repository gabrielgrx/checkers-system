package checkers;

import boardgame.Board;
import boardgame.Position;
import boardgame.Stone;
import checkers.enums.Color;

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

    private void initialSetup() {
        board.placePiece(new Stone(board, Color.BLUE), new Position(2, 1));
        board.placePiece(new Stone(board, Color.BLUE), new Position(4, 1));
    }
}
