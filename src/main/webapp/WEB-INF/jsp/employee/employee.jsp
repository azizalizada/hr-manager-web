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
    <title>Employee Profile</title>
</head>
<body>
    <c:choose>
        <c:when test="${not empty requestScope.employee}">
            <h2>Employee Profile</h2>
            ID:  ${requestScope.employee.id}<br/>
            First name: ${requestScope.employee.firstName}<br/>
            Last name: ${requestScope.employee.lastName}<br/>
            Salary: ${requestScope.employee.salary}<br/>
        </c:when>
        <c:otherwise>
            <h2>Employee not found!</h2>
        </c:otherwise>
    </c:choose>

</body>
</html>
