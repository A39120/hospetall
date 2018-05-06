USE HosPetAll;

CREATE TABLE Person(
	id INTEGER PRIMARY KEY IDENTITY(1,1),
	given_name VARCHAR(256) NOT NULL,
	family_name VARCHAR(256) NOT NULL,
	telephone VARCHAR(12) UNIQUE,
	email VARCHAR(256) UNIQUE
)

CREATE TABLE Account(
	person INTEGER FOREIGN KEY REFERENCES Person(id), 
	username VARCHAR(256) PRIMARY KEY, 
	register_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	password BINARY(512) NOT NULL, 
)

-- Entities that extend from Person
CREATE TABLE Client(
	id INTEGER PRIMARY KEY REFERENCES Person(id),
	address VARCHAR(512) UNIQUE,
	postal_code VARCHAR(16), 
	telephone_alternative VARCHAR(12),
	nif INT, 
	other TEXT
)

CREATE TABLE Veterinarian(
	id INTEGER PRIMARY KEY REFERENCES Person(id)
)

CREATE TABLE Nurse(
	id INTEGER PRIMARY KEY REFERENCES Person(id)
)

CREATE TABLE Receptionist(
	id INTEGER PRIMARY KEY REFERENCES Person(id) 
)

-- Animal related
CREATE TABLE Species(
	id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	name VARCHAR(31)
)

CREATE TABLE Race(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(31) UNIQUE
)

 
CREATE TABLE Pet(
	owner INTEGER REFERENCES Client(id),
	id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	name VARCHAR(255) NOT NULL,
	race INTEGER REFERENCES Race(id),
	species INTEGER REFERENCES Species(id) NOT NULL,
	birthdate DATE, -- Importante para a vacinação de algumas especies	
	chip_number INT, 
	license_number INT
)

-- Procedure Tables
CREATE TABLE MedicalProcedure(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	pet INTEGER FOREIGN KEY REFERENCES Pet(id) NOT NULL, 
	case_history TEXT, -- Anamnese 
	diagnosis TEXT, 
	treatment TEXT,
	observations TEXT
)

CREATE TABLE Consultation(
	id INTEGER REFERENCES MedicalProcedure(id) PRIMARY KEY, 
	veterinarian_id INTEGER REFERENCES Veterinarian(id) NOT NULL, 
	weight FLOAT, 
	temperature FLOAT, 
	heart_rythim FLOAT, 
)

CREATE TABLE Treatment(
	id INTEGER REFERENCES MedicalProcedure(id) PRIMARY KEY, 
	nurse_id INTEGER FOREIGN KEY REFERENCES Nurse(id) NOT NULL
)
---

CREATE TABLE Product(
	code VARCHAR(9) PRIMARY KEY, 
	name VARCHAR(255) UNIQUE NOT NULL, 
	type VARCHAR(32) NOT NULL,
	quantity FLOAT NOT NULL,
	quantity_measurement VARCHAR(8) NOT NULL, -- se está em mL ou mG ou L ou gramas etc. etc.
	stock INTEGER DEFAULT 0
)

CREATE TABLE Supplier(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	name VARCHAR(255), 
	phone_number VARCHAR(12),
	website_url VARCHAR(255),
	email VARCHAR(255)
)

CREATE TABLE Supplier_Product_Relationship(
	supplier_id INTEGER REFERENCES Supplier(id),
	product_id VARCHAR(9) REFERENCES Product(Code),
	price FLOAT NOT NULL
)

CREATE TABLE MedicalProcedure_ProductsUsed(
	procedure_id INTEGER REFERENCES MedicalProcedure(id) NOT NULL,
	product_code VARCHAR(9) REFERENCES Product(Code) NOT NULL
)

CREATE TABLE SideEffects(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	effect TEXT
)

CREATE TABLE Product_SideEffects(
	product_code VARCHAR(9) REFERENCES Product(Code) NOT NULL,
	side_effect_id INTEGER REFERENCES SideEffects(id) NOT NULL
)

CREATE TABLE Ingredient(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	ingredient_name VARCHAR(32) UNIQUE NOT NULL
)

CREATE TABLE Product_Ingredient(
	ingredient_id INTEGER REFERENCES Ingredient(id) NOT NULL,
	product_code VARCHAR(9) REFERENCES Product(Code) NOT NULL,
	ingredient_quantity FLOAT
)

