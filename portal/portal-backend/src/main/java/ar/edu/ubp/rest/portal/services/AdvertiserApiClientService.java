package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.beans.response.ServiceResponseMapperBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserApiClient;
import ar.edu.ubp.rest.portal.models.clients.AdvertiserApiClientFactory;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;

@Service
public class AdvertiserApiClientService {

    private AdvertiserApiClientFactory advertiserClientFactory;

    @Autowired
    private AdvertiserRepository advertiserRepository;

    public AdvertiserApiClientService() {
        this.advertiserClientFactory = AdvertiserApiClientFactory.getInstance();
    }

    public String ping(String companyName, String serviceType, String url, BasicPayloadBean authToken)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        AbstractAdvertiserApiClient advertiserClient = advertiserClientFactory.buildAdvertiserClient(companyName,
                serviceType, url, false);
        return advertiserClient.ping(authToken);
    }

    public BannerResponseBean getBannerById(Integer bannerId, Integer advertiserId) {
        AdvertiserDTO advertiser = advertiserRepository.getAdvertiserById(advertiserId);

        if (Objects.isNull(advertiser) || advertiser.getServiceType() == "ACCOUNT")
                return null;

        AbstractAdvertiserApiClient advertiserClient = null;
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

        
        BasicPayloadBean authToken = new BasicPayloadBean(advertiser.getAuthToken());

        BannerResponseBean banner = advertiserClient.getBannerById(bannerId,authToken);

        return banner;
    }

    public List<ServiceResponseMapperBean<AdvertisingResponseBean> > getAllAdvertisingsFromAdvertisers()
            throws Exception {
        List<AdvertiserDTO> advertisers = advertiserRepository.getAllAdvertisers();

        List<ServiceResponseMapperBean<AdvertisingResponseBean>> advertisingsByAdvertisers = new ArrayList<>();

        advertisers.forEach((advertiser) -> {
            if (advertiser.getServiceType() == "ACCOUNT")
                return;

            AbstractAdvertiserApiClient advertiserClient = null;
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

            BasicPayloadBean authToken = new BasicPayloadBean(advertiser.getAuthToken());

            List<AdvertisingResponseBean> advertisings = advertiserClient.getAllAdvertisings(authToken);

            if (!advertisings.isEmpty()) {

                advertisingsByAdvertisers.add(new ServiceResponseMapperBean<AdvertisingResponseBean>(advertiser.getId(), advertisings));
            }

        });

        return advertisingsByAdvertisers;
    }

}