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
	Id INTEGER PRIMARY KEY IDENTITY(1,1), 
	GivenName VARCHAR(256) NOT NULL,
	FamilyName VARCHAR(256) NOT NULL,
	Address VARCHAR(1024) UNIQUE,
	PostalCode VARCHAR(16), 
	Telephone VARCHAR(12) UNIQUE, 
	TelephoneAlternative VARCHAR(12),
	Email VARCHAR(256) UNIQUE, 
	Nif INT, 
	Other TEXT,
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
	Pet INTEGER REFERENCES Pet(Id) NOT NULL, 
	CaseHistory TEXT, -- Anamnese 
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

CREATE TABLE MedicalProcedureConsultation(
	Id INTEGER REFERENCES MedicalProcedure(Id) PRIMARY KEY, 
	VeterinarianId INTEGER REFERENCES Veterinarian(Id) NOT NULL, 
	Weight FLOAT, 
	Temperature FLOAT, 
	HeartRythim FLOAT, 
	-- Completar: Outros factores fisicos não obrigatórios
)

CREATE TABLE MedicalProcedureTreatment(
	Id INTEGER REFERENCES MedicalProcedure(Id) PRIMARY KEY, 
	NurseId INTEGER REFERENCES Nurse(Id) NOT NULL
)

CREATE TABLE Product(
	Code VARCHAR(9) PRIMARY KEY, 
	Name VARCHAR(255) UNIQUE NOT NULL, 
	Type VARCHAR(32) NOT NULL,
	Quantity FLOAT NOT NULL,
	QuantityMeasurement VARCHAR(8) NOT NULL, -- se está em mL ou mG ou L ou gramas etc. etc.
	Stock INTEGER DEFAULT 0
)

CREATE TABLE Supplier(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(255), 
	PhoneNumber VARCHAR(12),
	WebsiteUrl VARCHAR(255),
	Email VARCHAR(255)
)

CREATE TABLE Supplier_Product_Relationship(
	SupplierId INTEGER REFERENCES Supplier(Id),
	ProductId VARCHAR(9) REFERENCES Product(Code),
	Price FLOAT NOT NULL
)

CREATE TABLE MedicalProcedure_ProductsUsed(
	ProcedureId INTEGER REFERENCES MedicalProcedure(Id) NOT NULL,
	ProductCode VARCHAR(9) REFERENCES Product(Code) NOT NULL
)

-- Pensar melhor no que vêm a seguir
CREATE TABLE SideEffects(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY,
	Effect TEXT
)

CREATE TABLE Product_SideEffects(
	ProductCode VARCHAR(9) REFERENCES Product(Code) NOT NULL,
	SideEffectId INTEGER REFERENCES SideEffects(Id) NOT NULL
)

CREATE TABLE Ingredient(
	Id INTEGER IDENTITY(1,1) PRIMARY KEY,
	IngredientName VARCHAR(32) UNIQUE NOT NULL
)

CREATE TABLE Product_Ingredient(
	IngredientId INTEGER REFERENCES Ingredient(Id) NOT NULL,
	ProductCode VARCHAR(9) REFERENCES Product(Code) NOT NULL,
	IngredientQuantity FLOAT
)

