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
    INSERT INTO TargetCategory (targetTitle)
    VALUES (@targetTitle);
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllTargetCategories
CREATE OR ALTER PROCEDURE GetAllTargetCategories
AS
BEGIN
    SELECT * FROM TargetCategory
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
END;
GO

------------------- AdvertisingTarget procedures -------------------

-- DROP PROCEDURE IF EXISTS AddAdvertisingTarget
CREATE OR ALTER PROCEDURE AddAdvertisingTarget
@advertisingId INT,
@targetId INT
AS
BEGIN
    INSERT INTO AdvertisingTarget (advertisingId, targetId)
    VALUES (@advertisingId, @targetId);
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
END;
GO

------------------- MarketingPreference procedures -------------------

-- DROP PROCEDURE IF EXISTS AddMarketingPreference
CREATE OR ALTER PROCEDURE AddMarketingPreference
@subscriberId INT,
@targetId INT
AS
BEGIN
    INSERT INTO MarketingPreferences (subscriberId, targetId)
    VALUES (@subscriberId, @targetId);
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
END;
GO

