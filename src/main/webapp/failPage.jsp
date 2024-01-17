<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Game Over</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
        }

        h2 {
            color: #333;
        }

        .score-message {
            font-size: 18px;
            color: #007bff;
        }

        .start-quiz-button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h2>Game Over!</h2>
<%
    Integer score = (Integer) session.getAttribute("correctAnswersCount");
    Integer total = (Integer) session.getAttribute("totalQuestionsCount");
%>
<div class="score-message">
    <p>Your final score is: <span style="color: #28a745;"><%=score%>/<%=total%></span>></p>
</div>

<form action="welcomePage.jsp" method="get">
    <input type="submit" value="Play Again" class="start-quiz-button">
</form>

</body>
</html>

