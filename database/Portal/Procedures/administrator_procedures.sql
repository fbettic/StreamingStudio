USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateAdministrator
DROP PROCEDURE IF EXISTS GetAdministratorById
DROP PROCEDURE IF EXISTS UpdateAdministrator
*/

-- DROP PROCEDURE IF EXISTS CreateAdministrator
CREATE OR ALTER PROCEDURE CreateAdministrator
    @firstname VARCHAR(255),
    @lastname VARCHAR(255),
    @email VARCHAR(255),
    @password VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @emailExists BIT;

    EXEC  CheckEmailExists @email, @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END

    INSERT INTO dbo.Administrator
        (firstname, lastname, email, password)
    VALUES(@firstname, @lastname, @email, @password)

    EXEC GetAdministratorById @@IDENTITY
END
GO

-- DROP PROCEDURE IF EXISTS GetAdministratorById
CREATE OR ALTER PROCEDURE UpdateAdministrator
    @administratorId INT,
    @firstname VARCHAR(255),
    @lastname VARCHAR(255),
    @email VARCHAR(255),
    @password VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON
    DECLARE @emailExists BIT;

    EXEC CheckEmailExistsUpdate @email, @administratorId, 'ADMINISTRATOR', @emailExists OUTPUT;

    IF @emailExists = 1
    BEGIN;
        THROW 50001, 'Email already registered', 1;
    END


    UPDATE Administrator
    SET firstname=@firstname,
        lastname=@lastname,
        email=@email,
        password=@password
    WHERE administratorId=@administratorId

    EXEC GetAdministratorById @administratorId
END
GO

-- DROP PROCEDURE IF EXISTS UpdateAdministrator
CREATE OR ALTER PROCEDURE GetAdministratorById
    @administratorId INT
AS
BEGIN
    SELECT administratorId AS id, firstname, lastname, email
    FROM Administrator
    WHERE administratorId = @administratorId
    AND deletedAt IS NULL
END


--EXEC CreateAdministrator 'admin', 'admin', 'federicobettic@gmail.com', '$2a$10$VE1fyuP3SL9yK1i1NkRHweB76XI1o6qjzJoLUnUzdKBhibqt2H8MK'

-- TRUNCATE TABLE Administrator