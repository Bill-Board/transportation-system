<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<%--details modal--%>
<div class="modal fade" id="employeeDetailsModal" tabindex="-1" role="dialog"
     aria-labelledby="employeeDetailsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="employeeDetailsModalLabel">Employee Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-4">
                        <div class="text-center">
                            <div class="profile-icon">
                                <i class="fas fa-user fa-7x"></i>
                            </div>
                            <h4 class="mt-2"><span id="eName"></span></h4>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <ul class="list-unstyled">
                            <li><strong>Phone Number: </strong><span id="ePhoneNumber"></span></li>
                            <li><strong>Email: </strong><span id="eEmail"></span></li>
                            <li><strong>Age: </strong><span id="eAge"></span></li>
                            <li><strong>Date Of Birth: </strong><span id="eDob"></span></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).on('click', '.employeeDetails', function() {

        $('#employeeDetailsModal').modal('show');

        var employeeId = $(this).data('id');

        $.ajax({
            type: "GET",
            url: '<%= request.getContextPath() %>/employee/details',
            data: {employeeId: employeeId},
            dataType: 'json',
            success: function (employee) {
                $('#eName').text(employee.name);
                $('#ePhoneNumber').text(employee.phoneNumber);
                $('#eEmail').text(employee.authenticationInfo.email);
                $('#eAge').text(employee.age);
                var dob = employee.dateOfBirth;
                if (dob) {
                    var formattedDob = new Date(dob).toLocaleDateString();
                    $('#eDob').text(formattedDob);
                } else {
                    $('#eDob').text("Date of Birth not provided");
                }
            },
            error: function (xhr, status, error) {
                console.error(xhr.responseText);
            }
        });
    });
</script>