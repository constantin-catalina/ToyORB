import ToyORB.*;

import Applications.Info.*;

public class InfoServer {

    public static void main(String[] args) {

        InfoImpl infoImpl = new InfoImpl();

        ToyORB.register("info", infoImpl);
    }
}
