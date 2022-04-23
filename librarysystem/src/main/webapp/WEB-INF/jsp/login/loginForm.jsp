<jsp:include page="../include/header.jsp" />

<style>
    body {
        background: url("https://picsum.photos/seed/106/1920/1080");
        background-position: center;
        background-size: cover;
        background-attachment: fixed
    }
</style>

<!-- for spring security -->
<!-- method must be POST, fields must be username/password -->
<!-- action must match the security config class -->

<form action="/login/loginSubmit" method="POST" class="myCenteredContainer" style="text-align: right">
    <h3>Login</h3>
    Email <input type="text" name="username">
    <br>
    Password <input type="password" name="password">
    <br>
    <center><button type="submit">Submit</button></center>
</form>

<jsp:include page="../include/footer.jsp" />