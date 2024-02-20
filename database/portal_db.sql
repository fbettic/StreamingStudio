
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
DROP TABLE IF EXISTS PlatformPaymentRegister
DROP TABLE IF EXISTS StreamingPlatform
DROP TABLE IF EXISTS FeeType
DROP TABLE IF EXISTS Film
DROP TABLE IF EXISTS Country
DROP TABLE IF EXISTS Director
*/

CREATE TABLE Director
(
  directorId INT NOT NULL IDENTITY(1,1),
  directorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (directorId)
);

CREATE TABLE Country
(
  countryId INT NOT NULL IDENTITY(1,1),
  countryName VARCHAR(255) NOT NULL,
  countryCode CHAR(3) NOT NULL,
  PRIMARY KEY (countryId)
);

CREATE TABLE Film
(
  filmId INT NOT NULL IDENTITY(1,1),
  -- IMDb CODE
  filmCode CHAR(9) NOT NULL UNIQUE ,
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

CREATE TABLE FeeType
(
  feeId INT NOT NULL IDENTITY(1,1),
  feeType VARCHAR(255) NOT NULL,
  PRIMARY KEY (feeId)
);

CREATE TABLE StreamingPlatform
(
  platformId INT NOT NULL IDENTITY(1,1),
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

CREATE TABLE PlatformPaymentRegister
(
  paymentId INT NOT NULL IDENTITY(1,1),
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

CREATE TABLE Advertiser
(
  advertiserId INT NOT NULL IDENTITY(1,1),
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

CREATE TABLE Invoice
(
  invoiceId INT NOT NULL IDENTITY(1,1),
  advertiserId INT,
  platformId INT,
  invoiceType BIT NOT NULL,
  invoiceDate DATE NOT NULL,
  totalAmount FLOAT NOT NULL,
  PRIMARY KEY (invoiceId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId),
);

CREATE TABLE Subscriber
(
  subscriberId INT NOT NULL IDENTITY(1,1),
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  birth DATE NOT NULL,
  password VARCHAR(255) NOT NULL,
  validated BIT NOT NULL,
  PRIMARY KEY (subscriberId),
);

CREATE TABLE SizeType
(
  sizeId INT NOT NULL IDENTITY(1,1),
  sizeFeeId INT NOT NULL,
  sizeType VARCHAR(255) NOT NULL,
  sizeValue INT NOT NULL,
  height INT NOT NULL,
  width INT NOT NULL,
  PRIMARY KEY (sizeId),
  FOREIGN KEY (sizeFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE BannerPriority
(
  priorityId INT NOT NULL IDENTITY(1,1),
  priorityType VARCHAR(255) NOT NULL ,
  priorityValue INT NOT NULL,
  priorityFeeId INT NOT NULL,
  PRIMARY KEY (priorityId),
  FOREIGN KEY (priorityFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE Advertising
(
  advertisingId INT NOT NULL IDENTITY(1,1),
  advertiserId INT NOT NULL,
  sizeId INT NOT NULL,
  allPagesFeeId INT NOT NULL,
  priorityId INT NOT NULL,
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

CREATE TABLE AssociationRequest
(
  platformId INT NOT NULL,
  transactionId INT NOT NULL,
  subscriberId INT NOT NULL,
  "state" VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  associationType VARCHAR(255) NOT NULL,
  requestTime DATETIME NOT NULL,
  clasedTime DATETIME,
  paymentId INT;
  PRIMARY KEY (platformId, transactionId, subscriberId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId),
  FOREIGN KEY (paymentId) REFERENCES PlatformPaymentRegister(paymentId)
);

CREATE TABLE Association
(
  transactionId INT NOT NULL,
  platformId INT NOT NULL,
  subscriberId INT NOT NULL,
  userToken TEXT,
  entryDate DATE NOT NULL,
  leavingDate DATE,
  PRIMARY KEY (platformId, transactionId, subscriberId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES AssociationRequest(platformId, transactionId, subscriberId),
);

CREATE TABLE Sessions
(
  sessionId INT NOT NULL IDENTITY(1,1),
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

CREATE TABLE PlatformInvoiceDetail
(
  detailId INT NOT NULL IDENTITY(1,1),
  signupFeeId INT NOT NULL,
  invoiceId INT NOT NULL,
  signupFee FLOAT NOT NULL,
  signupQuantity FLOAT NOT NULL,
  PRIMARY KEY (detailId),
  FOREIGN KEY (invoiceId) REFERENCES Invoice(invoiceId),
  FOREIGN KEY (signupFeeId) REFERENCES FeeType(feeId)
);

CREATE TABLE Actor
(
  actorId INT NOT NULL IDENTITY(1,1),
  actorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (actorId)
);

CREATE TABLE FilmActor
(
  filmId INT NOT NULL,
  actorId INT NOT NULL,
  PRIMARY KEY (filmId, actorId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (actorId) REFERENCES Actor(actorId)
);

CREATE TABLE PlatformFilm
(
  filmId INT NOT NULL,
  platformId INT NOT NULL,
  newContent BIT NOT NULL,
  highlighted BIT NOT NULL,
  PRIMARY KEY (filmId, platformId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId)
);

CREATE TABLE WeeklyPlatformFilmReport
(
  reportId INT NOT NULL IDENTITY(1,1),
  platformId INT NOT NULL,
  filmId INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  weekViews INT NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (filmId, platformId) REFERENCES PlatformFilm(filmId, platformId),
);

CREATE TABLE PlayRegister
(
  reportId INT NOT NULL,
  playedAt DATETIME NOT NULL,
  sessionId INT NOT NULL,
  PRIMARY KEY (reportId, sessionId),
  FOREIGN KEY (reportId) REFERENCES WeeklyPlatformFilmReport(reportId)
);

CREATE TABLE Genre
(
  genreId INT NOT NULL IDENTITY(1,1),
  genre VARCHAR(255) NOT NULL,
  PRIMARY KEY (genreId)
);

CREATE TABLE TargetCategory
(
  targetId INT NOT NULL IDENTITY(1,1),
  targetTitle VARCHAR(255) NOT NULL,
  PRIMARY KEY (targetId)
);

CREATE TABLE AdvertisingTarget
(
  advertisingId INT NOT NULL,
  targetId INT NOT NULL,
  PRIMARY KEY (advertisingId, targetId),
  FOREIGN KEY (targetId) REFERENCES TargetCategory(targetId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId)
);


CREATE TABLE Administrator
(
  administratorId INT NOT NULL IDENTITY(1,1),
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (administratorId)
);

CREATE TABLE WeeklyAdvertiserReport
(
  reportId INT NOT NULL IDENTITY(1,1),
  advertiserId INT NOT NULL,
  weekClicks INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId)
);

CREATE TABLE SubscriberAdvertisingClick
(
  advertisingId INT NOT NULL,
  subscriberId INT NOT NULL,
  reportId INT NOT NULL,
  clickedAt DATETIME NOT NULL,
  PRIMARY KEY (advertisingId, subscriberId, clickedAt),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId),
  FOREIGN KEY (reportId) REFERENCES WeeklyAdvertiserReport(reportId)
);

CREATE TABLE FilmGenre
(
  filmId INT NOT NULL,
  genreId INT NOT NULL,
  PRIMARY KEY (filmId, genreId),
  FOREIGN KEY (genreId) REFERENCES Genre(genreId),
  FOREIGN KEY (filmId) REFERENCES Film(filmId)
);

CREATE TABLE MarketingPreferences
(
  subscriberId INT NOT NULL,
  targetId INT NOT NULL,
  PRIMARY KEY (subscriberId, targetId),
  FOREIGN KEY (targetId) REFERENCES TargetCategory(targetId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId)
);

CREATE TABLE FeeHistory
(
  historiyId INT NOT NULL IDENTITY(1,1),
  feeId INT NOT NULL,
  feeValue FLOAT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE,
  PRIMARY KEY (historiyId),
  FOREIGN KEY (feeId) REFERENCES FeeType(feeId)
);

CREATE TABLE AdvertisingInvoiceDetail
(
  detailId INT NOT NULL IDENTITY(1,1),
  priorityId INT NOT NULL,
  invoiceId INT NOT NULL,
  advertisingId INT NOT NULL,
  sizeId INT NOT NULL,
  sizeFee FLOAT NOT NULL,
  priorityFee FLOAT NOT NULL,
  allPagesFee FLOAT NOT NULL,
  PRIMARY KEY (detailId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId),
  FOREIGN KEY (priorityId) REFERENCES BannerPriority(priorityId),
  FOREIGN KEY (invoiceId) REFERENCES Invoice(invoiceId),
  FOREIGN KEY (sizeId) REFERENCES SizeType(sizeId),
);


