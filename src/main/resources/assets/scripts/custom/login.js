'use strict'


function setupFormListeners(){
    const form = document.body.getElementsByClassName('needs-validation')[0];

    // Loop over them and prevent submission

    form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
            event.preventDefault()
            event.stopPropagation()
        }

        form.classList.add('was-validated')
    }, false);
}

document.addEventListener("DOMContentLoaded", function() {
    setupFormListeners();
});
