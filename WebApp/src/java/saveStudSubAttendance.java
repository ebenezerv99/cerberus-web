
import static cerberus.AttFunctions.errorLogger;
import static cerberus.AttFunctions.currUserName;
import static cerberus.AttFunctions.dbLog;
import static cerberus.AttFunctions.getAccess;
import static cerberus.AttFunctions.getCurrTime;
import static cerberus.AttFunctions.getTimeID;
import static cerberus.AttFunctions.get_class_from_sub;
import cerberus.messages;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class saveStudSubAttendance extends HttpServlet {

    private static final long serialVersionUID = 7583683061797848576L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int access = getAccess(request);
        switch (access) {
            case 1:
                try {
                    String subjectID = request.getParameter("subjectid");
                    String prn = request.getParameter("prn");
                    String schedules[] = (request.getParameter("schedules")).split(",");
                    int no_of_subs = schedules.length;
                    for (int x = 0; x < no_of_subs; x++) {
                        int scheduleid = Integer.parseInt(schedules[x]);
                        int timeID = getTimeID(getCurrTime());
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://172.21.170.14:3306/cerberus?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "cerberus", "abc@123");
                            PreparedStatement insert = con.prepareStatement("insert into attendance values(null,?,?,?)");
                            PreparedStatement delete = con.prepareStatement("delete from attendance where scheduleid=? and prn = ?");
                            String att = request.getParameter("att" + (x + 1) + "," + scheduleid);
                            if (att != null) {
                                try {
                                    PreparedStatement select = con.prepareStatement("select attendance.attendanceID from attendance where prn = ? and scheduleid = ?");
                                    select.setString(1, prn);
                                    select.setInt(2, scheduleid);
                                    ResultSet check = select.executeQuery();
                                    int dup = 0;
                                    while (check.next()) {
                                        dup = 1;
                                    }
                                    if (dup != 1) {
                                        insert.setString(1, prn);
                                        insert.setInt(2, scheduleid);
                                        insert.setInt(3, timeID);
                                        insert.executeUpdate();
                                        dbLog(currUserName(request) + " marked attendance for " + prn + " as present for scheduleID " + scheduleid);
                                    }
                                } catch (SQLException y) {
                                    errorLogger(y.getMessage());
                                }
                            } else {
                                try {
                                    delete.setInt(1, scheduleid);
                                    delete.setString(2, prn);
                                    delete.executeUpdate();
                                    dbLog(currUserName(request) + " marked attendance for " + prn + " as absent for scheduleID " + scheduleid);
                                } catch (SQLException y) {
                                    errorLogger(y.getMessage());
                                }
                            }
                            con.close();
                        } catch (ClassNotFoundException | SQLException z) {
                            errorLogger(z.getMessage());
                            messages b = new messages();
                            b.error(request, response, z.getMessage(), "viewTimetable");
                        }
                    }
                    messages a = new messages();
                    a.success(request, response, "Attendance has been saved", "attendance?class=" + get_class_from_sub(subjectID));
                } catch (NumberFormatException e) {
                    errorLogger(e.getMessage());
                    messages b = new messages();
                    b.error(request, response, e.getMessage(), "viewTimetable");
                }
                break;
            case 0:
                RequestDispatcher rd = request.getRequestDispatcher("message.jsp?type=login2");
                rd.forward(request, response);
                break;
            default:
                messages c = new messages();
                c.nouser(request, response);
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

}
