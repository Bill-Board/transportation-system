<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title> <fmt:message key="route.title"/> </title>
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
                            <fmt:message key="route.title"/>
                        </h2>
                    </div>
                    <c:if test="${isAdmin != null}">
                        <div class="col-auto">
                            <button type="button" class="btn float-right" style="background-color: #19687e"
                                    onclick="window.location.href='create'; return false;">
                                <i class="fas fa-plus"></i> <fmt:message key="route.add"/>
                            </button>
                        </div>
                    </c:if>
                </div>
                <table class="table table-striped" id="routeTable">
                    <thead>
                    <tr>
                        <th scope="col" class="text-center">Serial No.</th>
                        <th scope="col" class="text-center">Name</th>
                        <th scope="col" class="text-center"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entity" items="${routes}" varStatus="loop">
                        <tr>
                            <td class="text-center">${loop.index + 1}</td>
                            <td class="text-center">${entity.name}</td>
                            <td class="text-center"><a class="btn btn-detail routeDetails" data-id="${entity.id}"><i class="fas fa-info-circle"></i></a></td>
                        </tr>
                        <c:set var="serialNo" value="${serialNo + 1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>
<%@include file="./../modal/route-details.jsp"%>
<script>
    $(document).ready(function () {
        $('#routeTable').DataTable({
            "columnDefs": [
                { "orderable": true, "searchable": true, "targets": [0, 1] },
                { "orderable": false, "searchable": false, "targets": [2] }
            ]
        });
    });
</script>
</body>
</html>