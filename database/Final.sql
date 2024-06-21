USE portal_db
--ALTER TABLE Association
--ADD obvservation VARCHAR(1000) NULL;
go



CREATE OR ALTER PROCEDURE UpdateObvservation
    @platformId INT,
    @transactionId INT,
    @subscriberId INT,
    @obvservation VARCHAR(1000)
AS
BEGIN
    BEGIN TRY
    UPDATE Association
    SET obvservation = @obvservation
    WHERE platformId = @platformId
        AND transactionId = @transactionId
        AND subscriberId = @subscriberId;

    SELECT 'Success' AS message;
  END TRY
  BEGIN CATCH
    -- Error
    SELECT ERROR_MESSAGE() AS message;
  END CATCH
END
GO


SELECT * FROM AssociationRequest