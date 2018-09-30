public class Main
{
    public static void main(String args[])
    {
        ChessBoard game = ChessBoard.getInstance();

        ChessPiece knight = new Knight();
        int piece_id = game.addPiece(knight);
        if (game.tryMovingPieceAllBoard(piece_id)) {
            System.out.println("Piece [" + Integer.toString(piece_id) + "] successful moved across all Chess Table:");
            System.out.println(game.toString());
        }
    }
}
