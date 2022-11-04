import java.io.BufferedReader;
import java.io.InputStreamReader;

class Controller extends Thread{
    private String msg;
    private boolean state;
    private Show show = new Show();

    public void run() {
        /*Menu*/
        TournamentLoop tournamentLoop = new TournamentLoop();
        GameLoop gameLoop = new GameLoop();
        state = true;
        show.showMenu();
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while (state) {
                if (stdIn.ready()) {
                    msg = stdIn.readLine();
                    if (!gameLoop.isAlive() && !tournamentLoop.isAlive()) {
                        switch (msg) {
                            case "1":
                                if (gameLoop.isAlive())
                                    System.out.println("Already playing vs computer");
                                else
                                    gameLoop.start();
                                break;
                            case "2":
                                    show.instructions();
                                break;
                            case "3":
                                if (tournamentLoop.isAlive())
                                    System.out.println("Already subscribed to tournament");
                                else
                                    tournamentLoop.start();
                                break;
                            case "9":
                                state = false;
                                break;
                            default:
                                System.err.println("Unrecognized option");
                        }
                    } else if (gameLoop.isAlive()) {
                        //pass stdIn.readline to gameLoop
                        switch (msg) {
                            case "EXIT": // IF stdin reads EXIT stop thread
                                gameLoop.setState();
                                sleep(100);
                                show.showMenu();
                                break;
                            default:
                                gameLoop.setIn(msg);
                                break;
                        }
                    } else if (tournamentLoop.isAlive()) {
                        switch (msg) {
                            case "EXIT": // IF stdin reads EXIT stop thread
                                tournamentLoop.setState();
                                sleep(100);
                                show.showMenu();
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            stdIn.close();
            System.out.println("closed the StdIn stream");
            System.out.println("Exiting the program");
        }catch (Exception e){
            System.out.println("Menu Thread Failed");
            System.out.println(e);
        }
    }
}