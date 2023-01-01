package checkers.pieces;

import boardgame.Board;
import boardgame.Position;
import checkers.CheckersPiece;
import checkers.enums.Color;

public class Dame extends CheckersPiece {

    public Dame(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "D";
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] matNoOpponent = new boolean[getBoard().getRows()][getBoard().getColumns()];
        boolean[][] matThereIsOpponent = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);
        Position p2 = new Position(0, 0);

        //nw
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        p2.setValues(position.getRow() - 2, position.getColumn() - 2);
        while (getBoard().positionExists(p)) {
            if (isThereOpponentPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                matThereIsOpponent[p2.getRow()][p2.getColumn()] = true;
                p.setValues(p.getRow() - 1, p.getColumn() - 1);
                p2.setValues(p2.getRow() - 1, p2.getColumn() - 1);
            } else if (!getBoard().thereIsAPiece(p)) {
                matNoOpponent[p.getRow()][p.getColumn()] = true;
                p.setValues(p.getRow() - 1, p.getColumn() - 1);
                p2.setValues(p2.getRow() - 1, p2.getColumn() - 1);
            }
        }
        //ne
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        p2.setValues(position.getRow() - 2, position.getColumn() + 2);
        while (getBoard().positionExists(p)) {
            if (isThereOpponentPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                matThereIsOpponent[p2.getRow()][p2.getColumn()] = true;
                p.setValues(p.getRow() - 1, p.getColumn() + 1);
                p2.setValues(p2.getRow() - 1, p2.getColumn() + 1);
            } else if (!getBoard().thereIsAPiece(p)) {
                matNoOpponent[p.getRow()][p.getColumn()] = true;
                p.setValues(p.getRow() - 1, p.getColumn() + 1);
                p2.setValues(p2.getRow() - 1, p2.getColumn() + 1);
            }
        }
        //se
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        p2.setValues(position.getRow() + 2, position.getColumn() - 2);
        while (getBoard().positionExists(p)) {
            if (isThereOpponentPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                matThereIsOpponent[p2.getRow()][p2.getColumn()] = true;
                p.setValues(p.getRow() + 1, p.getColumn() - 1);
                p2.setValues(p2.getRow() + 1, p2.getColumn() - 1);
            } else if (!getBoard().thereIsAPiece(p)) {
                matNoOpponent[p.getRow()][p.getColumn()] = true;
                p.setValues(p.getRow() + 1, p.getColumn() - 1);
                p2.setValues(p2.getRow() + 1, p2.getColumn() - 1);
            }
        }

        //sw
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        p2.setValues(position.getRow() + 2, position.getColumn() + 2);
        while (getBoard().positionExists(p)) {
            if (isThereOpponentPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                matThereIsOpponent[p2.getRow()][p2.getColumn()] = true;
                p.setValues(p.getRow() + 1, p.getColumn() + 1);
                p2.setValues(p2.getRow() + 1, p2.getColumn() + 1);
            } else if (!getBoard().thereIsAPiece(p)) {
                matNoOpponent[p.getRow()][p.getColumn()] = true;
                p.setValues(p.getRow() + 1, p.getColumn() + 1);
                p2.setValues(p2.getRow() + 1, p2.getColumn() + 1);
            }
        }

        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (matThereIsOpponent[i][j]) {
                    mat = matThereIsOpponent;
                }
            }
        }

        if (matThereIsOpponent == mat) {
            return mat;
        } else {
            return mat = matNoOpponent;
        }
    }
}
