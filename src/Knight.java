import java.util.Random;

public class Knight implements ChessPiece
{
    private int moves_count;

    Knight()
    {
        resetMovesCount();
    }

    public void resetMovesCount()
    {
        moves_count = 0;
    }

    public void increaseMovesCount()
    {
        moves_count++;
    }

    public ChessSquare[] getWalkPossibilities(ChessSquare from)
    {
        int knight_possibilities = 8;
        ChessSquare[] squares = new ChessSquare[knight_possibilities];

        squares[0] = new ChessSquare(from.pos_m+2, from.pos_n-1);
        squares[1] = new ChessSquare(from.pos_m+1, from.pos_n-2);

        squares[2] = new ChessSquare(from.pos_m-1, from.pos_n-2);
        squares[3] = new ChessSquare(from.pos_m-2, from.pos_n-1);

        squares[4] = new ChessSquare(from.pos_m-2, from.pos_n+1);
        squares[5] = new ChessSquare(from.pos_m-1, from.pos_n+2);

        squares[6] = new ChessSquare(from.pos_m+2, from.pos_n+1);
        squares[7] = new ChessSquare(from.pos_m+1, from.pos_n+2);

        return squares;
    }

}
