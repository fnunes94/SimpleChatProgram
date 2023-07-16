import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceive implements Runnable{

    private Socket clientSocket;
    private String inputText; //text received from server
    public ClientReceive(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while(true){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inputText = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(inputText);
        }
    }
}
