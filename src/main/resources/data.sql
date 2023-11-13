--Authorities
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');

-- Admins Users
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname, avatar) VALUES (1,'Alvaro','Fernandez','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','admin1@gmail.com', '2003-07-12', 1, 'admin1', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname, avatar) VALUES (2,'Carlos','Prieto','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','admin2@gmail.com', '2003-07-12', 1, 'admin2', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');

-- Player Users
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (3,'Angel','Garcia','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','angelgares6424@gmail.com', '2003-07-12', 2, 'Angelgares', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (4,'Jorge','Muñoz','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','jorgemr@pm.me', '2003-07-12', 2, 'Jorge_ADD', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (5,'Javier','Rodriguez','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','java@pm.me', '2003-07-12', 2, 'javrodrei', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (6,'Isaac','Solis','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','princesita@pm.me', '2003-07-12', 2, 'isasolpad', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (7,'Andres','Garcia','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','andres@gmail.com', '2003-07-12', 2, 'andgarriv', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (8,'Alejandro','Perez','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','alejandro@gmail.com', '2003-07-12', 2, 'alepersan', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');


-- Achievements
INSERT INTO achievements(id,name,description,threshold,badge_image,metric) VALUES (1,'Basic expirence','Play 10 games',10.0,'https://cdn-icons-png.flaticon.com/512/5243/5243423.png','GAMES_PLAYED');
INSERT INTO achievements(id,name,description,threshold,badge_image,metric) VALUES (2,'Explorer','Play 25 games',25.0,'https://cdn-icons-png.flaticon.com/512/603/603855.png','GAMES_PLAYED');
INSERT INTO achievements(id,name,description,threshold,metric) VALUES (3,'Expert','Win 20 games',20.0,'VICTORIES');

-- Player Achievements
INSERT INTO player_achievements(id,achieve_at,achievement_id)VALUES(1,'2021-09-01',1);
INSERT INTO player_achievements(id,achievement_id)VALUES(2,2);
INSERT INTO player_achievements(id,achievement_id)VALUES(3,3);


--Player PlayerAchievements
INSERT INTO players_player_achievement(player_achievement_id, player_id)VALUES(1,6),(2,6),(3,5);




-- GamePlayers

INSERT INTO game_players(id,energy,player_id,color) VALUES (3,3,3,'RED'); --player 3
INSERT INTO game_players(id,energy,player_id,color) VALUES (4,3,4,'BLUE'); --player 4

INSERT INTO game_players(id,energy,player_id,color) VALUES (5,3,5,'BLUE'); --player 5
INSERT INTO game_players(id,energy,player_id,color) VALUES (6,3,6,'RED'); --player 6

INSERT INTO game_players(id,energy,player_id,color) VALUES (7,3,5,'BLUE'); --player 5
INSERT INTO game_players(id,energy,player_id,color) VALUES (8,3,6,'RED'); --player 6


-- Games 
INSERT INTO games(id,rounds,winner,ended,started) VALUES (1,16,3,'2021-09-02','2021-09-01');
INSERT INTO games(id,rounds,winner,ended,started) VALUES (2,18,5,'2021-09-02','2021-09-01');
INSERT INTO games(id,rounds,winner,ended,started) VALUES (3,24,null,null,'2021-01-01');



-- Games_Game_Players
INSERT INTO games_game_players(game_id,game_players_id) VALUES (1,3);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (1,4);
--Player con id 3 y 4 juegan la partida 1 
INSERT INTO games_game_players(game_id,game_players_id) VALUES (2,5);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (2,6);
--Player con id 5 y 6 juegan la partida 1 
INSERT INTO games_game_players(game_id,game_players_id) VALUES (3,7);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (3,8);






