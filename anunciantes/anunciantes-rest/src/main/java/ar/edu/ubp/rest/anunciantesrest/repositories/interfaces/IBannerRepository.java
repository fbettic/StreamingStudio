package ar.edu.ubp.rest.anunciantesrest.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;

public interface IBannerRepository {
    public BannerBean getBannerById(Integer id); 
} 