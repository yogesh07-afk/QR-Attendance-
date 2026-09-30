package model;

public class Session {
    private int id;
    private int teacherId;
    private String subject;
    private String qrToken;
    private String createdAt;
    private boolean isActive;

    public Session() {}

    public Session(int id, int teacherId, String subject, String qrToken, String createdAt, boolean isActive) {
        this.id = id;
        this.teacherId = teacherId;
        this.subject = subject;
        this.qrToken = qrToken;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getQrToken() { return qrToken; }
    public void setQrToken(String qrToken) { this.qrToken = qrToken; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
}