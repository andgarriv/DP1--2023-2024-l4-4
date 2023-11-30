--Authorities
INSERT INTO authorities(id, authority) VALUES (1,'ADMIN'),
                                              (2,'PLAYER');


INSERT INTO players(id, name, surname, password, email, birth_date, authority, nickname, avatar) VALUES -- Admins Users
                                                                                                        (1,'Alvaro','Fernandez','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin1@gmail.com', '2003-07-12', 1, 'admin1', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (2,'Carlos','Prieto','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin2@gmail.com', '2000-05-11', 1, 'admin2', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (13, 'Sofia', 'Lopez', '$2a$12$anotherPasswordHashHere', 'sofia.lopez@example.com', '1998-09-10', 1, 'sofialopez', 'https://cdn-icons-png.flaticon.com/512/147/147138.png'),
                                                                                                        (14, 'David', 'Sanchez', '$2a$12$uniqueHashValueHere', 'david.sanchez@example.com', '1988-12-05', 1, 'davidsan', 'https://cdn-icons-png.flaticon.com/512/147/147139.png'),
                                                                                                        -- Player Users
                                                                                                        (3,'Angel','Garcia','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','angelgares6424@gmail.com', '2006-10-23', 2, 'Angelgares', 'https://i.pinimg.com/originals/09/69/c2/0969c2f8a87af926fcf912330b67206f.jpg'),
                                                                                                        (4,'Jorge','Muñoz','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','jorgemr@pm.me', '1999-09-11', 2, 'Jorge_ADD', 'https://images.squarespace-cdn.com/content/v1/5e45d3a8e509f61738454469/81e0d83b-0fa6-48d0-801b-1f0c1515d786/logo+arus+cuadrado+web.png'),
                                                                                                        (5,'Javier','Rodriguez','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','java@pm.me', '2006-11-30', 2, 'javrodrei', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (6,'Isaac','Solis','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','princesita@pm.me', '2002-07-12', 2, 'isasolpad', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (7,'Andres','Garcia','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','andres7212@gmail.com', '2003-07-12', 2, 'Andresisco', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHRfYlqLUzoJjY4CQnv2yIOwS7WAFuBZrs9w&usqp=CAU'),
                                                                                                        (8,'Alejandro','Perez','$2b$12$EB6dhGe96Fx1dt4CMxkF/ONMXHMNGLwSD9vhOWMwhLqKNRU2IvoCa','alejandro@gmail.com', '2001-08-11', 2, 'alepersan', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),                                                                                          
                                                                                                        (9, 'Maria', 'Herrero', '$2a$12$uniqueHashHere1234', 'maria.herrero@gmail.com', '2005-05-02', 2, 'Mariaa_hm', 'https://pbs.twimg.com/profile_images/1729861116203810816/bcN6IZ0H_400x400.jpg'),
                                                                                                        (10, 'Juan', 'Fernandez', '$2a$12$anotherUniqueHashHere', 'juan.fernandez@example.com', '1985-05-20', 2, 'juanfer', 'https://cdn-icons-png.flaticon.com/512/147/147142.png'),
                                                                                                        (11, 'Lucia', 'Martinez', '$2a$12$differentUniqueHash', 'lucia.martinez@example.com', '1995-03-30', 2, 'luciamart', 'https://cdn-icons-png.flaticon.com/512/147/147146.png'),
                                                                                                        (12, 'Carlos', 'Garcia', '$2a$12$uniquePasswordHashHere', 'carlos.garcia@example.com', '1992-07-25', 2, 'carlosgar', 'https://cdn-icons-png.flaticon.com/512/147/147143.png');


-- Achievements
INSERT INTO achievements(id, name, description, threshold, badge_image, category) VALUES (1,'Basic expirence','Play 10 games',10.0,'https://cdn-icons-png.flaticon.com/512/5243/5243423.png','GAMES_PLAYED'),
                                                                                         (2,'Explorer','Play 25 games',25.0,'https://cdn-icons-png.flaticon.com/512/603/603855.png','GAMES_PLAYED');
INSERT INTO achievements(id, name, description, threshold, category) VALUES (3,'Expert','Win 20 games',20.0,'VICTORIES');

-- Player Achievements
INSERT INTO player_achievements(id, achieve_at, achievement_id) VALUES (1,'2021-09-01', 1);
INSERT INTO player_achievements(id, achievement_id) VALUES (2,2);
INSERT INTO player_achievements(id, achievement_id) VALUES (3,3);


--Player PlayerAchievements
INSERT INTO players_player_achievement(player_achievement_id, player_id) VALUES (1,6),
                                                                                (2,6),
                                                                                (3,5);

-- GamePlayers

INSERT INTO game_players(id, energy, player_id, color) VALUES  (3,3,3,'RED'), --player 3
                                                               (4,3,4,'BLUE'), --player 4

                                                               (5,3,5,'BLUE'),--player 5
                                                               (6,3,6,'RED'), --player 6

                                                               (7,3,5,'BLUE'), --player 5
                                                               (8,3,6,'RED'); --player 6

                                                               (9,3,3,'BLUE'), --player 5
                                                               (10,3,6,'RED'), --player 6

                                                               (11,3,3,'BLUE'), --player 5
                                                               (12,3,6,'RED'); --player 6


-- Games 
INSERT INTO games(id, rounds, winner, ended, started) VALUES (1, 16, 3, '2021-09-01 11:13:24', '2021-09-01 10:35:10');
                                                             (2, 18, 5, '2021-09-01 14:30:00', '2021-09-01 11:00:00');
                                                             (3, 24, null, null, '2021-01-01 15:45:00');
                                                             (4, 3, 6, '2021-01-01 10:15:00', '2021-01-01 10:00:00');
                                                             (5, 7, 6, '2021-01-01 10:14:00', '2021-01-01 10:00:00');


-- Games_Game_Players
INSERT INTO games_game_players(game_id, game_players_id) VALUES  (1,3),
                                                                 (1,4),
                                                                --Player con id 3 y 4 juegan la partida 2
                                                                 (2,5),
                                                                 (2,6),
                                                                --Player con id 5 y 6 juegan la partida 3
                                                                 (3,7),
                                                                 (3,8),
                                                                --Player con id 3 y 6 juegan la partida 4
                                                                 (4,9),
                                                                 (4,10),
                                                                --Player con id 
                                                                 (5,11),
                                                                 (5,12);

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
INSERT INTO friendships(id, sender, receiver,friend_state) VALUES (1, 3, 4, 'ACCEPTED'),
                                                                  (2, 3, 5, 'ACCEPTED'),
                                                                  (3, 3, 6, 'ACCEPTED'),
                                                                  (4, 3, 7, 'PENDING'),
                                                                  (5, 3, 8, 'ACCEPTED'),
                                                                  (6, 3, 9, 'ACCEPTED'),
                                                                  (7, 3, 10, 'ACCEPTED'),
                                                                  (8, 3, 11, 'ACCEPTED'),
                                                                  (9, 3, 12, 'ACCEPTED'),
                                                                  (10, 9, 8, 'ACCEPTED'),
                                                                  (11, 5, 6, 'REJECTED'),
                                                                  (12, 5, 8, 'REJECTED'),
                                                                  (13, 6, 8, 'ACCEPTED'),
                                                                  (14, 8, 10, 'PENDING');

                                                                  

-- Cards
INSERT INTO cards (id, card_column, card_row, iniciative, color, exit, orientation, is_template) VALUES (1, null, null, 2, 'RED', 'EXIT_001_A', 'S', 1),
                                                                                                        (2, null, null, 2, 'RED', 'EXIT_001_B', 'S', 1),
                                                                                                        (3, null, null, 2, 'RED', 'EXIT_001_C', 'S', 1),
                                                                                                        (4, null, null, 2, 'RED', 'EXIT_001_D', 'S', 1),

                                                                                                        (5, null, null, 1, 'RED', 'EXIT_010_A', 'S', 1),
                                                                                                        (6, null, null, 1, 'RED', 'EXIT_010_B', 'S', 1),
                                                                                                        (7, null, null, 1, 'RED', 'EXIT_010_C', 'S', 1),
                                                                                                        (8, null, null, 1, 'RED', 'EXIT_010_D', 'S', 1),
                                                                                                        (9, null, null, 1, 'RED', 'EXIT_010_E', 'S', 1),
                                                                                                        (10, null, null, 1, 'RED', 'EXIT_010_F', 'S', 1),
                                                                                                        (11, null, null, 1, 'RED', 'EXIT_010_G', 'S', 1),
                                                                                                        (12, null, null, 1, 'RED', 'EXIT_010_H', 'S', 1),

                                                                                                        (13, null, null, 3, 'RED', 'EXIT_011_A', 'S', 1),
                                                                                                        (14, null, null, 3, 'RED', 'EXIT_011_B', 'S', 1),

                                                                                                        (15, null, null, 2, 'RED', 'EXIT_100_A', 'S', 1),
                                                                                                        (16, null, null, 2, 'RED', 'EXIT_100_B', 'S', 1),
                                                                                                        (17, null, null, 2, 'RED', 'EXIT_100_C', 'S', 1),
                                                                                                        (18, null, null, 2, 'RED', 'EXIT_100_D', 'S', 1),

                                                                                                        (19, null, null, 4, 'RED', 'EXIT_101_A', 'S', 1),
                                                                                                        (20, null, null, 4, 'RED', 'EXIT_101_B', 'S', 1),

                                                                                                        (21, null, null, 3, 'RED', 'EXIT_110_A', 'S', 1),
                                                                                                        (22, null, null, 3, 'RED', 'EXIT_110_B', 'S', 1),

                                                                                                        (23, null, null, 0, 'RED', 'EXIT_111_A', 'S', 1),

                                                                                                        (24, null, null, 5, 'RED', 'EXIT_111_B', 'S', 1),

                                                                                                        (25, null, null, 2, 'BLUE', 'EXIT_001_A', 'S', 1),
                                                                                                        (26, null, null, 2, 'BLUE', 'EXIT_001_B', 'S', 1),
                                                                                                        (27, null, null, 2, 'BLUE', 'EXIT_001_C', 'S', 1),
                                                                                                        (28, null, null, 2, 'BLUE', 'EXIT_001_D', 'S', 1),

                                                                                                        (29, null, null, 1, 'BLUE', 'EXIT_010_A', 'S', 1),
                                                                                                        (30, null, null, 1, 'BLUE', 'EXIT_010_B', 'S', 1),
                                                                                                        (31, null, null, 1, 'BLUE', 'EXIT_010_C', 'S', 1),
                                                                                                        (32, null, null, 1, 'BLUE', 'EXIT_010_D', 'S', 1),
                                                                                                        (33, null, null, 1, 'BLUE', 'EXIT_010_E', 'S', 1),
                                                                                                        (34, null, null, 1, 'BLUE', 'EXIT_010_F', 'S', 1),
                                                                                                        (35, null, null, 1, 'BLUE', 'EXIT_010_G', 'S', 1),
                                                                                                        (36, null, null, 1, 'BLUE', 'EXIT_010_H', 'S', 1),

                                                                                                        (37, null, null, 3, 'BLUE', 'EXIT_011_A', 'S', 1),
                                                                                                        (38, null, null, 3, 'BLUE', 'EXIT_011_B', 'S', 1),

                                                                                                        (39, null, null, 2, 'BLUE', 'EXIT_100_A', 'S', 1),
                                                                                                        (40, null, null, 2, 'BLUE', 'EXIT_100_B', 'S', 1),
                                                                                                        (41, null, null, 2, 'BLUE', 'EXIT_100_C', 'S', 1),
                                                                                                        (42, null, null, 2, 'BLUE', 'EXIT_100_D', 'S', 1),

                                                                                                        (43, null, null, 4, 'BLUE', 'EXIT_101_A', 'S', 1),
                                                                                                        (44, null, null, 4, 'BLUE', 'EXIT_101_B', 'S', 1),

                                                                                                        (45, null, null, 3, 'BLUE', 'EXIT_110_A', 'S', 1),
                                                                                                        (46, null, null, 3, 'BLUE', 'EXIT_110_B', 'S', 1),

                                                                                                        (47, null, null, 0, 'BLUE', 'EXIT_111_A', 'S', 1),

                                                                                                        (48, null, null, 5, 'BLUE', 'EXIT_111_B', 'S', 1),

                                                                                                        (49, null, null, 2, 'GREEN', 'EXIT_001_A', 'S', 1),
                                                                                                        (50, null, null, 2, 'GREEN', 'EXIT_001_B', 'S', 1),
                                                                                                        (51, null, null, 2, 'GREEN', 'EXIT_001_C', 'S', 1),
                                                                                                        (52, null, null, 2, 'GREEN', 'EXIT_001_D', 'S', 1),

                                                                                                        (53, null, null, 1, 'GREEN', 'EXIT_010_A', 'S', 1),
                                                                                                        (54, null, null, 1, 'GREEN', 'EXIT_010_B', 'S', 1),
                                                                                                        (55, null, null, 1, 'GREEN', 'EXIT_010_C', 'S', 1),
                                                                                                        (56, null, null, 1, 'GREEN', 'EXIT_010_D', 'S', 1),
                                                                                                        (57, null, null, 1, 'GREEN', 'EXIT_010_E', 'S', 1),
                                                                                                        (58, null, null, 1, 'GREEN', 'EXIT_010_F', 'S', 1),
                                                                                                        (59, null, null, 1, 'GREEN', 'EXIT_010_G', 'S', 1),
                                                                                                        (60, null, null, 1, 'GREEN', 'EXIT_010_H', 'S', 1),

                                                                                                        (61, null, null, 3, 'GREEN', 'EXIT_011_A', 'S', 1),
                                                                                                        (62, null, null, 3, 'GREEN', 'EXIT_011_B', 'S', 1),

                                                                                                        (63, null, null, 2, 'GREEN', 'EXIT_100_A', 'S', 1),
                                                                                                        (64, null, null, 2, 'GREEN', 'EXIT_100_B', 'S', 1),
                                                                                                        (65, null, null, 2, 'GREEN', 'EXIT_100_C', 'S', 1),
                                                                                                        (66, null, null, 2, 'GREEN', 'EXIT_100_D', 'S', 1),

                                                                                                        (67, null, null, 4, 'GREEN', 'EXIT_101_A', 'S', 1),
                                                                                                        (68, null, null, 4, 'GREEN', 'EXIT_101_B', 'S', 1),

                                                                                                        (69, null, null, 3, 'GREEN', 'EXIT_110_A', 'S', 1),
                                                                                                        (70, null, null, 3, 'GREEN', 'EXIT_110_B', 'S', 1),

                                                                                                        (71, null, null, 0, 'GREEN', 'EXIT_111_A', 'S', 1),

                                                                                                        (72, null, null, 5, 'GREEN', 'EXIT_111_B', 'S', 1),

                                                                                                        (73, null, null, 2, 'YELLOW', 'EXIT_001_A', 'S', 1),
                                                                                                        (74, null, null, 2, 'YELLOW', 'EXIT_001_B', 'S', 1),

                                                                                                        (75, null, null, 2, 'YELLOW', 'EXIT_001_C', 'S', 1),
                                                                                                        (76, null, null, 2, 'YELLOW', 'EXIT_001_D', 'S', 1),

                                                                                                        (77, null, null, 1, 'YELLOW', 'EXIT_010_A', 'S', 1),
                                                                                                        (78, null, null, 1, 'YELLOW', 'EXIT_010_B', 'S', 1),
                                                                                                        (79, null, null, 1, 'YELLOW', 'EXIT_010_C', 'S', 1),
                                                                                                        (80, null, null, 1, 'YELLOW', 'EXIT_010_D', 'S', 1),
                                                                                                        (81, null, null, 1, 'YELLOW', 'EXIT_010_E', 'S', 1),
                                                                                                        (82, null, null, 1, 'YELLOW', 'EXIT_010_F', 'S', 1),
                                                                                                        (83, null, null, 1, 'YELLOW', 'EXIT_010_G', 'S', 1),
                                                                                                        (84, null, null, 1, 'YELLOW', 'EXIT_010_H', 'S', 1),

                                                                                                        (85, null, null, 3, 'YELLOW', 'EXIT_011_A', 'S', 1),
                                                                                                        (86, null, null, 3, 'YELLOW', 'EXIT_011_B', 'S', 1),

                                                                                                        (87, null, null, 2, 'YELLOW', 'EXIT_100_A', 'S', 1),
                                                                                                        (88, null, null, 2, 'YELLOW', 'EXIT_100_B', 'S', 1),
                                                                                                        (89, null, null, 2, 'YELLOW', 'EXIT_100_C', 'S', 1),
                                                                                                        (90, null, null, 2, 'YELLOW', 'EXIT_100_D', 'S', 1),

                                                                                                        (91, null, null, 4, 'YELLOW', 'EXIT_101_A', 'S', 1),
                                                                                                        (92, null, null, 4, 'YELLOW', 'EXIT_101_B', 'S', 1),

                                                                                                        (93, null, null, 3, 'YELLOW', 'EXIT_110_A', 'S', 1),
                                                                                                        (94, null, null, 3, 'YELLOW', 'EXIT_110_B', 'S', 1),

                                                                                                        (95, null, null, 0, 'YELLOW', 'EXIT_111_A', 'S', 1),

                                                                                                        (96, null, null, 5, 'YELLOW', 'EXIT_111_B', 'S', 1),

                                                                                                        (97, null, null, 2, 'GREY', 'EXIT_001_A', 'S', 1),
                                                                                                        (98, null, null, 2, 'GREY', 'EXIT_001_B', 'S', 1),
                                                                                                        (99, null, null, 2, 'GREY', 'EXIT_001_C', 'S', 1),
                                                                                                        (100, null, null, 2, 'GREY', 'EXIT_001_D', 'S', 1),

                                                                                                        (101, null, null, 1, 'GREY', 'EXIT_010_A', 'S', 1),
                                                                                                        (102, null, null, 1, 'GREY', 'EXIT_010_B', 'S', 1),
                                                                                                        (103, null, null, 1, 'GREY', 'EXIT_010_C', 'S', 1),
                                                                                                        (104, null, null, 1, 'GREY', 'EXIT_010_D', 'S', 1),
                                                                                                        (105, null, null, 1, 'GREY', 'EXIT_010_E', 'S', 1),
                                                                                                        (106, null, null, 1, 'GREY', 'EXIT_010_F', 'S', 1),
                                                                                                        (107, null, null, 1, 'GREY', 'EXIT_010_G', 'S', 1),
                                                                                                        (108, null, null, 1, 'GREY', 'EXIT_010_H', 'S', 1),

                                                                                                        (109, null, null, 3, 'GREY', 'EXIT_011_A', 'S', 1),
                                                                                                        (110, null, null, 3, 'GREY', 'EXIT_011_B', 'S', 1),

                                                                                                        (111, null, null, 2, 'GREY', 'EXIT_100_A', 'S', 1),
                                                                                                        (112, null, null, 2, 'GREY', 'EXIT_100_B', 'S', 1),
                                                                                                        (113, null, null, 2, 'GREY', 'EXIT_100_C', 'S', 1),
                                                                                                        (114, null, null, 2, 'GREY', 'EXIT_100_D', 'S', 1),

                                                                                                        (115, null, null, 4, 'GREY', 'EXIT_101_A', 'S', 1),
                                                                                                        (116, null, null, 4, 'GREY', 'EXIT_101_B', 'S', 1),

                                                                                                        (117, null, null, 3, 'GREY', 'EXIT_110_A', 'S', 1),
                                                                                                        (118, null, null, 3, 'GREY', 'EXIT_110_B', 'S', 1),

                                                                                                        (119, null, null, 0, 'GREY', 'EXIT_111_A', 'S', 1),
                                                                                                        (120, null, null, 5, 'GREY', 'EXIT_111_B', 'S', 1),

                                                                                                        (121, null, null, 2, 'VIOLET', 'EXIT_001_A', 'S', 1),
                                                                                                        (122, null, null, 2, 'VIOLET', 'EXIT_001_B', 'S', 1),
                                                                                                        (123, null, null, 2, 'VIOLET', 'EXIT_001_C', 'S', 1),
                                                                                                        (124, null, null, 2, 'VIOLET', 'EXIT_001_D', 'S', 1),

                                                                                                        (125, null, null, 1, 'VIOLET', 'EXIT_010_A', 'S', 1),
                                                                                                        (126, null, null, 1, 'VIOLET', 'EXIT_010_B', 'S', 1),
                                                                                                        (127, null, null, 1, 'VIOLET', 'EXIT_010_C', 'S', 1),
                                                                                                        (128, null, null, 1, 'VIOLET', 'EXIT_010_D', 'S', 1),
                                                                                                        (129, null, null, 1, 'VIOLET', 'EXIT_010_E', 'S', 1),
                                                                                                        (130, null, null, 1, 'VIOLET', 'EXIT_010_F', 'S', 1),
                                                                                                        (131, null, null, 1, 'VIOLET', 'EXIT_010_G', 'S', 1),
                                                                                                        (132, null, null, 1, 'VIOLET', 'EXIT_010_H', 'S', 1),

                                                                                                        (133, null, null, 3, 'VIOLET', 'EXIT_011_A', 'S', 1),
                                                                                                        (134, null, null, 3, 'VIOLET', 'EXIT_011_B', 'S', 1),

                                                                                                        (135, null, null, 2, 'VIOLET', 'EXIT_100_A', 'S', 1),
                                                                                                        (136, null, null, 2, 'VIOLET', 'EXIT_100_B', 'S', 1),
                                                                                                        (137, null, null, 2, 'VIOLET', 'EXIT_100_C', 'S', 1),
                                                                                                        (138, null, null, 2, 'VIOLET', 'EXIT_100_D', 'S', 1),

                                                                                                        (139, null, null, 4, 'VIOLET', 'EXIT_101_A', 'S', 1),
                                                                                                        (140, null, null, 4, 'VIOLET', 'EXIT_101_B', 'S', 1),

                                                                                                        (141, null, null, 3, 'VIOLET', 'EXIT_110_A', 'S', 1),
                                                                                                        (142, null, null, 3, 'VIOLET', 'EXIT_110_B', 'S', 1),

                                                                                                        (143, null, null, 0, 'VIOLET', 'EXIT_111_A', 'S', 1),
                                                                                                        (144, null, null, 5, 'VIOLET', 'EXIT_111_B', 'S', 1),

                                                                                                        (145, null, null, 2, 'MAGENTA', 'EXIT_001_A', 'S', 1),
                                                                                                        (146, null, null, 2, 'MAGENTA', 'EXIT_001_B', 'S', 1),
                                                                                                        (147, null, null, 2, 'MAGENTA', 'EXIT_001_C', 'S', 1),
                                                                                                        (148, null, null, 2, 'MAGENTA', 'EXIT_001_D', 'S', 1),

                                                                                                        (149, null, null, 1, 'MAGENTA', 'EXIT_010_A', 'S', 1),
                                                                                                        (150, null, null, 1, 'MAGENTA', 'EXIT_010_B', 'S', 1),
                                                                                                        (151, null, null, 1, 'MAGENTA', 'EXIT_010_C', 'S', 1),
                                                                                                        (152, null, null, 1, 'MAGENTA', 'EXIT_010_D', 'S', 1),
                                                                                                        (153, null, null, 1, 'MAGENTA', 'EXIT_010_E', 'S', 1),
                                                                                                        (154, null, null, 1, 'MAGENTA', 'EXIT_010_F', 'S', 1),
                                                                                                        (155, null, null, 1, 'MAGENTA', 'EXIT_010_G', 'S', 1),
                                                                                                        (156, null, null, 1, 'MAGENTA', 'EXIT_010_H', 'S', 1),

                                                                                                        (157, null, null, 3, 'MAGENTA', 'EXIT_011_A', 'S', 1),
                                                                                                        (158, null, null, 3, 'MAGENTA', 'EXIT_011_B', 'S', 1),

                                                                                                        (159, null, null, 2, 'MAGENTA', 'EXIT_100_A', 'S', 1),
                                                                                                        (160, null, null, 2, 'MAGENTA', 'EXIT_100_B', 'S', 1),
                                                                                                        (161, null, null, 2, 'MAGENTA', 'EXIT_100_C', 'S', 1),
                                                                                                        (162, null, null, 2, 'MAGENTA', 'EXIT_100_D', 'S', 1),

                                                                                                        (163, null, null, 4, 'MAGENTA', 'EXIT_101_A', 'S', 1),
                                                                                                        (164, null, null, 4, 'MAGENTA', 'EXIT_101_B', 'S', 1),

                                                                                                        (165, null, null, 3, 'MAGENTA', 'EXIT_110_A', 'S', 1),
                                                                                                        (166, null, null, 3, 'MAGENTA', 'EXIT_110_B', 'S', 1),

                                                                                                        (167, null, null, 0, 'MAGENTA', 'EXIT_111_A', 'S', 1),
                                                                                                        (168, null, null, 5, 'MAGENTA', 'EXIT_111_B', 'S', 1),

                                                                                                        (169, null, null, 2, 'ORANGE', 'EXIT_001_A', 'S', 1),
                                                                                                        (170, null, null, 2, 'ORANGE', 'EXIT_001_B', 'S', 1),
                                                                                                        (171, null, null, 2, 'ORANGE', 'EXIT_001_C', 'S', 1),
                                                                                                        (172, null, null, 2, 'ORANGE', 'EXIT_001_D', 'S', 1),

                                                                                                        (173, null, null, 1, 'ORANGE', 'EXIT_010_A', 'S', 1),
                                                                                                        (174, null, null, 1, 'ORANGE', 'EXIT_010_B', 'S', 1),
                                                                                                        (175, null, null, 1, 'ORANGE', 'EXIT_010_C', 'S', 1),
                                                                                                        (176, null, null, 1, 'ORANGE', 'EXIT_010_D', 'S', 1),
                                                                                                        (177, null, null, 1, 'ORANGE', 'EXIT_010_E', 'S', 1),
                                                                                                        (178, null, null, 1, 'ORANGE', 'EXIT_010_F', 'S', 1),
                                                                                                        (179, null, null, 1, 'ORANGE', 'EXIT_010_G', 'S', 1),
                                                                                                        (180, null, null, 1, 'ORANGE', 'EXIT_010_H', 'S', 1),

                                                                                                        (181, null, null, 3, 'ORANGE', 'EXIT_011_A', 'S', 1),
                                                                                                        (182, null, null, 3, 'ORANGE', 'EXIT_011_B', 'S', 1),

                                                                                                        (183, null, null, 2, 'ORANGE', 'EXIT_100_A', 'S', 1),
                                                                                                        (184, null, null, 2, 'ORANGE', 'EXIT_100_B', 'S', 1),
                                                                                                        (185, null, null, 2, 'ORANGE', 'EXIT_100_C', 'S', 1),
                                                                                                        (186, null, null, 2, 'ORANGE', 'EXIT_100_D', 'S', 1),

                                                                                                        (187, null, null, 4, 'ORANGE', 'EXIT_101_A', 'S', 1),
                                                                                                        (188, null, null, 4, 'ORANGE', 'EXIT_101_B', 'S', 1),

                                                                                                        (189, null, null, 3, 'ORANGE', 'EXIT_110_A', 'S', 1),
                                                                                                        (190, null, null, 3, 'ORANGE', 'EXIT_110_B', 'S', 1),

                                                                                                        (191, null, null, 0, 'ORANGE', 'EXIT_111_A', 'S', 1),
                                                                                                        (192, null, null, 5, 'ORANGE', 'EXIT_111_B', 'S', 1);
                                                        
--Effects

INSERT INTO effects(id, color, hability) VALUES (1,null,'SPEED_UP'),
                                                (2,null,'BRAKE'),
                                                (3,null,'REVERSE'),
                                                (4,null,'EXTRA_GAS');

INSERT INTO friendships(id, sender, receiver, friend_state) VALUES (1, 3, 4, 'ACCEPTED'),
                                                                   (2, 3, 5, 'ACCEPTED'),
                                                                   (3, 3, 6, 'PENDING'),
                                                                   (4, 5, 6, 'REJECTED');
