<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<div id="background-container"></div>

<!-- Link to app.js-->
<script src="app.js" type="text/javascript" async></script>

<head>
    <!-- Link to style.css -->
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <title>Blage's Emporium</title>
    <link rel="icon" type="image/png" href="blage_icon.png"/>
</head>

<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0);
//prevents caching at the proxy server
%>

<div id="alert" class="noselect">Logging In!</div>

<body>
<div class="login-container">
    <form id="login-form">
        <h2 style="text-align: center; font-size: larger; margin: 0px;">Welcome To Blage's Helicopter Service's</h2>
        <div class="copter-container">
            <!-- link to blage_copter.png -->
            <img src="blage_copter.png" alt="Blage's Copter" class="copter">
        </div>
        <hr class="welcome-line">
        <label id="label_alert" style="color: red; display: block; visibility: hidden;">Wrong Username or Password</label>
        <label class="username_label" for="username">Username:</label>
        <input type="text" id="username" name="username">
        <br>
        <label class="password_label" for="password">Password:</label>
        <input type="password" id="password" name="password">
        <br><br>
        <input type="button" value="Submit" onclick="checkCredentials()">
        <input type="button" value="Sign Up" onclick="signUp()">
        <button type="submit" id="amsoil_button"><a href="https://www.amsoil.com/" style="color: white;
          text-decoration: none;">Go To Amsoil</a>
        </button>
    </form>
</div>
</body>

</html>