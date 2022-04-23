<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="include/header.jsp" />

    <style>
        body {
            background: url("https://picsum.photos/seed/100/1920/1080");
            background-position: center;
            background-size: cover;
            background-attachment: fixed
        }
    </style>

    <h1>Library System</h1>

    <!-- search form -->
    <form action="/index/searchSubmit" class="myCenteredContainer" method="post">
        <h3>Search</h3>
        <input type="text" name="searchInput" id="searchInput" value="${form.searchInput}">
        <br>
        <center><button type="submit">Submit</button></center>
        <center><button type="button" id="buttonAdvanced">Advanced</button></center>
        <!-- advanced controls -->
        <div id="searchAdvanced" style="display: none">
            <!-- radio buttons -->
            <input type="radio" id="searchRadioTitle" name="searchRadio" value="title" checked="true">
            <label for="html">Title</label><br>
            <input type="radio" id="searchRadioAuthor" name="searchRadio" value="author">
            <label for="css">Author</label><br>
            <input type="radio" id="searchRadioCategory" name="searchRadio" value="category">
            <label for="javascript">Category</label><br>

            <!-- checkbox buttons -->
            <input type="checkbox" id="searchCheck" name="searchCheck" value="caseSensitive">
            <label for="html">Case Sensitive</label><br>
        </div>
    </form>

    <!-- display errors -->
    <c:if test="${not empty error}">
        <div class="myErrorContainer">
            ${error}
        </div>
    </c:if>

    <!-- display search results -->
    <c:if test="${form.searchResult != null && empty form.searchResult && empty error}">
        <div class="myCenteredContainer">
            No results
        </div>
    </c:if>
    <c:if test="${not empty form.searchResult}">
        <table class="table myTable">
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Category</th>
                <th></th>
            </tr>
            <c:forEach items="${form.searchResult}" var="book">
                <tr scope="row">
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.category}</td>
                    <td>
                        <form action="/details" method="POST">
                            <input type="hidden" name="id" value="${book.id}">
                            <button type="submit">Details</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <script type="text/javascript" src="../../pub/js/script.js"></script>

    <jsp:include page="include/footer.jsp" />