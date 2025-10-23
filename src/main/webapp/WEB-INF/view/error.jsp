<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title><fmt:message key="error.title"/></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    </head>
    
    <body>
        <div class="container p-3 text-center">
            <img src="${pageContext.request.contextPath}/assets/image/image_1.svg" class="img-fluid" alt="error">
            <c:choose>
                <c:when test="${errorCode == 404}">
                    <h1 class="display-3">
                        <fmt:message key="error.404"/>
                    </h1>
                </c:when>
                <c:when test="${illegalAccess != null}">
                    <h1 class="display-3">
                        <fmt:message key="error.illegal.access"/>
                    </h1>
                </c:when>
                <c:when test="${noResultFound}">
                    <h1 class="display-3">
                        <fmt:message key="error.no.result.found"/>
                    </h1>
                </c:when>
                <c:otherwise>
                    <h1 class="display-3">
                        <fmt:message key="error.unknown"/>
                    </h1>
                </c:otherwise>
            </c:choose>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/">
                <fmt:message key="button.go.home"/>
            </a>
            <br><br>
        </div>
    </body>
</html>
