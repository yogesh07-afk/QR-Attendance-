<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Student, model.Teacher" %>
<%
    // Verify teacher is logged in
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("teacher") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    @SuppressWarnings("unchecked")
    List<Student> attendees = (List<Student>) request.getAttribute("attendees");
    String subject = (String) request.getAttribute("subject");
    Integer sessionId = (Integer) request.getAttribute("sessionId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Report - <%= subject %></title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        @media print {
            .no-print { display: none !important; }
            .container { box-shadow: none; border: none; margin: 0; max-width: 100%; }
        }
    </style>
</head>
<body>

    <div class="navbar no-print">
        <h2>QR Attendance System</h2>
        <div>
            <a href="dashboard.jsp" style="color: white; border-color: white; margin-right: 10px;">← Back to Dashboard</a>
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>

    <div class="container">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
            <div>
                <h1>Attendance Report</h1>
                <p style="color: #64748b; font-size: 16px;">
                    Subject: <strong><%= subject != null ? subject : "N/A" %></strong> 
                    <% if (sessionId != null) { %> | Session #<%= sessionId %> <% } %>
                </p>
            </div>
            <div class="no-print">
                <button onclick="window.print()" class="btn btn-primary btn-sm">🖨️ Print / Save as PDF</button>
            </div>
        </div>

        <div style="background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 14px 20px; margin-bottom: 20px; display: inline-block;">
            Total Students Present: <strong style="color: #16a34a; font-size: 18px;"><%= attendees != null ? attendees.size() : 0 %></strong>
        </div>

        <%
            if (attendees == null || attendees.isEmpty()) {
        %>
            <div class="alert alert-warning">No students have marked attendance for this session yet.</div>
        <%
            } else {
        %>
            <table>
                <thead>
                    <tr>
                        <th style="width: 80px;">Sr. No.</th>
                        <th>Roll Number</th>
                        <th>Student Name</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int srNo = 1;
                        for (Student s : attendees) {
                    %>
                    <tr>
                        <td><%= srNo++ %></td>
                        <td><strong><%= s.getRollNumber() %></strong></td>
                        <td><%= s.getName() %></td>
                        <td><span class="badge badge-active">Present ✅</span></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>

</body>
</html>