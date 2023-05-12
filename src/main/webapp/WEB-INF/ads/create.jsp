<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Create a new Ad" />
    </jsp:include>
</head>
<body>
    <div class="container">
        <h1>Create a new Ad</h1>
        <form action="${pageContext.request.contextPath}/ads/create" method="post">
            <div class="form-group">
                <label for="title">Title</label>
                <input id="title" name="title" class="form-control" type="text" value="${not empty title ? title : ''}">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" class="form-control" type="text">${not empty description ? description : ''}</textarea>
            </div>
            <input type="submit" class="btn btn-block btn-primary">
        </form>
        <c:if test="${not empty error}">
            <p class="error-message" style="color: red">${error}</p>
        </c:if>
    </div>
</body>
</html>
