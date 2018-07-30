USE HosPetAll;
-- inserts to tests
insert into client (email, family_name, given_name, telephone, address, nif, other, postal_code, telephone_alternative) values ('zglenny0@auda.org.au', 'Glenny', 'Zandra', '687-569-2069', '96260 North Circle', 278705361, 'ybCvZc8Vr96FaC4NXC0882FdFfnvjn4dihAtqY16fZYSjxGnIrGUuLP VG52QQUtxJwzP7Jlp26nILCK', null, '498-381-3900');
insert into species (name) values ('Trichosurus vulpecula');
insert into race (name) values ('Blue wildebeest'); 
insert into veterinarian (email, family_name, given_name, telephone) values ('gpentycross0@wisc.edu', 'Pentycross', 'Gwennie', '567-563-5790');
insert into pet (name, birthdate, chip_number, license_number, owner_id, race_id, species_id) values ('Vulture, lappet-faced', '2/9/2005', 100, 52, 1, 1, 1);
insert into pet (name, birthdate, chip_number, license_number, owner_id, race_id, species_id) values ('Capybara', '2/17/2018', 6, 72, 1, 1, 1);
insert into pet (name, birthdate, chip_number, license_number, owner_id, race_id, species_id) values ('Chipmunk, least', '2/20/2009', 69, 64, 1, 1, 1);
insert into consultation (case_history, diagnosis, observations, treatment, heart_rhythm, temperature, weight, pet_id, veterinarian_id) values ('Maecenas rhoncus aliquam lacus.', 'Ut at dolor quis odio consequat varius.', 'Maecenas tincidunt lacus at velit.', 'Proin eu mi.', 7.45, 2.1, 79.04, 1, 1);
