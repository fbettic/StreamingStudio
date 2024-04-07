package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.Fault;

import ar.edu.ubp.soap.beans.FilmBean;

public class FilmManager {
    private Connection getConnection() throws Exception {
        return DatabaseConnection.getConnection();
    }

    public List<FilmBean> getAllFilms() throws Fault {
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("{CALL dbo.GetAllFilms()}");

            try (ResultSet result = stmt.executeQuery()) {
                List<FilmBean> films = new ArrayList<>();

                while (result.next()) {
                    FilmBean film = FilmBean.builder()
                            .filmId(result.getInt("filmId"))
                            .filmCode(result.getString("filmCode"))
                            .title(result.getString("title"))
                            .cover(result.getString("cover"))
                            .description(result.getString("description"))
                            .director(result.getString("director"))
                            .countryCode(result.getString("countryCode"))
                            .year(result.getInt("year"))
                            .actors(result.getString("actors"))
                            .genres(result.getString("genres"))
                            .newContent(result.getBoolean("newContent"))
                            .highlighted(result.getBoolean("highlighted"))
                            .build();

                    films.add(film);
                }
                return films;
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }
}
