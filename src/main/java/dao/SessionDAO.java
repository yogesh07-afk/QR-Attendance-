package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Session;

public class SessionDAO {

    // 1. Create a new session and return the generated session ID
    public int createSession(int teacherId, String subject, String qrToken) {
        int sessionId = -1;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO sessions (teacher_id, subject, qr_token, is_active) VALUES (?, ?, ?, true)";
            
            // Statement.RETURN_GENERATED_KEYS tells MySQL to give us back the auto-incremented ID
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, teacherId);
            ps.setString(2, subject);
            ps.setString(3, qrToken);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sessionId = rs.getInt(1); // auto-incremented session id
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionId;
    }

    // 2. Find a session by its unique QR token (used when student scans)
    public Session getByToken(String token) {
        Session session = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM sessions WHERE qr_token = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                session = new Session();
                session.setId(rs.getInt("id"));
                session.setTeacherId(rs.getInt("teacher_id"));
                session.setSubject(rs.getString("subject"));
                session.setQrToken(rs.getString("qr_token"));
                session.setCreatedAt(rs.getString("created_at"));
                session.setActive(rs.getBoolean("is_active"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    // 3. Close/deactivate a session so the QR code can no longer be used
    public void closeSession(int sessionId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE sessions SET is_active = false WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sessionId);

            ps.executeUpdate();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Get all sessions created by a teacher (to show history on dashboard)
    public List<Session> getByTeacher(int teacherId) {
        List<Session> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM sessions WHERE teacher_id = ? ORDER BY created_at DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, teacherId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setId(rs.getInt("id"));
                s.setTeacherId(rs.getInt("teacher_id"));
                s.setSubject(rs.getString("subject"));
                s.setQrToken(rs.getString("qr_token"));
                s.setCreatedAt(rs.getString("created_at"));
                s.setActive(rs.getBoolean("is_active"));
                list.add(s);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}