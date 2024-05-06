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
    @filmCode VARCHAR(255),
    @platformId INT,
    @newContent BIT,
    @highlighted BIT
AS
BEGIN
    DECLARE @filmId INT;

    SELECT @filmId = filmId
    FROM Film
    WHERE filmCode = @filmCode

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

-- DROP PROCEDURE IF EXISTS ProcessPlatformFilmJson
CREATE OR ALTER PROCEDURE ProcessPlatformFilmJson
    @jsonData NVARCHAR(MAX)
AS
BEGIN
    BEGIN TRY
    DECLARE @tempTable TABLE (
        filmId INT,
        platformId INT,
        newContent BIT,
        highlighted BIT
    );

    -- Insertar los datos JSON en una tabla temporal
    INSERT INTO @tempTable
        (filmId, platformId, newContent, highlighted)
    SELECT filmId, platformId, newContent, highlighted
    FROM Film f
        JOIN OPENJSON(@jsonData)
    WITH (
      filmCode VARCHAR(255) '$.filmCode',
      platformId INT '$.platformId',
      newContent BIT '$.newContent',
      highlighted BIT '$.highlighted'
    ) AS js on f.filmCode = js.filmCode;
    
    -- Actualizar los registros existentes
    UPDATE pf
    SET pf.newContent = tt.newContent,
        pf.highlighted = tt.highlighted
    FROM PlatformFilm pf
        INNER JOIN @tempTable tt ON pf.filmId = tt.filmId AND pf.platformId = tt.platformId;

    -- Insertar nuevos registros
    INSERT INTO PlatformFilm
        (filmId, platformId, newContent, highlighted)
    SELECT tt.filmId, tt.platformId, tt.newContent, tt.highlighted
    FROM @tempTable tt
        LEFT JOIN PlatformFilm pf ON tt.filmId = pf.filmId AND tt.platformId = pf.platformId
    WHERE pf.filmId IS NULL AND pf.platformId IS NULL;

    -- Eliminar registros que ya no están en la lista JSON
    DELETE FROM PlatformFilm
    WHERE NOT EXISTS (
      SELECT 1
    FROM @tempTable tt
    WHERE PlatformFilm.filmId = tt.filmId AND PlatformFilm.platformId = tt.platformId
    );

    -- Éxito
    SELECT 'Success' AS Result;
  END TRY
  BEGIN CATCH
    -- Error
    SELECT ERROR_MESSAGE() AS Result;
  END CATCH

END
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

CREATE OR ALTER PROCEDURE DropAllPlatformFilmRelations
AS
BEGIN

    DELETE FROM PlatformFilm;
    SELECT @@ROWCOUNT AS "deleted"
END



EXEC GetAllFilms





