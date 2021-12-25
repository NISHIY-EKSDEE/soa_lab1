<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Movie names groups</title>
</head>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="group" value="active" />
</jsp:include>
<caption><h2>List of Movies</h2></caption>
<table border="1" cellpadding="13" class="table">
    <thead class="thead-dark">
    <tr>
        <th>Name group</th>
        <th>Count</th>
    </tr>
    </thead>
<c:forEach var="group" items="${groups}">
    <tr>
        <td>
            ${group[0]}
        </td>
        <td>
            ${group[1]}
        </td>
    </tr>
</c:forEach>
</table>
</body>
</html>