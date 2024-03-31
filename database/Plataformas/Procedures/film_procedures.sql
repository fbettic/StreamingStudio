-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
    DROP PROCEDURE IF EXISTS CreateFilmIfNotExists
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
    genres VARCHAR(MAX),
    url VARCHAR(MAX),
    newContent BIT,
    highlighted BIT
)
GO

-- DROP PROCEDURE IF EXISTS CreateFilmIfNotExists
CREATE OR ALTER PROCEDURE CreateFilmIfNotExists
    @filmCode CHAR(9),
    @title VARCHAR(255),
    @cover VARCHAR(255),
    @description TEXT,
    @newContent BIT,
    @highlighted BIT,
    @countryCode CHAR(3),
    @directorName VARCHAR(255),
    @year INT,
    @actorNames VARCHAR(MAX),
    @genreNames VARCHAR(MAX),
    @url VARCHAR(255)
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
            (filmCode, title, cover, description,  newContent, highlighted, countryCode, directorId, year, url)
        VALUES
            (@filmCode, @title, @cover, @description,  @newContent, @highlighted, @countryCode, @directorId, @year, @url)

        -- Obtener el ID de la película insertada
        SET @filmId = IDENT_CURRENT('Film')
        
        -- Insertar actores asociados a la película
        EXEC CreateFilmActor @actorNames, @filmId

        -- Insertar géneros asociados a la película
        EXEC CreateFilmGenre @genreNames, @filmId
    END
    ELSE
    BEGIN
        -- Si la película ya existe, devolver su ID
        SET @filmId = @existingFilmId
    END

    EXEC GetFilmById @filmId
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


    EXEC GetActorsByFilmId @filmId, @actorNames OUTPUT


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
        @genreNames AS genres,
        newContent,
        highlighted
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
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

    -- Obtener los nombres de los actores asociados a la película
    INSERT INTO @Actors
        (filmId, actors)
    EXEC GetAllFilmActorRelations

    -- Obtener los nombres de los géneros asociados a la película
    INSERT INTO @Genres
        (filmId, genres)
    EXEC GetAllFilmGenreRelations

    SELECT
        f.filmId AS filmId,
        filmCode,
        title,
        cover,
        description,
        directorName AS director,
        countryCode,
        year,
        actors,
        genres,
        newContent,
        highlighted
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Actors a ON  a.filmId = f.filmId
        JOIN @Genres g ON  g.filmId = f.filmId
    ORDER BY filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetHighlightedFilms
CREATE OR ALTER PROCEDURE GetHighlightedFilms
AS
BEGIN
    DECLARE @Actors AS FilmActorType;
    DECLARE @Genres AS FilmGenreType;

    -- Obtener los nombres de los actores asociados a la película
    INSERT INTO @Actors
        (filmId, actors)
    EXEC GetAllFilmActorRelations

    -- Obtener los nombres de los géneros asociados a la película
    INSERT INTO @Genres
        (filmId, genres)
    EXEC GetAllFilmGenreRelations

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
        highlighted
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Actors a ON  a.filmId = f.filmId
        JOIN @Genres g ON  g.filmId = f.filmId
    WHERE highlighted = 1
    ORDER BY filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetNewFilms
CREATE OR ALTER PROCEDURE GetNewFilms
AS
BEGIN
    DECLARE @Actors AS FilmActorType;
    DECLARE @Genres AS FilmGenreType;

    -- Obtener los nombres de los actores asociados a la película
    INSERT INTO @Actors
        (filmId, actors)
    EXEC GetAllFilmActorRelations

    -- Obtener los nombres de los géneros asociados a la película
    INSERT INTO @Genres
        (filmId, genres)
    EXEC GetAllFilmGenreRelations

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
        newContent
    FROM Film f
        JOIN Director d ON f.directorId = d.directorId
        JOIN @Actors a ON  a.filmId = f.filmId
        JOIN @Genres g ON  g.filmId = f.filmId
    WHERE newContent = 1
    ORDER BY filmId
END
GO
    

/*
EXEC CreateFilmIfNotExists 
    @filmCode = 'ABC123456',
    @title = 'Ejemplo de película 1',
    @cover = 'https://ejemplo.com/cover1.jpg',
    @description = 'Esta es una película de ejemplo 1',
    @directorName = 'Director Nuevo',
    @countryCode = 'USA',
    @year = 2022,
    @actorNames = 'Actor 1, Actor 2',
    @genreNames = 'Acción, Drama';


EXEC CreateFilmIfNotExists 
    @filmCode = 'DEF789012',
    @title = 'Ejemplo de película 2',
    @cover = 'https://ejemplo.com/cover2.jpg',
    @description = 'Esta es una película de ejemplo 2',
    @directorName = 'Director Existente',
    @countryCode = 'GBR',
    @year = 2019,
    @actorNames = 'Actor 3, Actor 4',
    @genreNames = 'Comedia, Romance';


EXEC CreateFilmIfNotExists 
    @filmCode = 'ABC123456',
    @title = 'Película repetida',
    @cover = 'https://ejemplo.com/repeated.jpg',
    @description = 'Esta es una película que ya existe',
    @directorName = 'Director Nuevo',
    @countryCode = 'USA',
    @year = 2022,
    @actorNames = 'Actor 1, Actor 2',
    @genreNames = 'Acción, Drama';


EXEC CreateFilmIfNotExists 
    @filmCode = 'GHI345678',
    @title = 'Nueva Película',
    @cover = 'https://ejemplo.com/nueva_pelicula.jpg',
    @description = 'Una película completamente nueva',
    @directorName = 'Director Existente',
    @countryCode = 'USA',
    @year = 2023,
    @actorNames = 'Actor 5, Actor 6',
    @genreNames = 'Acción, Aventura';


EXEC CreateFilmIfNotExists 
    @filmCode = 'DEF789012',
    @title = 'Nueva Versión de la Película 2',
    @cover = 'https://ejemplo.com/cover2_v2.jpg',
    @description = 'Una versión revisada de la película 2',
    @directorName = 'Director Existente',
    @countryCode = 'GBR',
    @year = 2020,
    @actorNames = 'Actor 3, Actor 4',
    @genreNames = 'Comedia, Drama';

EXEC CreateFilmIfNotExists 
    @filmCode = 'DEF789032',
    @title = 'Nueva Versión de la Película 3',
    @cover = 'https://ejemplo.com/cover3_v2.jpg',
    @description = 'Una versión revisada de la película 3',
    @directorName = 'Director Existente',
    @countryCode = 'GBR',
    @year = 2020,
    @actorNames = 'Actor 3, Actor 4',
    @genreNames = 'Comedia, Drama';

EXEC CreateFilmIfNotExists 
    @filmCode = 'DEF7890S2',
    @title = 'Nueva Versión de la Película 3',
    @cover = 'https://ejemplo.com/cover3_v2.jpg',
    @description = 'Una versión revisada de la película 3',
    @directorName = 'Director Existente',
    @countryCode = 'GBR',
    @year = 2020,
    @actorNames = 'Actor 3, Actor 4',
    @genreNames = 'Comedia, Drama';


EXEC GetAllFilms

EXEC GetHighlightedFilms
EXEC GetNewFilms
GO

EXEC GetFilmById 1

DECLARE @actors VARCHAR(MAX);
Exec GetActorsByFilmId 1, @actors output
*/
