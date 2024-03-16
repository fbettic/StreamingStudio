USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateCountries
DROP PROCEDURE IF EXISTS CreateDirectorAndGetId
*/



------------------- Country procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateCountries
CREATE OR ALTER PROCEDURE CreateCountries
    @countryCode CHAR(3),
    @countryName VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Country
        (countryName, countryCode)
    VALUES
        (@countryName, @countryCode)
END
GO


------------------- Director procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateDirectorAndGetId
CREATE OR ALTER PROCEDURE CreateDirectorAndGetId
    @directorName VARCHAR(255),
    @directorId INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    -- Variable para almacenar el ID del director
    DECLARE @existingDirectorId INT

    -- Verificar si el director ya existe en la tabla Director
    SELECT @existingDirectorId = directorId
    FROM Director
    WHERE directorName = @directorName

    IF @existingDirectorId IS NULL
    BEGIN
        -- Si el director no existe, insertarlo en la tabla Director
        INSERT INTO Director
            (directorName)
        VALUES
            (@directorName)
        SET @directorId = SCOPE_IDENTITY()
    -- Obtener el ID del nuevo director
    END
    ELSE
    BEGIN
        -- Si el director ya existe, devolver su ID
        SET @directorId = @existingDirectorId
    END
END
GO
