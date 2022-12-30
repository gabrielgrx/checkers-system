package checkers;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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

    public CheckersPiece performCheckersMove(CheckersPosition sourcePosition, CheckersPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        vallidateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);
        return (CheckersPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }

    private void vallidateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new CheckersException("There is no piece on source position");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new CheckersException("There is no possible moves for the chosen piece");
        }
    }

    private void placeNewPiece(char column, int row, CheckersPiece piece) {
        board.placePiece(piece, new CheckersPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('a', 2, new Stone(board, Color.BLUE));
        placeNewPiece('b', 2, new Stone(board, Color.BLUE));
        placeNewPiece('c', 2, new Stone(board, Color.BLUE));

        placeNewPiece('a', 7, new Stone(board, Color.RED));
        placeNewPiece('b', 7, new Stone(board, Color.RED));
        placeNewPiece('c', 7, new Stone(board, Color.RED));
    }
}
