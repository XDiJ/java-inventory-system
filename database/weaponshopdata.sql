-- -- Database creation
-- CREATE DATABASE IF NOT EXISTS WeaponShopDB;
USE WeaponShopDB;

-- Admin Table
CREATE TABLE Admin (
    Admin_ID VARCHAR(4) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Username VARCHAR(30) NOT NULL,
    Password VARCHAR(30) NOT NULL,
    Role VARCHAR(20) NOT NULL
);

-- Customer Table
CREATE TABLE Customer (
    Customer_ID VARCHAR(4) PRIMARY KEY,
    F_Name VARCHAR(30) NOT NULL,
    L_Name VARCHAR(30) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Address VARCHAR(100) NOT NULL,
    License_ID VARCHAR(20) NOT NULL
);

-- Customer_phone Table
CREATE TABLE Customer_phone (
    Phone VARCHAR(10) NOT NULL,
    Customer_id VARCHAR(4) NOT NULL,
    PRIMARY KEY (Phone, Customer_id),
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_ID)
);

-- Weapons Table
CREATE TABLE Weapons (
    Name VARCHAR(50) NOT NULL,
    Weapon_ID VARCHAR(4) PRIMARY KEY,
    Selling_price DECIMAL(10,2) NOT NULL,
    Rental_price DECIMAL(10,2) NOT NULL,
    Availability_status VARCHAR(20) NOT NULL,
    License_requirement VARCHAR(3) NOT NULL,
    Caliber VARCHAR(10) NOT NULL,
    Type VARCHAR(20) NOT NULL,
    Admin_id VARCHAR(4) NOT NULL,
    FOREIGN KEY (Admin_id) REFERENCES Admin(Admin_ID)
);

-- BackgroundCheck Table
CREATE TABLE BackgroundCheck (
    Customer_ID VARCHAR(4) NOT NULL,
    Background_ID VARCHAR(5) PRIMARY KEY,
    Status VARCHAR(10) NOT NULL,
    Check_date DATE NOT NULL,
    FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID)
);

-- Rental Table
CREATE TABLE Rental (
    Rental_ID VARCHAR(4) PRIMARY KEY,
    Total_Cost DECIMAL(10,2) NOT NULL,
    Return_status VARCHAR(15) NOT NULL,
    Start_Date DATE NOT NULL,
    End_Date DATE NOT NULL,
    Customer_id VARCHAR(4) NOT NULL,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_ID)
);

-- Rental_weapon Table
CREATE TABLE Rental_weapon (
    Rental_ID VARCHAR(4) NOT NULL,
    Weapon_ID VARCHAR(4) NOT NULL,
    Quantity INT NOT NULL,
    PRIMARY KEY (Rental_ID, Weapon_ID),
    FOREIGN KEY (Rental_ID) REFERENCES Rental(Rental_ID),
    FOREIGN KEY (Weapon_ID) REFERENCES Weapons(Weapon_ID)
);

-- Payment Table
CREATE TABLE Payment (
    Payment_ID VARCHAR(4) PRIMARY KEY,
    Transaction_ID VARCHAR(4) NOT NULL,
    Payment_Method VARCHAR(20) NOT NULL,
    Payment_Status VARCHAR(15) NOT NULL,
    Payment_Date DATE NOT NULL
);

-- Transaction Table
CREATE TABLE Transaction (
    Transaction_ID VARCHAR(4) PRIMARY KEY,
    Customer_ID VARCHAR(4) NOT NULL,
    Weapon_ID VARCHAR(4) NOT NULL,
    Transaction_Type VARCHAR(10) NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Date DATE NOT NULL,
    FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID),
    FOREIGN KEY (Weapon_ID) REFERENCES Weapons(Weapon_ID)
);

-- Insert data into Admin table
INSERT INTO Admin (Admin_ID, Name, Username, Password, Role) VALUES
('A101', 'Hassan Ahmed', 'hassan', 'pass1', 'Manager'),
('A102', 'Abbas Mohmmed', 'abbas', 'pass2', 'Supervisor'),
('A103', 'Mohmmed Ali', 'mohmmed', 'pass3', 'Sales'),
('A104', 'Hussain Ibraheem', 'hussain', 'pass4', 'Technician');

