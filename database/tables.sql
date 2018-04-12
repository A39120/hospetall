USE HosPetAll;

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
	Id INTEGER PRIMARY KEY IDENTITY(1,1), 
	GivenName VARCHAR(256) NOT NULL,
	FamilyName VARCHAR(256) NOT NULL,
	Address VARCHAR(1024) UNIQUE,
	PostalCode VARCHAR(16), 
	Telephone VARCHAR(12) UNIQUE, 
	TelephoneAlternative VARCHAR(12),
	Email VARCHAR(256) UNIQUE, 
	Nif INT, 
	CONSTRAINT ct_mphone CHECK (Telephone > 0 AND Telephone <= 999999999)
)

-- Application User
CREATE TABLE UserClient(
	Id INTEGER REFERENCES Client(Id), 
	Username VARCHAR(256) UNIQUE, 
	RegisterDate DATE NOT NULL,
--	Hashpwd VARCHAR(512) NOT NULL, 
	ClientEmail VARCHAR(256) REFERENCES Client(Email) NOT NULL
)

CREATE TABLE Species(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	Name VARCHAR(31)
)

CREATE TABLE Race(
	Species INTEGER REFERENCES Species(Id),
	Id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	Name VARCHAR(31) UNIQUE
)

-- Check http://www.lpda.pt/legislacao/#Estatuto%20jur%C3%ADdico%20dos%20animais
-- Retirar dai Deveres do Dono
-- https://www.sira.com.pt/
-- 
CREATE TABLE Pet(
	Owner INTEGER REFERENCES Client(Id),
	Id INTEGER IDENTITY(1,1) UNIQUE NOT NULL,
	Name VARCHAR(255) NOT NULL,
	Race INTEGER REFERENCES Race(Id),
	Species INTEGER REFERENCES Species(Id) NOT NULL,
	Birthdate DATE, -- Importante para a vacinação de algumas especies	
	ChipNumber INT, 
	LicenseNumber INT
)

CREATE TABLE MedicalProcedure(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY,
	Pet REFERENCES Pet(Id) NOT NULL, 
	CaseHistory TEXT, 
	Diagnosis TEXT, 
	Treatment TEXT,
	Observations TEXT
)

CREATE TABLE Veterinarian(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	Name VARCHAR(255) NOT NULL
)

CREATE TABLE Nurse(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY, 
	Name VARCHAR(255) NOT NULL 
)

CREATE TABLE Consultation(
	Id INTEGER REFERENCES MedicalProcedure(Id) PRIMARY KEY, 
	VeterinarianId REFERENCES Veterinarian(Id) NOT NULL, 
	--Weight FLOAT, 
)
