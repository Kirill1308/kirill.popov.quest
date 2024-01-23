<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/index.css">
    <title>User Registration</title>
</head>

<body>
<div class="container">
    <section class="form-section" id="register-section">
        <form action="user" method="post" id="registerForm">
            <input type="hidden" name="action" value="register">

            <label for="registerUsername">Username:</label>
            <input type="text" id="registerUsername" name="registerUsername" required>

            <label for="registerPassword">Password:</label>
            <input type="password" id="registerPassword" name="registerPassword" required>

<%--            <label for="registerPasswordConfirm">Confirm Password:</label>
            <input type="password" id="registerPasswordConfirm" name="registerPasswordConfirm" required>--%>

            <button type="submit">Sign Up</button>

            <%if (request.getAttribute("errorMessage") != null) {%>
            <p class="error-message"><%=(String) request.getAttribute("errorMessage")%>
            </p>
            <%}%>
        </form>
    </section>
</div>
</body>

</html>
