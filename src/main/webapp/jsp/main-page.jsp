<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <meta charset="utf-8">
</head>
<style>
    <%@ include file="/css/main.css" %>
</style>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="list" value="active" />
</jsp:include>
<div class="main-page">
    <div class="table" align="center">
        <caption><h2>List of Movies</h2></caption>
        <table border="1" cellpadding="13" class="table">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>X</th>
                <th>Y</th>
                <th>Creation Date</th>
                <th>Oscars</th>
                <th>Golden Palm</th>
                <th>Budget</th>
                <th>Genre</th>
                <th>Director Name</th>
                <th>Director Birthday</th>
                <th>Location name</th>
                <th>Location X</th>
                <th>Location Y</th>
                <th>Location Z</th>
                <th>Actions</th>
            </tr>
            </thead>
            <c:forEach var="movie" items="${movies}">
                <c:if test="${movie != null}">
                <tr class="table-rows">
                    <td>${movie.id}</td>
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
                    <td>${movie.director.location.name}</td>
                    <td>${movie.director.location.x}</td>
                    <td>${movie.director.location.y}</td>
                    <td>${movie.director.location.z}</td>

                    <td>
                        <a class="btn btn-primary mx-auto mt-2" href="edit-form?id=${movie.id}">Edit</a>
                        <button class="btn btn-primary mx-auto mt-2" onclick="deleteMovie(${movie.id});">Delete</button>
                    </td>
                </tr>
                </c:if>
            </c:forEach>
        </table>
    </div>
    <div class="filter">
        <jsp:include page="filter.jsp"/>
    </div>
</div>
</body>
</html>
