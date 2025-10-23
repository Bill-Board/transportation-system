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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/list.css">
        <title> <fmt:message key="route.title"/> </title>
    </head>
    <body>
        <%@include file="./../common/navbar.jsp"%>
        <div class=" my-4" style="min-height: 80%">
            <div class="container dash-content" >
                <div class="row">
                    <%@include file="./../common/sidebar.jsp"%>
                    <div class="col-sm-9">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addRouteModalLabel">Add Route</h5>
                        </div>
                        <div class="modal-body">
                            <form:form modelAttribute="routeCmd" action="route" method="post">
                                <div class="form-group">
                                    <label for="routeName">Route Name:</label>
                                    <form:input path="route.name" class="form-control"
                                                cssErrorClass="form-control border-danger" id="routeName" />
                                    <div>
                                        <form:errors path="route.name" cssClass="error"/>
                                    </div>
                                </div>

                                <!-- List of location buttons -->
                                <div id="locationButtons">
                                    <div class="row">
                                        <div class="col">
                                            <h2 class="text-center">
                                                Pick Points
                                            </h2>
                                        </div>
                                        <div class="col-auto">
                                            <button type="button" class="btn float-right" id="addPickPointBtn" style="background-color: #19687e">
                                                <i class="fas fa-plus"></i> Add Pick Point</button>

                                        </div>
                                    </div>
                                    <p class="error">Pick points should start from Therap.</p>
                                    <table id="locationTable" class="table table-bordered table-striped">
                                        <thead>
                                        <tr>
                                            <th>Serial No</th>
                                            <th>Location Name</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="entity" items="${routeCmd.route.locations}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>${entity.name}</td>
                                                <td>
                                                    <a class="btn btn-danger removePoint" data-id="${entity.id}">Remove</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div>
                                        <form:errors path="route.locations" cssClass="error"/>
                                    </div>
                                </div>
                                <div class="d-inline">
                                    <input type="submit" class="btn btn-secondary" value="&#x2716; Cancel" name="action_back">
                                    <input type="submit" name="action_save" class="btn btn-success float-right" value="&#x2714; Save">
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="./../common/footer.jsp"%>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <%--Modal--%>
        <div id="pickPointModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="pickPointModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="pickPointModalLabel">Select Pick Point</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <select id="locationSelect" class="form-control">
                            <c:forEach var="entity" items="${locations}">
                                <option value="${entity.id}">${entity.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" id="confirmPickPointBtn">Ok</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#addPickPointBtn').click(function() {
                $('#pickPointModal').modal('show');

                $.ajax({
                    type: "GET",
                    url: '<%= request.getContextPath() %>/route/getLocations',
                    contentType: 'application/json',
                    success: function(locations){
                        $('#locationSelect').empty();
                        $.each(locations, function(i, location){
                            $('#locationSelect').append($('<option>').val(location.id).text(location.name));
                        });
                    },
                    error: function(xhr, status, error){
                        console.error(xhr.responseText);
                    }
                });
            });

            $(document).on('click', '.removePoint', function() {
                var locationId = $(this).data('id');

                $.ajax({
                    url: '<%= request.getContextPath() %>/route/removeLocation', // Your endpoint URL
                    type: 'POST',
                    data: { locationId: locationId },
                    success: function(response) {
                        var tableBody = $('#locationTable tbody');
                        tableBody.empty();

                        $.each(response, function(index, location) {
                            var row = '<tr>' +
                                '<td>' + (index + 1) + '</td>' +
                                '<td>' + location.name + '</td>' +
                                '<td><a class="btn btn-danger removePoint" data-id="' + location.id + '">Remove</a></td>' +
                                '</tr>';
                            tableBody.append(row);
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error(error);
                    }
                });
            });

            $('#confirmPickPointBtn').click(function() {
                var locationId = $('#locationSelect').val();

                $.ajax({
                    url: '<%= request.getContextPath() %>/route/updateRouteCmd',
                    type: 'POST',
                    data: { locationId: locationId },
                    success: function(response) {
                        $('#pickPointModal').modal('hide');

                        var tableBody = $('#locationTable tbody');
                        tableBody.empty();

                        $.each(response, function(index, location) {
                            var row = '<tr>' +
                                '<td>' + (index + 1) + '</td>' +
                                '<td>' + location.name + '</td>' +
                                '<td><a class="btn btn-danger removePoint" data-id="' + location.id + '">Remove</a></td>' +
                                '</tr>';
                            tableBody.append(row);
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error(error);
                    }
                });
            });
        </script>
    </body>
</html>
