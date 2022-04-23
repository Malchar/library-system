<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Library System</title>
    </head>

    <script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../../../pub/css/style.css" media="screen" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

    <body>
        <!-- creates a base container in the body -->
        <div class="container">
            <nav>
                <a href="/index">Search</a> &nbsp | &nbsp

                <!-- admin links -->
                <sec:authorize access="hasAuthority('ADMIN')">
                    <a href="/admin/control">Admin Controls</a> &nbsp | &nbsp
                    <a href="/user/allOrders">Admin View Orders</a> &nbsp | &nbsp
                </sec:authorize>

                <!-- non-logged-in user links -->
                <sec:authorize access="!isAuthenticated()">
                    <a href="/login/register">Sign Up</a> &nbsp | &nbsp
                    <a href="/login/login">Login</a>
                </sec:authorize>

                <!-- logged-in user links -->
                <sec:authorize access="isAuthenticated()">
                    <a href="/user/cart">Cart</a> &nbsp | &nbsp
                    <a href="/user/orders">My Account</a> &nbsp | &nbsp
                    <a href="/login/logout">Logout</a> &nbsp
                </sec:authorize>
                <sec:authorize access="hasAnyAuthority('USER', 'ADMIN')">
                    <sec:authentication property="principal.username" />
                </sec:authorize>
            </nav>