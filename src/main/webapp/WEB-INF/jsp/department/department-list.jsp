<%--
  Created by IntelliJ IDEA.
  User: student
  Date: 11.04.20
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Department List</title>
    <style>
        table, tr, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h2></h2>
<table>
    <tr>
        <th>ID</th>
        <th>Department Name</th>
        <th>Manager First Name</th>
        <th>Manager Last Name</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${requestScope.departmentList}" var="department">
        <tr>
            <td>${department.id}</td>
            <td>${department.name}</td>
            <c:choose>
                <c:when test="${department.manager != null}">
                    <td>${department.manager.firstName}</td>
                    <td>${department.manager.lastName}</td>
                </c:when>
                <c:otherwise>
                    <td>N/A</td>
                    <td>N/A</td>
                </c:otherwise>
            </c:choose>
            <td><a href="cs?action=viewDepartment&id=${department.id}">View</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
