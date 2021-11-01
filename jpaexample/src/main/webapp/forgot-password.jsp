<!DOCTYPE html>
<html lang="en">
<head>
<title>TDL - Forgot Password</title>
</head>

<body>
	<h1>Forgot Your Password?</h1>
	<p>Just enter your email address below and we'll send you a link to reset your password!</p>
	<form class="user" action="./ForgotServlet" method="GET">
		<input type="email" name="email" id="email" placeholder="Enter Email Address">
		<button type="submit">Reset password</button>
		<span style="color: red"> 
			<% Object message = request.getAttribute("message");
			if(message != null) { %> <%= message %> <% } %>
		</span>
	</form>
</body>
</html>