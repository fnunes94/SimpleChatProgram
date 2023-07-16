import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect();
    }
}
