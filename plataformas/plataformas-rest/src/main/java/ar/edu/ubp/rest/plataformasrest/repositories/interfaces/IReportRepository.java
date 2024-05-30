package ar.edu.ubp.rest.plataformasrest.repositories.interfaces;

import ar.edu.ubp.rest.plataformasrest.beans.BasicResponseBean;

public interface IReportRepository {
    public BasicResponseBean createWeekleyReport(String reportJson);
}
