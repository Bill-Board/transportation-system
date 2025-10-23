<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="employee.pending.list"/> </title>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <h2 class="text-center">
                    <fmt:message key="employee.pending.list"/>
                </h2>

                <c:choose>
                    <c:when test="${not empty pendingEmployees}">
                        <table class="table table-striped table-border" id="employeePendingTable">
                            <thead>
                            <tr>
                                <th scope="col">Serial No.</th>
                                <th scope="col">Name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Phone No</th>
                                <th class="text-center" scope="col">Role</th>
                                <th class="text-center" scope="col">Decline</th>
                                <th class="text-center" scope="col">Approve</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="entity" items="${pendingEmployees}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${entity.firstName} ${entity.lastName}</td>
                                    <td>${entity.authenticationInfo.email}</td>
                                    <td>${entity.phoneNumber}</td>
                                    <td class="text-center">
                                        <c:forEach var="role" items="${entity.employeeTypes}">
                                            ${role}
                                        </c:forEach>
                                    </td>
                                    <td class="text-center"><a class="btn btn-danger employeeDecline" data-id="${entity.id}"><i class="fas fa-times-circle"></i></a></td>
                                    <td class="text-center"><a class="btn btn-success employeeApprove" data-id="${entity.id}"><i class="fas fa-check-circle"></i></a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p class="text-center">No pending request</p>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>

<!-- Employee Approval Modal -->
<div class="modal fade" id="employeeApprovalModal" tabindex="-1" role="dialog" aria-labelledby="employeeApprovalModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="employeeApprovalModalLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center">
                Are you sure to approve this employee?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <form:form action="employee" method="POST" class="d-inline mr-2 float-right">
                    <input type="hidden" id="employeeId" name="employeeId" value="">
                    <button type="submit" class="btn btn-success" name="action_approve">Yes</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- Employee Decline Modal -->
<div class="modal fade" id="employeeDeclineModal" tabindex="-1" role="dialog" aria-labelledby="employeeDeclineModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="employeeDeclineModalLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center">
                Are you sure to decline this employee?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <form:form action="employee" method="POST" class="d-inline mr-2 float-right">
                    <input type="hidden" id="employeeId" name="employeeId" value="">
                    <button type="submit" class="btn btn-success" name="action_decline">Yes</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#employeePendingTable').DataTable({
            "columnDefs": [
                { "orderable": true, "searchable": true, "targets": [0, 1, 4] },
                { "orderable": false, "searchable": false, "targets": [2, 3, 5, 6] }
            ]
        });
    });

    $(document).on('click', '.employeeApprove', function() {
        $('#employeeApprovalModal').modal('show');

        var employeeId = $(this).data('id');
        $('#employeeId').val(employeeId);
    });

    $(document).on('click', '.employeeDecline', function() {
        $('#employeeDeclineModal').modal('show');

        var employeeId = $(this).data('id');
        $('#employeeId').val(employeeId);
    });
</script>

</body>
</html>
