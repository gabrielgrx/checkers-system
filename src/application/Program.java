package application;

import checkers.CheckersException;
import checkers.CheckersMatch;
import checkers.CheckersPiece;
import checkers.CheckersPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CheckersMatch checkersMatch = new CheckersMatch();
        List<CheckersPiece> captured = new ArrayList<>();

        while (!checkersMatch.isEndMatch()) {
            try {
                UI.clearScreen();
                UI.printMatch(checkersMatch, captured);
                System.out.println();
                System.out.print("Source: ");
                CheckersPosition source = UI.readCheckersPosition(sc);

                boolean[][] possibleMoves = checkersMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(checkersMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                CheckersPosition target = UI.readCheckersPosition(sc);

                CheckersPiece capturedPiece = checkersMatch.performCheckersMove(source, target);

                if(capturedPiece != null) {
                    captured.add(capturedPiece);
                }
            } catch (CheckersException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(checkersMatch, captured);
    }
}
