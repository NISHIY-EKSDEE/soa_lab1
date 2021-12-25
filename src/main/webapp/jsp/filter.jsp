<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/filter_style.css" type="text/css">
    <script language="JavaScript" type="text/JavaScript" src="${pageContext.request.contextPath}/js/filter.js"></script>
</head>
<style>
    <%@ include file="/css/filter_style.css" %>
</style>
<body>
<caption><h2>Filter</h2></caption>
<form method="get" class="filter-form" name="filterForm">
    <p> Limit </p>
    <select id="limit" class="form-select" name="limit" onchange="changePagesQuality(${totalItems})">
        <option selected value="5">5</option>
        <option value="10">10</option>
        <option value="25">25</option>
    </select>
    <p> Selected Page </p>
    <div class="selectedPage"></div>
    <select id="selectedPage" name="selectedPage">
        <c:forEach var="item" items='${pagesQuality}'>
            <option value="${item}">${item}</option>
        </c:forEach>
    </select>
    <div class="tab-content">
            <div id="filter">
                    <p> <input class="form-check-input isNameDisabled" type="checkbox" > Name:
                        <input type="text" name="name" class="form-control Name" disabled/></p>

                    <p>Coordinates:</p>

                    <p> <input class="form-check-input isXDisabled" type="checkbox" > X: </p>
                    <div class="filter-form__range">
                        <input type="text" name="x1" class="form-control X" disabled/> - <input type="text" name="x2" class="form-control X" disabled/></div>

                    <p> <input class="form-check-input isYDisabled" type="checkbox" > Y: </p>
                    <div class="filter-form__range">
                        <input type="text" name="y1" class="form-control Y" disabled/> - <input type="text" name="y2" class="form-control Y" disabled/></div>

                    <p>Creation date:</p>

                    <p> <input class="form-check-input isDateDisabled" type="checkbox" > Date:</p>
                    <div class="filter-form__range">
                        <input type="date" name="start-creation-date" class="form-control Date" disabled> - <input type="date"
                                                                                                                   name="end-creation-date"
                                                                                                                   class="form-control Date" disabled>
                    </div>

                    <p>Time:</p>
                    <div class="filter-form__range">
                        <input type="time" name="start-creation-time" value="00:00" class="form-control Date" disabled> - <input type="time"
                                                                                                                                 name="end-creation-time"
                                                                                                                                 value="23:59"
                                                                                                                                 class="form-control Date" disabled>
                    </div>

                    <p> <input class="form-check-input isOscarsCountDisabled" type="checkbox" > OscarsCount:</p>
                    <div class="filter-form__range">
                        <input type="text" name="oscarsCount1" class="form-control OscarsCount" disabled/>- <input type="text" name="oscarsCount2" class="form-control OscarsCount" disabled/>
                    </div>

                    <p> <input class="form-check-input isGoldenPalmCountDisabled" type="checkbox" > GoldenPalmCount:</p>
                    <div class="filter-form__range">
                        <input type="text" name="goldenPalmCount1" class="form-control GoldenPalmCount" disabled/> - <input type="text" name="goldenPalmCount2"
                                                                                                                  class="form-control GoldenPalmCount" disabled/></div>

                    <p> <input class="form-check-input isBudgetDisabled" type="checkbox" > Budget:</p>
                    <div class="filter-form__range">
                        <input type="text" name="budget1" class="form-control Budget" disabled/> - <input type="text" name="budget2" class="form-control Budget" disabled/>
                    </div>

                    <p> <input class="form-check-input isGenreDisabled" type="checkbox" > Genre:
                    <div class="form-check form-check-inline">
                        <input class="form-check-input Genre" name="genre" type="radio" id="genre1" value="WESTERN" disabled>
                        <label class="form-check-label" for="genre1">WESTERN</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input Genre" name="genre" type="radio" id="genre2" value="COMEDY" disabled>
                        <label class="form-check-label" for="genre2">COMEDY</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input Genre" name="genre" type="radio" id="genre3" value="HORROR" disabled>
                        <label class="form-check-label" for="genre3">HORROR</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input Genre" name="genre" type="radio" id="genre4" value="FANTASY" disabled>
                        <label class="form-check-label" for="genre4">FANTASY</label>
                    </div>
                    </p>

                    <p>Director: </p>
                    <p> <input class="form-check-input isDirectorNameDisabled" type="checkbox" > Name:
                        <input type="text" name="director-name" class="form-control directorName" disabled/></p>
                    <p> <input class="form-check-input isBirthdayDisabled" type="checkbox" > Birthday:</p>
                    <p>Date:</p>
                    <div class="filter-form__range">
                        <input type="date" name="start-birthday-date" class="form-control Birthday" disabled> - <input type="date"
                                                                                                                       name="end-birthday-date"
                                                                                                                       class="form-control Birthday" disabled></div>
                    <p>Time:</p>
                    <div class="filter-form__range">
                        <input type="time" name="start-birthday-time" value="00:00" class="form-control Birthday" disabled> - <input type="time"
                                                                                                                                     name="end-birthday-time"
                                                                                                                                     value="23:59"
                                                                                                                                     class="form-control Birthday" disabled>
                    </div>

            </div>
            <div id="sort">
                    <h2>Sorting</h2>
                    <input type="radio" class="form-check-input" name="sortBy" value="id" checked>  id<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="name">  name<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="coordinatesX">  x<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="coordinatesY">  y<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="creationDate">  creationDate<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="oscarsCount">  oscarsCount<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="goldenPalmCount">  goldenPalmCount<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="budget">  budget<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="genre">  genre<BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="directorName">  director-name <BR>
                    <input type="radio" class="form-check-input" name="sortBy" value="directorBirthday">  birthday-time <BR>

                <input type="submit" class="btn btn-primary filter-btn" id="filter-btn" value="Submit"/>
            </div>
    </div>
</form>
<script>
    <%@ include file="/js/filter.js" %>
</script>
</body>
</html>
