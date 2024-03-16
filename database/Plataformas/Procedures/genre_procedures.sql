-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
DROP PROCEDURE IF EXISTS CreateFilmGenre
DROP PROCEDURE IF EXISTS GetGenresByFilmId
DROP PROCEDURE IF EXISTS GetAllFilmGenreRelations
DROP TYPE IF EXISTS FilmGenreType
*/

DROP TYPE IF EXISTS FilmGenreType
CREATE TYPE FilmGenreType AS TABLE (
    filmId INT,
    genres VARCHAR(MAX)
)
GO

-- DROP PROCEDURE IF EXISTS CreateFilmGenre
CREATE OR ALTER PROCEDURE CreateFilmGenre
    @genreList VARCHAR(MAX),
    @filmId INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Insertar los nuevos géneros que no existen en la tabla Genre
    INSERT INTO Genre (genre)
    SELECT value AS genre
    FROM string_split(@genreList, ',')
    WHERE value NOT IN (SELECT genre FROM Genre)

    -- Insertar las relaciones en la tabla FilmGenre
    INSERT INTO FilmGenre (filmId, genreId)
    SELECT @filmId, genreId
    FROM Genre
    WHERE genre IN (SELECT value AS genre FROM string_split(@genreList, ','))
END
GO

-- DROP PROCEDURE IF EXISTS GetGenresByFilmId
CREATE OR ALTER PROCEDURE GetGenresByFilmId
    @filmId INT,
    @genreNames VARCHAR(MAX) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT @genreNames = STRING_AGG(CONVERT(NVARCHAR(max), ISNULL(g.genre,'N/A')), ',')
    FROM Genre g
        INNER JOIN FilmGenre fg ON g.genreId = fg.genreId
    WHERE fg.filmId = @filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetAllFilmGenreRelations
CREATE OR ALTER PROCEDURE GetAllFilmGenreRelations
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @FilmGenres AS FilmGenreType;
    
    INSERT INTO @FilmGenres (filmId, genres)
    SELECT fg.filmId, 
        STRING_AGG(CONVERT(NVARCHAR(max), ISNULL(g.genre,'N/A')), ',') AS genres
    FROM FilmGenre fg
    INNER JOIN Genre g ON fg.genreId = g.genreId
    GROUP BY fg.filmId;
    
    SELECT * FROM @FilmGenres;
END
GO

/* 
 EXEC GetAllFilmGenreRelations
*/