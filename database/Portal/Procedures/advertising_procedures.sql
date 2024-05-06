
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
        bannerId,
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
        bannerId,
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

EXEC GetAllAdvertisings
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
        bannerId,
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
    @bannerId INT,
    @redirectUrl VARCHAR(255),
    @imageUrl VARCHAR(255),
    @bannerText VARCHAR(255)
AS
BEGIN
    UPDATE Advertising 
    SET redirectUrl = @redirectUrl,
    imageUrl = @imageUrl,
    bannerText = @bannerText
    WHERE bannerId = @bannerId 
    AND advertiserId = @advertiserId
END
GO

-- DROP PROCEDURE IF EXISTS
CREATE OR ALTER PROCEDURE GetAdvertisingsToShow 
    @jsonData NVARCHAR(MAX)
AS
BEGIN
    BEGIN TRY
    DECLARE @tempTable TABLE (
        filmId INT,
        platformId INT,
        newContent BIT,
        highlighted BIT
    );

    -- Insertar los datos JSON en una tabla temporal
    INSERT INTO @tempTable
        (filmId, platformId, newContent, highlighted)
    SELECT filmId, platformId, newContent, highlighted
    FROM Film f
        JOIN OPENJSON(@jsonData)
    WITH (
      filmCode VARCHAR(255) '$.filmCode',
      platformId INT '$.platformId',
      newContent BIT '$.newContent',
      highlighted BIT '$.highlighted'
    ) AS js on f.filmCode = js.filmCode;
    


    -- Éxito
    SELECT 'Success' AS Result;
  END TRY
  BEGIN CATCH
    -- Error
    SELECT ERROR_MESSAGE() AS Result;
  END CATCH
END
GO