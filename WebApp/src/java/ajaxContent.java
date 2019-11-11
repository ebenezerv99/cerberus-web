
import static cerberus.AttFunctions.getAccess;
import cerberus.messages;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ajaxContent extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int access = getAccess(request);
        if (access == 1 || access == 0) {
            String url;
            try {
                url = request.getAttribute("url").toString();
            } catch (Exception e) {
                try {
                    url = request.getParameter("url");
                } catch (Exception x) {
                    url = "homepage";
                }
            }   
            if (access == 1) {
                request.getRequestDispatcher("side-faculty.jsp").include(request, response);
            } else if (access == 0) {
                request.getRequestDispatcher("side-student.jsp").include(request, response);
            }
            request.setAttribute("url", url);
            request.getRequestDispatcher("end.jsp").include(request, response);
        } else {
            messages b = new messages();
            b.unauthaccess(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
