package dao;

import db.DBConnection;
import model.Payroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PayrollDAO {

    public boolean savePayroll(Payroll payroll) {

        String sql = "INSERT INTO payroll(employee_id, payroll_month, gross_salary, deductions, net_salary) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, payroll.getEmployeeId());
            pst.setString(2, payroll.getPayrollMonth());
            pst.setDouble(3, payroll.getGrossSalary());
            pst.setDouble(4, payroll.getDeductions());
            pst.setDouble(5, payroll.getNetSalary());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Payroll> getAllPayroll() {

    ArrayList<Payroll> list = new ArrayList<>();

    String sql = "SELECT p.*, e.employee_name " +
                 "FROM payroll p JOIN employee e " +
                 "ON p.employee_id = e.employee_id";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {

            Payroll payroll = new Payroll();

            payroll.setEmployeeId(rs.getInt("employee_id"));
            payroll.setEmployeeName(rs.getString("employee_name"));
            payroll.setPayrollMonth(rs.getString("payroll_month"));
            payroll.setGrossSalary(rs.getDouble("gross_salary"));
            payroll.setDeductions(rs.getDouble("deductions"));
            payroll.setNetSalary(rs.getDouble("net_salary"));

            list.add(payroll);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
     public boolean payrollExists(int employeeId, String month) {

        String sql = "SELECT * FROM payroll WHERE employee_id = ? AND payroll_month = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employeeId);
            pst.setString(2, month);

            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
     public ArrayList<Payroll> searchPayroll(String employeeId) {

    ArrayList<Payroll> list = new ArrayList<>();

    String sql =
        "SELECT p.*, e.employee_name " +
        "FROM payroll p JOIN employee e " +
        "ON p.employee_id = e.employee_id " +
        "WHERE p.employee_id LIKE ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, "%" + employeeId + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            Payroll payroll = new Payroll();

            payroll.setEmployeeId(rs.getInt("employee_id"));
            payroll.setEmployeeName(rs.getString("employee_name"));
            payroll.setPayrollMonth(rs.getString("payroll_month"));
            payroll.setGrossSalary(rs.getDouble("gross_salary"));
            payroll.setDeductions(rs.getDouble("deductions"));
            payroll.setNetSalary(rs.getDouble("net_salary"));

            list.add(payroll);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
     // Dashboard - Total Payroll Records
public int getPayrollCount() {

    int count = 0;

    String sql = "SELECT COUNT(*) FROM payroll";

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

// Dashboard - Latest Payroll Month
public String getLatestPayrollMonth() {

    String month = "-";

    String sql = "SELECT payroll_month FROM payroll ORDER BY payroll_id DESC LIMIT 1";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        if (rs.next()) {
            month = rs.getString("payroll_month");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return month;
}
}