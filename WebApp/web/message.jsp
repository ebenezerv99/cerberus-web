<%@ page import = "java.io.*,java.util.*" %>
<%
    int access = 2;
    try {
        access = Integer.parseInt(session.getAttribute("access").toString());
    } catch (Exception e) {
    }
    String redirect = "false", head = "Hi", body = "", fullpage = "true", sec = "", type = "", url = "index.jsp";
    try {
        type = request.getAttribute("type").toString();
    } catch (Exception e) {
        try {
            type = request.getParameter("type").toString();
        } catch (Exception d) {
            type = "";
        }
    }
    if (type.equals("login0")) {
        redirect = "false";
        head = "Security Firewall";
        body = "Invalid Username or Password. Please check your credentials and try again";
        url = "index.jsp";
        fullpage = "false";
    } else if (type.equals("login1")) {
        if (access == 1 || access == 0) {
            redirect = "false";
            head = "Login Successfull";
            body = "<font size = '3'>We are still at testing phase.<br><br>If you find any bugs <i class='fas fa-bug fa-lg'></i> , please feel free to report to us. "
                    + "We have hungry developers looking to eat some.<br><br>We would also love to hear your suggestions.</font>";
            url = "homepage";
            sec = "2";
            fullpage = "true";
        } else {
            redirect = "true";
            head = "Security Firewall";
            body = "Please login to continue";
            url = "index.jsp";
            fullpage = "false";
            sec = "2";
        }
    } else if (type.equals("login2")) {
        redirect = "false";
        fullpage = "false";
        head = "Nice Try!";
        body = "You're smart.<br>But not half as smart enough."
                + "<svg version='1.1' width = '150px' height = '150px' viewBox='-768 -512 2048 2048' >"
                + "<circle style='fill:#FFD93B;' cx='256' cy='256' r='256'/>"
                + "<path style='fill:#F4C534;' d='M512,256c0,141.44-114.64,256-256,256c-80.48,0-152.32-37.12-199.28-95.28	c43.92,35.52,99.84,56.72,160.72,56.72c141.36,0,256-114.56,256-256c0-60.88-21.2-116.8-56.72-160.72	C474.8,103.68,512,175.52,512,256z'/><path style='fill:#3E4347;' d='M494.4,148.816c0.736,1.984,0,4.24-1.712,5.616c-3.312,2.64-6.64,5.248-9.984,7.824	c-4.48,3.44-6.784,8.8-6.48,14.176c1.328,22.624-2.384,45.968-8.128,68.672c-5.28,19.088-15.568,22.8-35.088,26.864	c-53.232,11.424-112.992,11.968-124.272,10.384c-10.704-1.36-19.776-7.744-23.776-17.632c-4.88-12-9.776-28.432-13.92-44.112	c-1.632-6.48-7.904-10.992-14.96-11.008c-7.168,0.032-13.44,4.528-15.072,11.008c-4.144,15.696-9.024,32.144-13.92,44.112	c-4.096,9.888-13.072,16.336-23.776,17.632c-11.296,1.584-71.008,1.04-124.272-10.384c-19.52-4.048-29.936-7.776-35.088-26.864	c-5.84-23.264-9.648-47.296-7.936-70.48c0.416-5.52-1.888-10.96-6.432-14.48c-3.28-2.544-6.512-5.088-9.744-7.664	c-1.696-1.408-2.416-3.632-1.696-5.616c2.048-6.288,4.08-12.56,6.096-18.848c0.944-2.96,4.256-4.384,7.12-3.12	c10.48,4.544,21.056,8.912,31.728,13.088c1.28,0.544,2.656,0.816,4.032,0.992c76.464,7.488,112.112,29.824,188.848,31.04	c25.856-0.032,51.568-3.12,76.448-9.2c40.464-9.728,78.448-18.384,119.904-22.624c1.44-0.192,2.864-0.448,4.208-1.024	c8.272-3.328,16.496-6.784,24.656-10.336c2.848-1.168,6.176,0.192,7.12,3.12C490.32,136.224,492.368,142.528,494.4,148.816z'/><g>	<path style='fill:#5A5F63;' d='M209.696,258.32c-1.584,3.744-4.624,6.336-8.528,7.68c-11.568,4.48-89.728-3.392-116.464-10.128		c-10.944-2.32-17.84-2.688-20.656-14.208c-6.048-22.608-11.792-62.256-3.936-83.68c54.752,7.36,118.528,22.416,167.536,34.176		C224.064,214.544,218.128,236.992,209.696,258.32z'/>	<path style='fill:#5A5F63;' d='M284.352,192.16c49.12-11.776,112.736-26.864,167.536-34.176c7.84,21.408,2.096,61.056-3.952,83.68		c-2.816,11.472-9.712,11.888-20.656,14.192c-26.624,6.784-104.88,14.592-116.464,10.128c-3.904-1.312-6.944-3.952-8.528-7.68		C293.872,237.008,287.952,214.544,284.352,192.16z'/></g><g>	<path style='fill:#777B7E;' d='M451.888,157.984c-14.256,1.904-29.136,4.352-44.176,7.104l-99.44,99.44		c0.864,0.48,1.584,1.136,2.56,1.456c4.368,1.68,18.272,1.616,35.28,0.528L452.464,160.16		C452.24,159.472,452.128,158.64,451.888,157.984z'/>	<path style='fill:#777B7E;' d='M367.312,173.12l-70.368,70.368c1.52,4.528,3.136,9.024,4.864,13.456l88.56-88.56		C382.656,169.92,374.96,171.488,367.312,173.12z'/>	<path style='fill:#777B7E;' d='M130.576,170.224l-67.472,67.472c0.32,1.312,0.64,2.752,0.96,3.984		c2.816,11.52,9.712,11.888,20.656,14.208c0,0,0.032,0,0.048,0l78.672-78.672C152.592,174.8,141.616,172.464,130.576,170.224z'/>	<path style='fill:#777B7E;' d='M103.472,164.944l-45.44,45.44c0.704,5.472,1.6,10.72,2.56,15.76l58.272-58.272		C113.728,166.88,108.592,165.888,103.472,164.944z'/></g><path style='fill:#3E4347;' d='M393.232,352.928c9.776,5.616-45.472,85.808-137.232,85.808c-93.728,0-147.024-80.176-137.248-85.808	c7.344-7.44,62.432,42.48,137.248,42.48C330.768,395.408,385.888,345.456,393.232,352.928z'/></svg>";
        url = "index.jsp";
        sec = "2";
    } else if (type.equals("login3")) {
        redirect = "false";
        fullpage = "false";
        head = "Security Firewall";
        body = "You have exceeded allowed number of login attempts";
        url = "index.jsp";
        sec = "2";
    } else {
        redirect = request.getAttribute("redirect").toString();
        head = request.getAttribute("head").toString();
        body = request.getAttribute("body").toString();
        fullpage = request.getAttribute("fullpage").toString();
        url = request.getAttribute("url").toString();
        sec = request.getAttribute("sec").toString();
    }
    int ajax = 0;
    if (fullpage.equals("false") && redirect.equals("true")) {
        response.setHeader("Refresh", sec + ";url=" + url + "");
    } else if (fullpage.equals("true") && redirect.equals("true")) {
        response.setHeader("Refresh", sec + ";url=ajaxContent?url=" + url + "");
    } else if (fullpage.equals("true") && redirect.equals("false")) {
        ajax = 1;
    }
