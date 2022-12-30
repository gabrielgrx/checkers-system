package boardgame;

import checkers.CheckersPiece;
import checkers.enums.Color;

public class Stone extends CheckersPiece {

    public Stone(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "S";
    }
}
