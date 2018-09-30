public class Main
{
    public static void main(String args[])
    {
        ChessTable game = ChessTable.getInstance();
        ChessPiece knight = new Knight();
        int piece_id = game.addPiece(knight);
        game.movePiece(piece_id);
    }
}
