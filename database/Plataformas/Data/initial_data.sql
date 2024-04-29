--################################################################################################
--###################                       PLATAFORMA 1                       ###################
--################################################################################################

USE plataforma1_db
GO

-- Crear países
EXEC CreateCountries 'USA', 'United States of America'
EXEC CreateCountries 'GBR', 'United Kingdom'
EXEC CreateCountries 'FRA', 'France'
GO

-- Crear usuarios de plataforma
EXEC CreatePlatformUser 'John', 'Doe', 'john@example.com', '1234', '123456789'
EXEC CreatePlatformUser 'Jane', 'Smith', 'jane@example.com', '1234', '987654321'
GO

-- Crear conexiones de servicio
EXEC CreateServiceConnection 'Streaming Studio', 'STREAMING_STUDIO_SECRET_KEY'
GO

-- Crear tarifas de plataforma de streaming
DECLARE @serviceId2 INT
SELECT @serviceId2 = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'
EXEC CreateStreamingPlatformFee @serviceId2, 7.99, 0.00, '2024-01-01', '2024-12-31'
GO

-- Crear películas
EXEC CreateFilmIfNotExists 'F00000001', 'Inception', 'https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg', 'A thief who enters the dreams of others to steal their secrets', 1, 0, 'USA', 'Christopher Nolan', 2010, 'Leonardo DiCaprio,Joseph Gordon-Levitt', 'Action,Sci-Fi', 'https://www.youtube.com/embed/RV9L7ui9Cn8?si=0A9oRn2rOc1igucy'
EXEC CreateFilmIfNotExists 'F00000002', 'The Shawshank Redemption', 'https://m.media-amazon.com/images/M/MV5BMzBmNWM0YzktOTM2MC00MzVkLWFiNjUtNWVjMWRiOTQ0OWM0XkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1, 1, 'USA', 'Frank Darabont', 1994, 'Tim Robbins,Morgan Freeman', 'Drama', 'https://www.youtube.com/embed/xB4nJg0fyE0?si=dnYzPe-CWqJcIQCV'
EXEC CreateFilmIfNotExists 'F00000003', 'The Godfather', 'https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 0, 1, 'USA', 'Francis Ford Coppola', 1972, 'Marlon Brando,Al Pacino', 'Crime,Drama', 'https://www.youtube.com/embed/iOyQx7MXaz0?si=LT5xktGoEEIoINAP'
EXEC CreateFilmIfNotExists 'F00000012', 'The Silence of the Lambs', 'https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', 'A young FBI cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', 0, 1, 'USA', 'Jonathan Demme', 1991, 'Jodie Foster,Anthony Hopkins', 'Crime,Drama,Thriller', 'https://www.youtube.com/embed/3VZa6KAxE1I?si=PwMp3Gggce4SeChR'
EXEC CreateFilmIfNotExists 'F00000013', 'Titanic', 'https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_.jpg', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 0, 1, 'USA', 'James Cameron', 1997, 'Leonardo DiCaprio,Kate Winslet', 'Drama,Romance', 'https://www.youtube.com/embed/FiRVcExwBVA?si=UzHUcThRgo6NWEmt'
EXEC CreateFilmIfNotExists 'F00000014', 'Avatar', 'https://m.media-amazon.com/images/M/MV5BZDA0OGQxNTItMDZkMC00N2UyLTg3MzMtYTJmNjg3Nzk5MzRiXkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_.jpg', 'A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.', 1, 1, 'USA', 'James Cameron', 2009, 'Sam Worthington,Zoe Saldana', 'Action,Adventure,Fantasy', 'https://www.youtube.com/embed/Xg8kYk6uHN0?si=hckijugGINV4nziL'
GO

--################################################################################################
--###################                       PLATAFORMA 2                       ###################
--################################################################################################

USE plataforma2_db
GO

-- Crear países
EXEC CreateCountries 'USA', 'United States of America'
EXEC CreateCountries 'GBR', 'United Kingdom'
EXEC CreateCountries 'FRA', 'France'
GO

-- Crear usuarios de plataforma
EXEC CreatePlatformUser 'John', 'Doe', 'john@example.com', '1234', '123456789'
GO

-- Crear conexiones de servicio
EXEC CreateServiceConnection 'Streaming Studio', 'STREAMING_STUDIO_SECRET_KEY'
GO

-- Crear tarifas de plataforma de streaming
DECLARE @serviceId2 INT
SELECT @serviceId2 = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'
EXEC CreateStreamingPlatformFee @serviceId2, 7.99, 0.00, '2024-01-01', '2024-12-31'
GO

