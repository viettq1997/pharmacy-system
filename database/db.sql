--CREATE TABLE EMPLOGIN (
--    E_Username VARCHAR(255) PRIMARY KEY,
--    E_Password VARCHAR(255) NOT NULL,
--    E_ID VARCHAR(50) NOT NULL
--);

CREATE TABLE EMPLOYEE (
    E_ID VARCHAR(50) PRIMARY KEY,
    E_Fname VARCHAR(255) NOT NULL,
    E_Lname VARCHAR(255) NOT NULL,
    E_Bdate DATE,
    E_Age INT,
    E_Sex CHAR(1) NOT NULL,
    E_Type VARCHAR(50) NOT NULL,
    E_Jdate DATE NOT NULL,
    E_Add VARCHAR(255),
    E_Mail VARCHAR(255),
    E_Phno VARCHAR(20) NOT NULL,
    E_Sal DECIMAL(10, 2),
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);

CREATE TABLE SUPPLIERS (
    Sup_ID VARCHAR(50) PRIMARY KEY,
    Sup_Name VARCHAR(255) NOT NULL,
    Sup_Add VARCHAR(255) NOT NULL,
    Sup_Phno VARCHAR(20) NOT NULL,
    Sup_Mail VARCHAR(255),
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);

CREATE TABLE CUSTOMER (
    C_ID VARCHAR(50) PRIMARY KEY,
    C_Fname VARCHAR(255) NOT NULL,
    C_Lname VARCHAR(255) NOT NULL,
    C_Age INT NOT NULL,
    C_Sex CHAR(1) NOT NULL,
    C_Phno VARCHAR(20) NOT NULL,
    C_Mail VARCHAR(255),
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);

CREATE TABLE MEDS (
    Med_ID VARCHAR(50) PRIMARY KEY,
    Med_Name VARCHAR(255) NOT NULL,
    Med_Qty INT NOT NULL,
    Med_Price DECIMAL(10, 2) NOT NULL,
    Category VARCHAR(255) NOT NULL,
    Location_Rack VARCHAR(50) NOT NULL,
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);

CREATE TABLE PURCHASE (
    P_ID VARCHAR(50) PRIMARY KEY,
    Med_ID VARCHAR(50) NOT NULL,
    Sup_ID VARCHAR(50) NOT NULL,
    P_Qty INT NOT NULL,
    P_Cost DECIMAL(10, 2) NOT NULL,
    Pur_Date DATE NOT NULL,
    Mfg_Date DATE NOT NULL,
    Exp_Date DATE NOT NULL,
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);

CREATE TABLE SALES (
    Sale_ID VARCHAR(50) PRIMARY KEY,
    S_Date DATE NOT NULL,
    S_Time TIME NOT NULL,
    Total_Amt DECIMAL(10, 2),
    C_ID VARCHAR(50) NOT NULL,
    E_ID VARCHAR(50) NOT NULL,
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);

CREATE TABLE SALES_ITEMS (
    Med_ID VARCHAR(50),
    Sale_ID VARCHAR(50),
    Sale_Qty INT NOT NULL,
    Tot_Price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (Med_ID, Sale_ID),
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_date DATE,
    updated_by VARCHAR(50)
);


