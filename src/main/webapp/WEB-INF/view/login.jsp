<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <title><fmt:message key="login.title"/> </title>
</head>
    <body>
    <c:if test="${message != null}">
        <div class="text-center alert alert-success" role="alert">
                ${message}
        </div>
    </c:if>

    <c:if test="${declineMessage != null}">
        <div class="text-center alert alert-danger" role="alert">
                ${declineMessage}
        </div>
    </c:if>
        <div class="login bg-light">
            <div class="container bg-white login-container m-4">
                <div class="row">
                    <div class="col-md-6 p-4">
                        <h1 class=" text-center welcome "><fmt:message key="login.welcome.message"/> </h1>
                        <c:url var="loginAction" value="login"/>
                        <form:form action="${loginAction}" method="post" modelAttribute="authCmd">
                            <div class="form-group">
                                <form:input type="email" path="authenticationInfo.email" placeholder="Email" class="form-control" required="true"/>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:input type="password" id="password" path="authenticationInfo.password" placeholder="Password" class="form-control" required="true"/>
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                            <i class="fa fa-eye" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </div>
                                <form:errors path="authenticationInfo.email" cssClass="error" />
                            </div>
                            <button type="submit" class="btn w-100 login-btn">Log In</button>
                        </form:form>
                        <hr>
                        <p class="text-center text-mute"> <small>Don't Have An Account? <a href="employee/register">Sign Up</a> </small></p>
                    </div>
                    <div class="col-md-6 bg-photo">
                    </div>
                </div>
            </div>
        </div>
        <script>
            document.getElementById('togglePassword').addEventListener('click', function () {
                var passwordField = document.getElementById('password');
                var type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordField.setAttribute('type', type);
            });
        </script>
    </body>
</html>

