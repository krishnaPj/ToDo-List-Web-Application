<!DOCTYPE html>
<html lang="en">
<head>
    <title>TDL - Register</title>
</head>
<body>
	<h1>Join to us today!</h1>
	<form class="user" action="./register" method="POST">
		<input required type="text" id="firstName" name="firstName" placeholder="First Name"> 
		<input required type="text" id="lastName" name="lastName" placeholder="Last Name"> 
		<input required type="email" id="inputEmail" name="inputEmail"placeholder="Email Address"> 
		<input required type="password" id="inputPassword" name="inputPassword" placeholder="Password">
		<input required type="password" id="repeatPassword" name="repeatPassword" placeholder="Repeat Password"> 
		<input type="submit" value="Create Account">
	</form>
	<a class="small" href="login.jsp">Already have an account? Login!</a>
</body>
</html>