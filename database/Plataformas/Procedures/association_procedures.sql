-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
DROP PROCEDURE IF EXISTS CreateAssociationRequest
DROP PROCEDURE IF EXISTS SetUserToken
DROP PROCEDURE IF EXISTS CancelAssociationRequest
DROP PROCEDURE IF EXISTS FinalizeAssociationRequest
DROP PROCEDURE IF EXISTS GetLastOpenAssociationRequest
DROP PROCEDURE IF EXISTS GetAssociationRequestById
DROP PROCEDURE IF EXISTS GetUserToken
DROP PROCEDURE IF EXISTS CreateSession
DROP PROCEDURE IF EXISTS MarkSessionAsUsed
DROP PROCEDURE IF EXISTS MarkSessionAsExpired
DROP PROCEDURE IF EXISTS GetSessionById
DROP PROCEDURE IF EXISTS GetSession
*/


------------------- AssociationRequest procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateAssociationRequest
CREATE OR ALTER PROCEDURE CreateAssociationRequest
    @serviceId INT,
    @associationType VARCHAR(255),
    @uuid VARCHAR(255),
    @redirectUrl VARCHAR(255),
    @requestedAt DATETIME
AS
BEGIN
    DECLARE @associationId INT

    INSERT INTO AssociationRequest
        (serviceId, associationType, state, uuid, redirectUrl, requestedAt)
    VALUES
        (@serviceId, @associationType, 'OPEN', @uuid, @redirectUrl, @requestedAt);

    SET @associationId = SCOPE_IDENTITY()
    EXEC GetAssociationRequestById @associationId
END;
GO

-- DROP PROCEDURE IF EXISTS SetUserToken
CREATE OR ALTER PROCEDURE SetUserToken
    @userId INT,
    @uuid VARCHAR(255)
AS
BEGIN
    DECLARE @userToken NVARCHAR(255);

    EXEC GetToken @userToken OUTPUT

    UPDATE AssociationRequest
    SET userId = @userId,
    userToken = @userToken,
    state = 'FINALIZED'
    WHERE uuid = @uuid
        AND state = 'OPEN'

     SELECT *
    FROM AssociationRequest
    WHERE uuid = @uuid
END
GO

-- DROP PROCEDURE IF EXISTS CancelAssociationRequest
CREATE OR ALTER PROCEDURE CancelAssociationRequest
    @associationId INT
AS
BEGIN
    UPDATE AssociationRequest
    SET state = 'CANCELED', canceledAt = GETDATE()
    WHERE associationId = @associationId
        AND (state = 'OPEN' OR state = 'FINALIZED')
END;
GO


-- DROP PROCEDURE IF EXISTS FinalizeAssociationRequest
CREATE OR ALTER PROCEDURE FinalizeAssociationRequest
    @associationId INT
AS
BEGIN
    UPDATE AssociationRequest
    SET state = 'FINALIZED'
    WHERE associationId = @associationId
        AND state = 'OPEN'
END;
GO

-- DROP PROCEDURE IF EXISTS GetLastOpenAssociationRequest
CREATE OR ALTER PROCEDURE GetLastOpenAssociationRequest
    @userId INT,
    @serviceId INT
AS
BEGIN
    SELECT *
    FROM AssociationRequest
    WHERE userId = @userId
        AND serviceId = @serviceId
        AND state = 'OPEN'
END
GO

-- DROP PROCEDURE IF EXISTS GetAssociationRequestById
CREATE OR ALTER PROCEDURE GetAssociationRequestById
    @associationId INT
AS
BEGIN
    SELECT *
    FROM AssociationRequest
    WHERE associationId = @associationId
END
GO

-- DROP PROCEDURE IF EXISTS GetUserToken
CREATE OR ALTER PROCEDURE GetUserToken
    @userId INT,
    @serviceId INT
AS
BEGIN
    SELECT userId, serviceId, userToken
    FROM AssociationRequest
    WHERE userId = @userId
        AND serviceId = @serviceId
        AND state = 'OPEN'
END
GO




------------------- Session procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateSession
CREATE OR ALTER PROCEDURE CreateSession
    @serviceId INT,
    @userId INT,
    @filmCode CHAR(9),
    @createdAt DATETIME
AS
BEGIN
    DECLARE @canceledAt DATE;
    DECLARE @associationId INT;
    DECLARE @filmId INT;
    DECLARE @sessionUrl VARCHAR(255) ;
    DECLARE @sessionId INT;


    SELECT @canceledAt = canceledAt,
        @associationId = associationId
    FROM AssociationRequest
    WHERE serviceId = @serviceId
        AND userId = @userId
        AND state = 'FINALIZED'

    SELECT @filmId = filmId,
    @sessionUrl = url
    FROM Film
    WHERE filmCode = @filmCode

    IF @canceledAt IS NOT NULL OR @associationId IS NULL
    BEGIN;
        THROW 50005, 'The user does not exist', 1;
    END

    IF @filmId IS NULL OR @sessionUrl IS NULL
    BEGIN;
        THROW 50006, 'The film does not exist', 1;
    END


    INSERT INTO Sessions
        (usedAt, associationId, filmId, sessionUrl, createdAt, expired)
    VALUES
        (NULL, @associationId, @filmId, @sessionUrl, @createdAt, 0);

    SET @sessionId = SCOPE_IDENTITY()
    EXEC GetSessionById  @sessionId
    
END;
GO

select * from PlatformUser

-- DROP PROCEDURE IF EXISTS MarkSessionAsUsed
CREATE OR ALTER PROCEDURE MarkSessionAsUsed
    @sessionId INT
AS
BEGIN
    UPDATE Sessions
    SET usedAt = GETDATE()
    WHERE sessionId = @sessionId;
END;
GO

-- DROP PROCEDURE IF EXISTS MarkSessionAsExpired
CREATE OR ALTER PROCEDURE MarkSessionAsExpired
    @sessionId INT
AS
BEGIN
    UPDATE Sessions
    SET expired = 1
    WHERE sessionId = @sessionId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetSessionById
CREATE OR ALTER PROCEDURE GetSessionById
    @sessionId INT
AS
BEGIN
    SELECT sessionId, 
        associationId, 
        filmCode, 
        sessionUrl, 
        createdAt, 
        usedAt, 
        expired
    FROM Sessions s
    JOIN Film f ON f.filmId = s.filmId
    WHERE sessionId = @sessionId
END;
GO

-- DROP PROCEDURE IF EXISTS GetSession
CREATE OR ALTER PROCEDURE GetSession
    @filmCode INT,
    @userId INT,
    @serviceId INT
AS
BEGIN
    SELECT *
    FROM Sessions s
        JOIN AssociationRequest ar ON ar.associationId = s.associationId
        JOIN Film f ON f.filmId = s.filmId
    WHERE filmCode = @filmCode
        AND userId = @userId
        AND serviceId = @serviceId
        AND usedAt IS NULL
        AND expired = 0
END;


EXEC GetAssociationRequestById 1