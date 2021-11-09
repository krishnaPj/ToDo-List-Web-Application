<!DOCTYPE html>
<html lang="en">
<head>
<title>TDL - Forgot Password</title>
</head>
<body>
	<h1>Change password here</h1>
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