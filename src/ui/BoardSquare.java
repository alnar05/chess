package ui;

import javax.swing.*;
import java.awt.*;

public class BoardSquare extends JButton {
    private static Color dark = Color.decode("#1B9700");
    private static Color light = Color.LIGHT_GRAY;
    private int boardX;
    private int boardY;
    private Color originalColor;

    public BoardSquare(boolean isWhite, int boardX, int boardY) {
        super();
        if (isWhite)
            originalColor = light;
            //this.setBackground(Color.LIGHT_GRAY);
        else
            originalColor = dark;

        this.setBackground(originalColor);
        this.boardX = boardX;
        this.boardY = boardY;
    }

    public int[] getSquareCoords() {
        return new int[]{boardX,boardY};
    }

    public void setPiece() {
        this.setIcon(null);
    }

    public void setPiece(String s) {
        switch (s) {
            case "K":
            case "L":
                this.setIcon(new ImageIcon("./gfx/KingW.png"));
                break;
            case "Q":
                this.setIcon(new ImageIcon("./gfx/QueenW.png"));
                break;
            case "R":
            case "S":
                this.setIcon(new ImageIcon("./gfx/RookW.png"));
                break;
            case "N":
                this.setIcon(new ImageIcon("./gfx/KnightW.png"));
                break;
            case "B":
                this.setIcon(new ImageIcon("./gfx/BishopW.png"));
                break;
            case "P":
                this.setIcon(new ImageIcon("./gfx/PawnW.png"));
                break;
            case "k":
            case "l":
                this.setIcon(new ImageIcon("./gfx/KingB.png"));
                break;
            case "q":
                this.setIcon(new ImageIcon("./gfx/QueenB.png"));
                break;
            case "r":
            case "s":
                this.setIcon(new ImageIcon("./gfx/RookB.png"));
                break;
            case "n":
                this.setIcon(new ImageIcon("./gfx/KnightB.png"));
                break;
            case "b":
                this.setIcon(new ImageIcon("./gfx/BishopB.png"));
                break;
            case "p":
                this.setIcon(new ImageIcon("./gfx/PawnB.png"));
                break;
        }
    }

    public void setHighlight(boolean highlighted) {
        if (highlighted)
            this.setBackground(Color.RED);
        else
            this.setBackground(originalColor);
    }
}
