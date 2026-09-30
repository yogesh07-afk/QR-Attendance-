package servlet;

import dao.AttendanceDAO;
import dao.SessionDAO;
import dao.StudentDAO;
import model.Session;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ScanServlet")
public class ScanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String token = request.getParameter("token");
        String rollNumber = request.getParameter("rollNumber");

        if (token == null || token.trim().isEmpty()) {
            request.setAttribute("status", "error");
            request.setAttribute("message", "Invalid QR Code token!");
            request.getRequestDispatcher("scan-result.jsp").forward(request, response);
            return;
        }

        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            request.setAttribute("status", "error");
            request.setAttribute("message", "Please enter your roll number!");
            request.getRequestDispatcher("scan.jsp?token=" + token).forward(request, response);
            return;
        }

        // 1. Verify session by QR token
        SessionDAO sessionDAO = new SessionDAO();
        Session currentSession = sessionDAO.getByToken(token.trim());

        if (currentSession == null) {
            request.setAttribute("status", "error");
            request.setAttribute("message", "Session not found or QR is invalid!");
            request.getRequestDispatcher("scan-result.jsp").forward(request, response);
            return;
        }

        if (!currentSession.isActive()) {
            request.setAttribute("status", "error");
            request.setAttribute("message", "Attendance session is CLOSED by the teacher!");
            request.getRequestDispatcher("scan-result.jsp").forward(request, response);
            return;
        }

        // 2. Find student by roll number
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getByRollNumber(rollNumber.trim());

        if (student == null) {
            request.setAttribute("status", "error");
            request.setAttribute("message", "Roll Number " + rollNumber + " not registered in system!");
            request.getRequestDispatcher("scan-result.jsp").forward(request, response);
            return;
        }

        // 3. Check if student already marked attendance for this session (One Chance Only!)
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        boolean alreadyMarked = attendanceDAO.alreadyMarked(currentSession.getId(), student.getId());

        if (alreadyMarked) {
            request.setAttribute("status", "warning");
            request.setAttribute("message", "Attendance already marked for Roll No: " + student.getRollNumber() + " (" + student.getName() + ")!");
            request.getRequestDispatcher("scan-result.jsp").forward(request, response);
            return;
        }

        // 4. Mark attendance
        boolean marked = attendanceDAO.markAttendance(currentSession.getId(), student.getId());

        if (marked) {
            request.setAttribute("status", "success");
            request.setAttribute("studentName", student.getName());
            request.setAttribute("rollNumber", student.getRollNumber());
            request.setAttribute("subject", currentSession.getSubject());
            request.setAttribute("message", "Attendance marked successfully! ✅");
        } else {
            request.setAttribute("status", "error");
            request.setAttribute("message", "Database error while marking attendance. Please try again.");
        }

        request.getRequestDispatcher("scan-result.jsp").forward(request, response);
    }
}