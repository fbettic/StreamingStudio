USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateAssociationRequest
DROP PROCEDURE IF EXISTS CancelAssociationRequest
DROP PROCEDURE IF EXISTS FinalizeAssociationRequest
DROP PROCEDURE IF EXISTS GetLastOpenAssociationRequest
DROP PROCEDURE IF EXISTS CreateAssociation
DROP PROCEDURE IF EXISTS CancelAssociation
DROP PROCEDURE IF EXISTS GetAssociationToken
DROP PROCEDURE IF EXISTS GetFirstAssociation
DROP PROCEDURE IF EXISTS GetAssociationToPay
DROP PROCEDURE IF EXISTS GetAssociationToCharge
DROP PROCEDURE IF EXISTS CreateSession
DROP PROCEDURE IF EXISTS MarkSessionAsUsed
DROP PROCEDURE IF EXISTS GetSession
*/

------------------- AssociationRequest procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateAssociationRequest
CREATE OR ALTER PROCEDURE CreateAssociationRequest
    @platformId INT,
    @transactionId INT,
    @subscriberId INT,
    @state VARCHAR(255),
    @redirectUrl VARCHAR(255),
    @associationType VARCHAR(255),
    @requestAt DATETIME
AS
BEGIN
    INSERT INTO AssociationRequest
        (platformId, transactionId, subscriberId, state, redirectUrl, associationType, requestAt)
    VALUES
        (@platformId, @transactionId, @subscriberId, 'OPEN', @redirectUrl, @associationType, @requestAt);
END;
GO

-- DROP PROCEDURE IF EXISTS CancelAssociationRequest
CREATE OR ALTER PROCEDURE CancelAssociationRequest
    @platformId INT,
    @transactionId INT,
    @subscriberId INT
AS
BEGIN
    UPDATE AssociationRequest
    SET state = 'CANCELED', closedAt = GETDATE()
    WHERE platformId = @platformId
        AND transactionId = @transactionId
        AND subscriberId = @subscriberId;
END;
GO

-- DROP PROCEDURE IF EXISTS FinalizeAssociationRequest
CREATE OR ALTER PROCEDURE FinalizeAssociationRequest
    @platformId INT,
    @transactionId INT,
    @subscriberId INT
AS
BEGIN
    UPDATE AssociationRequest
    SET state = 'FINALIZED', closedAt = GETDATE()
    WHERE platformId = @platformId
        AND transactionId = @transactionId
        AND subscriberId = @subscriberId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetLastOpenAssociationRequest
CREATE OR ALTER PROCEDURE GetLastOpenAssociationRequest
    @platformId INT,
    @subscriberId INT
AS
BEGIN
    SELECT *
    FROM AssociationRequest
    WHERE platformId = @platformId
        AND subscriberId = @subscriberId
        AND state = 'OPEN'
END
GO

------------------- Association procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateAssociation
CREATE OR ALTER PROCEDURE CreateAssociation
    @platformId INT,
    @transactionId INT,
    @subscriberId INT,
    @userToken NVARCHAR(MAX),
    @entryDate DATE
AS
BEGIN
    DECLARE @associationState VARCHAR(255);
    SELECT @associationState = state
    FROM AssociationRequest
    WHERE platformId = @platformId
        AND transactionId = @transactionId
        AND subscriberId = @subscriberId;

    IF @associationState = 'OPEN'
    BEGIN
        INSERT INTO Association
            (platformId, transactionId, subscriberId, userToken, entryDate)
        VALUES
            (@platformId, @transactionId, @subscriberId, @userToken, @entryDate);

        EXEC FinalizeAssociationRequest @platformId, @transactionId, @subscriberId
    END;
END;
GO

-- DROP PROCEDURE IF EXISTS CancelAssociation
CREATE OR ALTER PROCEDURE CancelAssociation
    @platformId INT,
    @transactionId INT,
    @subscriberId INT
