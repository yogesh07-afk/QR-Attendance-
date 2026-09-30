package servlet;

import dao.SessionDAO;
import model.Teacher;
import util.QRCodeGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

@WebServlet("/CreateSessionServlet")
public class CreateSessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("teacher") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        String subject = request.getParameter("subject");

        if (subject == null || subject.trim().isEmpty()) {
            response.sendRedirect("dashboard.jsp?error=Subject cannot be empty");
            return;
        }

        // 1. Generate unique QR token
        String qrToken = UUID.randomUUID().toString();

        // 2. Save session in MySQL database
        SessionDAO sessionDAO = new SessionDAO();
        int sessionId = sessionDAO.createSession(teacher.getId(), subject, qrToken);

        if (sessionId != -1) {
            try {
                // 3. Construct URL that the QR code will open when scanned by a phone
                // We use your local machine's IP address so phones on same Wi-Fi can open it!
                String hostIp = InetAddress.getLocalHost().getHostAddress();
                int port = request.getServerPort();
                String contextPath = request.getContextPath();
                
                String scanUrl = "http://" + hostIp + ":" + port + contextPath + "/scan.jsp?token=" + qrToken;

                // 4. Save QR image to WebContent/qrcodes/session_<id>.png
                String qrDir = request.getServletContext().getRealPath("/qrcodes");
                File dir = new File(qrDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String qrFilePath = qrDir + File.separator + "session_" + sessionId + ".png";
                QRCodeGenerator.generateQRCodeImage(scanUrl, 300, 300, qrFilePath);

                // 5. Redirect to generate-qr.jsp to display the QR code
                response.sendRedirect("generate-qr.jsp?sessionId=" + sessionId + "&subject=" + subject);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("dashboard.jsp?error=Failed to generate QR");
            }
        } else {
            response.sendRedirect("dashboard.jsp?error=Failed to create session");
        }
    }
}