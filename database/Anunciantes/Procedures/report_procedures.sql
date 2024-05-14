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
    clicks NVARCHAR(MAX)
  );

    -- Insert JSON data into temporary table
    INSERT INTO #TempData
    (authToken, reportId, fromDate, toDate, clicks)
  SELECT authToken, reportId, fromDate, toDate, clicks
  FROM OPENJSON(@json)
    WITH (
        authToken NVARCHAR(255) '$.authToken',
        reportId INT '$.reportId',
        fromDate BIGINT '$.fromDate',
        toDate BIGINT '$.toDate',
        clicks NVARCHAR(MAX) '$.clicks' AS JSON
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
    INSERT INTO WeeklyAdvertisingClickReport
    (reportId, bannerId, subscriberId, clickedAt)
  SELECT @insertedReportId, bannerId, subscriberId, dbo.ConvertirUnixTimestamp(clickedAt)
  FROM OPENJSON((SELECT clicks FROM #TempData))
    WITH (
        bannerId INT '$.bannerId',
        clickedAt BIGINT '$.clickedAt',
        subscriberId INT '$.subscriberId'
    );

    -- Drop temporary table
    DROP TABLE #TempData;

    SELECT 'Success' AS Result;
  END TRY
  BEGIN CATCH
    -- Error
    SELECT ERROR_MESSAGE() AS Result;
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
GO


