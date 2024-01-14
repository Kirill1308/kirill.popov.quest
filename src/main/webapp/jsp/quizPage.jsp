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
        int questionId = 0;

        if (question != null) {
            questionId = question.getId();
            questionText = question.getText();
        }
    %>

    <h3 class="question-heading">
        Question <%=questionId%><br>
        <%=questionText%>
    </h3>

    <label class="answer-label">
        <input type="radio" name="answer" value="yes" class="answer-radio"> yes
        <input type="radio" name="answer" value="no" class="answer-radio"> no
    </label><br>

    <input type="submit" value="Submit Answer" class="submit-button">
</form>
</body>
</html>