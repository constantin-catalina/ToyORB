package ToyORB;

public interface ServerProxy {
    byte[] processRequest(byte[] requestBytes);
}