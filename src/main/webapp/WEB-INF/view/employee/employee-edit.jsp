<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="employee.edit"/> </title>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <h2 class="text-center">
                    <c:choose>
                        <c:when test="${sessionScope.Employee.id == employeeCmd.employee.id}">
                            <fmt:message key="employee.profile.edit"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="employee.details.edit"/>
                        </c:otherwise>
                    </c:choose>
                </h2>
                <div class="card-body">
                    <form:form modelAttribute="employeeCmd" action="employee" method="post">
                        <div class="form-group">
                            <label for="firstName"><fmt:message key="input.employee.name.first"/>
                                <span style="color: red">*</span></label>
                            <form:input type="text" class="form-control" id="firstName"
                                        path="employee.firstName" readonly="false" />
                            <div>
                                <form:errors path="employee.firstName" cssClass="error"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastName"><fmt:message key="input.employee.name.last"/>
                                <span style="color: red">*</span></label>
                            <form:input type="text" class="form-control" id="lastName"
                                        path="employee.lastName" readonly="false" />
                            <div>
                                <form:errors path="employee.lastName" cssClass="error"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="email"><fmt:message key="input.employee.email"/>
                                <span style="color: red">*</span></label>
                            <form:input type="text" class="form-control" id="email"
                                        path="employee.authenticationInfo.email" readonly="true" />
                            <div>
                                <form:errors path="employee.authenticationInfo.email" cssClass="error" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="age"><fmt:message key="input.employee.age"/> </label>
                            <form:input type="number" class="form-control" id="age"
                                        path="employee.age" readonly="true" />
                        </div>

                        <div class="form-group">
                            <label for="phone"><fmt:message key="input.employee.phone.no"/><span style="color: red">*</span></label>
                            <form:input type="text" class="form-control" id="phone"
                                        path="employee.phoneNumber" readonly="${employeeCmd.readOnly}" />
                            <div>
                                <form:errors path="employee.phoneNumber" cssClass="error" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="dob"><fmt:message key="input.employee.dob"/><span style="color: red">*</span> </label>
                            <form:input type="date" class="form-control" id="dob"
                                        path="employee.dateOfBirth" readonly="${employeeCmd.readOnly}" />
                            <div>
                                <form:errors path="employee.dateOfBirth" cssClass="error" />
                            </div>
                        </div>

                        <div class="card-footer">
                            <div class="d-inline flot-left">
                                <button type="submit" class="btn btn-secondary" name="action_back">
                                    <i class="fas fa-arrow-left" style="margin-right: auto"></i>
                                    <span style="margin-left: 5px;">Back</span>
                                </button>
                            </div>
                            <div class="d-inline float-right">
                                <c:choose>
                                    <c:when test="${sessionScope.Employee.id eq employeeCmd.employee.id}">
                                        <button type="submit" class="btn btn-success" name="action_update">
                                            <i class="fas fa-sync-alt"></i>
                                            <span style="margin-left: 5px;">Update</span>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-success" name="action_details_update">
                                            <i class="fas fa-sync-alt"></i>
                                            <span style="margin-left: 5px;">Update</span>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
</body>
</html>