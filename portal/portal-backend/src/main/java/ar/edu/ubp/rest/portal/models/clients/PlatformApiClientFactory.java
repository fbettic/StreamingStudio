package ar.edu.ubp.rest.portal.models.clients;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import ar.edu.ubp.rest.portal.enums.ServiceType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlatformApiClientFactory {
    private static final String SOAP_CLASS = "ar.edu.ubp.rest.portal.models.clients.soap.PlatformSoapApiClient";
    private static final String REST_CLASS = "ar.edu.ubp.rest.portal.models.clients.rest.PlatformRestApiClient";

    private final LinkedHashMap<String, AbstractPlatformApiClient> platformClients = new LinkedHashMap<>();
    private static PlatformApiClientFactory instance;

    public static synchronized PlatformApiClientFactory getInstance() {
        if (instance == null) {
            instance = new PlatformApiClientFactory();
        }
        return instance;
    }

    public AbstractPlatformApiClient buildPlatformClient(String platformName, String serviceType, String url,
            boolean save)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {

        if (this.platformClients.containsKey(platformName + serviceType)) {
            return this.platformClients.get(platformName + serviceType);
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
        AbstractPlatformApiClient dynamicClient = (AbstractPlatformApiClient) dynamicConstructor.newInstance();
        dynamicClient.setUrl(url);

        if (save) {
            this.platformClients.put(platformName + serviceType, dynamicClient);
        }
        return dynamicClient;
    }
}
