public class Main
{
    public static void main(String args[])
    {
        long start = System.currentTimeMillis();

        //ChessBoard game = ChessBoard.getInstance();
        //ChessPiece knight = new Knight();
        //int piece_id = game.addPiece(knight);

        KnightTour knight_tour = new KnightTour();
        knight_tour.solve();

       //if (game.tryMovingPieceAllBoard(piece_id)) {
       //    System.out.println("Piece [" + Integer.toString(piece_id) + "] successful moved across all Chess Board:");
       //    System.out.println(game.toString());
       //} else {
       //    System.out.println("It was not possible move the Knight through all the board squares.");
       //    System.out.println("Must select another start square!");
       //}
        System.out.println(knight_tour.toString());

        System.out.println("Took: " + Long.toString((System.currentTimeMillis() - start)) + "ms.");
    }
}
