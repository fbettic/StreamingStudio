package ar.edu.ubp.rest.plataformasrest.repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.plataformasrest.beans.NewSessionBean;
import ar.edu.ubp.rest.plataformasrest.beans.SessionBean;
import ar.edu.ubp.rest.plataformasrest.repositories.interfaces.ISessionRepository;

@Repository
public class SessionRepository implements ISessionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public SessionBean createSession(NewSessionBean newSession, Integer serviceId, Integer userId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("serviceId", serviceId)
                .addValue("userId", userId)
                .addValue("filmCode", newSession.getFilmCode())
                .addValue("createdAt", new Date());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateSession")
                .withSchemaName("dbo")
                .returningResultSet("session", BeanPropertyRowMapper.newInstance(SessionBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<SessionBean>) out.get("session")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SessionBean markSessionAsUsed(Integer sessionId) {
        SqlParameterSource input = new MapSqlParameterSource()
            .addValue("sessionId", sessionId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("MarkSessionAsUsed")
                .withSchemaName("dbo")
                .returningResultSet("session", BeanPropertyRowMapper.newInstance(SessionBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<SessionBean>) out.get("session")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SessionBean markSessionAsExpired(Integer sessionId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("sessionId", sessionId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("MarkSessionAsExpired")
                .withSchemaName("dbo")
                .returningResultSet("session", BeanPropertyRowMapper.newInstance(SessionBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<SessionBean>) out.get("session")).get(0);
    }

}
