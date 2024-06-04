package core;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn() {
        this(Chess.PieceColor.WHITE);
    }

    public Pawn(Chess.PieceColor color) {
        super(color);
    }

    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            return "P";
        else
            return "p";
    }

    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {
        ArrayList<Position> result = new ArrayList<>();
        Chess.PieceColor myColor = chess.getPieceAt(p).getPieceColor();

        Position front = null, jump = null, left = null, right = null;

        if (myColor == Chess.PieceColor.WHITE) {
            //upward movements.
            front = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile());
            if (p.getRank() == Chess.WHITE_PAWN_STARTING_RANK)
                jump = Position.generateFromRankAndFile(Chess.WHITE_PAWN_STARTING_RANK - 2, p.getFile());
            left = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() - 1);
            right = Position.generateFromRankAndFile(p.getRank() - 1, p.getFile() + 1);
        } else {
            //downward movements.
            front = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile());
            if (p.getRank() == Chess.BLACK_PAWN_STARTING_RANK)
                jump = Position.generateFromRankAndFile(Chess.BLACK_PAWN_STARTING_RANK + 2, p.getFile());
            left = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() - 1);
            right = Position.generateFromRankAndFile(p.getRank() + 1, p.getFile() + 1);
        }

        if (front != null && chess.isEmpty(front))
            result.add(front);

        if (left != null && chess.getPieceAt(left) != null
                && (includeDefending || chess.getPieceAt(left).getPieceColor() != this.getPieceColor()))
            result.add(left);

        if (right != null && chess.getPieceAt(right) != null
                && (includeDefending || chess.getPieceAt(right).getPieceColor() != this.getPieceColor()))
            result.add(right);

        if (front != null && chess.isEmpty(front) && jump != null && chess.isEmpty(jump))
            result.add(jump);

        return result.toArray(new Position[]{});
    }
}
