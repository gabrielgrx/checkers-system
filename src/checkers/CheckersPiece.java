package checkers;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import checkers.enums.Color;

public  abstract class CheckersPiece extends Piece {

    private Color color;

    public CheckersPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isThereOpponentPiece(Position position) {
        CheckersPiece p = (CheckersPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }

    public CheckersPosition getCheckersPosition() {
        return CheckersPosition.fromPosition(position);
    }
}
