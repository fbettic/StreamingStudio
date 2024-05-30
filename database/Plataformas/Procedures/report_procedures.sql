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

-- DROP PROCEDURE IF EXISTS CreateWeeklyReportFromJson
CREATE OR ALTER PROCEDURE CreateWeeklyReportFromJson
    @json NVARCHAR(MAX)
AS
BEGIN
    BEGIN TRY
    -- Create temporary table to hold JSON data
    CREATE TABLE #TempData
    (
        authToken NVARCHAR(255),
        reportId INT,
        fromDate BIGINT,
        toDate BIGINT,
        reproductions NVARCHAR(MAX),
        associations NVARCHAR(MAX)
    );

    -- Insert JSON data into temporary table
    INSERT INTO #TempData
        (authToken, reportId, fromDate, toDate, reproductions, associations)
    SELECT authToken, reportId, fromDate, toDate, reproductions, associations
    FROM OPENJSON(@json)
    WITH (
        authToken NVARCHAR(255) '$.authToken',
        reportId INT '$.reportId',
        fromDate BIGINT '$.fromDate',
        toDate BIGINT '$.toDate',
        reproductions NVARCHAR(MAX) '$.reproductions' AS JSON,
        associations NVARCHAR(MAX) '$.associations' AS JSON
    );

    INSERT INTO WeeklyReport
        (serviceId, fromDate, toDate)
    SELECT serviceId, dbo.ConvertirUnixTimestamp(fromDate), dbo.ConvertirUnixTimestamp(toDate)
    FROM ServiceConnection sc
        JOIN #TempData td ON td.authToken = sc.authToken;

    -- Get inserted reportId
    DECLARE @insertedReportId INT;
    SELECT @insertedReportId = SCOPE_IDENTITY();

    -- Insert data into PlayRegisterReport table
    INSERT INTO PlayRegisterReport
        (reportId, sessionId, playedAt, subscriberId, transactionId, filmCode, sessionUrl)
    SELECT @insertedReportId, sessionId, dbo.ConvertirUnixTimestamp(playedAt), subscriberId, transactionId, filmCode, sessionUrl
    FROM OPENJSON((SELECT reproductions FROM #TempData))
    WITH (
        sessionId INT '$.sessionId',
        playedAt BIGINT '$.playedAt',
        subscriberId INT '$.subscriberId',
        transactionId INT '$.transactionId',
        filmCode VARCHAR(MAX) '$.filmCode',
        sessionUrl VARCHAR(MAX) '$.sessionUrl'
    );

    -- Insert data into AssociationRegisterReport table
    INSERT INTO AssociationRegisterReport
        (reportId, subscriberId, transactionId, associationType, authUrl, entryDate, leavingDate)
    SELECT @insertedReportId, subscriberId, transactionId, associationType, authUrl, dbo.ConvertirUnixTimestamp(entryDate), CASE WHEN leavingDate IS NOT NULL THEN  dbo.ConvertirUnixTimestamp(leavingDate) ELSE NULL END
    FROM OPENJSON((SELECT associations FROM #TempData))
    WITH (
        subscriberId INT '$.subscriberId',
        transactionId INT '$.transactionId',
        associationType VARCHAR(MAX) '$.associationType',
        authUrl VARCHAR(MAX) '$.authUrl',
        entryDate BIGINT '$.entryDate',
        leavingDate BIGINT '$.leavingDate'
    );

    -- Drop temporary table
    DROP TABLE #TempData;

    SELECT 'Success' AS response;
  END TRY
  BEGIN CATCH
    -- Error
    SELECT ERROR_MESSAGE() AS response;
  END CATCH
END;
GO


CREATE OR ALTER FUNCTION ConvertirUnixTimestamp(@timestamp bigint)
RETURNS datetime
AS
BEGIN
    DECLARE @fechaInicioUnix datetime = '1970-01-01 00:00:00';
    DECLARE @fechaInicioSQLServer datetime = '1900-01-01 00:00:00';

    DECLARE @fechaHora datetime;

    SET @fechaHora = (
        SELECT
        CONVERT(datetime, DATEADD(s, @timestamp / 1000, @fechaInicioUnix) + 
                    DATEADD(ms, @timestamp % 1000, @fechaInicioSQLServer), 20)
    );

    RETURN @fechaHora;
END;
