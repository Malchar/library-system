<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../include/header.jsp" />

    <style>
        body {
            background: url("https://picsum.photos/seed/103/1920/1080");
            background-position: center;
            background-size: cover;
            background-attachment: fixed
        }
    </style>

    <h1>Order Details</h1>
    <div class="myCenteredContainer">Checkout Id : ${checkout.id}
        <c:if test="${empty lineItemList}">
            <br>No items
        </c:if>
    </div>

    <c:if test="${not empty lineItemList}">
        <table class="table myTable">
            <tr>
                <th>Line Id</th>
                <th>Inventory<br>Number</th>
                <th>Title</th>
                <th>Due</th>
                <th>Returned</th>
                <th></th>
            </tr>
            <c:forEach items="${lineItemList}" var="lineItem">
                <tr scope="row">
                    <td>${lineItem.id}</td>
                    <td>${lineItem.item.id}</td>
                    <td>${lineItem.item.book.title}</td>
                    <td>${lineItem.due}</td>
                    <td>${lineItem.returned}</td>
                    <td>
                        <c:if test="${lineItem.returned == null}">
                            <form action="/user/orderReturn" method="POST">
                                <input type="hidden" name="lineItemId" value="${lineItem.id}">
                                <button type="submit">Return Item</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <center>
        <form action="/user/orders" method="GET">
            <button type="submit">View All Orders</button>
        </form>
    </center>

    <jsp:include page="../include/footer.jsp" />