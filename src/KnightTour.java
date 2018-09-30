import java.util.*;

public class KnightTour implements Iterable<ChessSquare>
{
    private int[][] board;
    private ChessPiece piece;

    /**
     * Comparator that puts positions with fewer exits first,
     * breaking ties in favor of smaller degree of freedom first.
     */
    private Comparator<ChessSquare> hardestChessSquaresFirstWithLookahead1 = new Comparator<ChessSquare>()
    {
        @Override
        public int compare(ChessSquare a, ChessSquare b)
        {
            int aPossibleMoves = KnightTour.this.possibleMoves(a).size();
            int bPossibleMoves = KnightTour.this.possibleMoves(b).size();

            if (aPossibleMoves != bPossibleMoves) {
                return aPossibleMoves - bPossibleMoves;
            }

            return KnightTour.this.getDegreesOfFreedom(a) - KnightTour.this.getDegreesOfFreedom(b);
        }
    };

    private final int[][] degreesOfFreedom;
    private Set<ChessSquare> path;

    /**
     * Knight's tour starting at random ChessSquare.
     */
    public KnightTour(int[][] board, ChessPiece piece)
    {
        this.board = board;
        this.piece = piece;

        this.degreesOfFreedom = initDegreesOfFreedom(this.piece, this.board.length);
        this.path = new LinkedHashSet<>(this.board.length*this.board.length);
    }

    public boolean movePiece(ChessSquare initial_pos)
    {
        if (this.solve(initial_pos)) {
            // Prevent Iterator.remove()
            this.path = Collections.unmodifiableSet(this.path);
            return true;
        }

        // There's no moves possible
        this.path.clear();
        return false;
    }

    @Override
    public Iterator<ChessSquare> iterator()
    {
        return this.path.iterator();
    }

    public int getSize()
    {
        return this.degreesOfFreedom.length;
    }

    private int getDegreesOfFreedom(ChessSquare p)
    {
        return this.degreesOfFreedom[p.getPosM()][p.getPosN()];
    }

    private static int[][] initDegreesOfFreedom(ChessPiece piece, int boardSize)
    {
        int[][] board = new int[boardSize][boardSize];

        ChessSquare aux_pos = new ChessSquare(0, 0);

        // Most squares allow full freedom of motion
        for (int[] row : board) {
            Arrays.fill(row, piece.getWalkPossibilities(aux_pos).length);
        }

        // Reduced freedom of motion in the two ranks and files near the edges
        for (int x = 0; x < board.length; x++) {
            for (int y : new int[] { 0, 1, board.length - 2, board.length - 1 }) {
                ChessSquare[] pos = {new ChessSquare(x, y)};
                board[x][y] = board[y][x] = possibleMoves(pos, boardSize, Collections.<ChessSquare>emptySet()).size();
            }
        }

        return board;
    }

    private boolean solve(ChessSquare p)
    {
        int step = 1;
        this.board[p.getPosM()][p.getPosN()] = step++;
        this.piece.increaseMovesCount();

        this.path.add(p);
        while (this.path.size() < this.getSize() * this.getSize()) {
            List<ChessSquare> possibleMoves = possibleMoves(p);
            if (possibleMoves.isEmpty()) {
                return false;
            }

            Collections.sort(possibleMoves, this.hardestChessSquaresFirstWithLookahead1);
            this.path.add(p = possibleMoves.get(0));
            this.board[p.getPosM()][p.getPosN()] = step++;
            this.piece.increaseMovesCount();
        }

        return true;
    }

    private List<ChessSquare> possibleMoves(ChessSquare position)
    {
        ChessSquare[] moves = piece.getWalkPossibilities(position);
        return possibleMoves(moves, this.getSize(), this.path);
    }

    private static List<ChessSquare> possibleMoves(ChessSquare[] moves, int boardSize, Set<ChessSquare> prohibited)
    {
        List<ChessSquare> result = new ArrayList<>(moves.length);

        for (ChessSquare posMove : moves)
        {
            int m = posMove.getPosM();
            int n = posMove.getPosN();

            if (m >= 0 && n >= 0 && m < boardSize && n < boardSize) {

                boolean found = false;
                for (ChessSquare aProhibited : prohibited) {
                    if (aProhibited.equals(posMove)) {
                        found = true;
                        break;
                    }
                }

                if(!found) result.add(posMove);
            }
        }

        return result;
    }
}