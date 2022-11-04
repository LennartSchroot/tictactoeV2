import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

class GameLoop extends Thread {
    private Board board = new Board();
    private Show show = new Show();
    private Ai ai = new Ai();
    private int currentPlayer;
    private int playerPiece;
    private int computerPiece;
    private int checkW;
    private boolean state;
    private Queue<String> incoming = new ArrayDeque<>();

    public void run() {
        boolean showInfo = true;
        state = true;
        try{
            checkW = 2;
            whoGoesFirst();
            while(state){
                /*who is playing and board*/
                if(showInfo) {
                    if(currentPlayer == 0)
                        show.playerTurn();
                    if(currentPlayer == 1)
                        show.aiTurn();
                    showInfo = false;
                }
                if (currentPlayer == 0) {
                    if (makeMove()) {
                        currentPlayer = 1;
                        showInfo = true;
                        show.boardToConsole(board);
                    }
                } else {
                    ai.turn(board, computerPiece);
                    show.boardToConsole(board);
                    currentPlayer = 0;
                    showInfo = true;
                }
                /*check for Wins or EndGame*/
                checkW = board.checkWinner();
                if (checkW == 0) {
                    if (playerPiece == 0)
                        show.victory();
                    if (computerPiece == 0)
                        show.loss();
                    board.flush();
                } else if (checkW == 1) {
                    if (playerPiece == 0)
                        show.loss();
                    if (computerPiece == 0)
                        show.victory();
                    board.flush();
                } else {
                    if (board.endGame()) {
                        show.draw();
                        board.flush();
                    }
                }
                sleep(100);
            }
            System.out.printf("Exiting to main menu\n\n");
        }catch (Exception e){
            System.out.println("Game Loop Failed");
            System.out.println(e);
        }
    }
    private void whoGoesFirst() {
        /*Randomly assigns 0 or 1 to var rn, which then decides value for the player and computer.*/
        Random random = new Random();
        int rn = random.nextInt(2);
        currentPlayer = rn;
        if (rn == 1) {
            playerPiece = 0;
            computerPiece = 1;
        } else {
            playerPiece = 1;
            computerPiece = 0;
        }
    }
    private boolean makeMove() {
        /*take a position from console, asks board if this is a valid move.
         * if not valid return false so the loop asks for a valid move again*/
        if(incoming.isEmpty()) {
            return false;
        }else{
            char[] chars = incoming.poll().toCharArray();
            for(char c : chars){
                if(Character.isDigit(c)){
                    //System.out.printf("char to int: %d\n", Integer.parseInt(String.valueOf(c)));
                    if (board.setAtPos(Integer.parseInt(String.valueOf(c)), playerPiece))
                        return true;
                }
            }
        }
        return false;
    }
    public void setIn(String in){
        incoming.offer(in);
    }
    public void setState(){
        state = false;
    }
}