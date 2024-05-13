package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.time.LocalDate;
import java.util.List;

import ar.edu.ubp.rest.portal.beans.request.AssociationReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.PlayRegisterPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;

public interface IReportRepository {
    public WeeklyPlatformReportPayloadBean createWeeklyPlatformReport(Integer platformId, LocalDate fromDate,
            LocalDate toDate);

    public List<PlayRegisterPayloadBean> getPlayRegisterReportsByReportId(Integer reportId);

    public List<AssociationReportPayloadBean> GetAssociationsByReportId(Integer reportId);
}
