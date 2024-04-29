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

-- DROP PROCEDURE IF EXISTS UpdateTargetCategoryTitle
CREATE OR ALTER PROCEDURE UpdateTargetCategoryTitle
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

-- DROP PROCEDURE IF EXISTS AddAdvertisingTarget
CREATE OR ALTER PROCEDURE AddAdvertisingTarget
    @advertisingId INT,
    @targetId INT
AS
BEGIN
    INSERT INTO AdvertisingTarget
        (advertisingId, targetId)
    VALUES
        (@advertisingId, @targetId);

    SELECT *
    FROM AdvertisingTarget
    WHERE  advertisingId=@advertisingId
        AND targetId=@targetId
END;
GO

-- DROP PROCEDURE IF EXISTS RemoveAdvertisingTarget
CREATE OR ALTER PROCEDURE RemoveAdvertisingTarget
    @advertisingId INT,
    @targetId INT
AS
BEGIN
    DELETE FROM AdvertisingTarget
    WHERE advertisingId = @advertisingId AND targetId = @targetId;

    SELECT @advertisingId, @targetId
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

-- DROP PROCEDURE IF EXISTS AddMarketingPreference
CREATE OR ALTER PROCEDURE AddMarketingPreference
    @subscriberId INT,
    @targetId INT
AS
BEGIN
    INSERT INTO MarketingPreferences
        (subscriberId, targetId)
    VALUES
        (@subscriberId, @targetId);

    SELECT *
    FROM MarketingPreferences
    WHERE subscriberId = @subscriberId
        AND targetId = @targetId
END;
GO

-- DROP PROCEDURE IF EXISTS RemoveMarketingPreference
CREATE OR ALTER PROCEDURE RemoveMarketingPreference
    @subscriberId INT,
    @targetId INT
AS
BEGIN
    DELETE FROM MarketingPreferences
    WHERE subscriberId = @subscriberId AND targetId = @targetId;

    SELECT @subscriberId, @targetId

END;
GO

-- DROP PROCEDURE IF EXISTS GetAllAdvertisingTargetBySubscriberId
CREATE OR ALTER PROCEDURE GetAllAdvertisingTargetBySubscriberId
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