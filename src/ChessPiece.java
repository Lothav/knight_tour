interface ChessPiece
{
    ChessSquare[] walkPossibilities(ChessSquare from);

    void resetMovesCount();
    void increaseMovesCount();
}
