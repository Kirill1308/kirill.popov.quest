<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/completed-quiz.css">
    <title>Quiz Finished</title>
</head>

<body>
<h2>Quiz Finished!</h2>

<div class="result-container">
    <p class="result-message">Congratulations! You have answered all questions.</p>
    <p>Your result: <span style="color: #28a745;">100%</span></p>
    <p class="quiz-description">Click the button below to play one more time</p>
</div>

<form class="quiz-form" method="get" action="homeWelcome.jsp">
    <input type="submit" value="Start Quiz Again" class="start-quiz-button">
</form>
</body>

</html>
