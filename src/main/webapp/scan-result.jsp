<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String status = (String) request.getAttribute("status");
    if (status == null) status = request.getParameter("status");

    String message = (String) request.getAttribute("message");
    if (message == null) message = request.getParameter("message");

    String studentName = (String) request.getAttribute("studentName");
    String rollNumber = (String) request.getAttribute("rollNumber");
    String subject = (String) request.getAttribute("subject");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Status</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .result-box {
            max-width: 420px;
            margin: 60px auto;
            background: white;
            padding: 36px 24px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }
        .icon {
            font-size: 56px;
            margin-bottom: 16px;
        }
        .info-card {
            background: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            padding: 16px;
            margin: 20px 0;
            text-align: left;
            font-size: 15px;
        }
        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
        }
        .info-row:last-child {
            margin-bottom: 0;
        }
        .info-label {
            color: #64748b;
            font-weight: 500;
        }
        .info-value {
            font-weight: 600;
            color: #0f172a;
        }
    </style>
</head>
<body>

    <div class="result-box">
        <% if ("success".equals(status)) { %>
            <div class="icon">🎉</div>
            <h2 style="color: #16a34a; margin-bottom: 8px;">Success!</h2>
            <p style="color: #475569;"><%= message %></p>

            <div class="info-card">
                <div class="info-row">
                    <span class="info-label">Student:</span>
                    <span class="info-value"><%= studentName %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Roll Number:</span>
                    <span class="info-value"><%= rollNumber %></span>
                </div>
                <% if (subject != null) { %>
                <div class="info-row">
                    <span class="info-label">Subject:</span>
                    <span class="info-value"><%= subject %></span>
                </div>
                <% } %>
            </div>

        <% } else if ("warning".equals(status)) { %>
            <div class="icon">⚠️</div>
            <h2 style="color: #d97706; margin-bottom: 8px;">Notice</h2>
            <p style="color: #92400e; font-weight: 500;"><%= message %></p>

        <% } else { %>
            <div class="icon">❌</div>
            <h2 style="color: #dc2626; margin-bottom: 8px;">Failed</h2>
            <p style="color: #991b1b;"><%= message != null ? message : "An error occurred." %></p>
        <% } %>

        <div style="margin-top: 24px;">
            <p style="font-size: 13px; color: #94a3b8;">You may now safely close this browser window.</p>
        </div>
    </div>

</body>
</html>