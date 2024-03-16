-- USE anunciante1_db
-- USE anunciante2_db
USE anunciante3_db
GO

/*
DROP PROCEDURE IF EXISTS CreateWeeklyReport
DROP PROCEDURE IF EXISTS CreateWeeklyPriorityReport
DROP PROCEDURE IF EXISTS CreateWeeklyAdvertisingReport
DROP PROCEDURE IF EXISTS GetWeeklyReportById
*/

-- DROP PROCEDURE IF EXISTS CreateWeeklyReport
CREATE OR ALTER PROCEDURE CreateWeeklyReport
    @serviceId INT,
    @totalClicks INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    -- Insertar el nuevo informe en la tabla WeeklyReport
    INSERT INTO WeeklyReport (serviceId, totalClicks, fromDate, toDate)
    VALUES (@serviceId, @totalClicks, @fromDate, @toDate)

    -- Obtener el ID del informe creado
    DECLARE @reportId INT
    SET @reportId = SCOPE_IDENTITY()

    -- Devolver el informe recién creado
    EXEC GetWeeklyReportById @reportId
END
GO

-- DROP PROCEDURE IF EXISTS CreateWeeklyPriorityReport
CREATE OR ALTER PROCEDURE CreateWeeklyPriorityReport
    @reportId INT,
    @priorityId INT,
    @clicks INT
AS
BEGIN
    -- Insertar el nuevo informe de prioridad en la tabla WeeklyPriorityReport
    INSERT INTO WeeklyPriorityReport (reportId, priorityId, clicks)
    VALUES (@reportId, @priorityId, @clicks)

    -- No es necesario devolver el informe de prioridad creado, ya que no se solicita explícitamente
END
GO

-- DROP PROCEDURE IF EXISTS CreateWeeklyAdvertisingReport
CREATE OR ALTER PROCEDURE CreateWeeklyAdvertisingReport
    @reportId INT,
    @advertisingId INT,
    @clicks INT
AS
BEGIN
    -- Insertar el nuevo informe de publicidad en la tabla WeeklyAdvertisingReport
    INSERT INTO WeeklyAdvertisingReport (reportId, advertisingId, clicks)
    VALUES (@reportId, @advertisingId, @clicks)

    -- No es necesario devolver el informe de publicidad creado, ya que no se solicita explícitamente
END
GO

-- DROP PROCEDURE IF EXISTS GetWeeklyReportById
CREATE OR ALTER PROCEDURE GetWeeklyReportById
    @reportId INT
AS
BEGIN
    SELECT reportId, serviceId, totalClicks, fromDate, toDate
    FROM WeeklyReport
    WHERE reportId = @reportId
END
GO
