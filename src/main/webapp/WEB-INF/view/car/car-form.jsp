<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title> <fmt:message key="car.title"/> </title>
</head>
<body>
<%@include file="./../common/navbar.jsp"%>
<div class=" my-4" style="min-height: 80%">
    <div class="container dash-content" >
        <div class="row">
            <%@include file="./../common/sidebar.jsp"%>
            <div class="col-sm-9">
                <div>
                    <div>
                        <h2 class="text-center" id="addRouteModalLabel">
                            <c:choose>
                                <c:when test="${carCmd.car.isNew()}">
                                    <fmt:message key="car.create"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="car.update"/>
                                </c:otherwise>
                            </c:choose>
                        </h2>
                    </div>
                    <div class="modal-body">
                        <form:form modelAttribute="carCmd" action="car" method="post">

                            <div class="form-group">
                                <label for="name"><fmt:message key="input.car.name"/> <span style="color: red">*</span></label>
                                <form:input path="car.name" class="form-control"
                                            cssErrorClass="form-control border-danger"
                                            id="name" placeholder="Enter Car Name"/>
                                <div>
                                    <form:errors path="car.name" cssClass="error"/>
                                </div>
                            </div>

                            <div class="form-group">
                            <label for="carRegistrationNo"><fmt:message key="input.car.registration.no"/> <span style="color: red">*</span></label>
                                <form:input path="car.registrationNo" class="form-control"
                                            cssErrorClass="form-control border-danger"
                                            id="carRegistrationNo" placeholder="Enter Registration No"/>
                                <div>
                                    <form:errors path="car.registrationNo" cssClass="error"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="carTSeat"><fmt:message key="input.car.total.seat"/><span style="color: red">*</span> </label>
                                <form:input path="car.totalSeat" cssClass="form-control"
                                            cssErrorClass="form-control border-danger"
                                            id="carTSeat" placeholder="Enter Total seat" readonly="${carCmd.readOnly}"/>
                                <div>
                                    <form:errors path="car.totalSeat" cssClass="error"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="carASeat"><fmt:message key="input.car.available.seat"/><span style="color: red">*</span> </label>
                                <form:input path="car.availableSeat" cssClass="form-control"
                                            cssErrorClass="form-control border-danger"
                                            id="carASeat" placeholder="Enter Available seat" readonly="${carCmd.readOnly}"/>
                                <div>
                                    <form:errors path="car.availableSeat" cssClass="error"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="route"><fmt:message key="input.car.route"/><span style="color: red">*</span> </label>
                                <form:select path="car.route" class="form-control"
                                             cssErrorClass="form-control border-danger" id="route">
                                    <form:option value="" label="Select a Route"/>
                                    <form:options items="${carCmd.routes}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <div>
                                    <form:errors path="car.route" cssClass="error"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="driver"><fmt:message key="input.car.driver"/><span style="color: red">*</span> </label>
                                <form:select path="car.driver" class="form-control"
                                             cssErrorClass="form-control border-danger" id="driver">
                                    <form:option value="" label="Select a driver"/>
                                    <form:options items="${carCmd.drivers}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <div>
                                    <form:errors path="car.driver" cssClass="error"/>
                                </div>
                            </div>

                            <c:if test="${!carCmd.car.isNew()}">
                                <div class="form-group">
                                    <label for="manager"><fmt:message key="input.car.Manager"/><span style="color: red">*</span> </label>
                                    <form:select path="car.manager" class="form-control"
                                                 cssErrorClass="form-control border-danger" id="manager">
                                        <form:option value="" label="Select a Manager"/>
                                        <form:options items="${carCmd.managers}" itemValue="id" itemLabel="name"/>
                                    </form:select>
                                </div>
                            </c:if>

                            <div class="d-inline">
                                <div class="float-left">
                                    <button type="submit" name="action_back" class="btn btn-secondary">
                                        <i class="fas fa-times"></i>
                                        <span style="margin-left: 5px;"><fmt:message key="btn.cancel"/> </span>
                                    </button>
                                </div>
                                <c:choose>
                                    <c:when test="${carCmd.car.isNew()}">
                                        <div class="float-right">
                                            <button type="submit" name="action_save" class="btn btn-success">
                                                <i class="fas fa-save"></i>
                                                <span style="margin-left: 5px;">
                                                   <fmt:message key="btn.save"/>
                                                </span>
                                            </button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="float-right">
                                            <button type="submit" name="action_save" class="btn btn-success">
                                                <i class="fas fa-sync-alt"></i>
                                                <span style="margin-left: 5px;">
                                                   <fmt:message key="btn.update"/>
                                                </span>
                                            </button>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
</body>
</html>