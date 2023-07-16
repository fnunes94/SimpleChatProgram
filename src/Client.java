import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private String hostname; //IP
    private int serverPort;
    private String serverPortText;
    private String outputText; //text being sent to server

    private boolean closed = false;
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter out;

    public void connect() throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        /*System.out.println("Insert Server address:");
        hostname = br.readLine();
        System.out.println("Insert Server port:");
        serverPortText = br.readLine();
        serverPort = Integer.parseInt(serverPortText);*/

        clientSocket = new Socket("localhost", 6000);
        System.out.println("Connected to server with\nAddress: " + hostname + "\nPort: " + serverPort + "\n");

        Thread receive = new Thread(new ClientReceive(clientSocket));
        receive.start();

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        while (!closed) {
            outputText = br.readLine();
            out.println(outputText);
            if (outputText.equals("/quit")) {
                System.out.println("\nDisconnected from server");
                break;
            }
        }
    }
}
