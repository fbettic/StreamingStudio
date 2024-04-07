package ar.edu.ubp.rest.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ar.edu.ubp.rest.portal.services.AdvertiserApiClientService;
import ar.edu.ubp.rest.portal.services.BatchService;
import ar.edu.ubp.rest.portal.services.PlatformApiClientService;

@Configuration
@EnableScheduling
public class BatchConfiguration {
    @Autowired
    private BatchService batchService;

    @Autowired
    private AdvertiserApiClientService advertiserApiClientService;

    @Autowired
    private PlatformApiClientService platformApiClientService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void executeBatchTask() throws Exception {
        // Llama a los métodos de actualización de películas y anuncios
        batchService.updateAdvertisings(advertiserApiClientService.getAllAdvertisingsFromAdvertisers());

        batchService.updateFilms(platformApiClientService.getAllFilmsFromPlatforms());
    }

}
