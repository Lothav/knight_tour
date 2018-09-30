import java.util.*;

public class KnightTour implements Iterable<ChessSquare>
{
    private static final int[][] KNIGHT_MOVES = {
            { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
            { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
    };

    private static final int BOARDSIZE = 8;

    private int[][] board;

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
     * Knight's tour for an BOARDSIZExBOARDSIZE board.
     */
    public KnightTour()
    {
        this(BOARDSIZE);
    }

    /**
     * Knight's tour starting at random ChessSquare.
     */
    public KnightTour(int boardSize)
    {
        board = new int[BOARDSIZE][BOARDSIZE];
        for(int i = 0; i < BOARDSIZE; i++) {
            for(int j = 0; j < BOARDSIZE; j++) {
                board[i][j] = 0;
            }
        }

        this.degreesOfFreedom = initDegreesOfFreedom(boardSize);
        this.path = new LinkedHashSet<>(boardSize * boardSize);
    }

    public void solve()
    {
        ChessSquare initial_pos = choseRandomSquare();

        if (this.solve(initial_pos)) {
            // Prevent Iterator.remove()
            this.path = Collections.unmodifiableSet(this.path);
            return;
        }

        // There's no moves possible
        this.path.clear();
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

    private static int[][] initDegreesOfFreedom(int boardSize)
    {
        int[][] board = new int[boardSize][boardSize];
        // Most squares allow full freedom of motion
        for (int[] row : board) {
            Arrays.fill(row, KNIGHT_MOVES.length);
        }
        // Reduced freedom of motion in the two ranks and files near the edges
        for (int x = 0; x < board.length; x++) {
            for (int y : new int[] { 0, 1, board.length - 2, board.length - 1 }) {
                board[x][y] = board[y][x] =
                        possibleMoves(new ChessSquare(x, y), boardSize, Collections.<ChessSquare>emptySet()).size();
            }
        }

        return board;
    }

    // Choose a random square into chess board.
    public ChessSquare choseRandomSquare()
    {
        ChessSquare square = new ChessSquare();

        Random rand = new Random();
        square.setPosM(rand.nextInt(BOARDSIZE-1));
        square.setPosN(rand.nextInt(BOARDSIZE-1));

        return square;
    }

    private boolean solve(ChessSquare p)
    {
        int step = 1;
        this.board[p.getPosM()][p.getPosN()] = step++;

        this.path.add(p);
        while (this.path.size() < this.getSize() * this.getSize()) {
            List<ChessSquare> possibleMoves = possibleMoves(p);
            if (possibleMoves.isEmpty()) {
                return false;
            }

            Collections.sort(possibleMoves, this.hardestChessSquaresFirstWithLookahead1);
            this.path.add(p = possibleMoves.get(0));
            this.board[p.getPosM()][p.getPosN()] = step++;
        }

        return true;
    }

    private List<ChessSquare> possibleMoves(ChessSquare position)
    {
        return possibleMoves(position, this.getSize(), this.path);
    }

    private static List<ChessSquare> possibleMoves(ChessSquare position, int boardSize, Set<ChessSquare> prohibited)
    {
        List<ChessSquare> result = new ArrayList<>(KNIGHT_MOVES.length);

        for (int[] posMove : KNIGHT_MOVES)
        {
            int x = position.getPosM() + posMove[0];
            int y = position.getPosN() + posMove[1];

            if (x >= 0 && y >= 0 && x < boardSize && y < boardSize) {

                ChessSquare possiblePos = new ChessSquare(x, y);

                boolean found = false;
                for (ChessSquare aProhibited : prohibited) {
                    if (aProhibited.equals(possiblePos)) {
                        found = true;
                        break;
                    }
                }

                if(!found) result.add(possiblePos);
            }
        }

        return result;
    }

    /**
     * @return String The actual state of the chess board.
     */
    public String toString()
    {
        StringBuilder chess_board = new StringBuilder();

        for(int i = 0; i < BOARDSIZE; i++) {
            for(int j = 0; j < BOARDSIZE; j++) {
                if(board[i][j] < 10) {
                    chess_board.append(" ");
                }
                chess_board.append(Integer.toString(board[i][j]));
                if(j != BOARDSIZE-1) {
                    chess_board.append(", ");
                }
            }
            chess_board.append("\n");
        }

        return chess_board.toString();
    }
}