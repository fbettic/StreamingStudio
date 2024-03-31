package ar.edu.ubp.rest.plataformasrest.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.plataformasrest.beans.NewPlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.beans.PlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.repositories.interfaces.IPlatformUserRepository;

@Repository
public class PlatformUserRepository implements IPlatformUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public PlatformUserBean creatPlatformUser(NewPlatformUserBean newPlatformUser) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("firstname", newPlatformUser.getFirstname())
                .addValue("lastname", newPlatformUser.getLastname())
                .addValue("email", newPlatformUser.getEmail())
                .addValue("password", newPlatformUser.getPassword())
                .addValue("phone", newPlatformUser.getPhone());

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CreatePlatformUser")
                .withSchemaName("dbo")
                .returningResultSet("user", BeanPropertyRowMapper.newInstance(PlatformUserBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<PlatformUserBean>) out.get("user")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformUserBean getPlatformUserByEmail(String email) {
        SqlParameterSource input = new MapSqlParameterSource()
            .addValue("email", email);
        
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetPlatformUserByEmail")
                .withSchemaName("dbo")
                .returningResultSet("user", BeanPropertyRowMapper.newInstance(PlatformUserBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<PlatformUserBean>) out.get("user")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformUserBean getPlatformUserByToken(String userToken) {
        SqlParameterSource input = new MapSqlParameterSource()
            .addValue("userToken", userToken);
        
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetUserByToken")
                .withSchemaName("dbo")
                .returningResultSet("user", BeanPropertyRowMapper.newInstance(PlatformUserBean.class));

        Map<String, Object> out = jdbcCall.execute(input);

        return ((List<PlatformUserBean>) out.get("user")).get(0);
    }

}
