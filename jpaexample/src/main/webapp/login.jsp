<!DOCTYPE html>
<html lang="en">
<head>
    <title>TDL - Login</title>
</head>
<body>
	<form class="user" action="./LoginServlet" method="POST">
		<input type="email" required name="email" id="email" placeholder="Enter Email Address">
		<input type="password" required name="password" id="password" placeholder="Password">
		<button>Login</button>
		<span style="color: red">
			<% Object message = request.getAttribute("message");
            if(message != null) { %><%= message %> <% } %>
		</span>
	</form>
	<a href="register.jsp">Not have an account? Join us!</a>
</body>

</html>