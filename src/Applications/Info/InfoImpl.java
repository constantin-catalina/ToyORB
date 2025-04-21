package Applications.Info;

public class InfoImpl implements Info {

    public InfoImpl() {}

    @Override
    public String get_road_info(int road_ID) {
        switch(road_ID) {
            case 101:
                return "Road 101: Under construction";
            case 202:
                return "Road 202: Clear traffic";
            case 303:
                return "Road 303: Heavy congestion";
            default:
                return "No information available for road" + road_ID;

        }
    }

    @Override
    public String get_temp(String city) {
        switch(city) {
            case "Cluj-Napoca":
                return "16°C";
            case "Bucharest":
                return "24°C";
            case "Brasov":
                return "14°C";
            case "Timisoara":
                return "20°C";
            default:
                return "No temperature data available for " + city;
        }
    }
}
