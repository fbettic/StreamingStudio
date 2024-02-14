package edu.ubp.streamingstudio.backend.streamingstudiobackend.repositories;

import java.util.List;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;

public interface IFilmsRepository{
    public List<FilmBean> getFilms();
}