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

import ar.edu.ubp.rest.portal.dto.FeeDTO;
import ar.edu.ubp.rest.portal.dto.request.FeeRequestDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IFeeRepository;

@Repository
public class FeeRepository implements IFeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public FeeDTO createFee(FeeRequestDTO fee) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("feeType", fee.getFeeType())
                .addValue("feeValue", fee.getFeeValue());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateFee")
                .withSchemaName("dbo")
                .returningResultSet("fee", BeanPropertyRowMapper.newInstance(FeeDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<FeeDTO>) output.get("fee")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FeeDTO updateFee(FeeRequestDTO fee, Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("feeType", fee.getFeeType())
                .addValue("feeValue", fee.getFeeValue())
                .addValue("feeId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("UpdateFee")
                .withSchemaName("dbo")
                .returningResultSet("fee", BeanPropertyRowMapper.newInstance(FeeDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<FeeDTO>) output.get("fee")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FeeDTO getFeeById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
				.addValue("feeId", id);
				

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetFeeById")
				.withSchemaName("dbo")
				.returningResultSet("fee", BeanPropertyRowMapper.newInstance(FeeDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<FeeDTO>) output.get("fee")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FeeDTO> getAllFees() {
        SqlParameterSource input = new MapSqlParameterSource();
				
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllFees")
				.withSchemaName("dbo")
				.returningResultSet("fee", BeanPropertyRowMapper.newInstance(FeeDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return (List<FeeDTO>) output.get("fee");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer deleteFeeById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
				.addValue("tableName", "FeeType")
				.addValue("primaryKeyColumn", "feeId")
				.addValue("primaryKeyValue", id);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SoftDeleteRecord")
				.withSchemaName("dbo")
				.returningResultSet("feeId", BeanPropertyRowMapper.newInstance(Integer.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<Integer>) output.get("feeId")).get(0);
    }

}
