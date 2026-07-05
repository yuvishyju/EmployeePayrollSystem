package dao;

import db.DBConnection;
import model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AttendanceDAO {

    // Save Attendance
    public boolean addAttendance(Attendance attendance) {

        String sql = "INSERT INTO attendance(employee_id, attendance_date, status) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, attendance.getEmployeeId());
            pst.setString(2, attendance.getAttendanceDate());
            pst.setString(3, attendance.getStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Display Attendance
    public ArrayList<Attendance> getAllAttendance() {

        ArrayList<Attendance> list = new ArrayList<>();

        String sql = "SELECT a.*, e.employee_name " +
                     "FROM attendance a JOIN employee e " +
                     "ON a.employee_id = e.employee_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Attendance attendance = new Attendance();

                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setEmployeeId(rs.getInt("employee_id"));
                attendance.setEmployeeName(rs.getString("employee_name"));
                attendance.setAttendanceDate(rs.getString("attendance_date"));
                attendance.setStatus(rs.getString("status"));

                list.add(attendance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Attendance> searchAttendance(String employeeId) {

    ArrayList<Attendance> list = new ArrayList<>();

    String sql =
        "SELECT a.*, e.employee_name " +
        "FROM attendance a JOIN employee e " +
        "ON a.employee_id = e.employee_id " +
        "WHERE a.employee_id LIKE ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, "%" + employeeId + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            Attendance attendance = new Attendance();

            attendance.setAttendanceId(rs.getInt("attendance_id"));
            attendance.setEmployeeId(rs.getInt("employee_id"));
            attendance.setEmployeeName(rs.getString("employee_name"));
            attendance.setAttendanceDate(rs.getString("attendance_date"));
            attendance.setStatus(rs.getString("status"));

            list.add(attendance);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public boolean attendanceExists(int employeeId, String date) {

    String sql = "SELECT * FROM attendance WHERE employee_id = ? AND attendance_date = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, employeeId);
        pst.setString(2, date);

        ResultSet rs = pst.executeQuery();

        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}