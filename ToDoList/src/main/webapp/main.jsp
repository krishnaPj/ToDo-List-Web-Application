<!DOCTYPE html>
<html lang="en">
<head>
  <title>TDL - Home</title>
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
font-family: 'Zen Kaku Gothic Antique', sans-serif;font-size: 30px;">Hello <%=request.getAttribute("message")%></p>
    </li>
  </ul>
</nav>
<form action="./InsertData" method="POST" style="margin: 0 auto;width:650px; margin-top: 60px;">
<div class="input-group mb-3">
  <input id="content" name="content" style="color: #743CCE;height:50px;" required type="text" class="form-control rounded-pill" placeholder="Insert some content">
  <div class="input-group-append">
    <button style="height:50px;color:white;background-color:#743CCE;" class="btn btn-outline-secondary rounded-pill" type="button" id="button-addon2">Add item</button>
  </div>
</div>
</form>
<div class="card text-center mx-auto" style="width:600px;margin-top: 70px;">
  <div class="card-body">
    <h5 class="card-title" style="color:#743CCE;">			
    <span class="text-center" style="color: #743CCE;">
				<% Object message = request.getAttribute("content");
	            if(message != null) { %><%= message %> <% } %>
			</span></h5>
  </div>
  <div class="card-footer text-muted">
    2 days ago
  </div>
</div>
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