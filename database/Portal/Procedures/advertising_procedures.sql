
USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateAdvertising
DROP PROCEDURE IF EXISTS UpdateAdvertising
DROP PROCEDURE IF EXISTS GetAdvertisingByAdvertiser
DROP PROCEDURE IF EXISTS GetAdvertisingById
*/

-- DROP PROCEDURE IF EXISTS CreateAdvertising
CREATE OR ALTER PROCEDURE CreateAdvertising
    @advertiserId INT,
    @sizeId INT,
    @allPagesFeeId INT,
    @priorityId INT,
    @redirectUrl VARCHAR(255),
    @imageUrl VARCHAR(255),
    @bannerText VARCHAR(255),
    @referenceId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Advertising
        (
        advertiserId,
        sizeId,
        allPagesFeeId,
        priorityId,
        redirectUrl,
        imageUrl,
        bannerText,
        referenceId,
        fromDate,
        toDate
        )
    VALUES
        (
            @advertiserId,
            @sizeId,
            @allPagesFeeId,
            @priorityId,
            @redirectUrl,
            @imageUrl,
            @bannerText,
            @referenceId,
            @fromDate,
            @toDate
        );

    EXEC GetAdvertisingById @@IDENTITY
END;
GO

-- DROP PROCEDURE IF EXISTS UpdateAdvertising
CREATE OR ALTER PROCEDURE UpdateAdvertising
    @advertisingId INT,
    @advertiserId INT,
    @sizeId INT,
    @allPagesFeeId INT,
    @priorityId INT,
    @redirectUrl VARCHAR(255),
    @imageUrl VARCHAR(255),
    @bannerText VARCHAR(255),
    @referenceId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE Advertising
    SET advertiserId = @advertiserId,
        sizeId = @sizeId,
        allPagesFeeId = @allPagesFeeId,
        priorityId = @priorityId,
        redirectUrl = @redirectUrl,
        imageUrl = @imageUrl,
        bannerText = @bannerText,
        referenceId = @referenceId,
        fromDate = @fromDate,
        toDate = @toDate
    WHERE advertisingId = @advertisingId;

    EXEC GetAdvertisingById @advertisingId
END;
GO

-- DROP PROCEDURE IF EXISTS GetAdvertisingByAdvertiser
CREATE OR ALTER PROCEDURE GetAdvertisingById
    @advertisingId INT
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        advertisingId,
        advertiserId,
        a.sizeId AS sizeId,
        sizeType,
        sizeValue,
        height,
        width,
        af_st.feeValue AS sizeFee,
        a.allPagesFeeId AS allPagesFeeId,
        CASE
            WHEN a.allPagesFeeId IS NOT NULL THEN af.feeValue
            ELSE NULL
        END AS allPagesFee,
        a.priorityId AS priorityId,
        priorityType,
        priorityValue,
        af_bp.feeValue AS priorityFee,
        redirectUrl,
        imageUrl,
        bannerText,
        referenceId,
        fromDate,
        toDate
    FROM Advertising a
        INNER JOIN BannerPriority bp ON bp.priorityId = a.priorityId
        INNER JOIN SizeType st ON st.sizeId = a.sizeId
        INNER JOIN @AllFees af_st ON af_st.feeId = st.sizeFeeId
        INNER JOIN @AllFees af_bp ON af_bp.feeId = bp.priorityFeeId
        LEFT JOIN @AllFees af ON af.feeId = a.allPagesFeeId
    WHERE a.advertisingId = @advertisingId
        AND a.deletedAt IS NULL
END
GO



