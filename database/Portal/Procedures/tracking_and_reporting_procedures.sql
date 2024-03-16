USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateAdvertisingClickReports
DROP PROCEDURE IF EXISTS GetAdvertisingClickReportsByReportId
DROP PROCEDURE IF EXISTS CreateWeeklyAdvertiserReport
DROP PROCEDURE IF EXISTS GetWeeklyAdvertiserReport
DROP PROCEDURE IF EXISTS CreateSubscriberAdvertisingClick
DROP PROCEDURE IF EXISTS CreateWeeklyPlatformFilmReport
DROP PROCEDURE IF EXISTS GetWeeklyPlatformFilmReport
DROP PROCEDURE IF EXISTS CreatePlayRegisterReports
DROP PROCEDURE IF EXISTS GetPlayRegisterReportsByReportId
*/

------------------- AdvertisingClickReports procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateAdvertisingClickReports
CREATE OR ALTER PROCEDURE CreateAdvertisingClickReports
    @reportId INT,
    @rowCount INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @fromDate DATE
    DECLARE @toDate DATE
    DECLARE @advertiserId INT
    
    SELECT 
        @fromDate = fromDate, 
        @toDate = toDate,
        @advertiserId = advertiserId
    FROM WeeklyAdvertiserReport
    WHERE reportId = @reportId

    INSERT INTO AdvertisingClickReport (reportId, clickId)
    SELECT @reportId, clickId
    FROM SubscriberAdvertisingClick sac 
        JOIN Advertising a ON a.advertisingId = sac.advertisingId
    WHERE clickedAt BETWEEN @fromDate AND @toDate
    AND a.advertiserId = @advertiserId
    
    SET @rowCount = @@ROWCOUNT
END
GO

-- DROP PROCEDURE IF EXISTS GetAdvertisingClickReportsByReportId
CREATE OR ALTER PROCEDURE GetAdvertisingClickReportsByReportId
    @reportId INT
AS
BEGIN
    SELECT *
    FROM AdvertisingClickReport acr
    JOIN SubscriberAdvertisingClick sac on sac.clickId = acr.clickId
    WHERE reportId = @reportId
END
GO
------------------- WeeklyAdvertiserReport procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateWeeklyAdvertiserReport
CREATE OR ALTER PROCEDURE CreateWeeklyAdvertiserReport
    @advertiserId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    DECLARE @weekClicks INT
    DECLARE @reportId INT

    INSERT INTO WeeklyAdvertiserReport (advertiserId, fromDate, toDate)
    VALUES (@advertiserId, @fromDate, @toDate)

    SET @reportId = SCOPE_IDENTITY()

    EXEC CreateAdvertisingClickReports @reportId, @weekClicks OUTPUT

    UPDATE WeeklyAdvertiserReport 
    SET weekClicks = @weekClicks 
    WHERE reportId = @reportId

    EXEC GetWeeklyAdvertiserReport @reportId
END
GO

-- DROP PROCEDURE IF EXISTS GetWeeklyAdvertiserReport
CREATE OR ALTER PROCEDURE GetWeeklyAdvertiserReport
    @reportId INT
AS
BEGIN
    SELECT *
    FROM WeeklyAdvertiserReport
    WHERE reportId = @reportId
END
GO

------------------- SubscriberAdvertisingClick procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateSubscriberAdvertisingClick
CREATE OR ALTER PROCEDURE CreateSubscriberAdvertisingClick
    @advertisingId INT,
    @subscriberId INT,
    @clickedAt DATETIME
AS
BEGIN
    INSERT INTO SubscriberAdvertisingClick (advertisingId, subscriberId, clickedAt)
    VALUES (@advertisingId, @subscriberId, @clickedAt)
END
GO

------------------- WeeklyPlatformFilmReport procedures -------------------
-- DROP PROCEDURE IF EXISTS CreateWeeklyPlatformFilmReport
CREATE OR ALTER PROCEDURE CreateWeeklyPlatformFilmReport
    @platformId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    DECLARE @weekViews INT
    DECLARE @reportId INT

    INSERT INTO WeeklyPlatformFilmReport (platformId, fromDate, toDate)
    VALUES (@platformId, @fromDate, @toDate)

    SET @reportId = SCOPE_IDENTITY()

    EXEC CreatePlayRegisterReports @reportId, @weekViews OUTPUT

    UPDATE WeeklyPlatformFilmReport
    SET weekViews = @weekViews
    WHERE reportId = @reportId

    EXEC GetWeeklyPlatformFilmReport @reportId
END
GO

-- DROP PROCEDURE IF EXISTS GetWeeklyPlatformFilmReport
CREATE OR ALTER PROCEDURE GetWeeklyPlatformFilmReport
    @reportId INT
AS
BEGIN
    SELECT *
    FROM WeeklyPlatformFilmReport
    WHERE reportId = @reportId
END
GO

------------------- PlayRegister procedures -------------------

-- DROP PROCEDURE IF EXISTS CreatePlayRegisterReports
CREATE OR ALTER PROCEDURE CreatePlayRegisterReports
    @reportId INT,
    @rowCount INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @fromDate DATE
    DECLARE @toDate DATE
    DECLARE @platformId INT
    
    SELECT 
        @fromDate = fromDate, 
        @toDate = toDate,
        @platformId = platformId
    FROM WeeklyPlatformFilmReport
    WHERE reportId = @reportId

    INSERT INTO PlayRegister(reportId, sessionId, playedAt)
    SELECT @reportId, sessionId, usedAt AS playedAt
    FROM Sessions
    WHERE usedAt BETWEEN @fromDate AND @toDate
    AND platformId = @platformId
    
    SET @rowCount = @@ROWCOUNT
END
GO

-- DROP PROCEDURE IF EXISTS GetPlayRegisterReportsByReportId
CREATE OR ALTER PROCEDURE GetPlayRegisterReportsByReportId
    @reportId INT
AS
BEGIN
    SELECT 
        reportId,
        pr.sessionId,
        playedAt,
        subscriberId,
        platformId,
        transactionId,
        filmCode,
        sessionUrl
    FROM PlayRegister pr
    JOIN Sessions s ON s.sessionId= pr.sessionId
    JOIN Film f ON f.filmId = s.filmid
    WHERE reportId = @reportId
END
GO