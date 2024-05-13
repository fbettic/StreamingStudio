 
-- CREATE DATABASE portal_db
USE portal_db

/*
DROP TABLE IF EXISTS AdvertisingInvoiceDetail
DROP TABLE IF EXISTS AssociationsToCharge
DROP TABLE IF EXISTS PlatformInvoiceDetail
DROP TABLE IF EXISTS Invoice
DROP TABLE IF EXISTS AdvertisingClickReport
DROP TABLE IF EXISTS SubscriberAdvertisingClick
DROP TABLE IF EXISTS WeeklyAdvertiserReport
DROP TABLE IF EXISTS PlayRegister
DROP TABLE IF EXISTS WeeklyPlatformFilmReport
DROP TABLE IF EXISTS MarketingPreferences
DROP TABLE IF EXISTS AdvertisingTarget
DROP TABLE IF EXISTS TargetCategory
DROP TABLE IF EXISTS Advertising
DROP TABLE IF EXISTS BannerPriority
DROP TABLE IF EXISTS SizeType
DROP TABLE IF EXISTS Advertiser
DROP TABLE IF EXISTS AssociationsToPay
DROP TABLE IF EXISTS Sessions
DROP TABLE IF EXISTS Association
DROP TABLE IF EXISTS AssociationRequest
DROP TABLE IF EXISTS Subscriber
DROP TABLE IF EXISTS PlatformPaymentRegister
DROP TABLE IF EXISTS PlatformFilm
DROP TABLE IF EXISTS StreamingPlatform
DROP TABLE IF EXISTS FeeHistory
DROP TABLE IF EXISTS FeeType
DROP TABLE IF EXISTS FilmGenre
DROP TABLE IF EXISTS Genre
DROP TABLE IF EXISTS FilmActor
DROP TABLE IF EXISTS Actor
DROP TABLE IF EXISTS Film
DROP TABLE IF EXISTS Country
DROP TABLE IF EXISTS Director
DROP TABLE IF EXISTS Administrator
*/



CREATE TABLE Administrator
(
  administratorId INT NOT NULL IDENTITY(1,1),
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (administratorId)
);
GO

CREATE TABLE Director
(
  directorId INT NOT NULL IDENTITY(1,1),
  directorName VARCHAR(255) NOT NULL,
  PRIMARY KEY (directorId)
);
GO

CREATE TABLE Country
(
  countryName VARCHAR(255) NOT NULL UNIQUE,
  countryCode CHAR(3) NOT NULL UNIQUE,
  PRIMARY KEY (countryCode)
);
GO

