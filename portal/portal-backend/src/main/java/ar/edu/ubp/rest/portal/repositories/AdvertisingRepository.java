package ar.edu.ubp.rest.portal.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.BannerDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingClickRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.SubscriberAdvertisingDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAdvertisingRepository;

@Repository
public class AdvertisingRepository implements IAdvertisingRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("unchecked")
  @Override
  public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertising) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("advertiserId", advertising.getAdvertiserId())
        .addValue("sizeId", advertising.getSizeId())
        .addValue("allPagesFeeId", advertising.getAllPagesFeeId())
        .addValue("priorityId", advertising.getPriorityId())
        .addValue("redirectUrl", advertising.getRedirectUrl())
        .addValue("imageUrl", advertising.getImageUrl())
        .addValue("bannerText", advertising.getBannerText())
        .addValue("bannerId", advertising.getBannerId())
        .addValue("fromDate", advertising.getFromDate())
        .addValue("toDate", advertising.getToDate());

    System.out.println("----------------->" + input.toString());
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("CreateAdvertising")
        .withSchemaName("dbo")
        .returningResultSet("advertising", BeanPropertyRowMapper.newInstance(AdvertisingDTO.class));

    Map<String, Object> output = jdbcCall.execute(input);

    System.out.println("----------------->" + output.toString());
    return ((List<AdvertisingDTO>) output.get("advertising")).get(0);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AdvertisingDTO> getAllAdvertisings() {
    SqlParameterSource input = new MapSqlParameterSource();
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("GetAllAdvertisings")
        .withSchemaName("dbo")
        .returningResultSet("advertisings", BeanPropertyRowMapper.newInstance(AdvertisingDTO.class));

    Map<String, Object> output = jdbcCall.execute(input);
    return (List<AdvertisingDTO>) output.get("advertisings");
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AdvertisingDTO> getAllAdvertisingsByAdvertiser(Integer advertiserId) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("advertiserId", advertiserId);
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("GetAllAdvertisingsByAdvertiser")
        .withSchemaName("dbo")
        .returningResultSet("advertisings", BeanPropertyRowMapper.newInstance(AdvertisingDTO.class));

    Map<String, Object> output = jdbcCall.execute(input);
    return (List<AdvertisingDTO>) output.get("advertisings");
  }

  @Override
  public Integer updateBatchBanner(List<BannerDTO> banners) {
    String sql = "EXEC UpdateAdvertisingBanner @advertiserId=?, @bannerId=?, @redirectUrl=?, @imageUrl=?, @bannerText=?";

    int[] affectedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        BannerDTO banner = banners.get(i);
        ps.setInt(1, banner.getAdvertiserId());
        ps.setInt(2, banner.getBannerId());
        ps.setString(3, banner.getRedirectUrl());
        ps.setString(4, banner.getImageUrl());
        ps.setString(5, banner.getText());
      }

      @Override
      public int getBatchSize() {
        return banners.size();
      }
    });

    int totalAffectedRows = 0;
    for (int rows : affectedRows) {
      totalAffectedRows += rows;
    }

    return totalAffectedRows;
  }

  @SuppressWarnings("unchecked")
  @Override
  public AdvertisingDTO getAdvertisingById(Integer id) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("advertisingId", id);
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("GetAdvertisingById")
        .withSchemaName("dbo")
        .returningResultSet("advertising", BeanPropertyRowMapper.newInstance(AdvertisingDTO.class));

    Map<String, Object> output = jdbcCall.execute(input);
    return ((List<AdvertisingDTO>) output.get("advertising")).get(0);
  }

  @Override
  public Integer deleteAdvertisingById(Integer id) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("tableName", "Advertising")
        .addValue("primaryKeyColumn", "advertisingId")
        .addValue("primaryKeyValue", id)
        .addValue("deletedId", null, Types.INTEGER);

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("SoftDeleteRecord")
        .withSchemaName("dbo");

    Map<String, Object> output = jdbcCall.execute(input);

    return Integer.valueOf(output.get("deletedId").toString());
  }

  @SuppressWarnings("unchecked")
  @Override
  public AdvertisingDTO updateAdvertisingById(Integer id, AdvertisingRequestDTO advertising) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("advertiserId", advertising.getAdvertiserId())
        .addValue("sizeId", advertising.getSizeId())
        .addValue("allPagesFeeId", advertising.getAllPagesFeeId())
        .addValue("priorityId", advertising.getPriorityId())
        .addValue("redirectUrl", advertising.getRedirectUrl())
        .addValue("imageUrl", advertising.getImageUrl())
        .addValue("bannerText", advertising.getBannerText())
        .addValue("bannerId", advertising.getBannerId())
        .addValue("fromDate", advertising.getFromDate())
        .addValue("toDate", advertising.getToDate())
        .addValue("advertisingId", id);

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("UpdateAdvertising")
        .withSchemaName("dbo")
        .returningResultSet("advertising", BeanPropertyRowMapper.newInstance(AdvertisingDTO.class));

    Map<String, Object> output = jdbcCall.execute(input);
    return ((List<AdvertisingDTO>) output.get("advertising")).get(0);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<SubscriberAdvertisingDTO> getAllAdvertisingForSubscriber(Integer subscriberId) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("subscriberId", subscriberId);

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("GetAdvertisingsForSubscriber")
        .withSchemaName("dbo")
        .returningResultSet("advertisings", BeanPropertyRowMapper.newInstance(SubscriberAdvertisingDTO.class));

    Map<String, Object> output = jdbcCall.execute(input);
    return (List<SubscriberAdvertisingDTO>) output.get("advertisings");
  }

  @Override
  public String createSubscriberAdvertisingClick(Integer subscriberId, AdvertisingClickRequestDTO click) {
    SqlParameterSource input = new MapSqlParameterSource()
        .addValue("subscriberId", subscriberId)
        .addValue("advertisingId", click.getAdvertisingId())
        .addValue("clickedAt", click.getClickedAt());

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("CreateSubscriberAdvertisingClick")
        .withSchemaName("dbo")
        .returningResultSet("advertisings", BeanPropertyRowMapper.newInstance(SubscriberAdvertisingDTO.class));

    jdbcCall.execute(input);
    return "Success";
  }
}
