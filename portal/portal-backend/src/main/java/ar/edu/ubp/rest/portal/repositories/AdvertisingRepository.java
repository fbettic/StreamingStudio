package ar.edu.ubp.rest.portal.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.BannerUpdateDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAdvertisingRepository;

@Repository
public class AdvertisingRepository implements IAdvertisingRepository{

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
				

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("CreateAdvertising")
				.withSchemaName("dbo")
				.returningResultSet("advertising", BeanPropertyRowMapper.newInstance(AdvertisingDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
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
    

    @Override
    public Integer updateBatchBanner(List<BannerUpdateDTO> banners) {
        String sql = "EXEC UpdateAdvertisingBanner @advertiserId=?, @bannerId=?, @redirectUrl=?, @imageUrl=?, @bannerText=?";

        int[] affectedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BannerUpdateDTO banner = banners.get(i);
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
}
