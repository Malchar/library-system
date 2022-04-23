<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="include/header.jsp" />

    <style>
        body {
            background: url("https://picsum.photos/seed/101/1920/1080");
            background-position: center;
            background-size: cover;
            background-attachment: fixed
        }
    </style>

    <h1>Book Details</h1>

    <!-- show book details -->
    <div class="myCenteredContainer">
        Title : ${book.title}<br>
        Author : ${book.author}<br><br>
        <center><img src=${book.imageUrl}></img></center><br>
        ISBN : ${book.isbn}<br>
        Category : ${book.category}<br>
        <div style="width: 300px;">Description : ${book.description}</div><br>
    </div>

    <!-- show inventory items for this book -->
    <c:choose>
        <c:when test="${not empty itemList}">
            <table class="table myTable">
                <tr>
                    <th>Inventory Number</th>
                    <th>Condition</th>
                    <th></th>
                </tr>
                <c:forEach items="${itemList}" var="item">
                    <tr scope="row">
                        <td>${item.id}</td>
                        <td>${item.itemCondition}</td>
                        <td>
                            <form action="/user/cart/addItem" method="POST">
                                <input type="hidden" name="item" value="${item.id}">
                                <button type="submit">Add to Cart</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h1>Currently Unavailable</h1>>
        </c:otherwise>
    </c:choose>

    <jsp:include page="include/footer.jsp" />