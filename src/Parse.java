public class Parse {
    private boolean loginReady;
    private boolean myTurn;
    private boolean opponentTurn;
    private boolean gameEnd;
    private boolean moveMade;
    private int move;
    private String moveByPLayer;
    private String gameType;
    private String opponent;
    private int myPiece;
    private int opponentPiece;
    private String playerName;
    private boolean checkLogin;
    private boolean loginOK;
    private boolean moveOkay;
    private boolean matchFound;
    public Parse(String name){
        loginReady = false;
        myTurn = false;
        opponentTurn = false;
        gameEnd = false;
        moveMade = false;
        loginOK = false;
        moveOkay = false;
        matchFound = false;
        playerName = name;
    }
    public void parse(String msg) {
        //remove certain chars
        msg = msg.replace("{", "");//remove the stupid curly braces
        msg = msg.replace("}", "");//remove the stupid curly braces
        msg = msg.replace(",", "");//lets remove those commas too
        msg = msg.replace("\"", "");//while were at it get rid of the accolades

        //split message on regex ' '
        String[] splitMsg = msg.split(" ");

        if(msg.equals("(C) Copyright 2021 Hanzehogeschool Groningen")) {
            checkLogin = true;
            loginReady = true;
        }
        if(checkLogin && splitMsg[0].equals("OK")) {
            loginOK = true;
            checkLogin = false;
        }
        //other code
        if(splitMsg[0].equals("ERR")){
            for(int i = 0; i < splitMsg.length; i++)
                System.out.printf("%s,", splitMsg[i]);
            System.out.println();
        }
        /*if(splitMsg[0].equals("OK")){ // no real reason for OK checker yet
            for(int i = 0; i < splitMsg.length; i++)
                System.out.printf("%s ", splitMsg[i]);
            System.out.println();
        }*/
        if(splitMsg[0].equals("move")){
            for(int i = 0; i < splitMsg.length; i++)
                System.out.printf("%s,", splitMsg[i]);
            System.out.println();
        }
        if(splitMsg[0].equals("SVR")) {
            if (splitMsg[1].equals("GAME")) {
                if (splitMsg[2].equals("MATCH")) {
                    matchFound = true;
                    if (splitMsg[3].equals("PLAYERTOMOVE:")) {
                        if (playerName.equals(splitMsg[4])){
                            myPiece = 1; //set to 1 for X
                            opponentPiece = 0;
                        } else {
                            myPiece = 0; //set to 0 for O
                            opponentPiece = 1;
                        }
                    }
                    if (splitMsg[5].equals("GAMETYPE:"))
                        gameType = splitMsg[6];
                    if (splitMsg[7].equals("OPPONENT:"))
                        opponent = splitMsg[8];
                }
                if (splitMsg[2].equals("YOURTURN")) {
                    myTurn = true;
                }
                if (splitMsg[2].equals("MOVE")) {
                    if (splitMsg[3].equals("PLAYER:")){
                        moveByPLayer = splitMsg[4];
                        if(moveByPLayer.equals(playerName)){
                            System.out.println("SERVER HAS ACCEPTED OUR MOVE");
                            moveOkay = true;
                        }else{
                            moveMade = true;
                        }
                    }
                    if (splitMsg[5].equals("MOVE:")) {
                        move = Integer.parseInt(splitMsg[6]);
                        System.out.printf("move: %s by: %s\n",splitMsg[6], moveByPLayer);
                    }
                    if (splitMsg[7].equals("DETAILS:"))
                        if (splitMsg.length > 7) {
                            //System.out.printf("%s,", splitMsg[8]);
                        }
                }
                if (splitMsg[2].equals("WIN")  || splitMsg[2].equals("LOSS") || splitMsg[2].equals("DRAW")) {
                    for(int i = 0; i < splitMsg.length; i++)
                        System.out.printf("%s,", splitMsg[i]);
                    gameEnd = true;
                }
            }
        }
    }
    public boolean getLoginReady(){
        return loginReady;
    }
    public void setLoginReady(){
        loginReady = false;
    }
    public boolean getMyTurn(){
        return myTurn;
    }
    public void setMyTurn(){
        myTurn = false;
    }
    public String getOpponent(){
        return opponent;
    }
    public String gameType(){
        return gameType;
    }
    public boolean getGameEnd(){
        return gameEnd;
    }
    public void setGameEnd(){
        gameEnd = false;
    }
    public boolean moveFromServer() {
        return moveMade;
    }
    public void setMoveFromServer() {
        moveMade = false;
    }
    public int getMove() {
        return move;
    }
    public int getMyPiece(){
        return myPiece;
    }
    public int getOpponentPiece(){
        return opponentPiece;
    }
    public boolean getLoginOk(){
        return loginOK;
    }
    public void setLoginOK(){
        loginOK = false;
    }
    public boolean getMoveOkay(){
        return moveOkay;
    }
    public void setMoveOKay(){
        moveOkay = false;
    }
    public boolean matchFound(){
        return matchFound;
    }
    public void setMatchFound(){
        matchFound = false;
    }
}
