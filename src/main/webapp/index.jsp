<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/index.css">
    <title>User Login and Registration</title>
</head>

<body>
<div class="container">
    <section class="form-section" id="login-section">
        <form action="user" method="post" id="loginForm">
            <input type="hidden" name="action" value="login">

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Sign in</button>
        </form>

        <%if (request.getAttribute("errorMessage") != null) {%>
        <p class="error-message"><%=(String) request.getAttribute("errorMessage")%>
        </p>
        <%}%>

        <a href="register.jsp">Create an account</a>
    </section>
</div>
</body>

</html>
