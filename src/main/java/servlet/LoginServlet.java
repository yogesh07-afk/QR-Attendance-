package servlet;

import dao.TeacherDAO;
import model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.login(email, password);

        if (teacher != null) {
            // Login success: Store teacher in session
            HttpSession session = request.getSession();
            session.setAttribute("teacher", teacher);
            response.sendRedirect("dashboard.jsp");
        } else {
            // Login failed: Send back with error message
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}