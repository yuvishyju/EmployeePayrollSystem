package dao;

import db.DBConnection;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EmployeeDAO {

    // Add Employee
    public boolean addEmployee(Employee emp) {

        String sql = "INSERT INTO employee(employee_name, department, designation, basic_salary, hra, da, pf, tax) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, emp.getEmployeeName());
            pst.setString(2, emp.getDepartment());
            pst.setString(3, emp.getDesignation());
            pst.setDouble(4, emp.getBasicSalary());
            pst.setDouble(5, emp.getHra());
            pst.setDouble(6, emp.getDa());
            pst.setDouble(7, emp.getPf());
            pst.setDouble(8, emp.getTax());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // View Employees
    public ArrayList<Employee> getAllEmployees() {

        ArrayList<Employee> list = new ArrayList<>();

        String sql = "SELECT * FROM employee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Employee emp = new Employee();

                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setEmployeeName(rs.getString("employee_name"));
                emp.setDepartment(rs.getString("department"));
                emp.setDesignation(rs.getString("designation"));
                emp.setBasicSalary(rs.getDouble("basic_salary"));
                emp.setHra(rs.getDouble("hra"));
                emp.setDa(rs.getDouble("da"));
                emp.setPf(rs.getDouble("pf"));
                emp.setTax(rs.getDouble("tax"));

                list.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Update Employee
    public boolean updateEmployee(Employee emp) {

        String sql = "UPDATE employee SET employee_name=?, department=?, designation=?, basic_salary=?, hra=?, da=?, pf=?, tax=? WHERE employee_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, emp.getEmployeeName());
            pst.setString(2, emp.getDepartment());
            pst.setString(3, emp.getDesignation());
            pst.setDouble(4, emp.getBasicSalary());
            pst.setDouble(5, emp.getHra());
            pst.setDouble(6, emp.getDa());
            pst.setDouble(7, emp.getPf());
            pst.setDouble(8, emp.getTax());
            pst.setInt(9, emp.getEmployeeId());

            int rows = pst.executeUpdate();
            System.out.println("Rows Updated = " + rows);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    // Delete Employee
    public boolean deleteEmployee(int employeeId) {

        String sql = "DELETE FROM employee WHERE employee_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employeeId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search Employee
    public ArrayList<Employee> searchEmployee(String keyword) {

        ArrayList<Employee> list = new ArrayList<>();

        String sql = "SELECT * FROM employee WHERE employee_name LIKE ? OR employee_id LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Employee emp = new Employee();

                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setEmployeeName(rs.getString("employee_name"));
                emp.setDepartment(rs.getString("department"));
                emp.setDesignation(rs.getString("designation"));
                emp.setBasicSalary(rs.getDouble("basic_salary"));
                emp.setHra(rs.getDouble("hra"));
                emp.setDa(rs.getDouble("da"));
                emp.setPf(rs.getDouble("pf"));
                emp.setTax(rs.getDouble("tax"));

                list.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get Employee by ID
    public Employee getEmployeeById(int employeeId) {

        Employee emp = null;

        String sql = "SELECT * FROM employee WHERE employee_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employeeId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                emp = new Employee();

                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setEmployeeName(rs.getString("employee_name"));
                emp.setDepartment(rs.getString("department"));
                emp.setDesignation(rs.getString("designation"));
                emp.setBasicSalary(rs.getDouble("basic_salary"));
                emp.setHra(rs.getDouble("hra"));
                emp.setDa(rs.getDouble("da"));
                emp.setPf(rs.getDouble("pf"));
                emp.setTax(rs.getDouble("tax"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }

    // Dashboard
    public int getEmployeeCount() {

        int count = 0;

        String sql = "SELECT COUNT(*) FROM employee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

}