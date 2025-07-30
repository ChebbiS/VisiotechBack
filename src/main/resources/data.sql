-- 1. Vider les tables dans l’ordre des dépendances
DELETE FROM favorites;
DELETE FROM watch_history;
DELETE FROM movie_actors;
DELETE FROM movie_genres;
DELETE FROM actors;
DELETE FROM genres;
DELETE FROM movies;
DELETE FROM users;

-- 2. Réinitialiser les IDs (utile en dev, optionnel)
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE movies AUTO_INCREMENT = 1;
ALTER TABLE genres AUTO_INCREMENT = 1;
ALTER TABLE actors AUTO_INCREMENT = 1;
ALTER TABLE watch_history AUTO_INCREMENT = 1;

-- 3. USERS
INSERT INTO users (username, email, password, role) VALUES
('jean', 'jean.dupont@example.com', 'motdepasse123', 'USER'),
('claire', 'claire.leroy@example.com', 'securepass456', 'ADMIN');

-- 4. MOVIES
INSERT INTO movies (title, description, release_year, duration_minutes, language, poster_url) VALUES
('Intouchables', 'Un aristocrate paralysé engage un aide à domicile issu de la banlieue.', 2011, 112, 'Français', 'https://fr.web.img6.acsta.net/img/bb/32/bb329562d3c57e325dcb4ba23425c3e3.jpg'),
('La Haine', 'Trois jeunes banlieusards tentent de survivre après une nuit d’émeutes.', 1995, 98, 'Français', 'https://static.fnac-static.com/multimedia/Images/02/02/39/8F/9386242-1505-1540-1.jpg'),
('Amélie', 'Une jeune femme décide d’améliorer la vie de ceux qui l’entourent.', 2001, 122, 'Français', 'https://m.media-amazon.com/images/I/61bmWwBQ8NL.jpg');

-- 5. GENRES
INSERT INTO genres (name) VALUES
('Comédie'),
('Drame'),
('Romance'),
('Social');

-- 6. MOVIE_GENRES (avec sous-requêtes dynamiques)
INSERT INTO movie_genres (movie_id, genre_id) VALUES
((SELECT id FROM movies WHERE title = 'Intouchables'), (SELECT id FROM genres WHERE name = 'Comédie')),
((SELECT id FROM movies WHERE title = 'Intouchables'), (SELECT id FROM genres WHERE name = 'Drame')),
((SELECT id FROM movies WHERE title = 'La Haine'), (SELECT id FROM genres WHERE name = 'Drame')),
((SELECT id FROM movies WHERE title = 'La Haine'), (SELECT id FROM genres WHERE name = 'Social')),
((SELECT id FROM movies WHERE title = 'Amélie'), (SELECT id FROM genres WHERE name = 'Comédie')),
((SELECT id FROM movies WHERE title = 'Amélie'), (SELECT id FROM genres WHERE name = 'Romance'));

-- 7. ACTORS
INSERT INTO actors (name) VALUES
('Omar Sy'),
('François Cluzet'),
('Vincent Cassel'),
('Mathieu Kassovitz'),
('Audrey Tautou');

-- 8. MOVIE_ACTORS (avec sous-requêtes)
INSERT INTO movie_actors (movie_id, actor_id, role_name) VALUES
((SELECT id FROM movies WHERE title = 'Intouchables'), (SELECT id FROM actors WHERE name = 'Omar Sy'), 'Driss'),
((SELECT id FROM movies WHERE title = 'Intouchables'), (SELECT id FROM actors WHERE name = 'François Cluzet'), 'Philippe'),
((SELECT id FROM movies WHERE title = 'La Haine'), (SELECT id FROM actors WHERE name = 'Vincent Cassel'), 'Vinz'),
((SELECT id FROM movies WHERE title = 'La Haine'), (SELECT id FROM actors WHERE name = 'Mathieu Kassovitz'), 'Hubert'),
((SELECT id FROM movies WHERE title = 'Amélie'), (SELECT id FROM actors WHERE name = 'Audrey Tautou'), 'Amélie Poulain');

-- 9. WATCH_HISTORY (avec sous-requêtes)
INSERT INTO watch_history (user_id, movie_id, progress_percent) VALUES
((SELECT id FROM users WHERE username = 'jean'), (SELECT id FROM movies WHERE title = 'Intouchables'), 100),
((SELECT id FROM users WHERE username = 'jean'), (SELECT id FROM movies WHERE title = 'La Haine'), 80),
((SELECT id FROM users WHERE username = 'claire'), (SELECT id FROM movies WHERE title = 'Amélie'), 100);

-- 10. FAVORITES (avec sous-requêtes)
INSERT INTO favorites (user_id, movie_id) VALUES
((SELECT id FROM users WHERE username = 'jean'), (SELECT id FROM movies WHERE title = 'Amélie')),
((SELECT id FROM users WHERE username = 'claire'), (SELECT id FROM movies WHERE title = 'Intouchables'));
