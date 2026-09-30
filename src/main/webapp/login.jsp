<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Login - QR Attendance System</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .login-wrapper {
            max-width: 420px;
            margin: 80px auto;
            background: white;
            padding: 36px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }
        .login-wrapper h2 {
            text-align: center;
            margin-bottom: 8px;
        }
        .login-wrapper p {
            text-align: center;
            color: #64748b;
            margin-bottom: 24px;
        }
    </style>
</head>
<body>

    <div class="login-wrapper">
        <h2>QR Attendance</h2>
        <p>Teacher Portal</p>

        <%
            String error = (String) request.getAttribute("errorMessage");
            if (error != null) {
        %>
            <div class="alert alert-error"><%= error %></div>
        <%
            }
        %>

        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" required placeholder="shefali@college.com">
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required placeholder="Enter password">
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%;">Log In</button>
        </form>
    </div>

</body>
</html>