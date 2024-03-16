USE portal_db
GO

/*
DROP PROCEDURE IF EXIST CreateStreamingPlatform
DROP PROCEDURE IF EXIST UpdateStreamingPlatform
DROP PROCEDURE IF EXIST GetStreamingPlatformById
DROP PROCEDURE IF EXIST GetAllStreamingPlatforms
*/

-- DROP PROCEDURE IF EXIST CreateStreamingPlatform
CREATE OR ALTER PROCEDURE CreateStreamingPlatform
    @platformName VARCHAR(255),
    @email VARCHAR(255),
    @apiUrl VARCHAR(255),
    @authToken TEXT,
    @signupFeeId INT,
    @loginFeeId INT,
    @serviceType VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @emailExists BIT;

    EXEC  CheckEmailExists @email, @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END

    INSERT INTO StreamingPlatform
        (platformName, email, apiUrl, authToken, signupFeeId, loginFeeId, serviceType)
    VALUES
        (@platformName, @email, @apiUrl, @authToken, @signupFeeId, @loginFeeId, @serviceType);

    EXEC GetStreamingPlatformById @@IDENTITY
END;
GO

-- DROP PROCEDURE IF EXIST UpdateStreamingPlatform
CREATE OR ALTER PROCEDURE UpdateStreamingPlatform
    @platformId INT,
    @platformName VARCHAR(255),
    @email VARCHAR(255),
    @apiUrl VARCHAR(255),
    @authToken TEXT,
    @signupFeeId INT,
    @loginFeeId INT,
    @serviceType VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @emailExists BIT;

    EXEC CheckEmailExistsUpdate @email, @platformId, 'STREAMINGPLATFORM', @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END

    UPDATE StreamingPlatform
    SET platformName = @platformName,
        email = @email,
        apiUrl = @apiUrl,
        authToken = @authToken,
        signupFeeId = @signupFeeId,
        loginFeeId = @loginFeeId,
        serviceType = @serviceType
    WHERE platformId = @platformId;

    EXEC GetStreamingPlatformById @platformId
END;
GO

-- DROP PROCEDURE IF EXIST GetStreamingPlatformById
CREATE OR ALTER PROCEDURE GetStreamingPlatformById
    @platformId INT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        platformId, platformName, email, apiUrl, authToken, af_s.feeValue AS signupFee, af_l.feeValue AS loginFee, serviceType
    FROM StreamingPlatform sp
        INNER JOIN @AllFees af_l ON af_l.feeId = sp.loginFeeId
        INNER JOIN @AllFees af_s ON af_s.feeId = sp.signupFeeId
    WHERE platformId = @platformId
        AND deletedAt IS NULL
END
GO

-- DROP PROCEDURE IF EXIST GetAllStreamingPlatforms
CREATE OR ALTER PROCEDURE GetAllStreamingPlatforms
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        platformId, platformName, email, apiUrl, authToken, af_s.feeValue AS signupFee, af_l.feeValue AS loginFee, serviceType
    FROM StreamingPlatform sp
        INNER JOIN @AllFees af_l ON af_l.feeId = sp.loginFeeId
        INNER JOIN @AllFees af_s ON af_s.feeId = sp.signupFeeId
    WHERE deletedAt IS NULL
END
GO

/*
    EXEC CreateStreamingPlatform 'Netflix', 'netflix@gmail.com', 'netflix.com', 'SECRET_TOKEN_NETFLIX', 2, 1, 'REST'
EXEC CreateStreamingPlatform 'Prime', 'prime@gmail.com', 'prime.com', 'SECRET_TOKEN_PRIME', 2, 1, 'REST'
EXEC CreateStreamingPlatform 'MAX', 'max@gmail.com', 'max.com', 'SECRET_TOKEN_MAX', 2, 1, 'SOAP'
EXEC GetAllStreamingPlatforms

EXEC SoftDeleteRecord 'StreamingPlatform', 'platformId', 2

*/

