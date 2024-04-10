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

import ar.edu.ubp.rest.portal.dto.SessionDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.ISessionRepository;

@Repository
public class SessionRepository implements ISessionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public SessionDTO createSession(SessionDTO session) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", session.getSubscriberId())
                .addValue("platformId", session.getPlatformId())
                .addValue("transactionId", session.getTransactionId())
                .addValue("filmCode", session.getFilmCode())
                .addValue("sessionUrl", session.getSessionUrl())
                .addValue("createdAt", session.getCreatedAt());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateSession")
                .withSchemaName("dbo")
                .returningResultSet("session", BeanPropertyRowMapper.newInstance(SessionDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SessionDTO>) output.get("session")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SessionDTO markSesionAsUsed(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("sessionId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("MarkSessionAsUsed")
                .withSchemaName("dbo")
                .returningResultSet("session", BeanPropertyRowMapper.newInstance(SessionDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SessionDTO>) output.get("session")).get(0);
    }

}
