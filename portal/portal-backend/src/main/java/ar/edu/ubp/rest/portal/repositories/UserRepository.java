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

import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.repositories.interfaces.IUserRepository;

@Repository
public class UserRepository implements IUserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@SuppressWarnings("unchecked")
	public CustomUserDetails getUserByEmail(String email) {
		SqlParameterSource input = new MapSqlParameterSource().addValue("email", email);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetUserByEmail")
				.withSchemaName("dbo")
				.returningResultSet("user", BeanPropertyRowMapper.newInstance(CustomUserDetails.class));

		Map<String, Object> output = jdbcCall.execute(input);

		return ((List<CustomUserDetails>) output.get("user")).get(0);
	}

}
