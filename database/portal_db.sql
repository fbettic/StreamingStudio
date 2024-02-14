
-- CREATE DATABASE portal_db
USE portal_db

/*
DROP TABLE IF EXISTS AdvertisingInvoiceDetail
DROP TABLE IF EXISTS FeeHistory
DROP TABLE IF EXISTS MarketingPreferences
DROP TABLE IF EXISTS FilmGenre
DROP TABLE IF EXISTS SubscriberAdvertisingClick
DROP TABLE IF EXISTS WeeklyAdvertiserReport
DROP TABLE IF EXISTS Administrator
DROP TABLE IF EXISTS AdvertisingTarget
DROP TABLE IF EXISTS TargetCategory
DROP TABLE IF EXISTS Genre
DROP TABLE IF EXISTS PlayRegister
DROP TABLE IF EXISTS WeeklyPlatformFilmReport
DROP TABLE IF EXISTS PlatformFilm
DROP TABLE IF EXISTS FilmActor
DROP TABLE IF EXISTS Actor
DROP TABLE IF EXISTS PlatformInvoiceDetail
DROP TABLE IF EXISTS Sessions
DROP TABLE IF EXISTS Association
DROP TABLE IF EXISTS AssociationRequest
DROP TABLE IF EXISTS Advertising
DROP TABLE IF EXISTS BannerPriority
DROP TABLE IF EXISTS SizeType
DROP TABLE IF EXISTS Subscriber
DROP TABLE IF EXISTS Invoice
DROP TABLE IF EXISTS Advertiser
DROP TABLE IF EXISTS AssociationsToPay
DROP TABLE IF EXISTS PlatformPaymentRegister
DROP TABLE IF EXISTS StreamingPlatform
DROP TABLE IF EXISTS FeeType
DROP TABLE IF EXISTS Film
DROP TABLE IF EXISTS Country
DROP TABLE IF EXISTS Director
*/

CREATE TABLE Director (
  directorId INT NOT NULL,
  directorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (directorId)
);

CREATE TABLE Country (
  countryId INT NOT NULL,
  countryName VARCHAR(255) NOT NULL,
  countryCode CHAR(3) NOT NULL,
  PRIMARY KEY (countryId)
);

CREATE TABLE Film (
  filmId INT NOT NULL,
  filmCode CHAR(9) NOT NULL UNIQUE, -- IMDb CODE
  title VARCHAR(255) NOT NULL,
  cover VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  directorId INT NOT NULL,
  countryId INT NOT NULL,
  year INT NOT NULL,
  PRIMARY KEY (filmId),
  FOREIGN KEY (countryId) REFERENCES Country(countryId),
  FOREIGN KEY (directorId) REFERENCES Director(directorId)
);

CREATE TABLE FeeType (
  feeId INT NOT NULL,
  feeType VARCHAR(255) NOT NULL,
  PRIMARY KEY (feeId)
);

