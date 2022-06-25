DROP TABLE IF EXISTS Meals CASCADE;
DROP TABLE IF EXISTS Products CASCADE;
DROP TABLE IF EXISTS Menu_content CASCADE;
DROP TABLE IF EXISTS Shelves CASCADE;
DROP TABLE IF EXISTS Storages CASCADE; 
DROP TABLE IF EXISTS Deliveries CASCADE;
DROP TABLE IF EXISTS Working_time CASCADE;
DROP TABLE IF EXISTS Workers CASCADE;
DROP TABLE IF EXISTS Delivery_companies CASCADE;
DROP TABLE IF EXISTS Restaurants CASCADE;
DROP TABLE IF EXISTS Logs CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Meals_products CASCADE;

create table Logs (
	Log_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Created timestamp NOT NULL,
	Message varchar(1028) NOT NULL,
	Controller_name varchar(128),
	Status varchar(16)
);

create table Users (
	User_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Username varchar(128) NOT NULL,
	Password varchar(128) NOT NULL,
	Email varchar(128) NOT NULL,
	Role varchar(64) NOT NULL,
	Enabled boolean DEFAULT NULL,
	Name varchar(128) NOT NULL,
	Surname varchar(128) NOT NULL,
	Personal_code nchar(11) NOT NULL
);

create table Delivery_companies (
	Delivery_company_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name varchar(128) NOT NULL,
	Registration_number varchar(11) NOT NULL
);

create table Restaurants (
	Restaurant_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name varchar(128) NOT NULL,
	Registration_number varchar(11) NOT NULL,
	Address varchar(256) NOT NULL,
	Administrator_ID integer REFERENCES Users(User_ID)
);

create table Workers (
	Worker_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name varchar(128) NOT NULL,
	Surname varchar(128) NOT NULL,
	Personal_code nchar(11) NOT NULL,
	Occupation varchar(256) default 'Storage worker',
	Restaurant_ID integer references Restaurants(Restaurant_ID)
);

create table Working_time (
	Working_time_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Shift_start Time NOT NULL,
	Shift_end Time NOT NULL,
	Day_of_week int NOT NULL,
	Restaurant_ID integer references Restaurants(Restaurant_ID)
);

create table Deliveries (
	Delivery_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Price MONEY NOT NULL,
	Delivery_time Date NOT NULL,
	Delivery_company_ID integer references Delivery_companies(Delivery_company_ID),
	Administrator_ID integer references Users(User_ID),
	Restaurant_ID integer references Restaurants(Restaurant_ID)
);

create table Storages (
	Storage_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name varchar(128) NOT NULL,
	Size numeric(8,3),
	Restaurant_ID integer references Restaurants(Restaurant_ID)
);

create table Shelves (
	Shelf_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name varchar(128) NOT NULL,
	Storage_type varchar(16) NOT NULL,
	Allocated_space numeric(8,1) NOT NULL,
	Storage_ID integer references Storages(Storage_ID)
);

create table Menu_content (
	Menu_content_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Price MONEY NOT NULL,
	Name varchar(128) NOT NULL,
	Serving_date Date,
	Number_of_calories numeric(7,2),
	Restaurant_ID integer references Restaurants(Restaurant_ID)
);

create table Products (
	Product_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Amount_left numeric(8,2) NOT NULL,
	Date_of_expiry Date NOT NULL,
	Storage_type varchar(16) NOT NULL,
	Name varchar(128) NOT NULL,
	Shelf_ID integer references Shelves(Shelf_ID),
	Delivery_ID integer references Deliveries(Delivery_ID)
);

create table Meals (
	Meal_ID integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Amount numeric(8,4) NOT NULL,
	Restaurant_ID integer references Restaurants(Restaurant_ID),
	Menu_content_ID integer references Menu_content(Menu_content_ID)
);

create table Meals_products (
	Product_ID integer references Products(Product_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	Meal_ID integer references Meals(Meal_ID) ON UPDATE CASCADE,
	Amount numeric NOT NULL,
	CONSTRAINT Meal_product_ID PRIMARY KEY (Product_ID, Meal_ID)
)