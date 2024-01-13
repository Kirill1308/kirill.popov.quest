<%@ page import="quest.model.Question" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../style/quiz-page-styles.css">
    <title>Quest Form</title>
</head>

<body>
<form action="quiz" class="quiz-form">
    <%
        Question question = (Question) request.getAttribute("question");
        String questionText = "";
        String option1 = "";
        String option2 = "";
        String questionId = "";

        if (question != null) {
            questionId = question.id();
            questionText = question.text();
            option1 = question.option1();
            option2 = question.option2();
        }
    %>

    <h3 class="question-heading">
        Question <%=questionId%><br>
        <%=questionText%>
    </h3>

    <label class="answer-label">
        <input type="radio" name="answer" value="yes" class="answer-radio">
        <%=option1%>
    </label>

    <label class="answer-label">
        <input type="radio" name="answer" value="no" class="answer-radio">
        <%=option2%>
    </label>

    <br>
    <input type="submit" value="Submit Answer" class="submit-button">
</form>
</body>
</html>