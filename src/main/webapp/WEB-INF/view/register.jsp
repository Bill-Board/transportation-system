<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/signup.css">
    <title><fmt:message key="signup.title"/></title>
</head>
<body>
<div class="signup bg-light">
    <div class="container bg-white signup-container m-4">
        <div class="row">
            <div class="col-md-6 p-4">
                <h2 class=" text-center "><fmt:message key="signup.header"/> </h2>

                <form:form action="register" modelAttribute="employeeCmd" method="POST">

                    <c:set var="readOnly" value="${!employeeCmd.employee.isNew()}"/>

                    <!-- First Name -->
                    <div class="form-group row">
                        <label for="firstName" class="col-sm-3 col-form-label form-label"><fmt:message key="input.employee.name.first"/> <span
                                style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input path="employee.firstName" id="firstName" class="form-control"
                                        cssErrorClass="form-control border-danger"/>
                            <div>
                                <form:errors path="employee.firstName" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Last Name -->
                    <div class="form-group row">
                        <label for="lastName" class="col-sm-3 col-form-label form-label">Last name: <span
                                style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input path="employee.lastName" id="lastName" class="form-control"
                                        cssErrorClass="form-control border-danger"/>
                            <div>
                                <form:errors path="employee.lastName" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Email -->
                    <div class="form-group row">
                        <label for="email" class="col-sm-3 col-form-label form-label">Email: <span
                                style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input path="employee.authenticationInfo.email" id="email" type="email"
                                        cssErrorClass="form-control border-danger"
                                        class="form-control" readonly="${readOnly}"/>
                            <div>
                                <form:errors path="employee.authenticationInfo.email" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- DOB -->
                    <div class="form-group row">
                        <label for="dob" class="col-sm-3 col-form-label form-label">Date of Birth:<span style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input path="employee.dateOfBirth" id="dob" type="date"
                                        cssErrorClass="form-control border-danger"
                                        class="form-control"/>
                            <div>
                                <form:errors path="employee.dateOfBirth" cssClass="error" />
                            </div>
                        </div>
                    </div>

                    <!-- Phone -->
                    <div class="form-group row">
                        <label for="phoneNumber" class="col-sm-3 col-form-label form-label">Phone: <span style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input path="employee.phoneNumber" id="phoneNumber" cssErrorClass="form-control border-danger"
                                        class="form-control"/>
                            <div>
                                <form:errors path="employee.phoneNumber" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Gender -->
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label form-label">Gender: <span style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <c:forEach var="gender" items="${employeeCmd.genderOptions}">
                                <div class="form-check form-check-inline">
                                    <form:radiobutton path="employee.gender" value="${gender}"
                                                      class="form-check-input" id="gender-${gender}"/>
                                    <label class="form-check-label" for="gender-${gender}">${gender.getDisplayValue()}</label>
                                </div>
                            </c:forEach>
                            <div>
                                <form:errors path="employee.gender" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Employee_type -->
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label form-label">Employee Type:</label>
                        <div class="col-sm-9">
                            <c:forEach var="employeeType" items="${employeeCmd.typeOptions}">
                                <div class="form-check form-check-inline">
                                    <form:checkbox path="employee.employeeTypes" value="${employeeType}" class="form-check-input"
                                                   id="employeeType-${employeeType}"/>
                                    <label class="form-check-label" for="skill-${employeeType}">${employeeType.displayValue}</label>
                                </div>
                            </c:forEach>
                            <div>
                                <form:errors path="employee.employeeTypes" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Password -->
                    <div class="form-group row">
                        <label for="password" class="col-sm-3 col-form-label form-label">Password: <span style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input type="password" path="employee.authenticationInfo.password" id="password"
                                        class="form-control"
                                        cssErrorClass="form-control border-danger"/>
                            <div>
                                <form:errors path="employee.authenticationInfo.password" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Confirm Password -->
                    <div class="form-group row">
                        <label for="conPassword" class="col-sm-3 col-form-label form-label">Confirm Password: <span
                                style="color: red">*</span></label>
                        <div class="col-sm-9">
                            <form:input type="password" path="employee.authenticationInfo.confirmPassword" id="conPassword"
                                        class="form-control" cssErrorClass="form-control border-danger"/>
                            <div>
                                <form:errors path="employee.authenticationInfo.confirmPassword" cssClass="error"/>
                            </div>
                        </div>
                    </div>

                    <!-- Button -->
                    <div class="form-group row">
                        <a href="${pageContext.request.contextPath}/login" class="btn w-100 signup-cancel-btn mt-2">Cancel</a>
                        <input type="submit" name="action_save" class="btn w-100 signup-btn" value="Save">
                    </div>
                </form:form>
                <hr>
                <p class="text-center text-mute"> Go Back to <small><a href="${pageContext.request.contextPath}/login"><b>Log In</b></a> </small></p>
            </div>

            <div class="col-md-6 bg-photo">
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const adminCheckbox = document.getElementById("employeeType-ADMIN");
        const employeeCheckbox = document.getElementById("employeeType-EMPLOYEE");
        const supportStaffCheckbox = document.getElementById("employeeType-SUPPORT_STAFF");

        adminCheckbox.addEventListener("change", function () {
            if (this.checked || employeeCheckbox.checked) {
                supportStaffCheckbox.checked = false;
                supportStaffCheckbox.disabled = true;
            } else {
                supportStaffCheckbox.disabled = false;
            }
        });

        employeeCheckbox.addEventListener("change", function () {
            if (this.checked || adminCheckbox.checked) {
                supportStaffCheckbox.checked = false;
                supportStaffCheckbox.disabled = true;
            } else {
                supportStaffCheckbox.disabled = false;
            }
        });

        supportStaffCheckbox.addEventListener("change", function () {
            if (this.checked) {
                adminCheckbox.checked = false;
                employeeCheckbox.checked = false;
                adminCheckbox.disabled = true;
                employeeCheckbox.disabled = true;
            } else {
                adminCheckbox.disabled = false;
                employeeCheckbox.disabled = false;
            }
        });
    });


    document.addEventListener("DOMContentLoaded", function () {
        var passwordInput = document.getElementById('password');
        var confirmPasswordInput = document.getElementById('conPassword');

        function validatePassword() {
            var password = passwordInput.value;
            var confirmPassword = confirmPasswordInput.value;

            var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

            if (!regex.test(password)) {
                passwordInput.setCustomValidity("Password must contain at least one lowercase letter, one uppercase letter, and be at least 8 characters long.");
                passwordInput.classList.add("error");
                passwordInput.classList.add("border-danger");
                passwordInput.classList.remove("border-success");
            } else {
                passwordInput.setCustomValidity("");
                passwordInput.classList.remove("error");
                passwordInput.classList.remove("border-danger");
                passwordInput.classList.add("border-success");
            }

            if (password !== confirmPassword) {
                confirmPasswordInput.classList.add("error");
                confirmPasswordInput.classList.add("border-danger");
                confirmPasswordInput.classList.remove("border-success");
            } else {
                confirmPasswordInput.classList.remove("error");
                confirmPasswordInput.classList.remove("border-danger");
                confirmPasswordInput.classList.add("border-success");
            }
        }

        passwordInput.addEventListener('input', validatePassword);
        confirmPasswordInput.addEventListener('input', validatePassword);
    });
</script>
</body>
</html>