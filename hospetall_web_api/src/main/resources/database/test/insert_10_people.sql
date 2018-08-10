USE HosPetAll;

INSERT INTO receptionist(email, family_name, given_name, telephone) VALUES
  ('lennon@gmail.com', 'Lennon', 'John', '+3333'),
  ('mccartney@gmail.com', 'McCartney', 'Paul', '+3339'),
  ('harrison@gmail.com', 'Harrison', 'George', '+3338'),
  ('starr@gmail.com', 'Starr', 'Ringo', '+3337'),
  ('martin@gmail.com', 'Martin', 'George', '+3336'),
  ('latimer@gmail.com', 'Latimer', 'Andrew', '+3335')

INSERT INTO nurse(email, family_name, given_name, telephone) VALUES
  ('barret@gmail.com', 'Barret', 'Syd', '+3222'),
  ('joplin@gmail.com', 'Joplin', 'Janis', '+3229'),
  ('hendrix@gmail.com', 'Hendrix', 'Jimi', '+3228'),
  ('morrison@gmail.com', 'Morrison', 'Jim', '+3227'),
  ('warhol@gmail.com', 'Warhol', 'Andy', '+3226')

INSERT INTO veterinarian(email, family_name, given_name, telephone) VALUES
  ('waters@gmail.com', 'Waters', 'Roger', '+31111'),
  ('gilmour@gmail.com', 'Gilmour', 'David', '+31119'),
  ('wright@gmail.com', 'Wright', 'Roger', '+311110'),
  ('reed@gmail.com', 'Reed', 'Lou', '+311119'),
  ('mason@gmail.com', 'Mason', 'Paul', '+311115')

INSERT INTO account(username, password) VALUES
  ('lennon@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('mccartney@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('harrison@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('starr@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('martin@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('latimer@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('barret@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('joplin@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('hendrix@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('morrison@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('warhol@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('waters@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('gilmour@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('wright@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('reed@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('mason@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.'),
  ('admin@gmail.com', '$2a$12$gtgWXvA3I.J4Fe9Mwl0HS.KBGCaGHlbHqz.d1GMT/HC65ftcD57V.')

INSERT INTO account_authorities(account_id, authority_id) VALUES
  (1, 2), (2,2), (3,2), (4,2), (5,2), (6,2),
  (7, 6), (8,6), (9,6), (10,6), (11,6),
  (12,4), (13,4), (14,4), (15,4), (16,4),
  (1, 3), (2,3), (3,3), (4,3), (5,3), (6,3),
  (7, 3), (8,3), (9,3), (10,3), (11,3),
  (12,3), (13, 3), (14,3), (15,3), (16,3),
  (17,5)

