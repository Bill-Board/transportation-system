<%--
  Created by IntelliJ IDEA.
  User: shoebakib
  Date: 4/22/24
  Time: 1:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title><fmt:message key="location.title"/></title>
    <style>
        .location-card {
            max-width: 400px;
            margin: 0 auto;
            margin-bottom: 20px;
        }
        .location-image {
            width: 100%;
            height: auto;
        }
        .back-button {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content">
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <h2 class="mb-4">Location Details</h2>
                <div class="card location-card">
                    <div class="card-body">
                        <h5 class="card-title text-center"><b>${locationCmd.location.name}</b></h5>
                        <div class="form-group">
                            <c:choose>
                                <c:when test="${locationCmd.imageURL != null}">
                                    <img src="${locationCmd.imageURL}" class="card-img-top location-image" alt="Location Image">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/assets/image/not_found_image.jpg" class="card-img-top location-image" alt="Image Not Found">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <form:form action="location" method="post" modelAttribute="locationCmd">
                            <button type="submit" class="btn btn-secondary back-button" name="action_back">
                                <i class="fas fa-arrow-left"></i><span style="margin-left: 5px;">Back</span>
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
</body>
</html>
