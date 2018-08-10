use hospetall;

insert into client(email, family_name, given_name, telephone, address, nif, other, postal_code) values
 ('abails89@bbb.org', 'Bails', 'Andrej', '314-206-7994', '3 Amoth Circle', 747501024, 'xjjBMuwvPxqtTu5KWbyMfiSLACinl9vCRa6fQTa5Zt8wi0t1qQ5jF2F ABuU136i6vAhfLL5n4gZyUqh', null),
 ('abaldinottib@harvard.edu', 'Baldinotti', 'Avis', '443-581-8506', '76 Grasskamp Terrace', 802744965, 'zfUV8IItmbqxClgRrYWTqkqbWu5mUQbZOKPrSqXIdNlMRDA6WHPsX1X KnIYFBKPLC1dGP58rL41Djfy', null),
 ('abardelldv@google.it', 'Bardell', 'Ade', '597-466-5629', '07 Mesta Trail', 268533324, 's4ndzjcqtc1b4OCXRqDDWttcNGW9yluSkm4pfq1DAs8HjNLiA4CKoZd faHwAi1h80icHIUpvNaXCKRw', null),
 ('abarnewalll4@mac.com', 'Barnewall', 'Abdul', '744-471-5830', '5799 Bashford Trail', 253453334, 'QacNAUqZZmLrhToww9f0ljDtXIVN7n6ZGZ7bVoP1qQGh9HDy17NXvFn aIPamkGsaXamgZhcAHNt0byV', '6529'),
 ('abarocka3@hexun.com', 'Barock', 'Alicea', '923-136-5622', '2148 Walton Point', 160943416, '660rBDk7K7hazCcfyrsTOOnNSqhf3x8rFpw3MDlLjlBi1QuLKQ33Sc5 mNcCyQtUlRgG1H1BMdeeTj7K', '592 14'),
 ('abeasleigh48@imageshack.us', 'Beasleigh', 'Avie', '356-867-1462', '094 Roxbury Alley', 721416594, 'DRrpAr3Yl0krvgyRVE5iwVqw32zwycEisQLlbSX7qna9hPQrsBDGFnY x2lAx80zzMjkGTxBcLxEeZXH', '29950'),
 ('abennion1n@bbb.org', 'Bennion', 'Archambault', '103-428-2894', '316 Stone Corner Park', 498260595, 'pqvY0pG0vcFnDbYtxkSLDLQOSGJjmagra37WnQhvTCG3dZQQMClTNv9 gn4t6u21DGOdfB1Pgx3UFDg7', '8134'),
 ('ablasle4@wsj.com', 'Blasl', 'Andeee', '928-944-8664', '8 Continental Center', 618701314, 'nGznNIF3woasXSYozH7MC52XOUmKuoVIY9RfUuF89mLcb6O3YWc26FX 3YRB6xPsbvMByWa0BBRYR1bv', null),
 ('abool6l@house.gov', 'Bool', 'Anderea', '589-721-3340', '84 Havey Hill', 733479288, 'JS0P1dtyvJ4MEW42GZHmQu35uKv56gl50SgY300Y06SjpGWzeVhKCWB Nxi1vVv9vMRRw93C68ZHdztC', '3105-325'),
 ('abownassir@hhs.gov', 'Bownass', 'Adolpho', '471-357-3103', '2 Nova Circle', 625863761, '9uX6fCsGTkyOAt8skd8g7uhUb3wunvrxpzxpvbmP8tBmMUn6tXp43hF uuGUOFgJUSzXIBkwzzDQa4Ax', null);

INSERT INTO account(username, password) VALUES ('abails89@bbb.org', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abaldinottib@harvard.edu', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abardelldv@google.it', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abarnewalll4@mac.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abarocka3@hexun.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abeasleigh48@imageshack.us', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abennion1n@bbb.org', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('ablasle4@wsj.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abool6l@house.gov', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
 ('abownassir@hhs.gov', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.');

INSERT INTO account_authorities(account_id, authority_id) VALUES (18,5),(19,5),(20,5),(21,5),(22,5),(23,5),(26,5),(27,5);
