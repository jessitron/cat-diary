;             
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.CAT_IDENTITIES;
INSERT INTO "PUBLIC"."CATS" VALUES
(3, 'Pixie', 9),
(33, 'Pincho', 8);
INSERT INTO "PUBLIC"."CAT_IDENTITIES" VALUES
(4, '{bcrypt}$2a$10$7tCFINbwEfR4aHG.WtYFsug2BOsQta0RV8uIzzui2k54PGIKa41HO', 'Pixie', 3),
(34, '{bcrypt}$2a$10$yyPa9VgpwPYe1D3VfrFKDOV5j7aUl5fzTwLdPBMWe7WTTzH8TOT3y', 'Pincho', 33);

INSERT INTO "PUBLIC"."ENTRIES" VALUES
(5, 'Pixie', 'I sit on them.', 'https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pixie-on-computer.jpg', TIMESTAMP '2020-06-14 18:34:46.964', 'Computers'),
(35, 'Pincho', 'You cannot see my internal shape when I fold into a bean', 'https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_yoga.jpg', TIMESTAMP '2020-06-15 12:06:26.269', 'I am a pincho bean'),
(36, 'Pincho', 'My unicorn horn is heavy.', 'https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_unicorn.jpg', TIMESTAMP '2020-06-15 12:07:32.133', 'I bear the burden of magic'),
(37, 'Pincho', STRINGDECODE('Violets are blue\r\nI am the most handsome cat\r\nand you think so too.'), 'https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_strawhat.jpg', TIMESTAMP '2020-06-15 12:09:20.333', 'Roses are red'),
(38, 'Pincho', 'You can become trapped in merriment', 'https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_santa.jpg', TIMESTAMP '2020-06-15 12:10:15.907', 'Beware Christmas'),
(39, 'Pincho', 'I am dangerous, and fancy', 'https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_purrate_2.jpg', TIMESTAMP '2020-06-15 12:11:05.594', 'Fear me');             
