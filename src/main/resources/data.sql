-- Admin user
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (2,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Player user
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (9,'player1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);
INSERT INTO appusers(id,username,password,authority) VALUES (3,'anggaresc1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);
INSERT INTO appusers(id,username,password,authority) VALUES (4,'javrodrei','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'jormunrod','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'andgarriv','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'isasolpad','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'alepersan3','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',2);

-- Owner user
INSERT INTO authorities(id,authority) VALUES (3,'OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (11,'owner1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);

-- Achievements
INSERT INTO achievements(id,name,description,threshold,badge_image,metric,achieve_at) VALUES (1,'Basic expirence','Play 10 games',10.0,'https://cdn-icons-png.flaticon.com/512/5243/5243423.png','GAMES_PLAYED','2022-01-01');
INSERT INTO achievements(id,name,description,threshold,badge_image,metric,achieve_at) VALUES (2,'Explorer','Play 25 games',25.0,'https://cdn-icons-png.flaticon.com/512/603/603855.png','GAMES_PLAYED','2022-01-01');
INSERT INTO achievements(id,name,description,threshold,metric,achieve_at) VALUES (3,'Expert','Win 20 games',20.0,'VICTORIES','2022-01-01');
