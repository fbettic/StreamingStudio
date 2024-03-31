USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateSubscriber
DROP PROCEDURE IF EXISTS GetSubscriberById
DROP PROCEDURE IF EXISTS UpdateSubscriber
*/

-- DROP PROCEDURE IF EXISTS CreateSubscriber
CREATE OR ALTER PROCEDURE CreateSubscriber
    @firstname VARCHAR(255),
    @lastname VARCHAR(255),
    @email VARCHAR(255),
    @phone VARCHAR(255),
    @birth DATE,
    @password VARCHAR(255),
    @validated BIT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @emailExists BIT;

    EXEC  CheckEmailExists @email, @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END

    INSERT INTO dbo.Subscriber
        ( firstname, lastname, email, phone, birth, password, validated )
    VALUES
        ( @firstname, @lastname, @email, @phone, @birth, @password, @validated )

    EXEC GetSubscriberById @@IDENTITY
END
GO

-- DROP PROCEDURE IF EXISTS UpdateSubscriber
CREATE OR ALTER PROCEDURE UpdateSubscriber
    @subscriberId INT,
    @firstname VARCHAR(255),
    @lastname VARCHAR(255),
    @email VARCHAR(255),
    @phone VARCHAR(255),
    @birth DATE,
    @password VARCHAR(255),
    @validated BIT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @emailExists BIT;

    EXEC CheckEmailExistsUpdate @email, @subscriberId, 'SUBSCRIBER', @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END

    UPDATE Subscriber
    SET firstname=@firstname,
        lastname=@lastname,
        email=@email,
        phone=@phone,
        birth=@birth,
        password=@password,
        validated=@validated
    WHERE subscriberId=@subscriberId

    EXEC GetSubscriberById @subscriberId
END
GO

-- DROP PROCEDURE IF EXISTS GetSubscriberById
CREATE OR ALTER PROCEDURE GetSubscriberById
    @subscriberId INT
AS
BEGIN
    SELECT subscriberId AS id, firstname, lastname, email, phone, birth
    FROM Subscriber
    WHERE subscriberId = @subscriberId
        AND deletedAt IS NULL
END





