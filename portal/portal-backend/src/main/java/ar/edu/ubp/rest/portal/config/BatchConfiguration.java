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

    // Ejecutar updateAdvertisings cada 24 horas
    @Scheduled(fixedRate = 86400000)
    public void updateAdvertisings() throws Exception {
        String response = batchService.updateAdvertisings();
        System.out.println("updateAdvertisings()-executed; Result: " + response);
    }

    // Ejecutar updateFilms cada 7 días
    @Scheduled(fixedRate = 604800000)
    public void updateFilms() throws Exception {
        String response = batchService.updateFilms();
        System.out.println("updateFilms()-executed; Result: " + response);
    }

    // Ejecutar sendWeeklyReport cada 7 días
    @Scheduled(fixedRate = 604800000)
    public void sendWeeklyReport() throws Exception {
        String response = batchService.sendWeeklyReport();
        System.out.println("sendWeeklyReport()-executed; Result: " + response);
    }

    // Ejecutar cancelAllOpenAssociationRequests cada 3 segundos
    @Scheduled(fixedRate = 86400000)
    public void cancelAllOpenAssociationRequests() {
        String response = batchService.cancelAllOpenAssociationRequests();
        System.out.println("cancelAllOpenAssociationRequests()-executed; Result: " + response);
    }

}
