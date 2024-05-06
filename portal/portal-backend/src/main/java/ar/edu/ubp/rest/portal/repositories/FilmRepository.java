package ar.edu.ubp.rest.portal.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
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
    public Integer updateBatchFilm(List<FilmDTO> films) {
        String sql = "EXEC CreateFilmIfNotExists @filmCode = ?, @title = ?, @cover = ?, @description = ?, @directorName = ?, @countryCode = ?, @year = ?, @actorNames = ?, @genreNames = ?";
        int[] affectedRows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                FilmDTO film = films.get(i);
                ps.setString(1, film.getFilmCode());
                ps.setString(2, film.getTitle());
                ps.setString(3, film.getCover());
                ps.setString(4, film.getDescription());
                ps.setString(5, film.getDirector());
                ps.setString(6, film.getCountryCode());
                ps.setInt(7, film.getYear());
                ps.setString(8, film.getActors());
                ps.setString(9, film.getGenres());
            }

            @Override
            public int getBatchSize() {
                return films.size();
            }
        });

        int totalAffectedRows = 0;
        for (int rows : affectedRows) {
            totalAffectedRows += rows;
        }

        return totalAffectedRows;
    }

    @Override
    public Integer dropAllPlatformFilmRelations() {
        SqlParameterSource input = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("DropAllPlatformFilmRelations")
                .withSchemaName("dbo")
                .declareParameters(new SqlOutParameter("deleted", Types.INTEGER));

    Map<String, Object> output = jdbcCall.execute(input);
    return (Integer) output.get("deleted");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String updateBatchPlatformFilm(String platformFilmsJson) {
        
        SqlParameterSource input = new MapSqlParameterSource()
        .addValue("jsonData", platformFilmsJson);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ProcessPlatformFilmJson")
                .withSchemaName("dbo")
                .returningResultSet("message", BeanPropertyRowMapper.newInstance(String.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<String>) output.get("message")).get(0);
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


    @Override
    @SuppressWarnings("unchecked")
    public FilmSubscriberResponseDTO getFilmById(Integer id) {
        SqlParameterSource input = new MapSqlParameterSource()
        .addValue("filmId", id);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetFilmById")
                .withSchemaName("dbo")
                .returningResultSet("film", BeanPropertyRowMapper.newInstance(FilmSubscriberResponseDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<FilmSubscriberResponseDTO>) output.get("film")).get(0);
    }

}
