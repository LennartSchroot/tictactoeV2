import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;

class ConnectThread extends Thread{
    private boolean state;
    private Queue<String> incoming = new ArrayDeque<>();
    private  Queue<String> command = new ArrayDeque<>();;

    public void run(){
        state = true;
        try{
            Socket socket = new Socket("localhost", 7789);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (state) {
                if (in.ready()) {
                    incoming.offer(in.readLine());
                }
                if (!command.isEmpty()) {
                    out.println(String.format("%s", command.poll()));
                }
                sleep(100);
            }
            socket.close();
            System.out.println("closed the socket");
            in.close();
            System.out.println("closed the input stream");
            out.close();
            System.out.println("closed the output stream");
            System.out.printf("Exiting to main menu\n\n");
        }catch(Exception e){
            System.out.println("ConnectThread failed");
        }
    }
    public boolean isReady(){
        if(incoming.isEmpty())
            return false;
        else
            return true;
    }
    public String getIncoming(){
        return incoming.poll();
    }
    public void SetCommand(String command){
        this.command.offer(command);
    }
    public void setState(){
        state = false;
    }
}