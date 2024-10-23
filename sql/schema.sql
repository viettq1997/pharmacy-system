CREATE TABLE EMPLOYEE (
    E_ID VARCHAR(36) PRIMARY KEY,
    E_Username VARCHAR(50) NOT NULL UNIQUE,
    E_Fname VARCHAR(255) NOT NULL,
    E_Lname VARCHAR(255),
    E_Bdate DATE,
    E_Age INT,
    E_Sex CHAR(1) NOT NULL,
    E_Type VARCHAR(50) NOT NULL,
    E_Jdate DATE NOT NULL,
    E_Add VARCHAR(255),
    E_Mail VARCHAR(255),
    E_Phno VARCHAR(20) NOT NULL,
    E_Sal DECIMAL(10, 2),
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE SUPPLIER (
    Sup_ID VARCHAR(36) PRIMARY KEY,
    Sup_Name VARCHAR(255) NOT NULL,
    Sup_Add VARCHAR(255) NOT NULL,
    Sup_Phno VARCHAR(20) NOT NULL,
    Sup_Mail VARCHAR(255),
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE CUSTOMER (
    C_ID VARCHAR(36) NOT NULL PRIMARY KEY,
    C_Fname VARCHAR(255) NOT NULL,
    C_Lname VARCHAR(255),
    C_Age INT,
    C_Sex CHAR(1) NOT NULL,
    C_Phno VARCHAR(20) NOT NULL,
    C_Mail VARCHAR(255),
    C_Points NUMERIC,
    created_date TIMESTAMP NOT NULL UNIQUE,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36),
    UNIQUE(C_Phno)
);

CREATE TABLE MED_CATEGORY (
    Cat_ID VARCHAR(36) PRIMARY KEY,
    Cat_Name VARCHAR(255) NOT NULL,
    Cat_Description VARCHAR(1000),
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE MED (
    Med_ID VARCHAR(36) PRIMARY KEY,
    Med_Name VARCHAR(255) NOT NULL,
    Med_Price DECIMAL(10, 2) NOT NULL,
    Cat_ID VARCHAR(36) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE PURCHASE (
    P_ID VARCHAR(36) PRIMARY KEY,
    Med_ID VARCHAR(36) NOT NULL,
    Sup_ID VARCHAR(36) NOT NULL,
    P_Qty INT NOT NULL,
    P_Cost DECIMAL(10, 2) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE SALE (
    Sale_ID VARCHAR(36) NOT NULL PRIMARY KEY,
    Total_Amt DECIMAL(10, 2),
    C_ID VARCHAR(36) -- allow anonymous customer
);

CREATE TABLE SALE_LOG (
    Sale_ID VARCHAR(36) NOT NULL,
    use_point BOOLEAN,
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36),
    PRIMARY KEY (Sale_ID, created_date)
) PARTITION BY RANGE (created_date);

CREATE TABLE sale_log_2024 PARTITION OF SALE_LOG
  FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');

CREATE TABLE sale_log_2025 PARTITION OF SALE_LOG
  FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');

CREATE TABLE sale_log_default PARTITION OF SALE_LOG DEFAULT;

CREATE TABLE SALE_ITEM (
    I_ID VARCHAR(36),
    Sale_ID VARCHAR(36),
    Sale_Qty INT NOT NULL,
    Tot_Price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (I_ID, Sale_ID),
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE LOCATION_RACK (
    LR_ID VARCHAR(36) PRIMARY KEY,
    LR_POSITION VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

CREATE TABLE INVENTORY (
    I_ID VARCHAR(36) PRIMARY KEY,
    Med_ID VARCHAR(36) NOT NULL,
    LR_ID VARCHAR(36) NOT NULL,
    I_Qty INT NOT NULL,
    Mfg_Date DATE NOT NULL,
    Exp_Date DATE NOT NULL,
    created_date TIMESTAMP NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36),
    UNIQUE (Med_ID, Mfg_Date)
);

CREATE TABLE CUSTOMER_POINT_CONFIG (
    CPC_ID VARCHAR(36) PRIMARY KEY,
    CPC_Ratio NUMERIC NOT NULL,
    updated_date TIMESTAMP,
    updated_by VARCHAR(36)
);

INSERT INTO CUSTOMER_POINT_CONFIG VALUES('358dbac4-8270-4521-9aed-db3260b3db3e', 0.00001);

INSERT INTO EMPLOYEE VALUES('1b623830-1de1-46a4-ad7f-c553fcf0b6ca', 'admin', 'admin', 'admin', null, null, 'M', 'admin', '2024-01-01', null, null, '0974995189', null, '2024-01-01', 'admin', null, null);