
import static cerberus.AttFunctions.getAccess;
import static cerberus.AttFunctions.errorLogger;
import static cerberus.printer.kids;
import static cerberus.printer.nouser;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ajaxCheckEmail extends HttpServlet {

    private static final long serialVersionUID = -1766378544994615176L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int access = getAccess(request);
            switch (access) {
                case 1:
                    String email = request.getParameter("email");
                    if (Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)) {
                        int flag = 0;
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            try (Connection con = DriverManager.getConnection("jdbc:mysql://172.21.170.14:3306/cerberus?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "cerberus", "abc@123")) {
                                PreparedStatement ps = con.prepareStatement("select email from student where email=?");
                                ps.setString(1, email);
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                    flag = 1;
                                }
                                if (flag == 0) {
                                    ps = con.prepareStatement("select email from faculty where email=?");
                                    ps.setString(1, email);
                                    rs = ps.executeQuery();
                                    while (rs.next()) {
                                        flag = 1;
                                    }
                                }
                                con.close();
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            errorLogger(e.getMessage());
                        }
                        if (flag == 0) {
                            out.print("1");
                        } else {
                            out.print("2");
                        }
                    } else {
                        out.print("0");
                    }
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
