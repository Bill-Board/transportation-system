<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="employee.list"/> </title>
</head>
<body>
    <%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <h2 class="text-center">
                    ${whosList}
                </h2>
                <table class="table table-striped" id="employeeTable">
                    <thead>
                    <tr>
                        <th scope="col">Serial No.</th>
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <th scope="col"></th>
                        <c:if test="${isAdmin != null}">
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entity" items="${employees}" varStatus="loop">
                        <c:url var="editLink" value="/employee/edit">
                            <c:param name="employeeId" value="${entity.id}"/>
                        </c:url>

                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${entity.firstName} ${entity.lastName}</td>
                            <td>${entity.authenticationInfo.email}</td>
                            <td><a class="btn btn-detail employeeDetails" data-id="${entity.id}"><i class="fas fa-info-circle"></i></a></td>
                            <c:if test="${isAdmin != null}">
                                <td><a href="${editLink}" class="btn btn-edit"><i class="fas fa-edit"></i></a></td>
                                <td><a class="btn btn-danger employeeRemove" data-id="${entity.id}"><i class="fas fa-trash-alt"></i></a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
    <!-- Remove Confirmation Modal -->
    <div class="modal fade" id="employeeRemoveModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="employeeRemoveModalLabel">Confirmation</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure remove this employee?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <form:form action="employee" method="POST" class="d-inline mr-2 float-right">
                        <input type="hidden" id="employeeId" name="employeeId" value="">
                        <button type="submit" class="btn btn-success" name="action_remove">Yes</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function () {
            $('#employeeTable').DataTable({
                "columnDefs": [
                    { "orderable": true, "searchable": true, "targets": [0, 1, 2] },
                    { "orderable": false, "searchable": false, "targets": [3, 4, 5] }
                ]
            });
        });

        $(document).on('click', '.employeeRemove', function() {
            $('#employeeRemoveModal').modal('show');

            var employeeId = $(this).data('id');
            $('#employeeId').val(employeeId);
        });
    </script>

</body>
</html>