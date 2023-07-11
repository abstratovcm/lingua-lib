<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ page import="com.penguin.model.Translation" %>
<%@ page import="com.penguin.model.Sentence" %>
<%@ page import="java.util.List" %>
<%
    List<Translation> translations = (List<Translation>) request.getAttribute("translations");
    List<Sentence> allSentences = (List<Sentence>) request.getAttribute("allSentences");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Translations</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/jquery-3.7.0.js"></script>
</head>
<body>
    <div class="container">
        <h1>Translations</h1>
        <form id="translationForm" action="translation" method="post" class="form">
            <input type="hidden" id="id" name="id" value="">
            <select id="originalSentenceId" name="originalSentenceId">
                <% for (Sentence sentence : allSentences) { %>
                    <option value="<%= sentence.getId() %>"><%= sentence.getText() %></option>
                <% } %>
            </select>
            <select id="translatedSentenceId" name="translatedSentenceId">
                <% for (Sentence sentence : allSentences) { %>
                    <option value="<%= sentence.getId() %>"><%= sentence.getText() %></option>
                <% } %>
            </select>
            <input type="text" id="comments" name="comments" placeholder="Comments" class="form-control">
            <button type="submit" name="action" id="submit" value="add" class="button">Add</button>
        </form>

        <table class="table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Original Sentence</th>
                    <th>Translated Sentence</th>
                    <th>Comments</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Translation translation : translations) { %>
                    <tr>
                        <td><%= translation.getId() %></td>
                        <td><%= translation.getSentence1().getText() %></td>
                        <td><%= translation.getSentence2().getText() %></td>
                        <td><%= translation.getComments() %></td>
                        <td class="actions">
                            <div class="edit-wrapper">
                                <button class="edit button" data-id="<%= translation.getId() %>">Edit</button>
                            </div>
                            <form action="translation" method="post" class="delete-form">
                                <button type="submit" name="action" class="delete button" value="delete">Delete</button>
                                <input type="hidden" name="id" value="<%= translation.getId() %>">
                            </form>
                        </td>                                              
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <script>
        $(document).ready(function() {
            $(".edit").click(function(e) {
                e.preventDefault();

                var id = $(this).data("id");
                var originalSentence = $(this).closest("tr").find("td:nth-child(2)").text();
                var translatedSentence = $(this).closest("tr").find("td:nth-child(3)").text();
                var comments = $(this).closest("tr").find("td:nth-child(4)").text();

                $("#id").val(id);
                $("#originalSentenceId").val(originalSentence);
                $("#translatedSentenceId").val(translatedSentence);
                $("#comments").val(comments);
                $("#submit").val("update").text("Update");
            });

            $("#translationForm").submit(function(e) {
                if ($("#submit").val() === "update") {
                    e.preventDefault();
                    $('<input>').attr({
                        type: 'hidden',
                        name: 'action',
                        value: 'update'
                    }).appendTo('#translationForm');
                    $.post("translation", $(this).serialize(), function() {
                        location.reload();
                    });
                }
            });
        });
    </script>
</body>
</html>
