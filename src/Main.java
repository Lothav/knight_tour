public class Main
{
    public static void main(String args[])
    {
        long start = System.currentTimeMillis();

        ChessBoard game = ChessBoard.getInstance();
        ChessPiece knight = new Knight();
        int piece_id = game.addPiece(knight);

        if (game.tryMovingPieceAllBoard(piece_id)) {
            System.out.println(
                "\nPiece [" + Integer.toString(piece_id) + "] "+
                " of type '"+ knight.getClass().getName() +
                "' successful moved across all Chess Board:");
            System.out.println(game.toString());
        } else {
            System.out.println(
                "It was not possible move the Knight through "+
                "all the board squares.");
            System.out.println("Must select another start square!");
        }

        System.out.println(
            "Piece move " +
            Integer.toString(knight.getMovesCount()) +
            " times.\n");

        System.out.println(
            "Took: " +
            Long.toString((System.currentTimeMillis() - start)) +
            "ms.");
    }
}
