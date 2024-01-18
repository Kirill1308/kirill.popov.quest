<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style/fail-page.css">
    <title>Game Over</title>
</head>
<body>

<h2>Game Over!</h2>
<%
    Integer score = (Integer) session.getAttribute("correctAnswersCount");
    Integer total = (Integer) session.getAttribute("totalQuestionsCount");
%>
<div class="score-message">
    <p>Your final score is: <span style="color: #28a745;"><%=score%>/<%=total%></span></p>
</div>

<form action="welcomePage.jsp" method="get">
    <input type="submit" value="Play Again" class="start-quiz-button">
</form>

</body>
</html>

