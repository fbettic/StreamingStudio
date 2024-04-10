package ar.edu.ubp.rest.portal.models.clients;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

public class PlatformApiClientFactory {
    private final LinkedHashMap<String, AbstractPlatformApiClient> platformClients = new LinkedHashMap<>();
    private static PlatformApiClientFactory instance;

    private PlatformApiClientFactory() {

    }

    public static synchronized PlatformApiClientFactory getInstance() {
        if (instance == null) {
            instance = new PlatformApiClientFactory();
        }
        return instance;
    }

    public AbstractPlatformApiClient buildPlatformClient(String platformName, String serviceType, String url, boolean save)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
                
        if (this.platformClients.containsKey(platformName + serviceType)) {
            return this.platformClients.get(platformName + serviceType);
        }

        Class<?> dynamicClass;
        if ("SOAP".equals(serviceType)) {
            dynamicClass = Class.forName("ar.edu.ubp.rest.portal.models.clients.soap.PlatformSoapApiClient");
        } else if ("REST".equals(serviceType)) {
            dynamicClass = Class.forName("ar.edu.ubp.rest.portal.models.clients.rest.PlatformRestApiClient");
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> dynamicConstructor = dynamicClass.getDeclaredConstructor();
        AbstractPlatformApiClient dynamicClient = (AbstractPlatformApiClient) dynamicConstructor.newInstance();
        dynamicClient.setUrl(url);

        if(save){
            this.platformClients.put(platformName + serviceType, dynamicClient);
        }
        return dynamicClient;
    }
}
