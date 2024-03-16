-- DROP DATABASE plataforma1_db
-- DROP DATABASE plataforma2_db
-- DROP DATABASE plataforma3_db

-- CREATE DATABASE plataforma1_db
-- CREATE DATABASE plataforma2_db
-- CREATE DATABASE plataforma3_db

-- USE plataforma1_db
-- USE plataforma2_db
USE plataforma3_db

/*
DROP TABLE IF EXISTS WeeklyFilmReport
DROP TABLE IF EXISTS WeeklyGenreReport
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



CREATE TABLE ServiceConnection (
  serviceId INT NOT NULL IDENTITY(1,1),
  name VARCHAR(255) NOT NULL,
  authToken NVARCHAR(MAX) NOT NULL,
  PRIMARY KEY (serviceId)
);
GO

CREATE TABLE StreamingPlatformFee (
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

CREATE TABLE PlatformUser (
  userId INT NOT NULL IDENTITY(1,1),
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  PRIMARY KEY (userId)
);
GO

CREATE TABLE AssociationRequest (
  associationId INT NOT NULL IDENTITY(1,1),
  userId INT NOT NULL,
  serviceId INT NOT NULL,
  associationType VARCHAR(255) NOT NULL,
  "state" VARCHAR(255) NOT NULL,
  authUrl VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  requestedAt DATETIME NOT NULL,
  userToken NVARCHAR(MAX) NOT NULL,
  canceledAt DATETIME,
  PRIMARY KEY (associationId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId),
  FOREIGN KEY (userId) REFERENCES PlatformUser(userId),
  CONSTRAINT chk_state_association_request CHECK (state = 'OPEN' OR state = 'CANCELED' OR state = 'FINALICED'),
  CONSTRAINT chk_association_type_association_request CHECK (state = 'SIGNUP' OR state = 'LOGIN')
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

CREATE TABLE Director (
  directorId INT NOT NULL IDENTITY(1,1),
  directorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (directorId)
);
GO

CREATE TABLE Film (
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
  PRIMARY KEY (filmId),
  FOREIGN KEY (countryCode) REFERENCES Country(countryCode),
  FOREIGN KEY (directorId) REFERENCES Director(directorId)
);
GO

CREATE TABLE Sessions (
  sessionId INT NOT NULL IDENTITY(1,1),
  associationId INT NOT NULL,
  filmId INT,
  sessionUrl NVARCHAR(MAX) NOT NULL,
  createdAt DATE NOT NULL,
  usedAt DATETIME,
  expired BIT NOT NULL,
  PRIMARY KEY (sessionId),
  FOREIGN KEY (associationId) REFERENCES AssociationRequest(associationId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);
GO

CREATE TABLE Actor (
  actorId INT NOT NULL IDENTITY(1,1),
  actorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (actorId)
);
GO

CREATE TABLE FilmActor (
  filmId INT NOT NULL,
  actorId INT NOT NULL,
  PRIMARY KEY (filmId, actorId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (actorId) REFERENCES Actor(actorId)
);
GO

CREATE TABLE Genre (
  genreId INT NOT NULL IDENTITY(1,1),
  genre VARCHAR(255) NOT NULL,
  PRIMARY KEY (genreId)
);
GO

CREATE TABLE FilmGenre (
  filmId INT NOT NULL,
  genreId INT NOT NULL,
  PRIMARY KEY (filmId, genreId),
  FOREIGN KEY (genreId) REFERENCES Genre(genreId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);
GO

CREATE TABLE WeeklyReport (
  reportId INT NOT NULL IDENTITY(1,1),
  serviceId INT NOT NULL,
  signupQuantity INT NOT NULL,
  associationsQuantity INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);
GO

CREATE TABLE WeeklyGenreReport (
  reportId INT NOT NULL,
  genreId INT NOT NULL,
  views INT NOT NULL,
  PRIMARY KEY (reportId, genreId),
  FOREIGN KEY (genreId) REFERENCES Genre(genreId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);
GO

CREATE TABLE WeeklyFilmReport (
  filmId INT NOT NULL,
  reportId INT NOT NULL,
  views INT NOT NULL,
  PRIMARY KEY (filmId, reportId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);
GO





