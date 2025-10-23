<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="location.title"/> </title>
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
                                    <fmt:message key="location.title"/>
                                </h2>
                            </div>
                            <c:if test="${sessionScope.isAdmin != null}">
                                <div class="col-auto">
                                    <button type="button" class="btn float-right" style="background-color: #19687e"
                                            onclick="window.location.href='create'; return false;">
                                        <i class="fas fa-plus"></i> Add Location
                                    </button>
                                </div>
                            </c:if>
                        </div>
                        <table class="table table-striped" id="locationTable">
                            <thead>
                            <tr>
                                <th scope="col" class="text-center">Serial No.</th>
                                <th scope="col">Name</th>
                                <c:if test="${sessionScope.isAdmin != null || sessionScope.isDriver != null}">
                                    <th scope="col" class="text-center">Details</th>
                                </c:if>
                                <c:if test="${sessionScope.isAdmin != null}">
                                    <th scope="col" class="text-center">Edit</th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="entity" items="${locations}" varStatus="loop">
                                <c:url var="detailsLink" value="/location/details">
                                    <c:param name="locationId" value="${entity.id}"/>
                                </c:url>
                                <c:url var="editLink" value="/location/edit">
                                    <c:param name="locationId" value="${entity.id}"/>
                                </c:url>
                                <tr>
                                    <td class="text-center">${loop.index + 1}</td>
                                    <td>${entity.name}</td>
                                    <c:if test="${sessionScope.isAdmin != null || sessionScope.isDriver != null}">
                                        <td class="text-center"><a href="${detailsLink}" class="btn btn-detail"><i class="fas fa-info-circle"></i></a></td>
                                    </c:if>
                                    <c:if test="${sessionScope.isAdmin != null}">
                                        <td class="text-center"><a href="${editLink}" class="btn btn-edit"><i class="fas fa-edit"></i></a></td>
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
        <script>
            $(document).ready(function () {
                var tableColumns = $('#locationTable').find('tr:first th').length;

                if (tableColumns === 4) {
                    $('#locationTable').DataTable({
                        "columnDefs": [
                            { "orderable": true, "searchable": true, "targets": [0, 1] },
                            { "orderable": false, "searchable": false, "targets": [2, 3] }
                        ]
                    });
                }

                if (tableColumns === 3) {
                    $('#locationTable').DataTable({
                        "columnDefs": [
                            { "orderable": true, "searchable": true, "targets": [0, 1] },
                            { "orderable": false, "searchable": false, "targets": [2] }
                        ]
                    });
                }

                if (tableColumns === 2) {
                    $('#locationTable').DataTable({
                        "columnDefs": [
                            { "orderable": true, "searchable": true, "targets": [0, 1] }
                        ]
                    });
                }
            });
        </script>
    </body>
</html>