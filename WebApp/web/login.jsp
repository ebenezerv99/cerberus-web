<%@page import="java.text.SimpleDateFormat"%>
<%
    try {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat("w");
        int week = Integer.parseInt(ft.format(date));
        session.setAttribute("week", week);
        request.getRequestDispatcher("newTimetable?week=" + week + "&pwd=cerberus@123").include(request, response);
        int access;
        try {
            access = Integer.parseInt(session.getAttribute("access").toString());
        } catch (Exception e) {
            session.setAttribute("access", 2);
        }
    } catch (Exception m) {
    }

%>
<div class="row custom_row">
    <div class="col-lg-6 my-auto">
        <div class="main_content mx-auto">
            <img src="images/logomain.png" style="width: 101px; height: 101px; margin-bottom: 70px; pointer-events: none;">
            <svg width="331" height="34" viewBox="0 0 331 34" fill="none" id="logo">
            <path d="M7.61 1.9C1.633 1.9 1.633 7.877 1.633 7.877V26.023C1.633 26.023 1.633 32 7.61 32H14.49C15.393 32 15.78 31.613 15.78 30.71V30.151C15.78 29.248 15.393 28.861 14.49 28.861H7.61C4.987 28.861 4.987 26.238 4.987 26.238V7.662C4.987 7.662 4.987 5.039 7.61 5.039H14.49C15.393 5.039 15.78 4.652 15.78 3.749V3.19C15.78 2.287 15.393 1.9 14.49 1.9H7.61Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M48.1852 32H60.8702C61.7732 32 62.1602 31.613 62.1602 30.71V29.936C62.1602 29.033 61.7732 28.646 60.8702 28.646H52.0552C51.1522 28.646 50.7652 28.259 50.7652 27.356V19.573C50.7652 18.67 51.1522 18.283 52.0552 18.283H58.7202C59.6232 18.283 60.0102 17.896 60.0102 16.993V16.219C60.0102 15.316 59.6232 14.929 58.7202 14.929H52.0552C51.1522 14.929 50.7652 14.542 50.7652 13.639V6.544C50.7652 5.641 51.1522 5.254 52.0552 5.254H60.7842C61.6872 5.254 62.0742 4.867 62.0742 3.964V3.19C62.0742 2.287 61.6872 1.9 60.7842 1.9H48.1852C47.7122 1.9 47.3682 2.287 47.3682 2.717V31.183C47.3682 31.613 47.7122 32 48.1852 32Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M98.0914 20.175C98.9944 20.175 99.5104 20.519 99.8544 21.379L103.982 30.796C104.326 31.656 104.842 32 105.745 32H106.863C107.508 32 107.809 31.828 107.809 31.398C107.809 31.226 107.766 31.054 107.68 30.796L103.337 20.863C103.208 20.562 103.122 20.261 103.122 20.046C103.122 19.444 103.509 19.1 103.681 18.971C106.433 17.294 107.594 14.155 107.594 10.629C107.594 5.598 104.627 1.9 98.7794 1.9H92.7164C91.8994 1.9 91.5554 2.244 91.5554 3.061V30.839C91.5554 31.656 91.8994 32 92.7164 32H93.5334C94.4364 32 94.8234 31.613 94.8234 30.71V21.637C94.8234 20.605 95.2534 20.175 96.2424 20.175H98.0914ZM94.8234 6.63C94.8234 5.641 95.2534 5.211 96.2424 5.211H98.7794C102.477 5.512 104.24 7.662 104.24 11.059C104.24 14.413 102.52 16.821 99.3384 16.821H96.2424C95.2534 16.821 94.8234 16.391 94.8234 15.359V6.63Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M144.429 32C148.084 32 153.588 30.495 153.588 23.013C153.588 18.713 150.707 16.434 148.17 16.133C147.912 16.09 147.783 15.918 147.783 15.789C147.783 15.66 147.912 15.531 148.084 15.445C149.417 14.843 151.438 12.951 151.438 9.64C151.438 2.33 145.375 1.9 142.494 1.9H137.678C136.861 1.9 136.517 2.244 136.517 3.061V30.839C136.517 31.656 136.861 32 137.678 32H144.429ZM144.386 18.24C148.428 18.24 150.363 20.39 150.363 23.4C150.363 26.496 148.256 28.818 144.128 28.818H141.118C140.129 28.818 139.699 28.388 139.699 27.399V19.702C139.699 18.67 140.129 18.24 141.118 18.24H144.386ZM139.699 6.544C139.699 5.555 140.129 5.125 141.118 5.125H143.354C143.354 5.125 148.213 5.125 148.213 9.984C148.213 13.08 145.375 15.101 142.623 15.101H141.118C140.129 15.101 139.699 14.671 139.699 13.639V6.544Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M182.295 32H194.98C195.883 32 196.27 31.613 196.27 30.71V29.936C196.27 29.033 195.883 28.646 194.98 28.646H186.165C185.262 28.646 184.875 28.259 184.875 27.356V19.573C184.875 18.67 185.262 18.283 186.165 18.283H192.83C193.733 18.283 194.12 17.896 194.12 16.993V16.219C194.12 15.316 193.733 14.929 192.83 14.929H186.165C185.262 14.929 184.875 14.542 184.875 13.639V6.544C184.875 5.641 185.262 5.254 186.165 5.254H194.894C195.797 5.254 196.184 4.867 196.184 3.964V3.19C196.184 2.287 195.797 1.9 194.894 1.9H182.295C181.822 1.9 181.478 2.287 181.478 2.717V31.183C181.478 31.613 181.822 32 182.295 32Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M232.201 20.175C233.104 20.175 233.62 20.519 233.964 21.379L238.092 30.796C238.436 31.656 238.952 32 239.855 32H240.973C241.618 32 241.919 31.828 241.919 31.398C241.919 31.226 241.876 31.054 241.79 30.796L237.447 20.863C237.318 20.562 237.232 20.261 237.232 20.046C237.232 19.444 237.619 19.1 237.791 18.971C240.543 17.294 241.704 14.155 241.704 10.629C241.704 5.598 238.737 1.9 232.889 1.9H226.826C226.009 1.9 225.665 2.244 225.665 3.061V30.839C225.665 31.656 226.009 32 226.826 32H227.643C228.546 32 228.933 31.613 228.933 30.71V21.637C228.933 20.605 229.363 20.175 230.352 20.175H232.201ZM228.933 6.63C228.933 5.641 229.363 5.211 230.352 5.211H232.889C236.587 5.512 238.35 7.662 238.35 11.059C238.35 14.413 236.63 16.821 233.448 16.821H230.352C229.363 16.821 228.933 16.391 228.933 15.359V6.63Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M275.614 32H279.484C285.461 32 285.461 26.023 285.461 26.023V3.19C285.461 2.287 285.074 1.9 284.171 1.9H283.397C282.494 1.9 282.107 2.287 282.107 3.19V26.023C282.107 26.023 282.107 28.646 279.484 28.646H275.614C272.991 28.646 272.991 26.023 272.991 26.023V3.19C272.991 2.287 272.604 1.9 271.701 1.9H270.927C270.024 1.9 269.637 2.287 269.637 3.19V26.023C269.637 26.023 269.637 32 275.614 32Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            <path d="M315.157 25.679C315.157 30.108 318.984 32.172 322.338 32.172C325.95 32.172 329.261 29.678 329.261 25.722C329.261 21.422 327.025 18.584 323.929 15.316C319.93 11.145 319.414 9.468 319.414 7.533C319.414 5.77 320.489 4.91 322.037 4.91C323.757 4.91 325.176 5.985 325.176 7.963V9.683C325.176 10.457 325.52 10.93 326.466 10.93H327.154C328.057 10.887 328.444 10.5 328.444 9.597V8.092C328.444 3.663 325.649 1.642 322.338 1.642C318.941 1.642 315.888 3.835 315.888 7.834C315.888 9.941 316.49 12.349 321.177 17.552C323.628 20.261 325.907 22.282 325.907 26.023C325.907 27.872 324.144 28.904 322.295 28.904C320.403 28.904 318.425 27.786 318.425 25.808V23.658C318.425 22.884 318.081 22.411 317.135 22.411H316.447C315.544 22.454 315.157 22.841 315.157 23.744V25.679Z" stroke="white" stroke-width="1" mask="url(#path-1-outside-1)"/>
            </svg>
            <h3> The Attendance Initiative </h3>
        </div>
    </div>
    <div class="col-lg-6 my-auto">
        <div class="form_container">
            <form>
                <div class="form-group mail-tf">
                    <input type="email" id='email' class="form-control" name="email" placeholder="Email" title='Enter a valid e-mail address' required>
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group pass-tf">
                    <input type="password" class="form-control active" id="pass" placeholder="Password" title='Enter the password associated with your e-mail address' required>
                    <div id='eye' class='pass-tf' onclick="showPass()"><i class="fa fa-eye-slash" ></i></div>
                </div>
                <span id = 'mess' class="alert">Invalid Password</span><br>
                <button type="button" class="log-btn" id='loginbtn'>Login</button>
                <p class="message"> Forgot your password? 
                    <a href="#" onclick="return transfer_data()"> 
                        <b> Create a new password </b> 
                    </a>
                </p>
            </form>
            <form method="post" class="recaptcha_form" style="display: none;">
                <div class="form-group">
                    <input type="email" placeholder="Email" class="form-control" name="emailid" id="emailid" required>
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group custom_group">
                    <div class="g-recaptcha" data-sitekey="6Ldki7oUAAAAALfLMRYy4n45X2jxl1r7dol3BbDM"></div>
                </div>
                <p class="link"> 
                    <a href="resetpassword.html"> 
                        <b> I already have an OTP </b> 
                    </a> 
                </p>
                <button type="submit"> Send OTP </button>
                <p class="message"> Want to sign in?
                    <a href="#"> 
                        <b> Sign in </b>
                    </a> 
                </p>
            </form>
        </div>
    </div>
</div>
<script defer src="js/scriptShake.js"></script>
<script defer src="js/main.js"></script>
<script defer src="js/app.js"></script>