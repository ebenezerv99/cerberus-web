
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static cerberus.AttFunctions.getAccess;
import static cerberus.printer.error;
import static cerberus.printer.kids;
import static cerberus.printer.nouser;

public class editSubject extends HttpServlet {

    private static final long serialVersionUID = 4158201167262012542L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int access = getAccess(request);
            switch (access) {
                case 1:
                    out.print("<style>\n"
                            + "input[type=number]{\n"
                            + "    width: 15px;\n"
                            + "} \n"
                            + "</style>");
                    String flow = "";
                    try {
                        flow = request.getParameter("flow");
                    } catch (Exception e) {
                        flow = "add";
                    }
                    if (flow.equals("add")) {
                        out.print("<style>"
                                + "input[type=number]{"
                                + "width: 40px;"
                                + "} "
                                + "</style>");
                        out.print("<script>"
                                + "function zeroPad(num)"
                                + "{"
                                + "var s = num+'';"
                                + "while (s.length < 2) s = '0' + s;"
                                + "return(s);"
                                + "}"
                                + "</script>");
                        out.print("<br><div align='center'><form action='addSubject' method='post'><table cellspacing='10'>"
                                + "<tr><td class=\"editSubjectStyle\">Subject Code</td><td> : </td><td><input required type='text' id='subid' onkeyup='checkValidations(3)' onchange='checkValidations(3)' name='subjectID' class=\"editSubjectForm\" minlength='7' maxlength='11' placeholder='BCAxxxx'/></td></tr>"
                                + "<tr><td class=\"editSubjectStyle\">Subject Name</td><td> : </td><td><input required type='text' name='subject' class=\"editSubjectForm\" placeholder='Artificial Intelligence'/></td></tr></tr><td colspan=2></td><td align='center'><font style=\"font-size: 12.5px; color: red;\"> *Please do not use abbreviations in Subject Name </font> </td></tr> "
                                + "<tr><td class=\"editSubjectStyle\">Subject Abbreviation</td><td> : </td><td align=''><input required type='text' name='abbr' class=\"editSubjectForm\" placeholder='AI' minlength='2' maxlength='10'/> </td></tr>"
                                + "<tr><td class=\"editSubjectStyle\">Semester</td><td> : </td><td ><input type=\"radio\" name=\"sem\"  class=\"editSelect\" value=\"1\" checked> <font style=\"font-size: 14.5px;\"> Odd </font>\n"
                                + " <input type=\"radio\" name=\"sem\" class=\"editSelect\" value=\"0\"><font style=\"font-size: 14.5px;\"> Even </font> </td></tr>"
                                + "<tr><td class=\"editSubjectStyle\">Select Class</td><td> : </td><td><select name = 'class' id = 'class' class=\"editSelect\">");
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://172.21.170.14:3306/cerberus?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "cerberus", "abc@123");
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT classID, class FROM `class` ORDER BY `class` ASC");
                            while (rs.next()) {
                                out.print("<option name='Sub' value= '" + rs.getInt(1) + "'>" + rs.getString(2) + "</option>");
                            }
                            con.close();
                            out.print("</select>");
                        } catch (ClassNotFoundException | SQLException e) {
                            error(e.getMessage());
                        }
                        out.print("</td></tr>"
                                + "</table><br><div id='validations' style='color:red;font-size:14px;'><br></div><br><button type='submit' id='studbtn1' style='width:200px;' class='btn btn-primary'>Add Subject</button>"
                                + "</form>");
                    } else if (flow.equals("del")) {
                        try {
                            out.print("<body onload='myFunction()'>");
                            out.print("<script>"
                                    + "function myFunction()"
                                    + "{if (document.getElementById('warn').checked==true) "
                                    + "{document.getElementById('butt').style.display = 'block';}"
                                    + "else"
                                    + "{document.getElementById('butt').style.display = 'none';}}"
                                    + "</script>");
                            out.print("<form action='deltSubject' method='post'>");
                            out.print("<div align='center'><br><font style=\"font-size: 17px; color: red;\"> Select the subject you want to delete : </font> <br><br>");
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://172.21.170.14:3306/cerberus?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "cerberus", "abc@123");
                            Statement stmt = con.createStatement();
                            String sql = "SELECT `subjectID`,`subject` from `subject`;";
                            ResultSet rs = stmt.executeQuery(sql);
                            String select = "<select name = 'subject' class=\"editSelect\">";
                            while (rs.next()) {
                                select += "<option name='Sub' value='" + rs.getString(1) + "'> " + rs.getString(1) + " - " + rs.getString(2) + " </option>";
                            }
                            select += "</select>";
                            out.print(select);
                            out.print("<br><fieldset>"
                                    + "<legend><br> <font style=\"font-size: 20px;\"> Warning - The following changes will be made: </font> <br></legend>"
                                    + "<p> <font style=\"font-size: 15.5px;\"> 1. Subjects with alloted lab session cannot be deleted. </font> </p>"
                                    + "<p> <font style=\"font-size: 15.5px;\"> 2. A subject opted by any student cannot be deleted. </font> </p>"
                                    + "<br><table><tr><td><input type='checkbox' id='warn' onclick='myFunction()'><label for='warn'></label></td><td>&nbsp;&nbsp; <font style=\"font-size: 15px; color: green;\"> I have read all the Warnings! </font></td></tr></table>"
                                    + "<br></fieldset><style type='text/css'>\n"
                                    + "@import url('css/checkbox.css');\n"
                                    + "</style>");
                            out.print("<br><div id = 'butt' style='display:none;'><button type='Delete' style='width:200px;' class='btn btn-primary'>Delete Subject</button></div>");
                            out.print("</form></div>");

                            con.close();
                        } catch (ClassNotFoundException | SQLException e) {
                            error(e.getMessage());
                        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
