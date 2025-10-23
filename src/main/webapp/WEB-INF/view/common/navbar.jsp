<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-sm justify-content-between">
    <div><a class="navbar-brand" href="${pageContext.request.contextPath}/"><fmt:message key="navbar.title.name"/></a></div>
    <div>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <div class="row justify-content-center">
                        <a class="nav-link employeeDetails" data-id="${sessionScope.Employee.id}">
                            <div class="col text-center">
                                <i class="fas fa-user"></i> <b><fmt:message key="navbar.show.profile"/></b>
                                <div class="small">${sessionScope.Employee.name}</div>
                            </div>
                        </a>
                    </div>
                </li>
                <li class="nav-item">
                    <div class="row justify-content-center">
                        <a class="nav-link" href="${pageContext.request.contextPath}/employee/update-profile">
                            <div class="col text-center">
                                <i class="fas fa-user-edit"></i> <b><fmt:message key="navbar.edit.profile"/></b>
                            </div>
                        </a>
                    </div>
                </li>

                <li class="nav-item mx-4">
                    <div class="row justify-content-center">
                        <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST">
                            <a class="nav-link logout-btn" href="#" onclick="document.getElementById('logoutForm').submit(); return false;">
                                <div class="col text-center">
                                    <i class="fas fa-sign-out-alt"></i> <b><fmt:message key="navbar.logout"/></b>
                                </div>
                            </a>
                        </form>
                    </div>
                </li>

            </ul>
        </div>
    </div>
</nav>

<c:if test="${message != null}">
    <div class=" text-center alert alert-success" role="alert">
            ${message}
    </div>
</c:if>

<c:if test="${declineMessage != null}">
    <div class="text-center alert alert-danger" role="alert">
            ${declineMessage}
    </div>
</c:if>