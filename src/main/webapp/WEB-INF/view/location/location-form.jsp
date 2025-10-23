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
                <form:form modelAttribute="locationCmd" enctype="multipart/form-data" action="location" method="post">
                    <div class="form-group">
                        <form:input type="text" class="form-control" id="name" placeholder="Location name" path="location.name"
                                    readonly="${locationCmd.readOnly}"/>
                        <form:errors path="location.name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="attachment">Attachment</label>
                        <input type="file" class="form-control" id="attachment" name="attachment"/>
                    </div>
                    <div class="form-group mt-4">
                        <c:choose>
                            <c:when test="${!locationCmd.readOnly}">
                                <c:choose>
                                    <c:when test="${locationCmd.location.isNew()}">
                                        <button type="submit" class="btn btn-success float-right" name="action_save" value="Save">
                                            <i class="fas fa-save"></i><span style="margin-left: 5px;">Save</span>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-success float-right" name="action_save">
                                            <i class="fas fa-sync-alt"></i><span style="margin-left: 5px;">Update</span>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                                <button type="submit" class="btn btn-secondary float-left mr-2" name="action_back">
                                    <i class="fas fa-times"></i><span style="margin-left: 5px;">Cancel</span>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn btn-secondary float-right" name="action_back">
                                    <i class="fas fa-arrow-left"></i><span style="margin-left: 5px;">Back</span>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<%@include file="./../common/footer.jsp"%>
</body>
</html>