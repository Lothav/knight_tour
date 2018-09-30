import java.util.ArrayList;
import java.util.Random;

class ChessBoard
{
    private static final int BOARDSIZE = 8;

    private static final ChessBoard instance = new ChessBoard();
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    private int[][] board;

    // Private constructor to avoid client applications to use constructor
    private ChessBoard()
    {
        // Init chess board
        board = new int[BOARDSIZE][BOARDSIZE];
        for(int i = 0; i < BOARDSIZE; i++) {
            for(int j = 0; j < BOARDSIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    // Get private static instance.
    // Being private means that it cannot be modified from outside.
    public static ChessBoard getInstance()
    {
        return instance;
    }

    // Add a piece that implements ChessPiece interface.
    public int addPiece(ChessPiece piece)
    {
        pieces.add(piece);
        return pieces.size()-1;
    }

    // Choose a random square into chess board.
    public ChessSquare choseRandomSquare()
    {
        ChessSquare square = new ChessSquare();

        Random rand = new Random();
        square.setPosM(rand.nextInt(ChessBoard.BOARDSIZE-1));
        square.setPosN(rand.nextInt(ChessBoard.BOARDSIZE-1));

        return square;
    }

    // Check if board is complete. That means, every square != zero.
    private boolean checkBoardComplete()
    {
        for(int i = 0; i < BOARDSIZE; i++) {
            for(int j = 0; j < BOARDSIZE; j++) {
                // If there's a single square not taken, board is not complete!
                if (checkSquareNotTaken(new ChessSquare(i, j))) {
                    return false;
                }
            }
        }

        // There's no square not-taken, so, board is complete!
        return true;
    }

    private boolean checkSquareOutOfBoardBounds(ChessSquare square)
    {
        // Check square is NOT in board bounds.
        return square.getPosM() < 0 || square.getPosN() < 0 || square.getPosM() >= BOARDSIZE || square.getPosN() >= BOARDSIZE;
    }

    private boolean checkSquareNotTaken(ChessSquare square)
    {
        return board[square.getPosM()][square.getPosN()] == 0;
    }

    private boolean movePiece(ChessPiece piece, ChessSquare pos, int step)
    {
        // Every call of movePiece() must increment a piece movement count.
        piece.increaseMovesCount();

        // Set board matrix with the actual step.
        board[pos.getPosM()][pos.getPosN()] = step;

        // Get walkPossibilities() from selected piece.
        ChessSquare[] walk_to_squares = piece.getWalkPossibilities(pos);

        for (ChessSquare walk_to_square: walk_to_squares) {

            // If board is complete return true causing all recursion stack pops.
            if (checkBoardComplete()) {
                return true;
            }

            // Check bounds and square already taken. If fits, move to next square.
            if (checkSquareOutOfBoardBounds(walk_to_square) || !checkSquareNotTaken(walk_to_square)) {
                continue;
            }

            // Recursively move piece to next allowed square increasing the step.
            if (movePiece(piece, walk_to_square, step+1)) {
                return true;
            }
        }

        // This is not an viable path. Pop it out and choose another!
        board[pos.getPosM()][pos.getPosN()] = 0;
        return false;
    }

    /**
     * @param piece_id int
     * @return piece successful moved in all squares of the board
     */
    public boolean tryMovingPieceAllBoard(int piece_id)
    {
        if(piece_id >= pieces.size()) {
            System.out.println("ERR: piece_id[" + piece_id + "] not in Chessboard.pieces range!");
            return false;
        }

        ChessPiece piece = pieces.get(piece_id);
        ChessSquare initial_pos = choseRandomSquare();

        piece.resetMovesCount();

        if(piece instanceof Knight) {
            KnightTour knight_tour = new KnightTour(this.board, piece);
            return knight_tour.movePiece(initial_pos);
        }

        return movePiece(piece, initial_pos, 1);
    }

    /**
     * @return String The actual state of the chess board.
     */
    public String toString()
    {
        StringBuilder chess_board = new StringBuilder();

        for(int i = 0; i < BOARDSIZE; i++) {
            for(int j = 0; j < BOARDSIZE; j++) {
                if(board[i][j] < 10) {
                    chess_board.append(" ");
                }
                chess_board.append(Integer.toString(board[i][j]));
                if(j != BOARDSIZE-1) {
                    chess_board.append(", ");
                }
            }
            chess_board.append("\n");
        }

        return chess_board.toString();
    }
}
