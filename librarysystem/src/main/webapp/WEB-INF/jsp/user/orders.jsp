<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../include/header.jsp" />

    <style>
        body {
            background: url("https://picsum.photos/seed/104/1920/1080");
            background-position: center;
            background-size: cover;
            background-attachment: fixed
        }
    </style>

    <!-- User account info display -->
    <c:if test="${user != null}">
        <h1>My Account</h1>
        <div class="myCenteredContainer">
            Name : ${user.name}<br>
            Email : ${user.email}<br>
            Role : <c:forEach items="${userRoles}" var="role">
                ${role.roleName}
            </c:forEach>
        </div>
    </c:if>

    <!-- Orders view table -->
    <c:if test="${empty checkoutList}">
        <div class="myCenteredContainer">
            No orders
        </div>
    </c:if>
    <c:if test="${not empty checkoutList}">
        <table class="table myTable">
            <tr>
                <th>Checkout Id</th>
                <th>Status</th>
                <th></th>
            </tr>
            <!-- Could add a check for overdue books -->
            <c:forEach items="${checkoutList}" var="checkout" varStatus="status">
                <tr scope="row">
                    <td>${checkout.id}</td>
                    <td>
                        <c:choose>
                            <c:when test="${outstandingList[status.index]}">
                                Outstanding
                            </c:when>
                            <c:otherwise>
                                Completed
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form action="/user/orderDetails/${checkout.id}" method="POST">
                            <button type="submit">Details</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>


    <jsp:include page="../include/footer.jsp" />