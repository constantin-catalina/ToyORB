package NamingServer;

import Registry.Entry;
import RequestReply.Requestor;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;

public class NamingServerHelper {
    private static final String NAMING_SERVICE_HOST = "localhost";
    private static final int NAMING_SERVICE_PORT = 1900;
    private static final Entry NAMING_SERVICE_ENTRY = new Entry(NAMING_SERVICE_HOST, NAMING_SERVICE_PORT);

    private Requestor requestor;
    private Marshaller marshaller;

    public NamingServerHelper() {
        this.requestor = new Requestor("NamingServiceClient");
        this.marshaller = new Marshaller();
    }

    public boolean registerService(String serviceName, String host, int port) {
        String request = "register:" + serviceName + ":" + host + "," + port;
        Message message = new Message("Client", request);
        byte[] requestBytes = marshaller.marshal(message);

        byte[] responseBytes = requestor.deliver_and_wait_feedback(NAMING_SERVICE_ENTRY, requestBytes);
        Message response = marshaller.unmarshal(responseBytes);

        System.out.println("Registration response: " + response.data);
        return !response.data.startsWith("Error");
    }

    public Entry lookupService(String serviceName) {
        String request = "lookup:" + serviceName;
        Message message = new Message("Client", request);
        byte[] requestBytes = marshaller.marshal(message);

        byte[] responseBytes = requestor.deliver_and_wait_feedback(NAMING_SERVICE_ENTRY, requestBytes);
        Message response = marshaller.unmarshal(responseBytes);

        if (response.data.startsWith("Error")) {
            System.out.println("Lookup failed: " + response.data);
            return null;
        }

        String[] parts = response.data.split(",");
        String host = parts[0];
        int port = Integer.parseInt(parts[1]);

        return new Entry(host, port);
    }
}