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

import ar.edu.ubp.rest.anunciantesrest.beans.ServiceConnectionBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.interfaces.IServiceConnectionRepository;

@Repository
public class ServiceConnectionRepository implements IServiceConnectionRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @SuppressWarnings("unchecked")
    @Override
    public Integer getServiceId(String authToken) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("authToken", authToken);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetServiceConnectionByToken")
                .withSchemaName("dbo")
                .returningResultSet("service", BeanPropertyRowMapper.newInstance(ServiceConnectionBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<ServiceConnectionBean>) out.get("service")).get(0).getServiceId();
}}
