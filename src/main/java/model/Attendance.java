package model;

public class Attendance {
    private int id;
    private int sessionId;
    private int studentId;
    private String scannedAt;

    public Attendance() {}

    public Attendance(int id, int sessionId, int studentId, String scannedAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.scannedAt = scannedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getScannedAt() { return scannedAt; }
    public void setScannedAt(String scannedAt) { this.scannedAt = scannedAt; }
}