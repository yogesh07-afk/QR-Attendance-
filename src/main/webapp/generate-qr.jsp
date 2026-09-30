<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Teacher" %>
<%
    // Ensure teacher is logged in
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("teacher") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Teacher teacher = (Teacher) userSession.getAttribute("teacher");

    String sessionId = request.getParameter("sessionId");
    String subject = request.getParameter("subject");

    if (sessionId == null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance QR Code - <%= subject %></title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .qr-card {
            max-width: 500px;
            margin: 30px auto;
            text-align: center;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }
        .qr-card img {
            width: 280px;
            height: 280px;
            border: 4px solid #0f172a;
            border-radius: 12px;
            margin: 15px 0;
        }
    </style>
</head>
<body>

    <div class="navbar">
        <h2>QR Attendance System</h2>
        <div>
            <a href="dashboard.jsp" style="color: white; border-color: white; margin-right: 10px;">← Back to Dashboard</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>

    <div class="qr-card">
        <h2><%= subject != null ? subject : "Attendance Session" %></h2>
        <p style="color: #64748b;">Students: Scan this QR code using your phone camera to mark attendance.</p>
        <span class="badge badge-active" style="display: inline-block; margin-top: 8px;">Session Active 🟢</span>

        <!-- Display the generated QR PNG -->
        <div>
            <img src="qrcodes/session_<%= sessionId %>.png" alt="Attendance QR Code">
        </div>

        <p style="font-size: 14px; color: #dc2626; font-weight: 600; margin-bottom: 20px;">
            ⚠️ One attempt per student only!
        </p>

        <div style="display: flex; gap: 10px; justify-content: center;">
            <a href="ReportServlet?sessionId=<%= sessionId %>&subject=<%= subject %>" class="btn btn-primary btn-sm">
                View Live Attendance 👥
            </a>
            <a href="CloseSessionServlet?sessionId=<%= sessionId %>" class="btn btn-danger btn-sm" onclick="return confirm('Closing this session will disable the QR code. Proceed?')">
                Close Session 🛑
            </a>
        </div>
    </div>

</body>
</html>