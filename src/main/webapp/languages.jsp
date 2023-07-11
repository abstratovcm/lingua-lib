<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ page import="com.penguin.model.Language" %>
<%@ page import="java.util.List" %>
<%
    List<Language> languages = (List<Language>) request.getAttribute("languages");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Languages</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/jquery-3.7.0.js"></script>
</head>
<body>
    <div class="container">
        <h1>Languages</h1>
        <form id="languageForm" action="language" method="post" class="form">
            <input type="hidden" id="id" name="id" value="">
            <input type="text" id="name" name="name" placeholder="Language Name" class="form-control">
            <button type="submit" name="action" id="submit" value="add" class="button">Add</button>
        </form>

        <table class="table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Language Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Language language : languages) { %>
                    <tr>
                        <td><%= language.getId() %></td>
                        <td><%= language.getName() %></td>
                        <td class="actions">
                            <form action="language" method="post" class="delete-form">
                                <button type="submit" name="action" class="delete button" value="delete">Delete</button>
                                <input type="hidden" name="id" value="<%= language.getId() %>">
                            </form>
                        </td>                                              
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
