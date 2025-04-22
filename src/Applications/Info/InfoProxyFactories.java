package Applications.Info;

import ToyORB.ServerProxyFactory;
import ToyORB.ClientProxyFactory;
import ToyORB.ServerProxy;
import Proxy.InfoClientProxy;
import Proxy.InfoServerProxy;
import Registry.Entry;
import Applications.Info.Info;

public class InfoProxyFactories {

    public static class InfoServerProxyFactory implements ServerProxyFactory {
        @Override
        public ServerProxy createServerProxy(Object impl) {
            if (impl == null) {
                throw new IllegalArgumentException("Provided implementation cannot be null");
            }
            if (!(impl instanceof Info)) {
                throw new IllegalArgumentException("Provided implementation does not implement Info");
            }
            return new InfoServerProxy((Info) impl);

        }
    }

    public static class InfoClientProxyFactory implements ClientProxyFactory<Info> {
        @Override
        public Info createClientProxy(String serviceName, Entry serviceEntry) {
            return new InfoClientProxy(serviceName, serviceEntry);
        }
    }
}