-- DROP DATABASE plataforma1_db
-- DROP DATABASE plataforma2_db
-- DROP DATABASE plataforma3_db

-- CREATE DATABASE plataforma1_db
-- CREATE DATABASE plataforma2_db
-- CREATE DATABASE plataforma3_db

-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma1_db

/*
DROP TABLE IF EXISTS AssociationRegisterReport
DROP TABLE IF EXISTS PlayRegisterReport
DROP TABLE IF EXISTS WeeklyReport
DROP TABLE IF EXISTS FilmGenre
DROP TABLE IF EXISTS Genre
DROP TABLE IF EXISTS FilmActor
DROP TABLE IF EXISTS Actor
DROP TABLE IF EXISTS Sessions
DROP TABLE IF EXISTS Film
DROP TABLE IF EXISTS Director
DROP TABLE IF EXISTS Country
DROP TABLE IF EXISTS AssociationRequest
DROP TABLE IF EXISTS PlatformUser
DROP TABLE IF EXISTS StreamingPlatformFee
DROP TABLE IF EXISTS ServiceConnection
*/



CREATE TABLE ServiceConnection
(
  serviceId INT NOT NULL IDENTITY(1,1),
  name VARCHAR(255) NOT NULL UNIQUE,
  authToken NVARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (serviceId)
);
GO

CREATE TABLE StreamingPlatformFee
(
  feeId INT NOT NULL IDENTITY(1,1),
  serviceId INT NOT NULL,
  signupFee FLOAT NOT NULL,
  loginFee FLOAT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE,
  PRIMARY KEY (feeId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);
GO

CREATE TABLE PlatformUser
(
  userId INT NOT NULL IDENTITY(1,1),
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  PRIMARY KEY (userId)
);
GO

CREATE TABLE AssociationRequest
(
  associationId INT NOT NULL IDENTITY(1,1),
  userId INT,
  serviceId INT NOT NULL,
  associationType VARCHAR(255) NOT NULL,
  "state" VARCHAR(255) NOT NULL,
  uuid VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  requestedAt DATETIME NOT NULL,
  userToken NVARCHAR(MAX),
  canceledAt DATETIME,
  PRIMARY KEY (associationId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId),
  FOREIGN KEY (userId) REFERENCES PlatformUser(userId),
  CONSTRAINT chk_state_association_request CHECK (state = 'OPEN' OR state = 'CANCELED' OR state = 'FINALIZED'),
  CONSTRAINT chk_association_type_association_request CHECK (associationType = 'SIGNUP' OR associationType = 'LOGIN')
);
GO

CREATE TABLE Country
(
  countryName VARCHAR(255) NOT NULL UNIQUE,
  countryCode CHAR(3) NOT NULL UNIQUE,
  PRIMARY KEY (countryCode)
);
GO
GO

CREATE TABLE Director
(
  directorId INT NOT NULL IDENTITY(1,1),
  directorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (directorId)
);
GO

CREATE TABLE Film
(
  filmId INT NOT NULL IDENTITY(1,1),
  --IMDb id
  filmCode CHAR(9) NOT NULL UNIQUE,
  title VARCHAR(255) NOT NULL,
  cover VARCHAR(255) NOT NULL,
  description NVARCHAR(MAX) NOT NULL,
  newContent BIT NOT NULL,
  highlighted BIT NOT NULL,
  countryCode CHAR(3) NOT NULL,
  directorId INT NOT NULL,
  year INT NOT NULL,
  url VARCHAR(255) NOT NULL,
  PRIMARY KEY (filmId),
  FOREIGN KEY (countryCode) REFERENCES Country(countryCode),
  FOREIGN KEY (directorId) REFERENCES Director(directorId)
);
GO

CREATE TABLE Sessions
(
  sessionId INT NOT NULL IDENTITY(1,1),
  associationId INT NOT NULL,
  filmId INT,
  sessionUrl VARCHAR(255) NOT NULL,
  createdAt DATE NOT NULL,
  usedAt DATETIME,
  expired BIT NOT NULL,
  PRIMARY KEY (sessionId),
  FOREIGN KEY (associationId) REFERENCES AssociationRequest(associationId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);
GO

CREATE TABLE Actor
(
  actorId INT NOT NULL IDENTITY(1,1),
  actorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (actorId)
);
GO

CREATE TABLE FilmActor
(
  filmId INT NOT NULL,
  actorId INT NOT NULL,
  PRIMARY KEY (filmId, actorId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (actorId) REFERENCES Actor(actorId)
);
GO

CREATE TABLE Genre
(
  genreId INT NOT NULL IDENTITY(1,1),
  genre VARCHAR(255) NOT NULL,
  PRIMARY KEY (genreId)
);
GO

CREATE TABLE FilmGenre
(
  filmId INT NOT NULL,
  genreId INT NOT NULL,
  PRIMARY KEY (filmId, genreId),
  FOREIGN KEY (genreId) REFERENCES Genre(genreId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);
GO

CREATE TABLE WeeklyReport
(
  reportId INT NOT NULL IDENTITY(1,1),
  serviceId INT NOT NULL,
  fromDate DATETIME NOT NULL,
  toDate DATETIME NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);
GO

CREATE TABLE PlayRegisterReport
(
  reportId INT NOT NULL,
  sessionId INT NOT NULL,
  playedAt DATETIME NOT NULL,
  subscriberId INT NOT NULL,
  transactionId INT NOT NULL,
  filmCode VARCHAR(MAX) NOT NULL,
  sessionUrl VARCHAR(MAX) NOT NULL,
  PRIMARY KEY (reportId,sessionId,playedAt),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);
GO

CREATE TABLE AssociationRegisterReport
(
  reportId INT NOT NULL,
  subscriberId INT NOT NULL,
  transactionId INT NOT NULL,
  associationType VARCHAR(MAX) NOT NULL,
  authUrl VARCHAR(MAX) NOT NULL,
  entryDate DATETIME NOT NULL,
  leavingDate DATETIME,
  PRIMARY KEY (reportId,transactionId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport (reportId)
)
GO


select *
from Film

select *
from PlatformUser
select *
from AssociationRequest
WHERE userToken = '0E06FE89-D2B5-4F0E-B7AD-0736B520753F'

EXEC GetAssociationRequestByToken '98F28D9F-AF50-4EFD-A7D0-C0D3BA0AE213'