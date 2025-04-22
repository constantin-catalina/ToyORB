package Applications.Info;

import ToyORB.*;

public class InfoClient {
    public static void main(String[] args) {
        InfoProxyFactories.InfoClientProxyFactory clientProxyFactory =
                new InfoProxyFactories.InfoClientProxyFactory();

        Info info = ToyORB.lookupService("InfoService", clientProxyFactory);

        if (info == null) {
            System.out.println("InfoService not found");
            return;
        }

        String city = "Brasov";
        String temp = info.get_temp(city);
        System.out.println("Temperature in " + city + " is: " + temp);

        int roadId = 202;
        String roadInfo = info.get_road_info(roadId);
        System.out.println("Road information: " + roadInfo);
    }
}