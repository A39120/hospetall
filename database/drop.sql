USE HosPetAll
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

IF OBJECT_ID('dbo.Treatment') IS NOT NULL
	DROP TABLE dbo.Treatment

IF OBJECT_ID('dbo.Consultation') IS NOT NULL
	DROP TABLE dbo.Consultation

IF OBJECT_ID('dbo.MedicalProcedure') IS NOT NULL
	DROP TABLE dbo.MedicalProcedure

IF OBJECT_ID('dbo.Pet') IS NOT NULL 
	DROP TABLE dbo.Pet; 
  
IF OBJECT_ID('dbo.Race') IS NOT NULL
	DROP TABLE dbo.Race

IF OBJECT_ID('dbo.Species') IS NOT NULL
	DROP TABLE dbo.Species

IF OBJECT_ID('dbo.Client') IS NOT NULL
	DROP TABLE dbo.Client

IF OBJECT_ID('dbo.Receptionist') IS NOT NULL
	DROP TABLE dbo.Receptionist

IF OBJECT_ID('dbo.Nurse') IS NOT NULL
	DROP TABLE dbo.Nurse

IF OBJECT_ID('dbo.Veterinarian') IS NOT NULL
	DROP TABLE dbo.Veterinarian

IF OBJECT_ID('dbo.Account') IS NOT NULL
	DROP TABLE dbo.Account

IF OBJECT_ID('dbo.Person') IS NOT NULL
	DROP TABLE dbo.Person

