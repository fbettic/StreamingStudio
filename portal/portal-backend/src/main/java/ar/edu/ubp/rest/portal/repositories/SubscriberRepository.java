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

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.SubscriberDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberRequestDTO;
import ar.edu.ubp.rest.portal.models.users.Subscriber;
import ar.edu.ubp.rest.portal.repositories.interfaces.ISubscriberRepository;

@Repository
public class SubscriberRepository implements ISubscriberRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SubscriberDTO getSubscriberByEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'getSubscriberByEmail'");
    }

    @Override
    @SuppressWarnings("unchecked")
    public SubscriberDTO createSubscriber(Subscriber subscriber) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("firstname", subscriber.getFirstname())
                .addValue("lastname", subscriber.getLastname())
                .addValue("email", subscriber.getEmail())
                .addValue("phone", subscriber.getPhone())
                .addValue("birth", subscriber.getBirth())
                .addValue("password", subscriber.getPassword())
                .addValue("validated", 1);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreateSubscriber")
                .withSchemaName("dbo")
                .returningResultSet("subscriber", BeanPropertyRowMapper.newInstance(SubscriberDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SubscriberDTO>) output.get("subscriber")).get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SubscriberDTO updateSubscriber(Integer id, SubscriberRequestDTO subscriber) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", id)
                .addValue("firstname", subscriber.getFirstname())
                .addValue("lastname", subscriber.getLastname())
                .addValue("email", subscriber.getEmail())
                .addValue("phone", subscriber.getPhone())
                .addValue("birth", subscriber.getBirth())
                .addValue("password", subscriber.getPassword())
                .addValue("validated", 1);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("UpdateSubscriber")
                .withSchemaName("dbo")
                .returningResultSet("subscriber", BeanPropertyRowMapper.newInstance(SubscriberDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SubscriberDTO>) output.get("subscriber")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SubscriberDTO getSubscriberById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetSubscriberById")
                .withSchemaName("dbo")
                .returningResultSet("subscriber", BeanPropertyRowMapper.newInstance(SubscriberDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<SubscriberDTO>) output.get("subscriber")).get(0);
    }

    @Override
    public Integer deleteSubscriberById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("tableName", "Subscriber")
                .addValue("primaryKeyColumn", "subscriberId")
                .addValue("primaryKeyValue", id)
                .addValue("deletedId", null, Types.INTEGER);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SoftDeleteRecord")
                .withSchemaName("dbo");

        Map<String, Object> output = jdbcCall.execute(input);
        return Integer.valueOf(output.get("deletedId").toString());
    }

}
