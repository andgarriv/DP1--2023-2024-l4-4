--Authorities
INSERT INTO authorities(id, authority) VALUES (1,'ADMIN'),
                                              (2,'PLAYER');


INSERT INTO players(id, name, surname, password, email, birth_date, authority, nickname, avatar) VALUES -- Admins Users
                                                                                                        (1,'Alvaro','Fernandez','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin1@gmail.com', '2003-07-12', 1, 'admin1', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (2,'Carlos','Prieto','$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin2@gmail.com', '2000-05-11', 1, 'admin2', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (13, 'Sofia', 'Lopez', '$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin3@gmail.com', '1998-09-10', 1, 'admin3', 'https://cdn-icons-png.flaticon.com/512/147/147138.png'),
                                                                                                        (14, 'David', 'Sanchez', '$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','admin4@gmail.com', '1988-12-05', 1, 'admin4', 'https://cdn-icons-png.flaticon.com/512/147/147139.png'),
                                                                                                        (15, 'NotNamed', 'NotSurnamed', '$2b$12$kjSsw.4pp2iBQn8PfwSmM.DiYv5r2d4.PwmpreyvPdZrvOJDzUjEK','notuser@gmial.com', '1998-09-10', 1, 'DeleteUser', 'https://cdn-icons-png.flaticon.com/128/12225/12225949.png'),
                                                                                                        -- Player Users
                                                                                                        (3,'Angel','Garcia','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','angelgares6424@gmail.com', '2003-07-12', 2, 'Angelgares', 'https://i.pinimg.com/originals/09/69/c2/0969c2f8a87af926fcf912330b67206f.jpg'),
                                                                                                        (4,'Jorge','Muñoz','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','jorgemr@pm.me', '1999-09-11', 2, 'Jorge_ADD', 'https://images.squarespace-cdn.com/content/v1/5e45d3a8e509f61738454469/81e0d83b-0fa6-48d0-801b-1f0c1515d786/logo+arus+cuadrado+web.png'),
                                                                                                        (5,'Javier','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','java@gmail.com', '2006-11-30', 2, 'javrodrei', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (6,'Isaac','Solis','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','princesita@gmail.com', '2002-07-12', 2, 'isasolpad', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (7,'Andres','Garcia','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','andres7212@gmail.com', '2001-12-23', 2, 'Andresisco', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHRfYlqLUzoJjY4CQnv2yIOwS7WAFuBZrs9w&usqp=CAU'),
                                                                                                        (8,'Alejandro','Perez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','alejandro@gmail.com', '2001-08-11', 2, 'alepersan', 'https://pbs.twimg.com/media/FTQBiYDXwAADvbp.jpg'),                                                                                          
                                                                                                        (9, 'Maria', 'Herrero', '$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa', 'mariahm@gmail.com', '2005-05-02', 2, 'Mariia_hm25', 'https://pbs.twimg.com/profile_images/1729861116203810816/bcN6IZ0H_400x400.jpg'),
                                                                                                        (10, 'Juan', 'Fernandez', '$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa', 'juanfg@gmail.com', '1985-05-20', 2, 'juanfer', 'https://cdn-icons-png.flaticon.com/512/147/147142.png'),
                                                                                                        (11, 'Lucia', 'Martinez', '$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa', 'luciamn@gmail.com', '1995-03-30', 2, 'luciamar', 'https://cdn-icons-png.flaticon.com/512/147/147146.png'),
                                                                                                        (12, 'Carlos', 'Garcia', '$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa', 'carlosgc@gmail.com', '1992-07-25', 2, 'carlosgar', 'https://cdn-icons-png.flaticon.com/512/147/147143.png'),
                                                                                                        (16,'Manolito','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','manolito@gmail.com', '2003-07-12', 2, 'manolito', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (17,'Joselito','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','joselito@gmail.com', '2003-07-12', 2, 'joselito', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (18,'Pepito','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','pepito@gmail.com', '2003-07-12', 2, 'pepito', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (19,'Pepita','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','pepitaR@gmail.com', '2003-07-12', 2, 'pepita', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (20,'Manuel','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','manu@gmail.com', '2003-07-12', 2, 'manu', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (21,'Manuela','Rodriguez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa', 'manuela@gmail.com', '2003-07-12', 2, 'manuela', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (22,'Isaac','Fernaen','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','isasss@gmail.com', '2003-07-12', 2, 'isa', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (23,'Isaac','Fernandez','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFHV1xs2e.acOQUa','ferniii@gmail.com', '2003-07-12', 2, 'ferniii', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (24,'Isaac','Gares','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFH', 'isagar@gmail.com', '2003-07-12', 2, 'isagar', 'https://cdn-icons-png.flaticon.com/512/147/147144.png'),
                                                                                                        (25,'Isaac','Gareston','$2b$12$3XLFXRg97xdKsgFwVxoHMOdsQFbit2TS3fseApFH', 'gasreston.gmail.com', '2003-07-12', 2, 'gareston', 'https://cdn-icons-png.flaticon.com/512/147/147144.png');


-- Achievements
INSERT INTO achievements(id, name, description, threshold, badge_not_achieved, badge_achieved, category) VALUES (1, 'First crossing','This is just the beggining, take part in your first game', 1.0,'https://cdn-icons-png.flaticon.com/128/4245/4245486.png', 'https://cdn-icons-png.flaticon.com/128/4245/4245497.png','GAMES_PLAYED'),
                                                                                                                (2, 'Five Paths','Complete 5 games. Each game enhances your understanding and skills', 5.0,'https://cdn-icons-png.flaticon.com/128/3521/3521881.png', 'https://cdn-icons-png.flaticon.com/128/3521/3521852.png', 'GAMES_PLAYED'),
                                                                                                                (3, 'Decade of Detours','Play 10 games to showcase your growing expertise and ability to go through challenges', 10.0, 'https://cdn-icons-png.flaticon.com/128/5229/5229355.png', 'https://cdn-icons-png.flaticon.com/128/5229/5229362.png', 'GAMES_PLAYED'),

                                                                                                                (4, 'Winning initiative', 'Achieve your first victory by pushing your skills to the limit', 1.0, 'https://cdn-icons-png.flaticon.com/128/4442/4442281.png', 'https://cdn-icons-png.flaticon.com/128/4442/4442360.png', 'VICTORIES'),
                                                                                                                (5, 'Quintet of Conquest', 'Secure 5 victories to show it is not a matter of luck', 5.0, 'https://cdn-icons-png.flaticon.com/128/3884/3884644.png', 'https://cdn-icons-png.flaticon.com/128/3884/3884683.png', 'VICTORIES'),
                                                                                                                (6, 'Decisive dominion', 'Consolidate 10 victories to guarantee the constant capacity for improvement and hard work', 10.0, 'https://cdn-icons-png.flaticon.com/128/1357/1357043.png', 'https://cdn-icons-png.flaticon.com/128/1357/1357094.png', 'VICTORIES'), 

                                                                                                                (7, 'First Journey', 'First half hour of contact with the game', 0.5, 'https://cdn-icons-png.flaticon.com/128/6198/6198482.png', 'https://cdn-icons-png.flaticon.com/128/6198/6198463.png', 'TOTAL_PLAY_TIME'),
                                                                                                                (8, 'Hour of Skillful Maneuvering', 'Dedicate a whole hour to mastering the game', 1.0, 'https://cdn-icons-png.flaticon.com/128/6117/6117105.png', 'https://cdn-icons-png.flaticon.com/128/6116/6116661.png', 'TOTAL_PLAY_TIME'),
                                                                                                                (9, 'Marathon of Mastery', 'Invest 5 hours as a deep expertise in the games strategic depth', 5.0, 'https://cdn-icons-png.flaticon.com/128/1837/1837562.png', 'https://cdn-icons-png.flaticon.com/128/1837/1837573.png', 'TOTAL_PLAY_TIME');
                                                                                         
-- Player Achievements
INSERT INTO player_achievements(id, achieve_at) VALUES (1, '2023-09-01'),
                                                       (2, '2023-09-01'),
                                                       (3, '2023-09-02'),
                                                       (4, '2023-09-19'),
                                                       (5, '2023-10-09'),
                                                       (6, '2023-10-09'),
                                                       (7, '2023-11-01'),                                                     
                                                       (8, '2023-11-25'),                                                    
                                                       (9, '2023-12-19'),
                                                       (10, '2023-09-02'),
                                                       (11, '2023-09-01'),
                                                       (12, '2023-09-01'),
                                                       (13, '2023-09-01'),
                                                       (14, '2023-10-20'),
                                                       (15, '2023-09-01'),
                                                       (16, '2023-09-01'),
                                                       (17, '2023-09-01'),
                                                       (18, '2023-09-01'),
                                                       (19, '2023-09-01'),
                                                       (20, '2023-09-01'),
                                                       (21, '2023-09-27'),
                                                       (22, '2023-10-09'),
                                                       (23, '2023-09-01'),
                                                       (24, '2023-10-09'),
                                                       (25, '2023-11-01'),
                                                       (26, '2023-10-30'),
                                                       (27, '2023-09-01'),
                                                       (28, '2023-09-01'),
                                                       (29, '2023-09-01'),
                                                       (30, '2023-11-25'),
                                                       (31, '2023-09-01'),
                                                       (32, '2023-09-01');
                                                       


                                                       





--Player PlayerAchievements
INSERT INTO players_player_achievement(player_achievement_id, player_id) VALUES (1, 3),
                                                                                (2, 3),
                                                                                (3, 3),
                                                                                (4, 3),
                                                                                (5, 3),
                                                                                (6, 3),
                                                                                (7, 3),
                                                                                (8, 3),
                                                                                (9, 3),
                                                                                (10, 4),
                                                                                (11, 5),
                                                                                (12, 5),
                                                                                (13, 5),
                                                                                (14, 5),
                                                                                (15, 6),
                                                                                (16, 6),
                                                                                (17, 6),
                                                                                (18, 6),
                                                                                (19, 7),
                                                                                (20, 7),
                                                                                (21, 7),
                                                                                (22, 7),
                                                                                (23, 8),
                                                                                (24, 8),
                                                                                (25, 8),
                                                                                (26, 9),
                                                                                (27, 10),
                                                                                (28, 10),
                                                                                (29, 10),
                                                                                (30, 10),
                                                                                (31, 11),
                                                                                (32, 11);
                                                                                









--Achievement PlayerAchievements
INSERT INTO achievements_player_achievements(achievement_id, player_achievements_id) VALUES (1, 1),
                                                                                            (7, 2),
                                                                                            (4, 3),
                                                                                            (8, 4),
                                                                                            (2, 5),
                                                                                            (5, 6),
                                                                                            (3, 7),
                                                                                            (6, 8),
                                                                                            (9, 9),
                                                                                            (1, 10),
                                                                                            (1, 11),
                                                                                            (4, 12),
                                                                                            (7, 13),
                                                                                            (8, 14),
                                                                                            (1, 15),
                                                                                            (7, 16),
                                                                                            (4, 17),
                                                                                            (8, 18),
                                                                                            (1, 19),
                                                                                            (4, 20),
                                                                                            (7, 21),
                                                                                            (8, 22),
                                                                                            (1, 23),
                                                                                            (7, 24),
                                                                                            (8, 25),
                                                                                            (1, 26),
                                                                                            (1, 27),
                                                                                            (4, 28),
                                                                                            (7, 29),
                                                                                            (8, 30),
                                                                                            (1, 31),
                                                                                            (7, 32);




-- GamePlayers
INSERT INTO game_players(id, energy, player_id, color) VALUES (1, 3, 5,'VIOLET'), 
                                                              (2, 2, 6,'ORANGE'),                                                               
                                                              (3, 1, 5,'GREEN'),
                                                              (4, 3, 6,'RED'), 
                                                              (5, 1, 7,'ORANGE'), 
                                                              (6, 2, 8,'RED'), 
                                                              (7, 0, 10,'MAGENTA'), 
                                                              (8, 3, 11,'RED'), 
                                                              (9, 3, 3,'BLUE'), 
                                                              (10, 3, 6,'GREEN'),
                                                              (11, 2, 3,'MAGENTA'), 
                                                              (12, 1, 4,'ORANGE'),
                                                              (13, 3, 3,'BLUE'), 
                                                              (14, 3, 10,'RED'),
                                                              (15, 0, 3,'BLUE'), 
                                                              (16, 1, 7,'VIOLET'),
                                                              (17, 3, 3,'GREEN'), 
                                                              (18, 3, 7,'ORANGE'),
                                                              (19, 2, 3,'ORANGE'), 
                                                              (20, 3, 8,'RED'),
                                                              (21, 0, 3,'MAGENTA'), 
                                                              (22, 3, 6,'RED'),
                                                              (23, 3, 3,'VIOLET'), 
                                                              (24, 2, 5,'RED'),
                                                              (25, 3, 3,'BLUE'), 
                                                              (26, 1, 9,'ORANGE'),
                                                              (27, 1, 3,'BLUE'), 
                                                              (28, 2, 8,'VIOLET'),
                                                              (29, 3, 3,'GREEN'), 
                                                              (30, 1, 10,'RED'),
                                                              (31, 2, 3,'BLUE'), 
                                                              (32, 2, 8,'GREEN'),
                                                              (33, 3, 7,'ORANGE'), 
                                                              (34, 3, 8,'MAGENTA');

-- Games 
INSERT INTO games(id, round, winner, started, ended, game_player_turn_id, effect) VALUES (1, 16, 6, '2023-09-01 09:00:05', '2023-09-01 09:15:54', 1, 'NONE'),
                                                                                         (2, 18, 5, '2023-09-01 09:05:17', '2023-09-01 09:23:17', 4, 'NONE'),
                                                                                         (3, 24, 7, '2023-09-01 10:00:00', '2023-09-01 10:28:39', 6, 'NONE'),
                                                                                         (4, 20, 10, '2023-09-01 14:18:32', '2023-09-01 14:52:20', 8, 'NONE'),

                                                                                         (5, 22, 6, '2023-09-01 19:14:25', '2023-09-01 19:47:38', 9, 'NONE'),
                                                                                         (6, 25, 3, '2023-09-02 14:03:07', '2023-09-02 14:24:07', 12, 'NONE'),
                                                                                         (7, 20, 3, '2023-09-19 04:13:56', '2023-09-19 04:32:56', 14, 'NONE'),
                                                                                         (8, 21, 3, '2023-09-27 09:49:44', '2023-09-27 10:17:44', 16, 'NONE'),                                     
                                                                                         (9, 18, 3, '2023-10-09 08:30:29', '2023-10-09 08:53:29', 18, 'NONE'),
                                                                                         (10, 20, 3, '2023-10-09 09:07:00', '2023-10-09 09:27:00', 20, 'NONE'),
                                                                                         (11, 26, 3, '2023-10-12 07:08:07', '2023-10-12 07:25:07', 22, 'NONE'),
                                                                                         (12, 20, 3, '2023-10-20 00:51:04', '2023-10-20 01:27:04', 24, 'NONE'),
                                                                                         (13, 23, 3, '2023-10-30 22:34:55', '2023-10-30 22:54:55', 26, 'NONE'),
                                                                                         (14, 27, 3, '2023-11-01 13:44:27', '2023-11-01 14:06:27', 28, 'NONE'),
                                                                                         (15, 26, 3, '2023-11-25 03:59:03', '2023-11-25 04:24:03', 30, 'NONE'),
                                                                                         (16, 24, 3, '2023-12-19 17:33:57', '2023-12-19 18:10:57', 32, 'NONE'),

                                                                                         (17, 1, null, '2024-01-05 12:48:33', null, 33, 'NONE');

-- Games_Game_Players
INSERT INTO games_game_players(game_id, game_players_id) VALUES (1, 1),
                                                                (1, 2),
                                                                (2, 3),
                                                                (2, 4),
                                                                (3, 5),
                                                                (3, 6),
                                                                (4, 7),
                                                                (4, 8),
                                                                (5, 9),
                                                                (5, 10),
                                                                (6, 11),
                                                                (6, 12),
                                                                (7, 13),
                                                                (7, 14),
                                                                (8, 15),
                                                                (8, 16),
                                                                (9, 17),
                                                                (9, 18),
                                                                (10, 19),
                                                                (10, 20),
                                                                (11, 21),
                                                                (11, 22),
                                                                (12, 23),
                                                                (12, 24),
                                                                (13, 25),
                                                                (13, 26),
                                                                (14, 27),
                                                                (14, 28),
                                                                (15, 29),
                                                                (15, 30),
                                                                (16, 31),
                                                                (16, 32),
                                                                (17, 33),
                                                                (17, 34);

-- Message
 INSERT INTO messages(id, color, reaction) VALUES (1, null, 'HI'),
                                                 (2, null, 'WHAT_A_PITY'),
                                                 (3, null, 'SORRY'),
                                                 (4, null, 'THANKS'),
                                                 (5, null, 'NICE'),
                                                 (6, null, 'JAJAJAJA'),
                                                 (7, null, 'GG'),
                                                 (8, null, 'GOOD_LUCK'),
                                                 (9, 'ORANGE', 'HI'),
                                                 (10, 'MAGENTA', 'GOOD_LUCK'),
                                                 (11, 'ORANGE', 'THANKS'),
                                                 (12, 'ORANGE', 'NICE'),
                                                 (13, 'MAGENTA', 'JAJAJAJA');

INSERT INTO games_message(game_id, message_id) VALUES (17, 9),
                                                       (17, 10),
                                                       (17, 11),
                                                       (17, 12),
                                                       (17, 13);  

-- Friendship
INSERT INTO friendships(id, receiver, sender, friend_state) VALUES (1, 4, 3, 'ACCEPTED'),
                                                                  (2, 5, 3, 'ACCEPTED'),
                                                                  (3, 6, 3, 'ACCEPTED'),
                                                                  (4, 7, 3, 'PENDING'),
                                                                  (5, 8, 3, 'ACCEPTED'),
                                                                  (6, 3, 9, 'ACCEPTED'),
                                                                  (7, 3, 10, 'PENDING'),
                                                                  (8, 11, 3, 'PENDING'),
                                                                  (9, 4, 5, 'ACCEPTED'),
                                                                  (10, 5, 6, 'ACCEPTED'),
                                                                  (11, 5, 8, 'PENDING'),
                                                                  (12, 6, 8, 'PENDING'),
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

INSERT INTO cards (id, initiative, color, exit, orientation, is_template, card_state, card_row, card_column, updated_at) VALUES
                                                                                                        (201, 2, 'ORANGE', 'EXIT_001_A', 'S', 0, 'IN_HAND', null, null, null),
                                                                                                        (202, 2, 'ORANGE', 'EXIT_001_B', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (203, 2, 'ORANGE', 'EXIT_001_C', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (204, 2, 'ORANGE', 'EXIT_001_D', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (205, 1, 'ORANGE', 'EXIT_010_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (206, 1, 'ORANGE', 'EXIT_010_B', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (207, 1, 'ORANGE', 'EXIT_010_C', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (208, 1, 'ORANGE', 'EXIT_010_D', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (209, 1, 'ORANGE', 'EXIT_010_E', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (210, 1, 'ORANGE', 'EXIT_010_F', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (211, 1, 'ORANGE', 'EXIT_010_G', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (212, 1, 'ORANGE', 'EXIT_010_H', 'S', 0, 'IN_HAND', null, null, null),

                                                                                                        (213, 3, 'ORANGE', 'EXIT_011_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (214, 3, 'ORANGE', 'EXIT_011_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (215, 2, 'ORANGE', 'EXIT_100_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (216, 2, 'ORANGE', 'EXIT_100_B', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (217, 2, 'ORANGE', 'EXIT_100_C', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (218, 2, 'ORANGE', 'EXIT_100_D', 'S', 0, 'IN_HAND', null, null, null),

                                                                                                        (219, 4, 'ORANGE', 'EXIT_101_A', 'S', 0, 'IN_HAND', null, null, null),
                                                                                                        (220, 4, 'ORANGE', 'EXIT_101_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (221, 3, 'ORANGE', 'EXIT_110_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (222, 3, 'ORANGE', 'EXIT_110_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (223, 0, 'ORANGE', 'EXIT_111_A', 'S', 0, 'IN_HAND', null, null, null),

                                                                                                        (224, 5, 'ORANGE', 'EXIT_111_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (225, 0, 'ORANGE', 'START', 'S', 0, 'ON_BOARD', 4, 2, '2024-01-05 12:48:33.328'),

                                                                                                        (226, 2, 'MAGENTA', 'EXIT_001_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (227, 2, 'MAGENTA', 'EXIT_001_B', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (228, 2, 'MAGENTA', 'EXIT_001_C', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (229, 2, 'MAGENTA', 'EXIT_001_D', 'S', 0, 'IN_HAND', null, null, null),

                                                                                                        (230, 1, 'MAGENTA', 'EXIT_010_A', 'S', 0, 'IN_HAND', null, null, null),
                                                                                                        (231, 1, 'MAGENTA', 'EXIT_010_B', 'S', 0, 'IN_HAND', null, null, null),
                                                                                                        (232, 1, 'MAGENTA', 'EXIT_010_C', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (233, 1, 'MAGENTA', 'EXIT_010_D', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (234, 1, 'MAGENTA', 'EXIT_010_E', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (235, 1, 'MAGENTA', 'EXIT_010_F', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (236, 1, 'MAGENTA', 'EXIT_010_G', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (237, 1, 'MAGENTA', 'EXIT_010_H', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (238, 3, 'MAGENTA', 'EXIT_011_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (239, 3, 'MAGENTA', 'EXIT_011_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (240, 2, 'MAGENTA', 'EXIT_100_A', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (241, 2, 'MAGENTA', 'EXIT_100_B', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (242, 2, 'MAGENTA', 'EXIT_100_C', 'S', 0, 'IN_DECK', null, null, null),
                                                                                                        (243, 2, 'MAGENTA', 'EXIT_100_D', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (244, 4, 'MAGENTA', 'EXIT_101_A', 'S', 0, 'IN_HAND', null, null, null),
                                                                                                        (245, 4, 'MAGENTA', 'EXIT_101_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (246, 3, 'MAGENTA', 'EXIT_110_A', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (247, 3, 'MAGENTA', 'EXIT_110_B', 'S', 0, 'IN_HAND', null, null, null),

                                                                                                        (248, 0, 'MAGENTA', 'EXIT_111_A', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (249, 5, 'MAGENTA', 'EXIT_111_B', 'S', 0, 'IN_DECK', null, null, null),

                                                                                                        (250, 0, 'MAGENTA', 'START', 'S', 0, 'ON_BOARD', 4, 4, '2024-01-05 12:48:33.489');


INSERT INTO game_players_cards (cards_id, game_player_id) VALUES (201, 33),
                                                                 (202, 33),
                                                                 (203, 33),
                                                                 (204, 33),
                                                                 (205, 33),
                                                                 (206, 33),
                                                                 (207, 33),
                                                                 (208, 33),
                                                                 (209, 33),
                                                                 (210, 33),
                                                                 (211, 33),
                                                                 (212, 33),
                                                                 (213, 33),
                                                                 (214, 33),
                                                                 (215, 33),
                                                                 (216, 33),
                                                                 (217, 33),
                                                                 (218, 33),
                                                                 (219, 33),
                                                                 (220, 33),
                                                                 (221, 33),
                                                                 (222, 33),
                                                                 (223, 33),
                                                                 (224, 33),
                                                                 (225, 33),
                                                                 (226, 34),
                                                                 (227, 34),
                                                                 (228, 34),
                                                                 (229, 34),
                                                                 (230, 34),
                                                                 (231, 34),
                                                                 (232, 34),
                                                                 (233, 34),
                                                                 (234, 34),
                                                                 (235, 34),
                                                                 (236, 34),
                                                                 (237, 34),
                                                                 (238, 34),
                                                                 (239, 34),
                                                                 (240, 34),
                                                                 (241, 34),
                                                                 (242, 34),
                                                                 (243, 34),
                                                                 (244, 34),
                                                                 (245, 34),
                                                                 (246, 34),
                                                                 (247, 34),
                                                                 (248, 34),
                                                                 (249, 34),
                                                                 (250, 34);

                                                                                                                                                                
