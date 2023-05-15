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
    <input class="form-control mr-sm-2" type="search" name="q" placeholder="Search Ads" aria-label="Search">
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
</form>

<n class="container">
    <h1>Here Are all the ads!</h1>

    <c:forEach var="ad" items="${ads}">
        <div class="col-md-6">
            <h2><a href="${pageContext.request.contextPath}/ads/details?id=${ad.id}">${ad.title}</a></h2>
            <p>${ad.description}</p>
            <p>Categories: <c:forEach var="category" items="${ad.categories}">${category.name}<c:if test="${!categoryStatus.last}">, </c:if></c:forEach></p>


            <button class="update-btn">Update</button>
            <form class="update-form" action="/ads/update" method="POST" style="display: none">
                <input type="hidden" name="id" value="${ad.id}" />
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${ad.title}" />
                <br/>
                <label for="description">Description:</label>
                <textarea id="description" name="description">${ad.description}</textarea>
                <br/>
                <label for="categories">Categories:</label>
                <select id="categories" name="categories" multiple>
                    <c:forEach var="category" items="${allCategories}">
                        <option value="${category.id}" <c:if test="${fn:contains(ad.categories, category)}">selected</c:if>>${category.name}</option>
                    </c:forEach>
                </select>
                <br/>
                <input type="submit" value="Confirm" />
            </form>

            <form action="/ads/delete" method="POST">
                <input type="hidden" name="id" value="${ad.id}" />
                <input type="submit" value="Delete Ad" />
            </form>
        </div>
    </c:forEach>
    </n>
    <div class="text-center">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/ads/create">Create New Ad</a>
    </div>
</div>

<script>
    $(document).ready(function() {
        $('.update-btn').on('click', function() {
            var updateForm = $(this).siblings('.update-form');
            updateForm.toggle();
        });
    });
</script>

</body>
</html>
