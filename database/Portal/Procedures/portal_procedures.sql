USE portal_db
GO

/*
DROP PROCEDURE IF EXISTS GetUserByEmail
DROP PROCEDURE IF EXISTS CheckEmailExists
DROP PROCEDURE IF EXISTS CheckEmailExistsUpdate
DROP PROCEDURE IF EXISTS SoftDeleteRecord
DROP TYPE IF EXISTS dbo.UserData
*/

DROP TYPE IF EXISTS dbo.UserData
CREATE TYPE dbo.UserData AS TABLE(
  id INT,
  firstname VARCHAR(255),
  lastname VARCHAR(255),
  userRole VARCHAR(50),
  email VARCHAR(255),
  password VARCHAR(255)
);
GO

-- DROP PROCEDURE IF EXISTS GetUserByEmail
CREATE OR ALTER PROCEDURE GetUserByEmail
  @email VARCHAR(255)
AS
BEGIN
  SET NOCOUNT ON;

  DECLARE @UserData UserData;

  INSERT INTO @UserData
  SELECT administratorId AS id,
    firstname,
    lastname AS lastname,
    'ADMINISTRATOR' AS userRole,
    email,
    password
  FROM dbo.Administrator
  WHERE email = @email AND deletedAt IS NULL;

  IF @@ROWCOUNT = 0
  BEGIN
    INSERT INTO @UserData
    SELECT subscriberId AS id,
      firstname,
      lastname AS lastname,
      'SUBSCRIBER' AS userRole,
      email,
      password
    FROM dbo.Subscriber
    WHERE email = @email AND deletedAt IS NULL;
  END;

  IF @@ROWCOUNT = 0
  BEGIN
    INSERT INTO @UserData
    SELECT advertiserId AS id,
      agentFirstname AS firstname,
      agentLastname AS lastname,
      'ADVERTISER' AS userRole,
      email,
      password
    FROM dbo.Advertiser
    WHERE email = @email AND deletedAt IS NULL;
  END;

  -- Devolver los resultados
  SELECT
    id, firstname, lastname, userRole AS 'role', email, password
  FROM @UserData;
END;
GO

-- DROP PROCEDURE IF EXISTS CheckEmailExists
CREATE OR ALTER PROCEDURE CheckEmailExists
  @email VARCHAR(255),
  @emailExists BIT OUTPUT
AS
BEGIN
  SET NOCOUNT ON;

  SET @emailExists = 0;

  IF EXISTS (SELECT 1
  FROM dbo.Administrator
  WHERE email = @email AND deletedAt IS NULL)
  BEGIN
    SET @emailExists = 1;
    RETURN;
  END;

  IF EXISTS (SELECT 1
  FROM dbo.Subscriber
  WHERE email = @email AND deletedAt IS NULL)
  BEGIN
    SET @emailExists = 1;
    RETURN;
  END;

  IF EXISTS (SELECT 1
  FROM dbo.Advertiser
  WHERE email = @email AND deletedAt IS NULL)
  BEGIN
    SET @emailExists = 1;
    RETURN;
  END;

  IF EXISTS (SELECT 1
  FROM dbo.StreamingPlatform
  WHERE email = @email AND deletedAt IS NULL)
  BEGIN
    SET @emailExists = 1;
    RETURN;
  END;
END;
GO

-- DROP PROCEDURE IF EXISTS CheckEmailExistsUpdate
CREATE OR ALTER PROCEDURE CheckEmailExistsUpdate
  @email VARCHAR(255),
  @excludeUserId INT,
  @excludeTable VARCHAR(50),
  @emailExists BIT OUTPUT
AS
BEGIN
  SET NOCOUNT ON;
  SET @emailExists = 0;
  
  DECLARE @UserData AS TABLE(
    id INT,
    tableName VARCHAR(255)
  );

  INSERT INTO @UserData (id, tableName)
  SELECT administratorId AS id,
    'ADMINISTRATOR' AS tableName
  FROM dbo.Administrator
  WHERE email = @email AND deletedAt IS NULL

  IF @@ROWCOUNT = 0
  BEGIN
    INSERT INTO @UserData (id, tableName)
    SELECT subscriberId AS id,
      'SUBSCRIBER' AS tableName
    FROM dbo.Subscriber
    WHERE email = @email AND deletedAt IS NULL;
  END;

  IF @@ROWCOUNT = 0
  BEGIN
    INSERT INTO @UserData 
    SELECT advertiserId AS id,
      'ADVERTISER' AS tableName
    FROM dbo.Advertiser
    WHERE email = @email AND deletedAt IS NULL;
  END;

  IF @@ROWCOUNT = 0
  BEGIN
    INSERT INTO @UserData 
    SELECT platformId AS id,
      'STREAMINGPLATFORM' AS tableName
    FROM dbo.StreamingPlatform
    WHERE email = @email AND deletedAt IS NULL;
  END;

  IF EXISTS (SELECT 1
  FROM @UserData ud
  WHERE ud.id != @excludeUserId OR ud.tableName != @excludeTable)
  BEGIN 
    SET @emailExists = 1
    RETURN
  END
END;
GO

-- DROP PROCEDURE IF EXISTS SoftDeleteRecord
CREATE OR ALTER PROCEDURE SoftDeleteRecord
  @tableName NVARCHAR(255) ,
  @primaryKeyColumn NVARCHAR(255) ,
  @primaryKeyValue INT
AS
BEGIN
  SET NOCOUNT ON;
  DECLARE @sqlQuery NVARCHAR(MAX);
  DECLARE @columnExists BIT;
  DECLARE @tableExists BIT;

  SELECT @columnExists = COUNT(*)
  FROM sys.columns
  WHERE name = 'deletedAt' AND object_id = Object_ID(@tableName);
  SELECT @tableExists = COUNT(*)
  FROM INFORMATION_SCHEMA.TABLES
  WHERE TABLE_NAME = @tableName AND TABLE_TYPE = 'BASE TABLE';

  IF @tableExists = 0
  BEGIN;
    THROW 50002, 'The specified table does not exist.', 1
  END


  IF @columnExists = 0
  BEGIN;
    THROW 50003, 'The table does not have an "deletedAt" field.', 1
  END

  SET @sqlQuery = 'UPDATE ' + @tableName + ' SET deletedAt = GETDATE() WHERE ' + @primaryKeyColumn + ' = @primaryKeyValue;';
  EXEC sp_executesql @sqlQuery, N'@primaryKeyValue INT', @primaryKeyValue;

  SELECT @primaryKeyValue as deletedId
END;
GO

/*
  EXEC GetUserByEmail "federicobettic@gmail.com"

  DECLARE @Email VARCHAR(255) = 'federicobettic@gmail.com'; 
  DECLARE @EmailExists BIT; 
  EXEC CheckEmailExists @Email, @EmailExists OUTPUT;
  SELECT @EmailExists


  EXEC SoftDeleteRecord Administrator, administratorId, 1
*/