CREATE TABLE client (
  id INT IDENTITY NOT NULL,
  email VARCHAR(255),
  family_name VARCHAR(255),
  given_name VARCHAR(255),
  nif VARCHAR(9),
  telephone VARCHAR(255),
  address VARCHAR(255),
  other VARCHAR(255),
  postal_code VARCHAR(255),
  telephone_alternative VARCHAR(255),
  PRIMARY KEY (id));

CREATE TABLE consultation (id INT IDENTITY NOT NULL,
	 case_history VARCHAR(255),
	 date datetime2,
	 diagnosis VARCHAR(255),
	 observations VARCHAR(255),
	 treatment VARCHAR(255),
	 heart_rhythm float NOT NULL,
	 temperature float NOT NULL,
	 weight float NOT NULL,
	 pet_id INT NOT NULL,
	 veterinarian_id INT NOT NULL,
	 PRIMARY KEY (id));

CREATE TABLE consultation_schedule (id INT IDENTITY NOT NULL,
	 calendarPeriod BIGINT NOT NULL,
	 start_date BIGINT NOT NULL,
	 client_id INT,
	 veterinarian_id INT,
	 PRIMARY KEY (id));

CREATE TABLE nurse (id INT IDENTITY NOT NULL,
	 email VARCHAR(255),
	 family_name VARCHAR(255),
	 given_name VARCHAR(255),
	 nif VARCHAR(9),
	 telephone VARCHAR(255),
	 PRIMARY KEY (id));

CREATE TABLE pet (id INT IDENTITY NOT NULL,
	 name VARCHAR(255),
	 birthdate DATE,
	 chip_number INT NOT NULL,
	 license_number INT NOT NULL,
	 owner_id INT,
	 race_id INT,
	 species_id INT,
	 PRIMARY KEY (id));

CREATE TABLE product (code VARCHAR(255) NOT NULL,
	 name VARCHAR(255),
	 quantity FLOAT NOT NULL,
	 quantity_measurement VARCHAR(255),
	 stock INT NOT NULL,
	 type VARCHAR(255),
	 PRIMARY KEY (code));

CREATE TABLE race (id INT IDENTITY NOT NULL,
	 name VARCHAR(255),
	 PRIMARY KEY (id));

CREATE TABLE schedule (id INT IDENTITY NOT NULL,
	 date DATETIME2 NOT NULL,
	 type SMALLINT,
	 PRIMARY KEY (id));

CREATE TABLE species (id INT IDENTITY NOT NULL,
	 name VARCHAR(255),
	 PRIMARY KEY (id));

CREATE TABLE treatment (id INT IDENTITY NOT NULL,
	 case_history VARCHAR(255),
	 date datetime2,
	 diagnosis VARCHAR(255),
	 observations VARCHAR(255),
	 treatment VARCHAR(255),
	 pet_id INT NOT NULL,
	 nurse_id INT NOT NULL,
	 PRIMARY KEY (id));

CREATE TABLE treatment_schedule (id INT IDENTITY NOT NULL,
	 calendarPeriod BIGINT NOT NULL,
	 start_date BIGINT NOT NULL,
	 client_id INT,
	 nurse_id INT,
	 PRIMARY KEY (id));

CREATE TABLE veterinarian (id INT IDENTITY NOT NULL,
	 email VARCHAR(255),
	 family_name VARCHAR(255),
	 given_name VARCHAR(255),
	 nif VARCHAR(9),
	 telephone VARCHAR(255),
	 PRIMARY KEY (id));
