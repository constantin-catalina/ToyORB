package Applications.Math;

import ToyORB.*;
import Proxy.MathClientProxy;
import Registry.Entry;

public class MathClient {
    public static void main(String[] args) {
        Math math = ToyORB.lookupService(
                "MathService",
                (serviceName, entry) -> new MathClientProxy(serviceName, entry)
        );

        if (math == null) {
            System.out.println("MathService not found");
            return;
        }

        float a = 10.5f;
        float b = 5.2f;
        float sum = math.do_add(a, b);
        System.out.println(a + " + " + b + " = " + sum);

        float c = 16.0f;
        float sqrt = math.do_sqrt(c);
        System.out.println("sqrt(" + c + ") = " + sqrt);
    }
}