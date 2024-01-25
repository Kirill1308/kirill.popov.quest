<%@ page import="quest.model.Question" %>
<%@ page import="quest.model.Option" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style/quiz-page.css">
    <title>Quest Form</title>
</head>

<body>
<form action="quiz" class="quiz-form" method="post">
    <%
        Question question = (Question) request.getAttribute("question");

        int questionId = question.getId();
        String questionText = question.getText();
        List<Option> options = List.of(question.getOptions());
    %>

    <h3 class="question-heading-num">
        Question <%=questionId%><br>
    </h3>

    <h3 class="question-heading">
        <%=questionText%>
    </h3>

    <%
        for (Option option : options) {
            String optionText = option.text();
    %>
    <label class="answer-label">
        <input type="radio" name="answer" value="<%=optionText%>" class="answer-radio"> <%=optionText%>
    </label><br>
    <%
        }
    %>
    <input type="submit" value="Submit Answer" class="submit-button">
</form>
</body>
</html>
