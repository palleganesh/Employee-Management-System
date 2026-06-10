package com.ganesh.ems.dao;

import com.ganesh.ems.exception.EmployeeNotFoundException;
import com.ganesh.ems.model.Employee;
import com.ganesh.ems.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ─── CREATE TABLE ──────────────────────────────────────────────────────────
    public void createTableIfNotExists() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS employees (
                    id          INT AUTO_INCREMENT PRIMARY KEY,
                    name        VARCHAR(100) NOT NULL,
                    email       VARCHAR(100) NOT NULL UNIQUE,
                    department  VARCHAR(50)  NOT NULL,
                    designation VARCHAR(100) NOT NULL,
                    salary      DECIMAL(12,2) NOT NULL,
                    phone       VARCHAR(15),
                    join_date   DATE
                )
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // ─── CREATE (Add Employee) ─────────────────────────────────────────────────
    public void addEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO employees (name, email, department, designation, salary, phone, join_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getDepartment());
            ps.setString(4, emp.getDesignation());
            ps.setDouble(5, emp.getSalary());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getJoinDate());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    emp.setId(keys.getInt(1));
                }
                System.out.println("✅ Employee added with ID: " + emp.getId());
            }
        }
    }

    // ─── READ (Get All Employees) ──────────────────────────────────────────────
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── READ (Get By ID) ─────────────────────────────────────────────────────
    public Employee getEmployeeById(int id) throws SQLException, EmployeeNotFoundException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                else throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
            }
        }
    }

    // ─── READ (Search By Name) ────────────────────────────────────────────────
    public List<Employee> searchByName(String name) throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── READ (Get By Department) ─────────────────────────────────────────────
    public List<Employee> getByDepartment(String department) throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ? ORDER BY name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ─── UPDATE (Update Employee) ─────────────────────────────────────────────
    public void updateEmployee(Employee emp) throws SQLException, EmployeeNotFoundException {
        // Verify exists first
        getEmployeeById(emp.getId());

        String sql = "UPDATE employees SET name=?, email=?, department=?, designation=?, salary=?, phone=?, join_date=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getDepartment());
            ps.setString(4, emp.getDesignation());
            ps.setDouble(5, emp.getSalary());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getJoinDate());
            ps.setInt(8, emp.getId());

            ps.executeUpdate();
            System.out.println("✅ Employee updated successfully.");
        }
    }

    // ─── UPDATE (Update Salary Only) ─────────────────────────────────────────
    public void updateSalary(int id, double newSalary) throws SQLException, EmployeeNotFoundException {
        getEmployeeById(id); // verify exists
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, newSalary);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("✅ Salary updated for employee ID: " + id);
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    public void deleteEmployee(int id) throws SQLException, EmployeeNotFoundException {
        getEmployeeById(id); // verify exists
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Employee ID " + id + " deleted successfully.");
        }
    }

    // ─── STATS ────────────────────────────────────────────────────────────────
    public void printDepartmentStats() throws SQLException {
        String sql = "SELECT department, COUNT(*) as count, AVG(salary) as avg_salary, MAX(salary) as max_salary FROM employees GROUP BY department";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n" + "─".repeat(65));
            System.out.printf("| %-20s | %-6s | %-12s | %-12s |%n", "Department", "Count", "Avg Salary", "Max Salary");
            System.out.println("─".repeat(65));
            while (rs.next()) {
                System.out.printf("| %-20s | %-6d | %-12.2f | %-12.2f |%n",
                        rs.getString("department"), rs.getInt("count"),
                        rs.getDouble("avg_salary"), rs.getDouble("max_salary"));
            }
            System.out.println("─".repeat(65));
        }
    }

    // ─── HELPER ───────────────────────────────────────────────────────────────
    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("department"),
                rs.getString("designation"),
                rs.getDouble("salary"),
                rs.getString("phone"),
                rs.getString("join_date") != null ? rs.getString("join_date") : ""
        );
    }
}
