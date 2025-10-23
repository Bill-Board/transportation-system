<%--modal for rote details--%>
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
                    <h3><p id="routeName"></p></h3>
                </div>
                <div class="form-group row">
                    <div class="col-sm-12">
                        <table class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>Serial No</th>
                                <th>Location Name</th>
                            </tr>
                            </thead>
                            <tbody id="locationTableBody">
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
    $(document).on('click', '.routeDetails', function() {
        $('#routeDetailsModal').modal('show');

        var routeId = $(this).data('id');

        $.ajax({
            type: "GET",
            url: '<%= request.getContextPath() %>/route/details',
            data: {routeId : routeId},
            dataType : "json",
            success: function(routeDetailsDTO){
                $('#routeName').text(routeDetailsDTO.name);

                var locationTableBody = $('#locationTableBody');
                locationTableBody.empty();
                $.each(routeDetailsDTO.locations, function(index, location) {
                    var row = '<tr>' +
                        '<td>' + (index + 1) + '</td>' +
                        '<td>' + location.name + '</td>' +
                        '</tr>';
                    locationTableBody.append(row);
                });
            },
            error: function(xhr, status, error){
                console.error(xhr.responseText);
            }
        });
    });
</script>