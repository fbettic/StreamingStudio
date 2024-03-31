USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateAdvertiser
DROP PROCEDURE IF EXISTS UpdateAdvertiser
DROP PROCEDURE IF EXISTS GetAdvertisery
DROP PROCEDURE IF EXISTS GetAllAdvertisers
*/

-- DROP PROCEDURE IF EXISTS CreateAdvertiser


-- DROP PROCEDURE IF EXISTS UpdateAdvertiser
CREATE OR ALTER PROCEDURE UpdateAdvertiser
    @advertiserId INT,
    @agentFirstname VARCHAR(255),
    @agentLastname VARCHAR(255),
    @companyName VARCHAR(255),
    @bussinesName VARCHAR(255),
    @email VARCHAR(255),
    @phone VARCHAR(255),
    @password VARCHAR(255),
    @apiUrl VARCHAR(255),
    @authToken VARCHAR(MAX),
    @serviceType VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @emailExists BIT;

    EXEC CheckEmailExistsUpdate @email, @advertiserId, 'ADVERTISER', @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END

    UPDATE Advertiser
    SET agentFirstname = @agentFirstname,
        agentLastname = @agentLastname,
        companyName = @companyName,
        bussinesName = @bussinesName,
        email = @email,
        phone = @phone,
        password = @password,
        apiUrl = @apiUrl,
        authToken = @authToken,
        serviceType = @serviceType
    WHERE advertiserId = @advertiserId;

    EXEC GetAdvertiserById @advertiserId
END;
GO

-- DROP PROCEDURE IF EXISTS GetAdvertisery
CREATE OR ALTER PROCEDURE GetAdvertiserById
    @advertiserId INT
AS
BEGIN
    SELECT advertiserId AS id,
        agentFirstname,
        agentLastname,
        companyName,
        bussinesName,
        email,
        phone,
        apiUrl,
        authToken,
        serviceType
    FROM Advertiser
    WHERE advertiserId = @advertiserId
    AND deletedAt IS NULL
END
GO

-- DROP PROCEDURE IF EXISTS GetAllAdvertisers
CREATE OR ALTER PROCEDURE GetAllAdvertisers
AS
BEGIN
    SELECT
        advertiserId AS id,
        agentFirstname,
        agentLastname,
        companyName,
        bussinesName,
        email,
        phone,
        apiUrl,
        authToken,
        serviceType
    FROM Advertiser
    WHERE deletedAt IS NULL
END
GO

EXEC GetAllAdvertisers