public class Ai {

    private static final int MAX_DEPTH = 12;
    private static int xoxo = 2;
    private static int enemy = 2;
    

    public int turn(Board board, int naughtOrCross) {
        xoxo = naughtOrCross;
        if (xoxo == 0) { enemy = 1; }
        else { enemy = 0;}
        int move = getBestMove(board);
        board.setAtPos(move, xoxo);
        return move;
    }

    public static int miniMax(Board board, int depth, int alpha, int beta,
            boolean isMax) {

        int boardVal = evaluateBoard(board, depth);

        // Terminal node (win/lose/draw) or max depth reached.
        if (Math.abs(boardVal) > 0 || depth == 0) {
            return boardVal;
        }

        // Maximising player, find the maximum attainable value.
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int spot = 0; spot < 9; spot++) {
                if (board.filledIn(spot)) {
                    board.setAtPos(spot, xoxo);
                    highestVal = Math.max(highestVal, miniMax(board,
                            depth - 1, alpha, beta, false));
                    board.delMove(spot);
                    alpha = Math.max(alpha, highestVal);
                    if (alpha >= beta) {
                        return highestVal;
                    }
                }
            }
            return highestVal;

        // Minimising player, find the minimum attainable value;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int spot = 0; spot < 9; spot++) {
                if (board.filledIn(spot)) {
                    board.setAtPos(spot, enemy);
                    lowestVal = Math.min(lowestVal, miniMax(board,
                            depth - 1, alpha, beta, true));
                    board.delMove(spot);
                    beta = Math.min(beta, lowestVal);
                    if (beta <= alpha) {
                        return lowestVal;
                    }
                }
            }
            return lowestVal;
        }
    }

    public static int getBestMove(Board board) {
        int bestMove = 0;
        int bestValue = Integer.MIN_VALUE;

        for (int spot = 0; spot < 9; spot++) {
            if (board.filledIn(spot)) {
                board.setAtPos(spot, xoxo);
                int moveValue = miniMax(board, MAX_DEPTH, Integer.MIN_VALUE,
                        Integer.MAX_VALUE, false);
                board.delMove(spot);
                if (moveValue > bestValue) {
                    bestMove = spot;
                    bestValue = moveValue;
                }
            }
        }
        return bestMove;
    }

    private static int evaluateBoard(Board board, int depth) {
        int checkW = board.checkWinner();

        if (checkW == 0) {
            if (xoxo == 0)
                return 10 + depth;
            if (enemy == 0)
                return -10 - depth;
            board.flush();
        } else if (checkW == 1) {
            if (xoxo == 0)
                return -10 - depth;
            if (enemy == 0)
                return 10 + depth;
        }
        return 0;
    }
}
