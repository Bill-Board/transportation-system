<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="car.title"/> </title>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">

                <div class="row">
                    <div class="col">
                        <h2 class="text-center">
                            <fmt:message key="car.title"/>
                        </h2>
                    </div>
                    <c:if test="${isAdmin != null}">
                        <div class="col-auto">
                            <button type="button" class="btn float-right" style="background-color: #19687e"
                                    onclick="window.location.href='create'; return false;">
                                <i class="fas fa-plus"></i> Add Car
                            </button>
                        </div>
                    </c:if>
                </div>
                <table class="table table-striped" id="carTable">
                    <thead>
                    <tr>
                        <th scope="col" class="text-center">Serial No.</th>
                        <th scope="col">Name</th>
                        <th scope="col" class="text-center">Available seat</th>
                        <th scope="col" colspan=3 class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entity" items="${cars}" varStatus="loop">
                        <c:url var="detailsLink" value="/car/details">
                            <c:param name="carId" value="${entity.id}" />
                            <c:param name="backLink" value="/car/list" />
                        </c:url>
                        <c:url var="editLink" value="/car/edit">
                            <c:param name="carId" value="${entity.id}" />
                        </c:url>
                        <tr>
                            <td class="text-center">${loop.index + 1}</td>
                            <td>${entity.name}</td>
                            <td class="text-center">${entity.availableSeat}</td>
                            <td class="text-center"><a href="${detailsLink}" class="btn btn-detail"><i class="fas fa-info-circle"></i></a></td>
                            <td class="text-center"><a href="${editLink}" class="btn btn-edit"><i class="fas fa-edit"></i></a></td>
                            <td class="text-center"><a class="btn btn-danger carRemove" data-id="${entity.id}"><i class="fas fa-trash-alt"></i></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>

<!-- Modal -->
<div class="modal fade" id="carRemoveModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="carRemoveModalLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <fmt:message key="car.sure.remove"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <form:form action="car" method="POST" class="d-inline mr-2 float-right">
                    <input type="hidden" id="carId" name="carId" value="">
                    <button type="submit" class="btn btn-success" name="action_remove">Yes</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#carTable').DataTable({
            "columnDefs": [
                { "orderable": true, "searchable": true, "targets": [0, 1, 2] },
                { "orderable": false, "searchable": false, "targets": [3, 4, 5] }
            ]
        });
    });

    $(document).on('click', '.carRemove', function() {
        $('#carRemoveModal').modal('show');

        var carId = $(this).data('id');
        $('#carId').val(carId);
    });
</script>

</body>
</html>