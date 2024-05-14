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
				.returningResultSet("target",
						BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<TargetCategoryDTO>) output.get("target")).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TargetCategoryDTO updateTargetCategory(Integer id, String targetTitle) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("targetTitle", targetTitle)
				.addValue("targetId", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("UpdateTargetCategory")
				.withSchemaName("dbo")
				.returningResultSet("target",
						BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<TargetCategoryDTO>) output.get("target")).get(0);
	}

	@Override
	public Integer deleteTargetCategory(Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("tableName", "TargetCategory")
				.addValue("primaryKeyColumn", "targetId")
				.addValue("primaryKeyValue", id)
				.addValue("deletedId", null, Types.INTEGER);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SoftDeleteRecord")
				.withSchemaName("dbo");

		Map<String, Object> out = jdbcCall.execute(input);
		return Integer.valueOf(out.get("deletedId").toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TargetCategoryDTO> getAllTargetCategories() {
		SqlParameterSource input = new MapSqlParameterSource();

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllTargetCategories")
				.withSchemaName("dbo")
				.returningResultSet("targets",
						BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);

		return (List<TargetCategoryDTO>) output.get("targets");
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateAdvertisingTargetsFromJson(String json) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("json", json);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("UpdateAdvertisingTargetsFromJson")
				.withSchemaName("dbo")
				.returningResultSet("message",
						BeanPropertyRowMapper.newInstance(String.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<String>) output.get("message")).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("advertisingId", advertisingId);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllAdvertisingTargetByAdvertisingId")
				.withSchemaName("dbo")
				.returningResultSet("targets",
						BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return (List<TargetCategoryDTO>) output.get("targets");
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateMarketingPreferencesFromJson(String json) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("json", json);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("UpdateMarketingPreferencesFromJson")
				.withSchemaName("dbo")
				.returningResultSet("message",
						BeanPropertyRowMapper.newInstance(String.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<String>) output.get("message")).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TargetCategoryDTO> getAllMarketingPreferencesBySubscriberId(Integer subscriberId) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("subscriberId", subscriberId);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllMarketingPreferencesBySubscriberId")
				.withSchemaName("dbo")
				.returningResultSet("targets",
						BeanPropertyRowMapper.newInstance(TargetCategoryDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return (List<TargetCategoryDTO>) output.get("targets");
	}

}
