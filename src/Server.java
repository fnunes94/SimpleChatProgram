import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final List<ServerWorker> serverWorkersList = new ArrayList<>();
    private final int portNumber = 6000;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void init() throws IOException {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (true){

                System.out.println("Ready for new client");
                clientSocket = serverSocket.accept(); //blocks, until new connection
                System.out.println("Client connected");
                 //saves clientSocket in list
                ServerWorker serverWorker = new ServerWorker(clientSocket, this);
                Thread serverWorkerThread = new Thread(serverWorker);
                serverWorkerThread.start(); //starts new thread
                System.out.println("New serverWorker thread started");
                serverWorkersList.add(serverWorker);
                System.out.println("Client socket saved in list");

            }
    }

    public void sendAll(String inputText, String name) throws IOException {
        for (ServerWorker serverWorker: serverWorkersList) {
            if (serverWorker.getName().equals(name)){
                continue;
            }
            out = new PrintWriter(serverWorker.getClientSocket().getOutputStream(), true);
            out.println(name + ": " + inputText);
        }
    }
}

    class ServerWorker implements Runnable {

        private PrintWriter out;
        private BufferedReader in;
        private String inputText = null;
        private String name;
        private Server server;
        private Socket clientSocket;

        public ServerWorker(Socket clientSocket , Server server) {
            this.clientSocket = clientSocket;
            this.server = server;
        }

        @Override
        public void run() {
            try {
                receive();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        public String getName() {
            return name;
        }

        public void receive() throws IOException {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Please insert you name: ");
            name = in.readLine();
            out.println("Your name is now set to \"" + name + "\"." );
            System.out.println("Ready to receive \"" + name + "\" messages");

            while (true) {
                inputText = in.readLine(); //blocks, until message is received
                /*if (!clientSocket.isConnected())
                    break;*/
                System.out.println(name + ": " + inputText);
                if (!inputText.equals(" ")) {
                    send(inputText, name);
                }
            }
        }

        public void send(String inputText, String name) throws IOException {
            server.sendAll(inputText, name);
        }

        public Socket getClientSocket(){
            return clientSocket;
        }
    }

