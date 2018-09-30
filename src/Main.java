public class Main
{
    public static void main(String args[])
    {
        ChessTable game = ChessTable.getInstance();

        ChessPiece knight = new Knight();
        int piece_id = game.addPiece(knight);
        if (game.movePiece(piece_id)) {
            System.out.println("Chess Table for object [" + Integer.toString(piece_id) + "] movement:");
            System.out.println(game.toString());
        }
    }
}
