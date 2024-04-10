USE portal_db
GO

/*
    DROP PROCEDURE IF EXISTS CreateFilmIfNotExists
    DROP PROCEDURE IF EXISTS GetFilmWithoutPlatformsById
    DROP PROCEDURE IF EXISTS GetFilmById
    DROP PROCEDURE IF EXISTS GetAllFilms
    DROP PROCEDURE IF EXISTS GetHighlightedFilms
    DROP PROCEDURE IF EXISTS GetNewFilms
    DROP TYPE IF EXISTS FilmType
*/

DROP TYPE IF EXISTS FilmType
CREATE TYPE FilmType AS TABLE (
    filmId INT,
    filmCode CHAR(9),
    title VARCHAR(255),
    cover VARCHAR(255),
    description NVARCHAR(MAX),
    director VARCHAR(255),
    countryCode CHAR(3),
    year INT,
    actors VARCHAR(MAX),
    genres VARCHAR(MAX)
)
GO

-- DROP PROCEDURE IF EXISTS CreateFilmIfNotExists
CREATE OR ALTER PROCEDURE CreateFilmIfNotExists
    @filmCode CHAR(9),
    @title VARCHAR(255),
    @cover VARCHAR(255),
    @description TEXT,
    @directorName VARCHAR(255),
    @countryCode CHAR(3),
    @year INT,
    @actorNames VARCHAR(MAX),
    @genreNames VARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @filmId INT
    DECLARE @directorId INT
    DECLARE @existingFilmId INT

    -- Verificar si la película ya existe en la tabla Film
    SELECT @existingFilmId = filmId
    FROM Film
    WHERE filmCode = @filmCode

    IF @existingFilmId IS NULL
    BEGIN
        -- Insertar el director y obtener su ID
        EXEC CreateDirectorAndGetId @directorName, @directorId OUTPUT

        -- Insertar la película en la tabla Film
        INSERT INTO Film
            (filmCode, title, cover, description, directorId, countryCode, year)
        VALUES
            (@filmCode, @title, @cover, @description, @directorId, @countryCode, @year)

        -- Obtener el ID de la película insertada
        SET @filmId = IDENT_CURRENT('Film')

        -- Insertar actores asociados a la película
        EXEC CreateFilmActor @actorNames, @filmId

        -- Insertar géneros asociados a la película
        EXEC CreateFilmGenre @genreNames, @filmId
    END
END
GO


-- DROP PROCEDURE IF EXISTS GetFilmWithoutPlatformsById
CREATE OR ALTER PROCEDURE GetFilmWithoutPlatformsById
    @filmId INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @actorNames VARCHAR(MAX)
    DECLARE @genreNames VARCHAR(MAX)

    -- Obtener los nombres de los actores asociados a la película
    EXEC GetActorsByFilmId @filmId, @actorNames OUTPUT

    -- Obtener los nombres de los géneros asociados a la película
    EXEC GetGenresByFilmId @filmId, @genreNames OUTPUT

    SELECT
        f.filmId,
        filmCode,
        title,
        cover,
        description,
        directorName AS director,
        countryCode,
        year,
        @actorNames AS actors,
        @genreNames AS genres
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
    WHERE f.filmId = @filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetFilmById
CREATE OR ALTER PROCEDURE GetFilmById
    @filmId INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @actorNames VARCHAR(MAX)
    DECLARE @genreNames VARCHAR(MAX)
    DECLARE @Platform AS FilmPlatformType;

    -- Obtener los nombres de los actores asociados a la película
    EXEC GetActorsByFilmId @filmId, @actorNames OUTPUT

    -- Obtener los nombres de los géneros asociados a la película
    EXEC GetGenresByFilmId @filmId, @genreNames OUTPUT

    -- Obtener los nombres de las plataformas asociadas a la película
    INSERT INTO @Platform
        (filmId, platforms, highlightedBy, newOn)
    EXEC GetPlatformFilmRelationsByFilmId @filmId


    SELECT
        f.filmId,
        filmCode,
        title,
        cover,
        description,
        directorName AS director,
        countryCode,
        year,
        @actorNames AS actors,
        @genreNames AS genres,
        platforms,
        highlightedBy,
        newOn
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Platform p ON p.filmId = f.filmId
    WHERE f.filmId = @filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetAllFilms
