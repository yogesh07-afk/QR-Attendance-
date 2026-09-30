package servlet;

import dao.SessionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CloseSessionServlet")
public class CloseSessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("teacher") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String sessionIdStr = request.getParameter("sessionId");
        if (sessionIdStr != null) {
            try {
                int sessionId = Integer.parseInt(sessionIdStr);
                SessionDAO sessionDAO = new SessionDAO();
                sessionDAO.closeSession(sessionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("dashboard.jsp");
    }
}