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

import ar.edu.ubp.rest.portal.dto.AdministratorDTO;
import ar.edu.ubp.rest.portal.models.Administrator;
import ar.edu.ubp.rest.portal.repositories.interfaces.IAdministratorRepository;

@Repository
public class AdmistratorRepository implements IAdministratorRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public AdministratorDTO findAdministratorByEmail(String email) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAdministratorByEmail'");
	}

	@Override
	@SuppressWarnings("unchecked")
	public AdministratorDTO save(Administrator administrator) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("firstname", administrator.getFirstname())
				.addValue("lastname", administrator.getLastname())
				.addValue("email", administrator.getEmail())
				.addValue("password", administrator.getPassword());

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("InsertAdministrator")
				.withSchemaName("dbo")
				.returningResultSet("administrator", BeanPropertyRowMapper.newInstance(AdministratorDTO.class));

		Map<String, Object> out = jdbcCall.execute(in);
		return ((List<AdministratorDTO>) out.get("administrator")).get(0);
	}

}
