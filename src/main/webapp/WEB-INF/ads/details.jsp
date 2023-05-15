<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Ad Details" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<div class="container">
    <h1>${ad.title}</h1>
    <p>${ad.description}</p>
    <p>Categories: <c:forEach var="category" items="${ad.categories}">${category.name}<c:if test="${!categoryStatus.last}">, </c:if></c:forEach></p>
    <p>Posted by: ${username}</p>
</div>

</body>
</html>

