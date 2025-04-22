package Proxy;

import Applications.Math.Math;
import Registry.Entry;
import RequestReply.Requestor;
import MessageMarshaller.*;

public class MathClientProxy implements Math {
    private Entry entry;
    private Requestor requestor;
    private Marshaller marshaller;
    private String name;

    public MathClientProxy(String name, Entry entry) {
        this.name = name;
        this.entry = entry;
        this.requestor = new Requestor(name);
        this.marshaller = new Marshaller();
    }

    @Override
    public float do_add(float a, float b) {
        String req = "do_add:" + a + "," + b;
        byte[] request = marshaller.marshal(new Message(name, req));
        byte[] reply = requestor.deliver_and_wait_feedback(entry, request);
        Message resp = marshaller.unmarshal(reply);
        return Float.parseFloat(resp.data);
    }

    @Override
    public float do_sqrt(float a) {
        String req = "do_sqrt:" + a;
        byte[] request = marshaller.marshal(new Message(name, req));
        byte[] reply = requestor.deliver_and_wait_feedback(entry, request);
        Message resp = marshaller.unmarshal(reply);
        return Float.parseFloat(resp.data);
    }
}