AS
BEGIN
    UPDATE Association
    SET leavingDate = GETDATE()
    WHERE platformId = @platformId
        AND transactionId = @transactionId
        AND subscriberId = @subscriberId;
END;
GO

-- DROP PROCEDURE IF EXISTS GetAssociationToken
CREATE OR ALTER PROCEDURE GetAssociationToken
    @platformId INT,
    @subscriberId INT
AS
BEGIN
    SELECT platformId, subscriberId, userToken
    FROM Association
    WHERE leavingDate IS NULL
        AND platformId = @platformId
        AND subscriberId = @subscriberId
END
GO

-- DROP PROCEDURE IF EXISTS GetFirstAssociation
CREATE OR ALTER PROCEDURE GetFirstAssociation
    @platformId INT,
    @subscriberId INT
AS
BEGIN
    SELECT TOP 1
        *
    FROM Association
    WHERE platformId = @platformId
        AND subscriberId = @subscriberId
    ORDER BY entryDate ASC;
END;
GO

-- DROP PROCEDURE IF EXISTS GetAssociationToPay
CREATE OR ALTER PROCEDURE GetAssociationToPay
    @platformId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    WITH
        FirstAssociations
        AS
        (
            SELECT *,
                ROW_NUMBER() OVER (PARTITION BY subscriberId ORDER BY entryDate ASC) AS RowNum
            FROM Association
            WHERE platformId = @platformId
        )

    SELECT ar.transactionId, ar.platformId, ar.subscriberId, userToken, entryDate, leavingDate
    FROM FirstAssociations fa
        JOIN AssociationRequest ar ON ar.platformId = fa.platformId
            AND ar.transactionId = fa.transactionId
            AND ar.subscriberId = fa.subscriberId
    WHERE RowNum = 1
    AND associationType = 'LOGIN'
    AND fa.entryDate >= @fromDate
    AND fa.entryDate <= @toDate;
END;
GO

-- DROP PROCEDURE IF EXISTS GetAssociationToCharge
CREATE OR ALTER PROCEDURE GetAssociationToCharge
    @platformId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    WITH
        FirstAssociations
        AS
        (
            SELECT *,
                ROW_NUMBER() OVER (PARTITION BY subscriberId ORDER BY entryDate ASC) AS RowNum
            FROM Association
            WHERE platformId = @platformId
        )

    SELECT ar.transactionId, ar.platformId, ar.subscriberId
    FROM FirstAssociations fa
        JOIN AssociationRequest ar ON ar.platformId = fa.platformId
            AND ar.transactionId = fa.transactionId
            AND ar.subscriberId = fa.subscriberId
    WHERE RowNum = 1
    AND associationType = 'SIGNUP'
    AND fa.entryDate >= @fromDate
    AND fa.entryDate <= @toDate;
END;
GO

------------------- Session procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateSession
CREATE OR ALTER PROCEDURE CreateSession
    @subscriberId INT,
    @platformId INT,
    @transactionId INT,
    @filmId INT,
    @sessionUrl NVARCHAR(MAX),
    @createdAt DATETIME
AS
BEGIN
    DECLARE @leavingDate DATE;
    SELECT @leavingDate = leavingDate
    FROM Association
    WHERE platformId = @platformId
        AND transactionId = @transactionId
        AND subscriberId = @subscriberId;

    IF @leavingDate IS NULL
    BEGIN
        INSERT INTO Sessions
            (usedAt, subscriberId, platformId, transactionId, filmid, sessionUrl, createdAt)
        VALUES
            (NULL, @subscriberId, @platformId, @transactionId, @filmId, @sessionUrl, @createdAt);
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

-- DROP PROCEDURE IF EXISTS GetSession
CREATE OR ALTER PROCEDURE GetSession
    @filmId INT,
    @subscriberId INT,
    @platformId INT
AS
BEGIN
    SELECT *
    FROM Sessions
    WHERE filmid = @filmId
        AND subscriberId = @subscriberId
        AND platformId = @platformId;
END;
