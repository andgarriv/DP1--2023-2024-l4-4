--Authorities
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');

-- Admins Users
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname, avatar) VALUES (1,'Alvaro','Fernandez','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin1@gmail.com', '2003-07-12', 1, 'admin1', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname, avatar) VALUES (2,'Carlos','Prieto','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin2@gmail.com', '2003-07-12', 1, 'admin2', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');

-- Player Users
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (3,'Angel','Garcia','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','angelgares6424@gmail.com', '2003-07-12', 2, 'Angelgares', 'https://i.pinimg.com/originals/09/69/c2/0969c2f8a87af926fcf912330b67206f.jpg');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (4,'Jorge','Muñoz','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','jorgemr@pm.me', '2003-07-12', 2, 'Jorge_ADD', 'https://images.squarespace-cdn.com/content/v1/5e45d3a8e509f61738454469/81e0d83b-0fa6-48d0-801b-1f0c1515d786/logo+arus+cuadrado+web.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (5,'Javier','Rodriguez','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','java@pm.me', '2003-07-12', 2, 'javrodrei', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (6,'Isaac','Solis','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','princesita@pm.me', '2003-07-12', 2, 'isasolpad', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (7,'Andres','Garcia','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','andres7212@gmail.com', '2003-07-12', 2, 'Andresisco', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHRfYlqLUzoJjY4CQnv2yIOwS7WAFuBZrs9w&usqp=CAU');
INSERT INTO players(id,name,surname,password,email,birth_date,authority,nickname,avatar) VALUES (8,'Alejandro','Perez','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','alejandro@gmail.com', '2003-07-12', 2, 'alepersan', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');


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

INSERT INTO game_players(id,energy,player_id,color) VALUES (9,3,3,'BLUE'); --player 5
INSERT INTO game_players(id,energy,player_id,color) VALUES (10,3,6,'RED'); --player 6

INSERT INTO game_players(id,energy,player_id,color) VALUES (11,3,3,'BLUE'); --player 5
INSERT INTO game_players(id,energy,player_id,color) VALUES (12,3,6,'RED'); --player 6


-- Games 
INSERT INTO games(id, rounds, winner, ended, started) VALUES (1, 16, 3, '2021-09-01 11:13:24', '2021-09-01 10:35:10');
INSERT INTO games(id, rounds, winner, ended, started) VALUES (2, 18, 5, '2021-09-01 14:30:00', '2021-09-01 11:00:00');
INSERT INTO games(id, rounds, winner, ended, started) VALUES (3, 24, null, null, '2021-01-01 15:45:00');
INSERT INTO games(id, rounds, winner, ended, started) VALUES (4, 3, 6, '2021-01-01 10:15:00', '2021-01-01 10:00:00');
INSERT INTO games(id, rounds, winner, ended, started) VALUES (5, 7, 6, '2021-01-01 10:14:00', '2021-01-01 10:00:00');


-- Games_Game_Players
INSERT INTO games_game_players(game_id,game_players_id) VALUES (1,3);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (1,4);
--Player con id 3 y 4 juegan la partida 2
INSERT INTO games_game_players(game_id,game_players_id) VALUES (2,5);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (2,6);
--Player con id 5 y 6 juegan la partida 3
INSERT INTO games_game_players(game_id,game_players_id) VALUES (3,7);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (3,8);
--Player con id 3 y 6 juegan la partida 4
INSERT INTO games_game_players(game_id,game_players_id) VALUES (4,9);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (4,10);

INSERT INTO games_game_players(game_id,game_players_id) VALUES (5,11);
INSERT INTO games_game_players(game_id,game_players_id) VALUES (5,12);

--INSERT INTO cards (id,card_column,card_row,iniciative,color,exit) VALUES(1,null,null,1,'RED','EXIT_001');

-- Message
INSERT INTO messages(id, color, reaction) VALUES (1, null, 'HI');
INSERT INTO messages(id, color, reaction) VALUES (2, null, 'WHAT_A_PITY');
INSERT INTO messages(id, color, reaction) VALUES (3, null, 'SORRY');
INSERT INTO messages(id, color, reaction) VALUES (4, null, 'THANKS');
INSERT INTO messages(id, color, reaction) VALUES (5, null, 'NICE');
INSERT INTO messages(id, color, reaction) VALUES (6, null, 'JAJAJAJA');
INSERT INTO messages(id, color, reaction) VALUES (7, null, 'GG');
INSERT INTO messages(id, color, reaction) VALUES (8, null, 'GOOD_LUCK');

INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(1,null,null,2,'RED','EXIT_001','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(2,null,null,1,'RED','EXIT_010','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(3,null,null,3,'RED','EXIT_011','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(4,null,null,2,'RED','EXIT_100','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(5,null,null,4,'RED','EXIT_101','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(6,null,null,3,'RED','EXIT_110','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(7,null,null,0,'RED','EXIT_111','S');
INSERT INTO cards (id,card_column,card_row,iniciative,color,exit,orientation) VALUES(8,null,null,5,'RED','EXIT_111','S');

