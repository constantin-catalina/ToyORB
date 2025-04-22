package Applications.Math;

import Applications.Info.Info;
import ToyORB.*;
import Proxy.MathClientProxy;
import Proxy.MathServerProxy;
import Registry.Entry;

public class MathProxyFactories {

    public static class MathServerProxyFactory implements ServerProxyFactory {
        @Override
        public ServerProxy createServerProxy(Object impl) {
            if (impl == null) {
                throw new IllegalArgumentException("Provided implementation cannot be null");
            }
            if (!(impl instanceof Math)) {
                throw new IllegalArgumentException("Provided implementation does not implement Info");
            }
            return new MathServerProxy((Math) impl);
        }
    }

    public static class MathClientProxyFactory implements ClientProxyFactory<Math> {
        @Override
        public Math createClientProxy(String serviceName, Entry serviceEntry) {
            return new MathClientProxy(serviceName, serviceEntry);
        }
    }
}