-- USE anunciante1_db
-- USE anunciante2_db
USE anunciante3_db
GO

/*
DROP PROCEDURE IF EXISTS CreatePriority
DROP PROCEDURE IF EXISTS GetPriorityById
DROP PROCEDURE IF EXISTS CreateBanner
DROP PROCEDURE IF EXISTS GetBannerById
DROP PROCEDURE IF EXISTS CreateAdvertising
DROP PROCEDURE IF EXISTS GetAdvertisingById
*/

------------------- Priority procedures -------------------

-- DROP PROCEDURE IF EXISTS CreatePriority
CREATE OR ALTER PROCEDURE CreatePriority
    @priorityTitle VARCHAR(255)
AS
BEGIN
    -- Insertar la nueva prioridad en la tabla Priority
    INSERT INTO Priorty (priorityTitle)
    VALUES (@priorityTitle)

    -- Obtener el ID de la prioridad creada
    DECLARE @priorityId INT
    SET @priorityId = SCOPE_IDENTITY()

    -- Devolver la prioridad recién creada
    EXEC GetPriorityById @priorityId
END
GO

-- DROP PROCEDURE IF EXISTS GetPriorityById
CREATE OR ALTER PROCEDURE GetPriorityById
    @priorityId INT
AS
BEGIN
    SELECT priorityId, priorityTitle
    FROM Priorty
    WHERE priorityId = @priorityId
END
GO

------------------- Banner procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateBanner
CREATE OR ALTER PROCEDURE CreateBanner
    @text VARCHAR(255),
    @imageUrl VARCHAR(255),
    @redirectUrl VARCHAR(255)
AS
BEGIN
    -- Insertar el nuevo banner en la tabla Banner
    INSERT INTO Banner (text, imageUrl, redirectUrl)
    VALUES (@text, @imageUrl, @redirectUrl)

    -- Obtener el ID del banner creado
    DECLARE @bannerId INT
    SET @bannerId = SCOPE_IDENTITY()

    -- Devolver el banner recién creado
    EXEC GetBannerById @bannerId
END
GO

-- DROP PROCEDURE IF EXISTS GetBannerById
CREATE OR ALTER PROCEDURE GetBannerById
    @bannerId INT
AS
BEGIN
    SELECT bannerId, text, imageUrl, redirectUrl
    FROM Banner
    WHERE bannerId = @bannerId
END
GO

-- DROP PROCEDURE IF EXISTS GetAllBanners
CREATE OR ALTER PROCEDURE GetAllBanners
AS
BEGIN
    SELECT bannerId, text, imageUrl, redirectUrl
    FROM Banner
END
GO

------------------- Advertising procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateAdvertising
CREATE OR ALTER PROCEDURE CreateAdvertising
    @bannerId INT,
    @serviceId INT,
    @priority INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    -- Insertar el nuevo anuncio publicitario en la tabla Advertising
    INSERT INTO Advertising (bannerId, serviceId, priority, fromDate, toDate)
    VALUES (@bannerId, @serviceId, @priority, @fromDate, @toDate)

    -- Obtener el ID del anuncio publicitario creado
    DECLARE @advertisingId INT
    SET @advertisingId = SCOPE_IDENTITY()

    -- Devolver el anuncio publicitario recién creado
    EXEC GetAdvertisingById @advertisingId
END
GO

-- DROP PROCEDURE IF EXISTS GetAdvertisingById
CREATE OR ALTER PROCEDURE GetAdvertisingById
    @advertisingId INT
AS
BEGIN
    SELECT advertisingId, a.bannerId, text, imageUrl, redirectUrl, serviceId, priorityTitle, fromDate, toDate
    FROM Advertising a
    JOIN Priorty p ON p.priorityId = a.priority
    JOIN Banner b ON b.bannerId = a.bannerId
    WHERE advertisingId = @advertisingId
END
GO

-- DROP PROCEDURE IF EXISTS GetAllAdvertisings
CREATE OR ALTER PROCEDURE GetAllAdvertisings
    @serviceId INT
AS
BEGIN
    SELECT advertisingId, a.bannerId, text, imageUrl, redirectUrl, serviceId, priorityTitle, fromDate, toDate
    FROM Advertising a
    JOIN Priorty p ON p.priorityId = a.priority
    JOIN Banner b ON b.bannerId = a.bannerId
    WHERE serviceId = @serviceId
END