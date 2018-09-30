import java.util.Random;

class ChessTable
{
    protected static final int TABLESIZE = 8;

    private static final ChessTable instance = new ChessTable();
    private int[][] table;

    //private constructor to avoid client applications to use constructor
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

    public int[] choseSquare()
    {
        int[] square = new int[2];

        Random rand = new Random();
        square[0] = rand.nextInt(ChessTable.TABLESIZE-1);
        square[1] = rand.nextInt(ChessTable.TABLESIZE-1);

        return square;
    }

    public static ChessTable getInstance()
    {
        return instance;
    }
}
