package com.ganesh.ems.service;

import com.ganesh.ems.dao.EmployeeDAO;
import com.ganesh.ems.exception.EmployeeNotFoundException;
import com.ganesh.ems.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void initializeDatabase() {
        try {
            employeeDAO.createTableIfNotExists();
        } catch (SQLException e) {
            System.err.println("❌ Error initializing database: " + e.getMessage());
        }
    }

    public void addEmployee(String name, String email, String department,
                             String designation, double salary, String phone, String joinDate) {
        // Input validation
        if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Name cannot be empty.");
            return;
        }
        if (email == null || !email.contains("@")) {
            System.out.println("❌ Invalid email address.");
            return;
        }
        if (salary < 0) {
            System.out.println("❌ Salary cannot be negative.");
            return;
        }

        Employee emp = new Employee();
        emp.setName(name.trim());
        emp.setEmail(email.trim());
        emp.setDepartment(department.trim());
        emp.setDesignation(designation.trim());
        emp.setSalary(salary);
        emp.setPhone(phone);
        emp.setJoinDate(joinDate);

        try {
            employeeDAO.addEmployee(emp);
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("❌ Email already exists. Please use a different email.");
            } else {
                System.err.println("❌ Error adding employee: " + e.getMessage());
            }
        }
    }

    public void displayAllEmployees() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
                return;
            }
            printHeader();
            employees.forEach(System.out::println);
            printFooter(employees.size());
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving employees: " + e.getMessage());
        }
    }

    public void findEmployeeById(int id) {
        try {
            Employee emp = employeeDAO.getEmployeeById(id);
            printHeader();
            System.out.println(emp);
            System.out.println("─".repeat(130));
        } catch (EmployeeNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
        }
    }

    public void searchEmployees(String name) {
        try {
            List<Employee> results = employeeDAO.searchByName(name);
            if (results.isEmpty()) {
                System.out.println("No employees found matching: " + name);
                return;
            }
            printHeader();
            results.forEach(System.out::println);
            printFooter(results.size());
        } catch (SQLException e) {
            System.err.println("❌ Search error: " + e.getMessage());
        }
    }

    public void getByDepartment(String department) {
        try {
            List<Employee> results = employeeDAO.getByDepartment(department);
            if (results.isEmpty()) {
                System.out.println("No employees found in department: " + department);
                return;
            }
            System.out.println("\nEmployees in " + department + " department:");
            printHeader();
            results.forEach(System.out::println);
            printFooter(results.size());
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public void updateEmployee(int id, String name, String email, String department,
                                String designation, double salary, String phone, String joinDate) {
        try {
            Employee emp = new Employee(id, name, email, department, designation, salary, phone, joinDate);
            employeeDAO.updateEmployee(emp);
        } catch (EmployeeNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Update error: " + e.getMessage());
        }
    }

    public void updateSalary(int id, double newSalary) {
        if (newSalary < 0) {
            System.out.println("❌ Salary cannot be negative.");
            return;
        }
        try {
            employeeDAO.updateSalary(id, newSalary);
        } catch (EmployeeNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error updating salary: " + e.getMessage());
        }
    }

    public void deleteEmployee(int id) {
        try {
            employeeDAO.deleteEmployee(id);
        } catch (EmployeeNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Delete error: " + e.getMessage());
        }
    }

    public void showDepartmentStats() {
        try {
            employeeDAO.printDepartmentStats();
        } catch (SQLException e) {
            System.err.println("❌ Stats error: " + e.getMessage());
        }
    }

    private void printHeader() {
        System.out.println("\n" + "─".repeat(130));
        System.out.printf("| %-5s | %-20s | %-25s | %-15s | %-20s | %-12s | %-12s | %-12s |%n",
                "ID", "Name", "Email", "Department", "Designation", "Salary", "Phone", "Join Date");
        System.out.println("─".repeat(130));
    }

    private void printFooter(int count) {
        System.out.println("─".repeat(130));
        System.out.println("Total: " + count + " employee(s)");
    }
}
