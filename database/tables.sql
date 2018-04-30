USE HosPetAll;

IF OBJECT_ID('dbo.Product_SideEffects') IS NOT NULL
	DROP TABLE dbo.Product_SideEffects


IF OBJECT_ID('dbo.SideEffects') IS NOT NULL
	DROP TABLE dbo.SideEffects

IF OBJECT_ID('dbo.Product_Ingredient') IS NOT NULL
	DROP TABLE dbo.Product_Ingredient

IF OBJECT_ID('dbo.Ingredient') IS NOT NULL
	DROP TABLE dbo.Ingredient

IF OBJECT_ID('dbo.MedicalProcedure_ProductsUsed') IS NOT NULL
	DROP TABLE dbo.MedicalProcedure_ProductsUsed

IF OBJECT_ID('dbo.Supplier_Product_Relationship') IS NOT NULL
	DROP TABLE dbo.Supplier_Product_Relationship

IF OBJECT_ID('dbo.Supplier') IS NOT NULL
	DROP TABLE dbo.Supplier

IF OBJECT_ID('dbo.Product') IS NOT NULL
	DROP TABLE dbo.Product

IF OBJECT_ID('dbo.MedicalProcedureTreatment') IS NOT NULL
	DROP TABLE dbo.MedicalProcedureTreatment

IF OBJECT_ID('dbo.MedicalProcedureConsultation') IS NOT NULL
	DROP TABLE dbo.MedicalProcedureConsultation

IF OBJECT_ID('dbo.Veterinarian') IS NOT NULL
	DROP TABLE dbo.Veterinarian

IF OBJECT_ID('dbo.Nurse') IS NOT NULL
	DROP TABLE dbo.Nurse

IF OBJECT_ID('dbo.MedicalProcedure') IS NOT NULL
	DROP TABLE dbo.MedicalProcedure

IF OBJECT_ID('dbo.Pet') IS NOT NULL 
	DROP TABLE dbo.Pet; 
  
IF OBJECT_ID('dbo.Race') IS NOT NULL
	DROP TABLE dbo.Race

IF OBJECT_ID('dbo.Species') IS NOT NULL
	DROP TABLE dbo.Species

IF OBJECT_ID('dbo.UserClient') IS NOT NULL 
	DROP TABLE dbo.UserClient; 

IF OBJECT_ID('dbo.Client') IS NOT NULL
	DROP TABLE dbo.Client

CREATE TABLE Client(
	id INTEGER PRIMARY KEY IDENTITY(1,1), 
	given_name VARCHAR(256) NOT NULL,
	family_name VARCHAR(256) NOT NULL,
	address VARCHAR(1024) UNIQUE,
	postal_code VARCHAR(16), 
	telephone VARCHAR(12) UNIQUE, 
	telephone_alternative VARCHAR(12),
	email VARCHAR(256) UNIQUE, 
	nif INT, 
	other TEXT,
	CONSTRAINT ct_mphone CHECK (Telephone > 0 AND Telephone <= 999999999)
)

-- Application User
CREATE TABLE UserClient(
	id INTEGER REFERENCES Client(Id), 
	username VARCHAR(256) UNIQUE, 
	register_date DATE NOT NULL,
	password VARCHAR(512) NOT NULL, 
	client_email VARCHAR(256) REFERENCES Client(Email) NOT NULL
)

CREATE TABLE Species(
	id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	name VARCHAR(31)
)

CREATE TABLE Race(
	species INTEGER REFERENCES Species(Id),
	id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	name VARCHAR(31) UNIQUE
)

 
CREATE TABLE Pet(
	owner INTEGER REFERENCES Client(Id),
	id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	name VARCHAR(255) NOT NULL,
	race INTEGER REFERENCES Race(Id),
	species INTEGER REFERENCES Species(Id) NOT NULL,
	birthdate DATE, -- Importante para a vacinação de algumas especies	
	chip_number INT, 
	license_number INT
)

CREATE TABLE MedicalProcedure(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	pet INTEGER REFERENCES Pet(Id) NOT NULL, 
	case_history TEXT, -- Anamnese 
	diagnosis TEXT, 
	treatment TEXT,
	observations TEXT
)

CREATE TABLE Veterinarian(
	id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	name VARCHAR(255) NOT NULL
)

CREATE TABLE Nurse(
	id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	name VARCHAR(255) NOT NULL 
)

CREATE TABLE MedicalProcedureConsultation(
	id INTEGER REFERENCES MedicalProcedure(Id) PRIMARY KEY, 
	veterinarian_id INTEGER REFERENCES Veterinarian(Id) NOT NULL, 
	weight FLOAT, 
	temperature FLOAT, 
	heart_rythim FLOAT, 
	-- Completar: Outros factores fisicos não obrigatórios
)

CREATE TABLE MedicalProcedureTreatment(
	id INTEGER REFERENCES MedicalProcedure(Id) PRIMARY KEY, 
	nurse_id INTEGER REFERENCES Nurse(Id) NOT NULL
)

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
	supplier_id INTEGER REFERENCES Supplier(Id),
	product_id VARCHAR(9) REFERENCES Product(Code),
	price FLOAT NOT NULL
)

CREATE TABLE MedicalProcedure_ProductsUsed(
	procedure_id INTEGER REFERENCES MedicalProcedure(Id) NOT NULL,
	product_code VARCHAR(9) REFERENCES Product(Code) NOT NULL
)

-- Pensar melhor no que vêm a seguir
CREATE TABLE SideEffects(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	effect TEXT
)

CREATE TABLE Product_SideEffects(
	product_code VARCHAR(9) REFERENCES Product(Code) NOT NULL,
	side_effect_id INTEGER REFERENCES SideEffects(Id) NOT NULL
)

CREATE TABLE Ingredient(
	id INTEGER IDENTITY(1,1) PRIMARY KEY,
	ingredient_name VARCHAR(32) UNIQUE NOT NULL
)

CREATE TABLE Product_Ingredient(
	ingredient_id INTEGER REFERENCES Ingredient(Id) NOT NULL,
	product_code VARCHAR(9) REFERENCES Product(Code) NOT NULL,
	ingredient_quantity FLOAT
)

