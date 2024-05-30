package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.AdvertisingPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
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

    private AbstractAdvertiserApiClient getAdvertiserClient(AdvertiserDTO advertiser) {
        try {
            return advertiserClientFactory.buildAdvertiserClient(
                    advertiser.getCompanyName(), advertiser.getServiceType(), advertiser.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException("Error creating AdvertiserApiClient", e);
        }
    }

    public String ping(String companyName, String serviceType, String url, ServicePayloadBean authToken) {
        try {
            AbstractAdvertiserApiClient advertiserClient = advertiserClientFactory.buildAdvertiserClient(companyName,
                    serviceType, url, false);
            return advertiserClient.ping(authToken).getResponse();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException("Error during ping", e);
        }
    }

    public AdvertisingResponseBean getAdvertisingData(Integer referenceId, Integer advertiserId) {
        AdvertiserDTO advertiser = advertiserRepository.getAdvertiserById(advertiserId);

        if (Objects.isNull(advertiser) || advertiser.getServiceType() == "ACCOUNT")
            throw new NoSuchElementException("Failed to retrieve data from registered advertisers.");

        try {
            AbstractAdvertiserApiClient advertiserClient = getAdvertiserClient(advertiser);
            AdvertisingPayloadBean payload = new AdvertisingPayloadBean(advertiser.getAuthToken(), referenceId);
            return advertiserClient.getAdvertisingById(payload);
        } catch (Exception e) {
            System.err
                    .println("Error processing advertiser " + advertiser.getCompanyName() + ": " + e.getMessage());
            return null;
        }

    }

    public List<ServiceResponseMapperBean<AdvertisingResponseBean>> getAllAdvertisingsFromAdvertisers()
            throws Exception {
        List<AdvertiserDTO> advertisers = advertiserRepository.getAllAdvertisers();
        if (Objects.isNull(advertisers) || advertisers.isEmpty()) {
            throw new NoSuchElementException("Failed to retrieve data from registered advertisers.");
        }

        List<ServiceResponseMapperBean<AdvertisingResponseBean>> advertisingsByAdvertisers = new ArrayList<>();

        advertisers.forEach((advertiser) -> {
            if (Objects.nonNull(advertiser) && advertiser.getServiceType() != "ACCOUNT") {
                try {
                    AbstractAdvertiserApiClient advertiserClient = getAdvertiserClient(advertiser);
                    ServicePayloadBean authToken = new ServicePayloadBean(advertiser.getAuthToken());
                    List<AdvertisingResponseBean> advertisings = advertiserClient.getAllAdvertisings(authToken);
                    if (!advertisings.isEmpty()) {

                        advertisingsByAdvertisers.add(new ServiceResponseMapperBean<AdvertisingResponseBean>(
                                advertiser.getAdvertiserId(), advertisings));
                    }
                } catch (Exception e) {
                    System.err
                            .println("Error processing advertiser " + advertiser.getCompanyName() + ": "
                                    + e.getMessage());
                }
            }

        });

        return advertisingsByAdvertisers;
    }

    public Map<Integer, String> sendWeeklyReport(LocalDate fromDate, LocalDate toDate) throws Exception {
        List<AdvertiserDTO> advertisers = advertiserRepository.getAllAdvertisers();

        if (Objects.isNull(advertisers) || advertisers.isEmpty()) {
            throw new NoSuchElementException("Failed to retrieve data from registered advertisers.");
        }

        Map<Integer, String> response = new HashMap<Integer, String>();

        advertisers.forEach((advertiser) -> {

            if (Objects.nonNull(advertiser) && !advertiser.getServiceType().equals("ACCOUNT")) {
                try {
                    AbstractAdvertiserApiClient advertiserClient = getAdvertiserClient(advertiser);

                    WeeklyAdvertiserReportPayloadBean report = reportService.createWeeklyAdvertiserReport(
                            advertiser.getAdvertiserId(),
                            advertiser.getAuthToken(),
                            fromDate,
                            toDate);
                    String result = advertiserClient.sendWeeklyReport(report).getResponse();
                    response.put(advertiser.getAdvertiserId(), result);
                } catch (Exception e) {
                    System.err
                            .println("Error sending weekly report for advertiser " + advertiser.getCompanyName() + ": "
                                    + e.getMessage());
                    response.put(advertiser.getAdvertiserId(), "error");
                }
            }

        });

        return response;
    }

}