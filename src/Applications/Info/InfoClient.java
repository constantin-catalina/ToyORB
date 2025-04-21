package Applications.Info;

import ToyORB.*;
import Proxy.*;
import Registry.*;

public class InfoClient {

    public static void main(String[] args){

        // Info info = (Info)ToyORB.getObjectRef("InfoService");
        Info info = new InfoProxy("InfoService", new Entry("localhost", 2000));

        String city = "Brasov";
        String temp = info.get_temp(city);
        System.out.println("Temperature in " + city + " is: " + temp);
    }
}
