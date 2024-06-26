package ar.edu.ubp.rest.anunciantesrest.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.anunciantesrest.beans.BasicResponseBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.interfaces.IReportRepository;

@Repository
public class ReportRepository implements IReportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public BasicResponseBean createWeekleyReport(String reportJson) {
        System.out.println("---------->" + reportJson);
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("json", reportJson);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateWeeklyReportFromJson")
                .withSchemaName("dbo")
                .returningResultSet("message", BeanPropertyRowMapper.newInstance(BasicResponseBean.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<BasicResponseBean>) output.get("message")).get(0);
    }

}
