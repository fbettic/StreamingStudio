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
EXEC CreatePlatformUser 'John', 'Doe', 'john@example.com', '$2y$10$qv5k4NrXunsY7.qazR7lcO6Zubm8S2o4iU61cTw.3nkZoeHFVmo.u', '123456789'
EXEC CreatePlatformUser 'Jane', 'Smith', 'jane@example.com', '$2y$10$qv5k4NrXunsY7.qazR7lcO6Zubm8S2o4iU61cTw.3nkZoeHFVmo.u', '987654321'
GO

-- Crear conexiones de servicio
EXEC CreateServiceConnection 'Streaming Studio'
GO

-- Crear tarifas de plataforma de streaming
DECLARE @serviceId2 INT
SELECT @serviceId2 = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'
EXEC CreateStreamingPlatformFee @serviceId2, 7.99, 0.00, '2024-01-01', '2024-12-31'
GO

-- Crear películas
EXEC CreateFilmIfNotExists 'F00000001', 'Inception', 'inception.jpg', 'A thief who enters the dreams of others to steal their secrets', 1, 0, 'USA', 'Christopher Nolan', 2010, 'Leonardo DiCaprio,Joseph Gordon-Levitt', 'Action,Sci-Fi'
EXEC CreateFilmIfNotExists 'F00000002', 'The Shawshank Redemption', 'shawshank_redemption.jpg', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1, 1, 'USA', 'Frank Darabont', 1994, 'Tim Robbins,Morgan Freeman', 'Drama'
EXEC CreateFilmIfNotExists 'F00000003', 'The Godfather', 'godfather.jpg', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 0, 1, 'USA', 'Francis Ford Coppola', 1972, 'Marlon Brando,Al Pacino', 'Crime,Drama'
EXEC CreateFilmIfNotExists 'F00000012', 'The Silence of the Lambs', 'silence_of_the_lambs.jpg', 'A young FBI cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', 0, 1, 'USA', 'Jonathan Demme', 1991, 'Jodie Foster,Anthony Hopkins', 'Crime,Drama,Thriller'
EXEC CreateFilmIfNotExists 'F00000013', 'Titanic', 'titanic.jpg', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 0, 1, 'USA', 'James Cameron', 1997, 'Leonardo DiCaprio,Kate Winslet', 'Drama,Romance'
EXEC CreateFilmIfNotExists 'F00000014', 'Avatar', 'avatar.jpg', 'A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.', 1, 1, 'USA', 'James Cameron', 2009, 'Sam Worthington,Zoe Saldana', 'Action,Adventure,Fantasy'
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
EXEC CreatePlatformUser 'John', 'Doe', 'john@example.com', '$2y$10$qv5k4NrXunsY7.qazR7lcO6Zubm8S2o4iU61cTw.3nkZoeHFVmo.u', '123456789'
GO

-- Crear películas
EXEC CreateFilmIfNotExists 'F00000003', 'The Godfather', 'godfather.jpg', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 0, 1, 'USA', 'Francis Ford Coppola', 1972, 'Marlon Brando,Al Pacino', 'Crime,Drama'
EXEC CreateFilmIfNotExists 'F00000004', 'The Dark Knight', 'dark_knight.jpg', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 1, 1, 'USA', 'Christopher Nolan', 2008, 'Christian Bale,Heath Ledger', 'Action,Crime,Drama'
EXEC CreateFilmIfNotExists 'F00000005', 'The Matrix', 'matrix.jpg', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 1, 0, 'USA', 'Lana Wachowski, Lilly Wachowski', 1999, 'Keanu Reeves,Laurence Fishburne', 'Action,Sci-Fi'
EXEC CreateFilmIfNotExists 'F00000006', 'Forrest Gump', 'forrest_gump.jpg', 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.', 0, 1, 'USA', 'Robert Zemeckis', 1994, 'Tom Hanks,Robin Wright', 'Dram,Romance'
EXEC CreateFilmIfNotExists 'F00000002', 'The Shawshank Redemption', 'shawshank_redemption.jpg', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1, 1, 'USA', 'Frank Darabont', 1994, 'Tim Robbins,Morgan Freeman', 'Drama'
EXEC CreateFilmIfNotExists 'F00000012', 'The Silence of the Lambs', 'silence_of_the_lambs.jpg', 'A young FBI cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', 0, 1, 'USA', 'Jonathan Demme', 1991, 'Jodie Foster,Anthony Hopkins', 'Crime,Drama,Thriller'
EXEC CreateFilmIfNotExists 'F00000013', 'Titanic', 'titanic.jpg', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 0, 1, 'USA', 'James Cameron', 1997, 'Leonardo DiCaprio,Kate Winslet', 'Drama,Romance'
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
EXEC CreatePlatformUser 'Jane', 'Smith', 'jane@example.com', '$2y$10$qv5k4NrXunsY7.qazR7lcO6Zubm8S2o4iU61cTw.3nkZoeHFVmo.u', '987654321'
GO

-- Crear películas
EXEC CreateFilmIfNotExists 'F00000001', 'Inception', 'inception.jpg', 'A thief who enters the dreams of others to steal their secrets', 1, 0, 'USA', 'Christopher Nolan', 2010, 'Leonardo DiCaprio,Joseph Gordon-Levitt', 'Action,Sci-Fi'
EXEC CreateFilmIfNotExists 'F00000007', 'Pulp Fiction', 'pulp_fiction.jpg', 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 0, 1, 'USA', 'Quentin Tarantino', 1994, 'John Travolta,Uma Thurman', 'Crime,Drama'
EXEC CreateFilmIfNotExists 'F00000008', 'Inglourious Basterds', 'inglourious_basterds.jpg', 'In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner''s vengeful plans for the same.', 1, 1, 'USA', 'Quentin Tarantino', 2009, 'Brad Pitt,Diane Kruger', 'Adventure,Drama,War'
EXEC CreateFilmIfNotExists 'F00000009', 'Schindler''s List', 'schindlers_list.jpg', 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 0, 1, 'USA', 'Steven Spielberg', 1993, 'Liam Neeson,Ben Kingsley', 'Biography,Drama,History'
EXEC CreateFilmIfNotExists 'F00000010', 'The Lord of the Rings: The Fellowship of the Ring', 'lotr_fellowship.jpg', 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', 1, 1, 'USA', 'Peter Jackson', 2001, 'Elijah Wood,Ian McKellen', 'Action,Adventure,Drama'
EXEC CreateFilmIfNotExists 'F00000011', 'The Shawshank Redemption', 'shawshank_redemption.jpg', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1, 1, 'USA', 'Frank Darabont', 1994, 'Tim Robbins,Morgan Freeman', 'Drama'
GO