-- Insert data into Customer table
INSERT INTO Customer (Customer_ID, F_Name, L_Name, Email, Address, License_ID) VALUES
('C001', 'Mohammed', 'Ahmed', 'mohammed.ahmed@example.com', 'Riyadh, Al-Nakheel District', 'LSA-2023-001'),
('C002', 'Abdullah', 'Al-Khaled', 'abdullah.alkhaled@example.com', 'Jeddah, Al-Safa District', 'LSA-2023-002'),
('C003', 'Khaled', 'Saad', 'khaled.saad@example.com', 'Dammam, Al-Jalawiya District', 'LSA-2023-003'),
('C004', 'Saud', 'Nasser', 'saud.nasser@example.com', 'Riyadh, Al-Safarat District', 'LSA-2023-004'),
('C005', 'Fahad', 'Rashid', 'fahad.rashid@example.com', 'Makkah, Al-Aziziyah District', 'LSA-2023-005'),
('C006', 'Nasser', 'Ali', 'nasser.ali@example.com', 'Madinah, Al-Aqiq District', 'LSA-2023-006'),
('C007', 'Badr', 'Mohammed', 'badr.mohammed@example.com', 'Khobar, Al-Jisr District', 'LSA-2023-007'),
('C008', 'Turki', 'Ibrahim', 'turki.ibrahim@example.com', 'Riyadh, Al-Maather District', 'LSA-2023-008'),
('C009', 'Majed', 'Abdulrahman', 'majed.abdulrahman@example.com', 'Jeddah, Al-Rawdah District', 'LSA-2023-009'),
('C010', 'Abdulaziz', 'Sulaiman', 'abdulaziz.sulaiman@example.com', 'Taif, Al-Shuhada District', 'LSA-2023-010'),
('C011', 'Faisal', 'Hamad', 'faisal.hamad@example.com', 'Riyadh, Al-Wurud District', 'LSA-2023-011'),
('C012', 'Salman', 'Yousef', 'salman.yousef@example.com', 'Dammam, Al-Thuqbah District', 'LSA-2023-012'),
('C013', 'Ahmed', 'Khaled', 'ahmed.khaled@example.com', 'Jeddah, Al-Zahra District', 'LSA-2023-013'),
('C014', 'Yazeed', 'Abdullah', 'yazeed.abdullah@example.com', 'Riyadh, Al-Ghadir District', 'LSA-2023-014'),
('C015', 'Waleed', 'Faris', 'waleed.faris@example.com', 'Khobar, Al-Hamra District', 'LSA-2023-015');

-- Insert data into Customer_phone table
INSERT INTO Customer_phone (Phone, Customer_id) VALUES
('0501234567', 'C001'),
('0552345678', 'C001'),
('0513456789', 'C002'),
('0564567890', 'C003'),
('0505678901', 'C004'),
('0556789012', 'C005'),
('0517890123', 'C006'),
('0568901234', 'C007'),
('0509012345', 'C008'),
('0550123456', 'C009'),
('0511234567', 'C010'),
('0562345678', 'C011'),
('0503456789', 'C012'),
('0554567890', 'C013'),
('0515678901', 'C014'),
('0566789012', 'C015');

