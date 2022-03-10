<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Movies</title>
    <script language="JavaScript" type="text/JavaScript" src="${pageContext.request.contextPath}/js/movieForm.js"></script>
</head>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="new" value="active" />
</jsp:include>
<style>
    <%@ include file="/css/form.css" %>
</style>
<div class="movie-form">
    <caption>
        <h2>
            <c:if test="${movie != null}">
                Edit Movie
            </c:if>
            <c:if test="${movie == null}">
                Add New Movie
            </c:if>
        </h2>
    </caption>
    <div class="error-msg">
    </div>
    <c:if test="${movie != null}">
    <div align="center">
    <form name="updateMovieForm"></c:if>
        <c:if test="${movie == null}">
        <form align="center"  name="addMovieForm"></c:if>
            <c:if test="${movie != null}">
                <input type="hidden" name="id" value="<c:out value='${movie.id}' />" class="form-control"/>
            </c:if>
            <p>Name:
                <input type="text" name="name" value="<c:out value='${movie.name}' />" class="form-control"/> </p>

            <p>X:
                <input type="text" name="x" value="<c:out value='${movie.coordinates.x}' />" class="form-control"/></p>

            <p>Y:
                <input type="text" name="y" value="<c:out value='${movie.coordinates.y}' />" class="form-control"/></p>

            <p>Oscars:
                <input type="text" name="oscarsCount" value="<c:out value='${movie.oscarsCount}' />" class="form-control"/></p>

            <p>Golden Palm:
                <input type="text" name="goldenPalmCount" value="<c:out value='${movie.goldenPalmCount}' />" class="form-control"/></p>

            <p>Budget:
                <input type="text" name="budget" value="<c:out value='${movie.budget}' />" class="form-control"/>
            </p>

            <p>Genre:
                <select name="genre" value="<c:out value='${movie.genre}' />" class="form-control">
                    <option value="WESTERN">WESTERN</option>
                    <option value="COMEDY">COMEDY</option>
                    <option value="HORROR">HORROR</option>
                    <option value="FANTASY">FANTASY</option>
                </select></p>

            <p>Director: </p>
            <p>Name: <input type="text" name="director-name" value="<c:out value='${movie.director.name}'/>" class="form-control"/></p>
            <p>Location name: <input type="text" name="director-location-name" value="<c:out value='${movie.director.location.name}'/>" class="form-control"/></p>
            <p>Location X: <input type="text" name="director-location-x" value="<c:out value='${movie.director.location.x}'/>" class="form-control"/></p>
            <p>Location Y: <input type="text" name="director-location-y" value="<c:out value='${movie.director.location.y}'/>" class="form-control"/></p>
            <p>Location Z: <input type="text" name="director-location-z" value="<c:out value='${movie.director.location.z}'/>" class="form-control"/></p>
            <p>Birthday:</p>
            <p>Date: <input type="date" name="birthday-date" value='${movie.director.birthday.format( DateTimeFormatter.ofPattern("yyyy-MM-dd"))}' class="form-control"></p>
            <p>Time: <input type="time" name="birthday-time"
                            value='${movie.director.birthday.format(DateTimeFormatter.ofPattern("HH:mm"))}' class="form-control"></p>

            <c:if test="${movie == null}">
                <button class="btn btn-primary mx-auto mt-2" type="button" onclick="createMovie()">Add</button></c:if>
        </form>
        <c:if test="${movie != null}">
        <button class="btn btn-primary mx-auto mt-2" onclick="updateMovie()">Update</button></c:if>
    </div>
</div>
<script>
    <%@ include file="/js/movieForm.js" %>
</script>
</body>
</html>
