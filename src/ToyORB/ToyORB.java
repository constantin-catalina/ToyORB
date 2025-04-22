package ToyORB;

import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import RequestReply.Replyer;
import RequestReply.ByteStreamTransformer;
import Registry.Entry;
import Registry.Registry;
import NamingServer.NamingServerHelper;

public class ToyORB {
    private static final Marshaller marshaller = new Marshaller();
    private static final NamingServerHelper namingClient = new NamingServerHelper();

    public static boolean registerService(String serviceName, Object impl, ServerProxyFactory serverProxyFactory) {
        String host = "localhost";
        int port = namingClient.requestPort();

        if (port == -1) {
            System.out.println("Failed to get a port from the naming service");
            return false;
        }

        boolean registered = namingClient.registerService(serviceName, host, port);

        if (registered) {
            final ServerProxy serverProxy = serverProxyFactory.createServerProxy(impl);

            new Thread(() -> {
                Entry serviceEntry = new Entry(host, port);
                Replyer replyer = new Replyer(serviceName, serviceEntry);

                System.out.println(serviceName + " started on " + host + ":" + port);

                while (true) {
                    replyer.receive_transform_and_send_feedback(new ByteStreamTransformer() {
                        @Override
                        public byte[] transform(byte[] in) {
                            return serverProxy.processRequest(in);
                        }
                    });
                }
            }).start();
            return true;
        }
        return false;
    }

    public static <T> T lookupService(String serviceName, ClientProxyFactory<T> clientProxyFactory) {
        Entry serviceEntry = namingClient.lookupService(serviceName);

        if (serviceEntry == null) {
            System.out.println("Service '" + serviceName + "' not found in NamingService");
            return null;
        }

        return clientProxyFactory.createClientProxy(serviceName, serviceEntry);
    }

}