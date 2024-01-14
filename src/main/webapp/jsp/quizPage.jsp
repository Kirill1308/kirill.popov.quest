<%@ page import="quest.model.Question" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../style/quiz-page-styles.css">
    <title>Quest Form</title>
</head>

<body>
<form action="quiz" class="quiz-form" method="post">
    <%
        @SuppressWarnings("unchecked")
        Optional<Question> question = (Optional<Question>) request.getAttribute("question");
        if (question.isEmpty()) {
            throw new RuntimeException("Question not found");
        }
        int questionId = question.get().getId();
        String questionText = question.get().getText();
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
