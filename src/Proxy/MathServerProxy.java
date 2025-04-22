package Proxy;

import Applications.Math.*;
import Applications.Math.Math;
import MessageMarshaller.*;

public class MathServerProxy implements ToyORB.ServerProxy {
    private Math implementation;
    private Marshaller marshaller;

    public MathServerProxy(Math implementation) {
        this.implementation = implementation;
        this.marshaller = new Marshaller();
    }

    @Override
    public byte[] processRequest(byte[] requestBytes) {
        try {
            Message request = marshaller.unmarshal(requestBytes);
            String[] parts = request.data.split(":", 3);
            String methodName = parts[0];
            String result;

            if (methodName.equals("do_add")) {
                String[] args = parts[1].split(",");
                float a = Float.parseFloat(args[0]);
                float b = Float.parseFloat(args[1]);
                result = String.valueOf(implementation.do_add(a, b));
            } else if (methodName.equals("do_sqrt")) {
                float a = Float.parseFloat(parts[1]);
                result = String.valueOf(implementation.do_sqrt(a));
            } else {
                result = "Error: Unknown method " + methodName;
            }

            return marshaller.marshal(new Message(request.sender, result));
        } catch (Exception e) {
            return marshaller.marshal(new Message("Error", e.getMessage()));
        }
    }
}