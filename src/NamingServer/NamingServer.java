package NamingServer;

import Registry.Registry;
import Registry.Entry;
import RequestReply.Replyer;
import RequestReply.ByteStreamTransformer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NamingServer {
    private static final String HOST = "localhost";
    private static final int PORT = 1900;
    private Registry registry;
    private Replyer replyer;
    private ExecutorService executor;

    public NamingServer() {
        registry = Registry.instance();
        Entry serviceEntry = new Entry(HOST, PORT);
        replyer = new Replyer("NamingService", serviceEntry);
        executor = Executors.newSingleThreadExecutor();
    }

    public void start() {
        System.out.println("NamingService started on " + HOST + ":" + PORT);

        executor.submit(() -> {
            while (true) {
                replyer.receive_transform_and_send_feedback(new NamingServerTransformer(registry));
            }
        });
    }

    public static void main(String[] args) {
        NamingServer namingService = new NamingServer();
        namingService.start();
    }
}
