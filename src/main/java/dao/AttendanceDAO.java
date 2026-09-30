package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Student;

public class AttendanceDAO {

    // 1. Check if student already marked attendance for this session
    public boolean alreadyMarked(int sessionId, int studentId) {
        boolean exists = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM attendance WHERE session_id = ? AND student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sessionId);
            ps.setInt(2, studentId);

            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    // 2. Insert new attendance record
    public boolean markAttendance(int sessionId, int studentId) {
        boolean success = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO attendance (session_id, student_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sessionId);
            ps.setInt(2, studentId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                success = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    // 3. Fetch list of students who attended a specific session
    public List<Student> getAttendanceBySession(int sessionId) {
        List<Student> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT s.id, s.name, s.roll_number, s.email " +
                         "FROM students s " +
                         "INNER JOIN attendance a ON s.id = a.student_id " +
                         "WHERE a.session_id = ? " +
                         "ORDER BY a.scanned_at ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sessionId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setRollNumber(rs.getString("roll_number"));
                s.setEmail(rs.getString("email"));
                list.add(s);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}