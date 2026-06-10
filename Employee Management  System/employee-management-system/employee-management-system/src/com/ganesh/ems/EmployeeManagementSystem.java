package com.ganesh.ems;

import com.ganesh.ems.service.EmployeeService;
import com.ganesh.ems.util.DatabaseConnection;

import java.util.Scanner;

public class EmployeeManagementSystem {

    private static final EmployeeService service = new EmployeeService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   Employee Management System             ║");
        System.out.println("║   Built with Java, JDBC & MySQL          ║");
        System.out.println("╚══════════════════════════════════════════╝");

        service.initializeDatabase();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> service.displayAllEmployees();
                case 3 -> findById();
                case 4 -> searchByName();
                case 5 -> getByDepartment();
                case 6 -> updateEmployee();
                case 7 -> updateSalary();
                case 8 -> deleteEmployee();
                case 9 -> service.showDepartmentStats();
                case 0 -> {
                    System.out.println("\nGoodbye! Closing connection...");
                    DatabaseConnection.closeConnection();
                    running = false;
                }
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n═════════════════════════════");
        System.out.println("  MENU");
        System.out.println("─────────────────────────────");
        System.out.println("  1. Add Employee");
        System.out.println("  2. View All Employees");
        System.out.println("  3. Find Employee by ID");
        System.out.println("  4. Search by Name");
        System.out.println("  5. Filter by Department");
        System.out.println("  6. Update Employee Details");
        System.out.println("  7. Update Salary");
        System.out.println("  8. Delete Employee");
        System.out.println("  9. Department Statistics");
        System.out.println("  0. Exit");
        System.out.println("═════════════════════════════");
    }

    private static void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        System.out.print("Name        : "); String name = scanner.nextLine().trim();
        System.out.print("Email       : "); String email = scanner.nextLine().trim();
        System.out.print("Department  : "); String dept = scanner.nextLine().trim();
        System.out.print("Designation : "); String desig = scanner.nextLine().trim();
        double salary = readDouble("Salary      : ");
        System.out.print("Phone       : "); String phone = scanner.nextLine().trim();
        System.out.print("Join Date (YYYY-MM-DD): "); String joinDate = scanner.nextLine().trim();

        service.addEmployee(name, email, dept, desig, salary, phone, joinDate);
    }

    private static void findById() {
        int id = readInt("Enter Employee ID: ");
        service.findEmployeeById(id);
    }

    private static void searchByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        service.searchEmployees(name);
    }

    private static void getByDepartment() {
        System.out.print("Enter department name: ");
        String dept = scanner.nextLine().trim();
        service.getByDepartment(dept);
    }

    private static void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        int id = readInt("Enter Employee ID to update: ");
        System.out.println("Enter new details (leave blank to keep existing data - not yet implemented; provide all fields):");
        System.out.print("Name        : "); String name = scanner.nextLine().trim();
        System.out.print("Email       : "); String email = scanner.nextLine().trim();
        System.out.print("Department  : "); String dept = scanner.nextLine().trim();
        System.out.print("Designation : "); String desig = scanner.nextLine().trim();
        double salary = readDouble("Salary      : ");
        System.out.print("Phone       : "); String phone = scanner.nextLine().trim();
        System.out.print("Join Date (YYYY-MM-DD): "); String joinDate = scanner.nextLine().trim();

        service.updateEmployee(id, name, email, dept, desig, salary, phone, joinDate);
    }

    private static void updateSalary() {
        int id = readInt("Enter Employee ID: ");
        double salary = readDouble("Enter new salary: ");
        service.updateSalary(id, salary);
    }

    private static void deleteEmployee() {
        int id = readInt("Enter Employee ID to delete: ");
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes")) {
            service.deleteEmployee(id);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // ─── Input helpers ────────────────────────────────────────────────────────
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid decimal number.");
            }
        }
    }
}