CREATE TABLE Film
(
  filmId INT NOT NULL IDENTITY(1,1),
  -- IMDb CODE
  filmCode CHAR(9) NOT NULL UNIQUE ,
  title VARCHAR(255) NOT NULL,
  cover VARCHAR(255) NOT NULL,
  description NVARCHAR(MAX) NOT NULL,
  directorId INT NOT NULL,
  countryCode CHAR(3) NOT NULL,
  year INT NOT NULL,
  PRIMARY KEY (filmId),
  FOREIGN KEY (countryCode) REFERENCES Country(countryCode),
  FOREIGN KEY (directorId) REFERENCES Director(directorId)
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

CREATE TABLE FeeType
(
  feeId INT NOT NULL IDENTITY(1,1),
  feeType VARCHAR(255) NOT NULL,
  deletedAt DATETIME
    PRIMARY KEY (feeId)
);
GO

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
GO

CREATE TABLE StreamingPlatform
(
  platformId INT NOT NULL IDENTITY(1,1),
  platformName VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  apiUrl VARCHAR(255) NOT NULL,
  authToken NVARCHAR(MAX),
  signupFeeId INT NOT NULL,
  loginFeeId INT NOT NULL,
  serviceType VARCHAR(255) NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (platformId),
  FOREIGN KEY (loginFeeId) REFERENCES FeeType(feeId),
  FOREIGN KEY (signupFeeId) REFERENCES FeeType(feeId),
  CONSTRAINT chk_service_type_streaming_platform CHECK (serviceType = 'REST' OR serviceType = 'SOAP')
);
GO

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
GO

-- Lo que el portal tiene que pagar a las plataformas de streaming
CREATE TABLE PlatformPaymentRegister
(
  paymentId INT NOT NULL IDENTITY(1,1),
  platformId INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  loginFeeId INT NOT NULL,
  loginFee FLOAT NOT NULL,
  PRIMARY KEY (paymentId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (loginFeeId) REFERENCES FeeType(feeId),
);
GO




CREATE TABLE Subscriber
(
  subscriberId INT NOT NULL IDENTITY(1,1),
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  phone VARCHAR(255) NOT NULL,
  birth DATE,
  password VARCHAR(255) NOT NULL,
  validated BIT NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (subscriberId),
);
GO

CREATE TABLE AssociationRequest
(
  platformId INT NOT NULL,
  transactionId INT NOT NULL,
  subscriberId INT NOT NULL,
  "state" VARCHAR(255) NOT NULL,
  authUrl VARCHAR(255) NOT NULL,
  -- direccion url a la que debo enviar al usuario
  uuid VARCHAR(255) NOT NULL UNIQUE,
  -- identificador que debera enviar la plataforma cuando nod devuelva al usuario
  associationType VARCHAR(255) NOT NULL,
  requestAt DATETIME NOT NULL,
  closedAt DATETIME,
  PRIMARY KEY (platformId, transactionId, subscriberId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId),
  CONSTRAINT chk_state_association_request CHECK (state = 'OPEN' OR state = 'CANCELED' OR state = 'FINALICED'),
  CONSTRAINT chk_association_type_association_request CHECK (associationType = 'SIGNUP' OR associationType = 'LOGIN')
);
GO

CREATE TABLE Association
(
  transactionId INT NOT NULL,
  platformId INT NOT NULL,
  subscriberId INT NOT NULL,
  userToken NVARCHAR(MAX),
  entryDate DATE NOT NULL,
  leavingDate DATE,
  PRIMARY KEY (platformId, transactionId, subscriberId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES AssociationRequest(platformId, transactionId, subscriberId),
);
GO

-- TODO cambiar a Session
CREATE TABLE Sessions
(
  sessionId INT NOT NULL IDENTITY(1,1),
  usedAt DATETIME,
  subscriberId INT NOT NULL,
  platformId INT NOT NULL,
  transactionId INT NOT NULL,
  filmid INT,
  sessionUrl NVARCHAR(MAX) NOT NULL,
  createdAt DATETIME NOT NULL,
  PRIMARY KEY (sessionId),
  FOREIGN KEY (filmid) REFERENCES Film(filmId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES Association(platformId, transactionId, subscriberId),
);
GO

-- registro de que associations se deben pagar (se debe evitar las que son una reassociacion)
CREATE TABLE AssociationsToPay
(
  paymentId INT NOT NULL,
  transactionId INT NOT NULL,
  platformId INT NOT NULL,
  subscriberId INT NOT NULL,
  PRIMARY KEY (paymentId, platformId, transactionId, subscriberId),
  FOREIGN KEY (paymentId) REFERENCES PlatformPaymentRegister  (paymentId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES Association(platformId, transactionId, subscriberId),
);
GO

CREATE TABLE Advertiser
(
  advertiserId INT NOT NULL IDENTITY(1,1),
  agentFirstname VARCHAR(255) NOT NULL,
  agentLastname VARCHAR(255) NOT NULL,
  companyName VARCHAR(255) NOT NULL,
  bussinesName VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  apiUrl VARCHAR(255),
  authToken VARCHAR(MAX),
  serviceType VARCHAR(255) NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (advertiserId),
  CONSTRAINT chk_service_type CHECK (
    (serviceType = 'ACCOUNT' AND password IS NOT NULL AND apiUrl IS NULL AND authToken IS NULL) OR
    ((serviceType = 'REST' OR serviceType = 'SOAP') AND password IS NULL AND apiUrl IS NOT NULL AND authToken IS NOT NULL)
  )
);
GO

CREATE TABLE SizeType
(
  sizeId INT NOT NULL IDENTITY(1,1),
  sizeFeeId INT NOT NULL,
  sizeType VARCHAR(255) NOT NULL,
  sizeValue INT NOT NULL,
  height INT NOT NULL,
  width INT NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (sizeId),
  FOREIGN KEY (sizeFeeId) REFERENCES FeeType(feeId)
);
GO

CREATE TABLE BannerPriority
(
  priorityId INT NOT NULL IDENTITY(1,1),
  priorityType VARCHAR(255) NOT NULL ,
  priorityValue INT NOT NULL,
  priorityFeeId INT NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (priorityId),
  FOREIGN KEY (priorityFeeId) REFERENCES FeeType(feeId)
);
GO

CREATE TABLE Advertising
(
  advertisingId INT NOT NULL IDENTITY(1,1),
  advertiserId INT NOT NULL,
  sizeId INT NOT NULL,
  allPagesFeeId INT,
  priorityId INT NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  imageUrl VARCHAR(255) NOT NULL,
  bannerText VARCHAR(255) NOT NULL,
  bannerId INT,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  deletedAt DATETIME
    PRIMARY KEY (advertisingId),
  FOREIGN KEY (sizeId) REFERENCES SizeType(sizeId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId),
  FOREIGN KEY (priorityId) REFERENCES BannerPriority(priorityId),
  FOREIGN KEY (allPagesFeeId) REFERENCES FeeType(feeId)
);
GO

CREATE TABLE TargetCategory
(
  targetId INT NOT NULL IDENTITY(1,1),
  targetTitle VARCHAR(255) NOT NULL,
  deletedAt DATETIME,
  PRIMARY KEY (targetId)
);
GO

CREATE TABLE AdvertisingTarget
(
  advertisingId INT NOT NULL,
  targetId INT NOT NULL,
  PRIMARY KEY (advertisingId, targetId),
  FOREIGN KEY (targetId) REFERENCES TargetCategory(targetId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId)
);
GO

CREATE TABLE MarketingPreferences
(
  subscriberId INT NOT NULL,
  targetId INT NOT NULL,
  PRIMARY KEY (subscriberId, targetId),
  FOREIGN KEY (targetId) REFERENCES TargetCategory(targetId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId)
);
GO

CREATE TABLE WeeklyPlatformReport
(
  reportId INT NOT NULL IDENTITY(1,1),
  platformId INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
)

CREATE TABLE PlayRegister
(
  reportId INT NOT NULL,
  playedAt DATETIME NOT NULL,
  sessionId INT NOT NULL,
  PRIMARY KEY (reportId, sessionId),
  FOREIGN KEY (reportId) REFERENCES WeeklyPlatformReport(reportId),
  FOREIGN KEY (sessionId) REFERENCES Sessions(sessionId)
);
GO

CREATE TABLE WeeklyAdvertiserReport
(
  reportId INT NOT NULL IDENTITY(1,1),
  advertiserId INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId)
);
GO

CREATE TABLE SubscriberAdvertisingClick
(
  clickId INT NOT NULL IDENTITY(1,1),
  advertisingId INT NOT NULL,
  subscriberId INT NOT NULL,
  clickedAt DATETIME NOT NULL,
  PRIMARY KEY (clickId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId),
  FOREIGN KEY (subscriberId) REFERENCES Subscriber(subscriberId),
);
GO

CREATE TABLE AdvertisingClickReport
(
  reportId INT NOT NULL,
  clickId INT NOT NULL,
  FOREIGN KEY (reportId) REFERENCES WeeklyAdvertiserReport(reportId),
  FOREIGN KEY (clickId) REFERENCES SubscriberAdvertisingClick(clickId)
)
GO


CREATE TABLE Invoice
(
  invoiceId INT NOT NULL IDENTITY(1,1),
  advertiserId INT,
  platformId INT,
  invoiceType VARCHAR(255) NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  deletedAt DATETIME
    PRIMARY KEY (invoiceId),
  FOREIGN KEY (platformId) REFERENCES StreamingPlatform(platformId),
  FOREIGN KEY (advertiserId) REFERENCES Advertiser(advertiserId),
  CONSTRAINT chk_invoice_type_value_invoice CHECK (invoiceType = 'ADVERTISER' OR invoiceType = 'PLATFORM'),
  CONSTRAINT chk_invoice_type_invoice CHECK (
        (invoiceType = 'ADVERTISER' AND platformId IS NULL AND advertiserId IS NOT NULL)
    OR (invoiceType = 'PLATFORM' AND platformId IS NOT NULL AND advertiserId IS NULL)
  )
);
GO


CREATE TABLE PlatformInvoiceDetail
(
  detailId INT NOT NULL IDENTITY(1,1),
  signupFeeId INT NOT NULL,
  invoiceId INT NOT NULL,
  signupFee FLOAT NOT NULL,
  PRIMARY KEY (detailId),
  FOREIGN KEY (invoiceId) REFERENCES Invoice(invoiceId),
  FOREIGN KEY (signupFeeId) REFERENCES FeeType(feeId),
);
GO

CREATE TABLE AssociationsToCharge
(
  detailId INT NOT NULL,
  transactionId INT NOT NULL,
  platformId INT NOT NULL,
  subscriberId INT NOT NULL,
  PRIMARY KEY (detailId, platformId, transactionId, subscriberId),
  FOREIGN KEY (detailId) REFERENCES PlatformInvoiceDetail(detailId),
  FOREIGN KEY (platformId, transactionId, subscriberId) REFERENCES Association(platformId, transactionId, subscriberId),
);
GO

CREATE TABLE AdvertisingInvoiceDetail
(
  detailId INT NOT NULL IDENTITY(1,1),
  invoiceId INT NOT NULL,
  advertisingId INT NOT NULL,
  priorityId INT NOT NULL,
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
GO

select *
from Film
where filmCode = 'F00000013'

EXEC GetFilmById 7

Insert into AdvertisingTarget
  (targetId, advertisingId)
VALUEs
  (1, 2)

select *
from Advertiser

