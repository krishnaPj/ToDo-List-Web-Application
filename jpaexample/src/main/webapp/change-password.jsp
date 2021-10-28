<!DOCTYPE html>
<html lang="en">
<head>
<title>TDL - Forgot Password</title>
</head>
<body>
	<h1>Forgot Your Password?</h1>
	<p>Just enter your email address below and we'll send you a link to reset your password</p>
	<form action="./ChangePassword" method="POST">
		<input type="hidden" value="<%= request.getParameter("email") %>" name="email" id="email" />
		<input type="password" name="password" id="password" placeholder="Enter the new password">
		<input type="password" name="repeatPassword" id="repeatPassword" placeholder="Repeat the new password">
		<button type="submit">Change password</button>
		<span style="color: red"> 
			<% Object message = request.getAttribute("message");
            if(message != null) { %> <%= message %> <% } %>
		</span>
	</form>
</body>
</html>