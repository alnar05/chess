package core;

public abstract class Piece implements Cloneable {
    private Chess.PieceColor color;

    public Piece(Chess.PieceColor color) {
        this.color = color;
    }

    public Piece() {
        this(Chess.PieceColor.WHITE);
    }

    public abstract Position[] allDestinations(Chess chess, Position p, boolean includeDefending);

    public final Chess.PieceColor getPieceColor() {
        return this.color;
    }

    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
