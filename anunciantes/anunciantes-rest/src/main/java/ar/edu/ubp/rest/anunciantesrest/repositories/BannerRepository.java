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

import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.interfaces.IBannerRepository;

@Repository
public class BannerRepository implements IBannerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public BannerBean getBannerById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("bannerId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetBannerById")
                .withSchemaName("dbo")
                .returningResultSet("banner", BeanPropertyRowMapper.newInstance(BannerBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<BannerBean>) out.get("banner")).get(0);
    }
    
}
