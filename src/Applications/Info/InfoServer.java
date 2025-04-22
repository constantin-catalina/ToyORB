package Applications.Info;

import ToyORB.*;
import Proxy.InfoServerProxy;

public class InfoServer {
    private static final String SERVICE_NAME = "InfoService";

    public static void main(String[] args) {
        InfoImpl infoImpl = new InfoImpl();

        boolean registered = ToyORB.registerService(
                SERVICE_NAME,
                infoImpl,
                impl -> new InfoServerProxy((Info) impl)
        );

        if (registered) {
            System.out.println("InfoService registered successfully");
        } else {
            System.out.println("Failed to register InfoService");
        }
    }
}