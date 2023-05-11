<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <p>${sessionScope.email}</p>

        <h2>Your Ads:</h2>
        <c:forEach var="ad" items="${userAds}">
            <div class="col-md-6">
                <h3>${ad.title}</h3>
                <p>${ad.description}</p>
                <br>
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
            </div>
        </c:forEach>
    </div>
    <script>
        $(document).ready(function() {
            $('.edit-btn').on('click', function() {
                var updateForm = $(this).siblings('.update-profile');
                updateForm.toggle();
            });
        });
    </script>

</body>
</html>
