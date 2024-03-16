USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS CreateInvoice
DROP PROCEDURE IF EXISTS GetInvoicesByAdvertiser
DROP PROCEDURE IF EXISTS GetInvoicesByPlatform
DROP PROCEDURE IF EXISTS CreatePlatformInvoiceDetail
DROP PROCEDURE IF EXISTS CreateAssociationsToChargeRelation
DROP PROCEDURE IF EXISTS CreateAdvertisingInvoiceDetail
DROP PROCEDURE IF EXISTS CreatePlatformPaymentRegister
DROP PROCEDURE IF EXISTS CreateAssociationsToPayRelation
*/

------------------- Invoicing procedures -------------------

-- DROP PROCEDURE IF EXISTS CreateInvoice 
CREATE OR ALTER PROCEDURE CreateInvoice 
    @advertiserId INT,
    @platformId INT,
    @invoiceType VARCHAR(255),
    @fromDate DATE ,
    @toDate DATE 
AS
BEGIN

    DECLARE @invoiceid INT

    IF (@invoiceType = 'ADVERTISER' AND @platformId IS NULL AND @advertiserId IS NOT NULL)
    BEGIN
        INSERT INTO Invoice (advertiserId, platformId, invoiceType, fromDate, toDate)
        VALUES (@advertiserId, NULL, @invoiceType, @fromDate, @toDate);
    END;

    IF  (@invoiceType = 'PLATFORM' AND @platformId IS NOT NULL AND @advertiserId IS NULL)
    BEGIN
        INSERT INTO Invoice (advertiserId, platformId, invoiceType, fromDate, toDate)
        VALUES (NULL, @platformId, @invoiceType, @fromDate, @toDate);
        SET @invoiceid = SCOPE_IDENTITY()
        EXEC CreatePlatformInvoiceDetail @platformId, @invoiceid, @fromDate, @toDate
    END
END;
GO

-- DROP PROCEDURE IF EXISTS GetInvoicesByAdvertiser 
CREATE OR ALTER PROCEDURE GetInvoicesByAdvertiser (
    @advertiserId INT
)
AS
BEGIN
    SELECT *
    FROM Invoice
    WHERE advertiserId = @advertiserId
    AND deletedAt IS NULL;
END;
GO

-- DROP PROCEDURE IF EXISTS GetInvoicesByPlatform 
CREATE OR ALTER PROCEDURE GetInvoicesByPlatform (
    @platformId INT
)
AS
BEGIN
    SELECT *
    FROM Invoice
    WHERE platformId = @platformId
    AND deletedAt IS NULL;
END;
GO

-- DROP PROCEDURE IF EXISTS CreatePlatformInvoiceDetail 
CREATE OR ALTER PROCEDURE CreatePlatformInvoiceDetail
    @platformId INT,
    @invoieId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @AllFees AS dbo.AllFees
    DECLARE @signupFeeId FLOAT
    DECLARE @signupFee FLOAT

    DECLARE @detailId INT

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        @signupFeeId = sp.signupFeeId,
        @signupFee = af_s.feeValue
    FROM StreamingPlatform sp
        INNER JOIN @AllFees af_s ON af_s.feeId = sp.loginFeeId
    WHERE platformId = @platformId
        AND deletedAt IS NULL

    INSERT INTO PlatformInvoiceDetail
        (invoiceId, signupFeeId, signupFee)
    VALUES
        (@invoieId, @signupFeeId, @signupFee)

    SET @detailId = SCOPE_IDENTITY()

    EXEC CreateAssociationsToChargeRelation @detailId , @platformId, @fromDate, @toDate
END
GO
    
-- DROP PROCEDURE IF EXISTS CreateAssociationsToChargeRelation 
CREATE OR ALTER PROCEDURE CreateAssociationsToChargeRelation
    @detailId INT,
    @platformId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    DECLARE @Associations AS TABLE(
        transactionId INT,
        platformId INT,
        subscriberId INT
    )

    INSERT INTO @Associations
    EXEC GetAssociationToCharge @platformId, @fromDate, @toDate

    INSERT INTO AssociationsToCharge
        (detailId,transactionId,platformId,subscriberId)
    SELECT @detailId AS detailId, transactionId, platformId, subscriberId
    FROM @Associations
END
GO

-- DROP PROCEDURE IF EXISTS CreateAdvertisingInvoiceDetail 
CREATE OR ALTER PROCEDURE CreateAdvertisingInvoiceDetail
    @invoiceId INT,
    @advertiserId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    DECLARE @AllFees AS dbo.AllFees

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    INSERT INTO AdvertisingInvoiceDetail 
    (invoiceId, advertisingId, priorityId, sizeId, sizeFee, priorityFee, allPagesFee)
    SELECT
        @invoiceId AS invoiceId,
        advertisingId,
        a.priorityId,
        a.sizeId,
        af_st.feeValue AS sizeFee,
        af_bp.feeValue AS priorityFee,
        af.feeValue AS allPagesFee
    FROM Advertising a
    INNER JOIN BannerPriority bp ON bp.priorityId = a.priorityId
    INNER JOIN SizeType st ON st.sizeId = a.sizeId
    INNER JOIN @AllFees af_st ON af_st.feeId = st.sizeFeeId
    INNER JOIN @AllFees af_bp ON af_bp.feeId = bp.priorityFeeId
    INNER JOIN @AllFees af ON af.feeId = a.allPagesFeeId
    WHERE a.advertiserId = @advertiserId 
        AND NOT (a.toDate < @fromDate OR a.fromDate > @toDate)
END
GO

------------------- Payment procedures -------------------

-- DROP PROCEDURE IF EXISTS CreatePlatformPaymentRegister 
CREATE OR ALTER PROCEDURE CreatePlatformPaymentRegister
    @platformId INT,
    @fromDate DATE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @AllFees AS dbo.AllFees
    DECLARE @loginFeeId FLOAT
    DECLARE @loginFee FLOAT
    DECLARE @toDate DATE
    DECLARE @paymentId INT

    SET @toDate=CONVERT(DATE, GETDATE())

    INSERT INTO @AllFees
        (feeId, feeType, feeValue)
    EXEC GetAllFees

    SELECT
        @loginFeeId = sp.loginFeeId,
        @loginFee = af_l.feeValue
    FROM StreamingPlatform sp
        INNER JOIN @AllFees af_l ON af_l.feeId = sp.loginFeeId
    WHERE platformId = @platformId
        AND deletedAt IS NULL

    INSERT INTO PlatformPaymentRegister
        (platformId, fromDate, toDate, loginFeeId, loginFee)
    VALUES
        (@platformId, @fromDate, @toDate, @loginFeeId, @loginFee)

    SET @paymentId = SCOPE_IDENTITY()

    EXEC CreateAssociationsToPayRelation @paymentId , @platformId, @fromDate, @toDate
END
GO


-- DROP PROCEDURE IF EXISTS CreateAssociationsToPayRelation 
CREATE OR ALTER PROCEDURE CreateAssociationsToPayRelation
    @paymentId INT,
    @platformId INT,
    @fromDate DATE,
    @toDate DATE
AS
BEGIN
    DECLARE @Associations AS TABLE(
        transactionId INT,
        platformId INT,
        subscriberId INT
    )

    INSERT INTO @Associations
    EXEC GetAssociationToPay @platformId, @fromDate, @toDate

    INSERT INTO AssociationsToPay
        (paymentId,transactionId,platformId,subscriberId)
    SELECT @paymentId AS paymentId, transactionId, platformId, subscriberId
    FROM @Associations
END
GO
    