<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <title><fmt:message key="homepage.title."/></title>
    <style>

        .dash-card {
            background: linear-gradient(white, rgb(231, 234, 255));
        }

        .dash-btn-card {
            color: white;
            padding: 10px;
            background: rgb(30, 82, 115);
            border-radius: 5px;
        }

        .dash-btn-card:hover {
            color: white;
            border-radius: 0px;
            background: rgb(43, 117, 164);
        }

    </style>
</head>
    <body>

    <%@include file="common/navbar.jsp"%>

    <div class=" my-4" style="min-height: 80%">
            <div class="container dash-content">
                <div class="row">
                    <%--sidebar--%>
                    <%@include file="common/sidebar.jsp"%>

                    <div class="col-sm-9">
                        <h1 class="text-center"> Dashboard</h1>
                        <div class="row">
                            <c:if test="${sessionScope.isLoggedIn && car != null}">
                                <c:url var="carDetailsLink" value="/car/details">
                                    <c:param name="carId" value="${car.id}"/>
                                </c:url>
                                <div class="col-md-4 mt-4">
                                    <div class="card dash-card">
                                        <div class="card-body d-flex flex-column align-items-center justify-content-center">
                                            <h5 class="card-title"> <fmt:message key="label.dashboard.page.car.title"/> </h5>
                                            <a href="${carDetailsLink}" class="card-link dash-btn-card">
                                                <i class="fas fa-car fa-7x"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <!-- Driver Details -->
                            <c:if test="${sessionScope.isLoggedIn && car != null}">
                                <div class="col-md-4 mt-4">
                                    <div class="card dash-card">
                                        <div class="card-body d-flex flex-column align-items-center justify-content-center">
                                            <h5 class="card-title"><fmt:message key="label.dashboard.page.driver.title"/> </h5>
                                            <a href="#" class="card-link dash-btn-card" data-toggle="modal" data-target="#driverDetailsModal">
                                                <i class="fas fa-user fa-7x"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.isLoggedIn && car != null}">
                                <!-- Route Details -->
                                <div class="col-md-4 mt-4">
                                    <div class="card dash-card">
                                        <div class="card-body d-flex flex-column align-items-center justify-content-center">
                                            <h5 class="card-title"><fmt:message key="label.dashboard.page.route.title"/> </h5>
                                            <a href="#" class="card-link dash-btn-card" data-toggle="modal" data-target="#routeDetailsModal">
                                                <i class="fas fa-road fa-7x"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <!-- location Details -->
                            <div class="col-md-4 mt-4">
                                <div class="card dash-card">
                                    <div class="card-body d-flex flex-column align-items-center justify-content-center">
                                        <h5 class="card-title"><fmt:message key="label.dashboard.page.location.title"/> </h5>
                                        <a href="#" class="card-link dash-btn-card" data-toggle="modal" data-target="#locationDetailsModal">
                                            <i class="fas fa-map-marker-alt fa-7x"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    <%--driver details modal--%>
    <div class="modal fade" id="driverDetailsModal" tabindex="-1" role="dialog"
         aria-labelledby="driverDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="driverDetailsModalLabel">Employee Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="text-center">
                                <div class="profile-icon">
                                    <i class="fas fa-user fa-7x"></i>
                                </div>
                                <h4 class="mt-2"><span>${car.driver.name}</span></h4>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <h5 class="mb-3">Basic Information</h5>
                            <ul class="list-unstyled">
                                <li><strong>Phone Number: </strong><span>${car.driver.phoneNumber}</span></li>
                                <li><strong>Email: </strong><span>${car.driver.authenticationInfo.email}</span></li>
                                <li><strong>Age: </strong><span>${car.driver.age}</span></li>
                                <li><strong>Date Of Birth: </strong><span>${car.driver.dateOfBirth}</span></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div id="routeDetailsModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="routeDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="routeDetailsModalLabel">Route Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div style="text-align: center;">
                        <h3><p>${route.name}</p></h3>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-12">
                            <!-- Table listing the locations -->
                            <table class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>Serial No</th>
                                    <th>Location Name</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="entity" items="${routeLocations}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${entity.name}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </div>
    </div>

<%--locations--%>
    <div id="locationDetailsModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="locationDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="locationDetailsModalLabel">Locations</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div style="text-align: center;">
                        <h3><p> <fmt:message key="label.dashboard.page.location.title"/> </p></h3>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-12">
                            <!-- Table listing the locations -->
                            <table class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>Serial No</th>
                                    <th>Location Name</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="entity" items="${locations}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${entity.name}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <%@include file="common/footer.jsp"%>
    </body>
</html>