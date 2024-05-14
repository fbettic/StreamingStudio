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

import ar.edu.ubp.rest.portal.dto.BannerPriorityDTO;
import ar.edu.ubp.rest.portal.dto.request.BannerPriorityRequestDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IBannerPriorityRepository;

@Repository
public class BannerPriorityRepository implements IBannerPriorityRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public BannerPriorityDTO createBannerPriority(BannerPriorityRequestDTO priority) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("priorityType", priority.getPriorityType())
				.addValue("priorityValue", priority.getPriorityValue())
				.addValue("priorityFeeId", priority.getPriorityFeeId());

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("CreateBannerPriority")
				.withSchemaName("dbo")
				.returningResultSet("priority",
						BeanPropertyRowMapper.newInstance(BannerPriorityDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<BannerPriorityDTO>) output.get("priority")).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BannerPriorityDTO updateBannerPriority(BannerPriorityRequestDTO priority, Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("priorityType", priority.getPriorityType())
				.addValue("priorityValue", priority.getPriorityValue())
				.addValue("priorityFeeId", priority.getPriorityFeeId())
				.addValue("priorityId", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("UpdateBannerPriority")
				.withSchemaName("dbo")
				.returningResultSet("priority",
						BeanPropertyRowMapper.newInstance(BannerPriorityDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<BannerPriorityDTO>) output.get("priority")).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BannerPriorityDTO getBannerPriorityById(Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("priorityId", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetBannerPriorityById")
				.withSchemaName("dbo")
				.returningResultSet("priority",
						BeanPropertyRowMapper.newInstance(BannerPriorityDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<BannerPriorityDTO>) output.get("priority")).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BannerPriorityDTO> getAllBannerPriorities() {
		SqlParameterSource input = new MapSqlParameterSource();

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllBannerPriorities")
				.withSchemaName("dbo")
				.returningResultSet("priority",
						BeanPropertyRowMapper.newInstance(BannerPriorityDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return (List<BannerPriorityDTO>) output.get("priority");
	}

	@Override
	public Integer deleteBannerPriorityById(Integer id) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("tableName", "BannerPriority")
				.addValue("primaryKeyColumn", "priorityId")
				.addValue("primaryKeyValue", id)
				.addValue("deletedId", null, Types.INTEGER);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SoftDeleteRecord")
				.withSchemaName("dbo");

		Map<String, Object> out = jdbcCall.execute(input);
		return Integer.valueOf(out.get("deletedId").toString());
	}

}
