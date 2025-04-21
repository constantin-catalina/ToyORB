package Proxy;

import Applications.Info.*;
import Registry.Entry;
import RequestReply.Requestor;
import MessageMarshaller.*;

public class InfoProxy implements Info {

    private Entry entry;
    private Requestor requestor;
    private Marshaller marshaller;
    private String name;

    public InfoProxy(String name, Entry entry) {
        this.name = name;
        this.entry = entry;
        this.requestor = new Requestor(name);
        this.marshaller = new Marshaller();
    }

    public String get_temp(String city) {
        String req = "get_temp:" + city;
        byte[] request = marshaller.marshal(new Message(name, req));
        byte[] reply = requestor.deliver_and_wait_feedback(entry, request);
        Message resp = marshaller.unmarshal(reply);
        return resp.data;
    }

    public String get_road_info(int road_ID) {
        String req = "get_road_info:" + road_ID;
        byte[] request = marshaller.marshal(new Message(name, req));
        byte[] reply = requestor.deliver_and_wait_feedback(entry, request);
        Message resp = marshaller.unmarshal(reply);
        return resp.data;
    }
}
