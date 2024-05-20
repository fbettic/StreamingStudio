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
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.repositories.interfaces.IFilmRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FilmRepository implements IFilmRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String loadAllCountries(List<CountryDTO> countries) {
        try {
            String sql = "EXEC CreateCountries @countryCode = ?, @countryName = ?";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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

            return "Success";

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String updateBatchFilm(List<FilmDTO> films) {
        try {
            String sql = "EXEC CreateFilmIfNotExists @filmCode = ?, @title = ?, @cover = ?, @description = ?, @directorName = ?, @countryCode = ?, @year = ?, @actorNames = ?, @genreNames = ?";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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

            return "Success";

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public String updateBatchPlatformFilm(String json) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("json", json);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("UpdatePlatformFilmFromJson")
                .withSchemaName("dbo")
                .returningResultSet("message", BeanPropertyRowMapper.newInstance(String.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<String>) output.get("message")).get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FilmSubscriberResponseDTO> getAllFilms(Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllFilms")
                .withSchemaName("dbo")
                .returningResultSet("films", BeanPropertyRowMapper.newInstance(FilmSubscriberResponseDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<FilmSubscriberResponseDTO>) output.get("films");
    }

    @Override
    @SuppressWarnings("unchecked")
    public FilmSubscriberResponseDTO getFilmById(Integer filmId, Integer susbcriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("filmId", filmId)
                .addValue("subscriberId", susbcriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetFilmById")
                .withSchemaName("dbo")
                .returningResultSet("film", BeanPropertyRowMapper.newInstance(FilmSubscriberResponseDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return ((List<FilmSubscriberResponseDTO>) output.get("film")).get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FilmSubscriberResponseDTO> getHighlightedFilms(Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetHighlightedFilms")
                .withSchemaName("dbo")
                .returningResultSet("films", BeanPropertyRowMapper.newInstance(FilmSubscriberResponseDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<FilmSubscriberResponseDTO>) output.get("films");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FilmSubscriberResponseDTO> getNewFilms(Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetNewFilms")
                .withSchemaName("dbo")
                .returningResultSet("films", BeanPropertyRowMapper.newInstance(FilmSubscriberResponseDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<FilmSubscriberResponseDTO>) output.get("films");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FilmSubscriberResponseDTO> getMostViewedFilms(Integer subscriberId) {
        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetMostViewedFilms")
                .withSchemaName("dbo")
                .returningResultSet("films", BeanPropertyRowMapper.newInstance(FilmSubscriberResponseDTO.class));

        Map<String, Object> output = jdbcCall.execute(input);
        return (List<FilmSubscriberResponseDTO>) output.get("films");
    }

}
