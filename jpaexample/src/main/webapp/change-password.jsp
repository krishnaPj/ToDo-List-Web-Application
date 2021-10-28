<%@ page import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>S - Forgot Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>

<body class="bg-gradient-primary">

<div class="container animate__animated animate__fadeIn">

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-2">Forgot Your Password?</h1>
                                    <p class="mb-4">We get it, stuff happens. Just enter your email address below and we'll send you a link to reset your password!</p>
                                </div>
                                <form class="user" action="./ChangePassword" method="get">
                                	<input type= "hidden" value="<%= request.getParameter("email") %>" name="email" id="email" />
                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-user" name="password" id="password" aria-describedby="newPass" placeholder="Enter the new password...">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-user" name="repeatPassword" id="repeatPassword" aria-describedby="repPass" placeholder="Repeat the new password...">
                                    </div>
                                    <button class="btn btn-primary btn-user btn-block" type="submit">Change password</button>
                                    <span style="color: red"><% Object message = request.getAttribute("message");
                                        if(message != null) {
                                        	%><%= message %>  <%
                                        }
                                        %></span>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>

</body>

</html>