-- Crear películas
EXEC CreateFilmIfNotExists 'F00000003', 'The Godfather', 'https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 0, 1, 'USA', 'Francis Ford Coppola', 1972, 'Marlon Brando,Al Pacino', 'Crime,Drama', 'https://www.youtube.com/embed/iOyQx7MXaz0?si=LT5xktGoEEIoINAP'
EXEC CreateFilmIfNotExists 'F00000004', 'The Dark Knight', 'https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 1, 1, 'USA', 'Christopher Nolan', 2008, 'Christian Bale,Heath Ledger', 'Action,Crime,Drama', 'https://www.youtube.com/embed/e0qwi-4iOpE?si=UsHwrl5lWS0vj2P9'
EXEC CreateFilmIfNotExists 'F00000005', 'The Matrix', 'https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 1, 0, 'USA', 'Lana Wachowski, Lilly Wachowski', 1999, 'Keanu Reeves,Laurence Fishburne', 'Action,Sci-Fi', 'https://www.youtube.com/embed/Pl_H2Lmjn6k?si=_dn7lh-HzXeWZ8hC'
EXEC CreateFilmIfNotExists 'F00000006', 'Forrest Gump', 'https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg', 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.', 0, 1, 'USA', 'Robert Zemeckis', 1994, 'Tom Hanks,Robin Wright', 'Dram,Romance', 'https://www.youtube.com/embed/aSEPD9w_dLM?si=erfGy_qktmcr3Qwq'
EXEC CreateFilmIfNotExists 'F00000002', 'The Shawshank Redemption', 'https://m.media-amazon.com/images/M/MV5BMzBmNWM0YzktOTM2MC00MzVkLWFiNjUtNWVjMWRiOTQ0OWM0XkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1, 1, 'USA', 'Frank Darabont', 1994, 'Tim Robbins,Morgan Freeman', 'Drama', 'https://www.youtube.com/embed/xB4nJg0fyE0?si=dnYzPe-CWqJcIQCV'
EXEC CreateFilmIfNotExists 'F00000012', 'The Silence of the Lambs', 'https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', 'A young FBI cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', 0, 1, 'USA', 'Jonathan Demme', 1991, 'Jodie Foster,Anthony Hopkins', 'Crime,Drama,Thriller', 'https://www.youtube.com/embed/3VZa6KAxE1I?si=PwMp3Gggce4SeChR'
EXEC CreateFilmIfNotExists 'F00000013', 'Titanic', 'https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_.jpg', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 0, 1, 'USA', 'James Cameron', 1997, 'Leonardo DiCaprio,Kate Winslet', 'Drama,Romance', 'https://www.youtube.com/embed/FiRVcExwBVA?si=UzHUcThRgo6NWEmt'
GO

--################################################################################################
--###################                       PLATAFORMA 3                       ###################
--################################################################################################

USE plataforma3_db
GO

-- Crear países
EXEC CreateCountries 'USA', 'United States of America'
EXEC CreateCountries 'GBR', 'United Kingdom'
EXEC CreateCountries 'FRA', 'France'
GO

-- Crear usuarios de plataforma
EXEC CreatePlatformUser 'Jane', 'Smith', 'jane@example.com', '1234', '987654321'
GO

-- Crear conexiones de servicio
EXEC CreateServiceConnection 'Streaming Studio', 'STREAMING_STUDIO_SECRET_KEY'
GO

-- Crear tarifas de plataforma de streaming
DECLARE @serviceId2 INT
SELECT @serviceId2 = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'
EXEC CreateStreamingPlatformFee @serviceId2, 7.99, 0.00, '2024-01-01', '2024-12-31'
GO

-- Crear películas
EXEC CreateFilmIfNotExists 'F00000001', 'Inception', 'https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg', 'A thief who enters the dreams of others to steal their secrets', 1, 0, 'USA', 'Christopher Nolan', 2010, 'Leonardo DiCaprio,Joseph Gordon-Levitt', 'Action,Sci-Fi', 'https://www.youtube.com/embed/RV9L7ui9Cn8?si=0A9oRn2rOc1igucy'
EXEC CreateFilmIfNotExists 'F00000007', 'Pulp Fiction', 'https://m.media-amazon.com/images/M/MV5BNjE3ZDQwZDUtNDdhZC00YzcyLWIwYjktZGJlYzA0ZWQwZDJkXkEyXkFqcGdeQXVyNzYxMTcyMzE@._V1_.jpg', 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 0, 1, 'USA', 'Quentin Tarantino', 1994, 'John Travolta,Uma Thurman', 'Crime,Drama', 'https://www.youtube.com/embed/ZFYCXAG6fdo?si=vKfqqClomNOkz-q7'
EXEC CreateFilmIfNotExists 'F00000008', 'Inglourious Basterds', 'https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg', 'In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner''s vengeful plans for the same.', 1, 1, 'USA', 'Quentin Tarantino', 2009, 'Brad Pitt,Diane Kruger', 'Adventure,Drama,War', 'https://www.youtube.com/embed/2jcTRi8D80k?si=ZJ9HxU5R0gN4kSLU'
EXEC CreateFilmIfNotExists 'F00000009', 'Schindler''s List', 'https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 0, 1, 'USA', 'Steven Spielberg', 1993, 'Liam Neeson,Ben Kingsley', 'Biography,Drama,History', 'https://www.youtube.com/embed/BmkchuRJ82w?si=gISrhDek7G1PHwEi'
EXEC CreateFilmIfNotExists 'F00000010', 'The Lord of the Rings: The Fellowship of the Ring', 'https://m.media-amazon.com/images/M/MV5BYzA4Y2Q4YTMtOTI5OC00NWMyLTk0NTUtODc1ZmQxOTY5MmU5XkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_.jpg', 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', 1, 1, 'USA', 'Peter Jackson', 2001, 'Elijah Wood,Ian McKellen', 'Action,Adventure,Drama', 'https://www.youtube.com/embed/_nZdmwHrcnw?si=Jv8oRvO0-H9bj7QI'
EXEC CreateFilmIfNotExists 'F00000002', 'The Shawshank Redemption', 'https://m.media-amazon.com/images/M/MV5BMzBmNWM0YzktOTM2MC00MzVkLWFiNjUtNWVjMWRiOTQ0OWM0XkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1, 1, 'USA', 'Frank Darabont', 1994, 'Tim Robbins,Morgan Freeman', 'Drama', 'https://www.youtube.com/embed/xB4nJg0fyE0?si=dnYzPe-CWqJcIQCV'
GO