-- Insert data into Weapons table
INSERT INTO Weapons (Name, Weapon_ID, Selling_price, Rental_price, Availability_status, License_requirement, Caliber, Type, Admin_id) VALUES
('Glock 17', 'W001', 4500.00, 150.00, 'Available', 'Yes', '9mm', 'Pistol', 'A101'),
('Beretta 92FS', 'W002', 5000.00, 180.00, 'Available', 'Yes', '9mm', 'Pistol', 'A102'),
('Colt M4', 'W003', 12000.00, 400.00, 'Available', 'Yes', '5.56mm', 'Rifle', 'A101'),
('AK-47', 'W004', 9500.00, 350.00, 'Available', 'Yes', '7.62mm', 'Rifle', 'A102'),
('Remington 870', 'W005', 6000.00, 200.00, 'Available', 'Yes', '12 gauge', 'Shotgun', 'A101'),
('Smith & Wesson M&P', 'W006', 4800.00, 160.00, 'Rented', 'Yes', '9mm', 'Pistol', 'A102'),
('FN SCAR', 'W007', 15000.00, 500.00, 'Available', 'Yes', '7.62mm', 'Rifle', 'A101'),
('Desert Eagle', 'W008', 7000.00, 250.00, 'Available', 'Yes', '.50 AE', 'Pistol', 'A102'),
('Winchester SXP', 'W009', 5500.00, 190.00, 'Available', 'Yes', '12 gauge', 'Shotgun', 'A101'),
('SIG Sauer P320', 'W010', 5200.00, 170.00, 'Available', 'Yes', '9mm', 'Pistol', 'A102'),
('HK MP5', 'W011', 11000.00, 380.00, 'Under Maintenance', 'Yes', '9mm', 'SMG', 'A101'),
('Browning Hi-Power', 'W012', 4200.00, 140.00, 'Available', 'Yes', '9mm', 'Pistol', 'A102'),
('Mossberg 500', 'W013', 5800.00, 200.00, 'Available', 'Yes', '12 gauge', 'Shotgun', 'A101'),
('CZ 75', 'W014', 4600.00, 155.00, 'Available', 'Yes', '9mm', 'Pistol', 'A102'),
('AR-15', 'W015', 10000.00, 350.00, 'Available', 'Yes', '5.56mm', 'Rifle', 'A101');

-- Insert data into BackgroundCheck table
INSERT INTO BackgroundCheck (Customer_ID, Background_ID, Status, Check_date) VALUES
('C001', 'BC001', 'Approved', '2022-12-15'),
('C002', 'BC002', 'Approved', '2022-12-18'),
('C003', 'BC003', 'Approved', '2023-01-05'),
('C004', 'BC004', 'Approved', '2023-01-10'),
('C005', 'BC005', 'Approved', '2023-02-02'),
('C006', 'BC006', 'Pending', '2023-02-15'),
('C007', 'BC007', 'Approved', '2023-03-01'),
('C008', 'BC008', 'Approved', '2023-03-12'),
('C009', 'BC009', 'Rejected', '2023-04-05'),
('C010', 'BC010', 'Approved', '2023-04-20'),
('C011', 'BC011', 'Approved', '2023-05-10'),
('C012', 'BC012', 'Approved', '2023-05-25'),
('C013', 'BC013', 'Pending', '2023-06-08'),
('C014', 'BC014', 'Approved', '2023-06-20'),
('C015', 'BC015', 'Approved', '2023-07-05');

-- Insert data into Rental table
INSERT INTO Rental (Rental_ID, Total_Cost, Return_status, Start_Date, End_Date, Customer_id) VALUES
('R001', 1050.00, 'Returned', '2023-01-05', '2023-01-12', 'C001'),
('R002', 720.00, 'Not Returned', '2023-02-10', '2023-02-14', 'C003'),
('R003', 1800.00, 'Returned', '2023-03-15', '2023-03-25', 'C005'),
('R004', 1260.00, 'Returned', '2023-04-02', '2023-04-09', 'C007'),
('R005', 1600.00, 'Not Returned', '2023-05-18', '2023-05-26', 'C002'),
('R006', 900.00, 'Returned', '2023-06-01', '2023-06-06', 'C009'),
('R007', 2250.00, 'Not Returned', '2023-07-10', '2023-07-25', 'C011'),
('R008', 800.00, 'Returned', '2023-08-05', '2023-08-09', 'C013'),
('R009', 1900.00, 'Returned', '2023-09-12', '2023-09-22', 'C004'),
('R010', 1400.00, 'Not Returned', '2023-10-15', '2023-10-22', 'C006'),
('R011', 1120.00, 'Returned', '2023-11-03', '2023-11-10', 'C008'),
('R012', 750.00, 'Returned', '2023-12-01', '2023-12-05', 'C010'),
('R013', 1700.00, 'Not Returned', '2024-01-10', '2024-01-20', 'C012'),
('R014', 2000.00, 'Returned', '2024-02-05', '2024-02-15', 'C014'),
('R015', 1350.00, 'Not Returned', '2024-03-12', '2024-03-19', 'C015');

