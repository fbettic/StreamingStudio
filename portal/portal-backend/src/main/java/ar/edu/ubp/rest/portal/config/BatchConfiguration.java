package ar.edu.ubp.rest.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ar.edu.ubp.rest.portal.services.BatchService;

@Configuration
@EnableScheduling
public class BatchConfiguration {
    @Autowired
    private BatchService batchService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void executeBatchTask() throws Exception {
        // Llama a los métodos de actualización de películas y anuncios
        batchService.updateAdvertisings();
        batchService.updateFilms();
    }

}
