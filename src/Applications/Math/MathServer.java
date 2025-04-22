package Applications.Math;

import ToyORB.*;
import Proxy.MathServerProxy;

public class MathServer {
    private static final String SERVICE_NAME = "MathService";

    public static void main(String[] args) {
        MathImpl mathImpl = new MathImpl();

        boolean registered = ToyORB.registerService(
                SERVICE_NAME,
                mathImpl,
                impl -> new MathServerProxy((Math) impl)
        );

        if (registered) {
            System.out.println("MathService registered successfully");
        } else {
            System.out.println("Failed to register MathService");
        }
    }
}