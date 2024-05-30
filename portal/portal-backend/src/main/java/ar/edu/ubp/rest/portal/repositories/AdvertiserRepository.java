package ar.edu.ubp.rest.portal.repositories;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.models.users.Advertiser;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAdvertiserRepository;

@Repository
public class AdvertiserRepository implements IAdvertiserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@SuppressWarnings("unchecked")
	public AdvertiserDTO createAdvertiser(Advertiser advertiser) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("agentFirstname", advertiser.getAgentFirstname())
				.addValue("agentLastname", advertiser.getAgentLastname())
				.addValue("bussinesName", advertiser.getBussinesName())
				.addValue("companyName", advertiser.getCompanyName())
				.addValue("email", advertiser.getEmail())
				.addValue("phone", advertiser.getPhone())
				.addValue("password", advertiser.getPassword())
				.addValue("apiUrl", advertiser.getApiUrl())
				.addValue("authToken", advertiser.getAuthToken())
				.addValue("serviceType", advertiser.getServiceType().toString());

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("CreateAdvertiser")
				.withSchemaName("dbo")
				.returningResultSet("advertiser", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AdvertiserDTO>) output.get("advertiser")).get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AdvertiserDTO> getAllAdvertisers() {
		SqlParameterSource input = new MapSqlParameterSource();
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllAdvertisers")
				.withSchemaName("dbo")
				.returningResultSet("advertisers", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return (List<AdvertiserDTO>) output.get("advertisers");
	}

	@Override
	@SuppressWarnings("unchecked")
	public AdvertiserDTO getAdvertiserById(Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("advertiserId", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAdvertiserById")
				.withSchemaName("dbo")
				.returningResultSet("advertiser", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AdvertiserDTO>) output.get("advertiser")).get(0);
	}

	@Override
	public Integer deleteAdvertiserById(Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("tableName", "Advertiser")
				.addValue("primaryKeyColumn", "advertiserId")
				.addValue("primaryKeyValue", id)
				.addValue("deletedId", null, Types.INTEGER);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SoftDeleteRecord")
				.withSchemaName("dbo");

		Map<String, Object> out = jdbcCall.execute(input);
		return Integer.valueOf(out.get("deletedId").toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public AdvertiserDTO updateAdvertiserById(AdvertiserRequestDTO advertiser, Integer id) {

		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("agentFirstname", advertiser.getAgentFirstname())
				.addValue("agentLastname", advertiser.getAgentLastname())
				.addValue("bussinesName", advertiser.getBussinesName())
				.addValue("companyName", advertiser.getCompanyName())
				.addValue("email", advertiser.getEmail())
				.addValue("phone", advertiser.getPhone())
				.addValue("apiUrl", advertiser.getApiUrl())
				.addValue("authToken", advertiser.getAuthToken())
				.addValue("serviceType", advertiser.getServiceType().toString())
				.addValue("advertiserId", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("UpdateAdvertiser")
				.withSchemaName("dbo")
				.returningResultSet("advertiser", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AdvertiserDTO>) output.get("advertiser")).get(0);
	}

}
