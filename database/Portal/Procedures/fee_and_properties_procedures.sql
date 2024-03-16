USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateFee
DROP PROCEDURE IF EXISTS UpdateFee
DROP PROCEDURE IF EXISTS GetAllFees
DROP PROCEDURE IF EXISTS GetFeeById
DROP PROCEDURE IF EXISTS GetFeeByType
DROP PROCEDURE IF EXISTS CreateBannerPriority
DROP PROCEDURE IF EXISTS UpdateBannerPriority
DROP PROCEDURE IF EXISTS GetBannerPriorityById
DROP PROCEDURE IF EXISTS GetAllBannerPriorities
DROP PROCEDURE IF EXISTS CreateSizeType
DROP PROCEDURE IF EXISTS UpdateSizeType
DROP PROCEDURE IF EXISTS GetSizeTypeById
DROP PROCEDURE IF EXISTS GetAllSizeTypes
DROP TYPE IF EXISTS AllFees
*/



------------------- FEE -------------------
DROP TYPE IF EXISTS AllFees
CREATE TYPE dbo.AllFees AS TABLE (
    feeId INT,
    feeType VARCHAR(255),
    feeValue FLOAT
    );
GO

-- DROP PROCEDURE IF EXISTS CreateFee
CREATE OR ALTER PROCEDURE CreateFee
    @feeType VARCHAR(255),
    @feeValue FLOAT
AS
BEGIN

    IF EXISTS (
        SELECT *
    FROM FeeType
    WHERE feeType = @feeType AND deletedAt IS NULL)
    BEGIN;
        THROW 50001, 'Priority type already registered', 1;
    END

    DECLARE @feeId INT;

    INSERT INTO FeeType
        (feeType)
    VALUES
        (@feeType);

    SET @feeId = SCOPE_IDENTITY();

    INSERT INTO FeeHistory
        (feeId, feeValue, fromDate)
    VALUES
        (@feeId, @feeValue, GETDATE());

    EXEC GetFeeById @@IDENTITY
END;
GO

-- DROP PROCEDURE IF EXISTS UpdateFee
CREATE OR ALTER PROCEDURE UpdateFee
    @feeId INT,
    @feeType VARCHAR(255),
    @feeValue FLOAT
AS
BEGIN
    IF EXISTS (
        SELECT *
    FROM FeeType
    WHERE feeType = @feeType
        AND deletedAt IS NULL
        AND feeId != @feeId )
    BEGIN;
        THROW 50001, 'Fee type already registered', 1;
    END

    DECLARE @currentFeeType VARCHAR(255);
    SELECT @currentFeeType = feeType
    FROM FeeType
    WHERE feeId = @feeId;

    IF @currentFeeType != @feeType
    BEGIN
        UPDATE FeeType
        SET feeType = @feeType
        WHERE feeId = @feeId;
    END;

    UPDATE FeeHistory
    SET toDate = GETDATE()
    WHERE feeId = @feeId AND toDate IS NULL;

    INSERT INTO FeeHistory
        (feeId, feeValue, fromDate)
    VALUES
        (@feeId, @feeValue, GETDATE());

    EXEC GetFeeById @feeId
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllFees
CREATE OR ALTER PROCEDURE GetAllFees
AS
BEGIN
    SELECT
        ft.feeId,
        ft.feeType,
        fh.feeValue
    FROM
        FeeType ft
        INNER JOIN FeeHistory fh ON ft.feeId = fh.feeId
    WHERE
    fh.toDate IS NULL
    ORDER BY ft.feeId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetFeeById
CREATE OR ALTER PROCEDURE GetFeeById
    @feeId INT,
    @feeValue INT = NULL OUTPUT
AS
BEGIN
    SELECT
        ft.feeId,
        ft.feeType,
        fh.feeValue
    FROM
        FeeType ft
        INNER JOIN FeeHistory fh ON ft.feeId = fh.feeId
    WHERE ft.feeId = @feeId
        AND fh.toDate IS NULL
        AND ft.deletedAt IS NULL
END;
GO

-- DROP PROCEDURE IF EXISTS GetFeeByType
CREATE OR ALTER PROCEDURE GetFeeByType
    @feeType VARCHAR(255)
AS
BEGIN
    SELECT
        ft.feeId,
        ft.feeType,
        fh.feeValue
    FROM
        FeeType ft
        INNER JOIN FeeHistory fh ON ft.feeId = fh.feeId
    WHERE ft.feeType = @feeType
        AND fh.toDate IS NULL
        AND ft.deletedAt IS NULL
END;
GO


------------------- BANNER PRIORITY -------------------

-- DROP PROCEDURE IF EXISTS CreateBannerPriority
CREATE OR ALTER PROCEDURE CreateBannerPriority
    @priorityType VARCHAR(255),
    @priorityValue INT,
    @priorityFeeId INT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT *
    FROM BannerPriority
    WHERE priorityType = @priorityType
        AND deletedAt IS NULL
    )
    BEGIN;
        THROW 50001, 'Priority type already registered', 1;
    END

    INSERT INTO BannerPriority
        (priorityType, priorityValue, priorityFeeId)
    VALUES
        (@priorityType, @priorityValue, @priorityFeeId);

    EXEC GetBannerPriorityById @@IDENTITY
END;
GO

-- DROP PROCEDURE IF EXISTS UpdateBannerPriority
CREATE OR ALTER PROCEDURE UpdateBannerPriority
    @priorityId INT,
    @priorityType VARCHAR(255),
    @priorityValue INT,
    @priorityFeeId INT
