<%@ page import="model.Question" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quest Form</title>

    <style>
        body {
            background-color: #2c3e50;
            color: #ecf0f1;
            font-family: 'Arial', sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        form {
            background-color: #3498db;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
            width: 300px;
            text-align: center;
        }

        h2 {
            color: #ecf0f1;
        }

        label {
            display: block;
            margin: 10px 0;
            font-size: 18px;
        }

        input[type="radio"] {
            margin-right: 5px;
            cursor: pointer;
        }

        input[type="submit"] {
            background-color: #e74c3c;
            color: #ecf0f1;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #c0392b;
        }
    </style>

</head>
<body>
<form action="QuizServlet">
    <%
        Question question = (Question) request.getAttribute("question");
        String questionText = "";
        String option1 = "";
        String option2 = "";
        String questionId = "";

        if (question != null) {
            questionId = question.getId();
            questionText = question.getQuestion();
            option1 = question.getOption1();
            option2 = question.getOption2();
        }
    %>

    <h3>
        Question <%=questionId%><br>
        <%=questionText%>
    </h3>

    <label>
        <input type="radio" name="answer" value="yes">
        <%=option1%>
    </label>

    <label>
        <input type="radio" name="answer" value="no">
        <%=option2%>
    </label>

    <br>
    <input type="submit" value="Submit Answer">

</form>
</body>
</html>
