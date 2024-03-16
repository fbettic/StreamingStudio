-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
DROP PROCEDURE IF EXISTS GetToken
DROP PROCEDURE IF EXISTS CreateServiceConnection
DROP PROCEDURE IF EXISTS UpdateServiceConnection
DROP PROCEDURE IF EXISTS GetServiceConnectionById
*/

-- DROP PROCEDURE IF EXISTS GetToken
CREATE OR ALTER PROCEDURE GetToken
    @token NVARCHAR(MAX) OUTPUT
AS
BEGIN
    SET @token = CAST(NEWID() AS VARCHAR(255)) -- Generar un token aleatorio
END
GO

-- DROP PROCEDURE IF EXISTS CreateServiceConnection
CREATE OR ALTER PROCEDURE CreateServiceConnection
    @name VARCHAR(255)
AS
BEGIN
    DECLARE @serviceId INT
    DECLARE @authToken NVARCHAR(MAX)

    -- Generar un token para la nueva conexión
    EXEC GetToken @authToken OUTPUT

    -- Insertar la nueva conexión en la tabla
    INSERT INTO ServiceConnection (name, authToken)
    VALUES (@name, @authToken)

    -- Obtener el ID de la conexión creada
    SET @serviceId = SCOPE_IDENTITY()

    -- Devolver la conexión recién creada
    EXEC GetServiceConnectionById @serviceId
END
GO


-- DROP PROCEDURE IF EXISTS UpdateServiceConnection
CREATE OR ALTER PROCEDURE UpdateServiceConnection
    @serviceId INT,
    @name VARCHAR(255)
AS
BEGIN
    UPDATE ServiceConnection
    SET name = @name
    WHERE serviceId = @serviceId

    -- Devolver la conexión actualizada
    EXEC GetServiceConnectionById @serviceId
END
GO

-- DROP PROCEDURE IF EXISTS GetServiceConnectionById
CREATE OR ALTER PROCEDURE GetServiceConnectionById
    @serviceId INT
AS
BEGIN
    SELECT serviceId, name, authToken
    FROM ServiceConnection
    WHERE serviceId = @serviceId
END
GO
