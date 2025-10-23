<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="request.title"/> </title>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <h2 class="text-center"><fmt:message key="request.title"/></h2>
                <table class="table table-striped" id="notificationTable">
                    <thead>
                    <tr>
                        <th scope="col" class="text-center">Serial No.</th>
                        <th scope="col">Em. Name</th>
                        <th scope="col">Car</th>
                        <th scope="col" class="text-center">status</th>
                        <th scope="col" class="text-center">Details</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entity" items="${requests}" varStatus="loop">
                        <c:url var="detailsLink" value="/notification/details">
                            <c:param name="requestId" value="${entity.id}" />
                        </c:url>
                        <tr>
                            <td class="text-center">${loop.index + 1}</td>
                            <td>${entity.employee.name}</td>
                            <td>${entity.car.name}</td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${entity.status == 'PENDING'}">
                                        <p style="color: blue">Pending</p>
                                    </c:when>
                                    <c:when test="${entity.status == 'APPROVED'}">
                                        <p style="color: green">Approved</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="color: red">Decline</p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <a href="${detailsLink}" class="btn btn-detail"><i class="fas fa-info-circle"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
<script>
    $(document).ready(function () {
        $('#notificationTable').DataTable({
            "columnDefs": [
                { "orderable": true, "searchable": true, "targets": [0, 1, 2, 3] },
                { "orderable": false, "searchable": false, "targets": [4] }
            ]
        });
    });
</script>
</body>
</html>
