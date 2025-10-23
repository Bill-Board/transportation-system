<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="car.title"/> </title>
    <style>
        .clickable-icon {
            cursor: pointer;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 5px;
        }
        .clickable-icon:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <div class="card">
                    <div class="card-header">
                        <h2>Car Details</h2>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><i class="fas fa-car"></i> Car Name: ${carCmd.car.name}</p>
                                <c:if test="${sessionScope.isAdmin}">
                                    <p><i class="fas fa-id-card"></i> Registration No: ${carCmd.car.registrationNo}</p>
                                </c:if>
                                <p><i class="fas fa-chair"></i> Total Seat: ${carCmd.car.totalSeat}</p>
                                <p><i class="fas fa-chair"></i> Available Seat: ${carCmd.car.availableSeat}</p>
                            </div>
                            <div class="col-md-6">
                                <div class="d-flex flex-column align-items-center
                                            justify-content-center clickable-icon employeeDetails" data-id="${carCmd.car.driver.id}">
                                    <i class="fas fa-user fa-2x"></i>
                                    <p><fmt:message key="input.car.driver"/> </p>
                                </div>
                                <div class="d-flex flex-column align-items-center
                                            justify-content-center clickable-icon routeDetails" data-id="${carCmd.car.route.id}">
                                    <i class="fas fa-road fa-2x"></i>
                                    <p> <fmt:message key="input.car.route"/> </p>
                                </div>
                                <c:if test="${carCmd.car.manager != null}">
                                    <div class="d-flex flex-column align-items-center
                                    justify-content-center clickable-icon employeeDetails" data-id="${carCmd.car.manager.id}">
                                        <i class="fas fa-user fa-2x"></i>
                                        <p><fmt:message key="input.car.Manager"/> </p>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div style="margin-top: 10%">
                            <h3><fmt:message key="employee.list"/></h3>
                            <c:choose>
                                <c:when test="${not empty carCmd.employees}">
                                    <table class="table table-striped" id="employeeTable">
                                        <thead>
                                        <tr>
                                            <th scope="col" class="text-center">Serial No.</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Email</th>
                                            <th scope="col" class="text-center"> Details </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="entity" items="${carCmd.employees}" varStatus="loop">
                                            <tr>
                                                <td class="text-center">${loop.index + 1}</td>
                                                <td>${entity.name}</td>
                                                <td>${entity.authenticationInfo.email}</td>
                                                <td class="text-center"><a class="btn btn-detail employeeDetails" data-id="${entity.id}">
                                                    <i class="fas fa-info-circle"></i>
                                                </a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-center"><fmt:message key="car.no.employee.assign"/> </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="card-footer">
                        <form:form action="car" method="post" modelAttribute="carCmd">
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
<%@include file="./../modal/employee-details.jsp"%>
<%@include file="./../modal/route-details.jsp"%>

<script>
    $(document).ready(function () {
        $('#employeeTable').DataTable({
            "columnDefs": [
                { "orderable": true, "searchable": true, "targets": [0, 1, 2] },
                { "orderable": false, "searchable": false, "targets": [3] }
            ]
        });
    });
</script>

</body>
</html>