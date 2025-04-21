import ToyORB.*;

import Applications.Info.*;

public class InfoClient {

    public static void main(String[] args){

        Info info = (Info)ToyORB.getObjectRef("info");

        String city = "Brasov";
        String temp = info.get_temp(city);
        System.out.println("Temperature in " + city + " is: " + temp);
    }
}
