<!DOCTYPE html>
<html lang="en">

<head>
    <title>TDL - Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="style.css?v=1.0">

    <!-- Google Fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Zen+Kaku+Gothic+Antique&display=swap">

    <!-- Animate.css for animations -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
</head>

<body class="noselect">
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-sm justify-content-center" style="background-color: #743CCE;">
        <ul class="navbar-nav">
            <li class="nav-item">
                <p id="brand" class="animate__animated animate__fadeInDown" style="color: white; font-family: 'Open Sans', sans-serif, 'Zen Kaku Gothic Antique', sans-serif; font-size: 30px;">
                    Welcome back user
                </p>
            </li>
        </ul>
    </nav>

    <!-- Login Form -->
    <form class="col-lg-6 offset-lg-3" action="LoginServlet" method="POST">
        <div class="row justify-content-center">
            <h1 class="text-center" style="margin-top: 40px; color: #743CCE;">Access to your personal area</h1>

            <!-- Email Input -->
            <input style="color: #743CCE; margin-top: 40px; height: 50px;" 
                required 
                class="form-control rounded-pill text-center" 
                type="email" 
                id="email" 
                name="email" 
                placeholder="Email Address">

            <!-- Password Input -->
            <input style="color: #743CCE; margin-top: 40px; height: 50px;" 
                required 
                class="form-control rounded-pill text-center" 
                type="password" 
                id="password" 
                name="password" 
                placeholder="Password">

            <!-- Submit Button -->
            <button style="margin-top: 40px; height: 50px; background-color: #743CCE;" 
                class="btn btn-primary rounded-pill" 
                type="submit">
                Login
            </button>

            <!-- Additional Links -->
            <a style="color: gray; margin-top: 20px;" class="text-center d-block" href="register.jsp">Not have an account? Join us</a>
            <a style="color: gray;" class="text-center d-block" href="forgot-password.jsp">Forgot your password? Change it</a>

            <!-- Error Message Display -->
            <span class="text-center d-block" style="color: red; font-weight: bold; margin-top: 30px;">
                <% Object message = request.getAttribute("message"); 
                   if (message != null) { %><%= message %><% } %>
            </span>
        </div>
    </form>

    <!-- JavaScript Dependencies -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Custom JavaScript -->
    <script>
        // Function to create a sleep delay
        function sleep(ms) { return new Promise(resolve => setTimeout(resolve, ms)); }

        async function demo() {
            await sleep(2000);
            $("#brand").removeClass("animate__animated animate__fadeInDown")
                         .addClass("animate__animated animate__fadeOutDown");

            await sleep(500);
            $("#brand").removeClass("animate__animated animate__fadeOutDown")
                         .addClass("animate__animated animate__fadeInDown");

            const divData = `<a href='#'>To <img width='30px' height='30px' src='https://pngimage.net/wp-content/uploads/2018/05/do-icon-png-2.png'> Do</a>`;
            $("#brand").html(divData);
        }

        demo();
    </script>
</body>

</html>