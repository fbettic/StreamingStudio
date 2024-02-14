-- CREATE DATABASE platafortma1_db

USE platafortma1_db

/*
DROP TABLE IF EXISTS FilmGenre
DROP TABLE IF EXISTS WeeklyFilmReport
DROP TABLE IF EXISTS StreamingPlatformFee
DROP TABLE IF EXISTS WeeklyGenreReport
DROP TABLE IF EXISTS WeeklyReport
DROP TABLE IF EXISTS FilmActor
DROP TABLE IF EXISTS Actor
DROP TABLE IF EXISTS Genre
DROP TABLE IF EXISTS Film
DROP TABLE IF EXISTS Country
DROP TABLE IF EXISTS Sessions
DROP TABLE IF EXISTS AssociationRequest
DROP TABLE IF EXISTS PlatformUser
DROP TABLE IF EXISTS ServiceConnection
DROP TABLE IF EXISTS Director
*/

CREATE TABLE Director (
  directorId INT NOT NULL,
  directorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (directorId)
);

CREATE TABLE ServiceConnection (
  serviceId INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  authToken TEXT,
  PRIMARY KEY (serviceId)
);

CREATE TABLE PlatformUser (
  userId INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  PRIMARY KEY (userId)
);

CREATE TABLE AssociationRequest (
  associationId INT NOT NULL,
  userId INT NOT NULL,
  serviceId INT NOT NULL,
  type VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  aauthUrl VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  requested DATETIME NOT NULL,
  userToken TEXT,
  PRIMARY KEY (associationId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId),
  FOREIGN KEY (userId) REFERENCES PlatformUser(userId)
);

CREATE TABLE Sessions (
  sessionId INT NOT NULL,
  assosiationId INT NOT NULL,
  sessionToken TEXT NOT NULL,
  used BIT NOT NULL,
  creationDate DATE NOT NULL,
  expirationDate DATE NOT NULL,
  useDate DATE,
  expired BIT NOT NULL,
  PRIMARY KEY (sessionId),
  FOREIGN KEY (assosiationId) REFERENCES AssociationRequest(associationId)
);

CREATE TABLE Country (
  countryId INT NOT NULL,
  countryName VARCHAR(255) NOT NULL,
  countryCode CHAR(3) NOT NULL,
  PRIMARY KEY (countryId)
);

CREATE TABLE Film (
  filmId INT NOT NULL,
  filmCode CHAR(9) NOT NULL UNIQUE, --IMDb id
  title VARCHAR(255) NOT NULL,
  cover VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  newContent BIT NOT NULL,
  highlighted BIT NOT NULL,
  countryId INT NOT NULL,
  directorId INT NOT NULL,
  year INT NOT NULL,
  PRIMARY KEY (filmId),
  FOREIGN KEY (countryId) REFERENCES Country(countryId),
  FOREIGN KEY (directorId) REFERENCES Director(directorId)
);

CREATE TABLE Genre (
  genreId INT NOT NULL,
  genre VARCHAR(255) NOT NULL,
  PRIMARY KEY (genreId)
);

CREATE TABLE Actor (
  actorId INT NOT NULL,
  actorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (actorId)
);

