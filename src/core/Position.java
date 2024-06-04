package core;

public class Position {
    //mutable
    private int rank;
    private int file;

    public Position() {
        this.rank = 0;
        this.file = 0;
    }

    public Position(Position other) {
        this.setRank(other.rank);
        this.setFile(other.file);
    }

    private Position(int rank, int file) {
        this.setRank(rank);
        this.setFile(file);
    }

    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }

    public void setRank(int rank) {
        if (rank >= 0 && rank < Chess.BOARD_RANKS)
            this.rank = rank;
    }

    public void setFile(int file) {
        if (file >= 0 && file < Chess.BOARD_FILES)
            this.file = file;
    }

    public String toString() {
        return "" + (char)('A' + this.file) + (Chess.BOARD_RANKS - this.rank);
    }

    public static Position generateFromString(String s) {
        s = s.toLowerCase();
        if (s.length() != 2
                || (s.charAt(0) < 'a' || s.charAt(0) >= 'a' + Chess.BOARD_FILES)
                || (s.charAt(1) < '1' || s.charAt(1) >= '1' + Chess.BOARD_RANKS)
        )
            return null;
        return generateFromRankAndFile(Chess.BOARD_RANKS - s.charAt(1) + '0', s.charAt(0) - 'a');
    }

    public static Position generateFromRankAndFile(int rank, int file) {
        Position result = null;
        if (rank >= 0 && rank < Chess.BOARD_RANKS
                && file >= 0 && file < Chess.BOARD_FILES
        )
            result = new Position(rank, file);
        return result;
    }

    @Deprecated
    public static Position[] appendPositionsToArray(Position[] arr, Position... elements) {
        Position[] result = new Position[arr.length + elements.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        for (int i = 0; i < elements.length; i++)
            result[arr.length + i] = elements[i];

        return result;
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != Position.class)
            return false;
        Position otherPosition = (Position) other;
        return this.rank == otherPosition.rank && this.file == otherPosition.file;
    }
}