CREATE OR ALTER PROCEDURE GetAllFilms
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @Actors AS FilmActorType;
    DECLARE @Genres AS FilmGenreType;
    DECLARE @Platforms AS FilmPlatformType;

    -- Obtener los nombres de los actores asociados a la película
    INSERT INTO @Actors
        (filmId, actors)
    EXEC GetAllFilmActorRelations

    -- Obtener los nombres de los géneros asociados a la película
    INSERT INTO @Genres
        (filmId, genres)
    EXEC GetAllFilmGenreRelations

    -- Obtener los nombres de las plataformas asociadas a la película
    INSERT INTO @Platforms
        (filmId, platforms, highlightedBy, newOn)
    EXEC  GetAllPlatformFilmRelations


    SELECT
        f.filmId,
        filmCode,
        title,
        cover,
        description,
        directorName AS director,
        countryCode,
        year,
        actors,
        genres,
        platforms,
        highlightedBy,
        newOn
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Actors a ON  a.filmId = f.filmId
        JOIN @Genres g ON  g.filmId = f.filmId
        JOIN @Platforms p ON p.filmId = f.filmId
    ORDER BY filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetHighlightedFilms
CREATE OR ALTER PROCEDURE GetHighlightedFilms
AS
BEGIN
    DECLARE @Actors AS FilmActorType;
    DECLARE @Genres AS FilmGenreType;
    DECLARE @Platforms AS FilmPlatformType;

    -- Obtener los nombres de los actores asociados a la película
    INSERT INTO @Actors
        (filmId, actors)
    EXEC GetAllFilmActorRelations

    -- Obtener los nombres de los géneros asociados a la película
    INSERT INTO @Genres
        (filmId, genres)
    EXEC GetAllFilmGenreRelations

    -- Obtener los nombres de las plataformas asociadas a la película
    INSERT INTO @Platforms
        (filmId, platforms, highlightedBy, newOn)
    EXEC  GetAllPlatformFilmRelations


    SELECT
        f.filmId,
        filmCode,
        title,
        cover,
        description,
        directorName AS director,
        countryCode,
        year,
        actors,
        genres,
        platforms,
        highlightedBy
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Actors a ON  a.filmId = f.filmId
        JOIN @Genres g ON  g.filmId = f.filmId
        JOIN @Platforms p ON p.filmId = f.filmId
    WHERE p.highlightedBy IS NOT NULL
    ORDER BY filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetNewFilms
CREATE OR ALTER PROCEDURE GetNewFilms
AS
BEGIN
    DECLARE @Actors AS FilmActorType;
    DECLARE @Genres AS FilmGenreType;
    DECLARE @Platforms AS FilmPlatformType;

    -- Obtener los nombres de los actores asociados a la película
    INSERT INTO @Actors
        (filmId, actors)
    EXEC GetAllFilmActorRelations

    -- Obtener los nombres de los géneros asociados a la película
    INSERT INTO @Genres
        (filmId, genres)
    EXEC GetAllFilmGenreRelations

    -- Obtener los nombres de las plataformas asociadas a la película
    INSERT INTO @Platforms
        (filmId, platforms, highlightedBy, newOn)
    EXEC  GetAllPlatformFilmRelations


    SELECT
        f.filmId,
        filmCode,
        title,
        cover,
        description,
        directorName AS director,
        countryCode,
        year,
        actors,
        genres,
        platforms,
        newOn
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Actors a ON  a.filmId = f.filmId
        JOIN @Genres g ON  g.filmId = f.filmId
        JOIN @Platforms p ON p.filmId = f.filmId
    WHERE p.newOn IS NOT NULL
    ORDER BY filmId
END
GO
    

