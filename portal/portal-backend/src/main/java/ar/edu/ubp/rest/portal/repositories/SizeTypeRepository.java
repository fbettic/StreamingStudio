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

import ar.edu.ubp.rest.portal.dto.SizeTypeDTO;
import ar.edu.ubp.rest.portal.dto.request.SizeTypeRequestDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.ISizeTypeRepository;

@Repository
public class SizeTypeRepository implements ISizeTypeRepository {
        
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public SizeTypeDTO createSizeType(SizeTypeRequestDTO size) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("sizeType", size.getSizeType())
                .addValue("sizeValue", size.getSizeValue())
                .addValue("sizeFeeId", size.getSizeFeeId())
                .addValue("height", size.getHeight())
                .addValue("width", size.getWidth());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateSizeType")
                .withSchemaName("dbo")
                .returningResultSet("size", BeanPropertyRowMapper.newInstance(SizeTypeDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SizeTypeDTO>) output.get("size")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SizeTypeDTO updateSizeType(SizeTypeRequestDTO size, Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("sizeType", size.getSizeType())
                .addValue("sizeValue", size.getSizeValue())
                .addValue("sizeFeeId", size.getSizeFeeId())
                .addValue("height", size.getHeight())
                .addValue("width", size.getWidth())
                .addValue("sizeId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("UpdateSizeType")
                .withSchemaName("dbo")
                .returningResultSet("size", BeanPropertyRowMapper.newInstance(SizeTypeDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SizeTypeDTO>) output.get("size")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SizeTypeDTO getSizeTypeById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("sizeId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetSizeTypeById")
                .withSchemaName("dbo")
                .returningResultSet("size", BeanPropertyRowMapper.newInstance(SizeTypeDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SizeTypeDTO>) output.get("size")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SizeTypeDTO> getAllSizeTypes() {
        SqlParameterSource input = new MapSqlParameterSource();

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllSizeTypes")
                .withSchemaName("dbo")
                .returningResultSet("size", BeanPropertyRowMapper.newInstance(SizeTypeDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<SizeTypeDTO>) output.get("size");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer deleteSizeTypeById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("tableName", "SizeType")
                .addValue("primaryKeyColumn", "sizeId")
                .addValue("primaryKeyValue", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SoftDeleteRecord")
                .withSchemaName("dbo")
                .returningResultSet("sizeId", BeanPropertyRowMapper.newInstance(Integer.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<Integer>) output.get("sizeId")).get(0);
    }

}
