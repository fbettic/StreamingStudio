USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreatePlatformFilm
DROP PROCEDURE IF EXISTS UpdatePlatformFilm
DROP PROCEDURE IF EXISTS GetPlatformFilmRelationsByFilmId
DROP PROCEDURE IF EXISTS GetAllPlatformFilmRelationsd
DROP TYPE IF EXITS FilmPlatformType
*/

DROP TYPE IF EXISTS FilmPlatformType
CREATE TYPE FilmPlatformType AS TABLE (
    filmId INT,
    platforms VARCHAR(MAX),
    highlightedBy VARCHAR(MAX),
    newOn VARCHAR(MAX)
)
GO

-- DROP PROCEDURE IF EXISTS CreatePlatformFilm
CREATE OR ALTER PROCEDURE CreatePlatformFilm
    @filmId INT,
    @platformId INT,
    @newContent BIT,
    @highlighted BIT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1 
        FROM StreamingPlatform 
        WHERE platformId = @platformId AND deletedAt IS NULL
    )
    BEGIN;
        THROW 50004, 'The specified platform does not exist or has been removed.', 1;
    END

    INSERT INTO PlatformFilm
        (filmId, platformId, newContent, highlighted)
    VALUES
        (@filmId, @platformId, @newContent, @highlighted)
END;
GO

-- DROP PROCEDURE IF EXISTS UpdatePlatformFilm
CREATE OR ALTER PROCEDURE UpdatePlatformFilm
    @filmId INT,
    @platformId INT,
    @newContent BIT,
    @highlighted BIT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1 
        FROM StreamingPlatform 
        WHERE platformId = @platformId AND deletedAt IS NULL
    )
    BEGIN;
        THROW 50004, 'La plataforma especificada no existe o ha sido eliminada.', 1;
    END

    UPDATE PlatformFilm
    SET
        newContent = @newContent,
        highlighted = @highlighted
    WHERE
        filmId = @filmId AND
        platformId = @platformId
END;
GO

-- DROP PROCEDURE IF EXISTS GetPlatformFilmRelationsByFilmId
CREATE OR ALTER PROCEDURE GetPlatformFilmRelationsByFilmId
     @filmId INT
AS
BEGIN
    SELECT 
        pf.filmId,
        platforms = STRING_AGG(sp.platformName, ','),
        highlightedBy = STRING_AGG(CASE WHEN pf.highlighted = 1 THEN sp.platformName END, ','),
        newOn = STRING_AGG(CASE WHEN pf.newContent = 1 THEN sp.platformName END, ',')
    FROM 
        PlatformFilm pf
    JOIN 
        StreamingPlatform sp ON pf.platformId = sp.platformId
    WHERE 
        pf.filmId = @filmId
    AND sp.deletedAt IS NULL
    GROUP BY 
        pf.filmId;
END
GO 

-- DROP PROCEDURE IF EXISTS GetAllPlatformFilmRelations
CREATE OR ALTER PROCEDURE GetAllPlatformFilmRelations
AS
BEGIN
    SELECT 
        pf.filmId,
        platforms = STRING_AGG(sp.platformName, ','),
        highlightedBy = STRING_AGG(CASE WHEN pf.highlighted = 1 THEN sp.platformName END, ','),
        newOn = STRING_AGG(CASE WHEN pf.newContent = 1 THEN sp.platformName END, ',')
    FROM 
        PlatformFilm pf
    JOIN 
        StreamingPlatform sp ON pf.platformId = sp.platformId
    WHERE deletedAt IS NULL
    GROUP BY 
        pf.filmId;
END
GO