AS
BEGIN
    SET NOCOUNT ON;
    IF EXISTS (
        SELECT *
    FROM BannerPriority
    WHERE priorityType = @priorityType
        AND deletedAt IS NULL
        AND priorityId != @priorityId
    )
    BEGIN;
        THROW 50001, 'Priority type already registered', 1;
    END


    UPDATE BannerPriority
    SET priorityType = @priorityType,
        priorityValue = @priorityValue,
        priorityFeeId = @priorityFeeId
    WHERE priorityId = @priorityId;

    EXEC GetBannerPriorityById @priorityId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetBannerPriorityById
CREATE OR ALTER PROCEDURE GetBannerPriorityById
    @priorityId INT
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees


    SELECT
        bp.priorityId,
        bp.priorityType,
        bp.priorityValue,
        feeValue AS priorityFee
    FROM
        BannerPriority bp
        INNER JOIN @AllFees af ON af.feeId = bp.priorityFeeId
            AND bp.priorityId = @priorityId
            AND bp.deletedAt IS NULL
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllBannerPriorities
CREATE OR ALTER PROCEDURE GetAllBannerPriorities
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        bp.priorityId,
        bp.priorityType,
        bp.priorityValue,
        feeValue AS priorityFee
    FROM
        BannerPriority bp
        INNER JOIN @AllFees af ON af.feeId = bp.priorityFeeId
    WHERE
        bp.deletedAt IS NULL
    ORDER BY priorityId
END;
GO


------------------- SIZE TYPE -------------------

-- DROP PROCEDURE IF EXISTS CreateSizeType
CREATE OR ALTER PROCEDURE CreateSizeType
    @sizeFeeId INT,
    @sizeType VARCHAR(255),
    @sizeValue INT,
    @height INT,
    @width INT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT *
    FROM SizeType
    WHERE sizeType = @sizeType
        AND deletedAt IS NULL
    )
    BEGIN;
        THROW 50001, 'Size type already registered', 1;
    END

    INSERT INTO SizeType
        (sizeFeeId, sizeType, sizeValue, height, width)
    VALUES
        (@sizeFeeId, @sizeType, @sizeValue, @height, @width);

    EXEC GetSizeTypeById @@IDENTITY
END;
GO

-- DROP PROCEDURE IF EXISTS UpdateSizeType
CREATE OR ALTER PROCEDURE UpdateSizeType
    @sizeId INT,
    @sizeFeeId INT,
    @sizeType VARCHAR(255),
    @sizeValue INT,
    @height INT,
    @width INT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT *
    FROM SizeType
    WHERE sizeType = @sizeType
        AND deletedAt IS NULL
        AND sizeId != @sizeId
    )
    BEGIN;
        THROW 50001, 'Size type already registered', 1;
    END

    UPDATE SizeType
  SET sizeFeeId = @sizeFeeId,
      sizeType = @sizeType,
      sizeValue = @sizeValue,
      height = @height,
      width = @width
  WHERE sizeId = @sizeId;

    EXEC GetSizeTypeById @sizeId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetSizeTypeById
CREATE OR ALTER PROCEDURE GetSizeTypeById
    @sizeId INT
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        st.sizeType,
        st.sizeValue,
        st.height,
        st.width,
        feeValue AS sizeFee
    FROM
        SizeType st
        INNER JOIN @AllFees af ON af.feeId = st.sizeFeeId
    WHERE
        st.deletedAt IS NULL
        AND st.sizeId = @sizeId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetAllSizeTypes
CREATE OR ALTER PROCEDURE GetAllSizeTypes
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        st.sizeType,
        st.sizeValue,
        st.height,
        st.width,
        feeValue AS sizeFee
    FROM
        SizeType st
        INNER JOIN @AllFees af ON af.feeId = st.sizeFeeId
    WHERE
        st.deletedAt IS NULL
    ORDER BY sizeId;
END;
GO





/*
EXEC CreateFee 'BANNER_PRIORITY_LOW', 2.5
EXEC CreateFee 'BANNER_PRIORITY_MEDIUM', 6.5
EXEC CreateFee 'BANNER_PRIORITY_HIGHT', 9.8
EXEC CreateFee 'BANNER_SIZE_SMALL', 4.5
EXEC CreateFee 'BANNER_SIZE_MEDIUM', 8.5
EXEC CreateFee 'BANNER_SIZE_LARGE', 10.6
EXEC CreateFee 'ALLPAGES', 15.5
EXEC CreateFee 'LOGIN_FEE', 12.3
EXEC CreateFee 'SIGNUP_FEE', 13.5

EXEC GetAllFees

EXEC GetFeeById 1

EXEC UpdateFee 1, 'BANNER_PRIORITY_LOW', 3.7

EXEC GetAllFees

EXEC GetFeeById 1;


EXEC CreateBannerPriority "LOW", 5, 1
EXEC CreateBannerPriority "MEDIUM", 10, 2
EXEC CreateBannerPriority "HIGHT", 15, 3

EXEC GetAllBannerPriorities

EXEC GetBannerPriorityById 1

EXEC UpdateBannerPriority 1, "MEDIUM", 5, 1

EXEC CreateSizeType 4, 'SMALL', 5, 150, 100
EXEC CreateSizeType 5, 'MEDIUM', 5, 300, 100
EXEC CreateSizeType 6, 'LARGE', 5, 150, 600

EXEC GetAllSizeTypes

EXEC GetSizeTypeById 1


EXEC GetAllFees
EXEC GetAllBannerPriorities
EXEC GetAllSizeTypes
*/