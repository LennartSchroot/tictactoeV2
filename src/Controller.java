import java.io.BufferedReader;
import java.io.InputStreamReader;

class Controller extends Thread{
    private GameLoop gameLoop;
    private TournamentLoop tournamentLoop;
    private String msg;
    private boolean state;
    private Show show = new Show();
    private int status;

    public void run() {
        /*Menu*/
        state = true;
        show.showMenu();
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while (state) {
                if (stdIn.ready()) {
                    msg = stdIn.readLine();
                    if (status == 0) {
                        switch (msg) {
                            case "1":
                                gameLoop = new GameLoop();
                                gameLoop.start();
                                status = 1;
                                break;
                            case "2":
                                show.instructions();
                                break;
                            case "3":
                                tournamentLoop = new TournamentLoop();
                                tournamentLoop.start();
                                sleep(200);
                                if(tournamentLoop.gettState())
                                    status = 3;
                                break;
                            case "EXIT":
                                state = false;
                                break;
                            default:
                                System.err.println("Unrecognized option");
                        }
                    } else if (status == 1) {
                        //pass stdIn.readline to gameLoop
                        switch (msg) {
                            case "EXIT": // IF stdin reads EXIT stop thread
                                gameLoop.setState();
                                sleep(100);
                                show.showMenu();
                                status = 0;
                                break;
                            default:
                                gameLoop.setIn(msg);
                                break;
                        }
                    } else if (status == 3) {
                        switch (msg) {
                            case "EXIT": // IF stdin reads EXIT stop thread
                                tournamentLoop.setState();
                                sleep(100);
                                show.showMenu();
                                status = 0;
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