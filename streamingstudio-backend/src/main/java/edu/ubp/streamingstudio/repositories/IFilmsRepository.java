package edu.ubp.streamingstudio.repositories;

import java.util.List;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;

public interface IFilmsRepository{
    public List<FilmBean> getFilms();
}