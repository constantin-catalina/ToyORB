package NamingServer;

import Registry.Registry;
import Registry.Entry;
import RequestReply.ByteStreamTransformer;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import java.util.concurrent.atomic.AtomicInteger;

public class NamingServerTransformer implements ByteStreamTransformer {
    private Registry registry;
    private Marshaller marshaller;
    private static final AtomicInteger nextAvailablePort = new AtomicInteger(2000);

    public NamingServerTransformer(Registry registry) {
        this.registry = registry;
        this.marshaller = new Marshaller();
    }

    @Override
    public byte[] transform(byte[] in) {
        Message request = marshaller.unmarshal(in);
        String response = "Error: Unknown operation";

        try {
            if (request.data.equals("requestPort")) {
                // Allocate a new port
                int port = nextAvailablePort.getAndIncrement();
                response = Integer.toString(port);
                System.out.println("Allocated port " + port);
            } else {
                String[] parts = request.data.split(":", 3);
                String operation = parts[0];

                if (operation.equals("register")) {
                    String serviceName = parts[1];
                    String[] addressParts = parts[2].split(",");
                    String host = addressParts[0];
                    int port = Integer.parseInt(addressParts[1]);

                    Entry entry = new Entry(host, port);
                    registry.put(serviceName, entry);
                    response = "Service registered: " + serviceName;
                } else if (operation.equals("lookup")) {
                    String serviceName = parts[1];
                    Entry entry = registry.get(serviceName);

                    if (entry != null) {
                        response = entry.dest() + "," + entry.port();
                    } else {
                        response = "Error: Service not found";
                    }
                }
            }
        } catch (Exception e) {
            response = "Error: " + e.getMessage();
        }

        Message responseMsg = new Message("NamingService", response);
        return marshaller.marshal(responseMsg);
    }
}