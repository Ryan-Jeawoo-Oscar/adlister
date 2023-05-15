<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Viewing All The Ads" />
    </jsp:include>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<form action="${pageContext.request.contextPath}/ads/search" method="get" class="form-inline my-2 my-lg-0">
    <input class="form-control mr-sm-2" type="search" name="q" placeholder="Search ads" aria-label="Search">
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
</form>

<div class="container">
    <h1>Here Are all the ads!</h1>

    <c:forEach var="ad" items="${ads}">
        <div class="col-md-6">
            <h2><a href="${pageContext.request.contextPath}/ads/details?id=${ad.id}">${ad.title}</a></h2>
            <p>${ad.description}</p>
            <p>Categories: <c:forEach var="category" items="${ad.categories}">${category.name}<c:if test="${!categoryStatus.last}">, </c:if></c:forEach></p>
        </div>
    </c:forEach>
</div>

<div class="text-center">
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/ads/create">Create New Ad</a>
</div>

</body>
</html>

