package ar.edu.ubp.rest.portal.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.beans.request.AdvertisingClickReportBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.PlayRegisterPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;
import ar.edu.ubp.rest.portal.repositories.interfaces.IReportRepository;

@Repository
public class ReportRepository implements IReportRepository {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        @SuppressWarnings("unchecked")
        @Override
        public WeeklyPlatformReportPayloadBean createWeeklyPlatformReport(Integer platformId, LocalDate fromDate,
                        LocalDate toDate) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("platformId", platformId)
                                .addValue("fromDate", fromDate)
                                .addValue("toDate", toDate);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("CreateWeeklyPlatformReport")
                                .withSchemaName("dbo")
                                .returningResultSet("report", BeanPropertyRowMapper
                                                .newInstance(WeeklyPlatformReportPayloadBean.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return ((List<WeeklyPlatformReportPayloadBean>) output.get("report")).get(0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<PlayRegisterPayloadBean> getPlayRegisterReportsByReportId(Integer reportId) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("reportId", reportId);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("GetPlayRegisterReportsByReportId")
                                .withSchemaName("dbo")
                                .returningResultSet("click",
                                                BeanPropertyRowMapper.newInstance(PlayRegisterPayloadBean.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return (List<PlayRegisterPayloadBean>) output.get("click");
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<AssociationReportPayloadBean> getAssociationsByReportId(Integer reportId) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("reportId", reportId);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("GetAssociationsByReportId")
                                .withSchemaName("dbo")
                                .returningResultSet("association",
                                                BeanPropertyRowMapper.newInstance(AssociationReportPayloadBean.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return (List<AssociationReportPayloadBean>) output.get("association");
        }

        @SuppressWarnings("unchecked")
        @Override
        public WeeklyAdvertiserReportPayloadBean createWeeklyAdvertiserReport(Integer advertiserId, LocalDate fromDate,
                        LocalDate toDate) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("advertiserId", advertiserId)
                                .addValue("fromDate", fromDate)
                                .addValue("toDate", toDate);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("CreateWeeklyAdvertiserReport")
                                .withSchemaName("dbo")
                                .returningResultSet("report", BeanPropertyRowMapper
                                                .newInstance(WeeklyAdvertiserReportPayloadBean.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return ((List<WeeklyAdvertiserReportPayloadBean>) output.get("report")).get(0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<AdvertisingClickReportBean> getAdvertisingClickReportsByReportId(Integer reportId) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("reportId", reportId);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("GetAdvertisingClickReportsByReportId")
                                .withSchemaName("dbo")
                                .returningResultSet("click",
                                                BeanPropertyRowMapper.newInstance(AdvertisingClickReportBean.class));

                Map<String, Object> output = jdbcCall.execute(input);
                return (List<AdvertisingClickReportBean>) output.get("click");
        }
}
