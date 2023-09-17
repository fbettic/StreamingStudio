package edu.ubp.streamingstudio.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;

@Repository
public class FilmsRepository implements IFilmsRepository {

    @Override
    public List<FilmBean> getFilms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFilms'");
    }
    
}
