package ar.edu.ubp.rest.portal.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IFilmRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FilmRepository implements IFilmRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Integer loadAllCountries(List<CountryDTO> countries) {
        String sql = "EXEC CreateCountries @countryCode = ?, @countryName = ?";

        int[] affectedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CountryDTO country = countries.get(i);
                ps.setString(1, country.getCountryCode());
                ps.setString(2, country.getCountryName());
            }

            @Override
            public int getBatchSize() {
                return countries.size();
            }
        });
        
        int totalAffectedRows = 0;
        for (int rows : affectedRows) {
            totalAffectedRows += rows;
        }
        
        return totalAffectedRows;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<FilmDTO> getAllFilms() {
        SqlParameterSource input = new MapSqlParameterSource();
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("GetAllFilms")
				.withSchemaName("dbo")
				.returningResultSet("films", BeanPropertyRowMapper.newInstance(FilmDTO.class));

		Map<String, Object> output = jdbcCall.execute(input);
		return (List<FilmDTO>) output.get("films");
    }

}
