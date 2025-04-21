package Applications.Info;

import NamingServer.NamingServerHelper;
import ToyORB.*;
import Proxy.*;
import Registry.*;

public class InfoClient {

    public static void main(String[] args){

        NamingServerHelper namingClient = new NamingServerHelper();
        Entry serviceEntry = namingClient.lookupService("InfoService");

        if (serviceEntry == null) {
            System.out.println("InfoService not found in NamingService");
            return;
        }

        Info info = new InfoProxy("InfoService", serviceEntry);

        String city = "Brasov";
        String temp = info.get_temp(city);
        System.out.println("Temperature in " + city + " is: " + temp);

        int roadId = 202;
        String roadInfo = info.get_road_info(roadId);
        System.out.println("Road information: " + roadInfo);
    }
}
