<%--
  Created by IntelliJ IDEA.
  User: student
  Date: 04.04.20
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update Employee Profile</title>
</head>
<body>

    <c:if test="${not empty sessionScope.errorMessage}">
        <h2 style="color: red;">${sessionScope.errorMessage}</h2>
    </c:if>

    <c:choose>
        <c:when test="${not empty requestScope.employee or not empty requestScope.form}">
            <h2>Employee Profile</h2>
            <form method="post" action="employee?action=save">

                <c:choose>
                    <c:when test="${not empty requestScope.employee}">
                        <input type="hidden" name="id" value="${requestScope.employee.id}"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="id" value="${requestScope.form.id}"/>
                    </c:otherwise>
                </c:choose>


                <c:forEach items="${requestScope.errorMap.firstName}" var="error">
                    <span style="color: red">${error}</span> <br/>
                </c:forEach>

                <c:choose>
                    <c:when test="${not empty requestScope.form.firstName}">
                        First name: <input type="text" name="first_name" value="${requestScope.form.firstName}"> <br/>
                    </c:when>
                    <c:otherwise>
                        First name: <input type="text" name="first_name" value="${requestScope.employee.firstName}"> <br/>
                    </c:otherwise>
                </c:choose>

                <c:forEach items="${requestScope.errorMap.lastName}" var="error">
                    <span style="color: red">${error}</span> <br/>
                </c:forEach>

                <c:choose>
                    <c:when test="${not empty requestScope.form.lastName}">
                        Last name: <input type="text" name="last_name" value="${requestScope.form.lastName}"> <br/>
                    </c:when>
                    <c:otherwise>
                        Last name: <input type="text" name="last_name" value="${requestScope.employee.lastName}"> <br/>
                    </c:otherwise>
                </c:choose>

                <c:forEach items="${requestScope.errorMap.salary}" var="error">
                    <span style="color: red">${error}</span> <br/>
                </c:forEach>

                <c:choose>
                    <c:when test="${not empty requestScope.form.salary}">
                        Salary: <input type="text" name="salary" value="${requestScope.form.salary}"> <br/>
                    </c:when>
                    <c:otherwise>
                        Salary: <input type="text" name="salary" value="${requestScope.employee.salary}"> <br/>
                    </c:otherwise>
                </c:choose>


                <input type="submit" value="Save"> &nbsp;
                <input type="reset" value="Clear">
            </form>
        </c:when>
        <c:otherwise>
            <h2>Employee not found!</h2>
        </c:otherwise>
    </c:choose>

</body>
</html>
