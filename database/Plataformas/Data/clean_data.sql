--################################################################################################
--###################                       PLATAFORMA 1                       ###################
--################################################################################################

USE plataforma1_db 

EXEC sp_MSforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';

-- Eliminar datos de las tablas que no tienen referencias
DELETE FROM Genre;
DELETE FROM Actor;
DELETE FROM Director;

-- Eliminar datos de las tablas que tienen referencias
DELETE FROM WeeklyGenreReport;
DELETE FROM WeeklyFilmReport;
DELETE FROM Sessions;
DELETE FROM AssociationRequest;
DELETE FROM WeeklyReport;
DELETE FROM StreamingPlatformFee;

-- Eliminar datos de la tabla ServiceConnection
DELETE FROM ServiceConnection;

-- Eliminar datos de la tabla FilmGenre (se debe hacer después de borrar de la tabla Film)
DELETE FROM FilmGenre;
DELETE FROM FilmActor;

-- Eliminar datos de la tabla Film (se debe hacer después de borrar de las tablas que tienen referencias a ella)
DELETE FROM Film;

-- Finalmente, eliminar datos de la tabla Country
DELETE FROM Country;

EXEC sp_MSforeachtable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL'


--################################################################################################
--###################                       PLATAFORMA 2                       ###################
--################################################################################################

USE plataforma2_db

EXEC sp_MSforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';

-- Eliminar datos de las tablas que no tienen referencias
DELETE FROM Genre;
DELETE FROM Actor;
DELETE FROM Director;

-- Eliminar datos de las tablas que tienen referencias
DELETE FROM WeeklyGenreReport;
DELETE FROM WeeklyFilmReport;
DELETE FROM Sessions;
DELETE FROM AssociationRequest;
DELETE FROM WeeklyReport;
DELETE FROM StreamingPlatformFee;

-- Eliminar datos de la tabla ServiceConnection
DELETE FROM ServiceConnection;

-- Eliminar datos de la tabla FilmGenre (se debe hacer después de borrar de la tabla Film)
DELETE FROM FilmGenre;
DELETE FROM FilmActor;

-- Eliminar datos de la tabla Film (se debe hacer después de borrar de las tablas que tienen referencias a ella)
DELETE FROM Film;

-- Finalmente, eliminar datos de la tabla Country
DELETE FROM Country;

EXEC sp_MSforeachtable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL'


--################################################################################################
--###################                       PLATAFORMA 3                       ###################
--################################################################################################

USE plataforma3_db

EXEC sp_MSforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';

-- Eliminar datos de las tablas que no tienen referencias
DELETE FROM Genre;
DELETE FROM Actor;
DELETE FROM Director;

-- Eliminar datos de las tablas que tienen referencias
DELETE FROM WeeklyGenreReport;
DELETE FROM WeeklyFilmReport;
DELETE FROM Sessions;
DELETE FROM AssociationRequest;
DELETE FROM WeeklyReport;
DELETE FROM StreamingPlatformFee;

-- Eliminar datos de la tabla ServiceConnection
DELETE FROM ServiceConnection;

-- Eliminar datos de la tabla FilmGenre (se debe hacer después de borrar de la tabla Film)
DELETE FROM FilmGenre;
DELETE FROM FilmActor;

-- Eliminar datos de la tabla Film (se debe hacer después de borrar de las tablas que tienen referencias a ella)
DELETE FROM Film;

-- Finalmente, eliminar datos de la tabla Country
DELETE FROM Country;

EXEC sp_MSforeachtable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL'