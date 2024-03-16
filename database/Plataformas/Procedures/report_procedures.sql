-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
DROP PROCEDURE IF EXISTS CreateWeeklyReport
DROP PROCEDURE IF EXISTS CreateWeeklyGenreReport
DROP PROCEDURE IF EXISTS CreateWeeklyFilmReport
DROP PROCEDURE IF EXISTS GetWeeklyReportById
*/

-- DROP PROCEDURE IF EXISTS CreateWeeklyReport
CREATE OR ALTER PROCEDURE CreateWeeklyReport
    @serviceId INT,
    @signupQuantity INT,
    @associationsQuantity INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    -- Insertar el nuevo informe en la tabla WeeklyReport
    INSERT INTO WeeklyReport (serviceId, signupQuantity, associationsQuantity, fromDate, toDate)
    VALUES (@serviceId, @signupQuantity, @associationsQuantity, @fromDate, @toDate)

    -- Obtener el ID del informe creado
    DECLARE @reportId INT
    SET @reportId = SCOPE_IDENTITY()

    -- Devolver el informe recién creado
    EXEC GetWeeklyReportById @reportId
END
GO

-- DROP PROCEDURE IF EXISTS CreateWeeklyGenreReport
CREATE OR ALTER PROCEDURE CreateWeeklyGenreReport
    @reportId INT,
    @genreId INT,
    @views INT
AS
BEGIN
    -- Insertar el nuevo informe de género en la tabla WeeklyGenreReport
    INSERT INTO WeeklyGenreReport (reportId, genreId, views)
    VALUES (@reportId, @genreId, @views)

    -- No es necesario devolver el informe de género creado, ya que no se solicita explícitamente
END
GO

-- DROP PROCEDURE IF EXISTS CreateWeeklyFilmReport
CREATE OR ALTER PROCEDURE CreateWeeklyFilmReport
    @filmId INT,
    @reportId INT,
    @views INT
AS
BEGIN
    -- Insertar el nuevo informe de película en la tabla WeeklyFilmReport
    INSERT INTO WeeklyFilmReport (filmId, reportId, views)
    VALUES (@filmId, @reportId, @views)

    -- No es necesario devolver el informe de película creado, ya que no se solicita explícitamente
END
GO

-- DROP PROCEDURE IF EXISTS GetWeeklyReportById
CREATE OR ALTER PROCEDURE GetWeeklyReportById
    @reportId INT
AS
BEGIN
    SELECT reportId, serviceId, signupQuantity, associationsQuantity, fromDate, toDate
    FROM WeeklyReport
    WHERE reportId = @reportId
END
GO
