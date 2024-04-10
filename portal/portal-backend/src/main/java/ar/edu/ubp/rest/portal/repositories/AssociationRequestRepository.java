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

import ar.edu.ubp.rest.portal.dto.AssociationRequestDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAssociationRequestRepository;

@Repository
public class AssociationRequestRepository implements IAssociationRequestRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public AssociationRequestDTO createAssociationRequest(AssociationRequestDTO associationRequest) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("platformId", associationRequest.getPlatformId())
                .addValue("transactionId", associationRequest.getTransactionId())
                .addValue("subscriberId", associationRequest.getSubscriberId())
                .addValue("authtUrl", associationRequest.getAuthUrl())
                .addValue("associationType", associationRequest.getAssociationType())
                .addValue("requestAt", associationRequest.getRequestAt());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateAssociationRequest")
                .withSchemaName("dbo")
                .returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationRequestDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<AssociationRequestDTO>) output.get("association")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssociationRequestDTO getLastOpenAssociationRequest(Integer platformId, Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("platformId", platformId)
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetLastOpenAssociationRequest")
                .withSchemaName("dbo")
                .returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationRequestDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<AssociationRequestDTO>) output.get("association")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssociationRequestDTO cancelAssociationRequest(Integer platformId, Integer subscriberId, Integer transactionId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("platformId", platformId)
                .addValue("subscriberId", subscriberId)
                .addValue("transactionId", transactionId);
                

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CancelAssociationRequest")
                .withSchemaName("dbo")
                .returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationRequestDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<AssociationRequestDTO>) output.get("association")).get(0);
    }

@SuppressWarnings("unchecked")
@Override
public AssociationRequestDTO getAssociationRequestByUuid(String uuid) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("uuid", uuid);
           

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAssociationRequestByUuid")
                .withSchemaName("dbo")
                .returningResultSet("association", BeanPropertyRowMapper.newInstance(AssociationRequestDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<AssociationRequestDTO>) output.get("association")).get(0);
}

}
