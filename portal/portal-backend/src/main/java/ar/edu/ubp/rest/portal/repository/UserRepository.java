package ar.edu.ubp.rest.portal.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public String getRoleByEmail(String email) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("email", email);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetUserByEmail")
                .withSchemaName("dbo");

        Map<String, Object> out = simpleJdbcCall.execute(input);

        System.out.println("\n----------------> getRoleByEmail, out: " + out + "<----------------\\n");

        String role = null;

        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
        if (resultSet != null && !resultSet.isEmpty()) {
            Map<String, Object> resultMap = resultSet.get(0);
            role = (String) resultMap.get("role");
        }

        System.out.println("\n----------------> getRoleByEmail, result: " + role + "<----------------\\n");
        return role;
    }

}
