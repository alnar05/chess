package core;

import java.util.ArrayList;

public class King extends Piece {
    private boolean hasMoved;

    public King() {
        this(Chess.PieceColor.WHITE);
    }

    public King(Chess.PieceColor color) {
        this(color, false);
    }

    public King(Chess.PieceColor color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public String toString() {
        if (this.getPieceColor() == Chess.PieceColor.WHITE)
            if (this.hasMoved)
                return "L";
            else
                return "K";
        else
            if (this.hasMoved)
                return "l";
            else
                return "k";
    }

    private Position[] kingPattern(Chess chess, Position p, boolean includeDefending) {
        //An object of type King can access a private method of another King object.
        //The separation of patterns and accessible squares eliminates an infinite recursion
        //between kings, asking each other about their reachable squares.
        ArrayList<Position> result = new ArrayList<>();

        int[][] pattern = {
                {p.getRank() - 1, p.getFile() - 1},
                {p.getRank(),     p.getFile() - 1},
                {p.getRank() + 1, p.getFile() - 1},

                {p.getRank() - 1, p.getFile()},
                {p.getRank() + 1, p.getFile()},

                {p.getRank() - 1, p.getFile() + 1},
                {p.getRank(),     p.getFile() + 1},
                {p.getRank() + 1, p.getFile() + 1}
        };

        for (int i = 0; i < pattern.length; i++) {
            Position potential = Position.generateFromRankAndFile(pattern[i][0], pattern[i][1]);
            if (potential != null && (chess.isEmpty(potential) || includeDefending
                    || chess.getPieceAt(potential).getPieceColor()
                        != chess.getPieceAt(p).getPieceColor()))
                result.add(potential);
        }

        return result.toArray(new Position[]{});
    }

    public Position[] allDestinations(Chess chess, Position p, boolean includeDefending) {

        //This updated allDestinations method eliminates threatened squares from the reachable set.
        // Castling not implemented.

        ArrayList<Position> result = new ArrayList<>();
        ArrayList<Position> opponentReachable = new ArrayList<>();
        Position[] potentials = this.kingPattern(chess, p, false);


        Piece[][] board = chess.getBoard();
        for (int i = 0; i < Chess.BOARD_RANKS; i++)
            for (int j = 0; j < Chess.BOARD_FILES; j++)
                if (board[i][j] != null &&
                        board[i][j].getPieceColor() == chess.invertColor(this.getPieceColor())
                ) {
                    Position[] opponentPiecePattern;
                    if (board[i][j] instanceof King){
                        opponentPiecePattern = this.kingPattern(chess,
                                Position.generateFromRankAndFile(i, j), true);

                    } else {
                        opponentPiecePattern = board[i][j].allDestinations(chess,
                                Position.generateFromRankAndFile(i, j), true);
                    }
                    for (int k = 0; k < opponentPiecePattern.length; k++)
                        opponentReachable.add(opponentPiecePattern[k]);
                }

        if (!this.hasMoved) {
            Position path1, path2, path3, path4, kingLocation;

            if (this.getPieceColor() == Chess.PieceColor.WHITE) {
                path1 = Position.generateFromRankAndFile(7, 2);
                path2 = Position.generateFromRankAndFile(7, 3);
                kingLocation = Position.generateFromRankAndFile(7, 4);
                path3 = Position.generateFromRankAndFile(7, 5);
                path4 = Position.generateFromRankAndFile(7, 6);

                if (board[7][0] != null
                        && board[7][0] instanceof Rook
                        && !((Rook)board[7][0]).getHasMoved()
                        && !opponentReachable.contains(path1)
                        && chess.isEmpty(path1)
                        && !opponentReachable.contains(path2)
                        && chess.isEmpty(path2)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path1);

                if (board[7][7] != null
                        && board[7][7] instanceof Rook
                        && !((Rook)board[7][7]).getHasMoved()
                        && !opponentReachable.contains(path3)
                        && chess.isEmpty(path3)
                        && !opponentReachable.contains(path4)
                        && chess.isEmpty(path4)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path4);

            } else {
                path1 = Position.generateFromRankAndFile(0,2);
                path2 = Position.generateFromRankAndFile(0,3);
                kingLocation = Position.generateFromRankAndFile(0,4);
                path3 = Position.generateFromRankAndFile(0,5);
                path4 = Position.generateFromRankAndFile(0,6);

                if (board[0][0] != null
                        && board[0][0] instanceof Rook
                        && !((Rook)board[0][0]).getHasMoved()
                        && !opponentReachable.contains(path1)
                        && chess.isEmpty(path1)
                        && !opponentReachable.contains(path2)
                        && chess.isEmpty(path2)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path1);

                if (board[0][7] != null
                        && board[0][7] instanceof Rook
                        && !((Rook)board[0][7]).getHasMoved()
                        && !opponentReachable.contains(path3)
                        && chess.isEmpty(path3)
                        && !opponentReachable.contains(path4)
                        && chess.isEmpty(path4)
                        && !opponentReachable.contains(kingLocation)
                ) result.add(path4);
            }
        }

        for (Position potential : potentials)
            if (!opponentReachable.contains(potential))
                result.add(potential);


        return result.toArray(new Position[]{});
    }

}
