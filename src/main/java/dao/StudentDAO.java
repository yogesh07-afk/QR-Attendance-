package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Student;

public class StudentDAO {

    // Method to search a student by roll number
    public Student getByRollNumber(String rollNumber) {
        Student student = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students WHERE roll_number = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setRollNumber(rs.getString("roll_number"));
                student.setEmail(rs.getString("email"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
}