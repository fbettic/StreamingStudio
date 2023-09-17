package edu.ubp.streamingstudio.backend.streamingstudiobackend.beans;

public class FilmBean {

    private Integer id;
    private String name;
    private String overview;
    private Integer relaseDate;
    private String posterPath;
    private Float popularity;
    private Integer[] genere_ids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(Integer relaseDate) {
        this.relaseDate = relaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public Integer[] getGenere_ids() {
        return genere_ids;
    }

    public void setGenere_ids(Integer[] genere_ids) {
        this.genere_ids = genere_ids;
    }

}