CREATE TABLE FilmActor (
  filmId INT NOT NULL,
  actorId INT NOT NULL,
  PRIMARY KEY (filmId, actorId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (actorId) REFERENCES Actor(actorId)
);

CREATE TABLE WeeklyReport (
  reportId INT NOT NULL,
  serviceId INT NOT NULL,
  signupQuantity INT NOT NULL,
  associationsQuantity INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);

CREATE TABLE WeeklyGenreReport (
  reportId INT NOT NULL,
  genreId INT NOT NULL,
  views INT NOT NULL,
  PRIMARY KEY (reportId, genreId),
  FOREIGN KEY (genreId) REFERENCES Genre(genreId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);

CREATE TABLE StreamingPlatformFee (
  feeId INT NOT NULL,
  serviceId INT NOT NULL,
  signupFee FLOAT NOT NULL,
  loginFee FLOAT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE,
  PRIMARY KEY (feeId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);

CREATE TABLE WeeklyFilmReport (
  filmId INT NOT NULL,
  reportId INT NOT NULL,
  views INT NOT NULL,
  PRIMARY KEY (filmId, reportId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);

CREATE TABLE FilmGenre (
  filmId INT NOT NULL,
  genreId INT NOT NULL,
  PRIMARY KEY (filmId, genreId),
  FOREIGN KEY (filmId) REFERENCES Genre(genreId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);


-- Inserting data into the Director table
INSERT INTO Director (directorId, directorName) VALUES (1, 'Christopher Nolan');
INSERT INTO Director (directorId, directorName) VALUES (2, 'Quentin Tarantino');

-- Inserting data into the ServiceConnection table
INSERT INTO ServiceConnection (serviceId, name, authToken) VALUES (1, 'Netflix', 'abcdef123456');
INSERT INTO ServiceConnection (serviceId, name, authToken) VALUES (2, 'Amazon Prime Video', 'uvwxyz789012');

-- Inserting data into the PlatformUser table
INSERT INTO PlatformUser (userId, name, lastname, email, password, phone) VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password123', '123456789');
INSERT INTO PlatformUser (userId, name, lastname, email, password, phone) VALUES (2, 'Jane', 'Smith', 'jane.smith@example.com', 'abc123', '987654321');

-- Inserting data into the AssociationRequest table
INSERT INTO AssociationRequest (associationId, userId, serviceId, type, state, aauthUrl, redirectUrl, requested, userToken) VALUES (1, 1, 1, 'Subscription', 'Pending', 'https://example.com/auth', 'https://example.com/redirect', '2022-01-01 00:00:00', 'user_token_123');
INSERT INTO AssociationRequest (associationId, userId, serviceId, type, state, aauthUrl, redirectUrl, requested, userToken) VALUES (2, 2, 2, 'Subscription', 'Approved', 'https://example.com/auth', 'https://example.com/redirect', '2022-01-02 00:00:00', 'user_token_456');

-- Inserting data into the Sessions table
INSERT INTO Sessions (sessionId, assosiationId, sessionToken, used, creationDate, expirationDate, useDate, expired) VALUES (1, 1, 'session_token_123', 0, '2024-02-11', '2024-02-12', NULL, 0);
INSERT INTO Sessions (sessionId, assosiationId, sessionToken, used, creationDate, expirationDate, useDate, expired) VALUES (2, 2, 'session_token_456', 0, '2024-02-11', '2024-02-12', NULL, 0);

-- Inserting data into the Country table
INSERT INTO Country (countryId, countryName, countryCode) VALUES (1, 'United States', 'USA');
INSERT INTO Country (countryId, countryName, countryCode) VALUES (2, 'United Kingdom', 'UK');

-- Inserting data into the Film table
INSERT INTO Film (filmId, title, filmCode, cover, description, newContent, highlighted, countryId, directorId, year) VALUES (1, 'Inception', 'tt1375666', 'inception.jpg', 'A thief who enters the dreams of others to steal secrets from their subconscious.', 1, 1, 1, 1, 2010);
INSERT INTO Film (filmId, title, filmCode, cover, description, newContent, highlighted, countryId, directorId, year) VALUES (2, 'Pulp Fiction', 'tt0110912', 'pulpfiction.jpg', 'Various interconnected stories of criminals and their misadventures.', 0, 1, 1, 2, 1994);

-- Inserting data into the Genre table
INSERT INTO Genre (genreId, genre) VALUES (1, 'Action');
INSERT INTO Genre (genreId, genre) VALUES (2, 'Crime');

-- Inserting data into the Actor table
INSERT INTO Actor (actorId, actorName) VALUES (1, 'Leonardo DiCaprio');
INSERT INTO Actor (actorId, actorName) VALUES (2, 'Samuel L. Jackson');

-- Inserting data into the FilmActor table
INSERT INTO FilmActor (filmId, actorId) VALUES (1, 1);
INSERT INTO FilmActor (filmId, actorId) VALUES (2, 2);

-- Inserting data into the WeeklyReport table
INSERT INTO WeeklyReport (reportId, serviceId, signupQuantity, associationsQuantity, fromDate, toDate) VALUES (1, 1, 100, 50, '2024-02-04', '2024-02-10');
INSERT INTO WeeklyReport (reportId, serviceId, signupQuantity, associationsQuantity, fromDate, toDate) VALUES (2, 2, 150, 75, '2024-02-04', '2024-02-10');

-- Inserting data into the WeeklyGenreReport table
INSERT INTO WeeklyGenreReport (reportId, genreId, views) VALUES (1, 1, 500);
INSERT INTO WeeklyGenreReport (reportId, genreId, views) VALUES (1, 2, 300);
INSERT INTO WeeklyGenreReport (reportId, genreId, views) VALUES (2, 1, 600);
INSERT INTO WeeklyGenreReport (reportId, genreId, views) VALUES (2, 2, 400);

-- Inserting data into the StreamingPlatformFee table
INSERT INTO StreamingPlatformFee (feeId, serviceId, signupFee, loginFee, fromDate, toDate) VALUES (1, 1, 9.99, 0.50, '2024-02-01', '2024-03-01');
INSERT INTO StreamingPlatformFee (feeId, serviceId, signupFee, loginFee, fromDate, toDate) VALUES (2, 2, 8.99, 0.75, '2024-02-01', '2024-03-01');

-- Inserting data into the WeeklyFilmReport table
INSERT INTO WeeklyFilmReport (filmId, reportId, views) VALUES (1, 1, 1000);
INSERT INTO WeeklyFilmReport (filmId, reportId, views) VALUES (2, 1, 800);
INSERT INTO WeeklyFilmReport (filmId, reportId, views) VALUES (1, 2, 1200);
INSERT INTO WeeklyFilmReport (filmId, reportId, views) VALUES (2, 2, 900);

-- Inserting data into the FilmGenre table
INSERT INTO FilmGenre (filmId, genreId) VALUES (1, 1);
INSERT INTO FilmGenre (filmId, genreId) VALUES (2, 2);


SELECT * FROM Film