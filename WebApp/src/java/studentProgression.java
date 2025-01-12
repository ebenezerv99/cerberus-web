
import static cerberus.AttFunctions.getAccess;
import static cerberus.printer.kids;
import static cerberus.printer.nouser;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class studentProgression extends HttpServlet {

    private static final long serialVersionUID = -126704436612087153L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int access = getAccess(request);
            switch (access) {
                case 1:
                    out.println("Thank You for showing your interest"
                            + "<br><br>"
                            + "<b>This section of the website is still under testing.<br>"
                            + "You will be notified when this page is available."
                            + "<br><br></b>"
                            + "The following features have will be implemented here :<br>"
                            + "<ul style=' display:table; margin:0 auto;'>"
                            + "<li>Transfer students to next year (FY to SY, SY to TY)</li>"
                            + "<li>Change the number of labs, batches</li>"
                            + "<li>Add or Remove classes (FY,SY,TY,MSc.IT)</li>"
                            + "<li>Take Backup of all Data</li>"
                            + "<li>Upload Data of Students from Excel File</li>"
                            + "</ul><br>We would love to hear your suggestions"
                            + "<br>Please contact anyone of us at:"
                            + "<br>+91 89807 77667"
                            + "<br>+91 98791 92084"
                            + "<br><br><a href=\"http://172.21.170.14/phpmyadmin/db_export.php?db=cerberus\" target=\"_blank\">Click here to export Database manually</a><br>Username : cerberus<br>Password : abc@123");
                    break;
                case 0:
                    out.print(kids());
                    break;
                default:
                    out.print(nouser());
            }
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
