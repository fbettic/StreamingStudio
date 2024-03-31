package ar.edu.ubp.rest.portal.models.clients;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;


public class AdvertiserClientFactory {
    private final LinkedHashMap<String, AbstractAdvertiserClient> advertiserClients = new LinkedHashMap<>();
    private static AdvertiserClientFactory instance;

    private AdvertiserClientFactory() {

    }

    public static synchronized AdvertiserClientFactory getInstance() {
        if (instance == null) {
            instance = new AdvertiserClientFactory();
        }
        return instance;
    }

    public AbstractAdvertiserClient buildAdvertiserClient(String companyName, String serviceType, String url, boolean save)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
                
        if (this.advertiserClients.containsKey(companyName + serviceType)) {
            return this.advertiserClients.get(companyName + serviceType);
        }

        Class<?> dynamicClass;
        if ("SOAP".equals(serviceType)) {
            dynamicClass = Class.forName("ar.edu.ubp.rest.portal.models.clients.soap.AdvertiserSoapClient");
        } else if ("REST".equals(serviceType)) {
            dynamicClass = Class.forName("ar.edu.ubp.rest.portal.models.clients.rest.AdvertiserRestClient");
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> dynamicConstructor = dynamicClass.getDeclaredConstructor();
        AbstractAdvertiserClient dynamicClient = (AbstractAdvertiserClient) dynamicConstructor.newInstance();
        dynamicClient.setUrl(url);

        if(save){
            this.advertiserClients.put(companyName + serviceType, dynamicClient);
        }
        return dynamicClient;
    }

}
