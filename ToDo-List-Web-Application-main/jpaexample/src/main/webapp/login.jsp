<!DOCTYPE html>
<html lang="en">
<head>
      <title>TDL - Login</title>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
	  <link rel="stylesheet" href="style.css">
	  <link rel="preconnect" href="https://fonts.googleapis.com">
	  <link rel="preconnect" href="https://fonts.gstatic.com">
	  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Zen+Kaku+Gothic+Antique&display=swap">
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
</head>
<body class="noselect">
	<nav class="navbar navbar-expand-sm justify-content-center" style="background-color: #743CCE;">
	  <ul class="navbar-nav">
	    <li class="nav-item">
	      <p class="animate__animated animate__fadeInDown" style="color: white;font-family: 'Open Sans', sans-serif;
	font-family: 'Zen Kaku Gothic Antique', sans-serif;font-size: 70px;">To
		<img width="130px" height="130px" src="https://pngimage.net/wp-content/uploads/2018/05/do-icon-png-2.png"> 
	Do</p>
	    </li>
	  </ul>
	</nav>
	<div class="container">
		<form class="form-signin" action="./LoginServlet" method="POST">
			<input class="form-control" style="width:600px" type="email" required name="email" id="email" placeholder="Enter Email Address">
			<input class="form-control" type="password" required name="password" id="password" placeholder="Password">
			<button class="btn form-control" style="color: #fff; background-color: #743CCE;">Login</button>
			<span style="color: red">
				<% Object message = request.getAttribute("message");
	            if(message != null) { %><%= message %> <% } %>
			</span>
		</form>
		<a href="register.jsp">Not have an account? Join us</a>
		<a href="forgot-password.jsp">Forgot your password? Change it</a>
	</div>
</body>

</html>