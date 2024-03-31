package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.portal.beans.BannerResponseBean;
import ar.edu.ubp.rest.portal.beans.ClientAdvertisingsBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserClient;
import ar.edu.ubp.rest.portal.models.clients.AdvertiserClientFactory;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;

@Service
public class AdvertiserClientService {

    private AdvertiserClientFactory advertiserClientFactory;

    @Autowired
    private AdvertiserRepository advertiserRepository;

    public AdvertiserClientService() {
        this.advertiserClientFactory = AdvertiserClientFactory.getInstance();
    }

    public String pingAdvertiser(String companyName, String serviceType, String url, AuthTokenRequestBean authToken)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        AbstractAdvertiserClient advertiserClient = advertiserClientFactory.buildAdvertiserClient(companyName,
                serviceType, url, false);
        return advertiserClient.ping(authToken);
    }

    public BannerResponseBean getBannerById(Integer bannerId, Integer advertiserId) {
        AdvertiserDTO advertiser = advertiserRepository.getAdvertiserById(advertiserId);

        if (Objects.isNull(advertiser) || advertiser.getServiceType() == "ACCOUNT")
                return null;

        AbstractAdvertiserClient advertiserClient = null;
        try {
            advertiserClient = advertiserClientFactory.buildAdvertiserClient(
                    advertiser.getCompanyName(), advertiser.getServiceType(), advertiser.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        if (Objects.isNull(advertiserClient))
            return null;

        
        AuthTokenRequestBean authToken = new AuthTokenRequestBean(advertiser.getAuthToken());

        BannerResponseBean banner = advertiserClient.getBannerById(bannerId,authToken);

        return banner;
    }

    public List<ClientAdvertisingsBean> getAllAdvertisingsFromClients()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        List<AdvertiserDTO> advertisers = advertiserRepository.getAllAdvertisers();

        System.out.println("---------->" + advertisers.toString());

        // List<AbstractAdvertiserClient> advertiserClientList = new ArrayList<>();

        List<ClientAdvertisingsBean> clientAdvertisings = new ArrayList<>();

        advertisers.forEach((advertiser) -> {
            if (advertiser.getServiceType() == "ACCOUNT")
                return;

            AbstractAdvertiserClient advertiserClient = null;
            try {
                advertiserClient = advertiserClientFactory.buildAdvertiserClient(
                        advertiser.getCompanyName(), advertiser.getServiceType(), advertiser.getApiUrl(), true);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                System.out.println(e);
                e.printStackTrace();
            }

            if (Objects.isNull(advertiserClient))
                return;

            AuthTokenRequestBean authToken = new AuthTokenRequestBean(advertiser.getAuthToken());

            List<AdvertisingResponseBean> advertisings = advertiserClient.getAllAdvertisings(authToken);

            if (!advertisings.isEmpty()) {
                clientAdvertisings.add(new ClientAdvertisingsBean(advertiser.getId(), advertisings));
            }

        });

        return clientAdvertisings;
    }

}