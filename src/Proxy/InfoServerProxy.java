package Proxy;

import Applications.Info.*;
import MessageMarshaller.*;
import ToyORB.ServerProxy;

public class InfoServerProxy implements ServerProxy{
    private Info implementation;
    private Marshaller marshaller;

    public InfoServerProxy(Info implementation) {
        this.implementation = implementation;
        this.marshaller = new Marshaller();
    }

    public byte[] processRequest(byte[] requestBytes) {
        try {
            Message request = marshaller.unmarshal(requestBytes);
            String[] parts = request.data.split(":", 2);
            String methodName = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";
            String result;

            if (methodName.equals("get_temp")) {
                result = implementation.get_temp(arg);
            } else if (methodName.equals("get_road_info")) {
                result = implementation.get_road_info(Integer.parseInt(arg));
            } else {
                result = "Error: Unknown method " + methodName;
            }

            return marshaller.marshal(new Message(request.sender, result));

        } catch (Exception e) {
            return marshaller.marshal(new Message("Error", e.getMessage()));
        }
    }
}
