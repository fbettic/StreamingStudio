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

import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAssociationRepository;

@Repository
public class AssociationRepository implements IAssociationRepository {

    @Autowired
	private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public AssociationDTO createAssociation(AssociationDTO association) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("platformId",association.getPlatformId())
                .addValue("transactionId",association.getTransactionId())
                .addValue("subscriberId",association.getSubscriberId())
                .addValue("userToken",association.getUserToken())
                .addValue("entryDate",association.getEntryDate());

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("CreateAssociation")
				.withSchemaName("dbo")
				.returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AssociationDTO>) output.get("association")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssociationDTO getAssociationToken(Integer platformId, Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("platformId",platformId)
                .addValue("subscriberId",subscriberId);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAssociationToken")
				.withSchemaName("dbo")
				.returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AssociationDTO>) output.get("association")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssociationDTO cancelAssociation(Integer platformId, Integer subscriberId, Integer transactionId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("platformId",platformId)
                .addValue("subscriberId",subscriberId)
                .addValue("transactionId",transactionId);


		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("CreateAssociation")
				.withSchemaName("dbo")
				.returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AssociationDTO>) output.get("association")).get(0);
    }
    
}
