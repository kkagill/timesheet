DROP DATABASE IF EXISTS assignment;
CREATE DATABASE assignment;

GRANT ALL PRIVILEGES ON assignment.* TO admin@localhost IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON assignment.* TO admin@"%" IDENTIFIED BY 'password';

USE assignment;

DROP TABLE IF EXISTS users;
CREATE TABLE users(EmployeeID INTEGER(3) NOT NULL, EmployeePassword VARCHAR(20) NOT NULL,
                                        EmployeeName VARCHAR(40) NOT NULL, Superuser BOOLEAN, PRIMARY KEY(EmployeeID));
INSERT INTO users VALUES        ('000', 'admin', 'Admin User', '1'),
                                                        ('111', 'user', 'Ivy Cheung', '0'),
                                                        ('222', 'user', 'Stacy Mitchell', '0');

DROP TABLE IF EXISTS timesheet;
CREATE TABLE timesheet(EmployeeID INTEGER(3) NOT NULL, WeekNum INTEGER(2) NOT NULL, Project INTEGER(5) NOT NULL, 
                                                WP VARCHAR(5) NOT NULL, Sat DECIMAL(2,1), Sun DECIMAL(2,1), Mon DECIMAL(2,1), Tues DECIMAL(2,1), 
                                                Wed DECIMAL(2,1), Thurs DECIMAL(2,1), Fri DECIMAL(2,1), Notes LONGTEXT, 
                                                PRIMARY KEY(EmployeeID), PRIMARY KEY(WeekNum), FOREIGN KEY(EmployeeID) REFERENCES users(EmployeeID));
