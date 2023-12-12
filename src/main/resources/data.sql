--Authorities
INSERT INTO authorities(id, authority) VALUES (1,'ADMIN'),
                                              (2,'PLAYER');


INSERT INTO players(id, name, surname, password, email, birth_date, authority, nickname, avatar) VALUES -- Admins Users
                                                                                                        (1,'Alvaro','Fernandez','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin1@gmail.com', '2003-07-12', 1, 'admin1', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (2,'Carlos','Prieto','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin2@gmail.com', '2000-05-11', 1, 'admin2', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (13, 'Sofia', 'Lopez', '$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin3@gmail.com', '1998-09-10', 1, 'sofialopez', 'https://cdn-icons-png.flaticon.com/512/147/147138.png'),
                                                                                                        (14, 'David', 'Sanchez', '$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin4@gmail.com', '1988-12-05', 1, 'davidsan', 'https://cdn-icons-png.flaticon.com/512/147/147139.png'),
                                                                                                        -- Player Users
                                                                                                        (3,'Ángel','García','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','angelgares6424@gmail.com', '2003-07-12', 2, 'Angelgares', 'https://i.pinimg.com/originals/09/69/c2/0969c2f8a87af926fcf912330b67206f.jpg'),
                                                                                                        (4,'Jorge','Muñoz','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','jorgemr@pm.me', '1999-09-11', 2, 'Jorge_ADD', 'https://images.squarespace-cdn.com/content/v1/5e45d3a8e509f61738454469/81e0d83b-0fa6-48d0-801b-1f0c1515d786/logo+arus+cuadrado+web.png'),
                                                                                                        (5,'Javier','Rodriguez','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','java@pm.me', '2006-11-30', 2, 'javrodrei', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (6,'Isaac','Solis','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','princesita@pm.me', '2002-07-12', 2, 'isasolpad', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (7,'Andres','Garcia','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','andres7212@gmail.com', '2000-04-30', 2, 'Andresisco', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHRfYlqLUzoJjY4CQnv2yIOwS7WAFuBZrs9w&usqp=CAU'),
                                                                                                        (8,'Alejandro','Perez','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','alejandro@gmail.com', '2001-08-11', 2, 'alepersan', 'https://pbs.twimg.com/media/FTQBiYDXwAADvbp.jpg'),                                                                                          
                                                                                                        (9, 'Maria', 'Herrero', '$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa', 'maria.herrero@gmail.com', '2005-05-02', 2, 'Mariaa_hm', 'https://pbs.twimg.com/profile_images/1729861116203810816/bcN6IZ0H_400x400.jpg'),
                                                                                                        (10, 'Juan', 'Fernandez', '$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa', 'juan.fernandez@example.com', '1985-05-20', 2, 'juanfer', 'https://cdn-icons-png.flaticon.com/512/147/147142.png'),
                                                                                                        (11, 'Lucia', 'Martinez', '$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa', 'lucia.martinez@example.com', '1995-03-30', 2, 'luciamart', 'https://cdn-icons-png.flaticon.com/512/147/147146.png'),
                                                                                                        (12, 'Carlos', 'Garcia', '$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa', 'carlos.garcia@example.com', '1992-07-25', 2, 'carlosgar', 'https://cdn-icons-png.flaticon.com/512/147/147143.png');


-- Achievements
INSERT INTO achievements(id, name, description, threshold, badge_image, category) VALUES (1,'Basic expirence','Play 10 games',10.0,'https://cdn-icons-png.flaticon.com/512/5243/5243423.png','GAMES_PLAYED'),
                                                                                         (2,'Explorer','Play 25 games',25.0,'https://cdn-icons-png.flaticon.com/512/603/603855.png','GAMES_PLAYED'),
                                                                                         (3,'Expert','Win 20 games',20.0, null, 'VICTORIES');

-- Player Achievements
INSERT INTO player_achievements(id, achieve_at, achievement_id) VALUES (1,'2021-09-01', 1);


--Player PlayerAchievements
INSERT INTO players_player_achievement(player_achievement_id, player_id) VALUES (1, 3);

-- GamePlayers

INSERT INTO game_players(id, energy, player_id, color) VALUES (1, 3, 3,'RED'), 
                                                              (2, 3, 4,'BLUE'), 

                                                              (3, 3, 5,'BLUE'),
                                                              (4, 3, 6,'RED'), 

                                                              (5, 3, 3,'BLUE'), 
                                                              (6, 3, 8,'RED'), 

                                                              (7, 3, 9,'BLUE'), 
                                                              (8, 3, 10,'RED'), 

                                                              (9, 3, 11,'BLUE'), 
                                                              (10, 3, 6,'RED'); 


-- Games 
INSERT INTO games(id, round, winner, ended, started, game_player_turn_id, effect) VALUES (1, 16, 3, '2021-09-01 11:13:24', '2021-09-01 10:35:10', 3, 'NONE'),
                                                                                  (2, 18, 5, '2021-09-01 14:30:00', '2021-09-01 11:00:00', 5, 'NONE'),
                                                                                  (3, 24, null, null, '2021-01-01 15:45:00', 3, 'NONE'),
                                                                                  (4, 3, 10, '2021-01-01 10:15:00', '2021-01-01 10:00:00', 10, 'NONE'),
                                                                                  (5, 7, 6, '2021-01-01 10:14:00', '2021-01-01 10:00:00', 6, 'NONE');


-- Games_Game_Players
INSERT INTO games_game_players(game_id, game_players_id) VALUES --Player con id 3 y 4 juegan la partida 1
                                                                (1, 1),
                                                                (1, 2),
                                                                --Player con id 5 y 6 juegan la partida 2
                                                                (2, 3),
                                                                (2, 4),
                                                                --Player con id 3 y 8 juegan la partida 3
                                                                (3, 5),
                                                                (3, 6),
                                                                --Player con id 9 y 10 juegan la partida 4
                                                                (4, 7),
                                                                (4, 8),
                                                                --Player con id 11 y 12 juegan la partida 5
                                                                (5, 9),
                                                                (5, 10);

-- Message
INSERT INTO messages(id, color, reaction) VALUES (1, null, 'HI'),
                                                 (2, null, 'WHAT_A_PITY'),
                                                 (3, null, 'SORRY'),
                                                 (4, null, 'THANKS'),
                                                 (5, null, 'NICE'),
                                                 (6, null, 'JAJAJAJA'),
                                                 (7, null, 'GG'),
                                                 (8, null, 'GOOD_LUCK');

-- Friendship
INSERT INTO friendships(id, receiver, sender, friend_state) VALUES (1, 4, 3, 'ACCEPTED'),
                                                                  (2, 5, 3, 'PENDING'),
                                                                  (3, 6, 3, 'ACCEPTED'),
                                                                  (4, 7, 3, 'PENDING'),
                                                                  (5, 8, 3, 'ACCEPTED'),
                                                                  (6, 3, 9, 'ACCEPTED'),
                                                                  (7, 3, 10, 'PENDING'),
                                                                  (8, 11, 3, 'REJECTED'),
                                                                  (9, 4, 5, 'ACCEPTED'),
                                                                  (10, 5, 6, 'ACCEPTED'),
                                                                  (11, 5, 8, 'REJECTED'),
                                                                  (12, 6, 8, 'REJECTED'),
                                                                  (13, 8, 9, 'ACCEPTED'),
                                                                  (14, 9, 10, 'PENDING'),
                                                                  (15, 12, 3, 'ACCEPTED'),
                                                                  (16, 8, 12, 'PENDING');

                                                                  

-- Cards
INSERT INTO cards (id, initiative, color, exit, orientation, is_template) VALUES (1, 2, 'RED', 'EXIT_001_A', 'S', 1),
                                                                                                        (2, 2, 'RED', 'EXIT_001_B', 'S', 1),
                                                                                                        (3, 2, 'RED', 'EXIT_001_C', 'S', 1),
                                                                                                        (4, 2, 'RED', 'EXIT_001_D', 'S', 1),

                                                                                                        (5, 1, 'RED', 'EXIT_010_A', 'S', 1),
                                                                                                        (6, 1, 'RED', 'EXIT_010_B', 'S', 1),
                                                                                                        (7, 1, 'RED', 'EXIT_010_C', 'S', 1),
                                                                                                        (8, 1, 'RED', 'EXIT_010_D', 'S', 1),
                                                                                                        (9, 1, 'RED', 'EXIT_010_E', 'S', 1),
                                                                                                        (10, 1, 'RED', 'EXIT_010_F', 'S', 1),
                                                                                                        (11, 1, 'RED', 'EXIT_010_G', 'S', 1),
                                                                                                        (12, 1, 'RED', 'EXIT_010_H', 'S', 1),

                                                                                                        (13, 3, 'RED', 'EXIT_011_A', 'S', 1),
                                                                                                        (14, 3, 'RED', 'EXIT_011_B', 'S', 1),

                                                                                                        (15, 2, 'RED', 'EXIT_100_A', 'S', 1),
                                                                                                        (16, 2, 'RED', 'EXIT_100_B', 'S', 1),
                                                                                                        (17, 2, 'RED', 'EXIT_100_C', 'S', 1),
                                                                                                        (18, 2, 'RED', 'EXIT_100_D', 'S', 1),

                                                                                                        (19, 4, 'RED', 'EXIT_101_A', 'S', 1),
                                                                                                        (20, 4, 'RED', 'EXIT_101_B', 'S', 1),

                                                                                                        (21, 3, 'RED', 'EXIT_110_A', 'S', 1),
                                                                                                        (22, 3, 'RED', 'EXIT_110_B', 'S', 1),

                                                                                                        (23, 0, 'RED', 'EXIT_111_A', 'S', 1),

                                                                                                        (24, 5, 'RED', 'EXIT_111_B', 'S', 1),

                                                                                                        (25, 0, 'RED', 'START', 'S', 1),
                                                                                            
                                                                                                        (26, 2, 'BLUE', 'EXIT_001_A', 'S', 1),
                                                                                                        (27, 2, 'BLUE', 'EXIT_001_B', 'S', 1),
                                                                                                        (28, 2, 'BLUE', 'EXIT_001_C', 'S', 1),
                                                                                                        (29, 2, 'BLUE', 'EXIT_001_D', 'S', 1),

                                                                                                        (30, 1, 'BLUE', 'EXIT_010_A', 'S', 1),
                                                                                                        (31, 1, 'BLUE', 'EXIT_010_B', 'S', 1),
                                                                                                        (32, 1, 'BLUE', 'EXIT_010_C', 'S', 1),
                                                                                                        (33, 1, 'BLUE', 'EXIT_010_D', 'S', 1),
                                                                                                        (34, 1, 'BLUE', 'EXIT_010_E', 'S', 1),
                                                                                                        (35, 1, 'BLUE', 'EXIT_010_F', 'S', 1),
                                                                                                        (36, 1, 'BLUE', 'EXIT_010_G', 'S', 1),
                                                                                                        (37, 1, 'BLUE', 'EXIT_010_H', 'S', 1),

                                                                                                        (38, 3, 'BLUE', 'EXIT_011_A', 'S', 1),
                                                                                                        (39, 3, 'BLUE', 'EXIT_011_B', 'S', 1),

                                                                                                        (40, 2, 'BLUE', 'EXIT_100_A', 'S', 1),
                                                                                                        (41, 2, 'BLUE', 'EXIT_100_B', 'S', 1),
                                                                                                        (42, 2, 'BLUE', 'EXIT_100_C', 'S', 1),
                                                                                                        (43, 2, 'BLUE', 'EXIT_100_D', 'S', 1),

                                                                                                        (44, 4, 'BLUE', 'EXIT_101_A', 'S', 1),
                                                                                                        (45, 4, 'BLUE', 'EXIT_101_B', 'S', 1),

                                                                                                        (46, 3, 'BLUE', 'EXIT_110_A', 'S', 1),
                                                                                                        (47, 3, 'BLUE', 'EXIT_110_B', 'S', 1),

                                                                                                        (48, 0, 'BLUE', 'EXIT_111_A', 'S', 1),

                                                                                                        (49, 5, 'BLUE', 'EXIT_111_B', 'S', 1),

                                                                                                        (50, 0, 'BLUE', 'START', 'S', 1),

                                                                                                        (51, 2, 'GREEN', 'EXIT_001_A', 'S', 1),
                                                                                                        (52, 2, 'GREEN', 'EXIT_001_B', 'S', 1),
                                                                                                        (53, 2, 'GREEN', 'EXIT_001_C', 'S', 1),
                                                                                                        (54, 2, 'GREEN', 'EXIT_001_D', 'S', 1),

                                                                                                        (55, 1, 'GREEN', 'EXIT_010_A', 'S', 1),
                                                                                                        (56, 1, 'GREEN', 'EXIT_010_B', 'S', 1),
                                                                                                        (57, 1, 'GREEN', 'EXIT_010_C', 'S', 1),
                                                                                                        (58, 1, 'GREEN', 'EXIT_010_D', 'S', 1),
                                                                                                        (59, 1, 'GREEN', 'EXIT_010_E', 'S', 1),
                                                                                                        (60, 1, 'GREEN', 'EXIT_010_F', 'S', 1),
                                                                                                        (61, 1, 'GREEN', 'EXIT_010_G', 'S', 1),
                                                                                                        (62, 1, 'GREEN', 'EXIT_010_H', 'S', 1),

                                                                                                        (63, 3, 'GREEN', 'EXIT_011_A', 'S', 1),
                                                                                                        (64, 3, 'GREEN', 'EXIT_011_B', 'S', 1),

                                                                                                        (65, 2, 'GREEN', 'EXIT_100_A', 'S', 1),
                                                                                                        (66, 2, 'GREEN', 'EXIT_100_B', 'S', 1),
                                                                                                        (67, 2, 'GREEN', 'EXIT_100_C', 'S', 1),
                                                                                                        (68, 2, 'GREEN', 'EXIT_100_D', 'S', 1),

                                                                                                        (69, 4, 'GREEN', 'EXIT_101_A', 'S', 1),
                                                                                                        (70, 4, 'GREEN', 'EXIT_101_B', 'S', 1),

                                                                                                        (71, 3, 'GREEN', 'EXIT_110_A', 'S', 1),
                                                                                                        (72, 3, 'GREEN', 'EXIT_110_B', 'S', 1),

                                                                                                        (73, 0, 'GREEN', 'EXIT_111_A', 'S', 1),

                                                                                                        (74, 5, 'GREEN', 'EXIT_111_B', 'S', 1),

                                                                                                        (75, 0, 'GREEN', 'START', 'S', 1),

                                                                                                        (76, 2, 'YELLOW', 'EXIT_001_A', 'S', 1),
                                                                                                        (77, 2, 'YELLOW', 'EXIT_001_B', 'S', 1),
                                                                                                        (78, 2, 'YELLOW', 'EXIT_001_C', 'S', 1),
                                                                                                        (79, 2, 'YELLOW', 'EXIT_001_D', 'S', 1),

                                                                                                        (80, 1, 'YELLOW', 'EXIT_010_A', 'S', 1),
                                                                                                        (81, 1, 'YELLOW', 'EXIT_010_B', 'S', 1),
                                                                                                        (82, 1, 'YELLOW', 'EXIT_010_C', 'S', 1),
                                                                                                        (83, 1, 'YELLOW', 'EXIT_010_D', 'S', 1),
                                                                                                        (84, 1, 'YELLOW', 'EXIT_010_E', 'S', 1),
                                                                                                        (85, 1, 'YELLOW', 'EXIT_010_F', 'S', 1),
                                                                                                        (86, 1, 'YELLOW', 'EXIT_010_G', 'S', 1),
                                                                                                        (87, 1, 'YELLOW', 'EXIT_010_H', 'S', 1),

                                                                                                        (88, 3, 'YELLOW', 'EXIT_011_A', 'S', 1),
                                                                                                        (89, 3, 'YELLOW', 'EXIT_011_B', 'S', 1),

                                                                                                        (90, 2, 'YELLOW', 'EXIT_100_A', 'S', 1),
                                                                                                        (91, 2, 'YELLOW', 'EXIT_100_B', 'S', 1),
                                                                                                        (92, 2, 'YELLOW', 'EXIT_100_C', 'S', 1),
                                                                                                        (93, 2, 'YELLOW', 'EXIT_100_D', 'S', 1),

                                                                                                        (94, 4, 'YELLOW', 'EXIT_101_A', 'S', 1),
                                                                                                        (95, 4, 'YELLOW', 'EXIT_101_B', 'S', 1),

                                                                                                        (96, 3, 'YELLOW', 'EXIT_110_A', 'S', 1),
                                                                                                        (97, 3, 'YELLOW', 'EXIT_110_B', 'S', 1),

                                                                                                        (98, 0, 'YELLOW', 'EXIT_111_A', 'S', 1),

                                                                                                        (99, 5, 'YELLOW', 'EXIT_111_B', 'S', 1),

                                                                                                        (100, 0, 'YELLOW', 'START', 'S', 1),

                                                                                                        (101, 2, 'WHITE', 'EXIT_001_A', 'S', 1),
                                                                                                        (102, 2, 'WHITE', 'EXIT_001_B', 'S', 1),
                                                                                                        (103, 2, 'WHITE', 'EXIT_001_C', 'S', 1),
                                                                                                        (104, 2, 'WHITE', 'EXIT_001_D', 'S', 1),

                                                                                                        (105, 1, 'WHITE', 'EXIT_010_A', 'S', 1),
                                                                                                        (106, 1, 'WHITE', 'EXIT_010_B', 'S', 1),
                                                                                                        (107, 1, 'WHITE', 'EXIT_010_C', 'S', 1),
                                                                                                        (108, 1, 'WHITE', 'EXIT_010_D', 'S', 1),
                                                                                                        (109, 1, 'WHITE', 'EXIT_010_E', 'S', 1),
                                                                                                        (110, 1, 'WHITE', 'EXIT_010_F', 'S', 1),
                                                                                                        (111, 1, 'WHITE', 'EXIT_010_G', 'S', 1),
                                                                                                        (112, 1, 'WHITE', 'EXIT_010_H', 'S', 1),

                                                                                                        (113, 3, 'WHITE', 'EXIT_011_A', 'S', 1),
                                                                                                        (114, 3, 'WHITE', 'EXIT_011_B', 'S', 1),

                                                                                                        (115, 2, 'WHITE', 'EXIT_100_A', 'S', 1),
                                                                                                        (116, 2, 'WHITE', 'EXIT_100_B', 'S', 1),
                                                                                                        (117, 2, 'WHITE', 'EXIT_100_C', 'S', 1),
                                                                                                        (118, 2, 'WHITE', 'EXIT_100_D', 'S', 1),

                                                                                                        (119, 4, 'WHITE', 'EXIT_101_A', 'S', 1),
                                                                                                        (120, 4, 'WHITE', 'EXIT_101_B', 'S', 1),

                                                                                                        (121, 3, 'WHITE', 'EXIT_110_A', 'S', 1),
                                                                                                        (122, 3, 'WHITE', 'EXIT_110_B', 'S', 1),

                                                                                                        (123, 0, 'WHITE', 'EXIT_111_A', 'S', 1),

                                                                                                        (124, 5, 'WHITE', 'EXIT_111_B', 'S', 1),

                                                                                                        (125, 0, 'WHITE', 'START', 'S', 1),

                                                                                                        (126, 2, 'VIOLET', 'EXIT_001_A', 'S', 1),
                                                                                                        (127, 2, 'VIOLET', 'EXIT_001_B', 'S', 1),
                                                                                                        (128, 2, 'VIOLET', 'EXIT_001_C', 'S', 1),
                                                                                                        (129, 2, 'VIOLET', 'EXIT_001_D', 'S', 1),

                                                                                                        (130, 1, 'VIOLET', 'EXIT_010_A', 'S', 1),
                                                                                                        (131, 1, 'VIOLET', 'EXIT_010_B', 'S', 1),
                                                                                                        (132, 1, 'VIOLET', 'EXIT_010_C', 'S', 1),
                                                                                                        (133, 1, 'VIOLET', 'EXIT_010_D', 'S', 1),
                                                                                                        (134, 1, 'VIOLET', 'EXIT_010_E', 'S', 1),
                                                                                                        (135, 1, 'VIOLET', 'EXIT_010_F', 'S', 1),
                                                                                                        (136, 1, 'VIOLET', 'EXIT_010_G', 'S', 1),
                                                                                                        (137, 1, 'VIOLET', 'EXIT_010_H', 'S', 1),

                                                                                                        (138, 3, 'VIOLET', 'EXIT_011_A', 'S', 1),
                                                                                                        (139, 3, 'VIOLET', 'EXIT_011_B', 'S', 1),

                                                                                                        (140, 2, 'VIOLET', 'EXIT_100_A', 'S', 1),
                                                                                                        (141, 2, 'VIOLET', 'EXIT_100_B', 'S', 1),
                                                                                                        (142, 2, 'VIOLET', 'EXIT_100_C', 'S', 1),
                                                                                                        (143, 2, 'VIOLET', 'EXIT_100_D', 'S', 1),

                                                                                                        (144, 4, 'VIOLET', 'EXIT_101_A', 'S', 1),
                                                                                                        (145, 4, 'VIOLET', 'EXIT_101_B', 'S', 1),

                                                                                                        (146, 3, 'VIOLET', 'EXIT_110_A', 'S', 1),
                                                                                                        (147, 3, 'VIOLET', 'EXIT_110_B', 'S', 1),

                                                                                                        (148, 0, 'VIOLET', 'EXIT_111_A', 'S', 1),

                                                                                                        (149, 5, 'VIOLET', 'EXIT_111_B', 'S', 1),

                                                                                                        (150, 0, 'VIOLET', 'START', 'S', 1),

                                                                                                        (151, 2, 'MAGENTA', 'EXIT_001_A', 'S', 1),
                                                                                                        (152, 2, 'MAGENTA', 'EXIT_001_B', 'S', 1),
                                                                                                        (153, 2, 'MAGENTA', 'EXIT_001_C', 'S', 1),
                                                                                                        (154, 2, 'MAGENTA', 'EXIT_001_D', 'S', 1),

                                                                                                        (155, 1, 'MAGENTA', 'EXIT_010_A', 'S', 1),
                                                                                                        (156, 1, 'MAGENTA', 'EXIT_010_B', 'S', 1),
                                                                                                        (157, 1, 'MAGENTA', 'EXIT_010_C', 'S', 1),
                                                                                                        (158, 1, 'MAGENTA', 'EXIT_010_D', 'S', 1),
                                                                                                        (159, 1, 'MAGENTA', 'EXIT_010_E', 'S', 1),
                                                                                                        (160, 1, 'MAGENTA', 'EXIT_010_F', 'S', 1),
                                                                                                        (161, 1, 'MAGENTA', 'EXIT_010_G', 'S', 1),
                                                                                                        (162, 1, 'MAGENTA', 'EXIT_010_H', 'S', 1),

                                                                                                        (163, 3, 'MAGENTA', 'EXIT_011_A', 'S', 1),
                                                                                                        (164, 3, 'MAGENTA', 'EXIT_011_B', 'S', 1),

                                                                                                        (165, 2, 'MAGENTA', 'EXIT_100_A', 'S', 1),
                                                                                                        (166, 2, 'MAGENTA', 'EXIT_100_B', 'S', 1),
                                                                                                        (167, 2, 'MAGENTA', 'EXIT_100_C', 'S', 1),
                                                                                                        (168, 2, 'MAGENTA', 'EXIT_100_D', 'S', 1),

                                                                                                        (169, 4, 'MAGENTA', 'EXIT_101_A', 'S', 1),
                                                                                                        (170, 4, 'MAGENTA', 'EXIT_101_B', 'S', 1),

                                                                                                        (171, 3, 'MAGENTA', 'EXIT_110_A', 'S', 1),
                                                                                                        (172, 3, 'MAGENTA', 'EXIT_110_B', 'S', 1),

                                                                                                        (173, 0, 'MAGENTA', 'EXIT_111_A', 'S', 1),

                                                                                                        (174, 5, 'MAGENTA', 'EXIT_111_B', 'S', 1),

                                                                                                        (175, 0, 'MAGENTA', 'START', 'S', 1),

                                                                                                        (176, 2, 'ORANGE', 'EXIT_001_A', 'S', 1),
                                                                                                        (177, 2, 'ORANGE', 'EXIT_001_B', 'S', 1),
                                                                                                        (178, 2, 'ORANGE', 'EXIT_001_C', 'S', 1),
                                                                                                        (179, 2, 'ORANGE', 'EXIT_001_D', 'S', 1),

                                                                                                        (180, 1, 'ORANGE', 'EXIT_010_A', 'S', 1),
                                                                                                        (181, 1, 'ORANGE', 'EXIT_010_B', 'S', 1),
                                                                                                        (182, 1, 'ORANGE', 'EXIT_010_C', 'S', 1),
                                                                                                        (183, 1, 'ORANGE', 'EXIT_010_D', 'S', 1),
                                                                                                        (184, 1, 'ORANGE', 'EXIT_010_E', 'S', 1),
                                                                                                        (185, 1, 'ORANGE', 'EXIT_010_F', 'S', 1),
                                                                                                        (186, 1, 'ORANGE', 'EXIT_010_G', 'S', 1),
                                                                                                        (187, 1, 'ORANGE', 'EXIT_010_H', 'S', 1),

                                                                                                        (188, 3, 'ORANGE', 'EXIT_011_A', 'S', 1),
                                                                                                        (189, 3, 'ORANGE', 'EXIT_011_B', 'S', 1),

                                                                                                        (190, 2, 'ORANGE', 'EXIT_100_A', 'S', 1),
                                                                                                        (191, 2, 'ORANGE', 'EXIT_100_B', 'S', 1),
                                                                                                        (192, 2, 'ORANGE', 'EXIT_100_C', 'S', 1),
                                                                                                        (193, 2, 'ORANGE', 'EXIT_100_D', 'S', 1),

                                                                                                        (194, 4, 'ORANGE', 'EXIT_101_A', 'S', 1),
                                                                                                        (195, 4, 'ORANGE', 'EXIT_101_B', 'S', 1),

                                                                                                        (196, 3, 'ORANGE', 'EXIT_110_A', 'S', 1),

                                                                                                        (197, 3, 'ORANGE', 'EXIT_110_B', 'S', 1),
                                                                                                        
                                                                                                        (198, 0, 'ORANGE', 'EXIT_111_A', 'S', 1),
                                                                                        
                                                                                                        (199, 5, 'ORANGE', 'EXIT_111_B', 'S', 1),

                                                                                                        (200, 0, 'ORANGE', 'START', 'S', 1);
                                                                                                                                                                
