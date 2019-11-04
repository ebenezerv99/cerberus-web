/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cerberus.AttFunctions;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class newTimetable extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int week = Integer.parseInt(request.getParameter("week"));
        String pwd = "";
        try {
            pwd = AttFunctions.hashIt(request.getParameter("pwd"));
        } catch (NoSuchAlgorithmException e) {
        }
        if (pwd.equals("0959aab211c167df361128977811cdf1a2a46e8e47200e11dadb68b9dcb6b2ad")) {
            int weekid = 0;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cerberus?zeroDateTimeBehavior=convertToNull", "root", "");
                PreparedStatement ps6 = con.prepareStatement("SELECT weekID FROM WEEK where week = ?");
                ps6.setInt(1, week);
                ResultSet rs = ps6.executeQuery();
                while (rs.next()) {
                    weekid = rs.getInt(1);
                }
                if (weekid == 0) {
                    PreparedStatement ps2 = con.prepareStatement("insert into week(`week`) values(?)");
                    ps2.setInt(1, week);
                    ps2.executeUpdate();
                    rs = ps6.executeQuery();
                    while (rs.next()) {
                        weekid = rs.getInt(1);
                    }
                }
                PreparedStatement ps5 = con.prepareStatement("SELECT * FROM timetable where weekID = ?");
                ps5.setInt(1, weekid);
                rs = ps5.executeQuery();
                int flag = 0;
                while (rs.next()) {
                    flag = 1;
                    break;
                }
                if (flag == 0) {
                    PreparedStatement ps10 = con.prepareStatement("SELECT weekID FROM `week` ORDER BY `week`.`weekID` DESC");
                    rs = ps10.executeQuery();
                    while (rs.next() && flag == 0) {
                        PreparedStatement ps9 = con.prepareStatement("SELECT * FROM timetable where weekID = ?");
                        ps9.setInt(1, rs.getInt(1));
                        ResultSet rs1 = ps9.executeQuery();
                        while (rs1.next()) {
                            PreparedStatement ps3 = con.prepareStatement("insert into timetable (slotID, labID, subjectID, batchID, weekID, dayID) select slotID, labID, subjectID, batchID, ?, dayID from timetable where weekID = ?");
                            ps3.setInt(1, weekid);
                            ps3.setInt(2, rs.getInt(1));
                            ps3.executeUpdate();
                            flag = 1;
                            break;
                        }
                        break;
                    }
                }
                con.close();
            } catch (ClassNotFoundException | SQLException e) {
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
}