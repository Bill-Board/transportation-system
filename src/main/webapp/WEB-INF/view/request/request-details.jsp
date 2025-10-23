<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <title> <fmt:message key="location.title"/> </title>
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
                        <h2><i class="fas fa-info-circle"></i> Request Details</h2>
                    </div>
                    <div class="card-body">
                        <form:form id="approvalForm" modelAttribute="notificationCmd" method="get">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="requestedName">Requested Employee Name:</label>
                                        <div class="input-group">
                                            <form:input type="text" class="form-control" id="requestedName" readonly="true" path="employee.name"/>
                                            <div class="input-group-append">
                                                <button class="btn btn-primary requestEmployeeDetailsBtn" type="button"><i class="fas fa-info-circle"></i> Details</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="requestedEmail">Requested Employee Email:</label>
                                        <form:input path="employee.authenticationInfo.email" type="email" class="form-control" id="requestedEmail" readonly="true"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="routeName">Route Name:</label>
                                        <div class="input-group">
                                            <form:input path="route.name" type="text" class="form-control" id="routeName" readonly="true"/>
                                            <div class="input-group-append">
                                                <button class="btn btn-primary routeDetailsBtn" type="button"><i class="fas fa-info-circle"></i> Details</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="carName">Car Name:</label>
                                        <div class="input-group">
                                            <form:input path="car.name" type="text" class="form-control" id="carName" readonly="true"/>
                                            <div class="input-group-append">
                                                <button class="btn btn-primary carDetailsBtn" type="button"><i class="fas fa-car"></i> Car Details</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="availableSeats">Available Seats:</label>
                                        <form:input path="car.availableSeat" type="number" class="form-control" id="availableSeats" readonly="true"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="driverName">Driver Name:</label>
                                        <div class="input-group">
                                            <form:input path="car.driver.name" type="text" class="form-control" id="driverName" readonly="true"/>
                                            <div class="input-group-append">
                                                <button class="btn btn-primary driverDetailsBtn" type="button"><i class="fas fa-info-circle"></i> Details</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="comment">Comment:</label>
                                <form:textarea path="request.comment" class="form-control" id="comment" rows="4" readonly="true"/>
                            </div>
                        </form:form>
                    </div>
                    <div class="card-footer">
                        <form:form action="request" method="POST" class="d-inline flot-left">
                            <input type="hidden" name="requestId" value="${notificationCmd.request.id}">
                            <button type="submit" class="btn btn-secondary" name="action_back"><i class="fas fa-arrow-left"></i> Back</button>
                        </form:form>
                        <c:if test="${notificationCmd.request.status == 'PENDING'}">
                            <c:choose>
                                <c:when test="${notificationCmd.request.car.availableSeat > 0}">
                                    <div class="d-inline mr-2 float-right">
                                        <button id="approveBtn" type="submit" class="btn btn-success"><i class="fas fa-check"></i> Approve</button>
                                    </div>
                                    <form:form action="request" method="POST" class="d-inline ml-auto float-right mr-3">
                                        <input type="hidden" name="requestId" value="${notificationCmd.request.id}">
                                        <button type="submit" class="btn btn-danger" name="action_decline"><i class="fas fa-times"></i> Decline</button>
                                    </form:form>
                                </c:when>
                                <c:otherwise>
                                    <form:form action="request" method="POST" class="d-inline ml-auto float-right">
                                        <input type="hidden" name="requestId" value="${notificationCmd.request.id}">
                                        <button type="submit" class="btn btn-danger" name="action_decline"><i class="fas fa-times"></i> Decline</button>
                                    </form:form>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<!-- Approval Modal -->
<div class="modal fade" id="approvalModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure to approve this request and assign in this car?
                (If this employee already assigned in a car, it will assign in requested car and leave from older car)
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <form:form action="request" method="POST" class="d-inline mr-2 float-right">
                    <input type="hidden" name="requestId" value="${notificationCmd.request.id}">
                    <button type="submit" class="btn btn-success" name="action_approve">Approve</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Employee Details Modal -->
<div class="modal fade" id="requestEmployeeDetailsModal" tabindex="-1" role="dialog"
     aria-labelledby="requestEmployeeDetailsModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="requestEmployeeDetailsModalLabel">Employee Details</h5>
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
                            <h4 class="mt-2"><span>${notificationCmd.employee.name}</span></h4>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <h5 class="mb-3">Basic Information</h5>
                        <ul class="list-unstyled">
                            <li><strong>Phone Number: </strong><span>${notificationCmd.employee.phoneNumber}</span></li>
                            <li><strong>Email: </strong><span>${notificationCmd.employee.authenticationInfo.email}</span></li>
                            <li><strong>Age: </strong><span>${notificationCmd.employee.age}</span></li>
                            <li><strong>Date Of Birth: </strong><span>${notificationCmd.employee.dateOfBirth}</span></li>
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

<!-- Car Details Modal -->
<div class="modal fade" id="carDetailsModal" tabindex="-1" role="dialog"
     aria-labelledby="carDetailsModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="carDetailsModalLabel">Car Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-4">
                        <div class="text-center">
                            <div class="profile-icon">
                                <i class="fas fa-car fa-7x"></i>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <h5 class="mb-3">Basic Information</h5>
                        <ul class="list-unstyled">
                            <li><strong>Name: </strong><span>${notificationCmd.car.name}</span></li>
                            <li><strong>Registration No: </strong><span>${notificationCmd.car.registrationNo}</span></li>
                            <li><strong>Total Seat: </strong><span>${notificationCmd.car.totalSeat}</span></li>
                            <li><strong>Available Seat: </strong><span>${notificationCmd.car.availableSeat}</span></li>
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
                            <h4 class="mt-2"><span>${notificationCmd.car.driver.name}</span></h4>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <h5 class="mb-3">Basic Information</h5>
                        <ul class="list-unstyled">
                            <li><strong>Phone Number: </strong><span>${notificationCmd.car.driver.phoneNumber}</span></li>
                            <li><strong>Email: </strong><span>${notificationCmd.car.driver.authenticationInfo.email}</span></li>
                            <li><strong>Age: </strong><span>${notificationCmd.car.driver.age}</span></li>
                            <li><strong>Date Of Birth: </strong><span>${notificationCmd.car.driver.dateOfBirth}</span></li>
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

<%--route details modal--%>
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
                    <h3><p>${notificationCmd.route.name}</p></h3>
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
                            <c:forEach var="entity" items="${notificationCmd.locations}" varStatus="loop">
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
<script>
    $(document).on('click', '.requestEmployeeDetailsBtn', function() {
        $('#requestEmployeeDetailsModal').modal('show');
    });
    $(document).on('click', '#approveBtn', function() {
        $('#approvalModal').modal('show');
    });
    $(document).on('click', '.carDetailsBtn', function() {
        $('#carDetailsModal').modal('show');
    });
    $(document).on('click', '.driverDetailsBtn', function() {
        $('#driverDetailsModal').modal('show');
    });
    $(document).on('click', '.routeDetailsBtn', function() {
        $('#routeDetailsModal').modal('show');
    });
</script>
</body>
</html>
