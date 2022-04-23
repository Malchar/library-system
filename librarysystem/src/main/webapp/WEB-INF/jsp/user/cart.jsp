<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../include/header.jsp" />

    <style>
        body {
            background: url("https://picsum.photos/seed/102/1920/1080");
            background-position: center;
            background-size: cover;
            background-attachment: fixed
        }
    </style>

    <h1>My Cart</h1>
    <c:if test="${empty cartItems}">
        <div class="myCenteredContainer">
            No items
        </div>
    </c:if>
    <c:if test="${not empty cartItems}">
        <table class="table myTable">
            <tr>
                <th>Inventory Number</th>
                <th>Title</th>
                <th>Due</th>
                <th></th>
            </tr>
            <c:forEach items="${cartItems}" var="lineItem">
                <tr scope="row">
                    <td>${lineItem.item.id}</td>
                    <td>${lineItem.item.book.title}</td>
                    <td>${lineItem.due}</td>
                    <td>
                        <form action="/user/cart/removeItem" method="POST">
                            <input type="hidden" name="lineItemId" value="${lineItem.id}">
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <center>
            <form action="/user/cart/checkout" method="POST">
                <button type="submit">Checkout</button>
            </form>
        </center>
    </c:if>

    <jsp:include page="../include/footer.jsp" />