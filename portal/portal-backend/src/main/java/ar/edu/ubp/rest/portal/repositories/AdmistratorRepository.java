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

import ar.edu.ubp.rest.portal.dto.AdministratorDTO;
import ar.edu.ubp.rest.portal.models.users.Administrator;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAdministratorRepository;

@Repository
public class AdmistratorRepository implements IAdministratorRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@SuppressWarnings("unchecked")
	public AdministratorDTO createAdministrator(Administrator administrator) {
		SqlParameterSource input = new MapSqlParameterSource()
				.addValue("firstname", administrator.getFirstname())
				.addValue("lastname", administrator.getLastname())
				.addValue("email", administrator.getEmail())
				.addValue("password", administrator.getPassword());

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("CreateAdministrator")
				.withSchemaName("dbo")
				.returningResultSet("administrator", BeanPropertyRowMapper.newInstance(AdministratorDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return ((List<AdministratorDTO>) output.get("administrator")).get(0);
	}

}
