package ar.edu.ubp.rest.portal.services;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.AssociationReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.PlayRegisterPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;
import ar.edu.ubp.rest.portal.repositories.ReportRepository;

@Service
public class ReportService {

        @Autowired
        private ReportRepository reportRepository;

        public WeeklyPlatformReportPayloadBean createWeeklyPlatformReport(Integer platformId, String authToken) {
                LocalDate fromDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                LocalDate toDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

                WeeklyPlatformReportPayloadBean report = reportRepository.createWeeklyPlatformReport(platformId,
                                fromDate,
                                toDate);
                List<PlayRegisterPayloadBean> reproductions = reportRepository
                                .getPlayRegisterReportsByReportId(report.getReportId());

                List<AssociationReportPayloadBean> associations = reportRepository
                                .GetAssociationsByReportId(report.getReportId());

                return new WeeklyPlatformReportPayloadBean(authToken, report.getReportId(), report.getFromDate(),
                                report.getToDate(),
                                reproductions, associations);

        };
}