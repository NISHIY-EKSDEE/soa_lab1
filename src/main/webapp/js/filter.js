let movies;

$( document ).ready(function() {
    let fields = ["Name", "X", "Y", "Date", "OscarsCount", "GoldenPalmCount", "Budget", "Genre", "DirectorName", "Birthday"]

    fields.forEach((element) => {
        $('.is' + element + 'Disabled').change(function () {
            if ($('.is' + element + 'Disabled').is(':checked')) {
                $('.' + element).removeAttr("disabled");
            } else {
                $('.' + element).attr("disabled", "true");
            }
        });
    })

    let filterForm = document.forms.namedItem("filterForm");
    filterForm.addEventListener('submit', function (ev) {
        filterListener(filterForm, '/lab1/movies', ev);
    }, false);
});

function parseDate(date, time) {
    let year = date.children[0].textContent;
    let month = date.children[1].textContent;
    let day = date.children[2].textContent;
    let hours = time.children[0].textContent;
    let min = time.children[1].textContent;
    // yyyy-MM-dd HH:mm
    return year + "-" + month + "-" + day + " " + hours + ":" + min;
}

function filterListener(form, url, ev) {
    let formData = new FormData(form);
    let request = new XMLHttpRequest();
    request.responseType = 'document';
    let getStr = "?";
    for (let pair of formData.entries()) {
        getStr += pair[0] + '=' + pair[1] + '&';
    }
    getStr = url + getStr.substr(0, getStr.length - 1);
    request.open("GET", getStr);

    request.onload = function (oEvent) {
        createMovieTable(request);
    };
    request.send(formData);
    ev.preventDefault();
}

function changePagesQuality(totalItems) {
    const limit = document.getElementById("limit").value;
    const pagesQuality = Math.ceil(totalItems / limit);
    $('#selectedPage').remove();
    let html = "<select id='selectedPage' name='selectedPage'>";
    for (let i = 1; i < pagesQuality+1; i++) {
        html+='<option value='+ i + '>'+ i + '</option>'
    }
    html+= "</select>"
    $('.selectedPage').append(html);
}

function deleteMovie(id) {
    let request = new XMLHttpRequest();
    request.open("DELETE", "/lab1/movies/" + id);
    request.onload = function (oEvent) {
        window.location = '/lab1/pages/main';
    };
    request.send();
}

function createMovieTable(request) {
    if (request.status === 200) {
        console.log(request)
        let filteredMovies = [];
        movies = request.response.getElementsByTagName("paginationResult")[0].getElementsByTagName("list")[0];
        let k, i, j, oneRecord, oneObject, innerObject;
        for (i = 0; i < movies.children.length; i++) {
            oneRecord = movies.children[i];
            oneObject = filteredMovies[filteredMovies.length] = {};
            for (j = 0; j < oneRecord.children.length; j++) {
                if (oneRecord.children[j].children.length !== 0 && !oneRecord.children[j].tagName.includes('creationDate')) {
                    innerObject = oneObject[oneRecord.children[j].tagName] = {};
                    for (k = 0; k < oneRecord.children[j].children.length; k++) {
                        console.log(oneRecord.children[j].children[k].tagName);
                        if (oneRecord.children[j].children[k].tagName.includes('birthday')) {
                            let birthdayDate = oneRecord.children[j].children[k].children[0];
                            let birthdayTime = oneRecord.children[j].children[k].children[1];
                            innerObject[oneRecord.children[j].children[k].tagName] = parseDate(birthdayDate, birthdayTime);
                        } else {
                            innerObject[oneRecord.children[j].children[k].tagName] = oneRecord.children[j].children[k].textContent;
                        }
                    }
                    oneObject[oneRecord.children[j].tagName] = innerObject;
                } else {
                    if (oneRecord.children[j].tagName.includes('creationDate')) {
                        let dateTime = oneRecord.children[j].children[0];
                        let date = dateTime.children[0];
                        let time = dateTime.children[1];
                        oneObject[oneRecord.children[j].tagName] = parseDate(date, time);
                    } else {
                        oneObject[oneRecord.children[j].tagName] = oneRecord.children[j].textContent;
                    }
                }
            }
        }
        $('.table-rows').remove();
        let html;
        for (i = 0; i < filteredMovies.length; i++) {
            html += "<tr class='table-rows'><td>" + filteredMovies[i].id + "</td><td>" + filteredMovies[i].name + "</td><td>" + filteredMovies[i].coordinates.x
                + "</td><td>" + filteredMovies[i].coordinates.y + "</td><td>" + filteredMovies[i].creationDate + "</td><td>" + filteredMovies[i].oscarsCount
                + "</td><td>" + filteredMovies[i].goldenPalmCount + "</td><td>" + filteredMovies[i].budget
                + "</td><td>" + filteredMovies[i].genre + "</td><td>" + filteredMovies[i].director.name
                + "</td><td>" + filteredMovies[i].director.birthday + "</td>" +
                "<td><a class='btn btn-primary mx-auto mt-2' href=edit-form?id=" + filteredMovies[i].id + ">Edit</a>" +
                "    <button class='btn btn-primary mx-auto mt-2' onclick='deleteMovie(${movie.id});'>Delete</button></td></tr>";
        }
        $('table').append(html);

        let totalItems = parseInt(request.response.getElementsByTagName("paginationResult")[0].getElementsByTagName("totalItems")[0].textContent, 10);
        changePagesQuality(totalItems);

        console.log(filteredMovies);
    } else {
        console.log("Error " + request.status);
    }
}
