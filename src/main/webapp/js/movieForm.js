function createMovie() {
    const addMovieForm = document.forms.namedItem("addMovieForm");
    let formData = new FormData(addMovieForm);
    let request = new XMLHttpRequest();
    const newMovie = '<movie>' +
        // '         <id>0</id>' +
        '         <name>' + formData.get('name') + '</name>' +
        '         <coordinates>' +
        '            <x>' + formData.get('x') + '</x>' +
        '            <y>' + formData.get('y') + '</y>' +
        '         </coordinates>' +
        '         <oscarsCount>' + formData.get('oscarsCount') + '</oscarsCount>' +
        '         <goldenPalmCount>' + formData.get('goldenPalmCount') + '</goldenPalmCount>' +
        '         <budget>' + formData.get('budget') + '</budget>' +
        '         <genre>' + formData.get('genre') + '</genre>' +
        '         <director>' +
        '            <name>' + formData.get('director-name') + '</name>' +
        '            <location>' +
        '               <name>' + formData.get('director-location-name') + '</name>' +
        '               <x>' + formData.get('director-location-x') + '</x>' +
        '               <y>' + formData.get('director-location-y') + '</y>' +
        '               <z>' + formData.get('director-location-z') + '</z>' +
        '            </location>' +
        '            <birthday>' + formData.get('birthday-date') + "T" + formData.get('birthday-time') + ":00.000" + '</birthday>' +
        '         </director>' +
        '      </movie>'
    request.open("POST", "/lab1/movies");
    request.onload = function (oEvent) {
        getErrorMsg(request);
    };
    request.send(newMovie);
}

function updateMovie() {
    const updateMovieForm = document.forms.namedItem("updateMovieForm");
    let formData = new FormData(updateMovieForm);
    let request = new XMLHttpRequest();
    const newMovie = '<movie>' +
        // '         <id>' + formData.get('id') + '</id>' +
        '         <name>' + formData.get('name') + '</name>' +
        '         <coordinates>' +
        '            <x>' + formData.get('x') + '</x>' +
        '            <y>' + formData.get('y') + '</y>' +
        '         </coordinates>' +
        '         <oscarsCount>' + formData.get('oscarsCount') + '</oscarsCount>' +
        '         <goldenPalmCount>' + formData.get('goldenPalmCount') + '</goldenPalmCount>' +
        '         <budget>' + formData.get('budget') + '</budget>' +
        '         <genre>' + formData.get('genre') + '</genre>' +
        '         <director>' +
        '            <name>' + formData.get('director-name') + '</name>' +
        '            <birthday>' + formData.get('birthday-date') + "T" + formData.get('birthday-time') + ":00.000" + '</birthday>' +
        '         </director>' +
        '      </movie>'
    request.open("PUT", "/lab1/movies/" + formData.get('id'));
    request.onload = function (oEvent) {
        getErrorMsg(request);
    };
    request.send(newMovie);
}

function getErrorMsg(request) {
    let errorMsg = "";
    if (request.status >= 200 && request.status < 300) {
        window.location = '/lab1/pages/main';
    } else {
        console.log(request.response);
        let rawData = $.parseXML(request.response).getElementsByTagName("errors")[0].getElementsByTagName("errors")[0];
        for (let i = 0; i < rawData.children.length; i++) {
            errorMsg += rawData.children[i].children[1].textContent + "<br>"
        }
    }

    $('.error-msg__text').remove();
    $('.error-msg').append("<p class='error-msg__text alert alert-danger'>" + errorMsg + "</p>");
}
