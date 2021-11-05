<!DOCTYPE html>
<html lang="en">
<head>
  <title>TDL - Home</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Zen+Kaku+Gothic+Antique&display=swap">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
</head>
<body>

<nav class="navbar navbar-expand-sm justify-content-center" style="background-color: #743CCE;">
  <ul class="navbar-nav">
    <li class="nav-item">
      <p class="animate__animated animate__fadeInDown" style="color: white;font-family: 'Open Sans', sans-serif;
font-family: 'Zen Kaku Gothic Antique', sans-serif;font-size: 80px;">Hello <%=request.getAttribute("message")%></p>
    </li>
  </ul>
</nav>

</body>
</html>  