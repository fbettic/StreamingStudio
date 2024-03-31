package ar.edu.ubp.rest.plataformasrest.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.plataformasrest.beans.FilmBean;

public interface IFilmRepository {
    public List<FilmBean> getAllFilms();    
}