-- DROP PROCEDURE IF EXISTS GetAdvertisingById
CREATE OR ALTER PROCEDURE GetAllAdvertisingsByAdvertiser
    @advertiserId INT
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        advertisingId,
        advertiserId,
        a.sizeId AS sizeId,
        sizeType,
        sizeValue,
        height,
        width,
        af_st.feeValue AS sizeFee,
        a.allPagesFeeId AS allPagesFeeId,
        CASE
            WHEN a.allPagesFeeId IS NOT NULL THEN af.feeValue
            ELSE NULL
        END AS allPagesFee,
        a.priorityId AS priorityId,
        priorityType,
        priorityValue,
        af_bp.feeValue AS priorityFee,
        redirectUrl,
        imageUrl,
        bannerText,
        referenceId,
        fromDate,
        toDate
    FROM Advertising a
        INNER JOIN BannerPriority bp ON bp.priorityId = a.priorityId
        INNER JOIN SizeType st ON st.sizeId = a.sizeId
        INNER JOIN @AllFees af_st ON af_st.feeId = st.sizeFeeId
        INNER JOIN @AllFees af_bp ON af_bp.feeId = bp.priorityFeeId
        LEFT JOIN @AllFees af ON af.feeId = a.allPagesFeeId
    WHERE a.advertiserId = @advertiserId
        AND a.deletedAt IS NULL
END
GO

-- DROP PROCEDURE IF EXISTS GetAdvertisingById
CREATE OR ALTER PROCEDURE GetAllAdvertisings
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        advertisingId,
        advertiserId,
        a.sizeId AS sizeId,
        sizeType,
        sizeValue,
        height,
        width,
        af_st.feeValue AS sizeFee,
        a.allPagesFeeId AS allPagesFeeId,
        CASE
            WHEN a.allPagesFeeId IS NOT NULL THEN af.feeValue
            ELSE NULL
        END AS allPagesFee,
        a.priorityId AS priorityId,
        priorityType,
        priorityValue,
        af_bp.feeValue AS priorityFee,
        redirectUrl,
        imageUrl,
        bannerText,
        referenceId,
        fromDate,
        toDate
    FROM Advertising a
        INNER JOIN BannerPriority bp ON bp.priorityId = a.priorityId
        INNER JOIN SizeType st ON st.sizeId = a.sizeId
        INNER JOIN @AllFees af_st ON af_st.feeId = st.sizeFeeId
        INNER JOIN @AllFees af_bp ON af_bp.feeId = bp.priorityFeeId
        LEFT JOIN @AllFees af ON af.feeId = a.allPagesFeeId
    WHERE a.deletedAt IS NULL
END
GO

-- DROP PROCEDURE IF EXISTS UpdateAdvertisingBanner
CREATE OR ALTER PROCEDURE UpdateAdvertisingBanner
    @advertiserId INT,
    @referenceId INT,
    @redirectUrl VARCHAR(255),
    @imageUrl VARCHAR(255),
    @bannerText VARCHAR(255)
AS
BEGIN
    UPDATE Advertising 
    SET redirectUrl = @redirectUrl,
    imageUrl = @imageUrl,
    bannerText = @bannerText
    WHERE referenceId = @referenceId
        AND advertiserId = @advertiserId
END
GO

-- DROP PROCEDURE IF EXISTS GetAdvertisingsForSubscriber
CREATE OR ALTER PROCEDURE GetAdvertisingsForSubscriber
    @subscriberId INT
AS
BEGIN
    SELECT
        a.advertisingId,
        redirectUrl,
        imageUrl,
        bannerText,
        sizeType,
        height,
        width,
        (SELECT
            CASE 
        WHEN a.allPagesFeeId IS NULL 
            THEN 0 
            ELSE 1 
        END) AS allPages,
        COUNT(mt.targetId) + priorityValue AS points
    FROM
        Advertising a
        LEFT JOIN
        AdvertisingTarget at ON a.advertisingId = at.advertisingId
        LEFT JOIN
        MarketingPreferences mp ON at.targetId = mp.targetId
            AND mp.subscriberId = @subscriberId
        LEFT JOIN
        MarketingPreferences mt ON a.advertisingId = mt.targetId
            AND mt.subscriberId = @subscriberId
        INNER JOIN BannerPriority b_p ON a.priorityId = b_p.priorityId
        INNER JOIN SizeType s_t ON a.sizeId = s_t.sizeId
    WHERE 
    a.deletedAt IS NULL
    GROUP BY 
    a.advertisingId, allPagesFeeId, priorityValue,
    redirectUrl,imageUrl,bannerText, height, width, sizeType
END
GO

