package ui;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChessUI extends JFrame {
    private Chess game;
    private BoardSquare[][] buttonReferences;
    private ArrayList<Position> gatheredClicks;

    public ChessUI() {
        game = null;
        try {
            game = new Chess();
        } catch (Exception e) {}
        buttonReferences = new BoardSquare[Chess.BOARD_RANKS][Chess.BOARD_FILES];
        gatheredClicks = new ArrayList<>();

        this.setSize(650,650);
        this.setTitle("Chess");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        JPanel board = new JPanel(); //GUI board
        board.setLayout(new GridLayout( Chess.BOARD_RANKS, Chess.BOARD_RANKS )); //8x8
        //board.setPreferredSize(new Dimension(600,600));
        board.setSize(600,600);

        //populate board with squares
        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                BoardSquare sq = new BoardSquare(
                        (i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1), //color (black if false)
                        i, j //board coordinates
                );
                sq.setPreferredSize(new Dimension(75,75));

                sq.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BoardSquare source = (BoardSquare)e.getSource(); //the button that was clicked
                        int[] boardCoords = source.getSquareCoords();// board coordinates of that button
                        boardClicked(boardCoords);
                    }
                });

                buttonReferences[i][j] = sq;
                board.add(sq);
            }
        }

        this.add(board);
        updatePieces();
        this.setVisible(true);
    }

    private void boardClicked(int[] coords) {
        if (gatheredClicks.size() == 0) {
            Position p1 = Position.generateFromRankAndFile(coords[0], coords[1]);

            if (game.getPieceAt(p1) == null || game.getPieceAt(p1).getPieceColor() != game.getTurn())
                //ensure the player starts from their piece
                return;

            Position[] highlights = game.reachableFrom(p1);

            for (int i = 0; i < highlights.length; i++)
                buttonReferences[highlights[i].getRank()][highlights[i].getFile()].setHighlight(true);

            //gather the starting square
            gatheredClicks.add(p1);
        } else {
            Position p2 = Position.generateFromRankAndFile(coords[0], coords[1]);
            //gatheredClicks.add(coords); //destination of the move
            gatheredClicks.add(p2);

            //We have enough clicks to try to make a move
            //game.move(gatheredClicks.get(0),gatheredClicks.get(1));
            game.performMove(new Move(gatheredClicks.get(0),gatheredClicks.get(1)));

            //discard all clicks and refresh the board
            gatheredClicks.clear();
            updatePieces();

            for (int i = 0; i < buttonReferences.length; i++)
                for (int j = 0; j < buttonReferences[i].length; j++)
                    buttonReferences[i][j].setHighlight(false);

            if (game.getGameStatus() == Chess.GameStatus.WHITE_WON) {
                JOptionPane.showMessageDialog(this, "White is victorious");
                this.dispose();
            } else if (game.getGameStatus() == Chess.GameStatus.BLACK_WON) {
                JOptionPane.showMessageDialog(this, "Black is victorious");
                this.dispose();
            } else if (game.getGameStatus() == Chess.GameStatus.DRAW) {
                JOptionPane.showMessageDialog(this, "Draw");
                this.dispose();
            }
        }
    }

    private void updatePieces() {
        String s;
        Piece p;
        for (int i = 0; i < Chess.BOARD_RANKS; i++) {
            for (int j = 0; j < Chess.BOARD_FILES; j++) {
                p = game.getPieceAt(Position.generateFromRankAndFile(i, j));
                if (p != null)
                    buttonReferences[i][j].setPiece(p.toString()); //piece picture
                else
                    buttonReferences[i][j].setPiece(); //blank
            }
        }
    }
}
