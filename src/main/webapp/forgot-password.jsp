<!DOCTYPE html>
<html lang="en">

<head>
    <title>TDL - Forgot Password</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Zen+Kaku+Gothic+Antique&display=swap">

    <!-- Animate.css for animations -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="style.css?v=1.0">
</head>

<body class="noselect">
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-sm justify-content-center" style="background-color: #743CCE;">
        <ul class="navbar-nav">
            <li class="nav-item">
                <p id="brand" class="animate__animated animate__fadeInDown text-white" style="font-family: 'Open Sans', sans-serif, 'Zen Kaku Gothic Antique', sans-serif; font-size: 30px;">
                    We fix your problem as soon as possible
                </p>
            </li>
        </ul>
    </nav>

    <!-- Forgot Password Form -->
    <form class="col-lg-6 offset-lg-3" action="./ForgotServlet" method="POST">
        <div class="row justify-content-center">
            <h1 class="text-center" style="margin-top: 40px; color: #743CCE;">Forgot Your Password?</h1>

            <!-- Email Input -->
            <input style="color: #743CCE; margin-top: 40px; height: 50px;" 
                required 
                class="form-control rounded-pill text-center" 
                type="email" 
                name="email" 
                id="email" 
                placeholder="Enter Email Address">

            <!-- Submit Button -->
            <button style="margin-top: 40px; height: 50px; background-color: #743CCE;" 
                class="btn btn-primary rounded-pill" 
                type="submit">Reset Password</button>

            <!-- Error/Success Message -->
            <span class="text-center d-block mt-3 text-danger font-weight-bold"> 
                <% Object message = request.getAttribute("message");
                if (message != null) { %> <%= message %> <% } %>
            </span>
        </div>
    </form>

    <!-- JavaScript Dependencies -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Custom JavaScript -->
    <script>
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