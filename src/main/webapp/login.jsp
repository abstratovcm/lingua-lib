<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
    </head>
    <body>
        <div class="container">
            <h1>Login Form</h1>
            <form action="login" method="post">
                <div>
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username">
                </div>
                <div>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password">
                </div>
                <input type="submit" value="Login">
            </form>
            <% 
            if(request.getAttribute("error") != null) { 
            %>
                <p><%= request.getAttribute("error") %></p>
            <% 
            } 
            %>
        </div>
    </body>
</html>
