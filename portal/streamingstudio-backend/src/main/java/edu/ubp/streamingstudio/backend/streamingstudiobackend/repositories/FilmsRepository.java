package edu.ubp.streamingstudio.backend.streamingstudiobackend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;

@Repository
public class FilmsRepository implements IFilmsRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Autowired
    // private NamedParameterJdbcTemplate namedParamJdbcTpl;

    @Override
    public List<FilmBean> getFilms() {
        return jdbcTpl.query("select *"
                + " from dbo.films (nolock)\r\n"
                + "order by title", BeanPropertyRowMapper.newInstance(FilmBean.class));
    }

}
