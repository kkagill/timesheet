DROP DATABASE inventory;
CREATE DATABASE inventory;

GRANT ALL PRIVILEGES ON inventory.* TO stock@localhost IDENTIFIED BY 'check';
GRANT ALL PRIVILEGES ON inventory.* TO stock@"%" IDENTIFIED BY 'check';

USE inventory;

DROP TABLE IF EXISTS users;
CREATE TABLE users(EmpID int, EmpPwd VARCHAR(15), EmpName VARCHAR(20), Admin BOOLEAN);
INSERT INTO users VALUES('1', 'dd', 'Admin', '1');
INSERT INTO users VALUES('2', 'dd', 'Sejong Shon', '0');
INSERT INTO users VALUES('3', 'dd', 'Anthony Ng', '0');

DROP TABLE IF EXISTS timesheet;
CREATE TABLE timesheet(EmpID int, WeekDate DATE, Project VARCHAR(10), 
                       WP VARCHAR(10), Sat DECIMAL, Sun DECIMAL, Mon DECIMAL, Tue DECIMAL, 
                       Wed DECIMAL, Thu DECIMAL, Fri DECIMAL, Notes LONGTEXT);
INSERT INTO timesheet VALUES ('2', '2015-11-17', '12SD', '24DS', '2.0', '1.0', '2.0', '1.5', '1', '1', '2', 'Hello');
INSERT INTO timesheet VALUES ('2', '2015-11-17', '13AS', '34FG', '3.0', '2.0', '3.0', '2.5', '2', '2', '2', 'Hi');
INSERT INTO timesheet VALUES ('2', '2015-12-17', '14FD', '44QD', '4.0', '3.0', '4.0', '3.5', '3', '3', '2', 'how are you');
INSERT INTO timesheet VALUES ('2', '2015-12-17', '15GW', '54TG', '5.0', '4.0', '5.0', '4.5', '4', '4', '2', 'find');
INSERT INTO timesheet VALUES ('2', '2015-12-17', '16QD', '64DD', '6.0', '5.0', '6.0', '5.5', '5', '5', '2', 'thank you');