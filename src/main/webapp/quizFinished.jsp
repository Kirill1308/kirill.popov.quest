<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Finished</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 20px;
            margin: 0;
        }

        h2 {
            color: #333;
        }

        .result-container {
            margin-top: 20px;
        }

        .result-message {
            font-size: 18px;
            color: #007bff;
            margin-bottom: 20px;
        }

        .start-quiz-button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .start-quiz-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>

<h2>Quiz Finished!</h2>

<div class="result-container">
    <p class="result-message">Congratulations! You have answered all questions.</p>
    <p>Your result: <span style="color: #28a745;">100%</span></p>
    <p class="quiz-description">Click the button below to play one more time</p>
</div>

<form class="quiz-form" method="get" action="welcomePage.jsp">
    <input type="submit" value="Start Quiz Again" class="start-quiz-button">
</form>

</body>

</html>
