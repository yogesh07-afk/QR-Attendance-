<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Teacher, model.Session, dao.SessionDAO, java.util.List" %>
<%
    // Protect page: teacher must be logged in
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("teacher") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Teacher teacher = (Teacher) userSession.getAttribute("teacher");

    // Fetch this teacher's sessions from MySQL
    SessionDAO sessionDAO = new SessionDAO();
    List<Session> sessions = sessionDAO.getByTeacher(teacher.getId());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard - QR Attendance</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <div class="navbar">
        <h2>QR Attendance System</h2>
        <div>
            <span style="margin-right: 20px; font-weight: 500;">Welcome, <%= teacher.getName() %></span>
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>

    <div class="container">
        <!-- Section: Create New Session -->
        <div class="card">
            <h2>Start a New Attendance Session</h2>
            <p style="color: #64748b; margin-bottom: 16px;">Enter the subject name to generate a unique QR code for today's lecture.</p>
            
            <%
                String error = request.getParameter("error");
                if (error != null) {
            %>
                <div class="alert alert-error"><%= error %></div>
            <%
                }
            %>

            <form action="CreateSessionServlet" method="post" style="display: flex; gap: 12px; align-items: flex-end;">
                <div class="form-group" style="flex: 1; margin-bottom: 0;">
                    <label>Subject Name</label>
                    <input type="text" name="subject" required placeholder="e.g. Java Programming, COA, AOA">
                </div>
                <button type="submit" class="btn btn-primary">Generate QR Code 📱</button>
            </form>
        </div>

        <!-- Section: Past Sessions History -->
        <div class="card">
            <h2>Session History</h2>
            <%
                if (sessions.isEmpty()) {
            %>
                <p style="color: #64748b;">No sessions created yet. Start one above!</p>
            <%
                } else {
            %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Subject</th>
                            <th>Created At</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Session s : sessions) {
                        %>
                        <tr>
                            <td>#<%= s.getId() %></td>
                            <td><strong><%= s.getSubject() %></strong></td>
                            <td><%= s.getCreatedAt() %></td>
                            <td>
                                <% if (s.isActive()) { %>
                                    <span class="badge badge-active">Active (Open)</span>
                                <% } else { %>
                                    <span class="badge badge-closed">Closed</span>
                                <% } %>
                            </td>
                            <td>
                                <% if (s.isActive()) { %>
                                    <a href="generate-qr.jsp?sessionId=<%= s.getId() %>&subject=<%= s.getSubject() %>" class="btn btn-primary btn-sm">Show QR</a>
                                    <a href="CloseSessionServlet?sessionId=<%= s.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to close attendance for this session?')">Close</a>
                                <% } %>
                                <a href="ReportServlet?sessionId=<%= s.getId() %>&subject=<%= s.getSubject() %>" class="btn btn-success btn-sm">View Attendance</a>
                            </td>
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
    </div>

</body>
</html>