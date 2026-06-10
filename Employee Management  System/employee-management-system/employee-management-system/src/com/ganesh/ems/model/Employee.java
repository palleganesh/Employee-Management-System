package com.ganesh.ems.model;

public class Employee {
    private int id;
    private String name;
    private String email;
    private String department;
    private String designation;
    private double salary;
    private String phone;
    private String joinDate;

    public Employee() {}

    public Employee(int id, String name, String email, String department,
                    String designation, double salary, String phone, String joinDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.phone = phone;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-25s | %-15s | %-20s | %-12.2f | %-12s | %-12s |",
                id, name, email, department, designation, salary, phone, joinDate);
    }
}
