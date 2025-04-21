package ToyORB;

import Commons.Address;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import Applications.Info.*;
import RequestReply.Requestor;
import RequestReply.Replyer;
import RequestReply.ByteStreamTransformer;
import Registry.Entry;
import Registry.Registry;
import Proxy.*;

public class ToyORB {

    private static Registry registry = Registry.instance();
    private static Marshaller marshaller = new Marshaller();

    public static void register(String name, Object o) {
        int port = 2000;
        Entry entry = new Entry("localhost", port);
        registry.put(name, entry);

        new Thread(() -> {
            Replyer replyer = new Replyer(name, entry);
            while (true) {
                replyer.receive_transform_and_send_feedback(input -> {
                    Message msg = marshaller.unmarshal(input);
                    String response = invokeMethod(msg.data, o);
                    return marshaller.marshal(new Message(name, response));
                });
            }
        }).start();
    }

    public static Object getObjectRef(String name) {
        Entry entry = registry.get(name);
        if (entry == null) {
            System.out.println("Service with name " + name + " not found in registry.");
            return null;
        }
        return new InfoProxy(name, entry);
    }

    private static String invokeMethod(String request, Object impl) {
        try {
            String[] parts = request.split(":");
            String method = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";

            if (impl instanceof Applications.Info.Info) {
                Applications.Info.Info info = (Applications.Info.Info) impl;
                switch (method) {
                    case "get_temp":
                        return info.get_temp(arg);
                    case "get_road_info":
                        return info.get_road_info(Integer.parseInt(arg));
                }
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Unknown method.";
    }
}
