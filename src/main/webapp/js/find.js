function findMovie() {
    const findMovieForm = document.forms.namedItem("findMovieForm");
    let formData = new FormData(findMovieForm);
    window.location = '/lab1/pages/get-by-id-form?id=' + formData.get('id');
}