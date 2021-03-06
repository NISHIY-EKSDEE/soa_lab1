<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <ul class="navbar-nav">
        <li class="nav-item ${param.list}">
            <a class="nav-link" href="/lab1/pages/main">Movie List</a>
        </li>
        <li class="nav-item ${param.new}">
            <a class="nav-link" href="/lab1/pages/add-movie-form">New Movie</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.id}" href="/lab1/pages/get-by-id-form">Get Movie by id</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.group}" href="/lab1/pages/movie-names-groups">Get movie names groups</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.group}" href="/lab1/movies/count_oscars_count_less/99">Count oscars less</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.group}" href="/lab1/movies/get_by_name_start/value">Get by name start</a>
        </li>
    </ul>
</nav>
</body>
</html>
