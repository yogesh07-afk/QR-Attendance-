<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String token = request.getParameter("token");
    if (token == null || token.trim().isEmpty()) {
%>
    <jsp:forward page="scan-result.jsp">
        <jsp:param name="status" value="error"/>
        <jsp:param name="message" value="Invalid QR Code! No token provided."/>
    </jsp:forward>
<%
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mark Attendance</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .mobile-container {
            max-width: 400px;
            margin: 40px auto;
            background: white;
            padding: 28px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }
        .header {
            text-align: center;
            margin-bottom: 24px;
        }
        .header h2 {
            margin-bottom: 6px;
            color: #0f172a;
        }
        .header p {
            color: #64748b;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <div class="mobile-container">
        <div class="header">
            <h2>📱 Mark Attendance</h2>
            <p>Please enter your registered college roll number.</p>
        </div>

        <%
            String error = (String) request.getAttribute("message");
            if (error != null) {
        %>
            <div class="alert alert-error"><%= error %></div>
        <%
            }
        %>

        <form action="ScanServlet" method="post">
            <!-- Hidden field to carry the QR token -->
            <input type="hidden" name="token" value="<%= token %>">

            <div class="form-group">
                <label>Roll Number</label>
                <input type="text" name="rollNumber" required placeholder="e.g. 152, 163, 146, 154" autofocus>
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%;">
                Submit Attendance ✅
            </button>
        </form>

        <p style="font-size: 13px; color: #94a3b8; text-align: center; margin-top: 16px;">
            Note: You only have one chance to mark attendance for this lecture.
        </p>
    </div>

</body>
</html>