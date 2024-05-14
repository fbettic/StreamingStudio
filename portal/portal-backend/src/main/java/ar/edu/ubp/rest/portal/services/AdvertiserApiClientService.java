package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.BannerPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
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

    @Autowired
    private ReportService reportService;

    public AdvertiserApiClientService() {
        this.advertiserClientFactory = AdvertiserApiClientFactory.getInstance();
    }

    public String ping(String companyName, String serviceType, String url, ServicePayloadBean authToken)
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

        BannerPayloadBean payload = new BannerPayloadBean(advertiser.getAuthToken(), bannerId);

        BannerResponseBean banner = advertiserClient.getBannerById(payload);

        return banner;
    }

    public List<ServiceResponseMapperBean<AdvertisingResponseBean>> getAllAdvertisingsFromAdvertisers()
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

            ServicePayloadBean authToken = new ServicePayloadBean(advertiser.getAuthToken());

            List<AdvertisingResponseBean> advertisings = advertiserClient.getAllAdvertisings(authToken);

            if (!advertisings.isEmpty()) {

                advertisingsByAdvertisers.add(new ServiceResponseMapperBean<AdvertisingResponseBean>(
                        advertiser.getAdvertiserId(), advertisings));
            }

        });

        return advertisingsByAdvertisers;
    }

    public Map<Integer, String> sendWeeklyReport() throws Exception {
        List<AdvertiserDTO> advertisers = advertiserRepository.getAllAdvertisers();

        Map<Integer, String> response = new HashMap<Integer, String>();

        if (Objects.isNull(advertisers) || advertisers.size() == 0)
            throw new Exception("Failed to retrieve data from registered advertisers.");

        for (AdvertiserDTO advertiser : advertisers) {
            if (Objects.isNull(advertiser) || advertiser.getServiceType() == "ACCOUNT")
                continue;

            String result = "error";

            AbstractAdvertiserApiClient advertiserClient = null;

            try {
                advertiserClient = advertiserClientFactory.buildAdvertiserClient(advertiser.getCompanyName(),
                        advertiser.getServiceType(), advertiser.getApiUrl(), true);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                System.out.println(e);
                e.printStackTrace();
                continue;
            }

            if (Objects.isNull(advertiserClient)) {
                response.put(advertiser.getAdvertiserId(), result);
                continue;
            }

            WeeklyAdvertiserReportPayloadBean report = reportService.createWeeklyAdvertiserReport(
                    advertiser.getAdvertiserId(),
                    advertiser.getAuthToken());
            try {
                result = advertiserClient.sendWeeklyReport(report);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                continue;
            }

            if (Objects.nonNull(result) && result.equals("Success")) {
                response.put(advertiser.getAdvertiserId(), result);
            }
        }
        ;

        return response;
    }

}