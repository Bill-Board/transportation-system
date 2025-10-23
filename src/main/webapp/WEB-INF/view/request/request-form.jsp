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
    <title> <fmt:message key="request.title"/> </title>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <div class="modal-header">
                    <h5 class="modal-title" id="addRouteModalLabel"><fmt:message key="request.make.a.request"/></h5>
                </div>
                <div class="modal-body">
                    <form:form modelAttribute="requestCmd" action="carRequest" method="post">
                        <div class="form-group">
                            <label for="routeSelect"><fmt:message key="request.form.select.route"/> </label>
                            <form:select path="request.route" class="form-control" id="routeSelect">
                                <form:option value="" label="Select Location" />
                                <form:options items="${requestCmd.routes}" itemValue="id" itemLabel="name" />
                            </form:select>
                            <div>
                                <form:errors path="request.route" cssClass="error"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-group">
                                <label for="carSelect"><fmt:message key="request.form.select.car"/></label>
                                <form:select path="request.car" class="form-control" id="carSelect">
                                    <form:option value="" label="Select Car" />
                                    <form:options items="${cars}" itemValue="id" itemLabel="details" />
                                </form:select>
                                <div>
                                    <form:errors path="request.car" cssClass="error"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="comment"><fmt:message key="request.form.select.comment"/></label>
                                <form:textarea path="request.comment" class="form-control" id="comment" placeholder="Comment" />
                            </div>
                        </div>

                        <div class="d-inline">
                            <input type="submit" name="action_back" class="btn btn-secondary" value="&#x2716; Cancel" >
                            <input type="submit" name="action_save" class="btn btn-primary float-right" value="&#x2714; Save">
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function(){
        $('#routeSelect').change(function(){
            var routeId = $(this).val();
            $.ajax({
                type: "GET",
                url: '<%= request.getContextPath() %>/request/getCarsByRoute',
                data: { routeId: routeId },
                dataType : "json",
                success: function(cars){
                    $('#carSelect').empty();
                    $('#carSelect').append($('<option>').val('').text('Select Car'));
                    $.each(cars, function(i, car){
                        $('#carSelect').append($('<option>').val(car.id).text(car.details));
                    });
                },
                error: function(xhr, status, error){
                    console.error(xhr.responseText);
                }
            });
        });
    });
</script>
</body>
</html>
