package Applications.Info;

import ToyORB.*;
import Registry.*;
import NamingServer.*;
import RequestReply.*;
import MessageMarshaller.*;

public class InfoServer {

    public static void main(String[] args) {

        final String SERVICE_NAME = "InfoService";
        final String HOST = "localhost";
        final int PORT = 2000;

        InfoImpl infoImpl = new InfoImpl();

        NamingServerHelper namingClient = new NamingServerHelper();
        boolean registered = namingClient.registerService(SERVICE_NAME, HOST, PORT);

        if (registered) {
            System.out.println("InfoService registered with NamingService");

            // Start server to handle requests
            Entry serverEntry = new Entry(HOST, PORT);
            Replyer replyer = new Replyer(SERVICE_NAME, serverEntry);
            Marshaller marshaller = new Marshaller();

            // Process client requests
            while (true) {
                replyer.receive_transform_and_send_feedback(new ByteStreamTransformer() {
                    @Override
                    public byte[] transform(byte[] in) {
                        try {
                            Message request = marshaller.unmarshal(in);
                            String[] parts = request.data.split(":", 2);
                            String method = parts[0];
                            String arg = parts.length > 1 ? parts[1] : "";
                            String result = "";

                            if (method.equals("get_temp")) {
                                result = infoImpl.get_temp(arg);
                            } else if (method.equals("get_road_info")) {
                                result = infoImpl.get_road_info(Integer.parseInt(arg));
                            } else {
                                result = "Unknown method: " + method;
                            }

                            return marshaller.marshal(new Message(SERVICE_NAME, result));
                        } catch (Exception e) {
                            return marshaller.marshal(new Message(SERVICE_NAME, "Error: " + e.getMessage()));
                        }
                    }
                });
            }
        } else {
            System.out.println("Failed to register with NamingService");
        }
    }
}
