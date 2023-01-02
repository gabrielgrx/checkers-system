package checkers;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import checkers.enums.Color;
import checkers.pieces.Dame;
import checkers.pieces.Stone;

import java.util.ArrayList;
import java.util.List;

public class CheckersMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean endMatch;
    private CheckersPiece promoted;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public CheckersMatch() {
        this.board = new Board(8, 8);
        this.turn = 1;
        this.currentPlayer = Color.BLUE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isEndMatch() {
        return endMatch;
    }

    public CheckersPiece getPromoted() {
        return promoted;
    }

    private boolean terminateMatch() {
        List<Piece> blue = piecesOnTheBoard.stream().filter(x -> ((CheckersPiece) x).getColor() == Color.BLUE).toList();
        List<Piece> red = piecesOnTheBoard.stream().filter(x -> ((CheckersPiece) x).getColor() == Color.RED).toList();
        if (blue.isEmpty() || red.isEmpty()) {
            return true;
        }
        return false;
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

    public boolean[][] possibleMoves(CheckersPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    private boolean opponentNearTargetPosition(Position targetPosition) {
        CheckersPiece p = (CheckersPiece) board.piece(targetPosition);

        Position p1 = new Position(targetPosition.getRow() - 1, targetPosition.getColumn() - 1);
        Position p2 = new Position(targetPosition.getRow() - 1, targetPosition.getColumn() + 1);
        Position p3 = new Position(targetPosition.getRow() + 1, targetPosition.getColumn() - 1);
        Position p4 = new Position(targetPosition.getRow() + 1, targetPosition.getColumn() + 1);

        Position p5 = new Position(targetPosition.getRow() - 2, targetPosition.getColumn() - 2);
        Position p6 = new Position(targetPosition.getRow() - 2, targetPosition.getColumn() + 2);
        Position p7 = new Position(targetPosition.getRow() + 2, targetPosition.getColumn() - 2);
        Position p8 = new Position(targetPosition.getRow() + 2, targetPosition.getColumn() + 2);

        if (p instanceof Stone) {
            if (board.positionExists(p1) && p.isThereOpponentPiece(p1) && !board.thereIsAPiece(p5)) {
                return true;
            }
            if (board.positionExists(p2) && p.isThereOpponentPiece(p2) && !board.thereIsAPiece(p6)) {
                return true;
            }
            if (board.positionExists(p3) && p.isThereOpponentPiece(p3) && !board.thereIsAPiece(p7)) {
                return true;
            }
            if (board.positionExists(p4) && p.isThereOpponentPiece(p4) && !board.thereIsAPiece(p8)) {
                return true;
            }
        }

        if (p instanceof Dame) {
            while (board.positionExists(p1)) {
                if (p.isThereOpponentPiece(p1)) {
                    return true;
                }
                p1.setValues(p1.getRow() - 1, p1.getColumn() - 1);
            }
            while (board.positionExists(p1)) {
                if (p.isThereOpponentPiece(p1)) {
                    return true;
                }
                p1.setValues(p1.getRow() - 1, p1.getColumn() + 1);
            }
            while (board.positionExists(p1)) {
                if (p.isThereOpponentPiece(p1)) {
                    return true;
                }
                p1.setValues(p1.getRow() + 1, p1.getColumn() - 1);
            }
            while (board.positionExists(p1)) {
                if (p.isThereOpponentPiece(p1)) {
                    return true;
                }
                p1.setValues(p1.getRow() + 1, p1.getColumn() + 1);
            }
        }
        return false;
    }

    public CheckersPiece performCheckersMove(CheckersPosition sourcePosition, CheckersPosition targetPosition) {

        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        CheckersPiece movedPiece = (CheckersPiece) board.piece(target);

        if (terminateMatch()) {
            endMatch = true;
        }
        //#Specialmove promoted
        promoted = null;
        if (movedPiece instanceof Stone) {
            if ((movedPiece.getColor() == Color.BLUE && target.getRow() == 0) || (movedPiece.getColor() == Color.RED && target.getRow() == 7)) {
                promoted = (CheckersPiece) board.piece(target);
            }
        }
        if (!opponentNearTargetPosition(target)) {
            nextTurn();
        }

        return (CheckersPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Position opponentPosition = new Position(0, 0);
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        //Capture piece when moves nw
        opponentPosition.setValues(source.getRow() - 1, source.getColumn() - 1);
        while (!board.thereIsAPiece(opponentPosition)) {
            opponentPosition.setValues(opponentPosition.getRow() - 1, opponentPosition.getColumn() - 1);
        }
        if (board.positionExists(opponentPosition) && board.thereIsAPiece(opponentPosition)
                && target.getRow() < opponentPosition.getRow() && target.getColumn() < opponentPosition.getColumn()) {
            capturedPiece = board.removePiece(opponentPosition);
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
            return capturedPiece;
        }

        //Capture piece when moves ne
        opponentPosition.setValues(source.getRow() - 1, source.getColumn() + 1);
        while (!board.thereIsAPiece(opponentPosition)) {
            opponentPosition.setValues(opponentPosition.getRow() - 1, opponentPosition.getColumn() + 1);
        }
        if (board.positionExists(opponentPosition) && board.thereIsAPiece(opponentPosition)
                && target.getRow() <= opponentPosition.getRow() - 1 && target.getColumn() >= opponentPosition.getColumn() + 1) {
            capturedPiece = board.removePiece(opponentPosition);
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
            return capturedPiece;
        }

        //Capture piece when moves sw
        opponentPosition.setValues(source.getRow() + 1, source.getColumn() - 1);
        while (!board.thereIsAPiece(opponentPosition)) {
            opponentPosition.setValues(opponentPosition.getRow() + 1, opponentPosition.getColumn() - 1);
        }
        if (board.positionExists(opponentPosition) && board.thereIsAPiece(opponentPosition)
                && target.getRow() >= opponentPosition.getRow() + 1 && target.getColumn() <= opponentPosition.getColumn() - 1) {
            capturedPiece = board.removePiece(opponentPosition);
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
            return capturedPiece;
        }

        //Capture piece when moves se
        opponentPosition.setValues(source.getRow() + 1, source.getColumn() + 1);
        while (!board.thereIsAPiece(opponentPosition)) {
            opponentPosition.setValues(opponentPosition.getRow() + 1, opponentPosition.getColumn() + 1);
        }
        if (board.positionExists(opponentPosition) && board.thereIsAPiece(opponentPosition)
                && target.getRow() >= opponentPosition.getRow() + 1 && target.getColumn() >= opponentPosition.getColumn() + 1) {
            capturedPiece = board.removePiece(opponentPosition);
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
            return capturedPiece;
        }
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new CheckersException("There is no piece on source position");
        }
        if (currentPlayer != ((CheckersPiece) board.piece(position)).getColor()) {
            throw new CheckersException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new CheckersException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new CheckersException("The chosen piece can't move to target position");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.BLUE) ? Color.RED : Color.BLUE;
    }

    private void placeNewPiece(char column, int row, CheckersPiece piece) {
        board.placePiece(piece, new CheckersPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {

        placeNewPiece('d', 5, new Dame(board, Color.BLUE));
//        placeNewPiece('d', 4, new Dame(board, Color.BLUE));
        placeNewPiece('h', 3, new Dame(board, Color.BLUE));
        placeNewPiece('f', 2, new Dame(board, Color.BLUE));
//        placeNewPiece('e', 8, new Dame(board, Color.BLUE));
//        placeNewPiece('c', 2, new Dame(board, Color.BLUE));

        placeNewPiece('b', 7, new Dame(board, Color.RED));
        placeNewPiece('e', 6, new Dame(board, Color.RED));
        placeNewPiece('b', 3, new Dame(board, Color.RED));
        placeNewPiece('e', 3, new Dame(board, Color.RED));
        placeNewPiece('c', 5, new Dame(board, Color.RED));
        placeNewPiece('c', 6, new Dame(board, Color.RED));

//        placeNewPiece('e', 4, new Stone(board, Color.BLUE));
//        placeNewPiece('a', 6, new Stone(board, Color.BLUE));
//        placeNewPiece('h', 3, new Stone(board, Color.BLUE));
//
//        placeNewPiece('b', 3, new Stone(board, Color.RED));
//        placeNewPiece('d', 5, new Stone(board, Color.RED));
//        placeNewPiece('d', 3, new Stone(board, Color.RED));
//        placeNewPiece('a', 4, new Stone(board, Color.RED));
//        placeNewPiece('d', 7, new Stone(board, Color.RED));

//        placeNewPiece('a', 2, new Stone(board, Color.BLUE));
//        placeNewPiece('b', 1, new Stone(board, Color.BLUE));
//        placeNewPiece('b', 3, new Stone(board, Color.BLUE));
//        placeNewPiece('c', 2, new Stone(board, Color.BLUE));
//        placeNewPiece('d', 1, new Stone(board, Color.BLUE));
//        placeNewPiece('d', 3, new Stone(board, Color.BLUE));
//        placeNewPiece('e', 2, new Stone(board, Color.BLUE));
//        placeNewPiece('f', 1, new Stone(board, Color.BLUE));
//        placeNewPiece('f', 3, new Stone(board, Color.BLUE));
//        placeNewPiece('g', 2, new Stone(board, Color.BLUE));
//        placeNewPiece('h', 1, new Stone(board, Color.BLUE));
//        placeNewPiece('h', 3, new Stone(board, Color.BLUE));
//
//        placeNewPiece('a', 8, new Stone(board, Color.RED));
//        placeNewPiece('a', 6, new Stone(board, Color.RED));
//        placeNewPiece('b', 7, new Stone(board, Color.RED));
//        placeNewPiece('c', 8, new Stone(board, Color.RED));
//        placeNewPiece('c', 6, new Stone(board, Color.RED));
//        placeNewPiece('d', 7, new Stone(board, Color.RED));
//        placeNewPiece('e', 8, new Stone(board, Color.RED));
//        placeNewPiece('e', 6, new Stone(board, Color.RED));
//        placeNewPiece('f', 7, new Stone(board, Color.RED));
//        placeNewPiece('g', 8, new Stone(board, Color.RED));
//        placeNewPiece('g', 6, new Stone(board, Color.RED));
//        placeNewPiece('h', 7, new Stone(board, Color.RED));
    }
}
