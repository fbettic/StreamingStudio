-- DROP DATABASE anunciante2_db
-- CREATE DATABASE anunciante2_db
USE anunciante2_db

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

CREATE TABLE ServiceConnection (
  serviceId INT NOT NULL IDENTITY(1,1),
  name VARCHAR(255) NOT NULL,
  authToken TEXT NOT NULL,
  PRIMARY KEY (serviceId)
);

CREATE TABLE WeeklyReport (
  reportId INT NOT NULL IDENTITY(1,1),
  serviceId INT NOT NULL,
  totalClicks INT NOT NULL,
  fromDate DATE NOT NULL,
  toDate DATE NOT NULL,
  PRIMARY KEY (reportId),
  FOREIGN KEY (serviceId) REFERENCES ServiceConnection(serviceId)
);

CREATE TABLE WeeklyPriorityReport (
  reportId INT NOT NULL IDENTITY(1,1),
  priorityId INT NOT NULL,
  clicks INT NOT NULL,
  PRIMARY KEY (reportId, priorityId),
  FOREIGN KEY (priorityId) REFERENCES Priorty(priorityId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId)
);

CREATE TABLE Banner (
  bannerId INT NOT NULL IDENTITY(1,1),
  text VARCHAR(255) NOT NULL,
  imageUrl VARCHAR(255) NOT NULL,
  redirectUrl VARCHAR(255) NOT NULL,
  PRIMARY KEY (bannerId)
);

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

CREATE TABLE WeeklyAdvertisingReport (
  reportId INT NOT NULL IDENTITY(1,1),
  advertisingId INT NOT NULL,
  clicks INT NOT NULL,
  PRIMARY KEY (reportId, advertisingId),
  FOREIGN KEY (reportId) REFERENCES WeeklyReport(reportId),
  FOREIGN KEY (advertisingId) REFERENCES Advertising(advertisingId)
);

-- Inserts para la tabla Priorty
INSERT INTO Priorty (priorityTitle) VALUES ('Alta'),('Media'),('Baja');


-- Inserts para la tabla ServiceConnection
INSERT INTO ServiceConnection (name, authToken) VALUES ('Servicio A', 'token_servicio_A');
INSERT INTO ServiceConnection (name, authToken) VALUES ('Servicio B', 'token_servicio_B');
INSERT INTO ServiceConnection (name, authToken) VALUES ('Servicio C', 'token_servicio_C');

-- Inserts para la tabla WeeklyReport
INSERT INTO WeeklyReport (serviceId, totalClicks, fromDate, toDate) VALUES ( 1, 100, '2024-02-01', '2024-02-07');
INSERT INTO WeeklyReport (serviceId, totalClicks, fromDate, toDate) VALUES ( 2, 150, '2024-02-01', '2024-02-07');
INSERT INTO WeeklyReport (serviceId, totalClicks, fromDate, toDate) VALUES ( 3, 80, '2024-02-01', '2024-02-07');

-- Inserts para la tabla WeeklyPriorityReport
INSERT INTO WeeklyPriorityReport (priorityId, clicks) VALUES (1, 50);
INSERT INTO WeeklyPriorityReport (priorityId, clicks) VALUES (2, 30);
INSERT INTO WeeklyPriorityReport (priorityId, clicks) VALUES (3, 20);

-- Inserts para la tabla Banner
INSERT INTO Banner (text, imageUrl, redirectUrl) VALUES ('Oferta especial', 'imagen_url_1', 'url_redireccion_1');
INSERT INTO Banner (text, imageUrl, redirectUrl) VALUES ('Promoción de temporada', 'imagen_url_2', 'url_redireccion_2');
INSERT INTO Banner (text, imageUrl, redirectUrl) VALUES ('Descuento exclusivo', 'imagen_url_3', 'url_redireccion_3');

-- Inserts para la tabla Advertising
INSERT INTO Advertising (bannerId, serviceId, priority, fromDate, toDate) VALUES (1, 1, 1, '2024-02-01', '2024-02-28');
INSERT INTO Advertising (bannerId, serviceId, priority, fromDate, toDate) VALUES (2, 2, 2, '2024-02-01', '2024-02-28');
INSERT INTO Advertising (bannerId, serviceId, priority, fromDate, toDate) VALUES (3, 3, 3, '2024-02-01', '2024-02-28');

-- Inserts para la tabla WeeklyAdvertisingReport
INSERT INTO WeeklyAdvertisingReport (advertisingId, clicks) VALUES (1, 20);
INSERT INTO WeeklyAdvertisingReport (advertisingId, clicks) VALUES (2, 25);
INSERT INTO WeeklyAdvertisingReport (advertisingId, clicks) VALUES (3, 15);


SELECT * FROM ServiceConnection