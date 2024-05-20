package ar.edu.ubp.rest.portal.models.clients;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import ar.edu.ubp.rest.portal.enums.ServiceType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdvertiserApiClientFactory {
    private static final String SOAP_CLASS = "ar.edu.ubp.rest.portal.models.clients.soap.AdvertiserSoapApiClient";
    private static final String REST_CLASS = "ar.edu.ubp.rest.portal.models.clients.rest.AdvertiserRestApiClient";

    private final LinkedHashMap<String, AbstractAdvertiserApiClient> advertiserClients = new LinkedHashMap<>();
    private static AdvertiserApiClientFactory instance;

    public static synchronized AdvertiserApiClientFactory getInstance() {
        if (instance == null) {
            instance = new AdvertiserApiClientFactory();
        }
        return instance;
    }

    public AbstractAdvertiserApiClient buildAdvertiserClient(String companyName, String serviceType, String url,
            boolean save)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {

        if (this.advertiserClients.containsKey(companyName + serviceType)) {
            return this.advertiserClients.get(companyName + serviceType);
        }

        Class<?> dynamicClass;
        if (ServiceType.SOAP.name().equals(serviceType)) {
            dynamicClass = Class.forName(SOAP_CLASS);
        } else if (ServiceType.REST.name().equals(serviceType)) {
            dynamicClass = Class.forName(REST_CLASS);
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> dynamicConstructor = dynamicClass.getDeclaredConstructor();
        AbstractAdvertiserApiClient dynamicClient = (AbstractAdvertiserApiClient) dynamicConstructor.newInstance();
        dynamicClient.setUrl(url);

        if (save) {
            this.advertiserClients.put(companyName + serviceType, dynamicClient);
        }
        return dynamicClient;
    }

}
