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

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.models.Advertiser;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAdvertiserRepository;

@Repository
public class AdvertiserRepository implements IAdvertiserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@SuppressWarnings("unchecked")
	public AdvertiserDTO save(Advertiser advertiser) {
		SqlParameterSource in = new MapSqlParameterSource()
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
				.withProcedureName("InsertAdvertiser")
				.withSchemaName("dbo")
				.returningResultSet("advertiser", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> out = jdbcCall.execute(in);
		return ((List<AdvertiserDTO>) out.get("advertiser")).get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AdvertiserDTO> findAdvertisers() {
		SqlParameterSource in = new MapSqlParameterSource();
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllAdvertisers")
				.withSchemaName("dbo")
				.returningResultSet("advertisers", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> out = jdbcCall.execute(in);
		return (List<AdvertiserDTO>) out.get("advertisers");
	}

	@Override
	public AdvertiserDTO findAdvertiserByEmail(String email) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAdvertiserByEmail'");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Integer deleteAdvertiserById(Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("tableName", "Advertiser")
				.addValue("primaryKeyColumn", "advertiserId")
				.addValue("primaryKeyValue", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SoftDeleteRecord")
				.withSchemaName("dbo")
				.returningResultSet("advertiserId", BeanPropertyRowMapper.newInstance(Integer.class));

		Map<String, Object> out = jdbcCall.execute(input);
		return ((List<Integer>) out.get("advertiserId")).get(0);
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
				.addValue("password", advertiser.getPassword())
				.addValue("apiUrl", advertiser.getApiUrl())
				.addValue("authToken", advertiser.getAuthToken())
				.addValue("serviceType", advertiser.getServiceType().toString())
				.addValue("advertiserId", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("UpdateAdvertiser")
				.withSchemaName("dbo")
				.returningResultSet("advertiser", BeanPropertyRowMapper.newInstance(AdvertiserDTO.class));

		Map<String, Object> out = jdbcCall.execute(input);
		return ((List<AdvertiserDTO>) out.get("advertiser")).get(0);
	}

}
