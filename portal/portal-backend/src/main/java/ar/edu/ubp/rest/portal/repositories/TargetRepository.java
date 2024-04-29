package ar.edu.ubp.rest.portal.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.dto.AdvertisingTargetDTO;
import ar.edu.ubp.rest.portal.dto.FeeDTO;
import ar.edu.ubp.rest.portal.dto.MarketingPreferenceDTO;
import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.ITargetRepository;

@Repository
public class TargetRepository implements ITargetRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public TargetCategoryDTO createTargetCategory(String targetTitle) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("targetTitle", targetTitle);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateTargetCategory")
                .withSchemaName("dbo")
                .returningResultSet("target", BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<TargetCategoryDTO>) output.get("target")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TargetCategoryDTO updateTargetCategoryTitle(Integer id, String targetTitle) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("targetTitle", targetTitle)
                .addValue("targetId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("UpdateTargetCategoryTitle")
                .withSchemaName("dbo")
                .returningResultSet("target", BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<TargetCategoryDTO>) output.get("target")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TargetCategoryDTO> getAllTargetCategories() {
        SqlParameterSource input = new MapSqlParameterSource();

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllTargetCategories")
                .withSchemaName("dbo")
                .returningResultSet("targets", BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);

        return (List<TargetCategoryDTO>) output.get("targets");
    }

    @SuppressWarnings("unchecked")
    @Override
    public AdvertisingTargetDTO addAdvertisingTarget(Integer targetId, Integer advertisingId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("targetId", targetId)
                .addValue("advertisingId", advertisingId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("AddAdvertisingTarget")
                .withSchemaName("dbo")
                .returningResultSet("target", BeanPropertyRowMapper.newInstance(AdvertisingTargetDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<AdvertisingTargetDTO>) output.get("target")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AdvertisingTargetDTO removeAdvertisingTarget(Integer targetId, Integer advertisingId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("targetId", targetId)
                .addValue("advertisingId", advertisingId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("RemoveAdvertisingTarget")
                .withSchemaName("dbo")
                .returningResultSet("target", BeanPropertyRowMapper.newInstance(AdvertisingTargetDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<AdvertisingTargetDTO>) output.get("target")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("advertisingId", advertisingId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllAdvertisingTargetByAdvertisingId")
                .withSchemaName("dbo")
                .returningResultSet("targets", BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<TargetCategoryDTO>) output.get("targets");
    }

    @SuppressWarnings("unchecked")
    @Override
    public MarketingPreferenceDTO addMarketingPreference(Integer targetId, Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("targetId", targetId)
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("AddMarketingPreference")
                .withSchemaName("dbo")
                .returningResultSet("target", BeanPropertyRowMapper.newInstance(AdvertisingTargetDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<MarketingPreferenceDTO>) output.get("target")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MarketingPreferenceDTO removeMarketingPreference(Integer targetId, Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("targetId", targetId)
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("RemoveMarketingPreference")
                .withSchemaName("dbo")
                .returningResultSet("target", BeanPropertyRowMapper.newInstance(AdvertisingTargetDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<MarketingPreferenceDTO>) output.get("target")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TargetCategoryDTO> getAllAdvertisingTargetBySubscriberId(Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllAdvertisingTargetBySubscriberId")
                .withSchemaName("dbo")
                .returningResultSet("targets", BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<TargetCategoryDTO>) output.get("targets");
    }

}