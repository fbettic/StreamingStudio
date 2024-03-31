package ar.edu.ubp.rest.plataformasrest.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.rest.plataformasrest.beans.FilmBean;
import ar.edu.ubp.rest.plataformasrest.repositories.interfaces.IFilmRepository;

@Repository
public class FilmRepository implements IFilmRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public List<FilmBean> getAllFilms() {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetAllFilms")
                .withSchemaName("dbo")
                .returningResultSet("films", BeanPropertyRowMapper.newInstance(FilmBean.class));

        Map<String, Object> out = jdbcCall.execute();

        return (List<FilmBean>) out.get("films");
    }

}
