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
    @userId INT,
    @serviceId INT,
    @associationType VARCHAR(255),
    @authUrl VARCHAR(255),
    @redirectUrl VARCHAR(255),
    @requestedAt DATETIME
AS
BEGIN
    INSERT INTO AssociationRequest
        (userId, serviceId, associationType, state, authUrl, redirectUrl, requestedAt)
    VALUES
        ( @userId, @serviceId, @associationType, 'OPEN', @authUrl, @redirectUrl, @requestedAt);

    SELECT * FROM AssociationRequest WHERE associationId = SCOPE_IDENTITY()
END;
GO

-- DROP PROCEDURE IF EXISTS SetUserToken
CREATE OR ALTER PROCEDURE SetUserToken
    @userId INT,
    @serviceId INT,
    @userToken NVARCHAR(MAX)
AS
BEGIN
    UPDATE AssociationRequest
    SET userToken=@userToken
    WHERE userId = @userId
        AND serviceId = @serviceId
        AND state = 'OPEN'

    EXEC FinalizeAssociationRequest @userId, @serviceId
END
GO

-- DROP PROCEDURE IF EXISTS CancelAssociationRequest
CREATE OR ALTER PROCEDURE CancelAssociationRequest
    @userId INT,
    @serviceId INT
AS
BEGIN
    UPDATE AssociationRequest
    SET state = 'CANCELED', canceledAt = GETDATE()
    WHERE userId = @userId
        AND serviceId = @serviceId
        AND (state = 'OPEN' OR state = 'FINALIZED')
END;
GO


-- DROP PROCEDURE IF EXISTS FinalizeAssociationRequest
CREATE OR ALTER PROCEDURE FinalizeAssociationRequest
    @userId INT,
    @serviceId INT
AS
BEGIN
    UPDATE AssociationRequest
    SET state = 'FINALIZED'
    WHERE userId = @userId
        AND serviceId = @serviceId
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
    @associationId INT,
    @filmCode INT,
    @sessionUrl NVARCHAR(MAX),
    @createdAt DATETIME
AS
BEGIN
    DECLARE @canceledAt DATE;
    DECLARE @filmId INT;
    DECLARE @sessionId INT;

    SELECT @createdAt = canceledAt
    FROM AssociationRequest
    WHERE associationId = associationId;

    SELECT @filmId = filmId 
    FROM Film 
    WHERE filmCode = @filmCode 

    IF @canceledAt IS NULL
    BEGIN
        INSERT INTO Sessions
            (usedAt, filmId, sessionUrl, createdAt, expired)
        VALUES
            (NULL, @filmId, @sessionUrl, @createdAt, 0);

        SET @sessionId = SCOPE_IDENTITY()
        EXEC GetSessionById  @sessionId
    END;
END;
GO

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
    SELECT * 
    FROM Sessions
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
