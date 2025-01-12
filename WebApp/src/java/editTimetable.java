
import static cerberus.AttFunctions.errorLogger;
import static cerberus.AttFunctions.getAccess;
import static cerberus.AttFunctions.getCurrWeekYear;
import static cerberus.AttFunctions.getNextWeekYear;
import static cerberus.AttFunctions.getPrevWeekYear;
import static cerberus.AttFunctions.no_of_labs;
import static cerberus.AttFunctions.oddEveSubs;
import static cerberus.printer.error;
import static cerberus.printer.kids;
import static cerberus.printer.nouser;
import static cerberus.printer.tablehead;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.threeten.extra.YearWeek;

public class editTimetable extends HttpServlet {

    private static final long serialVersionUID = 1L;
    LocalDate wks;
    int week = 0, year = 0;
    String date[] = new String[6];
    String subs[][];

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int access = getAccess(request);
            switch (access) {
                case 1:
                    try {
                        week = Integer.parseInt(request.getParameter("week"));
                        year = Integer.parseInt(request.getParameter("year"));
                    } catch (NumberFormatException e) {
                        String weekYear[] = getCurrWeekYear().split(",");
                        week = Integer.parseInt(weekYear[1]);
                        year = Integer.parseInt(weekYear[0]);
                    }
                    YearWeek weekYeardate = YearWeek.of(year, week);
                    date[0] = weekYeardate.atDay(DayOfWeek.MONDAY) + "";
                    date[1] = weekYeardate.atDay(DayOfWeek.TUESDAY) + "";
                    date[2] = weekYeardate.atDay(DayOfWeek.WEDNESDAY) + "";
                    date[3] = weekYeardate.atDay(DayOfWeek.THURSDAY) + "";
                    date[4] = weekYeardate.atDay(DayOfWeek.FRIDAY) + "";
                    date[5] = weekYeardate.atDay(DayOfWeek.SATURDAY) + "";
                    wks = weekYeardate.atDay(DayOfWeek.MONDAY).plusDays(-1);
                    int labid;
                    try {
                        labid = Integer.parseInt(request.getParameter("lab"));
                    } catch (NumberFormatException e) {
                        labid = 1;
                    }
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://172.21.170.14:3306/cerberus?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "cerberus", "abc@123");
                        subs = oddEveSubs();
                        out.print("<style>"
                                + "input[type=number]{"
                                + "width: 61px;"
                                + "height: 40px;"
                                + "} "
                                + ".not-allowed {cursor: not-allowed;}"
                                + "</style>");
                        out.print("<script>"
                                + "function zeroPad(num) {"
                                + "var s = num+'';"
                                + "while (s.length < 2) s = '0' + s;"
                                + "return(s);}");
                        out.print("function batchdisable(id) {"
                                + "var index = document.getElementById(id).selectedIndex;"
                                + "if(index == 0)"
                                + "{id = id.substr(1);"
                                + "document.getElementById('batch' + id).selectedIndex=0;"
                                + "document.getElementById('batch' + id).disabled=true;"
                                + "document.getElementById('batch' + id).classList.add('not-allowed');}"
                                + "else{id = id.substr(1);"
                                + "document.getElementById('batch' + id).selectedIndex=1;"
                                + "document.getElementById('batch' + id).disabled=false;"
                                + "document.getElementById('batch' + id).classList.remove('not-allowed');}}");
                        out.print("function subsdisable(id) {"
                                + "var index = document.getElementById(id).selectedIndex;"
                                + "if(index == 0)"
                                + "{id = id.substr(5);"
                                + "document.getElementById('c' + id).selectedIndex=0;}"
                                + "document.getElementById('batch' + id).disabled=true;"
                                + "document.getElementById('batch' + id).classList.add('not-allowed');}"
                                + "</script>");
                        String heading = "<div class='row'><div class='col-xl-4 col-sm-6 mb-3 mt-3' align='center'><form action=\"";
                        String prevweekYear[] = getPrevWeekYear(week, year).split(",");
                        int prevweek = Integer.parseInt(prevweekYear[1]);
                        int prevyear = Integer.parseInt(prevweekYear[0]);
                        String weekYear[] = getCurrWeekYear().split(",");
                        int currweek = Integer.parseInt(weekYear[1]);
                        String nextweekYear[] = getNextWeekYear(week, year).split(",");
                        int nextweek = Integer.parseInt(nextweekYear[1]);
                        int nextyear = Integer.parseInt(nextweekYear[0]);
                        heading += "javascript:setContent('/Cerberus/editTimetable?week=" + prevweek + "&year=" + prevyear + "&lab=" + labid + "')\" >";
                        heading += "<button type=\"submit\"  style='width: 100px;' id=\"prev\" class=\"btn btn-primary\"";
                        heading += "><span>Previous</span>"
                                + "</button>"
                                + "</form></div>"
                                + "<div class='col-xl-4 col-sm-6 mb-3 mt-3' align='center'>Current Week : " + currweek + "";
                        heading += "<p align='center'>Displaying Timetable of Week : " + week + "</p>";
                        heading += "<p align='center'>LAB " + labid + " <br><b>" + wks + "</b> to <b>" + wks.plusDays(7) + "</b></p>";
                        heading += "</div><div class='col-xl-4 col-sm-6 mb-3 mt-3' align='center'><form action=\"";
                        heading += "javascript:setContent('/Cerberus/editTimetable?week=" + nextweek + "&year=" + nextyear + "&lab=" + labid + "')\" >";
                        heading += "<button type=\"submit\"  style='width: 100px;' id=\"next\" class=\"btn btn-primary\"";
                        heading += "><span>Next</span>"
                                + "</button>"
                                + "</form></div>";
                        heading += "</div>";
                        out.print("<div class='card'><div class='card-header'>" + heading + "</div>"
                                + "<form id='ajaxform' action='saveTimetable' method='post' align='center'>"
                                + "<div class='card-body'>"
                                + "<div class='table-responsive'>"
                                + "<table class='table table-bordered table-striped' width='100%' cellspacing='0'>");
                        out.print(printTimetable(labid));
                        String end = "</table></div></div>"
                                + "<input type='text' name='lab' value='" + labid + "' hidden>"
                                + "<input type='text' name='week' value='" + week + "' hidden>"
                                + "<input type='text' name='year' value='" + year + "' hidden>"
                                + "<button align='center' style='width: 200px;'type=\"submit\" id=\"sub\" class=\"btn btn-primary\">"
                                + "<span>Save Timetable</span>"
                                + "</button></form>";
                        end += "<br><div class='card-footer small text-muted'><div id='validations' style='color:red;font-size:14px;'>Only Lab Sessions that have not been marked as conducted will be changed when using Copy Timetable Feature</div><br>"
                                + "<div class='row'>";
                        for (int lab = 1; lab <= no_of_labs(); lab++) {
                            end += "<div class='col mb-3'>"
                                    + "<form action='copyTimetable' method='post' align='center'>"
                                    + "<input type='text' name='week' value='" + week + "' hidden>"
                                    + "<input type='text' name='lab' value='" + labid + "' hidden>"
                                    + "<input type='text' name='modlab' value='" + lab + "' hidden>"
                                    + "<input type='text' name='year' value='" + year + "' hidden>"
                                    + "<input type='week' style='background: #e6e6e6; font-size: 14.5px; padding: 3px 6px 3px 4px; border: none; margin: 6px; border-radius: 4px;' name='modweekyear' value='" + year + "-W" + String.format("%02d", week) + "'><br><br>"
                                    + "<button align='center' style='width: 200px;' type=\"submit\" id=\"sub\" class=\"btn btn-primary\">"
                                    + "<span>Copy From Selected Week's Lab " + lab + "</span>"
                                    + "</button></form>"
                                    + "<form action='copyTimetable' method='post' align='center'>"
                                    + "<input type='text' name='week' value='" + week + "' hidden>"
                                    + "<input type='text' name='lab' value='" + labid + "' hidden>"
                                    + "<input type='text' name='modlab' value='" + lab + "' hidden>"
                                    + "<input type='text' name='year' value='" + year + "' hidden>"
                                    + "<input type='text' name='modweekyear' value='0-W0' hidden><br><br>"
                                    + "<button align='center' style='width: 200px;' type=\"submit\" id=\"sub\" class=\"btn btn-primary\">"
                                    + "<span>Copy From Master Timetable's Lab " + lab + "</span>"
                                    + "</button></form>"
                                    + "</div>";
                        }
                        end += "</div></div>";
                        out.print(end + "</div>");
                        con.close();
                    } catch (ParseException | ClassNotFoundException | SQLException e) {
                        error(e.getMessage());
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

    public String printTimetable(int labID) throws ParseException {

        String table = "";
        table += ("<tr align = center>");
        table += ("<th style=\"white-space:nowrap;\" >Start Time</th>");
        table += ("<th>End Time</th>");
        table += ("<th>Monday<br>" + date[0] + "</th>");
        table += ("<th>Tuesday<br>" + date[1] + "</th>");
        table += ("<th>Wednesday<br>" + date[2] + "</th>");
        table += ("<th>Thursday<br>" + date[3] + "</th>");
        table += ("<th>Friday<br>" + date[4] + "</th>");
        table += ("<th>Saturday<br>" + date[5] + "</th>");
        table += ("</tr>");
        table = tablehead(table);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://172.21.170.14:3306/cerberus?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "cerberus", "abc@123");

            PreparedStatement ps = con.prepareStatement("SELECT slot.slotID, slot.startTime, slot.endTime, "
                    + "MAX(CASE WHEN dayID = 1 THEN concat(timetable.subjectID,' - ',(select batch.name from batch where timetable.batchID=batch.batchID)) END) as Monday, "
                    + "MAX(CASE WHEN dayID = 2 THEN concat(timetable.subjectID,' - ',(select batch.name from batch where timetable.batchID=batch.batchID)) END) as Tuesday, "
                    + "MAX(CASE WHEN dayID = 3 THEN concat(timetable.subjectID,' - ',(select batch.name from batch where timetable.batchID=batch.batchID)) END) as Wednesday, "
                    + "MAX(CASE WHEN dayID = 4 THEN concat(timetable.subjectID,' - ',(select batch.name from batch where timetable.batchID=batch.batchID)) END) as Thursday, "
                    + "MAX(CASE WHEN dayID = 5 THEN concat(timetable.subjectID,' - ',(select batch.name from batch where timetable.batchID=batch.batchID)) END) as Friday, "
                    + "MAX(CASE WHEN dayID = 6 THEN concat(timetable.subjectID,' - ',(select batch.name from batch where timetable.batchID=batch.batchID)) END) as Saturday "
                    + "FROM timetable "
                    + "INNER JOIN slot "
                    + "ON timetable.slotID = slot.slotID "
                    + "where labID=? and weekID=(select weekID from week where week = ? and year = ?) "
                    + "GROUP BY slot.startTime, slot.endTime;");
            ps.setInt(1, labID);
            ps.setInt(2, week);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            PreparedStatement ps7 = con.prepareStatement("SELECT * from slot");
            ResultSet rs1 = ps7.executeQuery();
            String slots[][];
            int no_of_slots = 0;
            while (rs1.next()) {
                no_of_slots++;
            }
            rs1.first();
            rs1.previous();
            slots = new String[no_of_slots][2];
            no_of_slots = 0;
            while (rs1.next()) {
                slots[no_of_slots][0] = rs1.getString(2).substring(0, 5);
                slots[no_of_slots][1] = rs1.getString(3).substring(0, 5);
                no_of_slots++;
            }
            no_of_slots--;
            String lines[] = new String[(no_of_slots + 2)];
            for (int y = 0; y <= no_of_slots; y++) {
                lines[y] = "";
            }
            while (rs.next()) {
                lines[rs.getInt(1) - 1] = ("<tr> ");
                String time = rs.getString(3);
                lines[rs.getInt(1) - 1] += ("<th>" + String.format("%02d", Integer.parseInt(rs.getString(2).substring(0, 2))) + " : " + String.format("%02d", Integer.parseInt(rs.getString(2).substring(3, 5))) + "</th>");
                lines[rs.getInt(1) - 1] += ("<th>" + String.format("%02d", Integer.parseInt(rs.getString(3).substring(0, 2))) + " : " + String.format("%02d", Integer.parseInt(rs.getString(3).substring(3, 5))) + "</th>");
                for (int j = 1; j <= 6; j++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    if (time.equals("12:00:00")) {
                        time = "11:59:59";
                    }
                    String dateInString = date[j - 1] + " " + time;
                    Date datetime = sdf.parse(dateInString);
                    Date now = new Date();
                    long nowmill = now.getTime();
                    long datetimemill = datetime.getTime();
                    String disabled = "";
                    String disabledstyl = "style='border:solid 1px green'";
                    if (nowmill > datetimemill) {
                        disabled = " disabled ";
                        disabledstyl = "style='border:solid 1px red'";
                    }
                    lines[rs.getInt(1) - 1] += ("<td align='center'>");
                    lines[rs.getInt(1) - 1] += ("<select class=\"editSelectTimeTable\" name = 'c" + (rs.getInt(1) - 1) + "" + j + "' id = 'c" + (rs.getInt(1) - 1) + "" + j + "'  onchange = 'batchdisable(this.id)' " + disabledstyl + ">");
                    lines[rs.getInt(1) - 1] += ("<option name='Sub' value='-'");
                    String[] arrOfsub = null;
                    int flag;
                    if (rs.getString(j + 3) == null) {
                        lines[rs.getInt(1) - 1] += ("selected ");
                        flag = 0;
                    } else {
                        lines[rs.getInt(1) - 1] += (disabled);
                        arrOfsub = rs.getString(j + 3).split(" - ");
                        flag = 1;
                    }
                    lines[rs.getInt(1) - 1] += (">No Lab</option>");
                    for (int k = 0; k <= subs.length - 1; k++) {
                        lines[rs.getInt(1) - 1] += ("<option name='Sub' value='" + subs[k][0] + "' ");
                        if (flag == 1) {
                            if (subs[k][0].equals(arrOfsub[0])) {
                                lines[rs.getInt(1) - 1] += ("selected ");
                            } else {
                                lines[rs.getInt(1) - 1] += (disabled);
                            }
                        }
                        if (flag == 0) {
                            lines[rs.getInt(1) - 1] += (disabled);
                        }
                        lines[rs.getInt(1) - 1] += (">" + subs[k][1] + "</option>");
                    }

                    lines[rs.getInt(1) - 1] += ("</select><br>");
                    lines[rs.getInt(1) - 1] += ("<select onchange = 'subsdisable(this.id)' class=\"editSelectTimeTable\" name = 'batch" + (rs.getInt(1) - 1) + "" + j + "' id = 'batch" + (rs.getInt(1) - 1) + "" + j + "'");
                    if (flag == 0) {
                        lines[rs.getInt(1) - 1] += ("disabled  class='not-allowed';");
                    }
                    lines[rs.getInt(1) - 1] += ("><option name='-' value='-'");
                    if (flag == 0) {
                        lines[rs.getInt(1) - 1] += ("selected");
                    } else {
                        lines[rs.getInt(1) - 1] += (disabled);
                    }
                    lines[rs.getInt(1) - 1] += (">No Batch</option>");
                    PreparedStatement ps11 = con.prepareStatement("Select name from batch where batchid>0");
                    ResultSet rs3 = ps11.executeQuery();
                    int index = 1;
                    while (rs3.next()) {
                        lines[rs.getInt(1) - 1] += ("<option name='" + rs3.getString(1) + "' value='" + index + "'");
                        if (flag == 1) {
                            if (rs3.getString(1).equals(arrOfsub[1])) {
                                lines[rs.getInt(1) - 1] += ("selected ");
                            } else {
                                lines[rs.getInt(1) - 1] += (disabled);
                            }
                        }
                        if (flag == 0) {
                            lines[rs.getInt(1) - 1] += (disabled);
                        }
                        lines[rs.getInt(1) - 1] += (">" + rs3.getString(1) + "</option>");
                        index++;
                    }
                    lines[rs.getInt(1) - 1] += ("</select>");
                    lines[rs.getInt(1) - 1] += ("</td>");
                }
                lines[rs.getInt(1) - 1] += ("</tr>");
            }
            for (int y = 0; y <= no_of_slots; y++) {
                if (lines[y].equals("")) {
                    lines[y] = ("<tr> ");
                    lines[y] += ("<th>" + String.format("%02d", Integer.parseInt(slots[y][0].substring(0, 2))) + " : " + String.format("%02d", Integer.parseInt(slots[y][0].substring(3, 5))) + "</th>");
                    lines[y] += ("<th>" + String.format("%02d", Integer.parseInt(slots[y][1].substring(0, 2))) + " : " + String.format("%02d", Integer.parseInt(slots[y][1].substring(3, 5))) + "</th>");
                    for (int j = 1; j <= 6; j++) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        if (slots[y][1].equals("12:00:00")) {
                            slots[y][1] = "11:59:59";
                        }
                        String dateInString = date[j - 1] + " " + slots[y][1] + ":00";
                        Date datetime = sdf.parse(dateInString);
                        Date now = new Date();
                        long nowmill = now.getTime();
                        long datetimemill = datetime.getTime();
                        String disabled = "";
                        String disabledstyl = "style='border:solid 1px green'";
                        if (nowmill > datetimemill) {
                            disabled = " disabled ";
                            disabledstyl = "style='border:solid 1px red'";
                        }
                        lines[y] += ("<td align='center'>");
                        lines[y] += ("<select class=\"editSelectTimeTable\" name = 'c" + y + "" + j + "' id = 'c" + y + "" + j + "' onchange = 'batchdisable(this.id)' " + disabledstyl + ">");
                        lines[y] += ("<option " + disabled + " name='Sub' value='-' selected>No Lab</option>");
                        for (int k = 0; k <= subs.length - 1; k++) {
                            lines[y] += ("<option " + disabled + " name='Sub' value='" + subs[k][0] + "'>" + subs[k][1] + "</option>");
                        }
                        lines[y] += ("</select><br>");
                        lines[y] += ("<select class=\"editSelectTimeTable\" name = 'batch" + y + "" + j + "' id = 'batch" + y + "" + j + "' disabled class='not-allowed'>");
                        lines[y] += ("<option " + disabled + " name='-' value='-' selected>No Batch</option>");
                        PreparedStatement ps11 = con.prepareStatement("Select name from batch where batchid>0");
                        ResultSet rs3 = ps11.executeQuery();
                        int index = 1;
                        while (rs3.next()) {
                            lines[y] += ("<option " + disabled + " name='A' value='" + index + "'>" + rs3.getString(1) + "</option>");
                            index++;
                        }
                        lines[y] += ("</select>");
                        lines[y] += ("</td>");
                    }
                    lines[y] += ("</tr>");
                }
                table += lines[y];
            }
            con.close();
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            errorLogger(e.getMessage());
        }
        return table;
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
