-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db
GO

/*
DROP PROCEDURE IF EXISTS CreateStreamingPlatformFee
DROP PROCEDURE IF EXISTS UpdateStreamingPlatformFee
DROP PROCEDURE IF EXISTS GetStreamingPlatformFeeById
*/

-- DROP PROCEDURE IF EXISTS CreateStreamingPlatformFee
CREATE OR ALTER PROCEDURE CreateStreamingPlatformFee
    @serviceId INT,
    @signupFee FLOAT,
    @loginFee FLOAT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    -- Insertar la nueva tarifa en la tabla
    INSERT INTO StreamingPlatformFee (serviceId, signupFee, loginFee, fromDate, toDate)
    VALUES (@serviceId, @signupFee, @loginFee, @fromDate, @toDate)

    -- Obtener el ID de la tarifa creada
    DECLARE @feeId INT
    SET @feeId = SCOPE_IDENTITY()

    -- Devolver la tarifa recién creada
    EXEC GetStreamingPlatformFeeById @feeId
END
GO

-- DROP PROCEDURE IF EXISTS UpdateStreamingPlatformFee
CREATE OR ALTER PROCEDURE UpdateStreamingPlatformFee
    @feeId INT,
    @signupFee FLOAT,
    @loginFee FLOAT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    -- Actualizar la tarifa en la tabla
    UPDATE StreamingPlatformFee
    SET signupFee = @signupFee,
        loginFee = @loginFee,
        fromDate = @fromDate,
        toDate = @toDate
    WHERE feeId = @feeId

    -- Devolver la tarifa actualizada
    EXEC GetStreamingPlatformFeeById @feeId
END
GO

-- DROP PROCEDURE IF EXISTS GetStreamingPlatformFeeById
CREATE OR ALTER PROCEDURE GetStreamingPlatformFeeById
    @feeId INT
AS
BEGIN
    SELECT feeId, serviceId, signupFee, loginFee, fromDate, toDate
    FROM StreamingPlatformFee
    WHERE feeId = @feeId
END
GO