%>
<html lang="en"> <head> <meta charset="utf-8"> <meta http-equiv="X-UA-Compatible" content="IE=edge"> <meta name="viewport" content="width=device-width, initial-scale=1"> <title>Message </title> <style>html{overflow: hidden;}</style> <link rel="stylesheet" href="css/bootstrap.css" type="text/css"> <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"> <link rel="stylesheet" href="css/bootstrap-grid.css" type="text/css"> <link rel="stylesheet" href="css/bootstrap-grid.min.css" type="text/css"> <link rel="stylesheet" href="css/custom.css" type="text/css"> <link rel="stylesheet" href="css/loader.css" type="text/css"> <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"> <link rel="icon" href="images/logo-circle-removebg.png" type="image/gif"> <style>.body{-webkit-touch-callout: none; -webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none;}</style> </head> <body> <div class="container" > <div class="reset_page" > <div class="form_reset" style=" -webkit-touch-callout: none;-webkit-user-select: none;-khtml-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;min-height: 250px"> <div align="center"> <h2 style="font-family: arno pro caption"> <%out.print(head);%> </h2> <br><p><%out.print(body);%></p>
        <%if (redirect.equals("true")) {
                out.print("<small><b>You will be redirected shortly</b></small>"
                        + "<div class='loader'>"
                        + "<div class='duo duo1'>"
                        + "<div class='dot dot-a'>"
                        + "</div>"
                        + "<div class='dot dot-b'>"
                        + "</div>"
                        + "</div>"
                        + "<div class='duo duo2'>"
                        + "<div class='dot dot-a'>"
                        + "</div>"
                        + "<div class='dot dot-b'>"
                        + "</div>"
                        + "</div>"
                        + "</div><br>");
            } else {
                out.print("<footer><form action='");
                if (ajax == 1) {
                    out.print("ajaxContent' method='post'>"
                            + "<input name='url' type='text' value='" + url + "' hidden>");
                } else {
                    out.print(url + "' method='post'>");
                }
                out.print("<button type ='submit' autofocus>Accept</button>"
                        + "</form></footer>");
            }
        %>
                    </div></div></div></div><div id="particles-js"></div><script src="js/jquery.min.js"></script> <script src="js/main.js"></script> <script src="js/particles.js"></script> <script src="js/app.js"></script> </body></html>