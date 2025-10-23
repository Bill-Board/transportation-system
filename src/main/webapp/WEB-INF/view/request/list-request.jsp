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

                <div class="row">
                    <div class="col">
                        <h2 class="text-center">
                            <fmt:message key="request.title"/>
                        </h2>
                    </div>
                    <c:if test="${isEmployee != null}">
                        <div class="col-auto">
                            <button type="button" class="btn float-right" style="background-color: #19687e"
                                    onclick="window.location.href='create'; return false;">
                                <i class="fas fa-car"></i> <fmt:message key="request.apply"/>
                            </button>
                        </div>
                    </c:if>
                </div>

                <table class="table table-striped" id="requestTable">
                    <thead>
                    <tr>
                        <th scope="col" class="text-center">Serial No.</th>
                        <th scope="col">Comment</th>
                        <th scope="col">Car</th>
                        <th scope="col" class="text-center">status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entity" items="${requests}" varStatus="loop">
                        <tr>
                            <td class="text-center">${loop.index + 1}</td>
                            <td>${entity.comment}</td>
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
        $('#requestTable').DataTable();
    });
</script>
</body>
</html>
