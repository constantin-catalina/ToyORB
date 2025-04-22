package ToyORB;

import Registry.Entry;

public interface ClientProxyFactory<T> {
    T createClientProxy(String serviceName, Entry serviceEntry);
}
