public class Board {
    private int[] board = new int[9];

    public Board(){
        for(int i = 0; i < 9; i++){
            board[i] = 2;
        }
    }

    private boolean checkMove(int pos, int value){
        /*Some checks*/
        if(pos > 9 || pos < 0){
            //System.out.print("out of bounds pos: " + pos);
            return false;
        } else if (!(value == 0 || value == 1)) {
            //System.out.print("value incorrect: " + value);
            return false;
        }else if(!(this.board[pos] == 2)){
            //System.out.print("this position: " + pos +" is not empty: "  + this.board[pos]);
            return false;
        }
        return true;
    }
    public boolean setAtPos(int pos, int value){
        if(checkMove(pos, value)) {
            this.board[pos] = value;
            return true;
        }else{
            return false;
        }
    }

    public int getValueAt(int pos){
        return this.board[pos];
    }

    public boolean endGame(){
        for(int i = 0; i < 9; i++){
            if(board[i] == 2)
                return false;
        }
        return true;
    }
    public int checkWinner(){
        //check rows
        int i = 0;
        for(int y = 0; y < 7; y+=3){
            for(int x = 0; x < 3; x++) {
                if(this.board[y] == this.board[x + y])
                    i++;
            }
            if(i == 3)
                return this.board[y];
            i = 0;
        }
        //check columns
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 7; y+=3){
                if(this.board[x] == this.board[x + y])
                    i++;
            }
            if(i == 3)
                return this.board[x];
            i = 0;
        }
        //check diagonal
        for(int x = 0; x < 9; x+=4) {
            if(this.board[0] == this.board[x])
                    i++;
            if(i == 3)
                return this.board[0];
        }
        i = 0;
        //check anti-diagonal
        for(int x = 2; x < 7; x+=2) {
            if(this.board[2] == this.board[x])
                i++;
            if(i == 3)
                return this.board[2];
        }
        return 2;
    }
    public void flush(){
        for(int i = 0; i < 9; i++){
            board[i] = 2;
        }
    }
}
