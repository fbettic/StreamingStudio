package ar.edu.ubp.rest.plataformasrest.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.plataformasrest.beans.AssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewAssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.repositories.interfaces.IAssociationRequestRepository;
import ar.edu.ubp.rest.plataformasrest.utils.AuthUrlGenerator;

@Repository
public class AssociationRequestRepository implements IAssociationRequestRepository {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        private AssociationRequestBean mapRowToAssociationRequestBean(ResultSet rs) throws SQLException {
                return AssociationRequestBean.builder()
                                .associationId(rs.getInt("associationId"))
                                .serviceId(rs.getInt("serviceId"))
                                .associationType(rs.getString("associationType"))
                                .state(rs.getString("state"))
                                .authUrl(AuthUrlGenerator.generateAuthUrl(rs.getString("associationType"),
                                                rs.getString("uuid")))
                                .redirectUrl(rs.getString("redirectUrl"))
                                .requestedAt(rs.getDate("requestedAt"))
                                .userId(rs.getInt("userId"))
                                .userToken(rs.getString("userToken"))
                                .build();
        }

        @Override
        public AssociationRequestBean createAssociationRequest(NewAssociationRequestBean newAssociationRequest,
                        Integer serviceId) {

                String uuid = AuthUrlGenerator.generateUuid();

                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("serviceId", serviceId)
                                .addValue("associationType", newAssociationRequest.getAssociationType())
                                .addValue("uuid", uuid)
                                .addValue("redirectUrl", newAssociationRequest.getRedirectUrl())
                                .addValue("requestedAt", new Date());

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("CreateAssociationRequest")
                                .withSchemaName("dbo")
                                .returningResultSet("associationRequest",
                                                (rs, rowNum) -> mapRowToAssociationRequestBean(rs));

                Map<String, Object> out = jdbcCall.execute(input);

                @SuppressWarnings("unchecked")
                List<AssociationRequestBean> associationRequests = (List<AssociationRequestBean>) out
                                .get("associationRequest");

                return associationRequests != null && !associationRequests.isEmpty() ? associationRequests.get(0)
                                : null;
        }

        @Override
        public AssociationRequestBean completeAssociationRequest(Integer userId, String uuid) {
                SqlParameterSource input = new MapSqlParameterSource()
                                .addValue("userId", userId)
                                .addValue("uuid", uuid);

                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("SetUserToken")
                                .withSchemaName("dbo")
                                .returningResultSet("associationRequest",
                                                (rs, rowNum) -> mapRowToAssociationRequestBean(rs));

                Map<String, Object> out = jdbcCall.execute(input);

                @SuppressWarnings("unchecked")
                List<AssociationRequestBean> associationRequests = (List<AssociationRequestBean>) out
                                .get("associationRequest");

                return associationRequests != null && !associationRequests.isEmpty() ? associationRequests.get(0)
                                : null;
        }

}
