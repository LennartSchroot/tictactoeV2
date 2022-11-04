class TournamentLoop extends Thread {
    private boolean state;
    private String playerName = "Baguette";
    private Board board = new Board();
    private Ai ai = new Ai();
    private Show show = new Show();
    private Parse parser = new Parse(playerName);
    public void run() {
        ConnectThread communication = new ConnectThread();
        communication.start();
        try {
            sleep(100);
            if(communication.isAlive()){
                state = true;
            }else{
                System.out.println("Could not make connection\n"
                        +"Start server by opening cmd \n"
                        +"move to path of ~/tictactoeV2/server\n"
                        +"run server with: java -jar server.jar\n");
                state = false;
                show.showMenu();
            }
            while (state) {
                if (communication.isReady()) {
                    parser.parse(communication.getIncoming()); //parses input and does things accordingly
                }
                if(parser.getLoginReady()) {
                    communication.SetCommand(String.format("login %s", playerName));
                    parser.setLoginReady();
                }
                if(parser.getLoginOk()) {
                    show.tournamentSubscription(playerName);
                    parser.setLoginOK();
                }
                if(parser.moveFromServer()){
                    board.setAtPos(parser.getMove(), parser.getOpponentPiece());// put move in board// made by this player
                    show.boardToConsole(board);
                    parser.setMoveFromServer();
                }
                if(parser.getMyTurn()){
                    communication.SetCommand(String.format("move %d", ai.turn(board, parser.getMyPiece()))); // send move
                    System.out.println();
                    show.boardToConsole(board);
                    parser.setMyTurn(); // set myTurn to false
                }
                if(parser.getGameEnd()){
                    //display result
                    //clean up board
                    show.flush();
                    board.flush();
                    parser.setGameEnd(); //cleanup for parser
                }
                sleep(100);
            }
            if(communication.isAlive())
                 communication.setState();
        } catch(Exception e){
            System.out.printf("Tournament loop failed");
            System.out.println(e);
        }
    }
    public boolean gettState(){
        return state;
    }
    public void setState(){
        state = false;
    }
}