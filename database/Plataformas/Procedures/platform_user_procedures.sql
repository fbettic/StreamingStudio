-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
DROP PROCEDURE IF EXISTS CreatePlatformUser
DROP PROCEDURE IF EXISTS UpdatePlatformUser
DROP PROCEDURE IF EXISTS GetPlatformUserById
*/


-- DROP PROCEDURE IF EXISTS CreatePlatformUser
CREATE OR ALTER PROCEDURE CreatePlatformUser
    @firstname VARCHAR(255),
    @lastname VARCHAR(255),
    @email VARCHAR(255),
    @password VARCHAR(255),
    @phone VARCHAR(255)
AS
BEGIN
    -- Insertar el nuevo usuario en la tabla
    INSERT INTO PlatformUser
        (firstname, lastname, email, password, phone)
    VALUES
        (@firstname, @lastname, @email, @password, @phone)

    -- Obtener el ID del usuario creado
    DECLARE @userId INT
    SET @userId = SCOPE_IDENTITY()

    -- Devolver el usuario recién creado
    EXEC GetPlatformUserById @userId
END
GO

-- DROP PROCEDURE IF EXISTS UpdatePlatformUser
CREATE OR ALTER PROCEDURE UpdatePlatformUser
    @userId INT,
    @firstname VARCHAR(255),
    @lastname VARCHAR(255),
    @email VARCHAR(255),
    @password VARCHAR(255),
    @phone VARCHAR(255)
AS
BEGIN
    -- Actualizar el usuario en la tabla
    UPDATE PlatformUser
    SET firstname = @firstname,
        lastname = @lastname,
        email = @email,
        password = @password,
        phone = @phone
    WHERE userId = @userId

    -- Devolver el usuario actualizado
    EXEC GetPlatformUserById @userId
END
GO

-- DROP PROCEDURE IF EXISTS GetPlatformUserById
CREATE OR ALTER PROCEDURE GetPlatformUserById
    @userId INT
AS
BEGIN
    SELECT userId, firstname, lastname, email, password, phone
    FROM PlatformUser
    WHERE userId = @userId
END
GO

-- DROP PROCEDURE IF EXISTS GetPlatformUserByEmail
CREATE OR ALTER PROCEDURE GetPlatformUserByEmail
    @email VARCHAR(255)
AS
BEGIN
    SELECT userId, firstname, lastname, email, password, phone
    FROM PlatformUser
    WHERE email = @email
END
GO

-- DROP PROCEDURE IF EXISTS GetUserByToken
CREATE OR ALTER PROCEDURE GetUserByToken
    @userToken NVARCHAR(MAX)
AS
BEGIN
    SELECT ar.userId, firstname, lastname, email, password, phone
    FROM AssociationRequest ar
    JOIN PlatformUser pu ON pu.userId = ar.userId
    WHERE userToken = @userToken
    AND [state] != 'CANCELED'
END
GO

SELECT * FROM AssociationRequest

EXEC GetAssociationRequestByToken "DE2DB1A0-B679-41C2-9B77-22B9310DE1A7"
EXEC GetUserByToken "DE2DB1A0-B679-41C2-9B77-22B9310DE1A7"