-- Insert data into Rental_weapon table
INSERT INTO Rental_weapon (Rental_ID, Weapon_ID, Quantity) VALUES
('R001', 'W001', 1),
('R001', 'W003', 1),
('R002', 'W002', 2),
('R003', 'W004', 1),
('R003', 'W005', 1),
('R004', 'W006', 3),
('R005', 'W007', 2),
('R006', 'W008', 1),
('R007', 'W009', 1),
('R007', 'W010', 2),
('R008', 'W011', 1),
('R009', 'W012', 1),
('R009', 'W013', 1),
('R010', 'W014', 2),
('R011', 'W015', 1),
('R012', 'W001', 1),
('R013', 'W002', 1),
('R013', 'W003', 1),
('R014', 'W004', 2),
('R015', 'W005', 1);

-- Insert data into Payment table
INSERT INTO Payment (Payment_ID, Transaction_ID, Payment_Method, Payment_Status, Payment_Date) VALUES
('P001', 'T001', 'Credit Card', 'Completed', '2023-01-04'),
('P002', 'T002', 'Bank Transfer', 'Completed', '2023-02-09'),
('P003', 'T003', 'Cash', 'Completed', '2023-03-14'),
('P004', 'T004', 'Credit Card', 'Completed', '2023-04-01'),
('P005', 'T005', 'Apple Pay', 'Completed', '2023-05-17'),
('P006', 'T006', 'Bank Transfer', 'Completed', '2023-05-31'),
('P007', 'T007', 'Credit Card', 'Pending', '2023-07-09'),
('P008', 'T008', 'Cash', 'Completed', '2023-08-04'),
('P009', 'T009', 'Credit Card', 'Completed', '2023-09-11'),
('P010', 'T010', 'Bank Transfer', 'Failed', '2023-10-14'),
('P011', 'T011', 'Apple Pay', 'Completed', '2023-11-02'),
('P012', 'T012', 'Cash', 'Completed', '2023-11-30'),
('P013', 'T013', 'Credit Card', 'Completed', '2024-01-09'),
('P014', 'T014', 'Bank Transfer', 'Completed', '2024-02-04'),
('P015', 'T015', 'Credit Card', 'Pending', '2024-03-11');

-- Insert data into Transaction table
INSERT INTO Transaction (Transaction_ID, Customer_ID, Weapon_ID, Transaction_Type, Amount, Date) VALUES
('T001', 'C001', 'W001', 'Rental', 1050.00, '2023-01-04'),
('T002', 'C003', 'W002', 'Rental', 720.00, '2023-02-09'),
('T003', 'C005', 'W004', 'Rental', 1800.00, '2023-03-14'),
('T004', 'C007', 'W006', 'Rental', 1260.00, '2023-04-01'),
('T005', 'C002', 'W007', 'Rental', 1600.00, '2023-05-17'),
('T006', 'C009', 'W008', 'Rental', 900.00, '2023-05-31'),
('T007', 'C011', 'W009', 'Rental', 2250.00, '2023-07-09'),
('T008', 'C013', 'W011', 'Rental', 800.00, '2023-08-04'),
('T009', 'C004', 'W012', 'Rental', 1900.00, '2023-09-11'),
('T010', 'C006', 'W014', 'Rental', 1400.00, '2023-10-14'),
('T011', 'C008', 'W015', 'Rental', 1120.00, '2023-11-02'),
('T012', 'C010', 'W001', 'Rental', 750.00, '2023-11-30'),
('T013', 'C012', 'W002', 'Purchase', 5000.00, '2024-01-09'),
('T014', 'C014', 'W004', 'Purchase', 9500.00, '2024-02-04'),
('T015', 'C015', 'W005', 'Rental', 1350.00, '2024-03-11');