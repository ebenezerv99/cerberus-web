
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;

public class resetpass extends HttpServlet {

    public static String hashIt(String raw) throws NoSuchAlgorithmException {
        raw = raw + "msubca";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        BigInteger number = new BigInteger(1, md.digest(raw.getBytes(StandardCharsets.UTF_8)));
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public String trimSQLInjection(String str) {
        str = str.replaceAll("\\s+", "");
        str = str.replaceAll("[A-Za-z0-9]+", "");
        str = str.replaceAll("\"", "'");
        return (str);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String otp = request.getParameter("otp");
            String pass = request.getParameter("conpass");
            if (trimSQLInjection(otp).equals("'''='") || trimSQLInjection(pass).equals("'''='")) {
                RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                request.setAttribute("redirect", "true");
                request.setAttribute("head", "Nice Try!");
                request.setAttribute("body", "You're smart.<br>But not half as smart enough.<br><br>" + new String(Character.toChars(0x1F60F)));
                request.setAttribute("url", "index.html");
                request.setAttribute("sec", "2");
                rd.forward(request, response);
            } else {
                int otp_count = 0;
                String corrotp = null;
                pass = hashIt(pass);
                otp = otp.toUpperCase();
                otp = hashIt(otp);
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cerberus?zeroDateTimeBehavior=convertToNull", "root", "");
                    PreparedStatement ps = con.prepareStatement("select otp from `otp` where email=?");
                    ps.setString(1, email);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        otp_count++;
                        corrotp = rs.getString(1);
                    }
                    con.close();
                } catch (ClassNotFoundException | SQLException e) {
                    RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                    request.setAttribute("redirect", "false");
                    request.setAttribute("head", "Error");
                    request.setAttribute("body", e.getMessage());
                    request.setAttribute("url", "resetpassword.html");
                    rd.forward(request, response);
                }
                if (otp_count == 1) {
                    if (corrotp.equals(otp)) {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cerberus?zeroDateTimeBehavior=convertToNull", "root", "");
                            PreparedStatement ps = con.prepareStatement("UPDATE `student` SET password = ? where email=?");
                            ps.setString(1, pass);
                            ps.setString(2, email);
                            ps.executeUpdate();
                            ps = con.prepareStatement("UPDATE `faculty` SET password = ? where email=?");
                            ps.setString(1, pass);
                            ps.setString(2, email);
                            ps.executeUpdate();
                            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                            request.setAttribute("redirect", "true");
                            request.setAttribute("head", "Security Message");
                            request.setAttribute("body", "Your password has been updated.<br> Please login with your new credentials");
                            request.setAttribute("url", "index.html");
                            request.setAttribute("sec", "2");
                            rd.forward(request, response);
                        } catch (ClassNotFoundException | SQLException e) {
                            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                            request.setAttribute("redirect", "false");
                            request.setAttribute("head", "Error");
                            request.setAttribute("body", e.getMessage());
                            request.setAttribute("url", "resetpassword.html");
                            rd.forward(request, response);
                        }
                    } else {
                        RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                        request.setAttribute("redirect", "false");
                        request.setAttribute("head", "Security Firewall");
                        request.setAttribute("body", "Please cheack your username and the OTP you provided and try again.");
                        request.setAttribute("url", "resetpassword.html");
                        request.setAttribute("button", "Redirect");
                        rd.forward(request, response);
                    }
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
                    request.setAttribute("redirect", "false");
                    request.setAttribute("head", "Security Firewall");
                    request.setAttribute("body", "An OTP was not found for the provided email address.");
                    request.setAttribute("url", "index.html");
                    request.setAttribute("button", "Redirect");
                    rd.forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
        request.setAttribute("redirect", "true");
        request.setAttribute("head", "Security Firewall");
        request.setAttribute("body", "Unauthorized access to this page has been detected.");
        request.setAttribute("url", "index.html");
        request.setAttribute("sec", "2");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
