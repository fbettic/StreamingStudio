
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
    @bannerId INT,
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
        bannerId,
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
            @bannerId,
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
    @bannerId INT,
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
        bannerId = @bannerId,
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
        advertisingId 
        advertiserId,
        sizeType
        sizeValue,
        af_st.feeValue AS sizeFee,
        af.feeValue AS allPagesFee,
        priorityType,
        priorityValue,
        af_bp.feeValue AS priorityFee,
        redirectUrl,
        imageUrl,
        bannerText,
        bannerId,
        fromDate,
        toDate
    FROM Advertising a
    INNER JOIN BannerPriority bp ON bp.priorityId = a.priorityId
    INNER JOIN SizeType st ON st.sizeId = a.sizeId
    INNER JOIN @AllFees af_st ON af_st.feeId = st.sizeFeeId
    INNER JOIN @AllFees af_bp ON af_bp.feeId = bp.priorityFeeId
    INNER JOIN @AllFees af ON af.feeId = a.allPagesFeeId
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
        advertisingId 
        advertiserId,
        sizeType
        sizeValue,
        af_st.feeValue AS sizeFee,
        af.feeValue AS allPagesFee,
        priorityType,
        priorityValue,
        af_bp.feeValue AS priorityFee,
        redirectUrl,
        imageUrl,
        bannerText,
        bannerId,
        fromDate,
        toDate
    FROM Advertising a
    INNER JOIN BannerPriority bp ON bp.priorityId = a.priorityId
    INNER JOIN SizeType st ON st.sizeId = a.sizeId
    INNER JOIN @AllFees af_st ON af_st.feeId = st.sizeFeeId
    INNER JOIN @AllFees af_bp ON af_bp.feeId = bp.priorityFeeId
    INNER JOIN @AllFees af ON af.feeId = a.allPagesFeeId
    WHERE a.advertiserId = @advertiserId
    AND a.deletedAt IS NULL
END
GO

/*
EXEC CreateAdvertising 1, 1, 7, 1, 'asdasdasd', 'asdasdasd', 'asdasdasd', 1, '2024-01-11', '2025-01-11'

EXEC UpdateAdvertising 1, 1, 2, 7, 3, 'aaaaaaaaaa', 'asdasdasd', 'asdasdasd', 1, '2024-01-11', '2025-01-11'

EXEC CreateAdvertising 1, 3, 7, 3, 'asdasdasd', 'asdasdasd', 'asdasdasd', 1, '2024-01-11', '2025-01-11'

EXEC GetAllAdvertisingsByAdvertiser 1

EXEC GetAdvertisingById 1
*/