package Applications.Info;

import ToyORB.*;

public class InfoServer {

    public static void main(String[] args) {

        InfoImpl infoImpl = new InfoImpl();

        ToyORB.register("InfoService", infoImpl);
    }
}
