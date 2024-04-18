package ar.edu.ubp.rest.portal.models.clients;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;


public class AdvertiserApiClientFactory {
    private final LinkedHashMap<String, AbstractAdvertiserApiClient> advertiserClients = new LinkedHashMap<>();
    private static AdvertiserApiClientFactory instance;

    private AdvertiserApiClientFactory() {

    }

    public static synchronized AdvertiserApiClientFactory getInstance() {
        if (instance == null) {
            instance = new AdvertiserApiClientFactory();
        }
        return instance;
    }

    public AbstractAdvertiserApiClient buildAdvertiserClient(String companyName, String serviceType, String url, boolean save)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
                
        if (this.advertiserClients.containsKey(companyName + serviceType)) {
            return this.advertiserClients.get(companyName + serviceType);
        }

        Class<?> dynamicClass;
        if ("SOAP".equals(serviceType)) {
            dynamicClass = Class.forName("ar.edu.ubp.rest.portal.models.clients.soap.AdvertiserSoapApiClient");
        } else if ("REST".equals(serviceType)) {
            dynamicClass = Class.forName("ar.edu.ubp.rest.portal.models.clients.rest.AdvertiserRestApiClient");
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> dynamicConstructor = dynamicClass.getDeclaredConstructor();
        AbstractAdvertiserApiClient dynamicClient = (AbstractAdvertiserApiClient) dynamicConstructor.newInstance();
        dynamicClient.setUrl(url);

        if(save){
            this.advertiserClients.put(companyName + serviceType, dynamicClient);
        }
        return dynamicClient;
    }

}
