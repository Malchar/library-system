<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../include/header.jsp" />

    <style>
        body{
            background: url("https://picsum.photos/seed/105/1920/1080");
            background-position: center;
            background-size:cover;
            background-attachment:fixed
        }
    </style>

    <!-- signup form -->
    <form action="/login/registerSubmit" method="post" class="myCenteredContainer"  style="text-align: right">
        <h3>Sign Up</h3>
        Email <input type="email" name="email" id="emailId" value="${form.email}"><br>
        Password <input type="password" name="password" id="passwordId" value="${form.password}"><br>
        Confirm Password <input type="password" name="confirmPassword" id="confirmPasswordId"
            value="${form.confirmPassword}"><br>
        Name <input type="text" name="name" id="nameId" value="${form.name}"><br>
        <center><button type="submit">Submit</button></center>
    </form>

    <!-- display errors -->
    
        <c:if test="${formErrors != null}">
            <div class="myErrorContainer">
            Errors : <br>
            <c:forEach items="${formErrors.values()}" var="error">
                ${error}<br>
            </c:forEach>
        </div>
        </c:if>

    <jsp:include page="../include/footer.jsp" />