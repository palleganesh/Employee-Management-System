-- Employee Management System - Database Schema
-- Run this script to set up the database

CREATE DATABASE IF NOT EXISTS employee_management_db;
USE employee_management_db;

CREATE TABLE IF NOT EXISTS employees (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    email       VARCHAR(100)  NOT NULL UNIQUE,
    department  VARCHAR(50)   NOT NULL,
    designation VARCHAR(100)  NOT NULL,
    salary      DECIMAL(12,2) NOT NULL,
    phone       VARCHAR(15),
    join_date   DATE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample data for testing
INSERT INTO employees (name, email, department, designation, salary, phone, join_date) VALUES
('Ganesh Palle',     'ganesh@company.com',  'Engineering',  'Java Developer',        450000.00, '9392771288', '2025-06-01'),
('Priya Sharma',     'priya@company.com',   'Engineering',  'Frontend Developer',    420000.00, '9876543210', '2025-04-15'),
('Ravi Kumar',       'ravi@company.com',    'HR',           'HR Manager',            550000.00, '9123456789', '2023-01-10'),
('Anjali Singh',     'anjali@company.com',  'Marketing',    'Marketing Executive',   380000.00, '9988776655', '2024-08-20'),
('Suresh Reddy',     'suresh@company.com',  'Engineering',  'Backend Developer',     480000.00, '9001122334', '2024-03-01'),
('Kavitha Nair',     'kavitha@company.com', 'Finance',      'Accountant',            410000.00, '8877665544', '2023-09-05'),
('Ramesh Babu',      'ramesh@company.com',  'Engineering',  'DevOps Engineer',       520000.00, '9712345678', '2024-01-15'),
('Deepika Joshi',    'deepika@company.com', 'HR',           'Recruiter',             350000.00, '9654321098', '2024-11-20'),
('Vijay Anand',      'vijay@company.com',   'Marketing',    'Digital Marketing Mgr', 460000.00, '9445566778', '2022-07-11'),
('Sunita Rao',       'sunita@company.com',  'Finance',      'Finance Manager',       620000.00, '9334455667', '2021-12-01');
