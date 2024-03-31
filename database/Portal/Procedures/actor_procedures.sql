USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateFilmActor
DROP PROCEDURE IF EXISTS GetActorsByFilmId
DROP PROCEDURE IF EXISTS GetAllFilmActorRelations
DROP TYPE IF EXISTS FilmActorType
*/

DROP TYPE IF EXISTS FilmActorType
CREATE TYPE FilmActorType AS TABLE (
    filmId INT,
    actors VARCHAR(MAX)
)
GO

-- DROP PROCEDURE IF EXISTS CreateFilmActor
CREATE OR ALTER PROCEDURE CreateFilmActor
    @namesList VARCHAR(MAX),
    @filmId INT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Actor
        (actorName)
    SELECT value
    FROM string_split(@namesList, ',')
    WHERE value NOT IN (SELECT actorName
    FROM Actor)

    INSERT INTO FilmActor
        (filmId, actorId)
    SELECT @filmId, actorId
    FROM Actor
    WHERE actorName IN (SELECT value
    FROM string_split(@namesList, ','))
END
GO

-- DROP PROCEDURE IF EXISTS GetActorsByFilmId
CREATE OR ALTER PROCEDURE GetActorsByFilmId
    @filmId INT,
    @actorNames VARCHAR(MAX) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    -- Concatenar los nombres de los actores asociados al filmId dado
    SELECT @actorNames = STRING_AGG(CONVERT(NVARCHAR(max), ISNULL(a.actorName,'N/A')), ',')
    FROM Actor a
        INNER JOIN FilmActor fa ON a.actorId = fa.actorId
    WHERE fa.filmId = @filmId
END
GO

-- DROP PROCEDURE IF EXISTS GetAllFilmActorRelations
CREATE OR ALTER PROCEDURE GetAllFilmActorRelations
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @FilmActors AS FilmActorType;

    -- Insertar los filmId y sus correspondientes listados de actores en la tabla variable
    INSERT INTO @FilmActors
        (filmId, actors)
    SELECT fa.filmId,
        STRING_AGG(CONVERT(NVARCHAR(max), ISNULL(a.actorName,'N/A')), ',') AS actors
    FROM FilmActor fa
        INNER JOIN Actor a ON fa.actorId = a.actorId
    GROUP BY fa.filmId;

    SELECT *
    FROM @FilmActors;
END
GO

