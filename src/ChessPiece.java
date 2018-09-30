interface ChessPiece
{
    ChessSquare[] getWalkPossibilities(ChessSquare from);

    int getMovesCount();
    void resetMovesCount();
    void increaseMovesCount();
}
