<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-3 side-bar">
    <h3><fmt:message key="sidebar.title"/></h3>
    <ul class="nav nav-pills flex-column">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/">
                <fmt:message key="sidebar.dashboard"/>
            </a>
        </li>
        <c:if test="${sessionScope.isAdmin}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/employee/list">
                    <fmt:message key="sidebar.employee"/>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.isAdmin}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/car/list">
                    <fmt:message key="sidebar.car"/>
                </a>
            </li>
        </c:if>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/route/list">
                <fmt:message key="sidebar.route"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/location/list">
                <fmt:message key="sidebar.location"/></a>
        </li>
        <c:if test="${sessionScope.isAdmin}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/employee/driverList">
                    <fmt:message key="sidebar.driver"/>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.isEmployee}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/request/list">
                    <fmt:message key="sidebar.request"/>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.isAdmin}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/notification/list">
                    <fmt:message key="sidebar.notification"/>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.isSuperAdmin}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/employee/pending-list">
                    <fmt:message key="sidebar.pending.employee"/>
                </a>
            </li>
        </c:if>
    </ul>
    <hr class="d-sm-none">

    <div class="row small bottom">
        <c:if test="${sessionScope.isSuperAdmin}">
            <ul>
                <li>
                    <p><fmt:message key="sidebar.login.as.super.admin"/> </p>
                </li>
            </ul>
        </c:if>
        <c:if test="${sessionScope.isAdmin}">
            <ul>
                <li>
                    <p><fmt:message key="sidebar.login.as.admin"/> </p>
                </li>
            </ul>
        </c:if>
        <c:if test="${sessionScope.isEmployee}">
            <ul>
                <li>
                    <p><fmt:message key="sidebar.login.as.employee"/></p>
                </li>
            </ul>
        </c:if>
        <c:if test="${sessionScope.isDriver}">
            <ul>
                <li>
                    <p><fmt:message key="sidebar.login.as.driver"/></p>
                </li>
            </ul>
        </c:if>
    </div>
</div>

<script>
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            navLinks.forEach(l => l.classList.remove('active'));
            link.classList.add('active');
        });
    });
</script>