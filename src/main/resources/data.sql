--Authirities
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');

-- Admins
INSERT INTO admins(id,name,surname,password,email,birth_date,authority,nickname) VALUES (5,'Juancar','Fernandes','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','admin@pm.me', '2003-07-12', 1, 'admin1');

--Players
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (1,'Angel','García','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','angelgares6424@gmail.com', '2003-07-12', 2, 'Angelgares', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (2,'Jorge','Muñoz','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','jorgemr@pm.me', '2003-07-12', 2, 'Jorge_ADD', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (3,'Javier','Rodriguez','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','java@pm.me', '2003-07-12', 2, 'javrodrei', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (4,'Isaac','Solis','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','princesita@pm.me', '2003-07-12', 2, 'isasolpad', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');

-- Achievements
INSERT INTO achievements(id,name,description,threshold,badge_image,metric,achieve_at) VALUES (1,'Basic expirence','Play 10 games',10.0,'https://cdn-icons-png.flaticon.com/512/5243/5243423.png','GAMES_PLAYED','2022-01-01');
INSERT INTO achievements(id,name,description,threshold,badge_image,metric,achieve_at) VALUES (2,'Explorer','Play 25 games',25.0,'https://cdn-icons-png.flaticon.com/512/603/603855.png','GAMES_PLAYED','2022-01-01');
INSERT INTO achievements(id,name,description,threshold,metric,achieve_at) VALUES (3,'Expert','Win 20 games',20.0,'VICTORIES','2022-01-01');


-- Games 
INSERT INTO games(id,rounds,winner,ended,started) VALUES (1,16,1,'2021-09-02','2021-09-01');