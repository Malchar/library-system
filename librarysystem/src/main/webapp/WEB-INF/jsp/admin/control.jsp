<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../include/header.jsp" />

    <style>
        body {
            background: url("https://picsum.photos/seed/107/1920/1080");
            background-position: center;
            background-size: cover;
            background-attachment: fixed
        }
    </style>

    <h1>Controls</h1>

    <!-- Display errors here -->
    <c:if test="${formErrors != null || errorMessage != null}">
        <div class="myErrorContainer">
            Errors : <br>
            <c:forEach items="${formErrors}" var="error">
                ${error}<br>
            </c:forEach>
            ${errorMessage}
        </div>
    </c:if>


    <!-- Book add/edit form -->
    <form action="/admin/addBook" method="POST" class="myCenteredContainer" style="text-align: right">
        <h3>
            <c:choose>
                <c:when test="${editBook.id != null}">
                    Edit Book ${editBook.id}
                </c:when>
                <c:otherwise>
                    Add Book
                </c:otherwise>
            </c:choose>
        </h3>
        <input type="hidden" name="id" value="${editBook.id}">
        Title <input type="text" name="title" value="${editBook.title}"><br>
        Author <input type="text" name="author" value="${editBook.author}"><br>
        Description <input type="text" name="description" value="${editBook.description}"><br>
        ISBN <input type="text" name="isbn" value="${editBook.isbn}"><br>
        Image <input type="text" name="imageUrl" value="${editBook.imageUrl}"><br>
        Category <input type="text" name="category" value="${editBook.category}"><br>
        <center><button type="submit">Submit</button></center>
    </form>

    <!--Book search results -->
    <center>
        <form action="/admin/searchBook" method="GET">
            <button type="submit">Show All Books</button>
        </form>
    </center>
    <table class="table myTable">
        <c:if test="${bookList.size() > 0}">
            <tr>
                <th>Book Id</th>
                <th>Title</th>
                <th>Author</th>
                <th>Description</th>
                <th>ISBN</th>
                <th>Image URL</th>
                <th>Category</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${bookList}" var="book">
                <tr scope="row">
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.description}</td>
                    <td>${book.isbn}</td>
                    <td>${book.imageUrl}</td>
                    <td>${book.category}</td>
                    <td>
                        <form action="/admin/editBook" method="POST">
                            <input type="hidden" name="book" value="${book.id}">
                            <button type="submit">Edit</button>
                        </form>
                    </td>
                    <td>
                        <form action="/admin/deleteBook" method="POST">
                            <input type="hidden" name="book" value="${book.id}">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>

    <!-- Item add/edit form -->
    <form action="/admin/addItem" method="POST" class="myCenteredContainer" style="text-align: right">
        <h3>
            <c:choose>
                <c:when test="${editItem.id != null}">
                    Edit Item ${editItem.id}
                </c:when>
                <c:otherwise>
                    Add Item
                </c:otherwise>
            </c:choose>
        </h3>
        <input type="hidden" name="id" value="${editItem.id}">
        Book ID <input type="text" name="bookId" value="${editItem.bookId}"><br>
        Condition <select id="itemCondition" style="width:203px" name="itemCondition" value="${editItem.itemCondition}">
            <c:forEach items="${itemConditionList}" var="itemConditionName" varStatus="loop">
                <option value="${loop.getCurrent()}">${itemConditionName}</option>
            </c:forEach>
        </select><br>
        <center><button type="submit">Submit</button></center>
    </form>

    <!-- Item search results -->
    <center>
        <form action="/admin/searchItem" method="GET">
            <button type="submit">Show All Items</button>
        </form>
    </center>
    <table class="table myTable">
        <c:if test="${itemList.size() > 0}">
            <tr>
                <th>Item Id</th>
                <th>Book Id</th>
                <th>Condition</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${itemList}" var="item">
                <tr scope="row">
                    <td>${item.id}</td>
                    <td>${item.book.id}</td>
                    <td>${item.itemCondition}</td>
                    <td>
                        <form action="/admin/editItem" method="POST">
                            <input type="hidden" name="item" value="${item.id}">
                            <button type="submit">Edit</button>
                        </form>
                    </td>
                    <td>
                        <form action="/admin/deleteItem" method="POST">
                            <input type="hidden" name="item" value="${item.id}">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>

    <jsp:include page="../include/footer.jsp" />