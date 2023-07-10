<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ page import="com.penguin.model.Sentence" %>
<%@ page import="java.util.List" %>
<%
    List<Sentence> sentences = (List<Sentence>) request.getAttribute("sentences");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Sentences</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/jquery-3.7.0.js"></script>
</head>
<body>
    <div class="container">
        <h1>Sentences</h1>
        <form id="sentenceForm" action="sentence" method="post" class="form">
            <input type="hidden" id="id" name="id" value="">
            <input type="text" id="mandarinSentence" name="mandarinSentence" placeholder="Sentence" class="form-control">
            <button type="submit" name="action" id="submit" value="add" class="button">Add</button>
        </form>

        <table class="table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Sentence</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Sentence sentence : sentences) { %>
                    <tr>
                        <td><%= sentence.getId() %></td>
                        <td><%= sentence.getMandarinSentence() %></td>
                        <td class="actions">
                            <div class="edit-wrapper">
                                <button class="edit button" data-id="<%= sentence.getId() %>">Edit</button>
                            </div>
                            <form action="sentence" method="post" class="delete-form">
                                <button type="submit" name="action" class="delete button" value="delete">Delete</button>
                                <input type="hidden" name="id" value="<%= sentence.getId() %>">
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
                var sentence = $(this).closest("tr").find("td:nth-child(2)").text();

                $("#id").val(id);
                $("#mandarinSentence").val(sentence);
                $("#submit").val("update").text("Update");
            });

            $("#sentenceForm").submit(function(e) {
                if ($("#submit").val() === "update") {
                    e.preventDefault();
                    $('<input>').attr({
                        type: 'hidden',
                        name: 'action',
                        value: 'update'
                    }).appendTo('#sentenceForm');
                    $.post("sentence", $(this).serialize(), function() {
                        location.reload();
                    });
                }
            });
        });
    </script>
</body>
</html>
