<!DOCTYPE html>
<html lang="en">

<head>
    <title>TDL - Forgot Password</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Zen+Kaku+Gothic+Antique&display=swap" rel="stylesheet">

    <!-- Animate.css for animations -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="style.css?v=1.0">
</head>

<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-sm justify-content-center" style="background-color: #743CCE;">
        <ul class="navbar-nav">
            <li class="nav-item">
                <p id="brand" class="animate__animated animate__fadeInDown text-white text-center" style="font-family: 'Open Sans', 'Zen Kaku Gothic Antique', sans-serif; font-size: 30px;">
                    Forgot Password?
                </p>
            </li>
        </ul>
    </nav>

    <!-- Change Password Form -->
    <form class="col-lg-6 offset-lg-3" action="./ChangePassword" method="POST">
        <div class="row justify-content-center">
            <h1 class="text-center mt-4" style="color: #743CCE;">Change Password</h1>

            <!-- Hidden Input for Email -->
            <input type="hidden" value="<%= request.getParameter('email') %>" name="email" id="email">

            <!-- New Password Input -->
            <input class="form-control rounded-pill text-center mt-4" type="password" name="password" id="password" 
                placeholder="Enter the new password" style="color: #743CCE; height: 50px;" required>

            <!-- Repeat Password Input -->
            <input class="form-control rounded-pill text-center mt-4" type="password" name="repeatPassword" id="repeatPassword" 
                placeholder="Repeat the new password" style="color: #743CCE; height: 50px;" required>

            <!-- Submit Button -->
            <button class="btn btn-primary rounded-pill mt-4" type="submit" style="background-color: #743CCE; height: 50px;">
                Change Password
            </button>

            <!-- Message Display -->
            <span class="text-center mt-4 d-block" style="font-size: 20px; color: red;">
                <% Object message = request.getAttribute("message"); 
                   if (message != null) { %><%= message %><% } %>
            </span>
        </div>
    </form>

    <!-- JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        async function animateBrand() {
            const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));

            await sleep(2000);
            $("#brand").removeClass("animate__fadeInDown").addClass("animate__fadeOutDown");

            await sleep(500);
            $("#brand").removeClass("animate__fadeOutDown").addClass("animate__fadeInDown");

            const content = `<a href='#'>To <img width='30px' height='30px' src='https://pngimage.net/wp-content/uploads/2018/05/do-icon-png-2.png'> Do</a>`;
            $("#brand").html(content);
        }

        animateBrand();
    </script>
</body>

</html>