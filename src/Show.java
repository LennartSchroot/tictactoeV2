public class Show {
    char charBoard[] = new char[9];
    public Show(){
        for(int i = 0; i < 9; i++){
            this.charBoard[i] = ' ';
        }
    }

    public void boardToConsole(Board board){
        for(int i = 0; i < 9; i++){
            if(board.getValueAt(i) == 0)
                this.charBoard[i] = 'O';
            else if (board.getValueAt(i) == 1) {
                this.charBoard[i] = 'X';
            }
        }
        for(int i = 0; i < 9; i++){
            System.out.print(charBoard[i]);
            if((i+1)%3 == 0)
                System.out.println();
            else
                System.out.print("|");
        }
    }

    public void showMenu(){
        String msg ="While in game or tournament mode write EXIT and enter to go back to main menu"
                    + "To play a game of Eieren, kaas & boter vs the ai. Press 1 and enter.\n"
                    + "For instructions on how to play. Press 2 and enter.\n"
                    + "To subscribe to tournament press 3.\n"
                    + "To Exit this program. Press 9 and enter.\n";
        System.out.printf("%s", msg);
    }
    public void tournamentSubscription(String name){
        System.out.printf("Logged in with name: %s\n\n", name);
    }
    public void instructions(){
        boolean state = true;
        System.out.print("Welcome to Eieren, kaas & boter.\n" +
                "To play enter first the position and press enter.\n" +
                "The positions are as follows.\n");
        char instruction[] = {'0','1','2','3','4','5','6','7','8'};
        for(int i = 0; i < 9; i++){
            System.out.print(instruction[i]);
            if((i+1)%3 == 0)
                System.out.println();
            else
                System.out.print("|");
        }
        clearConsole();
    }
    public void victory(){
        System.out.print("A win!\n");
        flush();
    }
    public void draw(){
        System.out.print("A draw.\n");
        flush();
    }
    public void loss(){
        System.out.print("A loss.\n");
        flush();
    }
    public void playerTurn(){
        System.out.print("It is your turn. Please input the position.\n");
    }
    public void aiTurn(){
        System.out.print("The computer made a move.\n");
    }

    public void clearConsole()
    {
        //this is where I would clear the console if I knew how to do that...
    }
    public void flush(){
        for(int i = 0; i < 9; i++){
            this.charBoard[i] = ' ';
        }
    }
}
