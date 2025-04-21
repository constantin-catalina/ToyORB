package NamingServer;

import Registry.Registry;
import Registry.Entry;
import RequestReply.ByteStreamTransformer;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;

public class NamingServerTransformer implements ByteStreamTransformer {
    private Registry registry;
    private Marshaller marshaller;

    public NamingServerTransformer(Registry registry) {
        this.registry = registry;
        this.marshaller = new Marshaller();
    }

    @Override
    public byte[] transform(byte[] in) {
        Message request = marshaller.unmarshal(in);
        String[] parts = request.data.split(":", 3);
        String operation = parts[0];
        String response = "Error: Unknown operation";

        try {
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
        } catch (Exception e) {
            response = "Error: " + e.getMessage();
        }

        Message responseMsg = new Message("NamingService", response);
        return marshaller.marshal(responseMsg);
    }
}