CREATE TABLE StreamingPlatform (
  platformId INT NOT NULL,
  platformName VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  apiUrl VARCHAR(255) NOT NULL,
  authToken TEXT,
  signupFeeId INT NOT NULL,
  loginFeeId INT NOT NULL,
  serviceType VARCHAR(255) NOT NULL,
  PRIMARY KEY (platformId),
  FOREIGN KEY (loginFeeId) REFERENCES FeeType(feeId),
  FOREIGN KEY (signupFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE PlatformPaymentRegister   (
  paymentId INT NOT NULL,
  platformId INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  loginFeeId INT NOT NULL,
  loginFee FLOAT NOT NULL,
  loginQuantity INT NOT NULL,
  PRIMARY KEY (paymentId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (loginFeeId) REFERENCES FeeType(feeId),
);

CREATE TABLE AssociationsToPay (
  paymentId INT NOT NULL,
  associationId INT NOT NULL,
  PRIMARY KEY (paymentId, associationId),
  FOREIGN KEY (paymentId) REFERENCES PlatformPaymentRegister  (paymentId)
);

CREATE TABLE Advertiser (
  advertiserId INT NOT NULL,
  agentName VARCHAR(255) NOT NULL,
  agentLastname VARCHAR(255) NOT NULL,
  companyName VARCHAR(255) NOT NULL,
  bussinesName VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  apiUrl VARCHAR(255),
  authToken TEXT,
  serviceType VARCHAR(255) NOT NULL,
  PRIMARY KEY (advertiserId)
);

CREATE TABLE Invoice (
  advertiserId INT,
  InvoiceId INT NOT NULL,
  platformId INT,
  invoiceType BIT NOT NULL,
  invoiceDate DATE NOT NULL,
  totalAmount FLOAT NOT NULL,
  PRIMARY KEY (InvoiceId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId),
);

CREATE TABLE Subscriber (
  subscriberId INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  birth DATE NOT NULL,
  password VARCHAR(255) NOT NULL,
  validated BIT NOT NULL,
  PRIMARY KEY (subscriberId),
);

CREATE TABLE SizeType (
  sizeFeeId INT NOT NULL,
  sizeType VARCHAR(255) NOT NULL,
  sizeValue INT NOT NULL,
  height INT NOT NULL,
  sizeId INT NOT NULL,
  width INT NOT NULL,
  PRIMARY KEY (sizeId),
  FOREIGN KEY (sizeFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE BannerPriority (
  priorityType VARCHAR(255) NOT NULL,
  priorityValue INT NOT NULL,
  priorityFeeId INT NOT NULL,
  priorityId INT NOT NULL,
  PRIMARY KEY (priorityId),
  FOREIGN KEY (priorityFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE Advertising (
  sizeId INT NOT NULL,
  allPagesFeeId INT NOT NULL,
  advertisingId INT NOT NULL,
  priorityId INT NOT NULL,
  advertiserId INT NOT NULL,
  allPages BIT NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  ImageUrl VARCHAR(255) NOT NULL,
  bannerText VARCHAR(255) NOT NULL,
  bannerId INT,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (advertisingId),
  FOREIGN KEY (sizeId) REFERENCES SizeType(sizeId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId),
  FOREIGN KEY (priorityId) REFERENCES BannerPriority(priorityId),
  FOREIGN KEY (allPagesFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE AssociationRequest (
  platformId INT NOT NULL,
  transactionId INT NOT NULL,
  subscriberId INT NOT NULL,
  "state" VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  associationType VARCHAR(255) NOT NULL,
  requestTime DATETIME NOT NULL,
  clasedTime DATETIME,
  PRIMARY KEY (platformId, transactionId, subscriberId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId)
);

CREATE TABLE Association (
  transactionId INT NOT NULL,
  platformId INT NOT NULL,
  subscriberId INT NOT NULL,
  userToken TEXT,
  entryDate DATE NOT NULL,
  leavingDate DATE,
  PRIMARY KEY (platformId, transactionId, subscriberId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES AssociationRequest(platformId, transactionId, subscriberId),
);

CREATE TABLE Sessions (
  sessionId INT NOT NULL,
  usedAt DATETIME,
  subscriberId INT NOT NULL,
  platformId INT NOT NULL,
  transactionId INT NOT NULL,
  filmid INT,
  sessionToken TEXT NOT NULL,
  creationTime DATETIME NOT NULL,
  expiratonTime INT NOT NULL,
  expired BIT NOT NULL,
  PRIMARY KEY (sessionId),
  FOREIGN KEY (filmid) REFERENCES Film(filmId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES Association(platformId, transactionId, subscriberId),
);

CREATE TABLE PlatformInvoiceDetail (
  detailId INT NOT NULL,
  signupFeeId INT NOT NULL,
  invoiceId INT NOT NULL,
  signupFee FLOAT NOT NULL,
  signupQuantity FLOAT NOT NULL,
  PRIMARY KEY (detailId),
  FOREIGN KEY (invoiceId) REFERENCES Invoice(InvoiceId),
  FOREIGN KEY (signupFeeId) REFERENCES FeeType(feeId)
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

CREATE TABLE PlatformFilm (
  filmId INT NOT NULL,
  platformId INT NOT NULL,
  newContent BIT NOT NULL,
  highlighted BIT NOT NULL,
  PRIMARY KEY (filmId, platformId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId)
);

CREATE TABLE WeeklyPlatformFilmReport (
  reportId INT NOT NULL,
  platformId INT NOT NULL,
  filmId INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  weekViews INT NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (filmId, platformId) REFERENCES PlatformFilm(filmId, platformId),
);

CREATE TABLE PlayRegister (
  reportId INT NOT NULL,
  playedAt DATETIME NOT NULL,
  sessionId INT NOT NULL,
  PRIMARY KEY (reportId, sessionId),
  FOREIGN KEY (reportId) REFERENCES WeeklyPlatformFilmReport(reportId)
);

CREATE TABLE Genre (
  genreId INT NOT NULL,
  genre VARCHAR(255) NOT NULL,
  PRIMARY KEY (genreId)
);

CREATE TABLE TargetCategory (
  targetId INT NOT NULL,
  targetTitle VARCHAR(255) NOT NULL,
  PRIMARY KEY (targetId)
);

CREATE TABLE AdvertisingTarget (
  advertisingId INT NOT NULL,
  targetId INT NOT NULL,
  PRIMARY KEY (advertisingId, targetId),
  FOREIGN KEY (targetId) REFERENCES TargetCategory(targetId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId)
);


CREATE TABLE Administrator (
  administratorId INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (administratorId)
);

CREATE TABLE WeeklyAdvertiserReport (
  reportId INT NOT NULL,
  advertiserId INT NOT NULL,
  weekClicks INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate  DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId)
);

CREATE TABLE SubscriberAdvertisingClick (
  advertisingId INT NOT NULL,
  subscriberId INT NOT NULL,
  reportId INT NOT NULL,
  clickedAt DATETIME NOT NULL,
  PRIMARY KEY (advertisingId, subscriberId, clickedAt),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId),
  FOREIGN KEY (reportId) REFERENCES WeeklyAdvertiserReport(reportId)
);

CREATE TABLE FilmGenre (
  filmId INT NOT NULL,
  genreId INT NOT NULL,
  PRIMARY KEY (filmId, genreId),
  FOREIGN KEY (genreId) REFERENCES Genre(genreId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);

CREATE TABLE MarketingPreferences (
  subscriberId INT NOT NULL,
  targetId INT NOT NULL,
  PRIMARY KEY (subscriberId, targetId),
  FOREIGN KEY (targetId) REFERENCES TargetCategory(targetId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId)
);

CREATE TABLE FeeHistory (
  historiyId INT NOT NULL,
  feeId INT NOT NULL,
  feeValue FLOAT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE,
  PRIMARY KEY (historiyId),
  FOREIGN KEY (feeId) REFERENCES FeeType(feeId)
);

CREATE TABLE AdvertisingInvoiceDetail (
  detailId INT NOT NULL,
  priorityId INT NOT NULL,
  invoiceId INT NOT NULL,
  advertisingId INT NOT NULL,
  sizeId INT  NOT NULL,
  sizeFee FLOAT NOT NULL,
  priorityFee FLOAT NOT NULL,
  allPagesFee FLOAT NOT NULL,
  PRIMARY KEY (detailId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId),
  FOREIGN KEY (priorityId) REFERENCES BannerPriority(priorityId),
  FOREIGN KEY (invoiceId) REFERENCES Invoice(InvoiceId),
  FOREIGN KEY (sizeId) REFERENCES SizeType(sizeId),
);



-- INSERTS
--------------------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------
-- Director
---------------------------------------------------------------------------------------
INSERT INTO Director (directorId, directorName) VALUES (1, 'Director1');



---------------------------------------------------------------------------------------
-- Country
---------------------------------------------------------------------------------------
INSERT INTO Country (countryId, countryName, countryCode) VALUES (1, 'Country1', 'ABC');



---------------------------------------------------------------------------------------
-- Film
---------------------------------------------------------------------------------------
INSERT INTO Film (filmId, filmCode, title, cover, description, directorId, countryId, year) 

VALUES (1, 'ABC123456', 'Film1', 'Cover1.jpg', 'Description1', 1, 1, 2022);


---------------------------------------------------------------------------------------
-- FeeType
---------------------------------------------------------------------------------------
INSERT INTO FeeType (feeId, feeType) VALUES (1, 'FeeType1');



---------------------------------------------------------------------------------------
-- StreamingPlatform
---------------------------------------------------------------------------------------
INSERT INTO StreamingPlatform (platformId, platformName, email, apiUrl, authToken, signupFeeId, loginFeeId, serviceType) 

VALUES (1, 'Platform1', 'platform1@example.com', 'http://api.platform1.com', 'Token1', 1, 1, 'ServiceType1');


---------------------------------------------------------------------------------------
-- PlatformPaymentRegister
---------------------------------------------------------------------------------------
INSERT INTO PlatformPaymentRegister (paymentId, platformId, fromDate, toDate, loginFeeId, loginFee, loginQuantity) 

VALUES (1, 1, '2022-01-01', '2022-02-01', 1, 10.00, 100);


---------------------------------------------------------------------------------------
-- AssociationsToPay
---------------------------------------------------------------------------------------
INSERT INTO AssociationsToPay (paymentId, associationId) VALUES (1, 1);



---------------------------------------------------------------------------------------
-- Advertiser
---------------------------------------------------------------------------------------
INSERT INTO Advertiser (advertiserId, agentName, agentLastname, companyName, bussinesName, email, phone, password, apiUrl, authToken, serviceType) 

VALUES (1, 'Agent1', 'Lastname1', 'Company1', 'Bussines1', 'advertiser1@example.com', '123456789', 'Password1', 'http://api.advertiser1.com', 'Token1', 'ServiceType1');


---------------------------------------------------------------------------------------
-- Invoice
---------------------------------------------------------------------------------------
INSERT INTO Invoice (advertiserId, InvoiceId, platformId, invoiceType, invoiceDate, totalAmount) 

VALUES (1, 1, 1, 1, '2022-01-01', 100.00);


---------------------------------------------------------------------------------------
-- Subscriber
---------------------------------------------------------------------------------------
INSERT INTO Subscriber (subscriberId, name, lastname, email, phone, birth, password, validated) 

VALUES (1, 'Subscriber1', 'Lastname1', 'subscriber1@example.com', '987654321', '2000-01-01', 'Password1', 1);


---------------------------------------------------------------------------------------
-- SizeType
---------------------------------------------------------------------------------------
INSERT INTO SizeType (sizeFeeId, sizeType, sizeValue, height, sizeId, width) 

VALUES (1, 'SizeType1', 10, 20, 1, 30);


---------------------------------------------------------------------------------------
-- BannerPriority
---------------------------------------------------------------------------------------
INSERT INTO BannerPriority (priorityType, priorityValue, priorityFeeId, priorityId) 

VALUES ('PriorityType1', 1, 1, 1);


---------------------------------------------------------------------------------------
-- Advertising
---------------------------------------------------------------------------------------
INSERT INTO Advertising (sizeId, allPagesFeeId, advertisingId, priorityId, advertiserId, allPages, redirectUrl, ImageUrl, bannerText, bannerId, fromDate, toDate) 

VALUES (1, 1, 1, 1, 1, 1, 'http://redirect.url', 'image.jpg', 'BannerText1', 1, '2022-01-01', '2022-02-01');


---------------------------------------------------------------------------------------
-- AssociationRequest
---------------------------------------------------------------------------------------
INSERT INTO AssociationRequest (platformId, transactionId, subscriberId, state, redirectUrl, associationType, requestTime, clasedTime) 

VALUES (1, 1, 1, 'State1', 'http://redirect.url', 'AssociationType1', '2022-01-01 00:00:00', NULL);


---------------------------------------------------------------------------------------
-- Association
---------------------------------------------------------------------------------------
INSERT INTO Association (transactionId, platformId, subscriberId, userToken, entryDate, leavingDate) 

VALUES (1, 1, 1, 'UserToken1', '2022-01-01', NULL);


---------------------------------------------------------------------------------------
-- Sessions
---------------------------------------------------------------------------------------
INSERT INTO Sessions (sessionId, usedAt, subscriberId, platformId, transactionId, filmid, sessionToken, creationTime, expiratonTime, expired) 

VALUES (1, '2022-01-01 00:00:00', 1, 1, 1, 1, 'SessionToken1', '2022-01-01 00:00:00', 60, 0);


---------------------------------------------------------------------------------------
-- PlatformInvoiceDetail
---------------------------------------------------------------------------------------
INSERT INTO PlatformInvoiceDetail (detailId, signupFeeId, invoiceId, signupFee, signupQuantity) 

VALUES (1, 1, 1, 10.00, 1);


---------------------------------------------------------------------------------------
-- Actor
---------------------------------------------------------------------------------------
INSERT INTO Actor (actorId, actorName) VALUES (1, 'Actor1');



---------------------------------------------------------------------------------------
-- FilmActor
---------------------------------------------------------------------------------------
INSERT INTO FilmActor (filmId, actorId) VALUES (1, 1);



---------------------------------------------------------------------------------------
-- PlatformFilm
---------------------------------------------------------------------------------------
INSERT INTO PlatformFilm (filmId, platformId, newContent, highlighted) 

VALUES (1, 1, 1, 1);


---------------------------------------------------------------------------------------
-- WeeklyPlatformFilmReport
---------------------------------------------------------------------------------------
INSERT INTO WeeklyPlatformFilmReport (reportId, platformId, filmId, fromDate, toDate, weekViews) 

VALUES (1, 1, 1, '2022-01-01', '2022-01-07', 100);


---------------------------------------------------------------------------------------
-- PlayRegister
---------------------------------------------------------------------------------------
INSERT INTO PlayRegister (reportId, playedAt, sessionId) 

VALUES (1, '2022-01-01 12:00:00', 1);


---------------------------------------------------------------------------------------
-- Genre
---------------------------------------------------------------------------------------
INSERT INTO Genre (genreId, genre) VALUES (1, 'Genre1');



---------------------------------------------------------------------------------------
-- TargetCategory
---------------------------------------------------------------------------------------
INSERT INTO TargetCategory (targetId, targetTitle) VALUES (1, 'TargetCategory1');



---------------------------------------------------------------------------------------
-- AdvertisingTarget
---------------------------------------------------------------------------------------
INSERT INTO AdvertisingTarget (advertisingId, targetId) VALUES (1, 1);



---------------------------------------------------------------------------------------
-- Administrator
---------------------------------------------------------------------------------------
INSERT INTO Administrator (administratorId, name, lastname, email, password) 

VALUES (1, 'Admin1', 'Lastname1', 'admin1@example.com', 'Password1');


---------------------------------------------------------------------------------------
-- WeeklyAdvertiserReport
---------------------------------------------------------------------------------------
INSERT INTO WeeklyAdvertiserReport (reportId, advertiserId, weekClicks, fromDate, toDate) 

VALUES (1, 1, 50, '2022-01-01', '2022-01-07');


---------------------------------------------------------------------------------------
-- SubscriberAdvertisingClick
---------------------------------------------------------------------------------------
INSERT INTO SubscriberAdvertisingClick (advertisingId, subscriberId, reportId, clickedAt) 

VALUES (1, 1, 1, '2022-01-01 12:00:00');


---------------------------------------------------------------------------------------
-- FilmGenre
---------------------------------------------------------------------------------------
INSERT INTO FilmGenre (filmId, genreId) VALUES (1, 1);



---------------------------------------------------------------------------------------
-- MarketingPreferences
---------------------------------------------------------------------------------------
INSERT INTO MarketingPreferences (subscriberId, targetId) VALUES (1, 1);



---------------------------------------------------------------------------------------
-- FeeHistory
---------------------------------------------------------------------------------------
INSERT INTO FeeHistory (historiyId, feeId, feeValue, fromDate, toDate) 

VALUES (1, 1, 10.00, '2022-01-01', NULL);


---------------------------------------------------------------------------------------
-- AdvertisingInvoiceDetail
---------------------------------------------------------------------------------------
INSERT INTO AdvertisingInvoiceDetail (detailId, priorityId, invoiceId, advertisingId, sizeId, sizeFee, priorityFee, allPagesFee) 

VALUES (1, 1, 1, 1, 1, 5.00, 3.00, 2.00);


SELECT * FROM Film