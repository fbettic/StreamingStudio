package ar.edu.ubp.rest.anunciantesrest.repositories;

import java.util.List;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingWithBannerBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;

@Repository
public class AnunciantesRestRepository implements IAnunciantesRestRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /*
     * @Override
     * public List<AdvertisingWithBannerBean> getAllAdvertisingsWithBanners(){
     * return jdbcTemplate.query("")
     * }
     * 
     * @Override
     * public AdvertisingWithBannerBean getAdvertisingWithBanner(int advertisingId){
     * 
     * }
     */

    @Override
    public BannerBean getBanner(int bannerId) {
        System.out.println("-----------------------> Getting banner <-----------------------");
        String query = "SELECT * FROM Banner WHERE bannerId = :bannerId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("bannerId", bannerId);
        return namedParameterJdbcTemplate.queryForObject(query, parameters,
                BeanPropertyRowMapper.newInstance(BannerBean.class));
    }

}
