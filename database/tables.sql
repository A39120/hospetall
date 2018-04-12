
CREATE TABLE Client(
	id INTEGER PRIMARY KEY IDENTITY(1,1), 
	given_name VARCHAR(256) NOT NULL,
	family_name VARCHAR(256) NOT NULL,
	address VARCHAR(1024) UNIQUE,
	postal_code VARCHAR(16), 
	telephone INT UNIQUE, 
	telephone_alternative INT,
	email VARCHAR(256) UNIQUE, 
	-- nif INT 
	CONSTRAINT ct_mphone CHECK (telephone > 0 AND telephone <= 999999999)
)

CREATE TABLE UserClient(
	id INTEGER REFERENCES Client(id), 
	username VARCHAR(256) UNIQUE, 
	register_date DATE NOT NULL,
	hashpwd VARCHAR(512) NOT NULL, 
	client_email VARCHAR(256) REFERENCES Client(email)
)

CREATE TABLE Species(
	id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	name VARCHAR(31)
)

CREATE TABLE Race(
	species_id INTEGER REFERENCES Species(id),
	race_id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	name VARCHAR(31) UNIQUE
)

-- Check http://www.lpda.pt/legislacao/#Estatuto%20jur%C3%ADdico%20dos%20animais
-- Retirar dai Deveres do Dono
-- https://www.sira.com.pt/
-- 
CREATE TABLE Pet(
	owner_id INTEGER REFERENCES Client(id),
	p_id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	p_name VARCHAR(255) NOT NULL,
	race_id INTEGER REFERENCES Race(id)
	birth_date DATE -- Importante para a vacinação de algumas especies	
)
