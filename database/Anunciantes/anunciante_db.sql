-- CREATE DATABASE anunciante1_db
-- CREATE DATABASE anunciante2_db
-- CREATE DATABASE anunciante3_db

-- DROP DATABASE anunciante1_db
-- DROP DATABASE anunciante2_db
-- DROP DATABASE anunciante3_db

-- USE anunciante1_db
-- USE anunciante2_db
USE anunciante3_db

/*
DROP TABLE IF EXISTS WeeklyAdvertisingReport
DROP TABLE IF EXISTS Advertising
DROP TABLE IF EXISTS Banner                
DROP TABLE IF EXISTS WeeklyPriorityReport
DROP TABLE IF EXISTS WeeklyReport
DROP TABLE IF EXISTS ServiceConnection
DROP TABLE IF EXISTS Priorty
*/


CREATE TABLE Priorty (
  priorityId INT NOT NULL IDENTITY(1,1),
  priorityTitle VARCHAR(255) NOT NULL,
  PRIMARY KEY (priorityId)
);
GO

CREATE TABLE ServiceConnection (
  serviceId INT NOT NULL IDENTITY(1,1),
  name VARCHAR(255) NOT NULL UNIQUE,
  authToken NVARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (serviceId)
);
GO

CREATE TABLE WeeklyReport (
  reportId INT NOT NULL IDENTITY(1,1),
  serviceId INT NOT NULL,
  totalClicks INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);
GO

CREATE TABLE WeeklyPriorityReport (
  reportId INT NOT NULL IDENTITY(1,1),
  priorityId INT NOT NULL,
  clicks INT NOT NULL,
  PRIMARY KEY (reportId, priorityId),
  FOREIGN KEY (priorityId) REFERENCES Priorty(priorityId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);
GO

CREATE TABLE Banner (
  bannerId INT NOT NULL IDENTITY(1,1),
  text VARCHAR(255) NOT NULL,
  imageUrl VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  PRIMARY KEY (bannerId)
);
GO

CREATE TABLE Advertising (
  advertisingId INT NOT NULL IDENTITY(1,1),
  bannerId INT NOT NULL,
  serviceId INT NOT NULL,
  priority INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (advertisingId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId),
  FOREIGN KEY (priority) REFERENCES Priorty(priorityId),
  FOREIGN KEY (bannerId) REFERENCES Banner(bannerId)
);
GO

CREATE TABLE WeeklyAdvertisingReport (
  reportId INT NOT NULL IDENTITY(1,1),
  advertisingId INT NOT NULL,
  clicks INT NOT NULL,
  PRIMARY KEY (reportId, advertisingId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId)
);
GO




use anunciante1_db

EXEC GetBannerById 2

EXEC UpdateBanner 2, "Oferta especial por tiempo limitado", "http://localhost:4210/images/small-banner-animales.jpg", 'https://example.net/sale'