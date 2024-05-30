package ar.edu.ubp.rest.anunciantesrest.repositories.interfaces;

import ar.edu.ubp.rest.anunciantesrest.beans.BasicResponseBean;

public interface IReportRepository {
    public BasicResponseBean createWeekleyReport(String reportJson);
}
