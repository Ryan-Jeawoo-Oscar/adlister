<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Your Profile" />
    </jsp:include>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<div class="container">
    <h1>Welcome, ${sessionScope.user.username}!</h1>
    <p>Email: ${sessionScope.user.email}</p>

    <input class="edit-btn" type="submit" value="Update Profile">
    <form class="update-profile" action="${pageContext.request.contextPath}/profile/update" method="POST" style="display: none">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="${sessionScope.user.username}" required>
        <br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${sessionScope.user.email}" required>
        <br>
        <input type="submit" value="Confirm">
    </form>

    <h2>Your Ads:</h2>
    <c:forEach var="ad" items="${userAds}">
        <div class="col-md-6">
            <h3>${ad.title}</h3>
            <p>${ad.description}</p>
            <p>Categories: <c:forEach var="category" items="${ad.categories}">${category.name}<c:if test="${!categoryStatus.last}">, </c:if></c:forEach></p>
            <br>

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
</div>

<div class="text-center">
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/ads/create">Create New Ad</a>
</div>

<script>
    $(document).ready(function() {
        $('.edit-btn').on('click', function () {
            var updateForm = $(this).siblings('.update-profile');
            updateForm.toggle();
        });
        $('.update-btn').on('click', function() {
            var updateForm = $(this).siblings('.update-form');
            updateForm.toggle();
        });
    });

</script>

</body>
</html>

