# Employee Management System

A console-based Employee Management System built with **Java**, **JDBC**, and **MySQL**.

## Features
- Add, View, Search, Update, Delete employees (full CRUD)
- Search by name or filter by department
- Update salary independently
- Department-wise statistics (count, avg salary, max salary)
- Input validation and custom exception handling
- Modular architecture: Model → DAO → Service → Main

## Tech Stack

| Layer         | Technology            |
|---------------|-----------------------|
| Language      | Java 17               |
| DB Connector  | JDBC (MySQL Connector) |
| Database      | MySQL 8               |
| Architecture  | DAO + Service pattern |

## Project Structure

```
employee-management-system/
├── src/com/ganesh/ems/
│   ├── EmployeeManagementSystem.java   # Entry point + interactive menu
│   ├── model/
│   │   └── Employee.java               # POJO / data model
│   ├── dao/
│   │   └── EmployeeDAO.java            # All JDBC database operations
│   ├── service/
│   │   └── EmployeeService.java        # Business logic + validation
│   ├── util/
│   │   └── DatabaseConnection.java     # Singleton DB connection manager
│   └── exception/
│       └── EmployeeNotFoundException.java
├── schema.sql                          # DB setup + sample data
└── README.md
```

## Setup & Run

### Prerequisites
- Java 17+
- MySQL 8+
- [MySQL Connector/J JAR](https://dev.mysql.com/downloads/connector/j/) — add to your classpath

### 1. Set up the database
```sql
mysql -u root -p < schema.sql
```

### 2. Update credentials
Edit `src/com/ganesh/ems/util/DatabaseConnection.java`:
```java
private static final String PASSWORD = "your_mysql_password";
```

### 3. Compile
```bash
javac -cp .:mysql-connector-j-8.x.x.jar -d out \
  src/com/ganesh/ems/**/*.java \
  src/com/ganesh/ems/*.java
```

### 4. Run
```bash
java -cp out:mysql-connector-j-8.x.x.jar com.ganesh.ems.EmployeeManagementSystem
```

## Operations Supported

| # | Operation               | SQL Used            |
|---|-------------------------|---------------------|
| 1 | Add Employee            | INSERT              |
| 2 | View All                | SELECT *            |
| 3 | Find by ID              | SELECT WHERE id     |
| 4 | Search by Name          | SELECT WHERE LIKE   |
| 5 | Filter by Department    | SELECT WHERE dept   |
| 6 | Update Employee Details | UPDATE              |
| 7 | Update Salary           | UPDATE (salary only)|
| 8 | Delete Employee         | DELETE              |
| 9 | Department Stats        | GROUP BY + AVG/MAX  |
