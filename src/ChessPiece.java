interface ChessPiece
{
    ChessSquare[] getWalkPossibilities(ChessSquare from);

    void resetMovesCount();
    void increaseMovesCount();
}
