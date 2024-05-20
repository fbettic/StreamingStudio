USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateTargetCategory
DROP PROCEDURE IF EXISTS GetAllTargetCategories
DROP PROCEDURE IF EXISTS UpdateTargetCategoryTitle
DROP PROCEDURE IF EXISTS AddAdvertisingTarget
DROP PROCEDURE IF EXISTS RemoveAdvertisingTarget
DROP PROCEDURE IF EXISTS AddMarketingPreference
DROP PROCEDURE IF EXISTS RemoveMarketingPreference
*/

------------------- TargetCategory procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateTargetCategory
CREATE OR ALTER PROCEDURE CreateTargetCategory
    @targetTitle VARCHAR(255)
AS
BEGIN
    INSERT INTO TargetCategory
        (targetTitle)
    VALUES
        (@targetTitle);

    SELECT targetId, targetTitle
    FROM TargetCategory
    WHERE targetId = @@IDENTITY
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllTargetCategories
CREATE OR ALTER PROCEDURE GetAllTargetCategories
AS
BEGIN
    SELECT targetId, targetTitle
    FROM TargetCategory
    WHERE deletedAt IS NULL
END;
GO

-- DROP PROCEDURE IF EXISTS UpdateTargetCategory
CREATE OR ALTER PROCEDURE UpdateTargetCategory
    @targetId INT,
    @newTitle VARCHAR(255)
AS
BEGIN
    UPDATE TargetCategory
    SET targetTitle = @newTitle
    WHERE targetId = @targetId;

    SELECT targetId, targetTitle
    FROM TargetCategory
    WHERE targetId = @targetId
END;
GO

------------------- AdvertisingTarget procedures -------------------

-- DROP PROCEDURE IF EXISTS UpdateAdvertisingTargetsFromJson
CREATE OR ALTER PROCEDURE UpdateAdvertisingTargetsFromJson
    @json NVARCHAR(MAX)
AS
BEGIN
    BEGIN TRY
    SET NOCOUNT ON;

    DECLARE @advertisingId INT;
    DECLARE @targets NVARCHAR(MAX);


    SELECT
        @advertisingId = JSON_VALUE(@json, '$.id'),
        @targets = JSON_QUERY(@json, '$.targets');

    CREATE TABLE #TempTargets
    (
        targetId INT
    );

    INSERT INTO #TempTargets
        (targetId)
    SELECT value
    FROM OPENJSON(@targets);

    DELETE FROM AdvertisingTarget
    WHERE advertisingId = @advertisingId
        AND targetId NOT IN (SELECT targetId
        FROM #TempTargets);

    INSERT INTO AdvertisingTarget
        (advertisingId, targetId)
    SELECT @advertisingId, targetId
    FROM #TempTargets
    WHERE targetId NOT IN (
        SELECT targetId
    FROM AdvertisingTarget
    WHERE advertisingId = @advertisingId
    );

    DROP TABLE #TempTargets;

    SELECT 'success' AS message
    END TRY
  BEGIN CATCH
    SELECT ERROR_MESSAGE() AS Result;
  END CATCH
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllAdvertisingTargetByAdvertisingId
CREATE OR ALTER PROCEDURE GetAllAdvertisingTargetByAdvertisingId
    @advertisingId INT
AS
BEGIN
    SELECT tc.targetId, targetTitle
    FROM TargetCategory AS tc
        JOIN AdvertisingTarget AS at ON at.targetId=tc.targetId
    WHERE deletedAt IS NULL
        AND advertisingId = @advertisingId
END;
GO

------------------- MarketingPreference procedures -------------------

-- DROP PROCEDURE IF EXISTS UpdateMarketingPreferencesFromJson
CREATE OR ALTER PROCEDURE UpdateMarketingPreferencesFromJson
    @json NVARCHAR(MAX)
AS
BEGIN
    BEGIN TRY
    SET NOCOUNT ON;

    DECLARE @subscriberId INT;
    DECLARE @targets NVARCHAR(MAX);


    SELECT
        @subscriberId = JSON_VALUE(@json, '$.id'),
        @targets = JSON_QUERY(@json, '$.targets');

    CREATE TABLE #TempTargets
    (
        targetId INT
    );

    INSERT INTO #TempTargets
        (targetId)
    SELECT value
    FROM OPENJSON(@targets);

    DELETE FROM MarketingPreferences
    WHERE subscriberId = @subscriberId
        AND targetId NOT IN (SELECT targetId
        FROM #TempTargets);

    INSERT INTO MarketingPreferences
        (subscriberId, targetId)
    SELECT @subscriberId, targetId
    FROM #TempTargets
    WHERE targetId NOT IN (
        SELECT targetId
    FROM MarketingPreferences
    WHERE subscriberId = @subscriberId
    );

    DROP TABLE #TempTargets;


    END TRY
  BEGIN CATCH
    SELECT ERROR_MESSAGE() AS Result;
    END CATCH
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllMarketingPreferencesBySubscriberId
CREATE OR ALTER PROCEDURE GetAllMarketingPreferencesBySubscriberId
    @subscriberId INT
AS
BEGIN
    SELECT tc.targetId, targetTitle
    FROM TargetCategory AS tc
        JOIN MarketingPreferences AS mp ON mp.targetId=tc.targetId
    WHERE deletedAt IS NULL
        AND subscriberId = @subscriberId
END;
GO
