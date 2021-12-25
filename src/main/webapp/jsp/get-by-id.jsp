<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Get by id</title>
</head>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="id" value="active" />
</jsp:include>
<form name="findMovieForm" align="center" style="margin-top: 20px">
    <caption><h2>Get movie by id</h2></caption>
    <input class="form-control mt-3" type="text" name="id" value="0" style="width: 30%; margin: 0 auto;"/>
    <c:if test="${movie == null && msg != null}">
        <div class="mx-auto" style="color: red">
            <h7>${msg}</h7>       </div>
    </c:if>
    <button class="btn btn-primary mx-auto mt-3" onclick="findMovie()">Find</button>
</form>
<c:if test="${movie != null}">
    <div align="center" class="mx-5">
        <caption><h2>Movie with id ${movie.id}</h2></caption>
        <table border="1" cellpadding="13" class="table">
            <thead class="thead-dark">
            <tr>
                <th>Name</th>
                <th>X</th>
                <th>Y</th>
                <th>creationDate</th>
                <th>oscarsCount</th>
                <th>goldenPalmCount</th>
                <th>budget</th>
                <th>genre</th>
                <th>name</th>
                <th>birthday</th>
            </tr>
            </thead>
            <tr>
                <td>${movie.name}</td>
                <td>${movie.coordinates.x}</td>
                <td>${movie.coordinates.y}</td>
                <td>${movie.creationDate.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                <td>${movie.oscarsCount}</td>
                <td>${movie.goldenPalmCount}</td>
                <td>${movie.budget}</td>
                <td>${movie.genre}</td>
                <td>${movie.director.name}</td>
                <td>${movie.director.birthday.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            </tr>
        </table>
    </div>
</c:if>
</body>
</html>
