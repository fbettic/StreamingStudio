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

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.interfaces.IAdvertisingRepository;

@Repository
public class AdvertisingRepository implements IAdvertisingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public AdvertisingBean getAdvertisingById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("advertisingId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAdvertisingById")
                .withSchemaName("dbo")
                .returningResultSet("advertising", BeanPropertyRowMapper.newInstance(AdvertisingBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<AdvertisingBean>) out.get("advertising")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdvertisingBean> getAllAdvertisings(Integer serviceId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("serviceId", serviceId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllAdvertisings")
                .withSchemaName("dbo")
                .returningResultSet("advertisings", BeanPropertyRowMapper.newInstance(AdvertisingBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return (List<AdvertisingBean>) out.get("advertisings");
    }

}
