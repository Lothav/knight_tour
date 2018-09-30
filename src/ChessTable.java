import java.util.ArrayList;
import java.util.Random;

class ChessTable
{
    private static final int TABLESIZE = 8;

    private static final ChessTable instance = new ChessTable();
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    private int[][] table;

    // Private constructor to avoid client applications to use constructor
    private ChessTable()
    {
        // Init chess table
        table = new int[TABLESIZE][TABLESIZE];
        for(int i = 0; i < TABLESIZE; i++) {
            for(int j = 0; j < TABLESIZE; j++) {
                table[i][j] = 0;
            }
        }
    }

    // Get private static instance.
    // Being private means that it cannot be modified from outside.
    public static ChessTable getInstance()
    {
        return instance;
    }

    // Add a piece that implements ChessPiece interface.
    public int addPiece(ChessPiece piece)
    {
        pieces.add(piece);
        return pieces.size()-1;
    }

    // Choose a random square into chess table.
    public ChessSquare choseRandomSquare()
    {
        ChessSquare square = new ChessSquare();

        Random rand = new Random();
        square.pos_m = rand.nextInt(ChessTable.TABLESIZE-1);
        square.pos_n = rand.nextInt(ChessTable.TABLESIZE-1);

        return square;
    }

    // Check if table is complete. That means, every square != zero.
    private boolean checkTableComplete()
    {
        for(int i = 0; i < TABLESIZE; i++) {
            for(int j = 0; j < TABLESIZE; j++) {
                // If there's a single square not taken, table is not complete!
                if (checkSquareNotTaken(new ChessSquare(i, j))) {
                    return false;
                }
            }
        }

        // There's no square not-taken, so, table is complete!
        return true;
    }

    private boolean checkSquareOutOfTableBounds(ChessSquare square)
    {
        // Check square is NOT in table bounds.
        return square.pos_m < 0 || square.pos_n < 0 || square.pos_m >= TABLESIZE || square.pos_n >= TABLESIZE;
    }

    private boolean checkSquareNotTaken(ChessSquare square)
    {
        return table[square.pos_m][square.pos_n] == 0;
    }

    private boolean movePiece(ChessPiece piece, ChessSquare pos, int step)
    {
        piece.increaseMovesCount();

        table[pos.pos_m][pos.pos_n] = step;

        ChessSquare[] walk_to_squares = piece.walkPossibilities(pos);

        for (ChessSquare walk_to_square: walk_to_squares) {

            // If table is complete return true causing all recursion stack pops.
            if (checkTableComplete()) {
                return true;
            }

            // Check bounds and square already taken. If fits, move to next square.
            if (checkSquareOutOfTableBounds(walk_to_square) || !checkSquareNotTaken(walk_to_square)) {
                continue;
            }

            // Recursively move piece to next allowed square increasing the step.
            if (movePiece(piece, walk_to_square, step+1)) {
                return true;
            }

            table[pos.pos_m][pos.pos_n] = 0;
        }

        return false;
    }

    public boolean movePiece(int piece_id)
    {
        if(piece_id >= pieces.size()) {
            System.out.println("ERR: piece_id[" + piece_id + "] not in Chesstable.pieces range!");
            return false;
        }

        int step = 1;
        ChessPiece piece = pieces.get(piece_id);
        ChessSquare initial_pos = choseRandomSquare();

        piece.resetMovesCount();
        return movePiece(piece, initial_pos, step);
    }

    public String toString()
    {
        StringBuilder chess_table = new StringBuilder();

        for(int i = 0; i < TABLESIZE; i++) {
            for(int j = 0; j < TABLESIZE; j++) {
                if(table[i][j] < 10) {
                    chess_table.append(" ");
                }
                chess_table.append(Integer.toString(table[i][j]));
                if(j != TABLESIZE-1) {
                    chess_table.append(", ");
                }
            }
            chess_table.append("\n");
        }

        return chess_table.toString();
    }
}
