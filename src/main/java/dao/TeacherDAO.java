package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Teacher;

public class TeacherDAO {

    public Teacher login(String email, String password) {
        Teacher teacher = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM teachers WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPassword(rs.getString("password"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;
    }
}