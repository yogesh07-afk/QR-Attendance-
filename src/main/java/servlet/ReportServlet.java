package servlet;

import dao.AttendanceDAO;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("teacher") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String sessionIdStr = request.getParameter("sessionId");
        String subject = request.getParameter("subject");

        if (sessionIdStr != null) {
            try {
                int sessionId = Integer.parseInt(sessionIdStr);
                AttendanceDAO attendanceDAO = new AttendanceDAO();
                List<Student> attendees = attendanceDAO.getAttendanceBySession(sessionId);

                request.setAttribute("attendees", attendees);
                request.setAttribute("sessionId", sessionId);
                request.setAttribute("subject", subject != null ? subject : "Session #" + sessionId);
                request.getRequestDispatcher("attendance-report.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("dashboard.jsp");
    }
}