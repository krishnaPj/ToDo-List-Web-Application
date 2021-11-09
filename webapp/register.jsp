<!DOCTYPE html>
<html lang="en">
<head>
    <title>TDL - Register</title>
      <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
	  <link rel="preconnect" href="https://fonts.googleapis.com">
	  <link rel="preconnect" href="https://fonts.gstatic.com">
	  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Zen+Kaku+Gothic+Antique&display=swap">
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
	  <link rel="stylesheet" href="style.css?v=1.0" type="text/css" />
</head>
<body class="noselect">
	<nav class="navbar navbar-expand-sm justify-content-center" style="background-color: #743CCE;">
	  <ul class="navbar-nav">
	    <li class="nav-item">
	      <p id="brand" class="animate__animated animate__fadeInDown" style="color: white;font-family: 'Open Sans', sans-serif;
	font-family: 'Zen Kaku Gothic Antique', sans-serif;font-size: 30px;">Hello new user</p>
	    </li>
	  </ul>
	</nav>
	 <form class="col-lg-6 offset-lg-3" action="./register" method="POST">
   <div class="row justify-content-center">
   			<h1 class="text-center" style="margin-top: 40px; color: #743CCE;">Join to us today!</h1><br>
    		<input autocomplete="off" style="color: #743CCE; margin-top: 40px;height:50px;" required class="form-control rounded-pill text-center" type="text" id="firstName" name="firstName" placeholder="First Name"> 
			<input autocomplete="off" style="color: #743CCE; margin-top: 40px;height:50px;" required class="form-control rounded-pill text-center" type="text" id="lastName" name="lastName" placeholder="Last Name">
			<input autocomplete="off" style="color: #743CCE; margin-top: 40px;height:50px;" required class="form-control rounded-pill text-center" type="email" id="inputEmail" name="inputEmail"placeholder="Email Address">
			<input autocomplete="off" style="color: #743CCE; margin-top: 40px;height:50px;" required class="form-control rounded-pill text-center" type="password" id="inputPassword" name="inputPassword" placeholder="Password">
			<input autocomplete="off" style="color: #743CCE; margin-top: 40px;height:50px;" required class="form-control rounded-pill text-center" type="password" id="repeatPassword" name="repeatPassword" placeholder="Repeat Password"> 
			<input autocomplete="off" style="margin-top: 40px;height:50px;background-color: #743CCE;" class="btn btn-primary rounded-pill" type="submit" value="Create Account">
   			<a style="color: gray; margin-top:20px;" class="text-center" href="login.jsp">Already have an account? Sign in!</a>
   </div>
 </form>
 			<span class="text-center" style="color: red; font-weight: bold;margin-top: 30px;">
				<% Object message = request.getAttribute("message");
	            if(message != null) { %><%= message %> <% } %>
			</span>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script>
	function sleep(ms) { return new Promise(resolve => setTimeout(resolve, ms)); }
	async function demo() {
	  await sleep(2000);
	  $("#brand").removeClass("animate__animated animate__fadeInDown");
	  $("#brand").addClass("animate__animated animate__fadeOutDown");
	  await sleep(500);
	  $("#brand").removeClass("animate__animated animate__fadeOutDown");
	  $("#brand").addClass("animate__animated animate__fadeInDown");
	  var div_data = "<a href='#'>To " + 
			"<img width='30px' height='30px' src='https://pngimage.net/wp-content/uploads/2018/05/do-icon-png-2.png'>" + 
		" Do</a>";
		$("#brand").html(div_data);
	}
	demo();
	</script>
</body>
</html>