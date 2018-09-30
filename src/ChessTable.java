import java.util.ArrayList;
import java.util.Random;

class ChessTable
{
    private static final int TABLESIZE = 8;

    private static final ChessTable instance = new ChessTable();
    private int[][] table;
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    public int addPiece(ChessPiece piece)
    {
        pieces.add(piece);
        return pieces.size()-1;
    }

    // private constructor to avoid client applications to use constructor
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

    public static ChessTable getInstance()
    {
        return instance;
    }

    public ChessSquare choseRandomSquare()
    {
        ChessSquare square = new ChessSquare();

        Random rand = new Random();
        square.pos_m = rand.nextInt(ChessTable.TABLESIZE-1);
        square.pos_n = rand.nextInt(ChessTable.TABLESIZE-1);

        return square;
    }

    private boolean checkTableComplete()
    {
        for(int i = 0; i < TABLESIZE; i++) {
            for(int j = 0; j < TABLESIZE; j++) {
                if (table[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean movePiece(ChessPiece piece, ChessSquare pos, int step)
    {
        table[pos.pos_m][pos.pos_n] = step;

        ChessSquare[] walk_to_squares = piece.walkPossibilities(pos);

        for (ChessSquare walk_to_square: walk_to_squares) {

            if (checkTableComplete()) {
                return true;
            }

            if (walk_to_square.pos_m < 0 || walk_to_square.pos_n < 0 || walk_to_square.pos_m >= TABLESIZE || walk_to_square.pos_n >= TABLESIZE) {
                continue;
            }

            if (table[walk_to_square.pos_m][walk_to_square.pos_n] != 0) {
                continue;
            }

            if (movePiece(piece, walk_to_square, step+1)) {
                System.out.println(this.toString());
                return true;
            }

            table[pos.pos_m][pos.pos_n] = 0;
        }

        return false;
    }

    public void movePiece(int piece_id)
    {
        if(piece_id >= pieces.size()) {
            System.out.println("ERR: piece_id[" + piece_id + "] not in Chesstable.pieces range!");
            return;
        }

        int step = 1;
        ChessPiece piece = pieces.get(piece_id);
        ChessSquare initial_pos = choseRandomSquare();

        movePiece(piece, initial_pos, step);
    }

    public String toString()
    {
        StringBuilder chess_table = new StringBuilder();

        for(int i = 0; i < TABLESIZE; i++) {
            for(int j = 0; j < TABLESIZE; j++) {
                chess_table.append(Integer.toString(table[i][j])).append(", ");
            }
            chess_table.append("\n");
        }

        return chess_table.toString();
    }
}
